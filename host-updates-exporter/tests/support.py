"""Points config at a temporary host root and state directory. Imported by
every test module before anything imports config."""

import os
import tempfile
from pathlib import Path

root = Path(tempfile.mkdtemp())
HOST_ROOT = root / "host"
STATE_DIR = root / "state"

for directory in ("etc", "run", "var/lib/dpkg"):
    (HOST_ROOT / directory).mkdir(parents=True)
(HOST_ROOT / "etc/hostname").write_text("testhost\n")
(HOST_ROOT / "etc/os-release").write_text('PRETTY_NAME="Ubuntu 24.04.3 LTS"\nVERSION_ID="24.04"\n')
(HOST_ROOT / "var/lib/dpkg/status").write_text("")

os.environ["HOST_ROOT"] = str(HOST_ROOT)
os.environ["STATE_DIR"] = str(STATE_DIR)



def samples(metrics, name):
    """(labels, value) of each sample of one metric, from a check's metrics."""
    return [(sample.labels, sample.value)
            for family in metrics for sample in family.samples if sample.name == name]
