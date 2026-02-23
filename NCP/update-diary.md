# Update diary

## Setting up 8.0.0 (the first docker version)

We have something that works, but it's unclear what configuration has been overwritten and what is copied, which configuration files we own and which configuration files we patch.

To improve that, we've talked in the team, and decided to group our configuration into these groups:

1. Files we overwrite and own completely (`logback.xml`, `docker-compose.yml`, `Dockerfile`, etc).
2. Files we change slightly (`tm.properties`, `tsam.properties`, `ArrConnections.template.xml`, etc).
3. Files we copy, but should still look at (the rest).

For 1., we check in our file and document which file(s) in OpenNCP they cover. When we upgrade, we manually check the files they cover.

For 2., we check in the changed file and a diff. We also document in the file why we change the things we change.

We could write a script that checks these files against the ehealth versions, so we know that they haven't changed, but we could also check them manually when we upgrade.

For 3., we document which files they are and copy them at build time into our images.

I'll list the different files below.

### 1 Owned files

- alloy/ (not from ehealth)
- common/logback.xml
- configuration-synchronizer/ (replaces openncp-docker/openncp-configuration-utility)
- docker-compose.yml
- Dockerfile
- mysql/initdb/90-grants.sql (not from ehealth, fixes some privileges in the database)
- ncp_a/entrypoint.sh (replaces openncp-docker/ncp_a/tomcat-config-entrypoint.sh)
- ncp_a/pom.xml (not from ehealth, injects our national connector in ncp_a)
- ncp_b/entrypoint.sh (replaces openncp-docker/ncp_b/tomcat-config-entrypoint.sh)
- openncp-configuration/pn-oid.xml (must be mounted, since it may need to be changed)
- openncp-national-connector/ (our implementation)
- openncp-openatna/entrypoint.sh (replaces openncp-docker/openncp-openatna/tomcat-config-entrypoint.sh)
- openncp-translations-and-mappings/entrypoint.sh (replaces openncp-docker/openncp-translations-and-mappings/tomcat-config-entrypoint.sh)
- openncp-trc-sts/entrypoint.sh (replaces openncp-docker/openncp-trc-sts/tomcat-config-entrypoint.sh)
- openncp-tsam-exporter/entrypoint.sh (replaces openncp-docker/openncp-tsam-exporter/tomcat-config-entrypoint.sh)
- openncp-web-manager/entrypoint.sh (replaces openncp-docker/openncp-web-manager/openncp-web-manager-backend/tomcat-config-entrypoint.sh)
- smp-files/ (these are files we upload to SMP, not actually used in openncp)
- test-tool/ (used to test that things are still working locally, written by us)
- openncp-configuration.properties (replaces openncp-docker/openncp-configuration-utility/openncp-configuration.properties, but should still be updated when they update)

### 2 Patched files

See the list of diff commands below for the complete list.

I added a readme to atna-resources to explain what's going on there.

The `env_default/.env` file is sourced from a few different places, see the file for details.

### 3 Copied files

See the list in `Dockerfile`, under `tomcat-base` for the ones copied to the base image.

Additionally, the `context.xml` file in several of the server configurations. This is also visible in the Dockerfile.

The `mysql/[01-05]*.sql` files are copied and not changed, but we need them locally to be able to start a mysql container.
There is also a configuration file in ehealth, but it's not loaded in their docker-compose.

`openncp-tsam-exporter/application.yml` is also copied.

`openncp-web-manager/openncp-web-manager-backend/application-docker.yml` is also copied.

### Commands I ran

Commented out commands were not necessary, but may still serve as documentation or help in future scripting approaches.

From a root where both ehdsi and ehealth repoes are available:

