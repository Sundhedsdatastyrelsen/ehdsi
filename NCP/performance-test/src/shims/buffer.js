// k6 has no Node globals; xml-crypto and our UTF-8 encoding both need Buffer.
import { Buffer } from "buffer";

export { Buffer };
