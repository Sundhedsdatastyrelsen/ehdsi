import http from "k6/http";
import { check } from "k6";
import { credentials, patientId, xcaQueryRequest, xcaRetrieveRequest, xcpdRequest } from "./requests.js";
import { documentHead, patientFound, prescriptionDocuments, registryStatus, retrievedDocumentId } from "./responses.js";
import { summaryReport } from "./report.js";

const BASE_URL = __ENV.BASE_URL || "https://localhost:8443";
const XCPD_URL = `${BASE_URL}/openncp-ws-server/services/XCPD_Service/`;
const XCA_URL = `${BASE_URL}/openncp-ws-server/services/XCA_Service/`;

// The four requests of one iteration, in order. The `name` tag ties each one to its
// own http_req_duration submetric; p95 is the generous placeholder limit for it (not
// an SLA). The labels are what the committed reports in results/ are headed with.
const STEPS = {
  xcpd: { label: "XCPD patient discovery", p95: 5000 },
  "xca-query": { label: "XCA Query (list prescriptions)", p95: 10000 },
  "xca-retrieve-l3": { label: "XCA Retrieve L3 (structured CDA)", p95: 15000 },
  "xca-retrieve-l1": { label: "XCA Retrieve L1 (PDF)", p95: 15000 },
};

const thresholds = Object.fromEntries([
  ["checks", ["rate==1"]],
  ...Object.entries(STEPS).map(([step, { p95 }]) => [`http_req_duration{name:${step}}`, [`p(95)<${p95}`]]),
]);

const SCENARIO = __ENV.SCENARIO || "smoke";

const scenarios = {
  smoke: { executor: "per-vu-iterations", vus: 1, iterations: 5, maxDuration: "10m" },
  load: {
    executor: "ramping-arrival-rate",
    startRate: 0,
    // RATE must be a whole number, so rates below one iteration per second are
    // expressed by stretching the time unit, e.g. RATE=1 TIME_UNIT=2s.
    timeUnit: __ENV.TIME_UNIT || "1s",
    preAllocatedVUs: Number(__ENV.MAX_VUS || 10),
    maxVUs: Number(__ENV.MAX_VUS || 10),
    stages: [
      { target: Number(__ENV.RATE || 1), duration: __ENV.RAMP_UP || "15s" },
      { target: Number(__ENV.RATE || 1), duration: __ENV.DURATION || "1m" },
    ],
  },
};

export const options = {
  scenarios: { [SCENARIO]: scenarios[SCENARIO] },
  thresholds,
  // The NCP serves a self-signed certificate and demands a client certificate.
  insecureSkipTLSVerify: true,
  tlsAuth: [{ cert: credentials.certificatePem, key: credentials.privateKeyPem }],
};

function post(url, body, soapAction, name) {
  return http.post(url, body, {
    headers: { "Content-Type": "application/soap+xml", SOAPAction: soapAction },
    tags: { name },
    responseType: "text",
  });
}

function retrieved(response, documentId) {
  return (
    /ResponseStatusType:(Partial)?Success/.test(registryStatus(response.body)) &&
    retrievedDocumentId(response.body) === documentId
  );
}

export default async function () {
  /// 1. XCPD
  const discovery = post(
    XCPD_URL,
    await xcpdRequest(),
    "urn:hl7-org:v3:PRPA_IN201305UV02:CrossGatewayPatientDiscovery",
    "xcpd",
  );
  const discovered = check(discovery, {
    "xcpd: 200": (r) => r.status === 200,
    "xcpd: patient found": (r) => patientFound(r.body),
  });
  if (!discovered) return;

  /// 2. XCA Query
  const query = post(XCA_URL, await xcaQueryRequest(), "urn:ihe:iti:2007:CrossGatewayQuery", "xca-query");
  const documents = query.status === 200 ? prescriptionDocuments(query.body) : null;
  const queried = check(query, {
    "xca-query: 200": (r) => r.status === 200,
    "xca-query: success": (r) => registryStatus(r.body).endsWith("ResponseStatusType:Success"),
    "xca-query: L3 and L1 document ids": () => documents !== null,
  });
  if (!queried) return;

  /// 3. XCA Retrieve L3
  const l3 = post(
    XCA_URL,
    await xcaRetrieveRequest({ repositoryId: documents.repositoryId, documentId: documents.l3 }),
    "urn:ihe:iti:2007:CrossGatewayRetrieve",
    "xca-retrieve-l3",
  );
  check(l3, {
    "xca-retrieve-l3: 200": (r) => r.status === 200,
    "xca-retrieve-l3: document returned": (r) => retrieved(r, documents.l3),
    "xca-retrieve-l3: structured CDA": (r) => {
      const head = documentHead(r.body);
      return head.includes("<ClinicalDocument") && head.includes("<structuredBody");
    },
  });

  /// 4. XCA Retrieve L1
  const l1 = post(
    XCA_URL,
    await xcaRetrieveRequest({ repositoryId: documents.repositoryId, documentId: documents.l1 }),
    "urn:ihe:iti:2007:CrossGatewayRetrieve",
    "xca-retrieve-l1",
  );
  check(l1, {
    "xca-retrieve-l1: 200": (r) => r.status === 200,
    "xca-retrieve-l1: document returned": (r) => retrieved(r, documents.l1),
    "xca-retrieve-l1: PDF": (r) => {
      const head = documentHead(r.body);
      return head.includes("<nonXMLBody") && head.includes("application/pdf");
    },
  });
}

export function setup() {
  console.log(`Running ${SCENARIO} against ${BASE_URL} for patient ${patientId}`);
}

// Writes results/<scenario>-<date>/{summary.md,summary.json} when run.sh set
// RESULTS_DIR, and always keeps k6's own summary on stdout.
export function handleSummary(data) {
  return summaryReport(data, {
    scenario: SCENARIO,
    config: scenarios[SCENARIO],
    steps: STEPS,
    baseUrl: BASE_URL,
    patient: patientId,
  });
}
