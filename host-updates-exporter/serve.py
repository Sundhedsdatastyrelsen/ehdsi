"""Serve the *.prom files in METRICS_DIR, concatenated, on /metrics."""
import glob
import os
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer

METRICS_DIR = os.environ.get("HOST_UPDATES_METRICS_DIR", "/app/metrics")
PORT = int(os.environ.get("HOST_UPDATES_PORT", "9090"))


class Handler(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path.split("?")[0] != "/metrics":
            self.send_error(404)
            return
        body = b"".join(
            open(path, "rb").read()
            for path in sorted(glob.glob(os.path.join(METRICS_DIR, "*.prom")))
        )
        self.send_response(200)
        self.send_header("Content-Type", "text/plain; version=0.0.4; charset=utf-8")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def log_message(self, *args):
        pass  # a scrape every 15 s would flood the container log


ThreadingHTTPServer(("", PORT), Handler).serve_forever()
