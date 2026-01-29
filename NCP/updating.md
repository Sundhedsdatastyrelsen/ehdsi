# Updating the openncp version

This isn't simple, it requires compiling a list of changes to the openncp repo at <https://code.europa.eu/ehdsi/ehealth> from the previous version to the version updating to, and then manually going through the changes to determine what should be reflected in this repo and what shouldn't.

The following list of things to be aware of is true at the time of writing (openncp 8.2.3), but might change. I suggest keeping an update diary, so we can go back and see how an update was done and why the decisions were made.

- `openncp-application/openncp-application-server/src/main/resources/logback.xml` maps to `./common/logback.xml`.
- `openncp-docker/.env` maps to `./env_defaults/.env`. Remember that this is just the default env, and is copied to the actual environment file (in NCP root), as well as modified in every deployment. These will need to be manually updated in each deployment.
- `openncp-docker/openncp-configuration-utility/openncp-configuration.properties` maps to `./openncp-configuration.properties`.
- `openncp-docker/openncp-configuration` maps to `./openncp-configuration`, except for `ATNA_resources` and `keystore` which map to `./atna-resources` and `./keystore`.

The rest comes from `openncp-docker`, I think.
