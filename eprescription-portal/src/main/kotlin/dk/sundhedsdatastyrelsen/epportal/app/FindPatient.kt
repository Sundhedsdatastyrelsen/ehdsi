package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.ism.IdField
import dk.sundhedsdatastyrelsen.epportal.ism.Labels
import dk.sundhedsdatastyrelsen.epportal.ism.SearchMask
import dk.sundhedsdatastyrelsen.epportal.ism.SearchMaskRepository
import dk.sundhedsdatastyrelsen.epportal.logger
import dk.sundhedsdatastyrelsen.epportal.patient.PatientDemographics
import dk.sundhedsdatastyrelsen.epportal.patient.PatientId
import dk.sundhedsdatastyrelsen.epportal.patient.PatientSearchClient
import io.javalin.http.Context
import io.javalin.http.HttpStatus
import io.javalin.router.JavalinDefaultRoutingApi
import java.time.format.DateTimeFormatter

object FindPatient {
    data class Config(val countries: List<String>, val ismDirectory: String)

    /**
     * Registers the "find patient" flow: a page to pick the patient's country ([SearchMaskRepository]
     * decides which countries and fields are available), an htmx fragment with the country-specific search
     * form, and an htmx fragment with the search results.
     */
    fun registerRoutes(
        routes: JavalinDefaultRoutingApi,
        auth: AuthProvider,
        masks: SearchMaskRepository,
        client: PatientSearchClient,
    ) {
        val handlers = Handlers(auth, masks, client)
        routes.get("/find-patient", handlers::page)
        routes.get("/find-patient/form", handlers::form)
        routes.post("/find-patient/search", handlers::search)
    }

    private class Handlers(
        private val auth: AuthProvider,
        private val masks: SearchMaskRepository,
        private val client: PatientSearchClient,
    ) {
        /** Full page with the country picker. */
        fun page(ctx: Context) {
            if (auth.current(ctx) == null) {
                ctx.redirect("/?error=not-authorized", HttpStatus.SEE_OTHER)
                return
            }
            val countries = masks.countries.map { cc -> mapOf("code" to cc, "name" to Labels.country(cc)) }
            ctx.render("find-patient.ftlh", mapOf("countries" to countries))
        }

        /** htmx fragment: the empty search form for the selected country (nothing if no country is selected). */
        fun form(ctx: Context) {
            auth.require(ctx)
            val country = ctx.queryParam("country")?.trim().orEmpty()
            if (country.isEmpty()) {
                ctx.html("")
                return
            }
            val mask = masks.get(country) ?: return ctx.unknownCountry()
            ctx.render(
                "find-patient-form.ftlh",
                searchFormModel(mask, country, values = emptyMap(), errors = emptyMap(), formError = null),
            )
        }

        /** htmx fragment: the search results, or the form re-rendered with validation errors. */
        fun search(ctx: Context) {
            auth.require(ctx)
            val country = ctx.formParam("country")?.trim().orEmpty()
            val mask = masks.get(country) ?: return ctx.unknownCountry()

            val values = mask.idFields.indices.associateWith { i -> ctx.formParam("id.$i")?.trim().orEmpty() }
            val input = validate(mask, values)
            if (!input.isValid) {
                ctx.render(
                    "find-patient-form.ftlh",
                    searchFormModel(mask, country, values, input.errors, input.formError),
                )
                return
            }

            try {
                val patients = client.queryPatient(country, input.ids)
                ctx.render("find-patient-results.ftlh", mapOf("patients" to patients.map(::toPatientView)))
            } catch (e: Exception) {
                log.error("Patient search failed for country {}", country, e)
                ctx.html(errorFragment("Søgningen fejlede."))
            }
        }
    }
}

private val log = logger()
private val birthDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")


/** A tiny static HTML error fragment for htmx to swap in. Never contains user input, so no escaping is needed. */
private fun errorFragment(message: String): String = """<p class="text-red">$message</p>"""

private fun Context.unknownCountry() {
    status(HttpStatus.BAD_REQUEST)
    html(errorFragment("Ukendt land."))
}

private class SearchInput(
    val ids: List<PatientId>,
    val errors: Map<Int, String>,
    val formError: String?,
) {
    val isValid: Boolean get() = errors.isEmpty() && formError == null
}

/** Validates the submitted id [values] (keyed by field index) against [mask]; blank optional fields are skipped. */
private fun validate(mask: SearchMask, values: Map<Int, String>): SearchInput {
    val errors = mutableMapOf<Int, String>()
    val ids = mutableListOf<PatientId>()
    mask.idFields.forEachIndexed { i, field ->
        val value = values.getValue(i)
        when {
            value.isBlank() -> if (field.mandatory) errors[i] = "Feltet skal udfyldes"
            field.format != null && !field.format.matches(value) -> errors[i] = "Ugyldigt format"
            else -> ids.add(PatientId(root = field.domain, extension = value))
        }
    }
    val formError = if (errors.isEmpty() && ids.isEmpty()) "Udfyld mindst ét felt" else null
    return SearchInput(ids, errors, formError)
}

private fun searchFormModel(
    mask: SearchMask,
    country: String,
    values: Map<Int, String>,
    errors: Map<Int, String>,
    formError: String?,
): Map<String, Any?> {
    val fields = mask.idFields.mapIndexed { i, field: IdField ->
        mapOf(
            "index" to i,
            "label" to Labels.ism(field.label),
            "contextualDescription" to field.contextualDescription,
            "mandatory" to field.mandatory,
            "value" to (values[i] ?: ""),
            "error" to errors[i],
        )
    }
    return mapOf(
        "country" to country,
        "fields" to fields,
        "formError" to formError,
    )
}

private fun toPatientView(patient: PatientDemographics): Map<String, Any?> {
    val name = listOfNotNull(patient.givenName, patient.familyName)
        .joinToString(" ")
        .ifBlank { null }
    return mapOf(
        "name" to name,
        "birthDate" to patient.birthDate?.format(birthDateFormatter),
        "gender" to patient.gender?.let { Labels.gender(it) },
        "addressStreet" to patient.addressStreet,
        "addressPostalCode" to patient.addressPostalCode,
        "addressCity" to patient.addressCity,
        "addressCountry" to patient.addressCountry?.let { Labels.country(it) },
        "phone" to patient.phone,
        "email" to patient.email,
        "ids" to patient.patientIds.map { "${it.extension} (${it.root})" },
    )
}
