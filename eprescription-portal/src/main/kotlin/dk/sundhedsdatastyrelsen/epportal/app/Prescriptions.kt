package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.cdadisplay.CdaDisplayTransformer
import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionDocumentClient
import dk.sundhedsdatastyrelsen.epportal.prescriptions.PrescriptionMetadataParser
import dk.sundhedsdatastyrelsen.epportal.utils.XmlUtils
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.HttpStatus
import io.javalin.http.NotFoundResponse
import io.javalin.router.JavalinDefaultRoutingApi
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
        documents: PrescriptionDocumentClient,
        cdaDisplay: CdaDisplayTransformer,
    ) {
        val handlers = Handlers(auth, documents, cdaDisplay)
        routes.get("/prescriptions", handlers::list)
        routes.get("/prescriptions/{documentId}", handlers::show)
        routes.get("/prescriptions/{documentId}/display", handlers::display)
    }

    private class Handlers(
        private val auth: AuthProvider,
        private val documents: PrescriptionDocumentClient,
        private val cdaDisplay: CdaDisplayTransformer,
    ) {
        private val prettyDateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale.of("da", "DK"))

        fun list(ctx: Context) {
            val principal = auth.current(ctx)
            if (principal == null) {
                ctx.redirect("/?error=not-authorized", HttpStatus.SEE_OTHER)
                return
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
                "opaqueId" to opaquePatientId.toString(),
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
            // Content security policy: it may only use its own inline styles and images, so it can't run scripts or
            // load anything, and only the portal may frame it.
            ctx.header(
                "Content-Security-Policy",
                "default-src 'none'; style-src 'unsafe-inline'; img-src data:; frame-ancestors 'self'; form-action 'none'"
            )
            ctx.html(cdaDisplay.toHtml(cda))
        }
    }
}
