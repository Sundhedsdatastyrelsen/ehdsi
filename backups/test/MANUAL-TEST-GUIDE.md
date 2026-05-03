---
name: MySQL Backup — Manual Test Guide
description: Step-by-step instructions for verifying the MySQL backup/restore scripts in a disposable environment
---

# MySQL Backup — Manual Test Guide

This guide walks through validating the scripts in [backups/mysql/](../mysql/) end-to-end.
The goal is to **prove the scripts work**, not just that they exit 0. Each test has a
clear pass/fail signal.

**Time budget:** ~30 min for the full run.

---

## 0. Prerequisites

- Live `openncp_db` container running (`docker ps | grep openncp_db`).
- `/opt/backup` (or `$EHDSI_BACKUP_DIR`) writable.
- `NCP/db_root_password.txt` readable.
- At least 2 GB free on the backup volume.
- Nothing else using TCP port 13308 on the host (used by test 4 only).
- This is a **dev** environment. Test 3 is destructive — it rolls `openncp_db`
  back to whatever point-in-time it's validating.

Run all commands from the repo root unless stated otherwise.

---

## Test 1 — Full backup + restore cycle (automated)

What it proves: a full `mysqldump` can be gzipped, decompressed, and re-loaded
into a fresh MySQL server; table counts match; a sentinel row round-trips.

```bash
./backups/test/test-mysql-cycle.sh
```


**Pass:** exits 0, final line reads `ALL CHECKS PASSED`.
**Fail:** any `FAIL:` line, or non-zero exit.

**What to look for in the logs:**
- Sentinel row value comes back as `backup_test_value`.
- Every `ehealth_*` database reports matching source/restored table counts.
- No `ERROR` lines during `gunzip | mysql` phase.


---

## Test 2 — Binlog backup (idempotency + rotation)

What it proves: `binlog-backup.sh` rotates the active binlog, copies rotated
files to the host, skips already-copied files, and never copies the active one.

```bash
# Count binlog files currently on the host
BEFORE=$(ls /opt/backup/mysql/binlog/ 2>/dev/null | wc -l)
echo "Before: $BEFORE binlog file(s)"

# First run — should copy at least one new file
./backups/mysql/binlog-backup.sh

AFTER_1=$(ls /opt/backup/mysql/binlog/ | wc -l)
echo "After first run: $AFTER_1 binlog file(s)"

# Second run back-to-back — should copy exactly ONE new file
# (the one that was active during the first run, now rotated)
./backups/mysql/binlog-backup.sh

AFTER_2=$(ls /opt/backup/mysql/binlog/ | wc -l)
echo "After second run: $AFTER_2 binlog file(s)"
```

**Pass:**
- `AFTER_1 > BEFORE` (at least one new file on first run).
- `AFTER_2 == AFTER_1 + 1` (each `FLUSH BINARY LOGS` rotates exactly one file).
- The currently-active binlog inside the container is NOT in `/opt/backup/mysql/binlog/`.

Verify the last point:
```bash
ACTIVE=$(docker exec openncp_db mysql -u root \
    -p"$(tr -d '[:space:]' < NCP/db_root_password.txt)" \
    --batch --skip-column-names \
    -e "SHOW BINARY LOGS;" 2>/dev/null | tail -n 1 | awk '{print $1}')
echo "Active binlog: $ACTIVE"
ls /opt/backup/mysql/binlog/ | grep -q "^$ACTIVE\$" \
    && echo "FAIL: active binlog was copied" \
    || echo "PASS: active binlog NOT in backup dir"
```

---

## Test 3 — Point-in-time recovery (PITR)

What it proves: after a full backup, each subsequent write lands in a binary
log. `restore-full.sh` + `restore-incremental.sh --stop-datetime T` should
bring the database back to *exactly* the state at `T` — not earlier (missing
writes), not later (replayed past the intended recovery point).

**This test is destructive to the dev database.** It drops and recreates
`ehealth_*` databases on `openncp_db` and rolls them back to a point in time.
Do not run on anything you care about.

### Helper scripts

Two small utilities drive the test, both under [backups/test/](.):

- [`add-sentinel.sh <label>`](add-sentinel.sh) — inserts a row
  `NAME='_sentinel_<label>'` into `ehealth_properties.EHNCP_PROPERTY`.
- [`list-sentinels.sh`](list-sentinels.sh) — lists every sentinel currently
  visible in the target container.

Both honour `$MYSQL_CONTAINER` and `$MYSQL_ROOT_PASSWORD`.

### 3a. Automated

```bash
./backups/test/test-pitr-cycle.sh
```


### 3b. Manual walkthrough

The nine-step flow the automated test implements. Run it by hand when you
want to inspect state between steps, or when reasoning about a real recovery
scenario.

