# List of changes that should be made in NCP configuration for production

In the `NCP` folder, the following changes should be made for the production environment.

- `keystore` - Add the keystores for production here
- `openncp-web-manager` - Write another secret for the jwt in `application-docker.yml`.
- `.env`
    - `TLS_*` - change to prod key/truststores.
    - `NCP_SIG_*` - change to prod key/truststore.
    - `TSAM_SYNC_URL` - change to prod value.
    - `SML_DOMAIN` - change to prod value.
    - `SMP_ADMIN_URL` - change to prod value.
- `secrets files` - the .txt files in the root should have the production values.
- `docker-compose.yml`
    - Ensure no open debug ports.
    - Make sure -DopenATNA.properties.path points to the right config file, for all the containers that use it.
    - Add `-Dserver.ehealth.mode=PRODUCTION` to all the ncp servers.
    - Make sure `openncp-web-manager` has the environment variable `SPRING_PROFILES_ACTIVE` set to `production`.
      I don't think it's used for anything, but that's how OpenNCP sets it and maybe some library depends on it.
- `openncp-configuration.properties`
    - `automated.validation` - set to false.

## Other values

These are the other values that seem like they might be relevant for production that I've checked, and
why they don't matter for production.

- `openncp-configuration`
    - The folders here are copied directly from `ehealth/openncp-docker/openncp-configuration`, and should only be updated when
      updating OpenNCP version.
    - `tm.properties`
        - `tm.schema.validation.enabled=true` writes a _static_ warning message if schema doesn't validate, so no personal data.
          Could maybe be disabled, since we might not care about this validation in production.
          The file in question is `openncp-core/openncp-core-common/src/main/java/eu/europa/ec/sante/openncp/core/common/ihe/transformation/service/CDATransformationServiceImpl.java`.
- `openncp-openatna`
    - The logging configuration translates to:
      `127.0.0.1 - admin [25/Oct/2018:07:12:49 -0600] "GET /manager/html HTTP/1.1" 200 19930`, which contains no personal data.
- `docker-compose.yml`
    - Set obligations and evidence to dev/null? Nope, that doesn't work "it's not a directory."
- `openncp-configuration.properties`
    - `secman.sts.url` - this is the same in production.
    - `SC_SMP_CLIENT_PRIVATEKEY_PASSWORD` - It's not used, and neither is `SC_SMP_CLIENT_PRIVATEKEY_ALIAS`.
    - `ABUSE_SCHEDULER_ENABLE` - Shouldn't be enabled. It simply logs if there are too many requests from somewhere. Doesn't block anything.
      We should create a task that we do actually block if there are too many requests.
    - `ncp.email` - I found the correct one, and it's the same in dev.
    - `ncp.countries` - Only relevant for country-B.
    - `TEST_AUDITS_PATH` - set to `/dev/null`? Nope, it doesn't work.
