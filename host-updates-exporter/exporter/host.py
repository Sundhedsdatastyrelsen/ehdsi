"""Which host this is, read through the bind mounts, and the gauge every
metric is built on."""

from prometheus_client.core import GaugeMetricFamily

import config


def name():
    return (config.HOST_ROOT / "etc/hostname").read_text().strip()


def os_release():
    """The host's /etc/os-release as a dict, e.g. {"VERSION_ID": "24.04", ...}."""
    lines = (config.HOST_ROOT / "etc/os-release").read_text().splitlines()
    return {key: value.strip('"') for key, value in (line.split("=", 1) for line in lines if "=" in line)}


def gauge(name, documentation, labels=()):
    """A gauge whose first label is the host's name, as on every metric here."""
    return GaugeMetricFamily(name, documentation, labels=["hostname", *labels])


def single_gauge(name, documentation, hostname, value):
    """A gauge with just the host's one value."""
    metric = gauge(name, documentation)
    metric.add_metric([hostname], value)
    return metric
