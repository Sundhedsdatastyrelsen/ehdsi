# Backup and Recovery Plan — NCPeH at Sundhedsdatastyrelsen

An operational backup and recovery plan for the NCPeH (National Contact Point eHealth)
installation run by Sundhedsdatastyrelsen (SDS).

---

## Recovery Objectives

| Objective | Target |
|-----------|--------|
| **RPO** (Recovery Point Objective) | 24 hours |
| **RTO** (Recovery Time Objective) | 24 hours |

In practice, MySQL binary log backups run every 15 minutes, so point-in-time recovery can
typically reach within 15 minutes of the incident. The 24-hour figures are the contractual
maximums, not the expected outcome for a well-executed recovery.

---

## System Overview

The production installation spans five servers:

| Server | Role |
|--------|------|
| `sds-openncp-01p` | OpenNCP (NCP-A / NCP-B), MySQL, job queue, undo log |
| `sds-ncpdata-01p` | National connector, FSEU SQLite data |
| `sds-openncp-01t` | OpenNCP — test environment |
| `sds-ncpdata-01t` | National connector — test environment |
| `sds-openncp-mon` | Grafana, Loki, Mimir, Tempo (monitoring stack) |

All persistent state lives in Docker containers managed from the `NCP/` and
`national-connector/` directories of this repository.

---

## Backup Layers

Backups operate in three independent layers. A full recovery requires all three to be
intact; when layers are intact, recovery is purely procedural.

### Layer 1 — VM-Level Disk Snapshots

**Managed by:** SIT.  
**Mechanism:** nightly full disk snapshots of each VM.  
**Retention:** 60 days.

This layer protects against catastrophic OS- or hardware-level failure. It requires no
action from the development team — SIT maintains it automatically.

**To restore from a VM snapshot:** contact SIT with the server name
and the target snapshot date. This restores the entire disk, including all running
containers and application data.

### Layer 2 — Offsite Application Backups (SIT Net-Backup)

**Managed by:** SIT net-backup agent on each server.  
**Mechanism:** SIT runs an agent that periodically copies the `/opt/backup` directory on
each server to an offsite backup system.

| Server | Directory | Frequency | Contents |
|--------|-----------|-----------|----------|
| `sds-openncp-01p` | `/opt/backup` | Every 30 min | MySQL dumps + binlogs, SQLite snapshots, undo data, job queue |
| `sds-ncpdata-01p` | `/opt/backup` | Every 30 min | FSEU SQLite data |
| `sds-openncp-01t` | `/opt/backup` | Every 24h | Test MySQL + SQLite data |
| `sds-ncpdata-01t` | `/opt/backup` | Every 24h | Test SQLite data |
| `sds-openncp-mon` | `/opt/backup` | Every 30 min | Grafana dashboards, metrics, log exports |

