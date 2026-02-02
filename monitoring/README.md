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

# Alloy Configuration
This section details the configuration of Grafana Alloy, to ease understanding when reading the configuration

The edge is deliberately simple -- receive, batch, forward. All routing, processing, and backend integration happens at the hub. This keeps the service hosts lightweight and means you only need to reconfigure the hub if you change backends.

## Alloy Edge Configuration

### Architecture Overview

The alloy-edge is a lightweight forwarder on each service host. Collects telemetry from local services and ships it to alloy-hub. No local routing or storage.

### Alloy Config: `NCP/alloy/config.alloy`

#### 1. OTLP Receiver 

Same as the hub -- accepts all three signal types on gRPC `:4317` and HTTP `:4318`. Local Java services (OpenNCP Tomcat nodes, OpenATNA, etc.) send their telemetry here.

#### 2. Batch Processor

Unlike the hub, the edge sends all signals to a single OTLP exporter:

```
metrics -> otelcol.exporter.otlp.default
logs    -> otelcol.exporter.otlp.default
traces  -> otelcol.exporter.otlp.default
```

Everything forwarded upstream.

#### 3. OTLP Exporter

```
otelcol.exporter.otlp "default" {
  client {
    endpoint = sys.env("OTLP_SERVER_ENDPOINT")
    tls { insecure = true }
  }
}
```

Destination comes from `OTLP_SERVER_ENDPOINT` env var (set to `http://host.docker.internal:4317` in docker-compose), pointing at alloy-hub via the host network. TLS insecure should probably not be there in production.

#### 4. Prometheus Scrape + Bridge

```
prometheus.scrape "node_exporter" -> otelcol.receiver.prometheus "edge" -> otelcol.exporter.otlp.default
```

- **`prometheus.scrape`** -- pulls metrics from `node-exporter:9100` (host-level CPU/memory/disk/network).
- **`otelcol.receiver.prometheus "edge"`** -- bridges those Prometheus metrics into the OTLP pipeline so they get forwarded to the hub.

Unlike the hub which writes scraped metrics directly to Mimir via `prometheus.remote_write`, the edge doesn't know about Mimir -- it just converts to OTLP and ships upstream.

### Docker Compose Integration

#### NCP (`NCP/docker-compose.yml`)

**YAML anchor for OTEL config**:

```yaml
x-opentelemetry-config: &opentelemetry-config
  OTEL_EXPORTER_OTLP_ENDPOINT: http://alloy-edge:4317
  OTEL_EXPORTER_OTLP_PROTOCOL: grpc
  OTEL_TRACES_SAMPLER: always_on
```

Merged (`<<: *opentelemetry-config`) into every instrumented service. Each service sets its own `OTEL_SERVICE_NAME` and loads the OTel Java agent via `JAVA_OPTS`. All six services declare `depends_on: [alloy-edge]`.

**alloy-edge service**:

- In dev, uses `host.docker.internal:host-gateway` via `extra_hosts` to reach the monitoring server's port 4317 from inside the Docker network.
- The `OTLP_SERVER_ENDPOINT` env var is set to `http://host.docker.internal:4317`, which is what the alloy config reads via `sys.env()`.
- In acceptance/production, this extra host should be removed.

**node-exporter** runs with host PID namespace and `/proc`, `/sys`, `/rootfs` mounts for host metrics.

#### National Connector (`national-connector/docker-compose.yml`)

The national-connector sets OTEL env vars directly:

```yaml
OTEL_SERVICE_NAME: "national-connector"
OTEL_EXPORTER_OTLP_ENDPOINT: "http://alloy-edge:4317"
OTEL_EXPORTER_OTLP_PROTOCOL: "grpc"
OTEL_RESOURCE_ATTRIBUTES: "service.version=${VERSION:-latest}"
OTEL_TRACES_SAMPLER: "always_on"
```

Shares the NCP's `alloy-edge` instance (both compose files use the same `epps` network).

## Alloy Hub Configuration

### Architecture Overview

`alloy-hub` is the central ingestion point. Edge instances forward here, and the hub routes to backends.

### 1. OTLP Receiver

```
otelcol.receiver.otlp "default"
```

Listens on gRPC `:4317` and HTTP `:4318`. All three signal types (metrics, logs, traces) go into the batch processor.

### 2. Batch Processor

```
otelcol.processor.batch "default"
```

Batches OTLP data before sending downstream. Fans out by signal type:

| Signal  | Destination                              |
|---------|------------------------------------------|
| Metrics | `otelcol.exporter.prometheus.default` -> Mimir |
| Logs    | `otelcol.exporter.loki.default` -> Loki  |
| Traces  | `otelcol.exporter.otlp.tempo` -> Tempo   |

### 3. Metrics Pipeline

- **`otelcol.exporter.prometheus "default"`** -- converts OTLP metrics to Prometheus remote-write format.
- **`prometheus.remote_write "default"`** -- pushes to Mimir at `http://mimir:9009/api/v1/push`.

### 4. Logs Pipeline 
- **`otelcol.exporter.loki "default"`** -- converts OTLP logs into Loki-native format.
- **`loki.write "default"`** -- pushes to **Loki** at `http://loki:3100/loki/api/v1/push`.

Both OTLP logs and Docker-scraped logs end up in the same `loki.write` component.

### 5. Traces Pipeline

```
otelcol.exporter.otlp "tempo"
```

Forwards traces over OTLP gRPC to Tempo at `http://tempo:4317`.

### 6. Prometheus Scraping

```
prometheus.scrape "node_exporter"
```

Scrapes `node-exporter:9100` (job: `monitoring-server-node-exporter`) and forwards to the `prometheus.remote_write` -> Mimir pipeline. Collects host-level metrics (CPU, memory, disk, network) from the monitoring server.

### 7. Docker Log Collection

Replaces Promtail. Collects container logs directly from the Docker daemon via four components:

#### 7a. Container Discovery

```
discovery.docker "local"
```

Connects to the Docker socket to discover all running containers automatically.

#### 7b. Label Relabeling

```
discovery.relabel "docker_labels"
```

Three relabel rules:

1. **Container name** -- strips the leading `/` from Docker container names (e.g. `/grafana` -> `grafana`) and stores it as the `container` label.
2. **Container ID** -- maps `__meta_docker_container_id` -> `container_id`.
3. **Log stream** -- maps `__meta_docker_container_log_stream` -> `logstream` (stdout vs stderr).

#### 7c. Log Processing Pipeline

```
loki.process "pipeline"
```

Stages applied to each log line:

1. **`stage.cri`** -- parses the CRI log format (timestamps + stream info from containerd).
2. **`stage.multiline`** -- joins continuation lines (e.g. stack traces) into the previous entry. New entry starts on any non-whitespace (`^\S`).
3. **`stage.json`** -- best-effort JSON parse, extracts `level`. Non-JSON logs pass through fine.
4. **`stage.labels`** -- promotes `level` to a Loki label so you can query `{level="error"}`.
5. **`stage.static_labels`** -- adds `exporter="docker-daemon"` to tell these apart from OTLP logs.

Output goes to `loki.write.default`.

#### 7d. Docker Log Source 
```
loki.source.docker "containers"
```

Tails logs from the discovered/relabeled containers and feeds them into the processing pipeline above.
