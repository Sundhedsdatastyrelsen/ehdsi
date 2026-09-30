# host-updates-exporter

A small Prometheus exporter that reports, per host, what needs updating:
pending OS/package updates, and the end of life of the images the host runs,
its Ubuntu release and its Docker Engine. It serves them on `/metrics`
(port 9090) and runs next to the stacks on every host, scraped by alloy.

## What it reports

**Pending packages** (`collect.sh`, every 6 hours): refreshes the host's apt
sources and simulates a `dist-upgrade` against the host's installed packages.
Each pending package is classified by who patches it — `hosting` (the Ubuntu
archive, patched by the hosting provider) or `ours` (the repositories in
`OWN_ORIGINS`, e.g. Docker) — and whether a security update is among it, and
remembers since when it has been pending. Also reports whether a reboot is
required, the OS and running kernel, last boot and last package change.
Nothing is installed: upgrading our packages is done on the host by the ops
repos' `update-packages.sh`, which asks this container what is pending
(`pending.sh`).

**End of life** (`eol.sh`, daily): for the images of the running containers
(except our own builds, `OWN_IMAGE_PREFIXES`), the host's Ubuntu release and
the Docker Engine, the running version, the newest releases, and when the
version's release cycle reaches end of life per
[endoflife.date](https://endoflife.date). The version is read from the
image's `<NAME>_VERSION` env var or version label where it has one, so
floating tags (`mysql:9`, `hashicorp/vault`) resolve to what was actually
pulled. Images endoflife.date doesn't cover get their latest GitHub release
instead.

Which images map to which endoflife.date product or GitHub repository, and
which apt origins are ours, is in [`lib/config.sh`](lib/config.sh). A new
third-party image in one of the stacks shows up with `status="untracked"`
until it is added there.

Every series carries a `hostname` label. Metric descriptions are in the
`# HELP` lines of `/metrics`.

## How it sees the host

It needs no host installation, only read-only bind mounts (see
[`docker-compose.yml`](docker-compose.yml)): the host's `/etc/apt`,
`/usr/share/keyrings` and `/etc/machine-id` at their own paths (sources name
their keyrings by absolute path; machine-id decides phased updates), and the
host's dpkg state, `/run` (reboot flag, Docker socket), os-release and
hostname under `/host`. apt runs with the host's configuration but its own
package lists, with the host's apt hooks disabled
([`lib/apt-no-hooks.conf`](lib/apt-no-hooks.conf)). The base image is the
hosts' Ubuntu release.

State (pending-since, package lists, the last good endoflife.date/GitHub
responses) lives in a volume at `/var/lib/host-updates`. It needs outbound
HTTPS to the host's apt mirrors, endoflife.date and api.github.com.

## Development

```bash
./test/tests.sh          # fixtures only; no root, apt, docker or network
docker compose up --build   # runs against this machine on localhost:9090
```

The Docker build runs the tests; a failing test fails the image build.
[`build-host-updates-exporter-image.yml`](../.github/workflows/build-host-updates-exporter-image.yml)
publishes `ghcr.io/sundhedsdatastyrelsen/ehdsi/host-updates-exporter:build-N`
on changes to this directory.
