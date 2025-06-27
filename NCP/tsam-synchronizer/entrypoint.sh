#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

export DB_USER; DB_USER=$(</run/secrets/db_username)
export DB_PASSWORD; DB_PASSWORD=$(</run/secrets/db_password)
export TSAM_SYNC_USERNAME; TSAM_SYNC_USERNAME=$(</run/secrets/cts_username)
export TSAM_SYNC_PASSWORD; TSAM_SYNC_PASSWORD=$(</run/secrets/cts_password)

exec "$@"
