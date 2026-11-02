"""Which host this is, read through the bind mounts."""

import config


def name():
    return (config.HOST_ROOT / "etc/hostname").read_text().strip()


def os_release():
    """The host's /etc/os-release as a dict, e.g. {"VERSION_ID": "24.04", ...}."""
    lines = (config.HOST_ROOT / "etc/os-release").read_text().splitlines()
    return {key: value.strip('"') for key, value in (line.split("=", 1) for line in lines if "=" in line)}
