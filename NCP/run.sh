#!/usr/bin/env bash

#Might be outdated since 8.0. TODO Needs updating
set -o errexit
set -o nounset
set -o pipefail

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

ENV_FILE_CONTENT="$(cat << EOF
MARIADB_HOST="mariadb"
MARIADB_PORT="3306"
OPENNCP_VERSION="7.1.0"
TLS_KEYSTORE_ALIAS=test-standard
SEAL_KEYSTORE_ALIAS=ncptestis
TLS_TRUSTSTORE_FILE=/opt/openncp-configuration/cert/sds-test-truststore.jks
TLS_KEYSTORE_FILE=/opt/openncp-configuration/cert/sds-test-keystore.jks
CTS_URL=https://webgate.training.ec.europa.eu/ehealth-term-server
EOF
)"

error_exit() {
  echo "$1" >&2
  exit 1
}

USAGE=$(echo "Usage: $0 [options]";
        echo "Options:";
        echo "  init              Initialize the project dir for running the NCP"
        echo "  up                Build and run the containers in the background"
        echo "  down              Stop the containers"
        echo "  tsam-sync         Synchronize terminology database with CTS"
        echo "  clean             Clean up the containers and volumes (wipes database)"
        echo "  logs              Follow the stdout logs of the containers"
        echo "  build             Builds all images"
        echo "  push              Push images to registry"
        echo "  update-keystore   Update keystore paths from .env and password files"
        echo "  -h, --help        Display this help message")

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
  initialize_file "$SCRIPT_DIR/tls_keystore_password.txt" "APQVk2dyWwCP0FzF7JiPfr8o"
  initialize_file "$SCRIPT_DIR/seal_keystore_password.txt" "ncptestis"
  initialize_file "$SCRIPT_DIR/tls_truststore_password.txt" "Byrx4vLoM3ZWn3PKiL7BZRUY"
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
      docker compose down --remove-orphans
      ;;
    clean)
      docker compose down --remove-orphans --volumes
      ;;
    up)
      init
      docker compose up --build --detach
      ;;
    tsam-sync)
      init
      docker compose run --build tsam-synchronizer
      ;;
    logs)
      docker compose logs --follow
      ;;
    build)
      docker compose --profile initialization build
      ;;
    push)
      docker compose --profile initialization push
      ;;
    update-keystore)
      # Requires xmlstartlet command.
      # Install using
      # sudo apt install xmlstarlet (on linux)
      init
      source "$SCRIPT_DIR/.env"
      keystorePassword=$(<tls_keystore_password.txt)
      truststorePassword=$(<tls_truststore_password.txt)
      xmlstarlet ed -L -u '/Configuration/SecureConnection/KeyStore' -v "$TLS_KEYSTORE_PATH" ./openncp-configuration/ATNA_resources/ArrConnections.xml
      xmlstarlet ed -L -u '/Configuration/SecureConnection/TrustStore' -v "$TLS_TRUSTSTORE_PATH" ./openncp-configuration/ATNA_resources/ArrConnections.xml
      xmlstarlet ed -L -u '/Configuration/SecureConnection/KeyPass' -v "$keystorePassword" ./openncp-configuration/ATNA_resources/ArrConnections.xml
      xmlstarlet ed -L -u '/Configuration/SecureConnection/TrustPass' -v "$truststorePassword" ./openncp-configuration/ATNA_resources/ArrConnections.xml
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
