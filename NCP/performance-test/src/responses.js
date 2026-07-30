import encoding from "k6/encoding";

// XDSDocumentEntry.uniqueId, the ebXML slot that carries the document ids we need.
const DOCUMENT_ENTRY_UNIQUE_ID = "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab";

// Retrieve responses inline the document as base64 rather than as MTOM/XOP parts.
// Decoding only the head of it is enough to tell a structured L3 CDA from the L1
// PDF wrapper, and avoids decoding a ~450 KB PDF on every iteration.
const DOCUMENT_HEAD_BASE64_CHARS = 12000;

export function registryStatus(body) {
  const match = /RegistryResponse[^>]*\bstatus="([^"]+)"|AdhocQueryResponse[^>]*\bstatus="([^"]+)"/.exec(body);
  return match ? match[1] || match[2] : "";
}

export function patientFound(body) {
  return /<typeCode code="AA"\/>/.test(body) && body.includes("<patientPerson") && !body.includes("acknowledgementDetail");
}

export function prescriptionDocuments(body) {
  const pattern = new RegExp(
    `<ExternalIdentifier[^>]*identificationScheme="${DOCUMENT_ENTRY_UNIQUE_ID}"[^>]*value="([^"]+)"`,
    "g",
  );
  const ids = [];
  let match;
  while ((match = pattern.exec(body)) !== null) {
    ids.push(match[1]);
  }

  // DK document ids are <repositoryId>^<prescriptionId><level>: the same prescription
  // is published both as L3 (structured CDA) and as L1 (PDF).
  const l3 = ids.find((id) => id.endsWith("L3"));
  if (!l3) return null;
  const l1 = `${l3.slice(0, -1)}1`;
  if (!ids.includes(l1)) return null;

  return { repositoryId: l3.split("^")[0], l3, l1 };
}

export function documentHead(body) {
  const opening = /<(?:\w+:)?Document>/.exec(body);
  if (!opening) return "";
  const start = opening.index + opening[0].length;
  const base64 = body.slice(start, start + DOCUMENT_HEAD_BASE64_CHARS).split("<")[0];
  return encoding.b64decode(base64.slice(0, base64.length - (base64.length % 4)), "std", "s");
}
