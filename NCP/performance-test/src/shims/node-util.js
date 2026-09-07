// xml-crypto uses util.deprecate to wrap one signature-verification helper. k6 has
// no Node util module, and we only sign, so the wrapper can pass the function through.
export function deprecate(fn) {
  return fn;
}

export default { deprecate };
