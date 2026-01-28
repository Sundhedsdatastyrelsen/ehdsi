# Observability stack

This directory contains a Docker Compose stack with auxiliary services for monitoring and observability.

## [Grafana](https://grafana.com/)

Grafana provides a web UI for data exploration and dashboards.

Access is restricted to localhost; use port forwarding (e.g., `ssh -L 3000:localhost:3000 <host>`) to access remotely.

## Telemetry pipeline

All telemetry (logs, metrics, traces, profiling) flows through Grafana Alloy.
Each service host runs an `alloy-edge` instance that accepts OTLP and forwards it to `alloy-hub`
in the monitoring stack. `alloy-hub` is the only exposed ingestion endpoint and it distributes
telemetry to the backend services.

### [Grafana Alloy](https://grafana.com/oss/alloy/)

Alloy handles OTLP ingestion, log scraping, metrics scraping, and routing to the backend services.
It replaces Promtail for log shipping and performs scraping for sources like node-exporter.

### [Loki](https://grafana.com/oss/loki/)

Loki is a logging backend which only indexes metadata.
Chosen over Elasticsearch (which also indexes log content) for its lower resource cost and operational simplicity.

### [Tempo](https://grafana.com/oss/tempo/)

Tempo is a tracing backend. Selected over Jaeger because of lower resource usage and simpler configuration.

Services push tracing data to Alloy via OTLP. We use the OpenTelemetry Java Agent to instrument the services.

### [Grafana Mimir](https://grafana.com/oss/mimir/)

Mimir is a Prometheus-compatible metrics backend that supports remote write.
Metrics are pushed to Alloy via OTLP and scraped by Alloy where needed.

### [Node Exporter](https://prometheus.io/docs/guides/node-exporter/)

Node Exporter exposes system-level metrics from the host VM for Alloy to scrape.

## Profiling

Tools for application performance profiling and flamegraphs.

### [Pyroscope](https://pyroscope.io/)

Pyroscope is a backend for collecting and visualizing profiling data.

We are only interested in profiling our own code, so we only enable profiling on national-connector.
Here we use a Pyroscope extension to the OpenTelemetry Java Agent to push profiling data to Pyroscope.
