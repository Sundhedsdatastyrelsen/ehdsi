#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

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
        echo "  grant-db          Grant all privileges to the database user (requires the db to run)"
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
  local env_default_dir="$SCRIPT_DIR/env_default"
  
  if [ ! -d "$env_default_dir" ]; then
    error_exit "env_default directory not found. Please ensure it exists in $SCRIPT_DIR"
  fi

  for file in "$env_default_dir"/*; do
    if [ -f "$file" ]; then
      local filename=$(basename "$file")
      local target_file="$SCRIPT_DIR/$filename"
      
      if [ ! -f "$target_file" ]; then
        echo "Initializing $filename..."
        cp "$file" "$target_file"
      fi
    fi
  done
}

init() {
  initialize_secrets
}

update_keystore() {
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
}

grant_all_privileges() {
  docker exec openncp_db mysql -u root -p$(<db_root_password.txt) -e "GRANT ALL PRIVILEGES ON *.* TO '$(<db_username.txt)'@'%';"
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
      update_keystore
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
      update_keystore
      ;;
    grant-db)
      grant_all_privileges
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
