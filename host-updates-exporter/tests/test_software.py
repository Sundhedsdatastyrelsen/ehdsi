import support  # first: points config at a temporary host root

import unittest
from datetime import date, datetime, timezone
from unittest import mock

import software
from software import Software
from support import samples

GRAFANA_RELEASES = [
    {"name": "12.1", "releaseDate": "2025-07-22", "isEol": False, "eolFrom": "2099-01-01", "latest": {"name": "12.1.10"}},
    {"name": "12.0", "releaseDate": "2025-05-05", "isEol": True, "eolFrom": "2026-02-05", "latest": {"name": "12.0.10"}},
]
CADDY_RELEASES = [
    {"name": "2", "releaseDate": "2020-05-04", "isEol": False, "eolFrom": None, "latest": {"name": "2.11.4"}},
    {"name": "1", "releaseDate": "2016-01-01", "isEol": True, "eolFrom": "2020-07-01", "latest": {"name": "1.0.5"}},
]


class ImageRefTest(unittest.TestCase):
    def test_split(self):
        self.assertEqual(software.split_image_ref("grafana/grafana:12.0.2"), ("grafana/grafana", "12.0.2"))
        self.assertEqual(software.split_image_ref("docker.io/library/mysql:9"), ("mysql", "9"))
        self.assertEqual(software.split_image_ref("hashicorp/vault"), ("hashicorp/vault", "latest"))
        self.assertEqual(software.split_image_ref("localhost:5000/tool"), ("localhost:5000/tool", "latest"))


class RunningVersionTest(unittest.TestCase):
    def test_tag(self):
        self.assertEqual(software.running_version("grafana/grafana", "12.0.2", {}), ("12.0.2", "tag"))

    def test_env_var_refines_a_floating_tag(self):
        config = {"Env": ["PATH=/usr/bin", "MYSQL_VERSION=9.7.2-1.el9"]}
        self.assertEqual(software.running_version("mysql", "9", config), ("9.7.2", "env"))

    def test_label_for_latest(self):
        self.assertEqual(software.running_version("hashicorp/vault", "latest", {"Labels": {"version": "2.1.1"}}),
                         ("2.1.1", "label"))

    def test_label_contradicting_the_tag_is_ignored(self):
        config = {"Labels": {"org.opencontainers.image.version": "24.04"}}
        self.assertEqual(software.running_version("grafana/alloy", "v1.12.2", config), ("1.12.2", "tag"))

    def test_unknown(self):
        self.assertEqual(software.running_version("busybox", "latest", {}), ("", "none"))


class EndOfLifeTest(unittest.TestCase):
    def test_most_specific_cycle(self):
        self.assertEqual(software.find_cycle(GRAFANA_RELEASES, "12.0.2")["name"], "12.0")
        self.assertEqual(software.find_cycle(CADDY_RELEASES, "2.11.4")["name"], "2")
        self.assertIsNone(software.find_cycle(GRAFANA_RELEASES, "11.6.1"))

    def test_apply(self):
        grafana = Software("image", "grafana/grafana", "12.0.2", "tag", product="grafana")
        software.apply_endoflife(grafana, GRAFANA_RELEASES)
        self.assertEqual((grafana.cycle, grafana.end_of_life, grafana.latest_in_cycle, grafana.latest),
                         ("12.0", date(2026, 2, 5), "12.0.10", "12.1.10"))

    def test_no_end_of_life_date_yet(self):
        caddy = Software("image", "caddy", "2.11.4", "env", product="caddy")
        software.apply_endoflife(caddy, CADDY_RELEASES)
        self.assertIsNone(caddy.end_of_life)


