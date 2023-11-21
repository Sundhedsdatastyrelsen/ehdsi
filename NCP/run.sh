#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

ENV_FILE_CONTENT="$(cat << EOF
RUN_TSAM="true"
LOAD_PROPERTIES="true"
MARIADB_HOST="mariadb"
MARIADB_PORT="3306"
OPENNCP_VERSION="7.0.0"
EOF
)"

error_exit() {
  echo "$1" >&2
  exit 1
}

USAGE=$(echo "Usage: $0 [options]";
        echo "Options:";
        echo "  init          Initialize the project dir for running the NCP"
        echo "  up            Build and run the containers"
        echo "  down          Stop and clean up the containers (wipes database)"
        echo "  -h, --help    Display this help message")

initialize_file() {
  local file_name="$1"
  local content="$2"
  if [ ! -f "$file_name" ]; then
    echo "Initializing $file_name..."
    echo "$content" > "$file_name"
  fi
}

initialize_secrets() {
  initialize_file "$SCRIPT_DIR/db_username.txt" "ncp"
  initialize_file "$SCRIPT_DIR/db_password.txt" "myPassword"
  initialize_file "$SCRIPT_DIR/db_root_password.txt" "s3cret"
  initialize_file "$SCRIPT_DIR/cts_username.txt" "ncpeh@dk"
  initialize_file "$SCRIPT_DIR/cts_password.txt" "<password for the central terminology service - ask someone>"
  initialize_file "$SCRIPT_DIR/tls_password.txt" "ncptestis"
  initialize_file "$SCRIPT_DIR/truststore_password.txt" "changeit"
  initialize_file "$SCRIPT_DIR/.env" "${ENV_FILE_CONTENT}"
}

init() {
  initialize_secrets
}

# Parse command-line options
if [[ ! $# -gt 0 ]]; then
  error_exit "$USAGE"
else
  case "$1" in
    init)
      initialize_secrets
      ;;
    down)
      docker compose down --remove-orphans --volumes
      ;;
    up)
      init
      docker compose up --build
      ;;
    -h|--help)
      echo "$USAGE";
      ;;
    # Add more options and their handling here
    *)
      error_exit "Unknown option: $1

$USAGE"
      ;;
  esac
fi
