#!/usr/bin/env bash
# Packs the value-set translation files that the CDA display tool needs from a TSAM export into the portal.
# See "Value sets for the CDA display tool" in README.md for how to produce the export.
#
# Usage: scripts/update-value-sets.sh <export-dir> <source-description>
#   <export-dir>          directory with the TSAM exporter output (the NCP's EpsosRepository)
#   <source-description>  where the export came from, e.g. "NCP test, exported 2026-10-15", recorded in VERSION

set -o errexit
set -o nounset
set -o pipefail

if [[ $# -ne 2 ]]; then
    echo "Usage: $0 <export-dir> <source-description>" >&2
    exit 1
fi

export_dir=$1
source_description=$2
project_dir=$(cd "$(dirname "$0")/.." && pwd)
xsl_dir="$project_dir/src/main/resources/cda-display-tool"
target_dir="$project_dir/src/main/resources/value-sets"

# The display tool loads value sets with document() on file names that are literals in the stylesheets
files=$(grep -rhoE '1\.3\.6\.1\.4\.1\.12559\.11\.10\.1\.3\.1\.42\.[0-9]+\.xml' "$xsl_dir" | sort -u)

missing=0
for f in $files; do
    if [[ ! -f "$export_dir/$f" ]]; then
        echo "Missing in export: $f" >&2
        missing=1
    fi
done
if [[ $missing -ne 0 ]]; then
    exit 1
fi

work_dir=$(mktemp -d)
trap 'rm -rf "$work_dir"' EXIT
for f in $files; do
    cp "$export_dir/$f" "$work_dir/$f"
done
# Fixed timestamps and no extra file attributes, so the same export gives the same zip
(cd "$work_dir" && TZ=UTC touch -t 198001010000 $files && TZ=UTC zip -q -X -D -9 value-sets.zip $files)

mkdir -p "$target_dir"
mv "$work_dir/value-sets.zip" "$target_dir/value-sets.zip"
printf '%s\n' "$source_description" > "$target_dir/VERSION"

echo "Packed $(echo "$files" | wc -l | tr -d ' ') value sets into $target_dir/value-sets.zip ($(du -h "$target_dir/value-sets.zip" | cut -f1))"
