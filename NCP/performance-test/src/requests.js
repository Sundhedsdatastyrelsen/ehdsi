import { uuidv4 } from "https://jslib.k6.io/k6-utils/1.4.0/index.js";
import { signAssertion } from "./signing.js";

import certificatePem from "../testcert.cer";
import privateKeyPem from "../testcert.p8.pem";
import xcpdHcp from "../templates/xcpd/hcp.xml";
import xcpdRequestTemplate from "../templates/xcpd/request.xml";
import xcaHcp from "../templates/xca/hcp.xml";
import xcaTrc from "../templates/xca/trc.xml";
import xcaQuery from "../templates/xca/query/request-ep.xml";
import xcaRetrieve from "../templates/xca/retrieve/request-ep.xml";

export const credentials = { certificatePem, privateKeyPem };

const templates = {
  xcpdHcp,
  xcpdRequest: xcpdRequestTemplate,
  xcaHcp,
  xcaTrc,
  xcaQuery,
  xcaRetrieve,
};

const DEFAULT_PATIENT_ID = "0410009234"; // Sofie Bach

export const patientId = __ENV.PATIENT_ID || DEFAULT_PATIENT_ID;

// Everything that varies between runs or between iterations is a {{placeholder}} in the
// templates, and this is the only thing that fills them in. Unknown keys throw rather
// than substituting "undefined", which the server would reject far from the cause.
function render(template, values) {
  return template.replace(/\{\{([\w-]+)\}\}/g, (_, key) => {
    if (!(key in values)) throw new Error(`No value for template placeholder {{${key}}}`);
    return values[key];
  });
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
  return withoutXmlDeclaration(render(template, { ...validityWindow(), "patient-id": patientId, ...values }));
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
  return render(templates.xcpdRequest, {
    "message-id": `uuid:${uuidv4()}`,
    "patient-id": patientId,
    "hcp-assertion": hcp.xml,
  });
}

export async function xcaQueryRequest() {
  return render(templates.xcaQuery, {
    "message-id": `uuid:${uuidv4()}`,
    "patient-id": patientId,
    ...(await xcaAssertions()),
  });
}

export async function xcaRetrieveRequest({ repositoryId, documentId }) {
  return render(templates.xcaRetrieve, {
    "message-id": `uuid:${uuidv4()}`,
    "repository-id": repositoryId,
    "document-id": documentId,
    ...(await xcaAssertions()),
  });
}
