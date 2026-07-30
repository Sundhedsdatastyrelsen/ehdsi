# k6 performance tests for ePrescription

These tests act as a country B service: an OpenNCP client in the country of treatment
calling our `openncp-server`, the same way the scripts in [`../test-tool`](../test-tool)
do manually.

One iteration is one clinician looking up a patient's prescriptions:

1. **XCPD** patient discovery
2. **XCA Query** for the patient's ePrescriptions
3. **XCA Retrieve** of one prescription as **L3** (structured CDA)
4. **XCA Retrieve** of the same prescription as **L1** (PDF)

The L3 and L1 document ids for steps 3 and 4 are taken from the query response.

The SAML HCP and TRC assertions are rendered from the test-tool's templates and signed
inside k6, freshly per iteration, so that the server's signature validation is part of
what is measured.

## Prerequisites

- `k6` (`brew install k6`) — developed against v0.55
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

## Configuration

All via environment variables:

| Variable     | Default                  | Meaning                                   |
| ------------ | ------------------------ | ----------------------------------------- |
| `BASE_URL`   | `https://localhost:8443` | NCP server to test                        |
| `PATIENT_ID` | `0410009234`             | Patient CPR, substituted into the templates |
| `RATE`       | `1`                      | Iterations per time unit (load)           |
| `TIME_UNIT`  | `1s`                     | Time unit for `RATE`, e.g. `2s` for one iteration every two seconds |
| `DURATION`   | `1m`                     | Time at the target rate (load)            |
| `RAMP_UP`    | `15s`                    | Time spent ramping to the target rate     |
| `MAX_VUS`    | `10`                     | VU ceiling (load)                         |

```sh
RATE=3 DURATION=2m MAX_VUS=30 ./run.sh load
RATE=1 TIME_UNIT=4s ./run.sh load
PATIENT_ID=0201909309 ./run.sh smoke
```

The default rate of one iteration per second already saturates the NCP running locally
in Docker: a measured run dropped iterations because all VUs stayed busy, and iteration
duration rose from ~4 s at one VU to ~20 s. Expect to dial `RATE`/`TIME_UNIT` down, not
up, when testing a laptop.

Per-step latency is visible in the summary through the `name` tag on each request
(`xcpd`, `xca-query`, `xca-retrieve-l3`, `xca-retrieve-l1`) and the per-tag
`http_req_duration` thresholds. Those thresholds are generous placeholders, not SLAs;
they live in `P95_LIMITS_MS` at the top of `src/main.js`.

## Layout

| File                  | Contents                                                          |
| --------------------- | ----------------------------------------------------------------- |
| `src/main.js`         | Scenario, options, thresholds, checks                             |
| `src/requests.js`     | Templates → SOAP envelopes with freshly signed assertions          |
| `src/signing.js`      | XML-DSig via xml-crypto, backed by k6's crypto                     |
| `src/responses.js`    | Regex/base64 extraction from the SOAP responses                    |
| `src/shims/`          | Minimal stand-ins for the Node globals xml-crypto expects          |
| `build.mjs`           | esbuild bundle configuration                                       |

## Notes

**XCPD response codes.** This OpenNCP build reports `queryResponseCode="AE"` even on
the success path: `XcpdServiceServerSideImpl` calls
`fillOutputMessage(outputMessage, null, null, null, "OK")`, which binds to the overload
whose fifth parameter is `location`, so `"OK"` never reaches the `code` parameter and it
defaults to `"AE"`. The XCPD check therefore asserts on the acknowledgement and the
returned demographics instead.

**Retrieve responses.** Documents come back inline as base64 rather than as MTOM/XOP
parts. Only the head of the base64 is decoded — enough to distinguish a structured L3
CDA from the L1 PDF wrapper without decoding a ~450 KB PDF every iteration.

**k6 and Node APIs.** xml-crypto is a Node library, so the bundle substitutes shims for
`crypto` and `util`, and injects a `Buffer` polyfill. The XML machinery (canonicalization,
transforms, digests, signature placement) is pure JS and runs unchanged; only the
cryptography is rewired to k6's own primitives, since k6 has no Node `crypto`.