```bash
# 1. Insert sentinel A (in source DB, before any backup)
./backups/test/add-sentinel.sh A

# 2. Full backup — captures A into the dump (with embedded binlog coordinate)
./backups/mysql/full-backup.sh
FULL=$(ls -t /opt/backup/mysql/full/*.sql.gz | head -1)
echo "Full backup: $FULL"

# 3. Insert sentinel B (must be recovered via binlog replay)
./backups/test/add-sentinel.sh B

# 4. Rotate + back up the binlog that contains B's write
./backups/mysql/binlog-backup.sh

# 5. Record cutoff in CONTAINER-LOCAL time 
sleep 2
STOP_AT=$(docker exec openncp_db date '+%Y-%m-%d %H:%M:%S')  # Get timezone in the container to avoid timezone problems
echo "Cutoff: $STOP_AT"
sleep 2

# 6. Insert sentinel C (AFTER cutoff — must NOT be in the restored DB)
./backups/test/add-sentinel.sh C

# 7. Back up the binlog containing C's write (still needed on the host so
#    --stop-datetime has something to filter in the replay)
./backups/mysql/binlog-backup.sh

# Confirm everything is in the source right now
./backups/test/list-sentinels.sh
# Expect: A, B, C

# 8. Restore only the full backup
./backups/mysql/restore-full.sh "$FULL" --yes
./backups/test/list-sentinels.sh
# Expect: A only (B and C were written after the dump)

# 9. Replay binlogs, stopping at the cutoff
./backups/mysql/restore-incremental.sh --after "$FULL" --stop-datetime "$STOP_AT"
./backups/test/list-sentinels.sh
# Expect: A and B — NOT C

# Cleanup: remove all test sentinels
docker exec openncp_db mysql -u root \
    -p"$(tr -d '[:space:]' < NCP/db_root_password.txt)" \
    -e "DELETE FROM ehealth_properties.EHNCP_PROPERTY WHERE NAME LIKE '\\_sentinel\\_%' ESCAPE '\\\\';" 2>/dev/null
```

**Pass:** step 8 shows `A` only; step 9 shows `A` and `B`.

### Continuity check

`restore-incremental.sh` refuses to replay if there are gaps in the binlog
sequence (e.g. `binlog.000050, binlog.000052` with `000051` missing), or if
the dump's start file is not on disk. 

Pass `--allow-gaps` only if you deliberately want to skip a known-corrupt
file; accept the silent data loss that comes with it.

Test this by renaming a binlog partway through it.

---

## Test 4 — Corrupt-file handling

What it proves: `restore-full.sh` bails cleanly on a truncated/corrupted dump
instead of partially applying it to the live DB.

```bash
# Create a deliberately corrupted dump
cp "$(ls -t /opt/backup/mysql/full/*.sql.gz | head -1)" /tmp/corrupt.sql.gz
truncate -s -100 /tmp/corrupt.sql.gz  # chop off the last 100 bytes

# Run restore (against a DISPOSABLE container, NOT openncp_db!)
docker run -d --name corrupt-test -e MYSQL_ROOT_PASSWORD=x -p 13308:3306 mysql:9
until docker exec corrupt-test mysqladmin ping -u root -px 2>/dev/null | grep -q alive; do
    sleep 1
done

# Simulate the restore pipeline manually so we don't point the real script at it
gunzip -c /tmp/corrupt.sql.gz | docker exec -i corrupt-test mysql -u root -px
echo "Exit: $?"

docker rm -f corrupt-test
rm -f /tmp/corrupt.sql.gz
```

**Pass:** non-zero exit code, visible `gzip: unexpected end of file` or
similar. 

---

## Test 5 — Retention enforcement

What it proves: `cleanup_old_backups` in [common.sh](../lib/common.sh)
actually deletes old full backups when more than `$FULL_BACKUP_RETAIN` exist.

```bash
# Fake N+2 old backups by touching files with past dates
for i in $(seq 1 $((FULL_BACKUP_RETAIN + 2))); do
    DATE=$(date -d "$i days ago" '+%Y%m%d-%H%M%S')
    touch "/opt/backup/mysql/full/mysql-full-fake-${DATE}.sql.gz"
done

BEFORE=$(ls /opt/backup/mysql/full/ | wc -l)

# Trigger cleanup by running a real backup
./backups/mysql/full-backup.sh

AFTER=$(ls /opt/backup/mysql/full/ | wc -l)

echo "Before: $BEFORE, After: $AFTER, Retain: ${FULL_BACKUP_RETAIN:-7}"

# Clean up fake files (if cleanup missed any)
rm -f /opt/backup/mysql/full/mysql-full-fake-*.sql.gz
```

**Pass:** `AFTER == FULL_BACKUP_RETAIN` (default 7).

---

## Test 6 — SQLite snapshot cycle

