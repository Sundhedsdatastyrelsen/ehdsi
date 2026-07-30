import * as esbuild from "esbuild";

await esbuild.build({
  entryPoints: ["src/main.js"],
  outfile: "dist/main.js",
  bundle: true,
  format: "esm",
  target: "es2022",
  platform: "browser",
  // k6's own modules are resolved by k6 at runtime, not by the bundler.
  external: ["k6", "k6/*", "https://*"],
  inject: ["src/shims/buffer.js"],
  alias: {
    crypto: "./src/shims/node-crypto.js",
    util: "./src/shims/node-util.js",
  },
});
