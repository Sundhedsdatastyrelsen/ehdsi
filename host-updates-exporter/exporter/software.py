"""End of life and newer releases of the software on the host: the images of
its running containers, its Ubuntu release and its Docker Engine."""

import http.client
import json
import logging
import re
import time
import urllib.request
from dataclasses import dataclass, field
from datetime import date, datetime, timezone

import config
import host
from host import gauge, single_gauge

log = logging.getLogger(__name__)

RESPONSE_CACHE_DIR = config.STATE_DIR / "eol-cache"


@dataclass
class Software:
    kind: str  # "image", "os" or "runtime"
    name: str
    version: str  # empty when unknown
    version_source: str  # where the version was read: tag, env, label, host, or none
    image: str = ""
    containers: list = field(default_factory=list)
    product: str = ""  # on endoflife.date
    github: str = ""  # owner/repository whose latest release is reported, if there's no product
    cycle: str = ""
    end_of_life: date | None = None
    latest_in_cycle: str = ""
    latest: str = ""
    # Whether we could find out about it: found, untracked (no source configured),
    # version_unknown, failed (source unreachable) or cycle_not_found. Starts as
    # found; look_up changes it when it can't.
    lookup: str = "found"


# ── Versions ──

def leading_version(text):
    """"v3.7" -> "3.7", "9.7.2-1.el9" -> "9.7.2", "latest" -> ""."""
    match = re.match(r"v?(\d+(?:\.\d+)*)", text or "")
    return match.group(1) if match else ""


def refines(version, prefix):
    """True if version is prefix or a more specific version of it:
    9.7.2 refines 9 and 9.7, but not 9.70."""
    return prefix == "" or version == prefix or version.startswith(prefix + ".")


def split_image_ref(ref):
    """"docker.io/library/mysql:9" -> ("mysql", "9"); no tag means latest."""
    ref = ref.split("@")[0]
    repository, tag = ref, "latest"
    if ":" in ref.rsplit("/", 1)[-1]:
        repository, tag = ref.rsplit(":", 1)
    repository = repository.removeprefix("docker.io/").removeprefix("library/")
    return repository, tag


def running_version(repository, tag, image_config):
    """The version of the image actually running, as (version, source).

    Floating tags (mysql:9, hashicorp/vault) don't say which release was
    pulled, but many images record it in a <NAME>_VERSION env var or a version
    label. Those are only trusted when they agree with the tag: grafana/alloy's
    version label is the version of its Ubuntu base image."""
    tag_version = leading_version(tag)

    env_name = repository.rsplit("/", 1)[-1].upper().replace("-", "_") + "_VERSION"
    env = dict(entry.split("=", 1) for entry in image_config.get("Env") or [] if "=" in entry)
    labels = image_config.get("Labels") or {}
    label = labels.get("org.opencontainers.image.version") or labels.get("version")

    for source, candidate in (("env", env.get(env_name)), ("label", label)):
        version = leading_version(candidate)
        if version and refines(version, tag_version):
            return version, source
    if tag_version:
        return tag_version, "tag"
    return "", "none"


def update_available(software):
    """patch: a newer release in the same cycle. newer: only a newer cycle.
    unknown: e.g. a floating tag (loki:3.5) whose exact release we can't tell."""
    if not software.version or not software.latest:
        return "unknown"
    # Ubuntu point releases are just package updates, which packages.py reports.
    latest_in_cycle = "" if software.kind == "os" else software.latest_in_cycle
    if latest_in_cycle and software.version != latest_in_cycle:
        return "unknown" if refines(latest_in_cycle, software.version) else "patch"
    if software.version != software.latest:
        return "unknown" if refines(software.latest, software.version) else "newer"
    return "none"


# ── Looking up releases ──

def find_cycle(releases, version):
    """The endoflife.date release cycle a version belongs to: the most
    specific one it refines (grafana 12.0.2 -> "12.0", caddy 2.11.4 -> "2")."""
    matching = [release for release in releases if refines(version, release["name"])]
    return max(matching, key=lambda release: len(release["name"]), default=None)


def apply_endoflife(software, releases):
    cycle = find_cycle(releases, software.version)
    if cycle is None:
        software.lookup = "cycle_not_found"
        return
    software.cycle = cycle["name"]
    if cycle.get("eolFrom"):
        software.end_of_life = date.fromisoformat(cycle["eolFrom"])
    elif cycle.get("isEol"):
        software.end_of_life = date(1970, 1, 1)  # end of life, date unknown
    software.latest_in_cycle = (cycle.get("latest") or {}).get("name", "")
    newest_cycle = max(releases, key=lambda release: release.get("releaseDate") or "")
    software.latest = (newest_cycle.get("latest") or {}).get("name", "")


