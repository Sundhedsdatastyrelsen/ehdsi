"""Pending package updates on the host.

apt runs with the host's configuration (its /etc/apt is mounted over ours)
and the host's installed packages, but keeps its own package lists."""

import json
import logging
import os
import time
from dataclasses import dataclass

import apt
import apt_pkg
from prometheus_client.core import GaugeMetricFamily

import config

log = logging.getLogger(__name__)

APT_LISTS_DIR = config.STATE_DIR / "apt-lists"
PENDING_SINCE_FILE = config.STATE_DIR / "pending-since.json"

# Hooks in the host's apt.conf.d that call host programs (update-notifier,
# command-not-found, the Ubuntu Pro hook) which don't exist in this image.
HOST_APT_HOOKS = [
    "APT::Update::Pre-Invoke",
    "APT::Update::Post-Invoke",
    "APT::Update::Post-Invoke-Success",
    "APT::Update::Post-Invoke-Stats",
]


@dataclass
class PendingPackage:
    name: str
    installed_version: str  # empty for a package the upgrade newly installs, e.g. a new kernel
    candidate_version: str
    origin: str  # the apt label of the repository offering it, e.g. "Ubuntu"
    security: bool
    owner: str  # "ours" or "hosting"


def classify(name, installed_version, candidate_version, origins):
    """origins: (label, suite) of each repository offering the candidate."""
    labels = [label for label, _ in origins]
    return PendingPackage(
        name=name,
        installed_version=installed_version,
        candidate_version=candidate_version,
        origin=labels[0] if labels else "",
        security=any(suite.endswith("-security") for _, suite in origins),
        owner="ours" if config.OWN_APT_ORIGINS.intersection(labels) else "hosting",
    )


def pending_packages(refresh=True):
    """Returns (pending packages, whether the refresh succeeded). The list is
    None when no package lists were ever fetched: with nothing to compare
    against, apt would report "all up to date"."""
    apt_pkg.config.set("Dir::State::status", str(config.HOST_ROOT / "var/lib/dpkg/status"))
    apt_pkg.config.set("Dir::State::Lists", str(APT_LISTS_DIR))
    for hook in HOST_APT_HOOKS:
        apt_pkg.config.clear(hook)
    (APT_LISTS_DIR / "partial").mkdir(parents=True, exist_ok=True)

    cache = apt.Cache(memonly=True)
    refresh_ok = True
    if refresh:
        try:
            cache.update(raise_on_error=True)
        except apt.cache.FetchFailedException as error:
            log.warning("Refreshing the package lists failed, using the ones we have: %s", error)
            refresh_ok = False
        cache.open()

    if not list(APT_LISTS_DIR.glob("*_Packages*")):
        return None, False

    cache.upgrade(dist_upgrade=True)
    pending = []
    for package in cache.get_changes():
        if package.marked_delete:
            continue
        candidate = package.candidate
        pending.append(classify(
            package.name,
            package.installed.version if package.installed else "",
            candidate.version,
            [(origin.label, origin.archive) for origin in candidate.origins],
        ))
    return pending, refresh_ok


def update_pending_since(previous, pending, now):
    """When each pending package was first seen pending. Keyed on the package
    only, so a newer candidate arriving doesn't reset its clock."""
    return {package.name: previous.get(package.name, now) for package in pending}


def reboot_required():
    """Returns (since when, the packages asking for it), or None."""
    flag = config.HOST_ROOT / "run/reboot-required"
    if not flag.exists():
        return None
    packages_file = config.HOST_ROOT / "run/reboot-required.pkgs"
    packages = sorted(set(packages_file.read_text().split())) if packages_file.exists() else []
    return flag.stat().st_mtime, packages


def os_pretty_name():
    for line in (config.HOST_ROOT / "etc/os-release").read_text().splitlines():
        if line.startswith("PRETTY_NAME="):
            return line.split("=", 1)[1].strip('"')
    return "unknown"


