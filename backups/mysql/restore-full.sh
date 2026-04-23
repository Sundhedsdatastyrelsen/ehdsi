#!/usr/bin/env bash
# Restore a full MySQL backup from a mysqldump file

source "$(dirname "$0")/../lib/common.sh"

BACKUP_DIR="$BACKUP_ROOT/mysql/full"
AUTO_YES=false

# Parse arguments
BACKUP_FILE=""
while [[ $# -gt 0 ]]; do
    case "$1" in
        --yes) AUTO_YES=true; shift ;;
        *) BACKUP_FILE="$1"; shift ;;
    esac
done

# If no file specified, use the latest
if [[ -z "$BACKUP_FILE" ]]; then
    BACKUP_FILE=$(find "$BACKUP_DIR" -maxdepth 1 -name '*.sql.gz' | sort | tail -n 1)
    if [[ -z "$BACKUP_FILE" ]]; then
        log "ERROR: No backup files found in $BACKUP_DIR"
        exit 1
    fi
    log "Auto-selected latest backup: $BACKUP_FILE"
fi

if [[ ! -f "$BACKUP_FILE" ]]; then
    log "ERROR: Backup file not found: $BACKUP_FILE"
    exit 1
fi

assert_container_running "$MYSQL_CONTAINER"

# Confirmation prompt
if [[ "$AUTO_YES" != true ]]; then
    echo "This will OVERWRITE the following databases:"
    printf "  - %s\n" "${MYSQL_DATABASES[@]}"
    echo ""
    echo "From backup: $BACKUP_FILE"
    read -r -p "Continue? [y/N] " response
    if [[ ! "$response" =~ ^[Yy]$ ]]; then
        log "Restore cancelled by user"
        exit 0
    fi
fi

ROOT_PASSWORD=$(read_mysql_password)

log "Starting restore from: $BACKUP_FILE"

# Drop target databases so stale tables not present in the dump don't survive.
# The dump recreates each database via CREATE DATABASE IF NOT EXISTS.
for db in "${MYSQL_DATABASES[@]}"; do
    docker exec "$MYSQL_CONTAINER" \
        mysql -u root -p"$ROOT_PASSWORD" \
        -e "DROP DATABASE IF EXISTS \`$db\`;" 2>/dev/null
done

gunzip -c "$BACKUP_FILE" | \
    docker exec -i "$MYSQL_CONTAINER" \
        mysql -u root -p"$ROOT_PASSWORD"

# Verify restoration
log "Verifying restored databases..."
docker exec "$MYSQL_CONTAINER" \
    mysql -u root -p"$ROOT_PASSWORD" \
    --batch --skip-column-names \
    -e "SELECT TABLE_SCHEMA, COUNT(*) AS table_count
        FROM information_schema.TABLES
        WHERE TABLE_SCHEMA IN ($(printf "'%s'," "${MYSQL_DATABASES[@]}" | sed 's/,$//'))
        GROUP BY TABLE_SCHEMA
        ORDER BY TABLE_SCHEMA;"

log "MySQL restore completed successfully"
