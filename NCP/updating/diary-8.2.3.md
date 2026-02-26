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