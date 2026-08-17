import { sha256 } from "k6/crypto";
import encoding from "k6/encoding";
import { SignedXml } from "xml-crypto";

const RSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
const SHA256 = "http://www.w3.org/2001/04/xmlenc#sha256";
const EXCLUSIVE_C14N = "http://www.w3.org/2001/10/xml-exc-c14n#";
const ENVELOPED_SIGNATURE = "http://www.w3.org/2000/09/xmldsig#enveloped-signature";

// xml-crypto's algorithm interfaces are synchronous, but WebCrypto (a k6 global since
// v1.0) is not. Digests therefore come from k6/crypto (synchronous), while signing uses
// the callback form of getSignature, which xml-crypto awaits internally.
class K6Sha256 {
  getHash(xml) {
    return sha256(xml, "base64");
  }

  getAlgorithmName() {
    return SHA256;
  }
}

// k6's WebCrypto does not accept the polyfilled Buffer as a TypedArray, so the UTF-8
// bytes come from TextEncoder, which yields a genuine Uint8Array.
const utf8 = new TextEncoder();

class K6RsaSha256 {
  getSignature(signedInfo, privateKey, callback) {
    crypto.subtle
      .sign({ name: "RSASSA-PKCS1-v1_5" }, privateKey, utf8.encode(signedInfo))
      .then((signature) => callback(null, encoding.b64encode(signature)))
      .catch((error) => callback(error));
  }

  getAlgorithmName() {
    return RSA_SHA256;
  }
}

let importedKey;

function signingKey(privateKeyPem) {
  if (!importedKey) {
    const der = encoding.b64decode(privateKeyPem.replace(/-----[^-]+-----/g, "").replace(/\s+/g, ""));
    importedKey = crypto.subtle.importKey(
      "pkcs8",
      der,
      { name: "RSASSA-PKCS1-v1_5", hash: "SHA-256" },
      false,
      ["sign"],
    );
  }
  return importedKey;
}

export async function signAssertion(assertionXml, { privateKeyPem, certificatePem }) {
  const signedXml = new SignedXml({
    privateKey: await signingKey(privateKeyPem),
    publicCert: certificatePem,
    signatureAlgorithm: RSA_SHA256,
    canonicalizationAlgorithm: EXCLUSIVE_C14N,
  });
  signedXml.HashAlgorithms = { [SHA256]: K6Sha256 };
  signedXml.SignatureAlgorithms = { [RSA_SHA256]: K6RsaSha256 };
  signedXml.addReference({
    xpath: "/*",
    transforms: [ENVELOPED_SIGNATURE, EXCLUSIVE_C14N],
    digestAlgorithm: SHA256,
  });

  return new Promise((resolve, reject) => {
    signedXml.computeSignature(
      assertionXml,
      // SAML 2.0 requires ds:Signature to be the first element after saml:Issuer.
      { prefix: "ds", location: { reference: "/*/*[local-name()='Issuer']", action: "after" } },
      (error) => (error ? reject(error) : resolve(signedXml.getSignedXml())),
    );
  });
}
