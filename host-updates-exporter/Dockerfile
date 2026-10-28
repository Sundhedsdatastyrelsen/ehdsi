# The hosts' Ubuntu release, so apt reads their sources and dpkg status the
# way their own apt does.
FROM ubuntu:24.04 AS base

RUN apt-get update \
    && apt-get install --yes --no-install-recommends ca-certificates python3 python3-apt python3-prometheus-client \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY exporter exporter

FROM base AS test
COPY tests tests
RUN PYTHONPATH=exporter:tests python3 -m unittest discover --start-directory tests --verbose \
    && touch /tests-passed

FROM base
# A failing test fails the build.
COPY --from=test /tests-passed /tests-passed

# Not root: refreshing the package lists only writes to the state volume, and
# Docker is reached through host-updates-docker-proxy. The volume takes this
# directory's owner when it is created.
RUN useradd --system --user-group --no-create-home host-updates \
    && mkdir -p /var/lib/host-updates && chown host-updates: /var/lib/host-updates
USER host-updates
EXPOSE 9090
CMD ["python3", "/app/exporter/main.py"]
