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

# Root, because refreshing the package lists and reading the Docker socket
# need it. The host is only mounted read-only.
EXPOSE 9090
CMD ["python3", "/app/exporter/main.py"]