def boot_time():
    # /proc is the host's kernel's, even inside the container.
    for line in open("/proc/stat"):
        if line.startswith("btime "):
            return int(line.split()[1])


def host_name():
    return (config.HOST_ROOT / "etc/hostname").read_text().strip()


def gauge(name, documentation, labels=()):
    return GaugeMetricFamily(name, documentation, labels=["hostname", *labels])


def pending_metrics(hostname, pending, pending_since):
    per_package = gauge(
        "host_updates_package_pending_since_timestamp_seconds",
        "When the package was first seen with an update pending; one series per pending package.",
        ["package", "current_version", "candidate_version", "origin", "owner", "security"])
    for p in pending:
        per_package.add_metric(
            [hostname, p.name, p.installed_version, p.candidate_version, p.origin, p.owner, str(p.security).lower()],
            pending_since[p.name])

    counts = gauge(
        "host_updates_pending_packages",
        "Number of packages with an update pending, by who patches them (owner) and whether it is a security update.",
        ["owner", "security"])
    for owner in ("hosting", "ours"):
        for security in (True, False):
            count = sum(1 for p in pending if p.owner == owner and p.security == security)
            counts.add_metric([hostname, owner, str(security).lower()], count)
    return [per_package, counts]


def reboot_metrics(hostname, reboot):
    required = gauge("host_updates_reboot_required", "1 if /var/run/reboot-required exists.")
    required.add_metric([hostname], 1 if reboot else 0)
    if not reboot:
        return [required]
    since, packages = reboot
    required_since = gauge("host_updates_reboot_required_since_timestamp_seconds", "When a reboot first became required.")
    required_since.add_metric([hostname], since)
    asking = gauge("host_updates_reboot_required_package", "Packages that asked for the pending reboot.", ["package"])
    for package in packages:
        asking.add_metric([hostname, package], 1)
    return [required, required_since, asking]


def host_metrics(hostname, refresh_ok, now):
    os_info = gauge("host_updates_os_info", "Operating system of the host.", ["pretty_name", "kernel"])
    os_info.add_metric([hostname, os_pretty_name(), os.uname().release], 1)
    booted = gauge("host_updates_boot_timestamp_seconds", "When the host last booted.")
    booted.add_metric([hostname], boot_time())
    dpkg_changed = gauge(
        "host_updates_dpkg_changed_timestamp_seconds",
        "When packages were last installed, upgraded or removed (by anyone).")
    dpkg_changed.add_metric([hostname], (config.HOST_ROOT / "var/lib/dpkg/status").stat().st_mtime)
    refreshed = gauge("host_updates_refresh_success", "1 if the last package list refresh fetched every source.")
    refreshed.add_metric([hostname], 1 if refresh_ok else 0)
    last_run = gauge("host_updates_last_run_timestamp_seconds", "When the package check last ran.")
    last_run.add_metric([hostname], now)
    return [os_info, booted, dpkg_changed, refreshed, last_run]


def check():
    """Runs the check and returns its metrics."""
    now = time.time()
    hostname = host_name()
    pending, refresh_ok = pending_packages()
    reboot = reboot_required()

    metrics = []
    if pending is None:
        log.info("Pending: unknown, no package lists; reboot required: %s", bool(reboot))
    else:
        previous = json.loads(PENDING_SINCE_FILE.read_text()) if PENDING_SINCE_FILE.exists() else {}
        pending_since = update_pending_since(previous, pending, now)
        PENDING_SINCE_FILE.parent.mkdir(parents=True, exist_ok=True)
        PENDING_SINCE_FILE.write_text(json.dumps(pending_since))
        metrics += pending_metrics(hostname, pending, pending_since)
        log.info("Pending: %d hosting, %d ours; reboot required: %s",
                 sum(p.owner == "hosting" for p in pending), sum(p.owner == "ours" for p in pending), bool(reboot))

    return metrics + reboot_metrics(hostname, reboot) + host_metrics(hostname, refresh_ok, now)
