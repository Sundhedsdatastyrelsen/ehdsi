import support  # first: points config at a temporary host root

import unittest

import config


class LoadTest(unittest.TestCase):
    def tearDown(self):
        support.CONFIG_FILE.unlink(missing_ok=True)
        config.load()

    def load(self, text):
        support.CONFIG_FILE.write_text(text)
        config.load()

    def load_broken(self, text):
        with self.assertLogs(config.log, "ERROR"):
            self.load(text)

    def test_defaults_without_a_file(self):
        config.load()
        self.assertEqual(config.ENDOFLIFE_PRODUCTS["grafana/grafana"], "grafana")
        self.assertEqual(config.OWN_APT_ORIGINS, {"Docker CE"})

    def test_adds_to_and_overrides_the_defaults(self):
        self.load("""
own_apt_origins = ["Our Repo"]
own_image_prefixes = ["registry.example/"]
[endoflife_products]
"redis" = "redis"
"grafana/grafana" = "grafana-oss"
[github_repositories]
"some/tool" = "some-org/tool"
""")
        self.assertEqual(config.ENDOFLIFE_PRODUCTS["redis"], "redis")
        self.assertEqual(config.ENDOFLIFE_PRODUCTS["grafana/grafana"], "grafana-oss")
        self.assertEqual(config.ENDOFLIFE_PRODUCTS["mysql"], "mysql")
        self.assertEqual(config.GITHUB_REPOSITORIES["some/tool"], "some-org/tool")
        self.assertEqual(config.OWN_APT_ORIGINS, {"Docker CE", "Our Repo"})
        self.assertTrue("registry.example/x:1".startswith(config.OWN_IMAGE_PREFIXES))

    def test_removing_an_addition_takes_it_away_again(self):
        self.load('[endoflife_products]\n"redis" = "redis"\n')
        self.load("")
        self.assertNotIn("redis", config.ENDOFLIFE_PRODUCTS)

    def test_broken_toml_keeps_the_defaults(self):
        self.load_broken('[endoflife_products\n"redis" = "redis"\n')
        self.assertNotIn("redis", config.ENDOFLIFE_PRODUCTS)

    def test_unknown_setting_ignores_the_whole_file(self):
        self.load_broken('endoflife_product = "typo"\n[github_repositories]\n"some/tool" = "some-org/tool"\n')
        self.assertNotIn("some/tool", config.GITHUB_REPOSITORIES)

    def test_wrong_type_keeps_the_defaults(self):
        self.load_broken('own_apt_origins = "Our Repo"\n')
        self.assertEqual(config.OWN_APT_ORIGINS, {"Docker CE"})


if __name__ == "__main__":
    unittest.main()
