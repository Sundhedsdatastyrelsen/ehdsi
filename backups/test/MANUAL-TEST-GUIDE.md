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

## Cleanup after all tests

```bash
docker rm -f pitr-test corrupt-test backup-test-mysql 2>/dev/null
# Optional: wipe test binlogs/dumps accumulated during testing
# rm -rf /opt/backup/mysql/full/*fake*
```