class UpdateAvailableTest(unittest.TestCase):
    def update(self, version, latest_in_cycle, latest, kind="image"):
        return software.update_available(
            Software(kind, "x", version, "tag", latest_in_cycle=latest_in_cycle, latest=latest))

    def test_cases(self):
        self.assertEqual(self.update("12.0.2", "12.0.10", "13.2.3"), "patch")
        self.assertEqual(self.update("1.12.2", "", "1.20.1"), "newer")  # GitHub: no cycles
        self.assertEqual(self.update("2.1.1", "2.1.1", "2.1.1"), "none")
        self.assertEqual(self.update("3.5", "3.5.12", "3.7.8"), "unknown")  # floating tag
        self.assertEqual(self.update("24.04", "24.04.5", "26.04.1", kind="os"), "newer")


class RunningImagesTest(unittest.TestCase):
    CONTAINERS = {
        "c1": ("/grafana", "grafana/grafana:12.0.2", "img-grafana"),
        "c2": ("/grafana-2", "grafana/grafana:12.0.2", "img-grafana"),
        "c3": ("/init", "busybox", "img-busybox"),
        "c4": ("/national-connector", "eppsregistry.azurecr.io/sundhedsdatastyrelsen/ehdsi/national-connector:build-334", "img-nc"),
    }

    def docker_get(self, path):
        if path == "/containers/json":
            return [{"Id": cid} for cid in self.CONTAINERS]
        if path.startswith("/containers/"):
            name, ref, image = self.CONTAINERS[path.split("/")[2]]
            return {"Name": name, "Config": {"Image": ref}, "Image": image}
        return {"Config": {}}

    def test_groups_containers_skips_own_images_and_marks_untracked(self):
        with mock.patch.object(software, "docker_get", self.docker_get):
            images = {image.name: image for image in software.running_images()}
        self.assertEqual(sorted(images), ["busybox", "grafana/grafana"])
        self.assertEqual(images["grafana/grafana"].containers, ["grafana", "grafana-2"])
        self.assertEqual(images["busybox"].status, "untracked")


class LookUpTest(unittest.TestCase):
    def test_github_release(self):
        alloy = Software("image", "grafana/alloy", "1.12.2", "tag")
        with mock.patch.object(software, "fetch_json", return_value={"tag_name": "v1.20.1"}):
            software.look_up(alloy)
        self.assertEqual((alloy.latest, alloy.status), ("1.20.1", "ok"))

    def test_unreachable(self):
        grafana = Software("image", "grafana/grafana", "12.0.2", "tag", product="grafana")
        with mock.patch.object(software, "fetch_json", return_value=None):
            software.look_up(grafana)
        self.assertEqual(grafana.status, "lookup_failed")

    def test_falls_back_to_the_last_response(self):
        with mock.patch("urllib.request.urlopen", side_effect=OSError("offline")):
            self.assertIsNone(software.fetch_json("https://example.invalid/x", "test.json"))
            software.RESPONSE_CACHE_DIR.mkdir(parents=True, exist_ok=True)
            (software.RESPONSE_CACHE_DIR / "test.json").write_text('{"cached": true}')
            self.assertEqual(software.fetch_json("https://example.invalid/x", "test.json"), {"cached": True})


class MetricsTest(unittest.TestCase):
    def test_end_of_life_once_per_version(self):
        grafana = Software("image", "grafana/grafana", "12.0.2", "tag", image="grafana/grafana:12.0.2",
                           product="grafana", cycle="12.0", end_of_life=date(2026, 2, 5))
        same_under_another_ref = Software(**{**grafana.__dict__, "image": "docker.io/grafana/grafana:12.0.2"})
        metrics = software.metrics("testhost", [grafana, same_under_another_ref], now=0)
        self.assertEqual(len(samples(metrics, "host_updates_software_info")), 2)
        self.assertEqual(samples(metrics, "host_updates_software_eol_timestamp_seconds"), [
            ({"hostname": "testhost", "kind": "image", "name": "grafana/grafana", "version": "12.0.2", "cycle": "12.0"},
             datetime(2026, 2, 5, tzinfo=timezone.utc).timestamp()),
        ])

if __name__ == "__main__":
    unittest.main()
