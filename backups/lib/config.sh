#!/usr/bin/env bash
# Per-repo backup configuration. Edit this file when porting these scripts
# to a new repository; everything else under lib/ should stay untouched.

BACKUP_ROOT="${EHDSI_BACKUP_DIR:-/opt/backup}"

# MySQL
MYSQL_CONTAINER="${MYSQL_CONTAINER:-openncp_db}"
MYSQL_DATABASES=(ehealth_properties ehealth_atna ehealth_ltrdb ehealth_eadc)

# $MYSQL_ROOT_PASSWORD wins if set; otherwise this file is read with
# whitespace stripped.
MYSQL_PASSWORD_FILE="${MYSQL_PASSWORD_FILE:-$REPO_ROOT/NCP/db_root_password.txt}"

# In the official mysql:9 image mysqlbinlog ships under /usr/libexec/mysqlsh/
# and is not on $PATH, so the bare name will not resolve under docker exec.
MYSQLBINLOG_PATH="${MYSQLBINLOG_PATH:-/usr/libexec/mysqlsh/mysqlbinlog}"

MYSQL_DATA_DIR_IN_CONTAINER="${MYSQL_DATA_DIR_IN_CONTAINER:-/var/lib/mysql}"

FULL_BACKUP_RETAIN="${FULL_BACKUP_RETAIN:-7}"
BINLOG_RETAIN_DAYS="${BINLOG_RETAIN_DAYS:-14}"

# SQLite
SQLITE_CONTAINER="${SQLITE_CONTAINER:-national-connector}"
SQLITE_DATABASES=(undo-db.sqlite local-lms-db.sqlite job-queue.sqlite)
SQLITE_DATA_DIR="${SQLITE_DATA_DIR:-$REPO_ROOT/national-connector/data}"

# Restore stops via compose if the file/service exists, falling back to
# `docker stop $SQLITE_CONTAINER`. Set SQLITE_COMPOSE_FILE='' to skip compose.
SQLITE_COMPOSE_FILE="${SQLITE_COMPOSE_FILE:-$REPO_ROOT/national-connector/docker-compose.yml}"
SQLITE_COMPOSE_SERVICE="${SQLITE_COMPOSE_SERVICE:-national-connector}"

# UID:GID applied to restored SQLite files. Empty disables chown.
SQLITE_DATA_OWNER="${SQLITE_DATA_OWNER:-10001:10001}"

SQLITE_BACKUP_RETAIN="${SQLITE_BACKUP_RETAIN:-7}"

# Legacy aliases — only this repo's test scripts read these.
NCP_DIR="$REPO_ROOT/NCP"
NC_DIR="${NC_DIR:-$REPO_ROOT/national-connector}"
NC_CONTAINER="$SQLITE_CONTAINER"
