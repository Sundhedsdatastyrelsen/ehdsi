import http from "k6/http";
import { check } from "k6";
import { credentials, patientId, xcaQueryRequest, xcaRetrieveRequest, xcpdRequest } from "./requests.js";
import { documentHead, patientFound, prescriptionDocuments, registryStatus, retrievedDocumentId } from "./responses.js";

const BASE_URL = __ENV.BASE_URL || "https://localhost:8443";
const XCPD_URL = `${BASE_URL}/openncp-ws-server/services/XCPD_Service/`;
const XCA_URL = `${BASE_URL}/openncp-ws-server/services/XCA_Service/`;

const P95_LIMITS_MS = {
  xcpd: 5000,
  "xca-query": 10000,
  "xca-retrieve-l3": 15000,
  "xca-retrieve-l1": 15000,
};

const thresholds = Object.fromEntries([
  ["checks", ["rate==1"]],
  ...Object.entries(P95_LIMITS_MS).map(([step, ms]) => [`http_req_duration{name:${step}}`, [`p(95)<${ms}`]]),
]);

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
  scenarios: { [__ENV.SCENARIO || "smoke"]: scenarios[__ENV.SCENARIO || "smoke"] },
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
  console.log(`Running ${__ENV.SCENARIO || "smoke"} against ${BASE_URL} for patient ${patientId}`);
}
