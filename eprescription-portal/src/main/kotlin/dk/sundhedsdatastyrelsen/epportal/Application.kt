package dk.sundhedsdatastyrelsen.epportal

import dk.sundhedsdatastyrelsen.epportal.app.WebApp

private const val PROFILE_ARG = "--profile="

/** Usage: `ep-portal [--profile=<name>]...`. See [Config] for what a profile is. */
fun main(args: Array<String>) {
    val profiles = args.map { arg ->
        require(arg.startsWith(PROFILE_ARG)) { "Unknown argument '$arg'. Usage: ep-portal [$PROFILE_ARG<name>]..." }
        arg.removePrefix(PROFILE_ARG)
    }
    log.info("Loading config with profiles: {}", profiles)
    val config = Config.load(profiles)
    log.info("Starting application with config: {}", config)
    WebApp.startServer(config)
}

val log = logger()
