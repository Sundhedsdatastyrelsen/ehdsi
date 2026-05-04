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
- **SQLite** — periodic online snapshots of the three SQLite databases.
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

### SQLite Databases (`national-connector` container)

Three SQLite files live in `national-connector/data/`:

| File | Contents |
|------|----------|
| `undo-db.sqlite` | Undo log for reversible prescription operations |
| `local-lms-db.sqlite` | Local LMS (medication) database cache |
| `job-queue.sqlite` | Persistent job queue for background processing |

**Strategy:** online snapshots using `sqlite3 .backup`, which produces a consistent copy
without stopping the container. Every snapshot is integrity-checked immediately after
creation.

**Retention:** 7 most recent snapshots in `/opt/backup/sqlite/`.

**Limitation:** SQLite has no binary log equivalent. Recovery is always to a snapshot
boundary — there is no point-in-time recovery between snapshots. Snapshot frequency
should be set according to how much data loss between snapshots is acceptable.

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

### Scenario 1 — MySQL Full Restore

Restores the MySQL databases to the state at the time of the most recent (or a specific)
full dump. No binlog replay; any writes after the dump are lost.

**Use when:** the database is corrupted or lost and you do not need to recover writes made
after the last full dump.

```bash
# Restore latest full backup (interactive confirmation prompt)
./backups/mysql/restore-full.sh

# Restore a specific dump file
./backups/mysql/restore-full.sh /opt/backup/mysql/full/mysql-full-20260101-030000.sql.gz

# Skip confirmation prompt (for automation)
./backups/mysql/restore-full.sh --yes
```

The script drops the existing databases before restoring, so tables in the live DB that
are absent from the dump do not survive.

**Verify after restore:**

```bash
docker exec openncp_db mysql -u root -p"$(tr -d '[:space:]' < NCP/db_root_password.txt)" \
    -e "SELECT TABLE_SCHEMA, COUNT(*) FROM information_schema.TABLES \
        WHERE TABLE_SCHEMA LIKE 'ehealth_%' GROUP BY TABLE_SCHEMA;"
```

---

### Scenario 2 — MySQL Point-in-Time Recovery (PITR)

Restores the MySQL databases to a specific moment in time, replaying binary logs on top
of a full dump to capture writes that occurred after the dump was taken.

**Use when:** you need to recover to a moment after the last full dump — e.g. recovering
to just before an accidental deletion or data corruption.

**Requirements:**
- The full dump file whose binlog coordinate precedes the target time.
- All binary log files from that coordinate up to the target time, present in
  `/opt/backup/mysql/binlog/`.

```bash
# Step 1 — Restore the full backup (--yes skips the prompt; required for PITR flow)
./backups/mysql/restore-full.sh /opt/backup/mysql/full/mysql-full-20260101-030000.sql.gz --yes

# Step 2 — Replay binlogs up to the target moment
./backups/mysql/restore-incremental.sh \
    --after /opt/backup/mysql/full/mysql-full-20260101-030000.sql.gz \
    --stop-datetime '2026-01-15 14:30:00'
```

`restore-incremental.sh` automatically parses the binlog start coordinate from the dump
file's embedded `CHANGE REPLICATION SOURCE TO` comment, so `--after <dump>` is all that
is needed to anchor the start. The `--stop-datetime` is in **container-local time** (not
necessarily UTC — check the container timezone).

**Gap detection:** if any binary log file in the sequence is missing from the backup
directory, the script aborts with an error listing which files are absent. This prevents
silent data loss from replaying across a gap. If you are intentionally skipping a
known-bad file, pass `--allow-gaps`.

**Advanced options:**

```bash
# Replay from a manually specified file and position
./backups/mysql/restore-incremental.sh \
    --from binlog.000051 \
    --start-position 4 \
    --stop-datetime '2026-01-15 14:30:00'
```

---

### Scenario 3 — SQLite Restore

Restores one or more SQLite databases to the state of a specific snapshot.

**Use when:** a SQLite database is corrupted, accidentally cleared, or needs to be rolled
back.

```bash
# Restore from the latest snapshot (interactive confirmation prompt)
./backups/sqlite/restore.sh

# Restore from a specific snapshot directory
./backups/sqlite/restore.sh /opt/backup/sqlite/20260115-100000

# Skip confirmation prompt
./backups/sqlite/restore.sh --yes
```

The `national-connector` container is stopped automatically during restore and restarted
afterwards. If Docker Compose is available, the script uses it; otherwise it falls back to
`docker stop` / `docker start`.

WAL and SHM journal files are handled correctly: the script carries them over from the
snapshot if present, and removes any stale ones if absent, so SQLite does not apply a
pre-restore journal against the restored database.

