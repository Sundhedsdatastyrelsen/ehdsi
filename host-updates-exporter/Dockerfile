# Same Ubuntu release as the hosts, so apt reads their sources and dpkg
# status the way their own apt does.
FROM ubuntu:24.04 AS base

RUN apt-get update \
    && apt-get install --yes --no-install-recommends ca-certificates curl jq python3 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY lib lib
COPY collect.sh eol.sh pending.sh run.sh serve.py ./

FROM base AS test
COPY test test
RUN ./test/tests.sh && touch /tests-passed

FROM base
# Depend on the test stage so a failing test fails the build.
COPY --from=test /tests-passed /app/.tests-passed

# Runs as root: apt-get update writes the package lists, and the Docker
# socket is root-owned. Everything of the host's is mounted read-only.
EXPOSE 9090
CMD ["/app/run.sh"]
