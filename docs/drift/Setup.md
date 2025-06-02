
## Opsætningsguide

Følg disse trin for at opsætte systemet:

### Klon Git Repository

Start med at klone følgende Git repository:

```bash
git clone https://github.com/Sundhedsdatastyrelsen/ehdsi
```

### Konfigurer NCP Mappen

1. Gå ind i `NCP` mappen.
2. Kopier alt indhold fra `env_default` mappen til roden af `NCP` mappen.
3. Indsæt de korrekte produktionsværdier i de relevante filer.
4. Placer de relevante keystores i `keystores` mappen.

### Log ind på Docker

Log ind på Docker gennem GitHub Container Registry:

```bash
docker login ghcr.io
```

### Start Docker NCP

Start Docker NCP:

```bash
docker compose pull
docker compose up -d
```

### Konfigurer Country-A-Service

1. Skift til `country-a-service` mappen.
2. Kopier `.env.defaults` filen til `.env` og udfyld den med de nødvendige værdier.

### Start Country-A-Service

Start Country-A-Service:

```bash
docker compose pull
docker compose up -d
```

### Vigtige Produktionsopsætninger

For at undgå at få for meget data ud i logs, skal følgende værdier være sat i `.env` filen for NCP:

```plaintext
automated.validation=false
WRITE_TEST_AUDITS=false
Server.ehealth.mode=PRODUCTION
```

### Deploy af nye versioner
Når der skal lægges nye versioner af denne enhed, skal køres
```bash
docker compose pull
docker compose up -d
```