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

I'll list these files here:

### 1 Owned files

- alloy/ (not from ehealth)
- common/logback.xml
- configuration-synchronizer/ (replaces openncp-docker/openncp-configuration-utility)
- docker-compose.yml
- Dockerfile
- mysql/initdb/90-grants.sql (not from ehealth, fixes some privileges in the database)
- ncp_a/entrypoint.sh (replaces openncp-docker/ncp_a/tomcat-config-entrypoint.sh)
- ncp_a/pom.xml (not from ehealth, injects our national connector in ncp_a)

### 2 Patched files

See the list of diff commands below for the complete list.

I added a readme to atna-resources to explain what's going on there.

The `env_default/.env` file is sourced from a few different places, see the file for details.

### 3 Copied files

See the list in `Dockerfile`, under `tomcat-base` for the ones copied to the base image.

Additionally, the `context.xml` file in several of the server configurations. This is also visible in the Dockerfile.

The `mysql/[01-05]*.sql` files are copied and not changed, but we need them locally to be able to start a mysql container.
There is also a configuration file in ehealth, but it's not loaded in their docker-compose.

### Commands I ran

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
```