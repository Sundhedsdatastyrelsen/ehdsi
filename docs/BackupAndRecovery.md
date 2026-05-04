# Backup and Recovery Plan — NCPeH at Sundhedsdatastyrelsen

An operational backup and recovery plan for the NCPeH (National Contact Point eHealth)
installation run by Sundhedsdatastyrelsen (SDS).

> **Status — read this first.** As of writing, the installation runs in
> **acceptance only**, exchanging partner-test traffic; it is not yet
> citizen-facing. This plan therefore reflects setup experience plus pre-go-live
> design, not an operational track record. Specifically:
>
> - **Backup-failure alerting is planned but not yet implemented.** Until the
>   Loki/Grafana alert rules are in place, rely on `systemctl list-timers
>   'ehdsi-*'` and `journalctl -u 'ehdsi-*.service'` to confirm the timers ran.
> - **Steady-state size of `/opt/backup` has not been measured.** Allow generous
>   headroom on first deploy and revisit once a few weeks of retention have
>   accumulated.
> - **No real recovery has been exercised in production.** The procedures below
>   have been tested against the automated test harness (`backups/test/tests.sh`)
>   and during initial server setup, but a full Scenario 1 rebuild has not yet
>   been performed under incident conditions.

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
| `sds-openncp-01p` | OpenNCP (NCP-A / NCP-B), MySQL, national connector, job queue, undo log |
| `sds-ncpdata-01p` | FSEU service (also referred to as the **opt-out service** — same thing under two names), FSEU SQLite data |
| `sds-openncp-01t` | OpenNCP — test environment |
| `sds-ncpdata-01t` | FSEU / opt-out — test environment |
| `sds-openncp-mon` | **Vault**, Grafana, Loki, Mimir, Tempo (monitoring stack) |

All persistent state lives in Docker containers managed from the `NCP/` and
`national-connector/` directories of this repository.

A few topology facts that matter during recovery:

