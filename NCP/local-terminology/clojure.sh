#!/usr/bin/env bash

## Running clojure using docker.
## This is mostly equivalent to running the "clojure" CLI command locally,
## if that is installed.  The command runs in the epps network to be able to
## reach the database instance.

set -o errexit
set -o nounset
set -o pipefail

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
CLOJURE_DOCKER_IMAGE="clojure:tools-deps-1.11.1.1429-bullseye-slim"

if ! command -v docker &> /dev/null
then
  echo "Docker is not installed. Please install it to continue."
  exit 1
fi

# Check that Docker daemon is running
if ! docker info > /dev/null 2>&1
then
  echo "Docker daemon is not running. Please start it to continue."
  exit 1
fi

# Check if m2 cache volume exists, otherwise create it
M2_CACHE_VOLUME="$(basename "$SCRIPT_DIR")-m2-cache"
if ! docker volume inspect "$M2_CACHE_VOLUME" > /dev/null 2>&1; then
  >&2 echo "Creating Docker volume for m2 cache: $M2_CACHE_VOLUME"
  docker volume create "$M2_CACHE_VOLUME" > /dev/null >&2
fi

clojure() {
    docker run \
           -it \
           --rm \
           --volume "$SCRIPT_DIR":"$SCRIPT_DIR" \
           --volume "$M2_CACHE_VOLUME":"/root/.m2/repository" \
           --volume "$SCRIPT_DIR/tmp":"/tmp" \
           --workdir "$SCRIPT_DIR" \
           --env-file "$SCRIPT_DIR/.env" \
           --network epps \
           --entrypoint clojure \
           "$CLOJURE_DOCKER_IMAGE" \
           "$@"
}

clojure "$@"
