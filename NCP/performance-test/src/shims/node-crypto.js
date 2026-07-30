// xml-crypto requires("crypto") at module load for its default hash and signature
// algorithms. We register k6-backed replacements for every algorithm we use (see
// signing.js), so these are never called -- they exist to make the bundle resolve.
function unavailable() {
  throw new Error("Node crypto is not available in k6; use the algorithms registered in signing.js");
}

export const createHash = unavailable;
export const createSign = unavailable;
export const createVerify = unavailable;

export default { createHash, createSign, createVerify };
