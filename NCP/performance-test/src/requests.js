import { uuidv4 } from "https://jslib.k6.io/k6-utils/1.4.0/index.js";
import { signAssertion } from "./signing.js";

// The test-tool is the reference implementation whose requests the server is known
// to accept; its templates are read directly so there is only one copy of them.
const TEST_TOOL = "../../test-tool";

export const credentials = {
  certificatePem: open(`${TEST_TOOL}/testcert.cer`),
  privateKeyPem: open(`${TEST_TOOL}/testcert.p8.pem`),
};

const templates = {
  xcpdHcp: open(`${TEST_TOOL}/templates/xcpd/hcp.xml`),
  xcpdRequest: open(`${TEST_TOOL}/templates/xcpd/request.xml`),
  xcaHcp: open(`${TEST_TOOL}/templates/xca/hcp.xml`),
  xcaTrc: open(`${TEST_TOOL}/templates/xca/trc.xml`),
  xcaQuery: open(`${TEST_TOOL}/templates/xca/query/request-ep.xml`),
  xcaRetrieve: open(`${TEST_TOOL}/templates/xca/retrieve/request-ep.xml`),
};

const TEMPLATE_PATIENT_ID = "0410009234";

export const patientId = __ENV.PATIENT_ID || TEMPLATE_PATIENT_ID;

// The templates bake the patient CPR into the XCPD query, the TRC assertion and the
// XCA query. Substituting it keeps the test-tool templates as the single source of
// truth while allowing runs against another patient.
function forPatient(template) {
  return template.split(TEMPLATE_PATIENT_ID).join(patientId);
}

// The templates are Selmer templates, but the only feature they use is
// {{placeholder}} substitution; |safe merely suppresses Selmer's HTML escaping.
function render(template, values) {
  return template.replace(/\{\{([\w-]+)(?:\|safe)?\}\}/g, (_, key) => values[key]);
}

// An XML declaration is legal only at the start of a document, so it has to go
// before an assertion is embedded in a SOAP header.
function withoutXmlDeclaration(xml) {
  return xml.replace(/^\s*<\?xml[^?]*\?>\s*/, "");
}

function validityWindow() {
  const now = new Date();
  return {
    "issue-instant": now.toISOString(),
    "authn-instant": now.toISOString(),
    "not-before": new Date(now.getTime() - 60 * 1000).toISOString(),
    "not-on-or-after": new Date(now.getTime() + 60 * 60 * 1000).toISOString(),
  };
}

function assertion(template, values) {
  return withoutXmlDeclaration(render(forPatient(template), { ...validityWindow(), ...values }));
}

async function hcpAssertion(template) {
  const id = `id${uuidv4()}`;
  return { id, xml: await signAssertion(assertion(template, { "assertion-id": id }), credentials) };
}

async function xcaAssertions() {
  const hcp = await hcpAssertion(templates.xcaHcp);
  const trc = assertion(templates.xcaTrc, {
    "assertion-id": `id${uuidv4()}`,
    "hcp-assertion-id": hcp.id,
  });
  return { "hcp-assertion": hcp.xml, "trc-assertion": await signAssertion(trc, credentials) };
}

export async function xcpdRequest() {
  const hcp = await hcpAssertion(templates.xcpdHcp);
  return render(forPatient(templates.xcpdRequest), {
    "message-id": `uuid:${uuidv4()}`,
    "hcp-assertion": hcp.xml,
  });
}

export async function xcaQueryRequest() {
  return render(forPatient(templates.xcaQuery), {
    "message-id": `uuid:${uuidv4()}`,
    ...(await xcaAssertions()),
  });
}

export async function xcaRetrieveRequest({ repositoryId, documentId }) {
  const envelope = render(templates.xcaRetrieve, {
    "message-id": `uuid:${uuidv4()}`,
    ...(await xcaAssertions()),
  });
  return envelope
    .replace(/(<xdsb:RepositoryUniqueId>)[^<]*/, `$1${repositoryId}`)
    .replace(/(<xdsb:DocumentUniqueId>)[^<]*/, `$1${documentId}`);
}
