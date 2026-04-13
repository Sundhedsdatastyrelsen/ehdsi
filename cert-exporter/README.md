# cert-exporter

A small Prometheus exporter that reads X.509 certificates from JKS, PKCS12, and PEM files and exposes their metadata — notably expiry timestamps — as Prometheus metrics on a `/metrics` endpoint.

## Quick start

For local development:

```
clojure -M:run config.local.edn
```

Docker:

```
docker compose up
```

The Docker setup reads `config.edn`.
Keystore passwords are read from Docker secrets backed by the same secret files used by the NCP and national-connector stacks.

## Why

We have several keystores across the NCP and national-connector stacks containing certificates with varying expiry dates and external renewal requirements.
An expired certificate causes hard-to-diagnose failures at runtime.
This exporter makes expiry visible in Grafana before it becomes a problem.

## Alternatives considered

We looked at existing open-source exporters:

- [joe-elliott/cert-exporter](https://github.com/joe-elliott/cert-exporter) — Kubernetes-only, no JKS support
- [enix/x509-certificate-exporter](https://github.com/enix/x509-certificate-exporter) — Kubernetes-only
- [amimof/node-cert-exporter](https://github.com/amimof/node-cert-exporter) — no JKS support

All three assume certificates are files on disk in PEM/DER format within a Kubernetes cluster.
None handle JKS or PKCS12 keystores, which is our primary use case.
This tool is intentionally small and can be replaced with an open-source solution if circumstances change.

## How it works

On startup and then on every `:scrape-interval-seconds` tick, each configured keystore is opened and all aliases are iterated.
For each alias, the leaf certificate is extracted (either a certificate entry directly, or the first certificate in a key entry's chain). The following Prometheus gauges are updated:

- `<prefix>not_after_timestamp_seconds` — Unix timestamp of certificate expiry
- `<prefix>not_before_timestamp_seconds` — Unix timestamp of certificate validity start

Both gauges carry labels: `description`, `path`, `alias`, `subject`, `issuer`.
If a keystore cannot be read (wrong password, missing file, etc.) an error is logged and the remaining keystores are still processed.

## Dashboard

`dashboards/cert-exporter.json` can be imported directly into Grafana.
It shows a table of all certificates with expiry colour-coded green (> 12 months), orange (6–12 months), and red (< 6 months).
