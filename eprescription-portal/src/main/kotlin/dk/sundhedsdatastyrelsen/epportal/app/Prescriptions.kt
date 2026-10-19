package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionMetadataParser
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import io.javalin.http.BadRequestResponse
import io.javalin.http.HttpStatus
import io.javalin.http.NotFoundResponse
import io.javalin.router.JavalinDefaultRoutingApi
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import java.util.UUID

object Prescriptions {
    /** Registers the page listing a patient's prescriptions, for a patient found with [FindPatient]. */
    fun registerRoutes(routes: JavalinDefaultRoutingApi, auth: AuthProvider) {
        routes.get("/prescriptions") { ctx ->
            val principal = auth.current(ctx)
            if (principal == null) {
                ctx.redirect("/?error=not-authorized", HttpStatus.SEE_OTHER)
                return@get
            }

            // Get the patient ID query parameter
            val opaquePatientId = ctx.queryParam("opaque-id")
                ?.let { runCatching { UUID.fromString(it) }.getOrNull() }
                ?: throw BadRequestResponse("Invalid opaque patient id.")

            // Load the patient demographics
            val patientDemographics = OpaquePatientIds.resolve(ctx, principal, opaquePatientId)
                ?: throw NotFoundResponse("Could not locate patient session, please search again.")

            // Load prescriptions from the stored response, so we have something to look at.
            val prescriptions =
                PrescriptionMetadataParser.parse(
                    XmlUtils.parse(
                        Thread.currentThread().contextClassLoader.getResourceAsStream(
                            "list-prescriptions.xml"
                        )!!
                    )
                )
            val dataModel = mapOf(
                // Mock patient id, we don't know how it will look yet.
                "patientId" to "FR-1234567890",
                "prescriptions" to prescriptions.map {
                    mapOf(
                        "effectiveTime" to it.l3?.effectiveTime?.format(prettyDateFormatter),
                        "authorName" to it.l3?.authorName,
                        "title" to it.l3?.title,
                        "description" to it.l3?.description,
                        // Dunno where these are supposed to come from yet
                        //"productCode" to it.l3?.productCode,
                        //"productName" to it.l3?.productName,
                        "atcCode" to it.l3?.atcCode,
                        "atcName" to it.l3?.atcName,
                        "doseFormCode" to it.l3?.doseFormCode,
                        "doseFormName" to it.l3?.doseFormName,
                        "strength" to it.l3?.strength,
                        // Dunno where these are supposed to come from yet
                        //"dispensable" to it.l3?.dispensable,
                        //"substitutionAllowed" to it.l3?.substitutionAllowed,
                        "l3DocumentId" to it.l3?.documentUniqueId,
                        "l1DocumentId" to it.l1?.documentUniqueId
                    )
                },
            )

            ctx.render("list-prescriptions.ftlh", dataModel)
        }
    }

    private val prettyDateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(Locale.of("da", "DK"))
}

