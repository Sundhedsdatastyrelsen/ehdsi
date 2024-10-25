#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail


export DB_USER; DB_USER=$(<"${DB_USER_FILE}")
export DB_PASSWORD; DB_PASSWORD=$(<"${DB_PASSWORD_FILE}")

# Execute the original command
exec "$@"
