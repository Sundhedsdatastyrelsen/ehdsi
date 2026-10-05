package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.patient.PatientDemographics
import io.javalin.config.JavalinConfig
import io.javalin.http.Context
import org.slf4j.LoggerFactory
import java.util.UUID
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

/**
 * Functions to manage Javalin's app data, which enables data between calls.
 */
object AppData {
    private val log = LoggerFactory.getLogger(AppData::class.java)

    private data class PatientSession(val demographics: PatientDemographics, val creationTimestamp: Instant)

    private val patientIdMapKey = io.javalin.config.Key<MutableMap<UUID, PatientSession>>("opaque-patient-id-map")

    fun setup(config: JavalinConfig): JavalinConfig = config.apply {
        // App map to app data
        val map = mutableMapOf<UUID, PatientSession>()
        appData(patientIdMapKey, map)

        // Hook up cleanup job to server start and stop
        val executor = Executors.newSingleThreadScheduledExecutor()
        events.serverStarted {
            executor.scheduleAtFixedRate(
                {
                    try {
                        cleanupPatientIds(map, 1.days)
                        log.info("Cleaned up patient cache.")
                    } catch (e: Exception) {
                        log.warn("Failed to cleanup opaquePatientIds.", e)
                    }
                }, 0, 1, TimeUnit.DAYS
            )
        }
        events.serverStopping { executor.shutdown() }
    }

    fun setOpaquePatientId(ctx: Context, patientData: PatientDemographics): UUID {
        val uuid = UUID.randomUUID()
        ctx.appData(patientIdMapKey)[uuid] = PatientSession(patientData, Clock.System.now())
        return uuid
    }

    fun getPatientData(ctx: Context, opaqueId: UUID): PatientDemographics? {
        return ctx.appData(patientIdMapKey)[opaqueId]?.demographics
    }

    private fun cleanupPatientIds(map: MutableMap<UUID, PatientSession>, maxAge: Duration) {
        val cutoff = Clock.System.now() - maxAge
        map.forEach {
            if (it.value.creationTimestamp < cutoff) {
                map.remove(it.key)
            }
        }
    }
}
