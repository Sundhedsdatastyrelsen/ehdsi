"""Prints the names of the host's packages with an update pending.

Used by update-packages.sh in the ops repos, which runs on the host and
upgrades the ones we own:
    docker exec host-updates-exporter python3 /app/exporter/pending.py --owner ours"""

import argparse
import sys

import packages

parser = argparse.ArgumentParser(description=__doc__.splitlines()[0])
parser.add_argument("--owner", choices=["ours", "hosting"])
args = parser.parse_args()

pending, _ = packages.pending_packages()
if pending is None:
    sys.exit("No package lists could be fetched; see the container's log.")
print(" ".join(p.name for p in pending if args.owner in (None, p.owner)))
