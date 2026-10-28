import support  # first: points config at a temporary host root

import unittest
from unittest import mock

import config

# Stand-ins for the built-in tables, so their contents can change freely.
DEFAULTS = ({"Default Origin"}, {"default/image": "default-product"}, {"default/tool": "org/tool"}, ("default.example/",))


class LoadTest(unittest.TestCase):
    def setUp(self):
        # Cleanups run last first: the real defaults are back before the reload.
        self.addCleanup(config.load)
        self.addCleanup(support.CONFIG_FILE.unlink, missing_ok=True)
        patcher = mock.patch.object(config, "DEFAULTS", DEFAULTS)
        patcher.start()
        self.addCleanup(patcher.stop)

    def load(self, text):
        support.CONFIG_FILE.write_text(text)
        config.load()

    def load_broken(self, text):
        with self.assertLogs(config.log, "ERROR"):
            self.load(text)

    def test_defaults_without_a_file(self):
        config.load()
        self.assertEqual(
            (config.OWN_APT_ORIGINS, config.ENDOFLIFE_PRODUCTS, config.GITHUB_REPOSITORIES, config.OWN_IMAGE_PREFIXES),
            DEFAULTS)

    def test_adds_to_and_overrides_the_defaults(self):
        self.load("""
own_apt_origins = ["Our Repo"]
own_image_prefixes = ["registry.example/"]
[endoflife_products]
"redis" = "redis"
"default/image" = "other-product"
[github_repositories]
"some/tool" = "some-org/tool"
""")
        self.assertEqual(config.ENDOFLIFE_PRODUCTS, {"default/image": "other-product", "redis": "redis"})
        self.assertEqual(config.GITHUB_REPOSITORIES, {"default/tool": "org/tool", "some/tool": "some-org/tool"})
        self.assertEqual(config.OWN_APT_ORIGINS, {"Default Origin", "Our Repo"})
        self.assertEqual(config.OWN_IMAGE_PREFIXES, ("default.example/", "registry.example/"))

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
        self.assertEqual(config.OWN_APT_ORIGINS, {"Default Origin"})


if __name__ == "__main__":
    unittest.main()
