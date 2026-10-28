"""Helpers for building gauges. Every metric this exporter serves is a gauge
whose first label is `hostname`."""

from prometheus_client.core import GaugeMetricFamily


def gauge(name, documentation, labels=()):
    return GaugeMetricFamily(name, documentation, labels=["hostname", *labels])


def single_gauge(name, documentation, hostname, value):
    """A gauge with a single sample, labelled only with the hostname."""
    metric = gauge(name, documentation)
    metric.add_metric([hostname], value)
    return metric
