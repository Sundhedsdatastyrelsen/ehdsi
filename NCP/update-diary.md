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

- common/logback.xml
- docker-compose.yml
- Dockerfile

### 2 Patched files

- atna-resources/ArrConnections.template.xml (from `openncp-docker/openncp-configuration/ATNA_resources/ArrConnections.xml`. The story here is complex, see the file for details.)
- atna-resources/openatna.properties (from `openncp-docker/openncp-configuration/ATNA_resources/openatna.properties`)

### 3 Copied files

See the list in `Dockerfile`, don't want to write it twice.

### Commands I ran

From a root where both ehdsi and ehealth repoes are available:

```bash
diff ehealth/openncp-docker/openncp-configuration/ATNA_resources/ArrConnections.xml ehdsi/NCP/atna-resources/ArrConnections.template.xml -u > ehdsi/NCP/atna-resources/ArrConnections.template.xml.diff
diff ehealth/openncp-docker/openncp-configuration/ATNA_resources/openatna.properties ehdsi/NCP/atna-resources/openatna.properties -u > ehdsi/NCP/atna-resources/openatna.properties.diff
```