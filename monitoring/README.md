# Observability stack

This directory contains a Docker Compose stack with auxiliary services for monitoring and observability.

## [Grafana](https://grafana.com/)

Grafana provides a web UI for data exploration and dashboards.

Access is restricted to localhost; use port forwarding (e.g., `ssh -L 3000:localhost:3000 <host>`) to access remotely.

## Log aggregation

Centralized tools for collecting and searching logs from all services.

### [Loki](https://grafana.com/oss/loki/)

Loki is a logging backend which only indexes metadata.
Chosen over Elasticsearch (which also indexes log content) for its lower resource cost and operational simplicity.

### [Promtail](https://grafana.com/docs/loki/latest/clients/promtail/)

Promtail collects logs from Docker containers and forwards them to Loki.
Alternatives considered:
 - Application-side log push instead of scraping. Requires configuration of each service.
 - [Grafana Alloy](https://grafana.com/oss/alloy/). Promtail seemed simpler to setup, but we could migrate in the future.

## Traces

Tools for collecting and visualizing distributed traces.

### [Tempo](https://grafana.com/oss/tempo/)

Tempo is a tracing backend. Selected over Jaeger because of lower resource usage and simpler configuration.

The services push tracing data to Tempo. We use the OpenTelemetry Java Agent to instrument the services.

## Metrics

Tools for collecting and storing metrics data.

### [Prometheus](https://prometheus.io/)

Prometheus is the industry standard metrics backend, scraping data from configured sources at regular intervals.

The services should expose a metrics endpoint for Prometheus to scrape.
We use the OpenTelemetry Java Agent to provide metrics for the Java services.

### [Node Exporter](https://prometheus.io/docs/guides/node-exporter/)

Node Exporter exposes system-level metrics from the host VM for Prometheus to collect.

## Profiling

Tools for application performance profiling and flamegraphs.

### [Pyroscope](https://pyroscope.io/)

Pyroscope is a backend for collecting and visualizing profiling data.

We are only interested in profiling our own code, so we only enable profiling on country-a-service.
Here we use a Pyroscope extension to the OpenTelemetry Java Agent to push profiling data to Pyroscope.
