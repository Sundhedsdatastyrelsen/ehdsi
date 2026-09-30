#!/usr/bin/env bash
# Write end-of-life and latest-version metrics for the host.
#
# Covers the images of the host's running containers (except our own, see
# OWN_IMAGE_PREFIXES in lib/config.sh), the host's Ubuntu release and the
# Docker Engine. Looks each up on endoflife.date (EOL_PRODUCTS), or on GitHub
# releases for images it doesn't cover (GITHUB_RELEASES), and writes
# host_software.prom to METRICS_DIR. Run by run.sh on start and every
# HOST_UPDATES_EOL_INTERVAL seconds.
#
# Usage:
#   ./eol.sh

# shellcheck source=SCRIPTDIR/lib/common.sh
source "$(dirname "${BASH_SOURCE[0]}")/lib/common.sh"

case "${1:-}" in
    -h|--help) sed --quiet '2,/^$/p' "$0" | sed 's/^# \?//'; exit 0 ;;
    "") ;;
    *) echo "Unknown argument: $1" >&2; exit 1 ;;
esac

EOL_API="${HOST_UPDATES_EOL_API:-https://endoflife.date/api/v1/products}"
GITHUB_API="${HOST_UPDATES_GITHUB_API:-https://api.github.com/repos}"
CACHE_DIR="$STATE_DIR/eol-cache"
HOSTNAME_LABEL="$(host_name)"
NOW="$(date +%s)"

# Leading dotted version: v3.7 -> 3.7, 9.7.2-1.el9 -> 9.7.2, latest -> "".
version_of() {
    [[ $1 =~ ^v?([0-9]+(\.[0-9]+)*) ]] && echo "${BASH_REMATCH[1]}" || true
}

# $1 is $2, or a more specific version of it (9.7.2 of 9, 3.7.13 of 3.7).
refines() {
    [[ -z $2 || $1 == "$2" || $1 == "$2".* ]]
}

# GETs a URL, falling back to the copy from the last successful run so a
# transient outage doesn't blank the metrics. Prints nothing if neither works.
fetch_cached() {
    local url="$1" cache="$CACHE_DIR/$2" body
    if body="$(curl --silent --show-error --fail --location --max-time 30 "$url" 2>/dev/null)"; then
        printf '%s' "$body" > "$cache"
        printf '%s' "$body"
    elif [[ -f "$cache" ]]; then
        log "WARNING: $url unreachable; using cached copy from $(date -r "$cache" '+%Y-%m-%d')"
        cat "$cache"
    else
        log "WARNING: $url unreachable and nothing cached"
    fi
}

# The running image's version: its <NAME>_VERSION env var or version label
# when they agree with the tag (grafana/alloy's label is its Ubuntu base
# version), else the tag itself. Prints "version|source".
resolve_version() {
    local image_id="$1" repo="$2" tag="$3" tag_version env_name config v
    tag_version="$(version_of "$tag")"
    env_name="$(basename "$repo" | tr '[:lower:]-' '[:upper:]_')_VERSION"

    config="$(docker_api "/images/$image_id/json" | jq '.Config // {}')"

    v="$(version_of "$(jq -r --arg n "$env_name" \
        '.Env // [] | .[] | select(startswith($n + "=")) | sub("^[^=]*="; "")' <<< "$config" | head -1)")"
    if [[ -n $v ]] && refines "$v" "$tag_version"; then echo "$v|env"; return; fi

    v="$(version_of "$(jq -r '.Labels // {} | .["org.opencontainers.image.version"] // .version // ""' <<< "$config")")"
    if [[ -n $v ]] && refines "$v" "$tag_version"; then echo "$v|label"; return; fi

    if [[ -n $tag_version ]]; then echo "$tag_version|tag"; return; fi
    echo "|none"
}

