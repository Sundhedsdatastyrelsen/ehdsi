# k6 performance tests for ePrescription

These tests act as a country B service: an OpenNCP client in the country of treatment
calling our `openncp-server`.

One iteration is one clinician looking up a patient's prescriptions:

1. **XCPD** patient discovery
2. **XCA Query** for the patient's ePrescriptions
3. **XCA Retrieve** of one prescription as **L3** (structured CDA)
4. **XCA Retrieve** of the same prescription as **L1** (PDF)

The L3 and L1 document ids for steps 3 and 4 are taken from the query response.

The SAML HCP and TRC assertions are rendered from the templates in `templates/` and
signed inside k6, freshly per iteration, so that the server's signature validation is
part of what is measured.

## Prerequisites

- `k6` (`brew install k6`) — v1.0 or later, developed against v2.2. The signing code uses
  the global `crypto` object, which replaced the `k6/experimental/webcrypto` module in
  v1.0, so it will not run on v0.x.
- `node` and `npm` — for building the bundle
- Docker, with the NCP running (`docker compose -f ../docker-compose.yml up`)
- **A patient with at least one open ePrescription.** The flow reaches all the way into
  the FMK test environment through the national connector, so the data has to exist
  there. Without it the XCA query returns `ERROR_EP_NOT_FOUND`. The tests do not
  dispense, so a single prescription is enough for an entire run.

> The NCP proxies to shared external test systems (FMK, NSP CPR, SOSI STS). A load run
> puts load on those systems too, not just on the NCP — hence the deliberately low
> default rate.

## Running

```sh
./run.sh smoke     # 1 VU, 5 iterations
./run.sh load      # ramping arrival rate, defaults below
```

`run.sh` builds the bundle (`npm run build` → `dist/main.js`) and then runs k6, so
there is no separate build step to remember. Anything after the scenario name is
passed through to `k6 run`.

The **smoke test** proves the chain works: every step is checked for functional
success, not just HTTP 200, and the `checks: rate==1` threshold makes it fail loudly.

The **load test** ramps to a constant arrival rate and holds it.

## Results

Every run writes `results/<scenario>-<date>/`:

| File           | Contents                                                         |
|----------------|------------------------------------------------------------------|
| `summary.md`   | The readable report: per-step latency, throughput, thresholds    |
| `summary.json` | k6's full end-of-test metrics, for anything the table leaves out |
| `info.txt`     | Where the load was generated and what the target was running     |

Results worth keeping can be committed, so a later run can be compared against them by
diffing two `summary.md` files.

`info.txt` is generated on the first run of the day with the host and k6 version
filled in and `System under test:` / `Notes:` left as `...`. **Fill those in before
committing a result** — they are the only part a machine cannot know, and without them
the numbers are not comparable: a laptop against Docker Desktop is a different system
than a workstation against the training server, however similar the figures look. Re-running
on the same day overwrites `summary.md` and `summary.json` but never `info.txt`.

## Baseline: the mock server

`tomcat_node_a_mock` is the same OpenNCP release built against OpenNCP's own
reference national connector (`openncp-core-server-mock`) instead of ours.  It
answers from canned documents inside that connector and never reaches FMK or the
NSP, so comparing a run against it with a run against the real node separates
OpenNCP's own cost from the cost of our national backend.

It is defined in `../docker-compose.performance.yml`, an overlay on the main compose
file, so it is only built and started when that file is passed explicitly:

```sh
cd .. && docker compose -f docker-compose.yml -f docker-compose.performance.yml \
    up --build --detach tomcat_node_a_mock && cd -
BASE_URL=https://localhost:9443 PATIENT_ID=1-1234-W9 ./run.sh smoke
```

- **It shares the real node's database.** Both use `ncp_a/ncpa.database.env`, so the two
  servers write audits to the same schema. Stop `tomcat_node_a` before measuring the
  baseline, or the contention lands in the numbers.