**Note on point-in-time recovery for SQLite:** there is no equivalent to MySQL's binary
log replay. You can only restore to a snapshot boundary. If the required state is between
two snapshots, the most recent snapshot before the incident is the best available
recovery point.

---

### Scenario 4 — Full Server Loss (Restore from Scratch)

Use this procedure when a server is lost and must be rebuilt, or when setting up a new
server to replace an existing one.

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
git clone <repo-url> /var/ehdsi
cd /var/ehdsi
```

#### Step 3 — Restore Secrets

Secrets are not in `/opt/backup`. They must be obtained from their secure storage before
any application can start. See [Secrets Management](#secrets-management) for the full
list.

At minimum, before running any restore scripts, you need:

- `NCP/db_root_password.txt` — MySQL root password.

#### Step 4 — Obtain Application Backups from SIT

If the original server's `/opt/backup` is gone, contact SIT to restore the offsite backup
to `/opt/backup` on the new server. Provide:

- The source server name (`sds-openncp-01p` or `sds-ncpdata-01p`).
- The most recent timestamp you need (ideally the last snapshot before the incident).

SIT will restore the contents of `/opt/backup` from their archive.

#### Step 5 — Configure Production Environment

Follow `docs/prod-environment.md` to apply all production-specific configuration to the
`NCP/` directory before starting containers. Key items:

- Production keystores in `NCP/keystore/`
- Production `.env` file (TLS, SMP, TSAM values)
- Production secret files (`.txt` files in `NCP/`)
- `openncp-configuration.properties` with `automated.validation=false`
- All `docker-compose.yml` production flags (`-Dserver.ehealth.mode=PRODUCTION`, no debug
  ports)

#### Step 6 — Start the MySQL Container

```bash
cd /var/ehdsi/NCP
./run.sh up openncp_db
```

Wait until MySQL is accepting connections:

```bash
docker exec openncp_db mysqladmin ping -u root \
    -p"$(tr -d '[:space:]' < db_root_password.txt)" --silent
```

#### Step 7 — Restore MySQL

Restore the most recent full backup, then replay binary logs to the desired recovery
point:

```bash
cd /var/ehdsi

# Full restore (latest dump)
./backups/mysql/restore-full.sh --yes

# PITR — replay binlogs to the point just before the incident
LATEST_DUMP=$(ls -t /opt/backup/mysql/full/*.sql.gz | head -1)
./backups/mysql/restore-incremental.sh \
    --after "$LATEST_DUMP" \
    --stop-datetime '2026-MM-DD HH:MM:SS'
```

If you only need to recover to the last full dump boundary (not PITR), skip the
`restore-incremental.sh` step.

#### Step 8 — Start the National Connector and Restore SQLite

```bash
# Restore the latest SQLite snapshot (stops/starts the container automatically)
./backups/sqlite/restore.sh --yes

# Then start the national connector normally
cd /var/ehdsi/national-connector
docker compose up -d
```

#### Step 9 — Install Backup Schedules

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

#### Step 10 — Verify

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

# Timer status
systemctl list-timers 'ehdsi-*'
```

---

## Secrets Management

Secrets are not written to `/opt/backup` and are not covered by the net-backup or disk
snapshot schedules in any automated sense. They must be maintained separately.

### NCP Secrets (`NCP/` directory)

| File | Contents |
|------|----------|
| `NCP/db_root_password.txt` | MySQL root password |
| `NCP/db_password.txt` | MySQL application user password |
| `NCP/db_username.txt` | MySQL application username |
| `NCP/cts_password.txt` | CTS (Central Terminology Service) password |
| `NCP/cts_username.txt` | CTS username |
| `NCP/seal_keystore_password.txt` | SEAL keystore password |
| `NCP/tls_keystore_password.txt` | TLS keystore password |
| `NCP/tls_truststore_password.txt` | TLS truststore password |

### National Connector Secrets (`national-connector/secrets/`)

| File | Contents |
|------|----------|
| `NC_NCP_CERTIFICATE` | National connector NCP certificate |
| `NC_NCP_PRIVATE_KEY` | National connector NCP private key |
| `SOSI_KEYSTORE_PASSWORD` | SOSI keystore password |
| `LMSFTP_USERNAME` | LMS SFTP username |
| `LMSFTP_PASSWORD` | LMS SFTP password |

### Vault and Encryption Keys

- The **Vault decryption key** and the **FSEU encryption key** are printed out and stored
  physically in a secure location (safe). These keys are required to decrypt secrets
  stored in Vault and to read FSEU data.
- In an emergency where the Vault server is lost, contact the designated key custodian to
  retrieve the physical copy.

### Backup of Secrets

Back up the secret files from the Vault server. The Vault decryption key is deliberately
not stored digitally — only the physical printed copy exists.

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
