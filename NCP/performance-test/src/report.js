import { textSummary } from "https://jslib.k6.io/k6-summary/0.1.0/index.js";

// Turns k6's end-of-test summary into a Markdown file meant to be committed next to
// earlier runs, so two results can be compared by reading them (or diffing them)
// rather than by loading a dashboard. summary.json keeps the full metric set for
// anything the table leaves out.

const MS = (v) => (v === undefined ? "—" : `${Math.round(v)}`);

function metric(data, name) {
  const m = data.metrics[name];
  return m ? m.values : {};
}

// A metric fails the run if any of its thresholds failed. k6 reports them per metric,
// keyed by the threshold expression.
function thresholdsOf(data, name) {
  const m = data.metrics[name];
  return Object.entries((m && m.thresholds) || {}).map(([expression, result]) => ({
    expression,
    ok: result.ok,
  }));
}

// Sorted, because k6 hands the metrics over in arbitrary order and an unstable list
// makes every result diff look like something changed.
function allThresholds(data) {
  return Object.keys(data.metrics)
    .sort()
    .flatMap((name) => thresholdsOf(data, name).map((t) => ({ metric: name, ...t })));
}

function describeScenario(name, config) {
  if (config.executor === "per-vu-iterations") {
    return `\`${name}\` — ${config.vus} VU × ${config.iterations} iterations`;
  }
  if (config.executor === "ramping-arrival-rate") {
    const [ramp, hold] = config.stages;
    return (
      `\`${name}\` — ramping-arrival-rate: up to ${ramp.target} iteration(s) per ` +
      `${config.timeUnit} over ${ramp.duration}, then held ${hold.duration} ` +
      `(max ${config.maxVUs} VUs)`
    );
  }
  return `\`${name}\` — ${config.executor}`;
}

function contextTable(rows) {
  return ["| | |", "|---|---|", ...rows.map(([k, v]) => `| ${k} | ${v} |`)].join("\n");
}

function latencyRow(label, values, limitMs, ok) {
  const verdict = limitMs === undefined ? "" : ` | p(95)<${limitMs} | ${ok ? "pass" : "**FAIL**"}`;
  return (
    `| ${label} | ${MS(values.avg)} | ${MS(values.med)} | ${MS(values["p(95)"])} | ` +
    `${MS(values.max)}${verdict} |`
  );
}

function markdown(data, { scenario, config, steps, baseUrl, patient }) {
  const failed = allThresholds(data).filter((t) => !t.ok);
  const iteration = metric(data, "iteration_duration");
  const checks = metric(data, "checks");
  const reqs = metric(data, "http_reqs");
  const dropped = metric(data, "dropped_iterations");
  const durationS = (data.state.testRunDurationMs / 1000).toFixed(1);

  const stepRows = Object.entries(steps).map(([tag, step]) => {
    const name = `http_req_duration{name:${tag}}`;
    const ok = thresholdsOf(data, name).every((t) => t.ok);
    return latencyRow(step.label, metric(data, name), step.p95, ok);
  });

  return [
    `# ${scenario} — ${__ENV.RUN_DATE || ""}`.trim(),
    "",
    contextTable([
      ["Target", `\`${baseUrl}\``],
      ["Scenario", describeScenario(scenario, config)],
      ["Patient", `\`${patient}\``],
      ["Commit", `\`${__ENV.GIT_COMMIT || "unknown"}\``],
      ["Run duration", `${durationS} s`],
      ["Result", failed.length === 0 ? "**all thresholds passed**" : `**${failed.length} threshold(s) FAILED**`],
    ]),
    "",
    "See `info.txt` for where the load was generated and what the target was running.",
    "",
    "## One patient lookup, end to end",
    "",
    "All four steps of one iteration, in milliseconds.",
    "",
    "| | avg | p50 | p95 | max |",
    "|---|--:|--:|--:|--:|",
    latencyRow("iteration", iteration),
    "",
    "## Per step",
    "",
    "Milliseconds per request, by the `name` tag.",
    "",
    "| Step | avg | p50 | p95 | max | Threshold | |",
    "|---|--:|--:|--:|--:|--:|:--|",
    ...stepRows,
    "",
    "## Throughput and correctness",
    "",
    `- Iterations completed: **${metric(data, "iterations").count}** ` +
      `(${(metric(data, "iterations").rate || 0).toFixed(2)}/s)`,
    `- HTTP requests: ${reqs.count} (${(reqs.rate || 0).toFixed(2)}/s)`,
    `- Failed requests: ${(metric(data, "http_req_failed").rate * 100).toFixed(2)} %`,
    `- Checks: ${checks.passes} passed, ${checks.fails} failed`,
    ...(dropped.count ? [`- **Dropped iterations: ${dropped.count}** — the VU pool could not keep up`] : []),
    // vus_max is the preallocated pool, vus.max how many were ever busy at once. The
    // gap between them is the headroom that was left.
    `- VUs: ${metric(data, "vus").max} busy at peak, of ${metric(data, "vus_max").max} allocated`,
    "",
    "## Thresholds",
    "",
    ...allThresholds(data).map((t) => `- ${t.ok ? "pass" : "**FAIL**"} — \`${t.metric}\`: \`${t.expression}\``),
    "",
  ].join("\n");
}

export function summaryReport(data, context) {
  const output = { stdout: textSummary(data, { indent: " ", enableColors: true }) };
  const dir = __ENV.RESULTS_DIR;
  if (!dir) return output;

  output[`${dir}/summary.json`] = JSON.stringify(data, null, 2);
  output[`${dir}/summary.md`] = markdown(data, context);
  return output;
}
