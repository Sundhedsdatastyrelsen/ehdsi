package dk.sundhedsdatastyrelsen.epportal

import dk.sundhedsdatastyrelsen.epportal.app.WebApp

fun main() {
    val config = Config.load()
    log.info("Starting application with config: {}", config)
    WebApp.startServer(config.webApp)
}

val log = logger()
