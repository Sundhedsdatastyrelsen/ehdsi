#!/usr/bin/env bash

#
# Script to fetch WSDL and XSD files for both ITI18 and ITI43
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
    local file=$(echo "xsd/${url##*/}" | sed -E 's|\?xsd=([0-9]+)|-\1.xsd|g')
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
    local iti_version="$1"
    local wsdl_file="sfsk${iti_version}.wsdl"
    local initial_url="https://test2-cnsp.ekstern-test.nspop.dk:8443/sfsk/dgws-wsdl/${iti_version}.wsdl"
    local retry_count=0
    local success=false

    echo "Processing ITI${iti_version} WSDL..."

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
        sed -Ei 's|schemaLocation="[^"]*/([^"]+)"|schemaLocation="./xsd/\1"|g;s|\?
        xsd=([0-9]+)|-\1.xsd|g' "$wsdl_file"

        # Fix schemaLocation in xsds
        for file in xsd/*; do
            sed -Ei 's|schemaLocation="[^"]*/([^"]+)"|schemaLocation="./\1"|g;s|\?xsd=([0-9]+)|-\1.xsd|g' "$file"
        done
    fi
}

# Create xsd directory if it doesn't exist
if [ ! -d "xsd" ]; then
    mkdir xsd
fi

# Fetch both ITI18 and ITI43 WSDLs
fetchWSDL "iti18"
fetchWSDL "iti43"
