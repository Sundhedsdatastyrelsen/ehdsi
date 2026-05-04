# backups/

Backup and restore scripts for the two databases this repo runs:

- **MySQL** (`openncp_db` container) — full dumps via `mysqldump`, plus binary
  logs for point-in-time recovery (PITR).
- **SQLite** (`national-connector` container's data dir) — online-safe `.backup`
  snapshots of each `.sqlite` file.

Everything is plain bash + `docker exec`. No daemon, no scheduler. Wire the
scripts into cron / systemd / your CI of choice.

## Layout

```
backups/
├── lib/
│   ├── config.sh         ← per-repo config; this is the only file you edit
│   └── common.sh         ← generic helpers (logging, retention, etc.)
├── mysql/
│   ├── full-backup.sh           # mysqldump → /opt/backup/mysql/full/*.sql.gz
│   ├── binlog-backup.sh         # FLUSH + docker cp rotated binlogs
│   ├── restore-full.sh          # gunzip | mysql, with confirm prompt
│   └── restore-incremental.sh   # mysqlbinlog replay for PITR
├── sqlite/
│   ├── backup.sh         # sqlite3 .backup of every configured DB → snapshot dir
│   └── restore.sh        # stop container, cp files, chown, start container
├── systemd/
│   └── install.sh        # install/uninstall/status for systemd timers
└── test/
    ├── tests.sh                # all five tests, dispatched by subcommand
    ├── sentinels.sh            # MySQL + SQLite sentinel helpers
    └── MANUAL-TEST-GUIDE.md    # step-by-step walkthrough
```

## Setup

1. Install host-side tools: `sqlite3`, `docker`, `gzip`. `mysqlbinlog` runs
   inside the MySQL container, not on the host.
2. Make `$BACKUP_ROOT` writable. Default is `/opt/backup` — override with
   `EHDSI_BACKUP_DIR=/some/other/path`.
3. Ensure `NCP/db_root_password.txt` exists (or export `MYSQL_ROOT_PASSWORD`).
4. Confirm the MySQL container has binary logging enabled. The repo's
   `NCP/mysql/my.cnf` already does:
   ```ini
   server-id = 1
   log-bin = binlog
   binlog-format = ROW
   binlog-expire-logs-seconds = 1209600   # 14 days
   ```
   Without this, `binlog-backup.sh` and `restore-incremental.sh` won't work.

## Running it

```bash
# MySQL — daily full + frequent binlog
./backups/mysql/full-backup.sh
./backups/mysql/binlog-backup.sh

# MySQL — restore-only (full)
./backups/mysql/restore-full.sh                       # latest backup
./backups/mysql/restore-full.sh /path/to/dump.sql.gz  # specific file

# MySQL — restore + PITR
./backups/mysql/restore-full.sh /path/to/dump.sql.gz --yes
./backups/mysql/restore-incremental.sh \
    --after /path/to/dump.sql.gz \
    --stop-datetime '2026-01-15 14:30:00'

# SQLite
./backups/sqlite/backup.sh
./backups/sqlite/restore.sh                  # latest snapshot
./backups/sqlite/restore.sh /opt/backup/sqlite/20260115-100000
```

`--yes` on the MySQL/SQLite restore scripts skips the confirmation prompt
(use it from automation; never on a hot prompt).

Retention is automatic: `FULL_BACKUP_RETAIN=7` dumps, `BINLOG_RETAIN_DAYS=14`,
`SQLITE_BACKUP_RETAIN=7` snapshots. Override via env var.

## Scheduling (systemd)

Install three timers — one per backup script — using
[`systemd/install.sh`](systemd/install.sh):

```bash
sudo ./backups/systemd/install.sh install      # use defaults
sudo ./backups/systemd/install.sh install \
    --full   "*-*-* 03:00:00" \    # daily 03:00
    --binlog "*:0/15" \             # every 15 min
    --sqlite "*-*-* 0/6:00:00"      # every 6 hours
sudo ./backups/systemd/install.sh uninstall
     ./backups/systemd/install.sh status
     ./backups/systemd/install.sh install --dry-run   # preview, no root needed
```

Defaults match the three lines above. Other flags: `--user` (default
`$SUDO_USER`), `--prefix` (default `ehdsi-`), `--no-start`. Once installed,
adjust schedules without re-running the installer:

```bash
sudo systemctl edit ehdsi-mysql-binlog-backup.timer   # creates a drop-in
sudo systemctl list-timers 'ehdsi-*'
journalctl -u ehdsi-mysql-full-backup.service
```

The service user needs docker-group membership, write access to `$BACKUP_ROOT`,
and read access to `MYSQL_PASSWORD_FILE` (or `MYSQL_ROOT_PASSWORD` set in a
`systemctl edit` drop-in). systemd starts services with a clean environment,
so any env-var overrides for [config.sh](lib/config.sh) belong in a drop-in,
not in your shell rc.

## How it works (the non-obvious bits)

- **PITR coordinate.** `full-backup.sh` runs `mysqldump --source-data=2`,
  which embeds a `CHANGE REPLICATION SOURCE TO SOURCE_LOG_FILE=...,
  SOURCE_LOG_POS=...` comment in the dump. `restore-incremental.sh --after
  <dump>` parses that comment to know where to start replaying binlogs.
- **Binlog gap check.** If the binlog backup directory has a missing number
  in the sequence (e.g. `binlog.000050`, `000052` but no `000051`), the
  incremental restore aborts. Replaying across a gap silently drops every
  write in the missing file — `--allow-gaps` is the explicit override for
  intentionally skipping a known-bad file.
- **Active binlog.** `binlog-backup.sh` always issues `FLUSH BINARY LOGS`
  first to rotate the active log, then skips the new active one when
  copying. The currently-written file is unsafe to copy.
- **SQLite WAL/SHM.** `restore.sh` carries WAL/SHM files over from the
  snapshot if present, and otherwise removes any stale ones in the live
  data dir, so SQLite doesn't reuse an old journal against the restored
  database.

## Testing

```bash
./backups/test/tests.sh                  # safe tests (the four below)
./backups/test/tests.sh sqlite-snapshot  # fully isolated, no live state touched
./backups/test/tests.sh sqlite-cycle     # backup live SQLite, verify integrity
./backups/test/tests.sh mysql-cycle      # backup + restore into disposable container
./backups/test/tests.sh gap-detection    # synthetic binlog gap-check assertions
./backups/test/tests.sh pitr-cycle       # DESTRUCTIVE — drops/recreates dev DBs
./backups/test/tests.sh -h               # show usage
```

The MySQL tests need `openncp_db` running and the password file readable
(or `MYSQL_ROOT_PASSWORD` exported). `test/MANUAL-TEST-GUIDE.md` walks
through what each test proves.

For ad-hoc inspection while debugging, [`sentinels.sh`](test/sentinels.sh)
exposes four subcommands: `mysql-add <label>`, `mysql-list`,
`sqlite-add <label>`, `sqlite-list`.

## Porting to another repo

The whole `backups/` directory is meant to be copy-pasted into a sibling
repo. Touch only `lib/config.sh`:

| Variable | Meaning |
|---|---|
| `MYSQL_CONTAINER` | name of the MySQL container |
| `MYSQL_DATABASES` | list of DBs to dump/restore |
| `MYSQL_PASSWORD_FILE` | host path to root-password file (or set `MYSQL_ROOT_PASSWORD`) |
| `MYSQLBINLOG_PATH` | absolute path to `mysqlbinlog` inside the container |
| `MYSQL_DATA_DIR_IN_CONTAINER` | container path holding binlog files (defaults to `/var/lib/mysql`) |
| `SQLITE_CONTAINER` | name of the container that owns the SQLite files |
| `SQLITE_DATABASES` | list of `.sqlite` filenames to back up |
| `SQLITE_DATA_DIR` | host-side path to the directory holding those files |
| `SQLITE_COMPOSE_FILE` / `SQLITE_COMPOSE_SERVICE` | optional, used to stop/start during restore — falls back to plain `docker stop`/`start` |
| `SQLITE_DATA_OWNER` | UID:GID applied to restored files (set empty to skip) |
| `BACKUP_ROOT`, `*_RETAIN*` | output paths and retention knobs |

After editing config.sh, run `./backups/test/tests.sh sqlite-snapshot` to
validate the SQLite path with no live state, then a manual `full-backup.sh`
+ `restore-full.sh` round-trip into a throwaway container for MySQL.
