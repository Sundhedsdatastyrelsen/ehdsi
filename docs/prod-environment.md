# List of changes that should be made in NCP configuration for production

In the `NCP` folder, the following changes should be made for the production environment.

- `keystore` - Add the keystores for production here
- `openncp-configuration`
    - `tm.properties` - Check whether we should set schema validation false.
- `openncp-openatna` - Check whether we need to do something about the cataline servlet container setup.
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
    - Set obligations and evidence to dev/null? Check if it works.
    - Make sure -DopenATNA.properties.path points to the right config file, for all the containers that use it.
- `openncp-configuration.properties`
    - `automated.validation` - set to false.
    - `TEST_AUDITS_PATH` - set to `/dev/null`? Check if it works.
    - `ncp.countries` - the list of countries we are country b for. Not relevant for country a.
    - `SC_SMP_CLIENT_PRIVATEKEY_PASSWORD` - Check if this is required.
    - `ABUSE_SCHEDULER_ENABLE` - Check if this should be enabled.
