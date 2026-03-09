# Updating from 8.2.1 to 9.1.0

## Scope

Upgrade OpenNCP to `9.1.0` and align our Dockerized deployment with upstream wave 9 changes.

## Changes

- **openncp-national-connector code updates (breaking API changes)**
  - Updated connector code to match 9.1.0 APIs:
    - `DispatchingService` -> `FhirDispatchingService`
    - assertion exception package moves
    - `XmlUtil` usage updates (`parseContent` -> `parse`)
    - related import/signature updates in `DenmarkDispatchingService`, `DocumentSearch`, `PatientSearch`, `DocumentSubmit`.

- **Spring integration changes**
  - Added `openncp-national-connector/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`.
  - Kept `ModuleConfiguration` as the bean source for NI implementations.
  - This fixes bean discovery in 9.1.0 (instead of falling back to OpenNCP default service beans).

- **JVM flag changes**
  - Increased heap for node A/B in `docker-compose.yml`:
    - `-Xms256m -Xmx512m` -> `-Xms512m -Xmx1G`
  - Done due to insufficient heap during tests.

- **Maven settings introduction**
  - Added `maven-settings.xml`.
  - Dockerfile build now uses `mvn -s /usr/src/app/maven-settings.xml ...`.
  - Replaces previous ad-hoc repository URL patching in upstream `pom.xml`.
  - Purpose: control repository/mirror behavior centrally and work around blocked/outdated endpoints.

- **Tomcat server.xml migration**
  - Updated service `server.xml` files to Tomcat 10 style.  Aligns with upstream openncp config.

- **Entrypoint certificate verification mapping**
  - Added `CLIENT_AUTH` -> `CERTIFICATE_VERIFICATION` mapping in entrypoints.  Aligns with upstream entrypoints and new tomcat server config.

- **Removed Oracle groupId workaround**
  - Removed Dockerfile `sed` workaround replacing `<groupId>com.oracle</groupId>`.
  - No longer required for this upgrade.

- **JVM version upgrade**
  - Migrated runtime/build from Java 11 to Java 17.

- **OpenATNA split repository**
  - OpenATNA is now built from its separate repository in a dedicated Docker build stage.
  - We check out a specific commit hash because there are no tags or branches except for master.
  - Follow-up from runtime testing:
    - `atna-resources/openatna.properties` needed adaptation for the standalone build on the new stack:
      `org.hibernate.dialect.MySQL5InnoDBDialect` -> `org.hibernate.dialect.MySQLDialect`,
      and DB credentials must use `DB_USER` / `DB_PWD`.
    - `openncp-openatna/config/server.xml` and `openncp-openatna/entrypoint.sh` also needed the Tomcat 10
      `SSLHostConfig` / `certificateVerification` migration, otherwise OpenATNA started without a working TLS listener on ATNA port `2862`.