- **Vault runs on `sds-openncp-mon`**, alongside the monitoring stack — not on a
  dedicated server. Every other service depends on Vault to fetch its secrets at
  startup, which makes mon a hard prerequisite for any cold-start recovery
  sequence (see [Recovery ordering](#recovery-ordering) below).
- **Ingress goes through an external SDS/SIT load balancer.** EU partners,
  Danish health services, and the opt-out callers do not hit the VM IPs directly.
- **Static IPs are allow-listed by partners.** A rebuilt VM that comes up on a
  new IP will be potentially silently rejected by EU partner NCPs, FMK, NSP, and others
  until the address is restored. The IP records are held by SIT / the network
  team — not in this repo. **Confirm the rebuilt VM has the original IP before
  attempting any partner-facing test.**
- **National connector and FSEU are distinct services.** The national connector
  on `sds-openncp-01p` integrates with Danish prescription services for outbound
  cross-border requests and owns the three SQLite files in
  `national-connector/data/`. FSEU on `sds-ncpdata-01p` is a separate service
  with its own SQLite database and its own `backups/lib/config.sh` overrides.

---

## Network & External Dependencies

A rebuilt or restored server is operationally useful only when it can talk to
the rest of the world. The required outbound destinations are:

| Destination | Purpose | Failure mode if blocked |
|-------------|---------|-------------------------|
| Azure DevOps (HTTPS) | Cloning this repo and the sibling gitops repo | Recovery cannot start; operator cannot fetch source |
| Azure Container Registry | Pulling production Docker images | `docker compose pull` fails; no images to start |
| EU SMP / SML directories | Service-metadata lookups for cross-border partners | NCP-A / NCP-B fail to resolve partner endpoints |
| TSAM source | Terminology master-data sync | Stale code translations; cron catches up later |
| EU partner NCPs | Direct cross-border NCP-to-NCP calls | Patient-summary / e-prescription exchange fails |
| FMK, NSPen, other Danish health services | National-side prescription / dispensing flows | Outbound prescription operations fail |
| Vault (`sds-openncp-mon`) on the management network | Secret retrieval for every service at startup | Containers refuse to start (or restart) |

A few authentication notes that affect what a recovering operator should
expect to see:

- **Cross-border (EU) traffic** uses certificates issued by the EU eHealth
  network CA. These are signed externally and are not regenerable in-house;
  they live in git encrypted at rest, decryption keys gotten by the secrets-fetch
  scripts at deploy time. The decryption key is bundled into the fetch
  script's logic — operators do not need to handle it manually.

- **Danish health services** authenticate primarily via **SOSI ticket** (an
  STS-signed federation token). A handful of legacy services still
  use certificate-based auth, but this is a dying breed and only used where
  services do not yet support SOSI.

- **Inter-service connections** (NC↔OpenNCP, NC↔FSEU/opt-out) use self-signed
  certificates managed by a rotation script that runs only when rotating.
  After a restore the existing certs come back from backup — the script is
  **not** run as part of recovery.

- **Ingress** terminates at the SDS/SIT load balancer in front of the VMs.
  Partners and Danish services do not connect to VM IPs directly. This means
  health-checks executed *from the VM itself* using `localhost` exercise the
  service container only; an end-to-end check requires going through the load
  balancer's public hostname.

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
| `sds-openncp-mon` | `/opt/backup` | Every 30 min | Vault data directory, Grafana dashboards, metrics, log exports |

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

Vault runs on **`sds-openncp-mon`**, co-located with the monitoring stack — it
does not have a dedicated server. It manages encrypted secrets for the entire
installation. Its data directory is backed up as a complete set of files to
`/opt/backup` on mon and picked up by the SIT net-backup agent alongside the
other application backups every 30 minutes.

The **Vault unseal key** is a single printed copy (no Shamir splitting) held
physically in a secure location (safe). Without it, a restored Vault instance
will start in sealed state and cannot serve secrets to any dependent service.
See [Scenario 2](#scenario-2--vault-server-recovery) for restore instructions.

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

In production the timers run as **root**, which already satisfies the access
requirements below. If you adapt the units to run as a different user instead,
that user needs:

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

### Recovery ordering

Vault sits on `sds-openncp-mon`, and every other service fetches its secrets
from Vault at startup. That puts mon on the critical path for any cold-start
recovery, even when mon itself is healthy from the operator's perspective. Two
rules follow:

1. **If both prod (`sds-openncp-01p` and/or `sds-ncpdata-01p`) and mon are
   down, restore mon first — always.** Until Vault is back, nothing else can
   start cleanly. The relevant subset of [Scenario 2](#scenario-2--vault-server-recovery)
   is a hard prerequisite for [Scenario 1](#scenario-1--full-server-loss-restore-from-scratch).
2. **If only mon is down**, the impact is **service-affecting on restart only.**
   Already-running OpenNCP, national connector and FSEU containers keep
   functioning with their cached, file-mounted secrets. But any container that
   restarts (deploy, OOM, reboot, image pull) will fail until Vault is reachable
   and unsealed again. Treat a mon outage as an active production incident even
   though the gateway still answers, because the next restart of any container
   will turn it into a partial outage.

### Before Starting Any Recovery

1. Identify **when** the incident occurred (from logs, monitoring, or user reports).
2. Confirm that the required backup files are present on-disk under `/opt/backup`. If
   not, request them from SIT (Layer 2 offsite restore).
3. Record the recovery start time and **preserve the current state before
   overwriting anything.** A restore is destructive, and once it runs the broken
   state is gone — capture it first, even if you don't yet know whether it will
   be needed for incident analysis. The minimum checklist:

   ```bash
   mkdir -p /opt/forensics/$(date +%Y%m%d-%H%M%S) && cd "$_"

   # Container state and logs
   docker ps -a > docker-ps.txt
   for c in $(docker ps -a --format '{{.Names}}'); do
       docker logs --tail 5000 "$c" > "docker-logs-$c.txt" 2>&1
   done

   # MySQL — dump the broken databases before they are overwritten
   docker exec openncp_db mysqldump -u root \
       -p"$(tr -d '[:space:]' < /var/ehdsi/NCP/db_root_password.txt)" \
       --all-databases --single-transaction --routines --triggers \
       2>mysqldump.err | gzip > mysql-broken.sql.gz

   # SQLite — copy aside (include WAL/SHM, they hold uncommitted writes)
   cp -av /var/ehdsi/national-connector/data/*.sqlite* ./

   # Host logs around the incident window
   journalctl --since "1 hour ago" > journal.txt
   tar -czf var-log.tgz /var/log/ 2>/dev/null
   ```

   Logs are also shipped to the Loki/Grafana stack, so a snapshot of the
   monitoring database (Layer 2 picks up `sds-openncp-mon:/opt/backup` every
   30 min) covers most of the log-based forensics. Local capture is still
   worthwhile for container `docker logs` output that may not have been
   shipped yet.
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

**NC↔Opt-Out (FSEU) mTLS** (mutual TLS on both sides)

The national connector on `sds-openncp-01p` and the **opt-out service — which is
the same service as FSEU on `sds-ncpdata-01p`** — communicate over mutual TLS.
Each side holds the other's certificate in its own trust store. After a restore,
both sides may need the certificate re-imported:

- National connector trust store: `national-connector/config/<env>-opt-out-truststore.p12`
- FSEU / opt-out trust store on `sds-ncpdata-01p`: its equivalent config on that server

Both servers must be touched for the mTLS connection to come back. If only one
side is restored, the handshake fails silently from the caller's perspective —
the symptom is a 503 / connection refused on the national connector, not an
explicit certificate error.

To verify the connection manually before starting full service:

```bash
# From sds-openncp-01p, test that the FSEU/opt-out endpoint responds over mTLS
curl --cert national-connector/config/<env>-opt-out-keystore.p12 \
     --cacert national-connector/config/<env>-opt-out-truststore.p12 \
     https://<fseu-host>:<port>/health //TODO real health endpoint
```

#### Static IP not preserved on VM rebuild

EU partner NCPs, FMK, NSP, and other Danish health services all might allow-list this
installation **by source IP**. A VM that comes up on a new IP after rebuild might be 
silently rejected by some partners — the symptom is connection timeouts or
generic TLS errors against partner endpoints, even though all certificates and
trust stores look correct locally.

The IP records are held by SIT / the network team, not in this repository.
Confirm with them that the rebuilt VM has the original IP **before** running
any of the post-restore liveness checks below; otherwise you will spend hours
chasing certificate issues that aren't there.

```bash
# On the rebuilt VM, confirm the externally-visible source IP is the expected one
curl -sS https://api.ipify.org; echo
ip -4 addr show | grep inet
```

#### Vault TLS verification on FSEU only

Most services in this installation reach Vault with TLS verification disabled
(closed network, ingress is the trust boundary). FSEU on `sds-ncpdata-01p` is
the exception: its Hoplite integration cannot skip verification and reads the
Vault server certificate from `vault-truststore.jks`. If Vault's certificate
has been regenerated during recovery, FSEU is the one service that will fail to
start with a TLS handshake error until the cert is re-imported. See
[Scenario 2 Step 5](#scenario-2--vault-server-recovery) for the procedure.

#### Verifying inter-service connectivity manually

Before declaring the restore complete, test each connection explicitly rather than
relying on container health checks:

```bash
# OpenNCP gateway is reachable
curl -k https://localhost:8443/openncp-gateway-backend/

# National connector health
curl -k https://localhost:4443/health //TODO real endpoint

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

### Restore semantics — what to expect from each layer

Behaviours that aren't obvious from the scripts but matter when you decide
which scenario to run:

- **MySQL restore is all-or-nothing.** The current scripts replace all four
  databases (`ehealth_properties`, `ehealth_atna`, `ehealth_ltrdb`,
  `ehealth_eadc`) together. There is no supported flag to restore a single one
  while leaving the others untouched; if you need that, do it by hand by
  extracting the relevant section of the dump and applying it to a running
  instance. Plan accordingly when only one database is corrupted.

- **MySQL and SQLite layers are independent.** They share no foreign-key-style
  references, so it is acceptable for a PITR'd MySQL state to be at a different
  timestamp than the SQLite snapshot you restore. Operators do **not** need to
  align timestamps across layers. (The undo log, LMS cache, and job queue are
  scoped to themselves.)

- **The job queue is not idempotent.** When `job-queue.sqlite` is restored from
  a snapshot, any pending jobs at the time of the snapshot will be re-executed.
  Some handlers have observable side effects that may duplicate. **The operating principle
  is that duplicates are less harmful than missing data: proceed with the
  restore.** Notify affected partners (EU partner NCPs, FMK / NSP / other
  Danish recipients) that duplicates are possible during the restore window so
  they can de-duplicate downstream. Do not try to hand-prune the queue before
  restart unless you have a specific reason — the cost of accidentally dropping
  a real pending job is higher than the cost of a duplicate.

- **TSAM synchronization is deferred.** OpenNCP and the national connector
  start and serve traffic without waiting for the TSAM synchronizer cron. The
  next 03:00 cron run will refresh terminology data; if you need it sooner,
  trigger it manually:

  ```bash
  cd /var/ehdsi/NCP && docker compose run tsam-synchronizer
  ```

- **The undo log and LMS cache are best-effort.** They will be slightly out of
  date relative to MySQL after a restore, but neither blocks normal operation —
  `undo-db.sqlite` only matters if a user invokes the undo flow within its
  retention window, and `local-lms-db.sqlite` is a refreshable cache.

- **The inter-service self-signed certificates are restored from backup, not
  regenerated.** A rotation script exists, but it is run only when certificates
  are actually being rotated — not as part of a restore. Do not run it during
  recovery unless you have a specific cert-rotation reason; doing so will
  cascade into trust-store re-imports on every dependent service.

---

### Scenario 1 — Full Server Loss (Restore from Scratch)

Use this procedure when a server is lost and must be rebuilt from nothing, or when
provisioning a replacement server.

> Before starting, confirm two preconditions:
> 1. **If mon is also down, restore mon first** — see
>    [Recovery ordering](#recovery-ordering). The other servers cannot start
>    without Vault.
> 2. **The rebuilt VM must come up on the original IP.** EU partner NCPs, FMK,
>    NSPen, and Danish health services allow-list this installation by source
>    IP, and a different IP will be silently rejected. Coordinate with SIT /
>    the network team before any partner-facing test.

Production pulls pre-built Docker images from a SDS-managed **Azure Container
Registry** (longer image-tag retention than ghcr.io) and clones source from
**Azure DevOps**. CI is also hosted in Azure DevOps. **No build step is required
on the server itself** — all images are pulled at deploy time.

If both Azure DevOps and the Azure Container Registry are unavailable
simultaneously, there is no documented fallback. (The public mirror on GitHub
is not used in production and may lag the Azure source of truth.)

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

Production uses **Azure DevOps over HTTPS with a personal access token (PAT)**.
Each operator authenticates with their own PAT — there is no shared service
account in Vault for git access (and Vault would not be reachable yet at this
step anyway). If you do not already have a PAT for the SDS Azure DevOps
organisation, generate one with **Code (read)** scope before starting.

```bash
# Replace <pat> with your own Azure DevOps PAT.
git clone https://globeteam@dev.azure.com/globeteam/ePPS-ops/_git/ePPS-ops /opt/ehdsi
cd /var/ehdsi
```

Production and acceptance are seperate **sibling "gitops" repositories** in 
Azure DevOps that holds environment-specific configuration. It is
designed to be self-sufficient at deploy time — secrets fetched from Vault, env
files generated by its own scripts — but a recovering operator will likely need
read access to the github repository for debugging deployment-specific values that are not in this
repo.

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

Production images live in a Globeteam-managed **Azure Container Registry** (ACR).
Authenticate using the credentials set up for this installation — typically a
service principal or admin account configured for the registry; see the gitops
repo for the canonical credentials path.

```bash
# Service principal / admin login (substitute the actual registry name and credentials)
echo "<password>" | docker login <registry-name>.azurecr.io \
    -u <username-or-app-id> --password-stdin
```

The Azure CLI alternative (`az acr login --name <registry-name>`) works too if
the host already has `az` installed and a session.

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

Use this procedure when only the Vault server (`sds-openncp-mon`) is lost or its
data directory is corrupted, while the other servers remain intact. Note that
mon also hosts the monitoring stack; restoring it brings Grafana / Loki / Mimir /
Tempo back at the same time.

**Prerequisites:**

- The **physical printed copy of the Vault unseal key** must be in hand before
  starting. The current Vault is configured **without Shamir splitting** —
  there is one printed key, and that single key unseals the vault. Without it,
  Vault cannot be unsealed and will not serve secrets to any dependent service.
- If the unseal key has been lost, Vault data cannot be recovered from backup
  alone. The fallback is to rebuild Vault, re-issue secrets to it, and have
  every dependent service re-fetch — which is significantly more work than this
  scenario covers.

#### Step 1 — Restore Vault Data Files from SIT

Contact SIT to restore the Vault data directory from the offsite backup. Provide the
Vault server name (`sds-openncp-mon`) and the target snapshot timestamp.

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

A single entry of the printed key is sufficient (no Shamir threshold). Vault
logs that it is unsealed when ready.

#### Step 4 — Verify and Re-fetch Secrets

```bash
docker exec vault vault status   # Sealed: false
```

Run the secrets-fetching scripts so all dependent services have current credentials. Then
verify that the NCP and national connector services can authenticate normally.

#### Step 5 — Re-import the Vault Server Certificate into FSEU's Trust Store

This step is **only required if Vault's TLS server certificate has changed**
during the recovery (e.g. because the data directory was re-bootstrapped, or a
rotation happened in parallel). Skip if the same certificate is in use.

Most callers of Vault in this installation are configured to skip TLS
verification — they run on a closed network and trust the network boundary
rather than the certificate. **FSEU on `sds-ncpdata-01p` is the exception.**
It uses a Hoplite integration that does not support disabling verification, and
it ships with a dedicated truststore at `vault-truststore.jks` in the
FSEU/Hoplite repository. After any Vault certificate change, the new server
cert must be imported into that truststore and the FSEU service restarted.

```bash
# On sds-ncpdata-01p, after fetching the new Vault server cert:
keytool -keystore <path-to>/vault-truststore.jks -storepass <password> \
    -delete -alias vault-server 2>/dev/null || true
keytool -keystore <path-to>/vault-truststore.jks -storepass <password> \
    -import -file vault-server.crt -alias vault-server -noprompt
docker compose restart fseu
```

Without this step, FSEU will fail to start with a TLS handshake error against
Vault, even though every other service is fine.

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
anchor the replay start.

The `--stop-datetime` value is interpreted in **container-local time, which is
UTC** for the production MySQL container. If you have an incident timestamp in
Europe/Copenhagen (CET/CEST), convert it to UTC first — getting this wrong by
one or two hours is the most common PITR mistake. Check the running container's
clock with:

```bash
docker exec openncp_db date          # Should print UTC
```

If the host VM's NTP source has drifted, the binlog timestamps themselves may
be off, in which case `--stop-datetime` is unreliable and you should fall back
to a `--stop-position` coordinate from `mysqlbinlog` output.

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

### Post-restore liveness checks

The `localhost`-based connectivity checks under [Common Problems on Restore](#verifying-inter-service-connectivity-manually)
confirm only that the container internals are wired correctly. They do not
prove that EU partners or Danish health services can actually reach the
installation through the SDS/SIT load balancer. Run the following in order
before declaring the restore complete:

1. **Internal health endpoints** — confirm each service is alive:

   ```bash
   # National connector (sds-openncp-01p)
   curl -k https://localhost:4443/health //TODO real endpoint

   # FSEU / opt-out (sds-ncpdata-01p)
   curl -k https://localhost:<fseu-port>/health //TODO real endpoint
   ```

2. **OpenNCP cross-border connectivity** — the most authoritative "are we
   back?" signal from a partner's perspective is a successful **patient
   fetch** through OpenNCP. This exercises the full chain (load balancer →
   OpenNCP → national connector → Danish back-end → response) and is
   **non-destructive** — no side effects on the Danish or partner side.
   Use a known test patient against a partner test environment.

3. **Selective use of the integration test suite.** A full integration test
   exists that simulates more of the cross-border flows. **Do not run it
   wholesale in production** — some of its steps are destructive (e.g.
   creating or modifying records). In production, run **only the fetch /
   read portions manually**, picking out the relevant test cases. Treat the
   integration test as a toolbox for individual checks during recovery, not
   as a press-the-button verification.

4. **Trust-store diff check.** Trust stores are the most common source of
   silent post-restore failure. Confirm each is current:

   ```bash
   # OpenNCP trust store contains the national connector cert
   keytool -list -keystore NCP/keystore/<env>-truststore.jks -storepass changeit \
       -alias national-connector

   # NC trust store contains the FSEU/opt-out cert
   keytool -list -keystore national-connector/config/<env>-opt-out-truststore.p12 \
       -storetype PKCS12
   ```

5. **Backup timers re-enabled.** A successful restore is not complete until
   the timers are armed and the next run has been confirmed:

   ```bash
   systemctl list-timers 'ehdsi-*'
   journalctl -u ehdsi-mysql-binlog-backup.service --since '1 hour ago'
   ```

Only after all five steps pass should the **automatic** secret-fetch script be
re-enabled (see [Manual verification before enabling automatic secret fetching](#manual-verification-before-enabling-automatic-secret-fetching)).

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

- The Vault unseal key is a **single printed key (no Shamir threshold)** required
  to bring Vault out of sealed state after any restart or recovery. Without it,
  Vault starts but cannot serve secrets to any service, and there is no
  digital recovery path.
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
