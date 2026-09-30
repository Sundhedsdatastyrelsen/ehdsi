#!/usr/bin/env bash
# Settings for host-updates-exporter. The same for every host; the host
# itself is reached through the bind mounts in docker-compose.yml.

# Where the host's root is mounted (dpkg status, /run, os-release, hostname).
# /etc/apt, /usr/share/keyrings and /etc/machine-id are mounted at their own
# paths instead, because apt sources reference keyrings by absolute path.
HOST_ROOT="${HOST_UPDATES_HOST_ROOT:-/host}"

# Metrics files served on /metrics by serve.py.
METRICS_DIR="${HOST_UPDATES_METRICS_DIR:-/app/metrics}"

# Remembers when each pending package was first seen pending, and the last
# good endoflife.date/GitHub responses. A volume, so it survives upgrades.
STATE_DIR="${HOST_UPDATES_STATE_DIR:-/var/lib/host-updates}"

DOCKER_SOCKET="${HOST_UPDATES_DOCKER_SOCKET-$HOST_ROOT/run/docker.sock}"
DOCKER_API="${HOST_UPDATES_DOCKER_API:-http://docker}"

# ── collect.sh ──
# apt origin labels (the `Label:` of a repo's Release file, as printed by
# `apt-get --simulate dist-upgrade`) of the repositories we added ourselves
# and keep up to date (the ops repos' update-packages.sh). Packages from every
# other origin (the Ubuntu archive, or the mirror the hosting provider points
# it at) are patched by the hosting provider and only reported on.
OWN_ORIGINS=("Docker CE")

# ── eol.sh ──
# endoflife.date product (https://endoflife.date) of each third-party image,
# keyed by image repository without tag. Gives end-of-life dates per release
# cycle plus the latest version.
declare -A EOL_PRODUCTS=(
    [grafana/grafana]=grafana
    [grafana/loki]=grafana-loki
    [prom/prometheus]=prometheus
    [traefik]=traefik
    [caddy]=caddy
    [mysql]=mysql
    [memcached]=memcached
    [hashicorp/vault]=hashicorp-vault
)
# Images endoflife.date doesn't cover: GitHub repository whose latest release
# is reported instead (no end-of-life date).
declare -A GITHUB_RELEASES=(
    [grafana/alloy]=grafana/alloy
    [grafana/mimir]=grafana/mimir
    [grafana/tempo]=grafana/tempo
    [prom/node-exporter]=prometheus/node_exporter
)
# Images we build ourselves; their tags are bumped by the ops repos'
# image-tag pipelines, so they are not reported.
OWN_IMAGE_PREFIXES=("eppsregistry.azurecr.io/" "ghcr.io/sundhedsdatastyrelsen/")
