### Logs

For at hente logs for en specifik service, brug navnet på servicen fra `docker-compose.yml` filen. Kør følgende kommando i samme sti som `docker-compose.yml`:

```bash
docker compose logs [containernavn] -f
```

Flaget `-f` er til at følge loggene i realtid. Udelad flaget, hvis du kun vil se de seneste logs.
