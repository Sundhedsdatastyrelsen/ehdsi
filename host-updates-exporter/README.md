# host-updates-exporter

A Prometheus exporter that reports, per host, what needs updating. It runs
next to the stacks on every host (deployed by the ops repos) and serves
`/metrics` on port 9090. Every series carries a `hostname` label; the
`# HELP` lines on `/metrics` describe each metric.

- **Pending packages** ([`packages.py`](exporter/packages.py), every 6 hours):
  which packages apt would upgrade on the host, who patches each — `hosting`
  (the Ubuntu archive) or `ours` (Docker) — whether it is a security update,
  and since when it has been pending. Also whether a reboot is required, the
  OS and running kernel. Nothing is installed: the ops repos'
  `update-packages.sh` upgrades our packages on the host, asking this
  container what is pending ([`pending.py`](exporter/pending.py)).
- **End of life** ([`software.py`](exporter/software.py), daily): for the
  images of the running containers (except our own builds), the Ubuntu
  release and the Docker Engine, the running version, the newest releases,
  and when the version's release cycle reaches end of life per
  [endoflife.date](https://endoflife.date), or the latest GitHub release where
  endoflife.date has no entry.

Which apt repositories are ours, and which image maps to which
endoflife.date product or GitHub repository, is in
[`config.py`](exporter/config.py). A new third-party image in one of the
stacks shows up with `status="untracked"` until it is added there.

## How it sees the host

Only through read-only bind mounts, listed in
[`docker-compose.yml`](docker-compose.yml). apt runs with the host's
configuration and installed packages but keeps its own package lists, in the
state volume. It needs outbound HTTPS to the host's apt mirrors,
endoflife.date and api.github.com.

## Development

```bash
docker compose up --build   # runs against this machine on localhost:9090
```

To see the metrics in Grafana, run it with the local monitoring stack in
[`../monitoring`](../monitoring). The override adds an Alloy that scrapes the
exporter into that stack's Mimir, standing in for a host's alloy-edge:

```bash
docker compose -f ../monitoring/docker-compose.yml up -d
docker compose -f docker-compose.yml -f docker-compose.local-monitoring.yml up -d --build

# Import the dashboard (or Dashboards → New → Import in the UI)
curl -X POST localhost:3000/api/dashboards/db -H 'Content-Type: application/json' \
    -d "{\"dashboard\": $(cat dashboards/host-updates.json), \"overwrite\": true}"
```

Then open <http://localhost:3000/d/host-updates>. The first results appear
within a minute of starting. [`dashboards/host-updates.json`](dashboards/host-updates.json)
is a copy of the one monitoring-ops provisions; export changes made in the UI
back to both.

The tests run as part of the image build ([`tests/`](tests/)); a failing test
fails the build.
[`build-host-updates-exporter-image.yml`](../.github/workflows/build-host-updates-exporter-image.yml)
publishes `ghcr.io/sundhedsdatastyrelsen/ehdsi/host-updates-exporter:build-N`
on changes to this directory.
