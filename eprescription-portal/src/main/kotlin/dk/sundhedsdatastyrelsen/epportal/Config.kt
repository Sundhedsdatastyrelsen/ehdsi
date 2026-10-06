package dk.sundhedsdatastyrelsen.epportal

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addPathSource
import dk.sundhedsdatastyrelsen.epportal.app.AuthConfig
import dk.sundhedsdatastyrelsen.epportal.app.FindPatient
import dk.sundhedsdatastyrelsen.epportal.app.WebApp
import dk.sundhedsdatastyrelsen.epportal.config.VaultSecretPreprocessor
import dk.sundhedsdatastyrelsen.epportal.openncp.OpenNcp
import java.nio.file.Path

/**
 * All application config. It is read from `config/` by default:
 *
 * - `application.toml` is the base config, used by deployments.
 * - `application-<profile>.toml` is an overlay for each profile given, e.g. `application-dev.toml` for `./gradlew run`.
 *   Its values take precedence over the base config.
 *
 * Env vars are referenced explicitly in the files, as `"${NAME}"` or `"${NAME:-default}"`.
 * Beware that a reference to an unset env var without a default is left as is.
 */
data class Config(
    val webApp: WebApp.Config,
    val auth: AuthConfig,
    val findPatient: FindPatient.Config,
    /** If absent, use dummy data */
    val openncp: OpenNcp.Config? = null,
) {
    companion object {
        /**
         * @param profiles overlays to load on top of `application.toml`. Each one takes precedence over the ones
         *   before it, so the last one wins. It is an error if the file for a profile does not exist.
         */
        fun load(profiles: List<String> = emptyList(), dir: Path = Path.of("config")): Config =
            ConfigLoaderBuilder.default()
                .addPreprocessor(VaultSecretPreprocessor())
                // Fail on keys that don't match any config field, so a misspelled key in an overlay isn't ignored
                .strict()
                // Hoplite takes each value from the first source that has it, so the last profile is added first
                .apply { profiles.asReversed().forEach { addPathSource(dir.resolve("application-$it.toml")) } }
                .addPathSource(dir.resolve("application.toml"))
                .build()
                .loadConfigOrThrow()
    }
}