**To restore application backups from offsite:** contact SIT/the hosting provider and
specify the server name and timestamp you need. SIT will restore `/opt/backup` (or a
subdirectory) from their archive to the server. Once the files are back on-disk, follow
the restore procedures in [Recovery Procedures](#recovery-procedures) below.

### Layer 3 — On-Server Application Backups

**Managed by:** scripts in `backups/` of this repository, running under systemd timers.  
**Output:** files written to `/opt/backup` on each server (subsequently picked up by Layer 2).

This layer produces the logical backups that the restore scripts consume. It covers:

- **MySQL** — weekly full `mysqldump` plus binary logs for point-in-time recovery (PITR).
- **SQLite** — periodic online snapshots of each server's SQLite databases.
- **Vault** — full file-level backup of the Vault data directory.
- **Secrets** — backed up separately (see [Secrets Management](#secrets-management)).

---

## Components Backed Up

### MySQL (`openncp_db` container)

The OpenNCP MySQL instance holds four databases:

| Database | Contents |
|----------|----------|
| `ehealth_properties` | OpenNCP runtime configuration and gateway properties |
| `ehealth_atna` | OpenATNA audit log records (EADC) |
| `ehealth_ltrdb` | Local terminology reference data |
| `ehealth_eadc` | EADC (European Audit Data Consumption) records |

**Strategy:** a weekly full `mysqldump` combined with binary logs capturing every write
since the last full backup. This combination enables both a simple full restore and
point-in-time recovery to any moment within the binlog retention window.

Binary logging is configured in `NCP/mysql/my.cnf`:

```ini
server-id = 1
log-bin = binlog
binlog-format = ROW
binlog-expire-logs-seconds = 1209600   # 14 days
```

**Retention:**

| Artifact | Retention |
|----------|-----------|
| Full dumps (`/opt/backup/mysql/full/`) | 7 most recent |
| Binary logs (`/opt/backup/mysql/binlog/`) | 14 days |

### SQLite Databases

SQLite databases are spread across two servers.

**`sds-openncp-01p`** — three files in `national-connector/data/` (managed by the scripts in `backups/`):

| File | Contents |
|------|----------|
| `undo-db.sqlite` | Undo log for reversible prescription operations |
| `local-lms-db.sqlite` | Local LMS (medication) database cache |
| `job-queue.sqlite` | Persistent job queue for background processing |

**`sds-ncpdata-01p`** — the Frabedelsesservice EU (FSEU) database. This server runs the national connector for cross-border prescription data. Its SQLite files follow the same backup approach but are configured separately in the `backups/lib/config.sh` on that server.

**Strategy:** online snapshots using `sqlite3 .backup`, which produces a consistent copy
without stopping the container. Every snapshot is integrity-checked immediately after
creation.

**Retention:** 7 most recent snapshots in `/opt/backup/sqlite/`.

**Limitation:** SQLite has no binary log equivalent. Recovery is always to a snapshot
boundary — there is no point-in-time recovery between snapshots. Snapshot frequency
should be set according to how much data loss between snapshots is acceptable.

### Vault Server

The Vault server manages encrypted secrets for the entire installation. Its data
directory is backed up as a complete set of files to `/opt/backup` and picked up by the
SIT net-backup agent alongside the other application backups.

The **Vault unseal key** is not stored digitally. It is printed and held physically in a
secure location (safe). Without it, a restored Vault instance will start in sealed state
and cannot serve secrets to any dependent service. See
[Scenario 2](#scenario-2--vault-server-recovery) for restore instructions.

### Secrets

Secrets are not stored in `/opt/backup` because they are sensitive. They are handled
separately — see [Secrets Management](#secrets-management).

---

## Running Backups

### Scheduled Backups (systemd timers)

The recommended production setup is three systemd timer pairs installed by
`backups/systemd/install.sh`. The default schedule:

| Timer | Default schedule | Script |
|-------|-----------------|--------|
| `ehdsi-mysql-full-backup` | Daily at 03:00 | `backups/mysql/full-backup.sh` |
| `ehdsi-mysql-binlog-backup` | Every 15 minutes | `backups/mysql/binlog-backup.sh` |
| `ehdsi-sqlite-backup` | Every 6 hours | `backups/sqlite/backup.sh` |

**Install:**

```bash
sudo ./backups/systemd/install.sh install
```

**Check status:**

```bash
./backups/systemd/install.sh status
systemctl list-timers 'ehdsi-*'
journalctl -u ehdsi-mysql-full-backup.service
```

**Adjust a schedule without reinstalling** (creates a systemd drop-in):

```bash
sudo systemctl edit ehdsi-mysql-binlog-backup.timer
```

**Uninstall:**

```bash
sudo ./backups/systemd/install.sh uninstall
```

The service user needs:
- membership of the `docker` group
- write access to `/opt/backup`
- read access to `NCP/db_root_password.txt` (or `MYSQL_ROOT_PASSWORD` set in a drop-in)

### Manual Backups

```bash
# MySQL — full dump
./backups/mysql/full-backup.sh

# MySQL — copy rotated binary logs
./backups/mysql/binlog-backup.sh

# SQLite — snapshot all three databases
./backups/sqlite/backup.sh
```

Each script logs timestamped output to stderr. Run them from the repo root or set
`REPO_ROOT` explicitly.

---

## Recovery Procedures

### Before Starting Any Recovery

1. Identify **when** the incident occurred (from logs, monitoring, or user reports).
2. Confirm that the required backup files are present on-disk under `/opt/backup`. If
   not, request them from SIT (Layer 2 offsite restore).
3. Record the recovery start time. If the incident requires audit documentation, preserve
   the current state (logs, `docker ps`, disk listing) before overwriting anything.
4. Confirm that the relevant Docker container is running before attempting a restore into
   it. Stop the container only when the restore script requires it (SQLite restore does;
   MySQL restore does not).

---

### Common Problems on Restore

The steps below are easy to miss and will prevent services from communicating with each
other even when all data has been correctly restored.

#### Manual verification before enabling automatic secret fetching

There are two separate mechanisms for secrets:

1. A **manual fetch script** that pulls secrets from Vault and writes the local files
   (`NCP/*.txt`, `national-connector/secrets/`). Run this after unsealing Vault to
   populate secrets for the first time.
2. An **automatic fetch script** that runs on a schedule and keeps those files current.
   This is a separate script — do not enable it until you have verified manually that
   services are working correctly end-to-end.

The recommended order:

1. Unseal Vault and confirm it is serving secrets by manually querying at least one path:
   ```bash
   docker exec -it vault vault kv get <secret-path>
   ```
2. Run the manual fetch script to write local secret files.
3. Start services one at a time (MySQL first, then OpenNCP, then the national connector)
   and manually verify each connection before starting the next.
4. Only once the full stack is confirmed healthy should the automatic fetch script be
   enabled (e.g. via its cron job or systemd timer). Running it on a broken setup can
   overwrite partially-correct files with stale or malformed values, making diagnosis
   harder.

#### Certificate trust store mismatches

Several internal connections use self-signed certificates that must be manually imported
into the receiving service's trust store. These entries are not recreated automatically
on restore — if the trust store file is restored from backup but the certificate has been
rotated since, or if the trust store is missing entirely, the connection will be refused
with a TLS handshake error.

**NC→OpenNCP TLS** (OpenNCP must trust the national connector's certificate)

The national connector presents a self-signed certificate (`NC_NCP_CERTIFICATE`) for its
HTTPS endpoint. OpenNCP verifies this against its trust store
(`NCP/keystore/<env>-truststore.jks`). After a restore, confirm the certificate in the
trust store matches the one currently in `national-connector/secrets/NC_NCP_CERTIFICATE`:

```bash
# Inspect what is currently trusted
keytool -list -keystore NCP/keystore/<env>-truststore.jks -storepass changeit -alias national-connector

# If the alias is missing or the certificate fingerprint does not match NC_NCP_CERTIFICATE,
# remove the old entry and import the current one:
keytool -keystore NCP/keystore/<env>-truststore.jks -storepass changeit \
    -delete -alias national-connector 2>/dev/null || true
keytool -keystore NCP/keystore/<env>-truststore.jks -storepass changeit \
    -import -file national-connector/secrets/NC_NCP_CERTIFICATE \
    -alias national-connector -noprompt
```

If `keytool` is not available on the server, download the trust store, make the change
locally, and re-upload it. Then restart the OpenNCP containers:

```bash
cd NCP && docker compose up -d tomcat_node_a tomcat_node_b
```

**NC↔Opt-Out mTLS** (mutual TLS on both sides)

The national connector and the opt-out service use mutual TLS. Each side holds the
other's certificate in its own trust store. After a restore, both sides may need the
certificate re-imported:

- National connector trust store: `national-connector/config/<env>-opt-out-truststore.p12`
- Opt-out service trust store: its equivalent config on the opt-out server

If the opt-out service is on a separate host, the restore steps above must be carried out
on both servers before the mTLS connection works.

To verify the connection manually before starting full service:

```bash
# From the national connector server, test that the opt-out endpoint responds over mTLS
curl --cert national-connector/config/<env>-opt-out-keystore.p12 \
     --cacert national-connector/config/<env>-opt-out-truststore.p12 \
     https://<opt-out-host>:<port>/actuator/health
```

#### Verifying inter-service connectivity manually

Before declaring the restore complete, test each connection explicitly rather than
relying on container health checks:

```bash
# OpenNCP gateway is reachable
curl -k https://localhost:8443/openncp-gateway-backend/

# National connector health
curl -k https://localhost:4443/actuator/health

# MySQL is accepting connections and databases are intact
docker exec openncp_db mysql -u root \
    -p"$(tr -d '[:space:]' < NCP/db_root_password.txt)" \
    -e "SHOW DATABASES;"

# SQLite files pass integrity checks
for db in national-connector/data/*.sqlite; do
    echo -n "$db: "; sqlite3 "$db" "PRAGMA integrity_check;"
done
```

Only once all four checks pass should the automatic secret-fetch script be enabled.

---

### Scenario 1 — Full Server Loss (Restore from Scratch)

Use this procedure when a server is lost and must be rebuilt from nothing, or when
provisioning a replacement server.

Production uses pre-built Docker images published to the GitHub Container Registry
(`ghcr.io/sundhedsdatastyrelsen/ehdsi/`) by the CI workflows in `.github/workflows/`.
**No build step is required on the server itself** — all images are pulled at deploy time.

#### Step 1 — Prerequisites

Install on the new server:

```bash
apt install docker.io docker-compose-plugin sqlite3 git
```

Ensure the deploying user is in the `docker` group:

```bash
usermod -aG docker <username>
```

Create the backup output directory:

```bash
mkdir -p /opt/backup
chown <username>:<username> /opt/backup
```

#### Step 2 — Clone the Repository

```bash
git clone https://github.com/Sundhedsdatastyrelsen/ehdsi.git /var/ehdsi
cd /var/ehdsi
```

#### Step 3 — Obtain Application Backups from SIT

Contact SIT to restore the offsite backup to `/opt/backup` on the new server. Provide:

- The source server name (`sds-openncp-01p` or `sds-ncpdata-01p`).
- The most recent timestamp you need (ideally the last snapshot before the incident).

SIT will restore the contents of `/opt/backup` from their archive.

#### Step 4 — Restore Vault and Fetch Secrets

Secrets for all services are stored in Vault. Vault must be running and unsealed before
any other service can start.

**4a.** Restore the Vault data directory from SIT (or from the Vault server's
`/opt/backup` if it is still accessible).

**4b.** Start the Vault container — it will be in sealed state:

```bash
docker compose up -d vault
```

**4c.** Unseal Vault using the physical printed key:

```bash
docker exec -it vault vault operator unseal
# Enter the unseal key when prompted
```

Vault will log that it is unsealed when ready. Confirm:

```bash
docker exec vault vault status   # Sealed: false
```

**4d.** Run the secrets-fetching scripts to populate the local secret files that the
other services mount at startup (e.g. `NCP/*.txt`, `national-connector/secrets/`).

#### Step 5 — Configure the Production docker-compose Files

The `docker-compose.yml` files in the repository are used directly in production but
require production-specific values. Follow `docs/prod-environment.md` in full. Key items:

- `NCP/.env` — set production `IMAGE_TAG` (e.g. `latest` or a specific `build-<N>`),
  production TLS/SMP/TSAM URLs, and `COMPOSE_PROFILES` appropriate for the server.
- `NCP/keystore/` — place production keystores here.
- `NCP/openncp-configuration.properties` — set `automated.validation=false`.
- `NCP/docker-compose.yml` — confirm no open debug ports, and that all NCP services
  include `-Dserver.ehealth.mode=PRODUCTION` and `SPRING_PROFILES_ACTIVE=production`.
- `national-connector/docker-compose.yml` — confirm image tag and that no debug ports
  are exposed.

#### Step 6 — Authenticate with the Container Registry

```bash
echo "<TOKEN>" | docker login ghcr.io -u <github-username> --password-stdin
```

#### Step 7 — Pull Images and Start MySQL

```bash
cd /var/ehdsi/NCP
docker compose pull

# Start only the database first so data is restored before the app servers connect
docker compose up -d mysql
```

Wait until MySQL is accepting connections:

```bash
docker exec openncp_db mysqladmin ping -u root \
    -p"$(tr -d '[:space:]' < db_root_password.txt)" --silent
```

#### Step 8 — Restore MySQL

```bash
cd /var/ehdsi

# Full restore (latest dump)
./backups/mysql/restore-full.sh --yes

# Replay binlogs up to the point just before the incident
LATEST_DUMP=$(ls -t /opt/backup/mysql/full/*.sql.gz | head -1)
./backups/mysql/restore-incremental.sh \
    --after "$LATEST_DUMP" \
    --stop-datetime '2026-MM-DD HH:MM:SS'
```

If binlog files are unavailable (purged or lost), skip `restore-incremental.sh` and
accept the full dump boundary as the recovery point.

#### Step 9 — Start Remaining NCP Services

```bash
cd /var/ehdsi/NCP
docker compose up -d
```

#### Step 10 — Restore SQLite and Start the National Connector

On `sds-ncpdata-01p` (or the server hosting the national connector):

```bash
cd /var/ehdsi

# Restore the latest SQLite snapshot (stops/starts the container automatically)
./backups/sqlite/restore.sh --yes

# Pull images and start the national connector
cd national-connector
docker compose pull
docker compose up -d
```

#### Step 11 — Install Backup Schedules

```bash
cd /var/ehdsi
sudo ./backups/systemd/install.sh install
./backups/systemd/install.sh status
```

Also set up the TSAM synchronizer cron job (see `NCP/README.md`):

```bash
crontab -e
# Add: 0 3 * * * cd /var/ehdsi/NCP && /usr/bin/docker compose run tsam-synchronizer
```

#### Step 12 — Verify

```bash
# MySQL table counts
docker exec openncp_db mysql -u root \
    -p"$(tr -d '[:space:]' < NCP/db_root_password.txt)" \
    -e "SELECT TABLE_SCHEMA, COUNT(*) FROM information_schema.TABLES \
        WHERE TABLE_SCHEMA LIKE 'ehealth_%' GROUP BY TABLE_SCHEMA;"

# SQLite integrity
for db in national-connector/data/*.sqlite; do
    echo -n "$db: "
    sqlite3 "$db" "PRAGMA integrity_check;"
done

# All containers running
docker ps --format 'table {{.Names}}\t{{.Status}}'

# Timer status
systemctl list-timers 'ehdsi-*'
```

---

### Scenario 2 — Vault Server Recovery

Use this procedure when only the Vault server is lost or its data directory is corrupted,
while the other servers remain intact.

**Prerequisites:** the physical printed copy of the Vault unseal key must be in hand
before starting. Without it, Vault cannot be unsealed and will not serve secrets to any
dependent service.

#### Step 1 — Restore Vault Data Files from SIT

Contact SIT to restore the Vault data directory from the offsite backup. Provide the
Vault server name and the target snapshot timestamp.

#### Step 2 — Start the Vault Container

```bash
docker compose up -d vault
```

Vault will start in **sealed** state. It will not serve secrets until unsealed.

#### Step 3 — Unseal Vault

```bash
docker exec -it vault vault operator unseal
# Enter the physical printed unseal key when prompted
```

Repeat if the threshold requires multiple key shares. Vault logs that it is unsealed when
ready.

#### Step 4 — Verify and Re-fetch Secrets

```bash
docker exec vault vault status   # Sealed: false
```

Run the secrets-fetching scripts so all dependent services have current credentials. Then
verify that the NCP and national connector services can authenticate normally.

---

### Scenario 3 — MySQL Point-in-Time Recovery

**Use when:** the MySQL databases are corrupted or an accidental change needs to be
undone. This covers both recovering to just before an incident and recovering to the most
recent full dump.

**Requirements:**
- The full dump file whose binlog coordinate precedes the target time.
- All binary log files from that coordinate up to the target time, present in
  `/opt/backup/mysql/binlog/`.

```bash
# Step 1 — Restore the full backup
./backups/mysql/restore-full.sh /opt/backup/mysql/full/mysql-full-20260101-030000.sql.gz --yes

# Step 2 — Replay binlogs up to the target moment
./backups/mysql/restore-incremental.sh \
    --after /opt/backup/mysql/full/mysql-full-20260101-030000.sql.gz \
    --stop-datetime '2026-01-15 14:30:00'
```

`restore-incremental.sh` parses the binlog start coordinate from the dump's embedded
`CHANGE REPLICATION SOURCE TO` comment — `--after <dump>` is all that is needed to
anchor the replay start. The `--stop-datetime` is in **container-local time**.

If binlog files are unavailable (purged or lost after the retention window), skip step 2.
The databases will be at the full dump boundary.

**Gap detection:** if any binary log in the sequence is missing, the script aborts with a
list of the absent files. Replaying across a gap silently loses all writes in the missing
file. Pass `--allow-gaps` only if you are intentionally skipping a known-corrupt file.

**Manual coordinate override:**

```bash
./backups/mysql/restore-incremental.sh \
    --from binlog.000051 \
    --start-position 4 \
    --stop-datetime '2026-01-15 14:30:00'
```

**Verify after restore:**

```bash
docker exec openncp_db mysql -u root -p"$(tr -d '[:space:]' < NCP/db_root_password.txt)" \
    -e "SELECT TABLE_SCHEMA, COUNT(*) FROM information_schema.TABLES \
        WHERE TABLE_SCHEMA LIKE 'ehealth_%' GROUP BY TABLE_SCHEMA;"
```

---

### Scenario 4 — SQLite Restore

**Use when:** a SQLite database (undo log, LMS cache, job queue, or Frabedelsesservice EU
data) is corrupted or accidentally cleared.

```bash
# Restore from the latest snapshot
./backups/sqlite/restore.sh

# Restore from a specific snapshot
./backups/sqlite/restore.sh /opt/backup/sqlite/20260115-100000

# Skip confirmation prompt
./backups/sqlite/restore.sh --yes
```

The `national-connector` container is stopped automatically during restore and restarted
afterwards. WAL and SHM journal files are handled correctly: carried over from the
snapshot if present, removed if absent, so SQLite does not replay a pre-restore journal
against the new file.

**Limitation:** SQLite has no binary log. Recovery is always to a snapshot boundary —
the most recent snapshot before the incident is the best available recovery point.

---

## Secrets Management

All application secrets are stored in Vault. Scripts exist to fetch secrets from Vault
and write them to the local files that Docker Compose mounts into containers (e.g.
`NCP/*.txt`, `national-connector/secrets/`). **After Vault is restored and unsealed,
run these scripts — no manual secret management is required.**

The secret files themselves are not backed up independently and are not in the
repository. They are always derived from Vault.

### Secret Files Written by the Fetch Scripts

**NCP (`NCP/` directory):**

| File | Contents |
|------|----------|
| `db_root_password.txt` | MySQL root password |
| `db_password.txt` | MySQL application user password |
| `db_username.txt` | MySQL application username |
| `cts_password.txt` | CTS (Central Terminology Service) password |
| `cts_username.txt` | CTS username |
| `seal_keystore_password.txt` | SEAL keystore password |
| `tls_keystore_password.txt` | TLS keystore password |
| `tls_truststore_password.txt` | TLS truststore password |

**National connector (`national-connector/secrets/`):**

| File | Contents |
|------|----------|
| `NC_NCP_CERTIFICATE` | National connector NCP certificate |
| `NC_NCP_PRIVATE_KEY` | National connector NCP private key |
| `SOSI_KEYSTORE_PASSWORD` | SOSI keystore password |
| `LMSFTP_USERNAME` | LMS SFTP username |
| `LMSFTP_PASSWORD` | LMS SFTP password |

### Vault Unseal Key and the Frabedelsesservice EU Encryption Key

The **Vault unseal key** and the **Frabedelsesservice EU (FSEU) encryption key** are
printed and stored physically in a secure location (safe). Neither is stored digitally.

- The Vault unseal key is required to bring Vault out of sealed state after any restart
  or recovery. Without it, Vault starts but cannot serve secrets to any service.
- The FSEU encryption key is required to read Frabedelsesservice EU cross-border
  prescription data.

In an emergency, contact the designated key custodian to retrieve the physical copies.

### Backup of Vault Data

The Vault server's data directory is backed up as a complete set of files to
`/opt/backup` and picked up by the SIT net-backup agent. To restore, see
[Scenario 2](#scenario-2--vault-server-recovery).

The Vault unseal key is never backed up digitally. Only the physical printed copy is
authoritative.

---

## Configuration Reference

All backup scripts are configured in `backups/lib/config.sh`. The following variables
control behavior and can be overridden via environment variables:

| Variable | Default | Description |
|----------|---------|-------------|
| `EHDSI_BACKUP_DIR` | `/opt/backup` | Root output directory for all backups |
| `MYSQL_CONTAINER` | `openncp_db` | Name of the MySQL Docker container |
| `MYSQL_DATABASES` | `ehealth_properties ehealth_atna ehealth_ltrdb ehealth_eadc` | Databases to dump/restore |
| `MYSQL_PASSWORD_FILE` | `NCP/db_root_password.txt` | Path to root password file |
| `MYSQLBINLOG_PATH` | `/usr/libexec/mysqlsh/mysqlbinlog` | Path to `mysqlbinlog` inside the container |
| `FULL_BACKUP_RETAIN` | `7` | Number of full dumps to keep |
| `BINLOG_RETAIN_DAYS` | `14` | Age in days after which binlogs are purged |
| `SQLITE_CONTAINER` | `national-connector` | Name of the SQLite container |
| `SQLITE_DATABASES` | `undo-db.sqlite local-lms-db.sqlite job-queue.sqlite` | SQLite files to snapshot |
| `SQLITE_DATA_DIR` | `national-connector/data` | Host-side path to SQLite files |
| `SQLITE_BACKUP_RETAIN` | `7` | Number of SQLite snapshots to keep |

---

## Testing the Backup Pipeline

The automated test suite in `backups/test/tests.sh` covers the full backup/restore cycle.
Run it regularly and always after any changes to the backup scripts.

```bash
# Run all safe tests (no destructive state changes)
./backups/test/tests.sh

# Individual tests
./backups/test/tests.sh sqlite-snapshot   # fully isolated, no live state touched
./backups/test/tests.sh sqlite-cycle      # backup live SQLite, verify integrity
./backups/test/tests.sh mysql-cycle       # backup + restore into disposable container
./backups/test/tests.sh gap-detection     # synthetic binlog gap-check assertions

# Destructive test — drops and recreates dev DBs; dev environment only
./backups/test/tests.sh pitr-cycle
```

See `backups/test/MANUAL-TEST-GUIDE.md` for a detailed walkthrough of each test,
including what each test proves and how to interpret pass/fail output.

**Minimum recommended verification before relying on a backup for production recovery:**

1. Run `mysql-cycle` to confirm a full dump can be restored into a fresh container.
2. Run `sqlite-cycle` to confirm SQLite snapshots are intact and restorable.
3. Spot-check a recent full dump with `gzip -t /opt/backup/mysql/full/<latest>.sql.gz`.
