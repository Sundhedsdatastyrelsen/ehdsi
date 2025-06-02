## Opsætningsguide - Frabedelsesservice
### Klon Git Repository

Start med at klone følgende Git repository:

```bash
git clone https://github.com/Sundhedsdatastyrelsen/FSEU
```

### Start Docker

Start Docker:

```bash
docker compose build
docker compose up -d
```

### Deploy af nye versioner
Når der skal lægges nye versioner af denne enhed, skal køres
```bash
docker compose build
docker compose up -d
```