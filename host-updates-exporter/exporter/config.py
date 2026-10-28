"""Settings, and the built-in tables: which apt repositories and images are
ours, and where each third-party image is looked up. A host's config.toml
adds to the tables (see load)."""

import logging
import os
import tomllib
from pathlib import Path

log = logging.getLogger(__name__)

# The host files read directly (dpkg status and file lists, os-release,
# hostname) are mounted read-only under this directory, e.g. the host's
# /etc/hostname at /host/etc/hostname; see docker-compose.yml.
# The host's apt configuration is mounted at its own paths instead, because
# apt sources name their keyrings by absolute path.
HOST_ROOT = Path(os.environ.get("HOST_ROOT", "/host"))

# On a volume, so it survives upgrading this image.
STATE_DIR = Path(os.environ.get("STATE_DIR", "/var/lib/host-updates"))

# Mounted from the ops repo. The directory is mounted rather than the file, so
# the exporter sees a new version when the file is replaced.
CONFIG_FILE = Path(os.environ.get("CONFIG_FILE", "/etc/host-updates/config.toml"))

PORT = 9090
PACKAGES_CHECK_HOURS = 6
SOFTWARE_CHECK_HOURS = 24

# The proxy in docker-proxy/haproxy.cfg: the exporter never gets the Docker
# socket itself.
DOCKER_API = "host-updates-docker-proxy:2375"
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
    """Adds the config file, if there is one, to the defaults above (see the
    README). A broken file leaves the tables as the last valid one did, so a
    typo doesn't quietly drop the host's own images and apt repositories.

    Replaces the module's tables rather than changing them, so read them as
    config.ENDOFLIFE_PRODUCTS etc., never import them by name."""
    global OWN_APT_ORIGINS, ENDOFLIFE_PRODUCTS, GITHUB_REPOSITORIES, OWN_IMAGE_PREFIXES
    origins, products, repositories, prefixes = DEFAULTS
    if CONFIG_FILE.exists():
        try:
            added = parse(CONFIG_FILE.read_text())
        except (OSError, ValueError) as error:  # ValueError includes TOMLDecodeError
            log.error("Ignoring %s, keeping the last valid one: %s", CONFIG_FILE, error)
            return
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
