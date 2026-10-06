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

## CI/CD pipeline

Todo
