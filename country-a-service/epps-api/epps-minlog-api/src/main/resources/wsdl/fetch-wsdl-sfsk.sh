#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

#
# Script to fetch WSDL and XSD files
# Includes retry logic for failed downloads
# Original file developed for "Det gode CPR opslag", which has some legacy functionality I don't dare replace, since
# the script is already complicated enough that it is hard to parse.
# Made a bit more readable with help from AI, as evident by the comments and echo statements
#

MAX_RETRIES=3
RETRY_DELAY=2  # seconds

function resolve_url() {
    local base_url="$1"
    local relative_url="$2"

    # If the URL is already absolute, return it as is
    if [[ "$relative_url" =~ ^https?:// ]]; then
        echo "$relative_url"
        return
    fi

    # Get the directory part of the base URL
    local base_dir=$(dirname "$base_url")

    # Handle different relative path cases
    if [[ "$relative_url" == /* ]]; then
        # Absolute path from domain root
        local domain=$(echo "$base_url" | sed -E 's|(https?://[^/]+).*|\1|')
        echo "${domain}${relative_url}"
    elif [[ "$relative_url" == ./ ]]; then
        # Current directory
        echo "$base_dir"
    elif [[ "$relative_url" == ../* ]]; then
        # Parent directory
        local parent_dir=$(dirname "$base_dir")
        local relative_path=${relative_url#../}
        echo "$parent_dir/$relative_path"
    else
        # Simple relative path
        echo "$base_dir/$relative_url"
    fi
}

function fetchFile() {
    local url="$1"
    local base_url="$2"
    local resolved_url=$(resolve_url "$base_url" "$url")

    # Extract filename from URL, handling query parameters that contain filenames
    local filename
    if [[ "$url" == *"?xsd="* ]]; then
        # Extract filename from ?xsd= parameter
        filename=$(echo "$url" | sed -E 's|.*\?xsd=([^&]*).*|\1|')
    else
        # Extract filename from URL path, removing query parameters
        filename=$(echo "${url##*/}" | sed -E 's|\?.*$||')
    fi
    local file="xsd/${filename}"

    local retry_count=0
    local success=false

    if [ ! -f "$file" ] || [ ! -s "$file" ]; then
        while [ $retry_count -lt $MAX_RETRIES ] && [ "$success" = false ]; do
            echo "Fetching $resolved_url -> $file (Attempt $((retry_count + 1))/$MAX_RETRIES)"
            if wget -q "$resolved_url" -O "$file" && [ -s "$file" ]; then
                success=true
                echo "Successfully downloaded $file"
            else
                retry_count=$((retry_count + 1))
                if [ $retry_count -lt $MAX_RETRIES ]; then
                    echo "Download failed, retrying in $RETRY_DELAY seconds..."
                    sleep $RETRY_DELAY
                else
                    echo "Failed to download $resolved_url after $MAX_RETRIES attempts"
                    return 1
                fi
            fi
        done

        if [ "$success" = true ]; then
            local subFiles=$(grep -Po 'schemaLocation="\K.*?(?=")' "$file")
            for subUrl in $subFiles; do
                fetchFile "$subUrl" "$resolved_url"
            done
        fi
    fi
}

function fetchWSDL() {
    local wsdl_file="minlog.wsdl"
    local initial_url="http://test1.ekstern-test.nspop.dk:8080/minlog2-registration/20250312/RegisterService?wsdl"
    local retry_count=0
    local success=false

    echo "Processing WSDL..."

    if [ ! -f "$wsdl_file" ] || [ ! -s "$wsdl_file" ]; then
        while [ $retry_count -lt $MAX_RETRIES ] && [ "$success" = false ]; do
            echo "Fetching $initial_url (Attempt $((retry_count + 1))/$MAX_RETRIES)"
            if wget -q "$initial_url" -O "$wsdl_file" && [ -s "$wsdl_file" ]; then
                success=true
                echo "Successfully downloaded $wsdl_file"
            else
                retry_count=$((retry_count + 1))
                if [ $retry_count -lt $MAX_RETRIES ]; then
                    echo "Download failed, retrying in $RETRY_DELAY seconds..."
                    sleep $RETRY_DELAY
                else
                    echo "Failed to download $initial_url after $MAX_RETRIES attempts"
                    return 1
                fi
            fi
        done
    fi

    if [ ! -d "xsd" ]; then
        mkdir xsd
    fi

    if [ "$success" = true ] || [ -s "$wsdl_file" ]; then
        local initialFiles=$(grep -Po 'schemaLocation="\K.*?(?=")' "$wsdl_file")
        for file in $initialFiles; do
            fetchFile "$file" "$initial_url"
        done

        # Fix schemaLocation in main wsdl
        sed -Ei 's|schemaLocation="[^"]*/([^"]+)"|schemaLocation="./xsd/\1"|g' "$wsdl_file"
        # Handle ?xsd= parameters in schemaLocation
        sed -Ei 's|schemaLocation="[^"]*\?xsd=([^"]+)"|schemaLocation="./xsd/\1"|g' "$wsdl_file"

        # Fix schemaLocation in xsds
        for file in xsd/*; do
            sed -Ei 's|schemaLocation="[^"]*/([^"]+)"|schemaLocation="./\1"|g' "$file"
            # Handle ?xsd= parameters in schemaLocation
            sed -Ei 's|schemaLocation="[^"]*\?xsd=([^"]+)"|schemaLocation="./\1"|g' "$file"
        done
    fi
}

# Create xsd directory if it doesn't exist
if [ ! -d "xsd" ]; then
    mkdir xsd
fi

# Fetch both ITI18 and ITI43 WSDLs
fetchWSDL
