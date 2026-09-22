package dk.sundhedsdatastyrelsen.epportal.patient

import dk.sundhedsdatastyrelsen.epportal.logger
import java.time.OffsetDateTime

class DummyPatientSearchClient : PatientSearchClient {
    private val log = logger()

    override fun queryPatient(countryCode: String, ids: List<PatientId>): List<PatientDemographics> {
        log.info("Using DummyPatientSearchClient: returning fabricated patient data for country {}", countryCode)
        return listOf(
            PatientDemographics(
                givenName = "Test",
                familyName = "Testersen",
                birthDate = OffsetDateTime.parse("1980-01-01T00:00:00Z"),
                gender = "M",
                addressStreet = "Testvej 1",
                addressPostalCode = "1000",
                addressCity = "Testby",
                addressCountry = countryCode,
                phone = "+45 12 34 56 78",
                email = "test.testersen@example.org",
                patientIds = ids,
            ),
        )
    }
}