- **`PATIENT_ID` must be one the mock knows**, e.g. `1-1234-W9` or `1-5678-W9`. It looks
  patients up as `integration/<assigning authority>/<id>.properties` under
  `/opt/openncp-configuration`, and the image repeats one of those files under the Danish
  OID so the request templates work unchanged. The prescriptions come from `epstore` in
  the connector jar, and the mock matches them to the patient by id prefix.
- **Document ids follow a different convention.** The mock publishes the same document as
  `<oid>.1` (XML/L3) and `<oid>.2` (PDF/L1), where DK uses `<repositoryId>^<id>L3`/`L1`.
  `prescriptionDocuments()` in `src/responses.js` handles both.

## Configuration

All via environment variables:

| Variable     | Default                  | Meaning                                                             |
|--------------|--------------------------|---------------------------------------------------------------------|
| `BASE_URL`   | `https://localhost:8443` | NCP server to test                                                  |
| `PATIENT_ID` | `0410009234`             | Patient CPR, substituted into the templates                         |
| `RATE`       | `1`                      | Iterations per time unit (load)                                     |
| `TIME_UNIT`  | `1s`                     | Time unit for `RATE`, e.g. `2s` for one iteration every two seconds |
| `DURATION`   | `1m`                     | Time at the target rate (load)                                      |
| `RAMP_UP`    | `15s`                    | Time spent ramping to the target rate                               |
| `MAX_VUS`    | `10`                     | VU ceiling (load)                                                   |

```sh
RATE=3 DURATION=2m MAX_VUS=30 ./run.sh load
RATE=1 TIME_UNIT=4s ./run.sh load
PATIENT_ID=0201909309 ./run.sh smoke
```

The default rate of one iteration per second already saturates the **real** NCP running
locally in Docker: a measured run dropped iterations because all VUs stayed busy, and
iteration duration rose from ~4 s at one VU to ~20 s. Expect to dial `RATE`/`TIME_UNIT`
down, not up, when testing a laptop. The mock server takes the same rate comfortably
(see `results/load-2026-08-17/`), which is most of the point of having it.

Per-step latency is visible in the summary through the `name` tag on each request
(`xcpd`, `xca-query`, `xca-retrieve-l3`, `xca-retrieve-l1`) and the per-tag
`http_req_duration` thresholds. Those thresholds are generous placeholders, not SLAs;
they live in `STEPS` at the top of `src/main.js`, which also supplies the step labels
used in `summary.md`.

## Layout

| File                              | Contents                                                  |
|-----------------------------------|-----------------------------------------------------------|
| `src/main.js`                     | Scenario, options, thresholds, checks                     |
| `src/requests.js`                 | Templates → SOAP envelopes with freshly signed assertions |
| `src/signing.js`                  | XML-DSig via xml-crypto, backed by k6's crypto            |
| `src/responses.js`                | Regex/base64 extraction from the SOAP responses           |
| `src/report.js`                   | `handleSummary` → the Markdown and JSON in `results/`     |
| `src/shims/`                      | Minimal stand-ins for the Node globals xml-crypto expects |
| `results/`                        | Committed benchmark runs                                  |
| `templates/`                      | Request and assertion templates                           |
| `testcert.cer`, `testcert.p8.pem` | Client certificate and key                                |
| `build.mjs`                       | esbuild bundle configuration                              |

## Templates and credentials

`templates/` contain the XML request templates.

The certificate is self-signed and must be trusted by the server under test —
`keystore/dev-truststore.jks` should contain it.

esbuild inlines all of these into `dist/main.js` as strings (the `text` loader in
`build.mjs`), so the bundle has no runtime file dependencies at all.

## Notes

**Retrieve responses.** Documents come back inline as base64 rather than as MTOM/XOP
parts. Only the head of the base64 is decoded — enough to distinguish a structured L3
CDA from the L1 PDF wrapper without decoding a ~450 KB PDF every iteration.

**k6 and Node APIs.** xml-crypto is a Node library, so the bundle substitutes shims for
`crypto` and `util`, and injects a `Buffer` polyfill. The XML machinery (canonicalization,
transforms, digests, signature placement) is pure JS and runs unchanged; only the
cryptography is rewired to k6's own primitives, since k6 has no Node `crypto`.