# Prints "cycle|eol_date|is_eol|latest_in_cycle|latest" for a version of an
# endoflife.date product, or nothing if the lookup or cycle match fails.
# The cycle is the longest release name the version refines (12.0.2 -> 12.0,
# caddy 2.11.4 -> 2).
eol_lookup() {
    local product="$1" version="$2" json
    json="$(fetch_cached "$EOL_API/$product" "$product.json")"
    [[ -z $json ]] && return 0
    jq -r --arg v "$version" '
        .result.releases as $all
        | ($all | max_by(.releaseDate) | .latest.name // "") as $latest
        | [$all[] | select(.name as $c | $v == $c or ($v | startswith($c + ".")))]
        | max_by(.name | length) // empty
        | [.name, (.eolFrom // ""), (.isEol | tostring), (.latest.name // ""), $latest]
        | join("|")' <<< "$json"
}

github_latest() {
    local repo="$1" json
    json="$(fetch_cached "$GITHUB_API/$repo/releases/latest" "github-${repo//\//_}.json")"
    [[ -z $json ]] && return 0
    [[ $(jq -r '.tag_name // ""' <<< "$json") =~ ([0-9]+(\.[0-9]+)+) ]] && echo "${BASH_REMATCH[1]}" || true
}

info_lines=""
eol_lines=""
declare -A seen_eol=()
count=0

# Emits the metrics for one piece of software.
#   $1 kind (image|os|runtime)  $2 name  $3 image ref  $4 containers
#   $5 version  $6 version source  $7 endoflife.date product  $8 GitHub repo
report() {
    local kind="$1" name="$2" image="$3" containers="$4" version="$5" source="$6" product="$7" github="$8"
    local status=ok cycle="" eol_date="" is_eol="" latest_in_cycle="" latest="" found=""
    if [[ -z $product && -z $github ]]; then
        status=untracked
    elif [[ -z $version ]]; then
        status=version_unknown
    elif [[ -n $product ]]; then
        found="$(eol_lookup "$product" "$version")"
        if [[ -n $found ]]; then
            IFS='|' read -r cycle eol_date is_eol latest_in_cycle latest <<< "$found"
        elif [[ -f "$CACHE_DIR/$product.json" ]]; then
            status=cycle_not_found    # product data available, version matches no cycle
        else
            status=lookup_failed
        fi
    else
        latest="$(github_latest "$github")"
        [[ -z $latest ]] && status=lookup_failed
    fi

    # Ubuntu point releases (24.04.5) are just apt updates, which collect.sh
    # reports; only a newer release cycle is news here.
    [[ $kind == os ]] && latest_in_cycle=""

    # patch: a newer release of the same cycle; newer: only a newer cycle;
    # unknown: a floating tag (3.5) whose exact patch level we can't tell.
    local update=none
    if [[ -z $version || -z $latest ]]; then
        update=unknown
    elif [[ -n $latest_in_cycle && $version != "$latest_in_cycle" ]]; then
        refines "$latest_in_cycle" "$version" && update=unknown || update=patch
    elif [[ $version != "$latest" ]]; then
        refines "$latest" "$version" && update=unknown || update=newer
    fi

    local h="hostname=\"$(esc "$HOSTNAME_LABEL")\",kind=\"$kind\",name=\"$(esc "$name")\",version=\"$(esc "$version")\""
    info_lines+="host_updates_software_info{$h,image=\"$(esc "$image")\",containers=\"$(esc "$containers")\",version_source=\"$source\",product=\"$(esc "$product${github:+github:$github}")\",cycle=\"$(esc "$cycle")\",latest_in_cycle=\"$(esc "$latest_in_cycle")\",latest=\"$(esc "$latest")\",update=\"$update\",status=\"$status\"} 1"$'\n'

    local ts=""
    if [[ -n $eol_date ]]; then
        ts="$(date --date="$eol_date" +%s)"
    elif [[ $is_eol == true ]]; then
        ts=0    # end of life, date unknown
    fi
    if [[ -n $ts && -z ${seen_eol[$h]:-} ]]; then
        seen_eol[$h]=1
        eol_lines+="host_updates_software_eol_timestamp_seconds{$h,cycle=\"$(esc "$cycle")\"} $ts"$'\n'
    fi
    count=$(( count + 1 ))
}

mkdir --parents "$CACHE_DIR"

# ── Host ──
# shellcheck disable=SC1091
report os ubuntu "" "" "$(. "${HOST_UPDATES_OS_RELEASE:-$HOST_ROOT/etc/os-release}" && echo "${VERSION_ID:-}")" host ubuntu ""
report runtime docker-engine "" "" "$(version_of "$(docker_api /version | jq -r '.Version // ""' || true)")" host docker-engine ""

# ── Images of running containers, grouped by image ──
declare -A containers_of=() image_id_of=()
while IFS='|' read -r cname ref image_id; do
    [[ -z $ref ]] && continue
    containers_of[$ref]+="${containers_of[$ref]:+,}${cname#/}"
    image_id_of[$ref]=$image_id
done < <(docker_api /containers/json | jq -r '.[].Id' | while read -r id; do
    # Config.Image is the reference the container was created from; the
    # list's .Image turns into an image id once that tag moves.
    docker_api "/containers/$id/json" | jq -r '"\(.Name)|\(.Config.Image)|\(.Image)"'
done)

for ref in "${!containers_of[@]}"; do
    own=false
    for prefix in "${OWN_IMAGE_PREFIXES[@]}"; do
        [[ $ref == "$prefix"* ]] && own=true
    done
    $own && continue

    # docker.io/library/mysql:9 -> repo mysql, tag 9
    repo="${ref%@*}"
    tag=latest
    if [[ ${repo##*/} == *:* ]]; then
        tag="${repo##*:}"
        repo="${repo%:*}"
    fi
    repo="${repo#docker.io/}"
    repo="${repo#library/}"

    IFS='|' read -r version source <<< "$(resolve_version "${image_id_of[$ref]}" "$repo" "$tag")"
    report image "$repo" "$ref" "${containers_of[$ref]}" "$version" "$source" \
        "${EOL_PRODUCTS[$repo]:-}" "${GITHUB_RELEASES[$repo]:-}"
done

h="hostname=\"$(esc "$HOSTNAME_LABEL")\""
{
    echo "# HELP host_updates_software_info Software on the host (images of running containers, OS, Docker Engine) with its release cycle and latest versions. update: patch (newer release in the same cycle), newer (newer cycle only), none, unknown. status: ok, untracked, version_unknown, lookup_failed, cycle_not_found."
    echo "# TYPE host_updates_software_info gauge"
    printf '%s' "$info_lines"
    echo "# HELP host_updates_software_eol_timestamp_seconds When the release cycle of the running version reaches end of life, per endoflife.date (0: end of life, date unknown)."
    echo "# TYPE host_updates_software_eol_timestamp_seconds gauge"
    printf '%s' "$eol_lines"
    echo "# HELP host_updates_software_last_run_timestamp_seconds When eol.sh last wrote these metrics."
    echo "# TYPE host_updates_software_last_run_timestamp_seconds gauge"
    echo "host_updates_software_last_run_timestamp_seconds{$h} $NOW"
} | write_metrics host_software.prom

log "Reported $count piece(s) of software"
