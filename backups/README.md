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
└── test/
    ├── test-all.sh                  # run the two cycle tests
    ├── test-mysql-cycle.sh          # full backup + restore into disposable container
    ├── test-pitr-cycle.sh           # DESTRUCTIVE: full + binlog PITR on dev DB
    ├── test-sqlite-cycle.sh         # backup live SQLite, verify integrity
    ├── test-sqlite-snapshot-cycle.sh # fully isolated snapshot↔restore↔snapshot
    ├── test-gap-detection.sh        # synthetic binlog gap-check assertions
    ├── add-sentinel.sh / list-sentinels.sh           # MySQL sentinel helpers
    ├── sqlite-add-sentinel.sh / sqlite-list-sentinels.sh  # SQLite equivalents
    └── MANUAL-TEST-GUIDE.md         # step-by-step walkthrough
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
./backups/test/test-all.sh              # safe — runs two cycle tests
./backups/test/test-sqlite-snapshot-cycle.sh   # safe — fully isolated
./backups/test/test-gap-detection.sh    # safe — synthetic data
./backups/test/test-mysql-cycle.sh      # safe — disposable test container
./backups/test/test-pitr-cycle.sh       # DESTRUCTIVE — drops/recreates dev DBs
```

The MySQL tests need `openncp_db` running and the password file readable.
`test/MANUAL-TEST-GUIDE.md` walks through what each test proves.

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

After editing config.sh, run `./backups/test/test-sqlite-snapshot-cycle.sh`
to validate the SQLite path with no live state, then a manual `full-backup.sh`
+ `restore-full.sh` round-trip into a throwaway container for MySQL.
