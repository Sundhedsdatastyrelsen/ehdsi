# Updating from 8.0.0 to 8.2.3

Moved the `diff` commands to a shell script and updated the documentation.

## Code

The past version in Dockerfile was 8.2.1, but the configuration hasn't been updated to that, so I'm lifting all of it to 8.2.3. 

Updated Dockerfile openncp version and ran docker compose build. Error in web manager frontend.
Copied `ENV NODE_OPTIONS="--openssl-legacy-provider"` from ehealth's dockerfile (the only change), which fixed it.

Built succesfully.

Ran `docker compose up -d` and inspected logs.

Configuration utility still can't connect to the database. Was also a problem before.
Ignoring alloy-edge and node-exporter, no changes to them.
No errors in node_b or openatna.
node_a has this warning: `[2026-02-26T10:47:42.240Z] [main] trace_id= span_id= trace_flags= WARN  e.e.e.s.o.c.s.i.ServicesConfiguration.lambda$loadWithFallBack$6(203) - No bean of [PatientSearchInterface] found through the Service Loader mechanism, falling back to the default bean supplier.` and a few more with other bean names. Will check if it works.
No errors in translations and mappings, trc-sts.
Web-manager backend has an error `Config data resource 'file [/opt/config/application-docker.yml]' via location 'file:/opt/config/application-docker.yml' does not exist`. Might also have been an error in the old version, I didn't check that. Fixed by adding a `/` to a path in the Dockerfile.

Otherwise nothing. On to the configuration, then tests at the end, then look at release notes.

## Configuration

Going through the files we own, from the list in 00-updating.md.

No changes in most of the Dockerfile, I already included the change to the web-manager frontend.

### Owned files with relevant changes

docker-compose.yml.
TSAM-sync has introduced environment variables for url, password and username. We already have password and username as secrets, so ignoring those. Might need to look at the url.
node_a has added `-Dserver.ehealth.mode=PRODUCTION` to CATALINA_OPTS. We don't pass `CATALINA_OPTS` at all, so I don't know what to do about that. Will look at it.
node_b same as node_a. Logging level for node_b has also been reduced, we already have that, as we overwrite logback.xml.
Other than that, a few changes so that some of the services run on 10.0.0.1 in addition to 8.8.8.8 (wonder why), and some open debug ports.

openncp-configuration.properties.
A few lines added that I've just copied over, will check if the services still run like they should in the tests.

pn-oid.xml has been updated. Replaced ours with theirs.

### New files

A few in openncp-configuration/integration. I don't think we need them.

### Removed files

Some in the TM_resources folders. Ignoring, we copy that directory in the dockerfile.

openncp-configuration/hcer.properties. Removed.

### Patched files

Running the diff tool.

Turns out it's a little hard to diff a diff.

env_default/.env has a few TLS_PROVIDER values that OpenNCP added. Will try to run without them. Yeah we already have that.

OpenNCP has replaced hardcoded keystore paths with environment variables. We could use the same approach they do now, possibly. In several server.xml files.

Added three added properties to tm.properties (tm.audittrail.enabled, .transactionnumber and .targetip).

## Testing

Running `docker compose up -d --build` to check that everything still works.

Building goes fine. Checking logs. Only the same errors as when we updated the code.

Running test tool.

Test tool runs green.

## Release notes

Checking release notes for the versions inbetween.

Nothing indicates things breaking for us, so this update is concluded. We will see if dispensing from the portal works when we deploy.