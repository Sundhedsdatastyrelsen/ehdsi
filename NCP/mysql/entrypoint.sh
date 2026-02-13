#!/usr/bin/env bash
## Custom entrypoint for mysql container

set -o errexit
set -o nounset
set -o pipefail

# Get the username from the secret file
DB_USERNAME=$(cat /run/secrets/db_username)

# Copy the openncp init scripts to the final location
cp /docker-entrypoint-initdb.d-openncp/* /docker-entrypoint-initdb.d/
# Copy the DK init scripts to final location
cp /docker-entrypoint-initdb.d-dk/* /docker-entrypoint-initdb.d/

# Replace placeholders in the init scripts
find /docker-entrypoint-initdb.d -type f -name "*.sql" -print0 \
    | xargs -0 sed -i "s/{{DB_USERNAME}}/$DB_USERNAME/g"

# Start mysqld
docker-entrypoint.sh mysqld
