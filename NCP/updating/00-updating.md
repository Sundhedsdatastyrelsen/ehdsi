# Updating the openncp version

Release notes for each version are published here: <https://webgate.ec.europa.eu/fpfis/wikis/spaces/EHDSI/pages/2114489422/OpenNCP+Release+Notes>.

Please have a look in the diary files for historical context, lists of files to look out for, and scripts to help with the process.

## Code

Change the `OPENNCP_BRANCH` in the Dockerfile, and run `docker compose build`. See if anything fails and fix it.

## Configuration

Clone the ehealth repository `git clone https://code.europa.eu/ehdsi/ehealth` and compile a list of changes from the current version to the new version:

```sh
# make sure your working tree is clean (git stash if not)
git checkout <new version, eg. 8.2.3>
git reset --soft <old version, eg. 8.0.0>
```

The changes in the working directory will then be the all the changes from the past version to the new one. Use your favorite tool to navigate it.

Go through the changes to the `openncp-docker` directory, and see if anything needs to be reflected in our docker setup.

To regenerate the `.diff` files, run [diff.sh](./diff.sh) from a directory where both the ehdsi repo and the ehealth repo reside.

### Current setup

Currently, we categorize the openncp configuration into three types of files.

1. Those we own, where changes to openncp's files probably shouldn't be reflected, but we still need to review what they did.
2. Those we patch, where changes to openncp's files probably should be reflected, and we keep a .diff file of the changes we've done.
3. Those we copy, where we assume things will keep working.

#### Owned files

- alloy/ (not from ehealth)
- common/logback.xml (replaces openncp's logging)
- configuration-synchronizer/ (replaces openncp-docker/openncp-configuration-utility)
- docker-compose.yml (replaces openncp's docker-compose.yml)
- Dockerfile (replaces all openncp's dockerfiles)
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
- openncp-configuration.properties (replaces openncp-docker/openncp-configuration-utility/openncp-configuration.properties)

#### Patched files

See the list of diffs made in [./diff.sh](./diff.sh), be aware that `NCP/env_default/.env` is sources from a few different places, see the file for details.

#### Copied files

See the list in `Dockerfile`, under `tomcat-base` for the ones copied to the base image.

Additionally, the `context.xml` file in several of the server configurations. This is also visible in the Dockerfile.

The `mysql/[01-05]*.sql` files are copied and not changed, but we need them locally to be able to start a mysql container.
There is also a configuration file in ehealth, but it's not loaded in their docker-compose.

`openncp-tsam-exporter/application.yml` is also copied.

`openncp-web-manager/openncp-web-manager-backend/application-docker.yml` is also copied.