"""Pending package updates on the host.

apt runs with the host's configuration (its /etc/apt is mounted over ours)
and the host's installed packages, but keeps its own package lists."""

import json
import logging
import os
import pwd
import re
import time
from dataclasses import dataclass

import apt
import apt_pkg
import config
import host
from gauges import gauge, single_gauge

log = logging.getLogger(__name__)

DPKG_STATUS = config.HOST_ROOT / "var/lib/dpkg/status"
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


def pending_packages():
    """Refreshes the package lists, then returns (pending packages, whether the
    refresh succeeded). The list is None when no package lists were ever
    fetched: with nothing to compare against, apt would report "all up to
    date"."""
    config.load()
    apt_pkg.config.set("Dir::State::status", str(DPKG_STATUS))
    apt_pkg.config.set("Dir::State::Lists", str(APT_LISTS_DIR))
    for hook in HOST_APT_HOOKS:
        apt_pkg.config.clear(hook)
    # Downloads run as whoever runs the exporter; switching to the _apt
    # sandbox user would need capabilities the container doesn't have.
    apt_pkg.config.set("APT::Sandbox::User", pwd.getpwuid(os.getuid()).pw_name)
    (APT_LISTS_DIR / "partial").mkdir(parents=True, exist_ok=True)

    cache = apt.Cache(memonly=True)
    refresh_ok = True
    try:
        cache.update(raise_on_error=True)
    # LockFailedException: another refresh is running, e.g. pending.py's
    # beside the scheduled check.
    except (apt.cache.FetchFailedException, apt.cache.LockFailedException) as error:
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


KERNEL_PACKAGE = re.compile(r"linux-image-(?:unsigned-)?(\d.*)")


def running_kernel():
    # The container runs on the host's kernel.
    return os.uname().release


def installed_kernels():
    """(kernel release, package) of each kernel installed on the host."""
    kernels = []
    with apt_pkg.TagFile(str(DPKG_STATUS)) as status:
        for section in status:
            match = KERNEL_PACKAGE.fullmatch(section.get("Package", ""))
            if match and section.get("Status") == "install ok installed":
                kernels.append((match.group(1), section["Package"]))
    return kernels


def installed_at(package):
    """When dpkg last wrote the package's file list, i.e. roughly when it was
    installed; dpkg's status file if there is no list."""
    files_list = config.HOST_ROOT / f"var/lib/dpkg/info/{package}.list"
    return (files_list if files_list.exists() else DPKG_STATUS).stat().st_mtime


def reboot_required():
    """Returns (since when, the packages asking for it), or None. A reboot is
    required when a newer kernel is installed than the one running. The
    host's /run/reboot-required isn't visible in the container, so the rarer
    reboots asked for by libc6, dbus and the like are missed."""
    running = running_kernel()
    newer = sorted(package for release, package in installed_kernels()
                   if apt_pkg.version_compare(release, running) > 0)
    if not newer:
        return None
    return min(installed_at(package) for package in newer), newer


def boot_time():
    # /proc is the host's kernel's, even inside the container.
    with open("/proc/stat") as stat:
        for line in stat:
            if line.startswith("btime "):
                return int(line.split()[1])


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
    required = single_gauge("host_updates_reboot_required", "1 if a newer kernel is installed than the one running.",
                            hostname, 1 if reboot else 0)
    if not reboot:
        return [required]
    since, packages = reboot
    required_since = single_gauge("host_updates_reboot_required_since_timestamp_seconds",
                                  "When the first of those kernels was installed.", hostname, since)
    asking = gauge("host_updates_reboot_required_package", "Newer kernels installed than the one running, which take effect on reboot.", ["package"])
    for package in packages:
        asking.add_metric([hostname, package], 1)
    return [required, required_since, asking]


def host_metrics(hostname, refresh_ok, now):
    os_info = gauge("host_updates_os_info", "Operating system of the host.", ["pretty_name", "kernel"])
    os_info.add_metric([hostname, host.os_release().get("PRETTY_NAME", "unknown"), running_kernel()], 1)
    return [
        os_info,
        single_gauge("host_updates_boot_timestamp_seconds", "When the host last booted.", hostname, boot_time()),
        single_gauge("host_updates_dpkg_changed_timestamp_seconds",
                     "When packages were last installed, upgraded or removed (by anyone).",
                     hostname, DPKG_STATUS.stat().st_mtime),
        single_gauge("host_updates_refresh_success", "1 if the last package list refresh fetched every source.",
                     hostname, 1 if refresh_ok else 0),
        single_gauge("host_updates_packages_last_run_timestamp_seconds", "When the package check last ran.",
                     hostname, now),
    ]


def check():
    """Runs the check and returns its metrics."""
    now = time.time()
    hostname = host.name()
    pending, refresh_ok = pending_packages()
    reboot = reboot_required()

    metrics = []
    if pending is None:
        log.info("Pending: unknown, no package lists; reboot required: %s", bool(reboot))
    else:
        previous = json.loads(PENDING_SINCE_FILE.read_text()) if PENDING_SINCE_FILE.exists() else {}
        pending_since = update_pending_since(previous, pending, now)
        PENDING_SINCE_FILE.parent.mkdir(parents=True, exist_ok=True)
        # Replaced, not rewritten: a half-written file would fail every later run.
        temporary = PENDING_SINCE_FILE.with_suffix(".tmp")
        temporary.write_text(json.dumps(pending_since))
        os.replace(temporary, PENDING_SINCE_FILE)
        metrics += pending_metrics(hostname, pending, pending_since)
        log.info("Pending: %d hosting, %d ours; reboot required: %s",
                 sum(p.owner == "hosting" for p in pending), sum(p.owner == "ours" for p in pending), bool(reboot))

    return metrics + reboot_metrics(hostname, reboot) + host_metrics(hostname, refresh_ok, now)
