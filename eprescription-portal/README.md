# ePrescription portal

This was copied from FSEU and some information or pages may be out of date.

The portal is a minimal proof of concept. It will be used to test that we have what we need to support the use cases in the backend, and to test with other EU countries. It may also be used as a base for whatever solution we decide on for the production-product.

## Quickstart

- `./gradlew run`

## Configuration

All config lives in `config/` and is loaded by `Config.load` (see `Config.kt`):

- `config/application.toml` is the base config. Deployments use it as is.
- `config/application-<profile>.toml` is an overlay that takes precedence over the base config. It is selected with
  the `--profile=<profile>` program argument, which can be repeated: later profiles take precedence over earlier
  ones. `./gradlew run` uses `--profile=dev`, which enables hot-reloading of templates and static files.

Values from the environment are referenced explicitly in the config files, as `"${NAME}"` or `"${NAME:-default}"`.
Application code reads config, not env vars or system properties. Unknown keys are an error, so a misspelled key
fails at startup.

## CSS pipeline

If you need to recompile the css, install npm and run `npm install`, then start the pipeline with `npm run tw`. Check in
the generated `style.css` file, so the project can be run without this process.

## Value sets for the CDA display tool

Prescriptions are rendered with the eHDSI CDA display tool (see `src/main/resources/cda-display-tool/README.md`). It
looks up labels and code translations in value set files, which are packed in
`src/main/resources/value-sets/value-sets.zip`. `VERSION` next to it says where they came from. They are a zip because
they are tens of MB of XML; `ValueSets` reads them from it, and parses each one once.

The value sets come from the central terminology service (CTS). The NCP syncs them into its database every night
(`tsam-synchronizer`), and the `tsam-exporter` job in the NCP's `docker-compose.yml` exports them as the files the
display tool reads. The portal has its own copy, so it doesn't share anything with the NCP at runtime. Update it when
the CTS publishes a new release of the value sets, and when new translations are available:

1. On an NCP host whose terminology is synced, export the value sets to the `tsam-exporter-data` volume:
   ```
   cd NCP && docker compose run --rm --no-deps tsam-exporter
   ```
2. Copy the export out of the volume (the volume name is prefixed with the compose project name):
   ```
   mkdir /tmp/tsam-export
   docker run --rm -v ncp_tsam-exporter-data:/src:ro -v /tmp/tsam-export:/out alpine sh -c 'cp /src/* /out/'
   ```
3. Pack the value sets the display tool uses into the portal's zip, saying where they came from:
   ```
   scripts/update-value-sets.sh /tmp/tsam-export "NCP <environment>, terminology synced <date>, exported <date>"
   ```
4. Run the tests, check that a prescription renders, and commit.

The script packs exactly the files the stylesheets reference, and fails if the export is missing any of them. The same
export gives the same zip, so an unchanged zip means unchanged value sets.

## CI/CD pipeline

Todo
