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

## Glossary

- **PITR** — Point-In-Time Recovery. Restore the database to an exact moment
  between your last full backup and now, by replaying binary logs from that
  coordinate and stopping at a chosen timestamp. This is how you undo "someone
  dropped the wrong table at 14:37" without losing the writes from 14:36.

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

**What this test does NOT cover:** binlog backup, incremental/PITR restore,
crash-during-backup, corrupted file handling. Those are tests 2–5 below.

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

**Pass:** exits 0, final line reads `PITR CYCLE TEST PASSED`.
**Fail:** any `FAIL:` line — see "known failure modes" below.

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

# 5. Record cutoff in CONTAINER-LOCAL time (see note on timezones below)
sleep 2
STOP_AT=$(docker exec openncp_db date '+%Y-%m-%d %H:%M:%S')
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

### Timezone gotcha

`mysqlbinlog --stop-datetime` interprets the string in **the container's**
local timezone, not the host's. If the host is (say) CEST and the container
is UTC, a naive `STOP_AT=$(date …)` on the host gets interpreted as UTC
inside the container and the cutoff lands two hours in the wrong place — C
gets replayed.

The walkthrough above uses `docker exec openncp_db date …` to avoid this.
Always capture the cutoff from the container you're targeting.

### Known failure modes

- **A present twice, or PK conflict during replay** → binlog replay is
  double-applying writes that are already in the dump. Usually means
  `restore-incremental.sh` is starting earlier than the dump's embedded
  coordinate. Check `--after` is picking up the right position.
- **B missing after step 9** → the binlog containing B was not captured on
  the host (step 4 didn't run, or ran before the write), or `--after` is
  starting *after* B. Inspect `/opt/backup/mysql/binlog/` timestamps.
- **C present after step 9** → `--stop-datetime` mismatch. Most often
  timezone (see above); occasionally a too-tight cutoff where C's event
  shares a second with the cutoff (events with `ts >= stop-datetime` are
  excluded — so make sure C is at least 1s after the cutoff).

### Continuity check

`restore-incremental.sh` refuses to replay if there are gaps in the binlog
sequence (e.g. `binlog.000050, binlog.000052` with `000051` missing), or if
the dump's start file is not on disk. Errors look like:

```
ERROR: Binlog sequence has 1 gap(s) in /opt/backup/mysql/binlog:
  missing: binlog.000051
```

or

```
ERROR: Required start binlog 'binlog.000048' is not in /opt/backup/mysql/binlog
       The binlog was either never backed up or has been purged by retention.
```

Pass `--allow-gaps` only if you deliberately want to skip a known-corrupt
file; accept the silent data loss that comes with it.

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
similar. The fact that `gunzip | mysql` returns non-zero is what `set -o pipefail`
in `common.sh` relies on.

**Fail:** exit 0 despite truncation → your pipeline is swallowing errors.

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

**Pass:** `AFTER == FULL_BACKUP_RETAIN` (default 7). The oldest files were pruned.
**Fail:** `AFTER > FULL_BACKUP_RETAIN` → retention is broken.

---

## Cleanup after all tests

```bash
docker rm -f pitr-test corrupt-test backup-test-mysql 2>/dev/null
# Optional: wipe test binlogs/dumps accumulated during testing
# rm -rf /opt/backup/mysql/full/*fake*
```

---

## Reporting

Record for each test: pass/fail, duration, and any unexpected output.
If test 3 (PITR) fails with A=2 or PK conflicts, **that's the review's
issue #1 reproducing live** — fix `full-backup.sh` to include
`--source-data=2` before trusting the incremental path in production.
