#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

export DB_USER; DB_USER=$(</run/secrets/db_username)
export DB_PASSWORD; DB_PASSWORD=$(</run/secrets/db_password)
export TSAM_SYNC_USERNAME; TSAM_SYNC_USERNAME=$(</run/secrets/cts_username)
export TSAM_SYNC_PASSWORD; TSAM_SYNC_PASSWORD=$(</run/secrets/cts_password)

# Set OpenTelemetry environment variables
export OTEL_SERVICE_NAME="tsam-synchronizer"
export OTEL_EXPORTER_OTLP_ENDPOINT="http://jaeger:4317"
export OTEL_EXPORTER_OTLP_PROTOCOL="grpc"
export OTEL_TRACES_SAMPLER="always_on"
export OTEL_METRICS_EXPORTER="none"
export OTEL_LOGS_EXPORTER="none"
export OTEL_PROPAGATORS="tracecontext,baggage"

# Execute the original command with OpenTelemetry agent
exec java -javaagent:/opt/opentelemetry-javaagent.jar "$@"
