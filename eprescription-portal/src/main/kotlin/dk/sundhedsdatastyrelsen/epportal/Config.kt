package dk.sundhedsdatastyrelsen.epportal

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addPathSource
import dk.sundhedsdatastyrelsen.epportal.app.FindPatient
import dk.sundhedsdatastyrelsen.epportal.app.WebApp
import dk.sundhedsdatastyrelsen.epportal.config.VaultSecretPreprocessor
import dk.sundhedsdatastyrelsen.epportal.openncp.OpenNcp
import java.nio.file.Path

data class Config(
    val webApp: WebApp.Config,
    val findPatient: FindPatient.Config,
    /** If absent, patient search uses fabricated data instead of calling OpenNCP. */
    val openncp: OpenNcp.Config? = null,
) {
    companion object {
        /**
         * @param localOverride optional, untracked file whose settings take precedence, for local setups such as
         *   the [openncp] integration against the NCP docker-compose stack.
         */
        fun load(
            filePath: Path = Path.of("config", "application.toml"),
            localOverride: Path? = Path.of("config", "local.toml"),
        ): Config =
            ConfigLoaderBuilder.default()
                .addPreprocessor(VaultSecretPreprocessor())
                .apply { if (localOverride != null) addPathSource(localOverride, optional = true) }
                .addPathSource(filePath)
                .build()
                .loadConfigOrThrow()
    }
}
