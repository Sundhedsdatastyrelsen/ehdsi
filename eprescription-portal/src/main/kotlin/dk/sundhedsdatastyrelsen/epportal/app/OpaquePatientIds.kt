package dk.sundhedsdatastyrelsen.epportal.app

import dk.sundhedsdatastyrelsen.epportal.patient.PatientDemographics
import io.javalin.config.JavalinConfig
import io.javalin.config.Key
import io.javalin.config.NoValueForKeyException
import io.javalin.http.Context
import org.slf4j.LoggerFactory
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

/**
 * Functions to manage opaque patient ids. These are used to remember which patient the pages
 * of the webapp show, without leaking pii in query parameters or elsewhere.
 *
 * Uses Javalin's AppData as storage, and is lost on server restart, where the flows will have
 * to be restarted.
 *
 * Needs to be threadsafe, since it's the same storage for all requests.
 */
object OpaquePatientIds {
    private val log = LoggerFactory.getLogger(OpaquePatientIds::class.java)

    internal data class PatientSession(
        val owner: Principal,
        val demographics: PatientDemographics,
        val creationTimestamp: Instant,
    )

    internal val patientIdMapKey = Key<ConcurrentMap<UUID, PatientSession>>("opaque-patient-id-map")

    fun setup(config: JavalinConfig): JavalinConfig = config.apply {
        val map = ConcurrentHashMap<UUID, PatientSession>()
        appData(patientIdMapKey, map)

        // Hook up cleanup job to server start and stop
        val executor = Executors.newSingleThreadScheduledExecutor()
        events.serverStarted {
            log.info("Scheduling patient cache cleanup job.")
            executor.scheduleAtFixedRate(
                {
                    try {
                        log.info("Cleaning up patient cache.")
                        cleanup(map, 1.days, Clock.System)
                        log.info("Cleaned up patient cache.")
                    } catch (e: Exception) {
                        log.warn("Failed to cleanup opaquePatientIds.", e)
                    }
                }, 0, 1, TimeUnit.DAYS
            )
        }
        events.serverStopping {
            log.info("Stopping patient cache cleanup job.")
            executor.shutdown()
        }
    }

    fun issue(
        ctx: Context,
        owner: Principal,
        patientData: PatientDemographics,
        clock: Clock
    ): UUID {
        val uuid = UUID.randomUUID()
        ctx.appData(patientIdMapKey)[uuid] = PatientSession(owner, patientData, clock.now())
        return uuid
    }

    /** Only the user who did the search can look up the patient. */
    fun resolve(ctx: Context, owner: Principal, opaqueId: UUID): PatientDemographics? =
        ctx.appData(patientIdMapKey)[opaqueId]?.takeIf { it.owner == owner }?.demographics

    internal fun cleanup(
        map: ConcurrentMap<UUID, PatientSession>,
        maxAge: Duration,
        clock: Clock,
    ) {
        val cutoff = clock.now() - maxAge
        map.entries.removeIf { it.value.creationTimestamp < cutoff }
    }
}
