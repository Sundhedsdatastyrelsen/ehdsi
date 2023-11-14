#!/usr/bin/env bash
## Custom entrypoint for mariadb container

set -o errexit
set -o nounset
set -o pipefail

# Get the username from the secret file
DB_USERNAME=$(cat /run/secrets/db_username)

# Copy the init scripts to final location
cp /docker-entrypoint-initdb.d-original/* /docker-entrypoint-initdb.d/

# Replace placeholders in the init scripts
find /docker-entrypoint-initdb.d -type f -name "*.sql" -print0 \
    | xargs -0 sed -i "s/{{DB_USERNAME}}/$DB_USERNAME/g"

# Start MariaDB
docker-entrypoint.sh mariadbd
