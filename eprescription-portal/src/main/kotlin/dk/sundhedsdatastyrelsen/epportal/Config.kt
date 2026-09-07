package dk.sundhedsdatastyrelsen.epportal

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addPathSource
import dk.sundhedsdatastyrelsen.epportal.app.WebApp
import dk.sundhedsdatastyrelsen.epportal.config.VaultSecretPreprocessor
import java.nio.file.Path

data class Config(
    val webApp: WebApp.Config,
) {
    companion object {
        fun load(filePath: Path = Path.of("config", "application.toml")): Config =
            ConfigLoaderBuilder.default()
                .addPreprocessor(VaultSecretPreprocessor())
                .addPathSource(filePath)
                .build()
                .loadConfigOrThrow()
    }
}
