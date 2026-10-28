"""Settings. The same on every host; the host itself is reached through the
bind mounts in docker-compose.yml. The tables below are defaults: a host's
config file adds to them (see load)."""

import logging
import os
import tomllib
from pathlib import Path

log = logging.getLogger(__name__)

# The host's root filesystem, mounted read-only. The host's /etc/apt,
# /usr/share/keyrings and /etc/machine-id are mounted at their own paths
# instead, because apt sources name their keyrings by absolute path.
HOST_ROOT = Path(os.environ.get("HOST_ROOT", "/host"))

# A volume: survives upgrades of this image.
STATE_DIR = Path(os.environ.get("STATE_DIR", "/var/lib/host-updates"))

# Mounted from the ops repo, as a directory so a replaced file is seen.
CONFIG_FILE = Path(os.environ.get("CONFIG_FILE", "/etc/host-updates/config.toml"))

PORT = int(os.environ.get("PORT", "9090"))
PACKAGES_CHECK_HOURS = float(os.environ.get("PACKAGES_CHECK_HOURS", "6"))
SOFTWARE_CHECK_HOURS = float(os.environ.get("SOFTWARE_CHECK_HOURS", "24"))

# A proxy that only lets through reading containers, images and the version
# (docker-proxy/haproxy.cfg): the exporter never gets the Docker socket itself.
DOCKER_API = os.environ.get("DOCKER_API", "host-updates-docker-proxy:2375")
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
    "haproxy": "haproxy",
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

DEFAULTS = (OWN_APT_ORIGINS, ENDOFLIFE_PRODUCTS, GITHUB_REPOSITORIES, OWN_IMAGE_PREFIXES)


def load():
    """Adds the config file, if there is one, to the defaults above. Runs
    before every check, so a changed file applies from the next one. A broken
    file is logged and ignored: the defaults keep working.

        own_apt_origins = ["..."]       # apt repository labels
        own_image_prefixes = ["..."]
        [endoflife_products]
        "image/repository" = "product"
        [github_repositories]
        "image/repository" = "owner/repository"
    """
    global OWN_APT_ORIGINS, ENDOFLIFE_PRODUCTS, GITHUB_REPOSITORIES, OWN_IMAGE_PREFIXES
    origins, products, repositories, prefixes = DEFAULTS
    if CONFIG_FILE.exists():
        try:
            added = parse(CONFIG_FILE.read_text())
        except (OSError, ValueError) as error:  # ValueError includes TOMLDecodeError
            log.error("Ignoring %s: %s", CONFIG_FILE, error)
        else:
            origins = origins | set(added.get("own_apt_origins", []))
            products = products | added.get("endoflife_products", {})
            repositories = repositories | added.get("github_repositories", {})
            prefixes = prefixes + tuple(added.get("own_image_prefixes", []))
    OWN_APT_ORIGINS, ENDOFLIFE_PRODUCTS, GITHUB_REPOSITORIES, OWN_IMAGE_PREFIXES = (
        origins, products, repositories, prefixes)


def parse(text):
    added = tomllib.loads(text)
    for key, value in added.items():
        if key in ("own_apt_origins", "own_image_prefixes"):
            valid = isinstance(value, list) and all(isinstance(v, str) for v in value)
        elif key in ("endoflife_products", "github_repositories"):
            valid = isinstance(value, dict) and all(isinstance(v, str) for v in value.values())
        else:
            raise ValueError(f"unknown setting {key!r}")
        if not valid:
            raise ValueError(f"{key!r} has the wrong type")
    return added
