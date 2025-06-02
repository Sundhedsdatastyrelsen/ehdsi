### Debugging

#### Debugging af en Enkelt Container

For at debugge en enkelt container, tilføj følgende værdi under `environment` i `docker-compose.yml`:

```yaml
-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n
```

Åbn den valgte port under `ports` i `docker-compose.yml` og i firewallen. Du kan derefter forbinde til containeren med Remote JVM Debug i IntelliJ.

#### Container Debugging

For at debugge direkte inde i containeren, start en shell med:

```bash
docker exec -it [containernavn] /bin/bash
```

Erstat `/bin/bash` med `/bin/sh`, hvis bash ikke er installeret. Find containernavnet i `docker-compose.yml` eller ved at køre `docker ps`.

Herefter kan du køre kommandoer i containerens kontekst