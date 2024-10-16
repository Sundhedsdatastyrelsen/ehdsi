#!/usr/bin/env bash

#
# In an empty directory this script can fetch the wsdl and xsd files for "Det Gode CPR Opslag".
#


function fetchFile() {
	url="$1"
	file=$(echo "xsd/${url##*/}" | sed -E 's|\?xsd=([0-9]+)|-\1.xsd|g')

	if [ ! -f "$file" ]; then
		echo "Fetching $url -> $file"
		wget -q "$url" -O "$file"

		subFiles=$(grep -Po 'schemaLocation="\K.*?(?=")' "$file")

		for subUrl in $subFiles; do
			fetchFile "$subUrl"
		done
	fi
}


initialUrl="https://wsdl.nspop.dk/stamdata-cpr-ws/service/DetGodeCPROpslag-1.0.4?wsdl"
if [ ! -f "DetGodeCPROpslag-1.0.4.wsdl" ]; then
	echo "Fetching $initialUrl"
	wget -q "$initialUrl" -O "DetGodeCPROpslag-1.0.4.wsdl"
fi
if [ ! -d "xsd" ]; then
	mkdir xsd
fi


initialFiles=$(grep -Po 'schemaLocation="\K.*?(?=")' DetGodeCPROpslag-1.0.4.wsdl)

for file in $initialFiles; do
	fetchFile "$file"
done


# Fix schemaLocation in main wsdl
sed -Ei 's|schemaLocation="[^"]*/([^"]+)"|schemaLocation="./xsd/\1"|g;s|\?xsd=([0-9]+)|-\1.xsd|g' DetGodeCPROpslag-1.0.4.wsdl

# Fix schemaLocation in xsds.
for file in xsd/*; do
	sed -Ei 's|schemaLocation="[^"]*/([^"]+)"|schemaLocation="./\1"|g;s|\?xsd=([0-9]+)|-\1.xsd|g' $file
done
