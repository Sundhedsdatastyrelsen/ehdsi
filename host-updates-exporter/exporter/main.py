"""Serves the metrics on /metrics and reruns the checks on their schedules.

Between runs the last results are served; each check's *_last_run metric
tells Grafana how old they are. A failing check is logged and retried at its
next scheduled run."""

import logging
import signal
import sys
import time

from prometheus_client import start_http_server
from prometheus_client.registry import CollectorRegistry

import config
import packages
import software

HOUR = 3600

checks = [
    # (check, hours between runs)
    (packages.check, config.PACKAGES_CHECK_HOURS),
    (software.check, config.SOFTWARE_CHECK_HOURS),
]
latest_metrics = {check: [] for check, _ in checks}


class LatestMetrics:
    def collect(self):
        for metrics in list(latest_metrics.values()):
            yield from metrics


def main():
    logging.basicConfig(level=logging.INFO, format="%(asctime)s %(levelname)s %(name)s: %(message)s")
    # As PID 1, the process ignores SIGTERM unless it handles it, and
    # `docker stop` would wait out its 10 second timeout.
    signal.signal(signal.SIGTERM, lambda *_: sys.exit(0))

    registry = CollectorRegistry()
    registry.register(LatestMetrics())
    start_http_server(config.PORT, registry=registry)

    next_run = {check: 0 for check, _ in checks}
    while True:
        for check, hours in checks:
            if time.time() >= next_run[check]:
                try:
                    latest_metrics[check] = check()
                except Exception:
                    logging.exception("%s.check failed", check.__module__)
                next_run[check] = time.time() + hours * HOUR
        time.sleep(60)


if __name__ == "__main__":
    main()
