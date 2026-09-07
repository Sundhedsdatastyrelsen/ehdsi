# smoke — 2026-08-17

| | |
|---|---|
| Target | `https://localhost:9443` |
| Scenario | `smoke` — 1 VU × 5 iterations |
| Patient | `1-1234-W9` |
| Commit | `e62b87e4-dirty` |
| Run duration | 10.6 s |
| Result | **all thresholds passed** |

See `info.txt` for where the load was generated and what the target was running.

## One patient lookup, end to end

All four steps of one iteration, in milliseconds.

| | avg | p50 | p95 | max |
|---|--:|--:|--:|--:|
| iteration | 2123 | 2065 | 2277 | 2296 |

## Per step

Milliseconds per request, by the `name` tag.

| Step | avg | p50 | p95 | max | Threshold | |
|---|--:|--:|--:|--:|--:|:--|
| XCPD patient discovery | 369 | 355 | 413 | 426 | p(95)<5000 | pass |
| XCA Query (list prescriptions) | 418 | 409 | 450 | 454 | p(95)<10000 | pass |
| XCA Retrieve L3 (structured CDA) | 690 | 651 | 856 | 906 | p(95)<15000 | pass |
| XCA Retrieve L1 (PDF) | 493 | 493 | 501 | 501 | p(95)<15000 | pass |

## Throughput and correctness

- Iterations completed: **5** (0.47/s)
- HTTP requests: 20 (1.88/s)
- Failed requests: 0.00 %
- Checks: 55 passed, 0 failed
- VUs: 1 busy at peak, of 1 allocated

## Thresholds

- pass — `checks`: `rate==1`
- pass — `http_req_duration{name:xca-query}`: `p(95)<10000`
- pass — `http_req_duration{name:xca-retrieve-l1}`: `p(95)<15000`
- pass — `http_req_duration{name:xca-retrieve-l3}`: `p(95)<15000`
- pass — `http_req_duration{name:xcpd}`: `p(95)<5000`
