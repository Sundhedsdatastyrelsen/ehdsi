# host-updates-exporter

A Prometheus exporter that reports, per host, what needs updating. It runs
next to the stacks on every host (deployed by the ops repos) and serves
`/metrics` on port 9090. Every series carries a `hostname` label; the
`# HELP` lines on `/metrics` describe each metric.

- **Pending packages** ([`packages.py`](exporter/packages.py), every 6 hours):
  which packages apt would upgrade on the host, who patches each — `hosting`
  (the Ubuntu archive) or `ours` (Docker) — whether it is a security update,
  and since when it has been pending. Also whether a reboot is required (a
  newer kernel is installed than the one running), and the OS and running
  kernel. The exporter itself never installs anything: the ops repos'
  `update-packages.sh` upgrades our packages on the host, asking this
  container what is pending ([`pending.py`](exporter/pending.py)).
- **End of life** ([`software.py`](exporter/software.py), daily): for the
  images of the running containers (except our own builds), the Ubuntu
  release and the Docker Engine, the running version, the newest releases,
  and when the version's release cycle reaches end of life per
  [endoflife.date](https://endoflife.date), or the latest GitHub release where
  endoflife.date has no entry.

Which apt repositories are ours, which images we build, and which image maps
to which endoflife.date product or GitHub repository, has defaults in
[`config.py`](exporter/config.py). Each ops repo adds to them in its own
`host-updates-exporter/config.toml`, mounted at `/etc/host-updates`;
[`config.example.toml`](config.example.toml) describes the format. A
third-party image no table covers shows up with `lookup="untracked"` until it
is added to one. The file is read before every check, so changes need no
restart; a broken file is logged and ignored.

## How it sees the host

Only through read-only bind mounts, listed in
[`docker-compose.yml`](docker-compose.yml). apt runs with the host's
configuration and installed packages but keeps its own package lists, in the
state volume. It needs outbound HTTPS to the host's apt mirrors,
endoflife.date and api.github.com.

It runs as an unprivileged user with no capabilities on a read-only root
filesystem, and can write only to the state volume and `/tmp`. It never gets
the Docker socket: `host-updates-docker-proxy`, the official HAProxy image
with [`docker-proxy/haproxy.cfg`](docker-proxy/haproxy.cfg), lets through
listing containers, inspecting a container or an image, and the Docker
version, and refuses everything else. It is on an internal network
with only the exporter. Every ops repo runs the two services with the
settings of [`docker-compose.yml`](docker-compose.yml) and a copy of the
HAProxy configuration; change them here and in the ops repos together.

As apt runs as that user, it can't read credentials in the host's
`/etc/apt/auth.conf.d` that only root may read (Ubuntu Pro, for one): those
sources then fail to refresh and `host_updates_refresh_success` is 0.

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
on changes to this directory, and weekly with the newest Ubuntu packages. A
host's own updates need no new image; a new Ubuntu release on the hosts
needs the base image in the [`Dockerfile`](Dockerfile) moved along.