What it proves: SQLite backups are full snapshots; restoring one brings the
database back to exactly that point and discards any writes made after it.
This is the SQLite analogue of Test 3, but **without** a `--stop-datetime`
knob — SQLite has no binary log, so you can only restore to snapshot
boundaries, never to a moment between them.

```
t0 ─ sentinel A ─┬─ snapshot S1
                 │
                 ├─ sentinel B ─┬─ snapshot S2
                 │              │
                 │              └─ sentinel C ─┬─ restore S1 → only A
                 │                             │
                 │                             └─ restore S2 → A, B (never C)
```

### Helper scripts

Mirror of the MySQL helpers, operating on `undo-db.sqlite`:

- [`sqlite-add-sentinel.sh <label>`](sqlite-add-sentinel.sh) — insert a row
  `(label, ts)` into a `_sentinel` table (created on first call).
- [`sqlite-list-sentinels.sh`](sqlite-list-sentinels.sh) — dump the
  `_sentinel` rows currently visible.

Both target `$NC_DIR/data/undo-db.sqlite`, where `$NC_DIR` defaults to
`national-connector/` and can be overridden for testing.

### 6a. Automated

```bash
./backups/test/test-sqlite-snapshot-cycle.sh
```

Runs the full flow inside a temp `$NC_DIR` + `$EHDSI_BACKUP_DIR` so the real
data and backup directories are untouched.

**Pass:** exits 0, final line reads `SQLITE SNAPSHOT CYCLE TEST PASSED`.

### 6b. Manual walkthrough

Run this against the real dev data if you want to inspect the filesystem
between steps. It is **destructive** — step 6 overwrites
`national-connector/data/undo-db.sqlite` with the snapshot contents. Dev only.

> **Permissions:** the NC container runs as uid 10001 and owns
> `national-connector/data/`. From the host, your user can read but not
> write, so `sqlite-add-sentinel.sh` and `sqlite/restore.sh` need either
> `sudo` or a temporary `chown` to your uid. The automated test (6a)
> avoids this by using a temp directory.

```bash
# 0. Make sure undo-db.sqlite exists and has at least one table.
#    (The NC container creates it on first run; if you're coming from an
#    empty data dir, seed it from the latest backup:)
LATEST=$(ls -td /opt/backup/sqlite/*/ | head -1)
[[ -f national-connector/data/undo-db.sqlite ]] || \
    cp "$LATEST/undo-db.sqlite" national-connector/data/

# 1. Add sentinel A
sudo ./backups/test/sqlite-add-sentinel.sh A

# 2. Snapshot S1 (captures A)
sudo ./backups/sqlite/backup.sh
S1=$(ls -td /opt/backup/sqlite/*/ | head -1 | sed 's:/$::')
echo "S1 = $S1"

# 3. Add sentinel B
sudo ./backups/test/sqlite-add-sentinel.sh B

# 4. Snapshot S2 (captures A and B). 
sudo ./backups/sqlite/backup.sh
S2=$(ls -td /opt/backup/sqlite/*/ | head -1 | sed 's:/$::')
echo "S2 = $S2"

# 5. Add sentinel C (after both snapshots — should not be in either)
sudo ./backups/test/sqlite-add-sentinel.sh C

# Confirm the live state
sudo ./backups/test/sqlite-list-sentinels.sh
# Expect: A, B, C

# 6. Restore S1, verify
sudo ./backups/sqlite/restore.sh "$S1" --yes
sudo ./backups/test/sqlite-list-sentinels.sh
# Expect: A only

# 7. Restore S2, verify
sudo ./backups/sqlite/restore.sh "$S2" --yes
sudo ./backups/test/sqlite-list-sentinels.sh
# Expect: A and B — never C

# Cleanup: drop the test table
sudo sqlite3 national-connector/data/undo-db.sqlite "DROP TABLE IF EXISTS _sentinel;"
```

**Pass:** step 6 shows `A` only; step 7 shows `A` and `B`.

### Known failure modes

- **Both snapshots resolve to the same directory** → `sqlite/backup.sh`
  names snapshots by `YYYYMMDD-HHMMSS`. Two backups within the same second
  collide; make sure there's at least a 1-second gap between steps 2 and 4.
- **`chown` error during restore** → if you are not root and the data dir
  isn't already owned by uid 10001, `sqlite/restore.sh` skips the chown
  with a NOTE. The restore itself completes; the NC container's init step
  fixes permissions on next start.
- **C visible after step 6 or 7** → the snapshot file was modified after
  it was taken (e.g. another process wrote to `$SNAPSHOT_DIR`), or
  `sqlite-add-sentinel.sh` is writing into the wrong database. Check
  `$NC_DIR` / `SQLITE_DATA_DIR`.

---

## Cleanup after all tests

```bash
docker rm -f pitr-test corrupt-test backup-test-mysql 2>/dev/null
# Optional: wipe test binlogs/dumps accumulated during testing
# rm -rf /opt/backup/mysql/full/*fake*
```