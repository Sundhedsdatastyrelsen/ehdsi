#!/bin/bash
#
# Regenerate the NC<->Opt-Out mTLS certificates used in development.
#
#   national-connector/config/dev-opt-out-keystore.p12    NC client certificate
#   national-connector/config/dev-opt-out-truststore.p12  trusts the FSEU server certificate
#   <FSEU>/config/dev-server-keystore.p12                 FSEU server certificate
#   <FSEU>/config/dev-server-truststore.p12               trusts the NC client certificate
#                                                         and <FSEU>/dev-client.crt (curl client)
#
# The previous stores are not backed up: they are in git history.
#
# Usage:
#   ./regenerate-dev-certificates.sh [path-to-FSEU-checkout]
#
#   FSEU_ROOT           FSEU checkout (default: argument, else ../FSEU, else ~/repos/FSEU)
#   SERVER_CN           CN of the FSEU server certificate. Must equal the hostname the
#                       national connector dials (opt-out.host in application.yml) —
#                       Java verifies the CN and ignores SANs here. Default: localhost
#   CLIENT_CN           CN of the NC client certificate. Default: optouttest.ehdsi.sundhedsdata.dk
#   CERT_VALIDITY_DAYS  Default: 3650
#
# See docs/certificates.md.

set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

FSEU_ROOT="${FSEU_ROOT:-${1:-}}"
if [[ -z "$FSEU_ROOT" ]]; then
    for candidate in "${REPO_ROOT}/../FSEU" "${HOME}/repos/FSEU"; do
        [[ -d "$candidate/config" ]] && FSEU_ROOT="$candidate" && break
    done
fi
[[ -n "$FSEU_ROOT" && -d "$FSEU_ROOT/config" ]] \
    || { echo "Error: FSEU checkout not found. Pass its path as the first argument or set FSEU_ROOT." >&2; exit 1; }
FSEU_ROOT="$(cd "$FSEU_ROOT" && pwd)"

SERVER_CN="${SERVER_CN:-localhost}"
CLIENT_CN="${CLIENT_CN:-optouttest.ehdsi.sundhedsdata.dk}"
CERT_VALIDITY_DAYS="${CERT_VALIDITY_DAYS:-3650}"
STORE_PASSWORD="changeit"
# Subjects match the certificates generated in November 2025
SERVER_SUBJECT="/CN=${SERVER_CN}"
CLIENT_SUBJECT="/C=DK/ST=Copenhagen/O=Sundhedsdatastyrelsen/CN=${CLIENT_CN}"

NC_CONFIG_DIR="${REPO_ROOT}/national-connector/config"
FSEU_CONFIG_DIR="${FSEU_ROOT}/config"
FSEU_DEV_CLIENT_CERT="${FSEU_ROOT}/dev-client.crt"

NC_KEYSTORE="${NC_CONFIG_DIR}/dev-opt-out-keystore.p12"
NC_TRUSTSTORE="${NC_CONFIG_DIR}/dev-opt-out-truststore.p12"
FSEU_KEYSTORE="${FSEU_CONFIG_DIR}/dev-server-keystore.p12"
FSEU_TRUSTSTORE="${FSEU_CONFIG_DIR}/dev-server-truststore.p12"

# Aliases — keep in sync with what the applications and cert-exporter expect
NC_CLIENT_ALIAS="opt-out-client"
FSEU_SERVER_ALIAS="server"
FSEU_DEV_CLIENT_ALIAS="dev-client-leaf"

command -v openssl &>/dev/null || { echo "Error: openssl not found" >&2; exit 1; }
command -v keytool &>/dev/null || { echo "Error: keytool not found" >&2; exit 1; }

WORK_DIR=$(mktemp -d)
trap 'rm -rf "$WORK_DIR"' EXIT

make_self_signed() {
    local subject="$1" key="$2" crt="$3"
    openssl req -new -x509 -newkey rsa:2048 -noenc -days "$CERT_VALIDITY_DAYS" \
        -subj "$subject" -keyout "$key" -out "$crt" 2>/dev/null
}

