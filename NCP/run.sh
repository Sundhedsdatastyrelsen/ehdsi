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

  for file in "$env_default_dir"/{.,}*; do
    if [ -f "$file" ]; then
      local filename;
      filename=$(basename "$file")
      local target_file="$SCRIPT_DIR/$filename"
      if [ ! -f "$target_file" ]; then
        echo "Initializing $filename..."
        cp "$file" "$target_file"
      fi
    fi
  done
}

# These directories will be mounted on the containers, and needs to be writable
# by the container user.
ensure_evidence_dirs_writable() {
  local evidence_dir="$SCRIPT_DIR/evidence"
  chmod go+w "$evidence_dir/obligations"
  chmod go+w "$evidence_dir/validation"
}

init() {
  initialize_secrets
  ensure_evidence_dirs_writable
}

# Parse command-line options
if [[ ! $# -gt 0 ]]; then
  error_exit "$USAGE"
else
  case "$1" in
    init)
      init
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