```bash
# atna
diff ehealth/openncp-docker/openncp-configuration/ATNA_resources/ArrConnections.xml ehdsi/NCP/atna-resources/ArrConnections.template.xml -u > ehdsi/NCP/atna-resources/ArrConnections.template.xml.diff
diff ehealth/openncp-docker/openncp-configuration/ATNA_resources/openatna.properties ehdsi/NCP/atna-resources/openatna.properties -u > ehdsi/NCP/atna-resources/openatna.properties.diff

# .env
diff ehealth/openncp-docker/.env ehdsi/NCP/env_default/.env -u > ehdsi/NCP/env_default/.env.diff

# mysql
# diff ehealth/openncp-docker/mysql/initdb/01-ehealth-properties.sql ehdsi/NCP/mysql/initdb/01-ehealth-properties.sql -u > ehdsi/NCP/mysql/initdb/01-ehealth-properties.sql.diff
# diff ehealth/openncp-docker/mysql/initdb/02-atna.sql ehdsi/NCP/mysql/initdb/02-atna.sql -u > ehdsi/NCP/mysql/initdb/02-atna.sql.diff
# diff ehealth/openncp-docker/mysql/initdb/03-ltrdb.sql ehdsi/NCP/mysql/initdb/03-ltrdb.sql -u > ehdsi/NCP/mysql/initdb/03-ltrdb.sql.diff
# diff ehealth/openncp-docker/mysql/initdb/04-eadc.sql ehdsi/NCP/mysql/initdb/04-eadc.sql -u > ehdsi/NCP/mysql/initdb/04-eadc.sql.diff
# diff ehealth/openncp-docker/mysql/initdb/05-populate-gateway-test-user.sql ehdsi/NCP/mysql/initdb/05-populate-gateway-test-user.sql -u > ehdsi/NCP/mysql/initdb/05-populate-gateway-test-user.sql.diff

# ncp_a
# diff ehealth/openncp-docker/ncp_a/config/context.xml ehdsi/NCP/ncp_a/config/context.xml -u > ehdsi/NCP/ncp_a/config/context.xml.diff
diff ehealth/openncp-docker/ncp_a/config/server.template.xml ehdsi/NCP/ncp_a/config/server.xml -u > ehdsi/NCP/ncp_a/config/server.xml.diff
diff ehealth/openncp-docker/ncp_a/ncpa.database.env ehdsi/NCP/ncp_a/ncpa.database.env -u > ehdsi/NCP/ncp_a/ncpa.database.env.diff
diff ehealth/openncp-docker/ncp_a/ncpa.env ehdsi/NCP/ncp_a/ncpa.env -u > ehdsi/NCP/ncp_a/ncpa.env.diff

# ncp_b
# diff ehealth/openncp-docker/ncp_b/config/context.xml ehdsi/NCP/ncp_b/config/context.xml -u > ehdsi/NCP/ncp_b/config/context.xml.diff
diff ehealth/openncp-docker/ncp_b/config/server.template.xml ehdsi/NCP/ncp_b/config/server.xml -u > ehdsi/NCP/ncp_b/config/server.xml.diff
diff ehealth/openncp-docker/ncp_b/ncpb.database.env ehdsi/NCP/ncp_b/ncpb.database.env -u > ehdsi/NCP/ncp_b/ncpb.database.env.diff
diff ehealth/openncp-docker/ncp_b/ncpb.env ehdsi/NCP/ncp_b/ncpb.env -u > ehdsi/NCP/ncp_b/ncpb.env.diff

# openncp-configuration
diff ehealth/openncp-docker/openncp-configuration/tm.properties ehdsi/NCP/openncp-configuration/tm.properties -u > ehdsi/NCP/openncp-configuration/tm.properties.diff
diff ehealth/openncp-docker/openncp-configuration/tsam.properties ehdsi/NCP/openncp-configuration/tsam.properties -u > ehdsi/NCP/openncp-configuration/tsam.properties.diff

# openncp-openatna
# diff ehealth/openncp-docker/openncp-openatna/config/context.xml ehdsi/NCP/openncp-openatna/config/context.xml -u > ehdsi/NCP/openncp-openatna/config/context.xml.diff
diff ehealth/openncp-docker/openncp-openatna/config/server.template.xml ehdsi/NCP/openncp-openatna/config/server.xml -u > ehdsi/NCP/openncp-openatna/config/server.xml.diff
diff ehealth/openncp-docker/openncp-openatna/openatna.database.env ehdsi/NCP/openncp-openatna/openatna.database.env -u > ehdsi/NCP/openncp-openatna/openatna.database.env.diff
diff ehealth/openncp-docker/openncp-openatna/openatna.env ehdsi/NCP/openncp-openatna/openatna.env -u > ehdsi/NCP/openncp-openatna/openatna.env.diff

# openncp-translations-and-mappings
# diff ehealth/openncp-docker/openncp-translations-and-mappings/config/context.xml ehdsi/NCP/openncp-translations-and-mappings/config/context.xml -u > ehdsi/NCP/openncp-translations-and-mappings/config/context.xml.diff
diff ehealth/openncp-docker/openncp-translations-and-mappings/config/server.template.xml ehdsi/NCP/openncp-translations-and-mappings/config/server.xml -u > ehdsi/NCP/openncp-translations-and-mappings/config/server.xml.diff
diff ehealth/openncp-docker/openncp-translations-and-mappings/translations-and-mappings.database.env ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.database.env -u > ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.database.env.diff
diff ehealth/openncp-docker/openncp-translations-and-mappings/translations-and-mappings.env ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.env -u > ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.env.diff

# openncp-trc-sts
# diff ehealth/openncp-docker/openncp-trc-sts/config/context.xml ehdsi/NCP/openncp-trc-sts/config/context.xml -u > ehdsi/NCP/openncp-trc-sts/config/context.xml.diff
diff ehealth/openncp-docker/openncp-trc-sts/config/server.template.xml ehdsi/NCP/openncp-trc-sts/config/server.xml -u > ehdsi/NCP/openncp-trc-sts/config/server.xml.diff
diff ehealth/openncp-docker/openncp-trc-sts/.database.env ehdsi/NCP/openncp-trc-sts/.database.env -u > ehdsi/NCP/openncp-trc-sts/.database.env.diff
diff ehealth/openncp-docker/openncp-trc-sts/.env ehdsi/NCP/openncp-trc-sts/.env -u > ehdsi/NCP/openncp-trc-sts/.env.diff

# openncp-tsam-exporter
# diff ehealth/openncp-docker/openncp-tsam-exporter/application.yml ehdsi/NCP/openncp-tsam-exporter/application.yml -u > ehdsi/NCP/openncp-tsam-exporter/application.yml.diff

# openncp-web-manager/openncp-web-manager-backend
# diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/config/context.xml ehdsi/NCP/openncp-web-manager/config/context.xml -u > ehdsi/NCP/openncp-web-manager/config/context.xml.diff
diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/config/server.template.xml ehdsi/NCP/openncp-web-manager/config/server.xml -u > ehdsi/NCP/openncp-web-manager/config/server.xml.diff
diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/.database.env ehdsi/NCP/openncp-web-manager/.database.env -u > ehdsi/NCP/openncp-web-manager/.database.env.diff
diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/.env ehdsi/NCP/openncp-web-manager/.env -u > ehdsi/NCP/openncp-web-manager/.env.diff
# diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/application-docker.yml ehdsi/NCP/openncp-web-manager/application-docker.yml -u > ehdsi/NCP/openncp-web-manager/application-docker.yml.diff

# tsam-synchronizer
diff ehealth/openncp-docker/openncp-tsam-sync/application.yaml ehdsi/NCP/tsam-synchronizer/application.yml -u > ehdsi/NCP/tsam-synchronizer/application.yml.diff

# openncp-configuration.properties
diff ehealth/openncp-docker/openncp-configuration-utility/openncp-configuration.properties ehdsi/NCP/openncp-configuration.properties -u > ehdsi/NCP/openncp-configuration.properties.diff
```