make_keystore() {
    local crt="$1" key="$2" alias="$3" out="$4"
    openssl pkcs12 -export -in "$crt" -inkey "$key" -name "$alias" \
        -out "$out" -password "pass:${STORE_PASSWORD}"
}

add_trusted() {
    local store="$1" alias="$2" crt="$3"
    keytool -importcert -noprompt -storetype PKCS12 -storepass "$STORE_PASSWORD" \
        -keystore "$store" -alias "$alias" -file "$crt" >/dev/null 2>&1
}

echo "=== Regenerating dev NC<->Opt-Out mTLS certificates ==="
echo "  ehdsi root:   ${REPO_ROOT}"
echo "  FSEU root:    ${FSEU_ROOT}"
echo "  server CN:    ${SERVER_CN}"
echo "  client CN:    ${CLIENT_CN}"
echo "  validity:     ${CERT_VALIDITY_DAYS} days"
echo

echo "Generating FSEU server certificate (CN=${SERVER_CN})..."
make_self_signed "$SERVER_SUBJECT" "${WORK_DIR}/server.key" "${WORK_DIR}/server.crt"
make_keystore "${WORK_DIR}/server.crt" "${WORK_DIR}/server.key" "$FSEU_SERVER_ALIAS" "${WORK_DIR}/dev-server-keystore.p12"

echo "Generating NC client certificate (CN=${CLIENT_CN})..."
make_self_signed "$CLIENT_SUBJECT" "${WORK_DIR}/client.key" "${WORK_DIR}/client.crt"
make_keystore "${WORK_DIR}/client.crt" "${WORK_DIR}/client.key" "$NC_CLIENT_ALIAS" "${WORK_DIR}/dev-opt-out-keystore.p12"

echo "Building NC truststore (trusts the FSEU server certificate)..."
add_trusted "${WORK_DIR}/dev-opt-out-truststore.p12" "$SERVER_CN" "${WORK_DIR}/server.crt"

echo "Building FSEU truststore (trusts the NC client certificate)..."
add_trusted "${WORK_DIR}/dev-server-truststore.p12" "$CLIENT_CN" "${WORK_DIR}/client.crt"
if [[ -f "$FSEU_DEV_CLIENT_CERT" ]]; then
    add_trusted "${WORK_DIR}/dev-server-truststore.p12" "$FSEU_DEV_CLIENT_ALIAS" "$FSEU_DEV_CLIENT_CERT"
    echo "  -> also trusts $(basename "$FSEU_DEV_CLIENT_CERT") (curl/load-test client)"
else
    echo "  WARNING: ${FSEU_DEV_CLIENT_CERT} not found; the curl dev client will not be trusted."
fi

echo
echo "Installing..."
for pair in \
    "dev-opt-out-keystore.p12:${NC_KEYSTORE}" \
    "dev-opt-out-truststore.p12:${NC_TRUSTSTORE}" \
    "dev-server-keystore.p12:${FSEU_KEYSTORE}" \
    "dev-server-truststore.p12:${FSEU_TRUSTSTORE}"; do
    src="${WORK_DIR}/${pair%%:*}"
    dst="${pair#*:}"
    cp "$src" "$dst"
    echo "  -> ${dst}"
done

echo
echo "Resulting entries:"
for store in "$NC_KEYSTORE" "$NC_TRUSTSTORE" "$FSEU_KEYSTORE" "$FSEU_TRUSTSTORE"; do
    echo "  ${store}"
    keytool -list -v -storetype PKCS12 -storepass "$STORE_PASSWORD" -keystore "$store" 2>/dev/null \
        | grep -E 'Alias name|Valid from' | sed 's/^/    /'
done

echo
echo "Done. Commit the changed files in both repositories, pull on the dev host, and"
echo "recreate the fseu and national-connector containers (docker compose up -d)."
