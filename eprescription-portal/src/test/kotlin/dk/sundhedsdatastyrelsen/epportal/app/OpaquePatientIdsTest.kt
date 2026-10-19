package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.app.OpaquePatientIds.PatientSession
import dk.sundhedsdatastyrelsen.epportal.patient.PatientDemographics
import io.javalin.http.Context
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

class OpaquePatientIdsTest {

    private class FakeClock(var time: Instant) : Clock {
        override fun now(): Instant = time
    }

    private val now = Instant.parse("2026-01-02T12:00:00Z")
    private val clock = FakeClock(now)

    private fun session(creationTimestamp: Instant) = PatientSession(
        owner = Principal("0101011234"),
        demographics = PatientDemographics(
            givenName = null,
            familyName = null,
            birthDate = null,
            gender = null,
            addressStreet = null,
            addressPostalCode = null,
            addressCity = null,
            addressCountry = null,
            phone = null,
            email = null,
            patientIds = emptyList(),
        ),
        creationTimestamp = creationTimestamp,
    )

    @Test
    fun `removes sessions older than max age and keeps newer ones`() {
        val old = UUID.randomUUID()
        val fresh = UUID.randomUUID()
        val map = ConcurrentHashMap(
            mapOf(
                old to session(now - 2.days),
                fresh to session(now - 1.seconds),
            )
        )

        OpaquePatientIds.cleanup(map, 1.days, clock)

        assertEquals(setOf(fresh), map.keys)
    }

    @Test
    fun `keeps a session created exactly at the cutoff`() {
        val atCutoff = UUID.randomUUID()
        val justBefore = UUID.randomUUID()
        val map = ConcurrentHashMap(
            mapOf(
                atCutoff to session(now - 1.days),
                justBefore to session(now - 1.days - 1.seconds),
            )
        )

        OpaquePatientIds.cleanup(map, 1.days, clock)

        assertEquals(setOf(atCutoff), map.keys)
    }

    @Test
    fun `sessions expire as the clock advances`() {
        val id = UUID.randomUUID()
        val map = ConcurrentHashMap(mapOf(id to session(now)))

        clock.time = now + 1.days
        OpaquePatientIds.cleanup(map, 1.days, clock)
        assertEquals(setOf(id), map.keys)

        clock.time = now + 1.days + 1.seconds
        OpaquePatientIds.cleanup(map, 1.days, clock)
        assertEquals(emptySet(), map.keys)
    }
}
