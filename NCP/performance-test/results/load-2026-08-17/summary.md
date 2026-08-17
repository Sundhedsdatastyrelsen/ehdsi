# load — 2026-08-17

| | |
|---|---|
| Target | `https://localhost:9443` |
| Scenario | `load` — ramping-arrival-rate: up to 1 iteration(s) per 1s over 15s, then held 1m (max 10 VUs) |
| Patient | `1-1234-W9` |
| Commit | `e62b87e4-dirty` |
| Run duration | 76.7 s |
| Result | **all thresholds passed** |

See `info.txt` for where the load was generated and what the target was running.

## One patient lookup, end to end

All four steps of one iteration, in milliseconds.

| | avg | p50 | p95 | max |
|---|--:|--:|--:|--:|
| iteration | 2305 | 2270 | 2534 | 2728 |

## Per step

Milliseconds per request, by the `name` tag.

| Step | avg | p50 | p95 | max | Threshold | |
|---|--:|--:|--:|--:|--:|:--|
| XCPD patient discovery | 422 | 410 | 506 | 621 | p(95)<5000 | pass |
| XCA Query (list prescriptions) | 459 | 454 | 516 | 610 | p(95)<10000 | pass |
| XCA Retrieve L3 (structured CDA) | 712 | 698 | 850 | 1021 | p(95)<15000 | pass |
| XCA Retrieve L1 (PDF) | 561 | 547 | 661 | 774 | p(95)<15000 | pass |

## Throughput and correctness

- Iterations completed: **67** (0.87/s)
- HTTP requests: 268 (3.50/s)
- Failed requests: 0.00 %
- Checks: 737 passed, 0 failed
- VUs: 3 busy at peak, of 10 allocated

## Thresholds

- pass — `checks`: `rate==1`
- pass — `http_req_duration{name:xca-query}`: `p(95)<10000`
- pass — `http_req_duration{name:xca-retrieve-l1}`: `p(95)<15000`
- pass — `http_req_duration{name:xca-retrieve-l3}`: `p(95)<15000`
- pass — `http_req_duration{name:xcpd}`: `p(95)<5000`