def fetch_json(url, cache_name):
    """GETs a URL, falling back to the response from the last successful run
    so that an outage doesn't blank the metrics. None if neither works."""
    cached = RESPONSE_CACHE_DIR / cache_name
    try:
        with urllib.request.urlopen(url, timeout=30) as response:
            body = response.read()
        data = json.loads(body)
        RESPONSE_CACHE_DIR.mkdir(parents=True, exist_ok=True)
        cached.write_bytes(body)
        return data
    except (OSError, ValueError) as error:  # ValueError: not JSON, e.g. a proxy's error page
        if not cached.exists():
            log.warning("%s unusable and nothing cached: %s", url, error)
            return None
        log.warning("%s unusable, using the cached response: %s", url, error)
        return json.loads(cached.read_bytes())


def look_up(software):
    if not software.version:
        software.lookup = "version_unknown"
    elif software.product:
        data = fetch_json(f"{config.ENDOFLIFE_API}/{software.product}", f"{software.product}.json")
        if data is None:
            software.lookup = "failed"
        else:
            apply_endoflife(software, data["result"]["releases"])
    else:
        data = fetch_json(f"{config.GITHUB_API}/{software.github}/releases/latest",
                          f"github-{software.github.replace('/', '_')}.json")
        # Tags like "v1.20.1" or "mimir-3.0.2"
        match = re.search(r"\d+(\.\d+)+", (data or {}).get("tag_name", ""))
        if match:
            software.latest = match.group(0)
        else:
            software.lookup = "failed"


# ── The host ──

def docker_get(path):
    connection = http.client.HTTPConnection(config.DOCKER_API, timeout=30)
    try:
        connection.request("GET", path)
        response = connection.getresponse()
        if response.status != 200:
            raise RuntimeError(f"Docker API {path}: HTTP {response.status}")
        return json.load(response)
    finally:
        connection.close()


def running_images():
    by_ref = {}
    for listed in docker_get("/containers/json"):
        container = docker_get(f"/containers/{listed['Id']}/json")
        # Config.Image is the reference the container was created from; the
        # list's Image turns into an image id once that tag is pulled anew.
        ref = container["Config"]["Image"]
        if ref.startswith(config.OWN_IMAGE_PREFIXES):
            continue
        if ref not in by_ref:
            repository, tag = split_image_ref(ref)
            image_config = docker_get(f"/images/{container['Image']}/json").get("Config") or {}
            version, source = running_version(repository, tag, image_config)
            product = config.ENDOFLIFE_PRODUCTS.get(repository, "")
            github = "" if product else config.GITHUB_REPOSITORIES.get(repository, "")
            by_ref[ref] = Software("image", repository, version, source, image=ref, product=product, github=github,
                                   lookup="found" if product or github else "untracked")
        by_ref[ref].containers.append(container["Name"].lstrip("/"))
    return list(by_ref.values())


def host_software():
    ubuntu = leading_version(host.os_release().get("VERSION_ID", ""))
    docker = leading_version(docker_get("/version").get("Version", ""))
    return [
        Software("os", "ubuntu", ubuntu, "host", product="ubuntu"),
        Software("runtime", "docker-engine", docker, "host", product="docker-engine"),
    ]


def metrics(hostname, software, now):
    info = gauge(
        "host_updates_software_info",
        "Software on the host (images of running containers, OS, Docker Engine) with its release cycle and latest "
        "versions. update: patch (newer release in the same cycle), newer (newer cycle only), none, unknown. "
        "lookup: found, untracked, version_unknown, failed, cycle_not_found.",
        ["kind", "name", "version", "image", "containers", "version_source", "product", "cycle", "latest_in_cycle",
         "latest", "update", "lookup"])
    end_of_life = gauge(
        "host_updates_software_eol_timestamp_seconds",
        "When the release cycle of the running version reaches end of life, per endoflife.date "
        "(0: end of life, date unknown).",
        ["kind", "name", "version", "cycle"])
    end_of_life_reported = set()
    for s in software:
        product = s.product or (f"github:{s.github}" if s.github else "")
        info.add_metric([hostname, s.kind, s.name, s.version, s.image, ",".join(s.containers), s.version_source,
                         product, s.cycle, s.latest_in_cycle, s.latest, update_available(s), s.lookup], 1)
        # The same image can run under two references (mysql:9, mysql:9.7).
        key = (s.kind, s.name, s.version, s.cycle)
        if s.end_of_life and key not in end_of_life_reported:
            end_of_life_reported.add(key)
            timestamp = datetime.combine(s.end_of_life, datetime.min.time(), timezone.utc).timestamp()
            end_of_life.add_metric([hostname, *key], timestamp)
    last_run = single_gauge("host_updates_software_last_run_timestamp_seconds", "When the end-of-life check last ran.",
                            hostname, now)
    return [info, end_of_life, last_run]


def check():
    """Runs the check and returns its metrics."""
    config.load()
    software = host_software() + running_images()
    for s in software:
        if s.lookup != "untracked":
            look_up(s)
    log.info("Checked %d pieces of software", len(software))
    return metrics(host.name(), software, time.time())
