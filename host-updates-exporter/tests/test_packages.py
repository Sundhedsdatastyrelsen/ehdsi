import support  # first: points config at a temporary host root

import unittest
from unittest import mock

import packages
from packages import PendingPackage, classify
from support import samples


LIBC = PendingPackage("libc6", "2.39-0ubuntu8.6", "2.39-0ubuntu8.9", "Ubuntu", True, "hosting")
TZDATA = PendingPackage("tzdata", "2025b-0ubuntu0.24.04", "2025b-0ubuntu0.24.04.1", "Ubuntu", False, "hosting")
DOCKER = PendingPackage("docker-ce", "5:28.5.1-1~ubuntu.24.04~noble", "5:29.0.2-1~ubuntu.24.04~noble",
                        "Docker CE", False, "ours")


class ClassifyTest(unittest.TestCase):
    def test_security_update_from_the_ubuntu_archive(self):
        package = classify("libc6", "2.39-0ubuntu8.6", "2.39-0ubuntu8.9",
                           [("Ubuntu", "noble-updates"), ("Ubuntu", "noble-security")])
        self.assertEqual(package, LIBC)

    def test_our_repository(self):
        package = classify("docker-ce", "5:28.5.1-1~ubuntu.24.04~noble", "5:29.0.2-1~ubuntu.24.04~noble",
                           [("Docker CE", "noble")])
        self.assertEqual(package, DOCKER)

    def test_newly_installed_package(self):
        package = classify("linux-image-6.8.0-90-generic", "", "6.8.0-90.91", [("Ubuntu", "noble-security")])
        self.assertEqual(package.installed_version, "")
        self.assertTrue(package.security)


class PendingSinceTest(unittest.TestCase):
    def test_keeps_first_seen_and_drops_upgraded_packages(self):
        previous = {"libc6": 1000, "openssl": 2000}
        self.assertEqual(packages.update_pending_since(previous, [LIBC, DOCKER], now=5000),
                         {"libc6": 1000, "docker-ce": 5000})


class CheckTest(unittest.TestCase):
    def setUp(self):
        packages.PENDING_SINCE_FILE.unlink(missing_ok=True)
        for name in ("reboot-required", "reboot-required.pkgs"):
            (support.HOST_ROOT / "run" / name).unlink(missing_ok=True)

    def check(self, pending, refresh_ok=True):
        with mock.patch.object(packages, "pending_packages", return_value=(pending, refresh_ok)):
            return packages.check()

    def test_counts_by_owner_and_security(self):
        counts = {(labels["owner"], labels["security"]): value
                  for labels, value in samples(self.check([LIBC, TZDATA, DOCKER]), "host_updates_pending_packages")}
        self.assertEqual(counts, {("hosting", "true"): 1, ("hosting", "false"): 1,
                                  ("ours", "true"): 0, ("ours", "false"): 1})

    def test_one_series_per_package(self):
        metrics = self.check([LIBC, DOCKER])
        [(docker, _)] = [(labels, value) for labels, value
                         in samples(metrics, "host_updates_package_pending_since_timestamp_seconds")
                         if labels["package"] == "docker-ce"]
        self.assertEqual(docker, {"hostname": "testhost", "package": "docker-ce",
                                  "current_version": "5:28.5.1-1~ubuntu.24.04~noble",
                                  "candidate_version": "5:29.0.2-1~ubuntu.24.04~noble",
                                  "origin": "Docker CE", "owner": "ours", "security": "false"})

    def test_no_package_lists_reports_no_counts_and_keeps_history(self):
        self.check([LIBC])
        history = packages.PENDING_SINCE_FILE.read_text()
        metrics = self.check(None, refresh_ok=False)
        self.assertEqual(samples(metrics, "host_updates_pending_packages"), [])
        self.assertEqual(samples(metrics, "host_updates_refresh_success"), [({"hostname": "testhost"}, 0)])
        self.assertEqual(packages.PENDING_SINCE_FILE.read_text(), history)

    def test_reboot_required(self):
        self.assertEqual(samples(self.check([]), "host_updates_reboot_required"), [({"hostname": "testhost"}, 0)])
        (support.HOST_ROOT / "run/reboot-required").touch()
        (support.HOST_ROOT / "run/reboot-required.pkgs").write_text("linux-base\nlibc6\nlinux-base\n")
        metrics = self.check([])
        self.assertEqual(samples(metrics, "host_updates_reboot_required"), [({"hostname": "testhost"}, 1)])
        self.assertEqual([labels["package"] for labels, _ in samples(metrics, "host_updates_reboot_required_package")],
                         ["libc6", "linux-base"])

    def test_os(self):
        [(labels, _)] = samples(self.check([]), "host_updates_os_info")
        self.assertEqual(labels["pretty_name"], "Ubuntu 24.04.3 LTS")


if __name__ == "__main__":
    unittest.main()
