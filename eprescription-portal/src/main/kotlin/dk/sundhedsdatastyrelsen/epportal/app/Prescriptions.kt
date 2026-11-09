package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.cdadisplay.CdaDisplayTransformer
import dk.sundhedsdatastyrelsen.epportal.log
import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionDocumentClient
import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionListClient
import dk.sundhedsdatastyrelsen.epportal.utils.PublicException
import io.javalin.http.Context
import io.javalin.http.HttpStatus
import io.javalin.http.NotFoundResponse
import io.javalin.router.JavalinDefaultRoutingApi
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import java.util.UUID

object Prescriptions {
    /**
     * Registers the list of a patient's prescriptions, for a patient found with [FindPatient], and the page that shows
     * a prescription.
     *
     * The prescription is rendered by the CDA display tool in an iframe, which keeps the display tool's stylesheet
     * apart from the portal's and lets the browser sandbox it.
     */
    fun registerRoutes(
        routes: JavalinDefaultRoutingApi,
        auth: AuthProvider,
        prescriptionList: PrescriptionListClient,
        documents: PrescriptionDocumentClient,
        cdaDisplay: CdaDisplayTransformer,
    ) {
        val handlers = Handlers(auth, prescriptionList, documents, cdaDisplay)
        routes.get("/prescriptions", handlers::list)
        routes.get("/prescriptions/{documentId}", handlers::show)
        routes.get("/prescriptions/{documentId}/display", handlers::display)
    }

    private class Handlers(
        private val auth: AuthProvider,
        private val prescriptionList: PrescriptionListClient,
        private val documents: PrescriptionDocumentClient,
        private val cdaDisplay: CdaDisplayTransformer,
    ) {
        private val utcDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT

        private val prettyDateFormatter: DateTimeFormatter =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.of("da", "DK"))

        fun list(ctx: Context) {
            val principal = auth.current(ctx)
            if (principal == null) {
                ctx.redirect("/?error=not-authorized", HttpStatus.SEE_OTHER)
                return
            }

            try {
                // Get the patient ID query parameter
                val opaquePatientId =
                    ctx.queryParam("opaque-id")?.let { runCatching { UUID.fromString(it) }.getOrNull() }
                        ?: throw PublicException("Invalid opaque patient id.")

                val patientDemographics = OpaquePatientIds.resolve(ctx, principal, opaquePatientId)
                    ?: throw PublicException("Could not locate patient session, please search again.")

                // TODO what if there are more ids?
                val patientId = patientDemographics.patientIds.first()

                val prescriptions = prescriptionList.listDocuments(patientDemographics.countryCode, patientId)
                val dataModel = mapOf(
                    "patientId" to patientId.extension,
                    "opaqueId" to opaquePatientId.toString(),
                    "prescriptions" to prescriptions.map {
                        mapOf(
                            "effectiveTimeUtc" to it.l3.effectiveTime?.atZone(ZoneId.of("UTC"))
                                ?.format(utcDateTimeFormatter),
                            "prettyDate" to it.l3.effectiveTime?.atZone(ZoneId.of("UTC"))?.format(prettyDateFormatter),
                            "authorName" to it.l3.authorName,
                            "title" to it.l3.title,
                            // Dunno where this is supposed to come from yet, or if it's even relevant.
                            // I took it from the metadata we send in national-connector.
                            //"productCode" to it.l3?.productCode,
                            // Description is the product name, even though it doesn't sound like it.
                            "productName" to it.l3.description,
                            "atcCode" to it.l3.atcCode,
                            "atcName" to it.l3.atcName,
                            "doseFormCode" to it.l3.doseFormCode,
                            "doseFormName" to it.l3.doseFormName,
                            "strength" to it.l3.strength,
                            "substitution" to it.l3.substitution?.let(::substitutionLabel),
                            "dispensable" to it.l3.dispensable,
                            "l3DocumentId" to it.l3.documentUniqueId,
                            "l1DocumentId" to it.l1.documentUniqueId
                        )
                    },
                )

                ctx.render("list-prescriptions.ftlh", dataModel)
            } catch (e: Exception) {
                if (e is PublicException) {
                    log.warn("Public error while listing prescriptions", e)
                } else {
                    log.error("Unexpected error while listing prescriptions", e)
                }
                ctx.render("error-page.ftlh", mapOf("error" to if (e is PublicException) e.message else null))
            }

        }

        /** Danish label for an HL7 ActSubstanceAdminSubstitutionCode */
        // TODO: Check which substitution codes eHDSI actually uses.
        private fun substitutionLabel(code: String): String = when (code) {
            "N" -> "Nej"
            "G" -> "Ja, generisk"
            else -> code
        }

        /** Full page with the portal's layout, framing [display]. */
        fun show(ctx: Context) {
            val principal = auth.current(ctx)
            if (principal == null) {
                ctx.redirect("/?error=not-authorized", HttpStatus.SEE_OTHER)
                return
            }
            ctx.render(
                "show-prescription.ftlh",
                mapOf("documentId" to ctx.pathParam("documentId"), "opaqueId" to ctx.queryParam("opaque-id")),
            )
        }

        /** The prescription rendered by the CDA display tool, a complete HTML document for the iframe. */
        fun display(ctx: Context) {
            auth.require(ctx)
            val cda = documents.retrieveDocument(ctx.pathParam("documentId"))
                ?: throw NotFoundResponse("Prescription not found")
            // Content security policy: tell the browser not to allow any network requests from the rendered html, and
            // only to display the html on the same origin as the rest of the portal.
            ctx.header(
                "Content-Security-Policy",
                "default-src 'none'; style-src 'unsafe-inline'; img-src data:; frame-ancestors 'self'; form-action 'none'"
            )
            ctx.html(cdaDisplay.toHtml(cda))
        }
    }
}
