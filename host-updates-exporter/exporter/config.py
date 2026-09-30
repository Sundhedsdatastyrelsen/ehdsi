"""Settings. The same on every host; the host itself is reached through the
bind mounts in docker-compose.yml."""

import os
from pathlib import Path

# The host's root filesystem, mounted read-only. The host's /etc/apt,
# /usr/share/keyrings and /etc/machine-id are mounted at their own paths
# instead, because apt sources name their keyrings by absolute path.
HOST_ROOT = Path(os.environ.get("HOST_ROOT", "/host"))

# A volume: survives upgrades of this image.
STATE_DIR = Path(os.environ.get("STATE_DIR", "/var/lib/host-updates"))

PORT = int(os.environ.get("PORT", "9090"))
PACKAGES_CHECK_HOURS = float(os.environ.get("PACKAGES_CHECK_HOURS", "6"))
SOFTWARE_CHECK_HOURS = float(os.environ.get("SOFTWARE_CHECK_HOURS", "24"))

DOCKER_SOCKET = HOST_ROOT / "run/docker.sock"
ENDOFLIFE_API = "https://endoflife.date/api/v1/products"
GITHUB_API = "https://api.github.com/repos"

# Packages from these apt repositories (by the `Label:` of their Release file)
# are ours to upgrade, with update-packages.sh in the ops repos. Everything
# else, i.e. the Ubuntu archive, is patched by the hosting provider.
OWN_APT_ORIGINS = {"Docker CE"}

# endoflife.date product for each third-party image, by image repository.
ENDOFLIFE_PRODUCTS = {
    "grafana/grafana": "grafana",
    "grafana/loki": "grafana-loki",
    "prom/prometheus": "prometheus",
    "traefik": "traefik",
    "caddy": "caddy",
    "mysql": "mysql",
    "memcached": "memcached",
    "hashicorp/vault": "hashicorp-vault",
}

# Images endoflife.date doesn't cover: the GitHub repository whose latest
# release is reported instead (without an end-of-life date).
GITHUB_REPOSITORIES = {
    "grafana/alloy": "grafana/alloy",
    "grafana/mimir": "grafana/mimir",
    "grafana/tempo": "grafana/tempo",
    "prom/node-exporter": "prometheus/node_exporter",
}

# Images we build ourselves. The ops repos' pipelines bump their tags, so
# they are not reported.
OWN_IMAGE_PREFIXES = ("eppsregistry.azurecr.io/", "ghcr.io/sundhedsdatastyrelsen/")
