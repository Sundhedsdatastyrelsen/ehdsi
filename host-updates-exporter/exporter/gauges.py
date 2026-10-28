"""Builds the gauges every metric here is: each one's first label is the
host's name."""

from prometheus_client.core import GaugeMetricFamily


def gauge(name, documentation, labels=()):
    return GaugeMetricFamily(name, documentation, labels=["hostname", *labels])


def single_gauge(name, documentation, hostname, value):
    """A gauge with just the host's one value."""
    metric = gauge(name, documentation)
    metric.add_metric([hostname], value)
    return metric
