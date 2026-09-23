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
     * Registers the "find patient" flow: a page with the search form, where picking the patient's country
     * ([SearchMaskRepository] decides which countries and fields are available) loads that country's id fields
     * as an htmx fragment, and any change to a field revalidates the whole form.
     *
     * The form itself is submitted normally. A valid search is stored in the session and the browser is
     * redirected to the results page, so the patient ids never appear in a URL (GDPR).
     */
    fun registerRoutes(
        routes: JavalinDefaultRoutingApi,
        auth: AuthProvider,
        masks: SearchMaskRepository,
        client: PatientSearchClient,
    ) {
        val handlers = Handlers(auth, masks, client)
        routes.get("/find-patient", handlers::page)
        routes.get("/find-patient/fields", handlers::fields)
        routes.post("/find-patient/validate", handlers::validateFields)
        routes.post("/find-patient/search", handlers::search)
        routes.get("/find-patient/results", handlers::results)
    }

    /** A validated search, kept in the session between the form post and the results page. */
    private data class PendingSearch(val country: String, val ids: List<PatientId>)

    private const val SEARCH_SESSION_KEY = "findPatient.search"

    private class Handlers(
        private val auth: AuthProvider,
        private val masks: SearchMaskRepository,
        private val client: PatientSearchClient,
    ) {
        /** Full page with the search form, no country selected yet. */
        fun page(ctx: Context) {
            if (auth.current(ctx) == null) {
                ctx.redirect("/?error=not-authorized", HttpStatus.SEE_OTHER)
                return
            }
            ctx.render("find-patient/index.ftlh", formModel(country = "", mask = null))
        }

        /** htmx fragment: the empty id fields for the selected country (nothing if no country is selected). */
        fun fields(ctx: Context) {
            auth.require(ctx)
            val country = ctx.queryParam("country")?.trim().orEmpty()
            if (country.isEmpty()) {
                ctx.html("")
                return
            }
            val mask = masks.get(country) ?: return ctx.unknownCountry()
            ctx.render("find-patient/fields.ftlh", formModel(country, mask))
        }

        /** htmx fragment: the validation errors of the whole form, for htmx to swap into the error slots. */
        fun validateFields(ctx: Context) {
            auth.require(ctx)
            val country = ctx.formParam("country")?.trim().orEmpty()
            val mask = masks.get(country) ?: return ctx.unknownCountry()

            val values = ctx.idValues(mask)
            val input = validate(mask, values)
            ctx.render("find-patient/errors.ftlh", formModel(country, mask, values, input.errors, input.formError))
        }

        /** Form post: re-renders the page with errors, or stores the search and redirects to the results. */
        fun search(ctx: Context) {
            auth.require(ctx)
            val country = ctx.formParam("country")?.trim().orEmpty()
            val mask = masks.get(country) ?: return ctx.unknownCountry()

            val values = ctx.idValues(mask)
            val input = validate(mask, values)
            if (!input.isValid) {
                ctx.status(HttpStatus.UNPROCESSABLE_CONTENT)
                ctx.render("find-patient/index.ftlh", formModel(country, mask, values, input.errors, input.formError))
                return
            }

            ctx.sessionAttribute(SEARCH_SESSION_KEY, PendingSearch(country, input.ids))
            ctx.redirect("/find-patient/results", HttpStatus.SEE_OTHER)
        }

        /** Full page with the results of the search stored in the session by [search]. */
        fun results(ctx: Context) {
            if (auth.current(ctx) == null) {
                ctx.redirect("/?error=not-authorized", HttpStatus.SEE_OTHER)
                return
            }
            val search = ctx.sessionAttribute<PendingSearch>(SEARCH_SESSION_KEY)
            if (search == null) {
                ctx.redirect("/find-patient", HttpStatus.SEE_OTHER)
                return
            }
            // Patient data must not linger in browser or proxy caches
            ctx.header("Cache-Control", "no-store")
            val model = try {
                val patients = client.queryPatient(search.country, search.ids)
                mapOf("patients" to patients.map(::toPatientView))
            } catch (e: Exception) {
                log.error("Patient search failed for country {}", search.country, e)
                mapOf("error" to "Søgningen fejlede.")
            }
            ctx.render("find-patient/results.ftlh", model)
        }

        private fun formModel(
            country: String,
            mask: SearchMask?,
            values: Map<Int, String> = emptyMap(),
            errors: Map<Int, String> = emptyMap(),
            formError: String? = null,
        ): Map<String, Any?> {
            val countries = masks.countries.map { cc -> mapOf("code" to cc, "name" to Labels.country(cc)) }
            return mapOf(
                "countries" to countries,
                "country" to country,
                "fields" to mask?.let { fieldViews(it, values, errors) }.orEmpty(),
                "formError" to formError,
            )
        }
    }
}

private val log = logger()
private val birthDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")


/** A tiny static HTML error fragment for htmx to swap in. Message may not contain user input. */
private fun errorFragment(message: String): String = """<p class="text-red">$message</p>"""

/** The submitted, trimmed id values, keyed by field index. Every field of [mask] gets an entry. */
private fun Context.idValues(mask: SearchMask): Map<Int, String> =
    mask.idFields.indices.associateWith { i -> formParam("id.$i")?.trim().orEmpty() }

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
        val error = fieldError(field, value)
        when {
            error != null -> errors[i] = error
            value.isNotBlank() -> ids.add(PatientId(root = field.domain, extension = value))
        }
    }
    val formError = if (errors.isEmpty() && ids.isEmpty()) "Udfyld mindst ét felt" else null
    return SearchInput(ids, errors, formError)
}

/** The validation error for a single id [field] with the given [value], or null if it is valid. */
private fun fieldError(field: IdField, value: String): String? = when {
    value.isBlank() -> if (field.mandatory) "Feltet skal udfyldes" else null
    field.format != null && !field.format.matches(value) -> "Ugyldigt format"
    else -> null
}

private fun fieldViews(mask: SearchMask, values: Map<Int, String>, errors: Map<Int, String>): List<Map<String, Any?>> =
    mask.idFields.mapIndexed { i, field: IdField ->
        mapOf(
            "index" to i,
            "label" to Labels.ism(field.label),
            "contextualDescription" to field.contextualDescription,
            "mandatory" to field.mandatory,
            "value" to (values[i] ?: ""),
            "error" to errors[i],
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
