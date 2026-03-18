#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

# Check that ehealth and ehdsi exist
for dir in ehdsi ehealth; do
    if [[ ! -d "$dir" ]]; then
        echo "Error: Directory '$dir' not found. Make sure both the ehdsi and ehealth repositories are available where you run this script" >&2
        exit 1
    fi
done

diff_files() {
    diff "$1" "$2" -u --label "$1" --label "$2" > "$2.diff"
}

# diff returns 1 if there were any changes, which terminates the script if errexit is on.
set +e

# Create all the diff files
# atna
diff_files ehealth/openncp-docker/openncp-configuration/ATNA_resources/ArrConnections.xml ehdsi/NCP/atna-resources/ArrConnections.template.xml
diff_files ehealth/openncp-docker/openncp-configuration/ATNA_resources/openatna.properties ehdsi/NCP/atna-resources/openatna.properties

# .env
diff_files ehealth/openncp-docker/.env ehdsi/NCP/env_default/.env

# mysql
# diff_files ehealth/openncp-docker/mysql/initdb/01-ehealth-properties.sql ehdsi/NCP/mysql/initdb/01-ehealth-properties.sql
# diff_files ehealth/openncp-docker/mysql/initdb/02-atna.sql ehdsi/NCP/mysql/initdb/02-atna.sql
# diff_files ehealth/openncp-docker/mysql/initdb/03-ltrdb.sql ehdsi/NCP/mysql/initdb/03-ltrdb.sql
# diff_files ehealth/openncp-docker/mysql/initdb/04-eadc.sql ehdsi/NCP/mysql/initdb/04-eadc.sql
# diff_files ehealth/openncp-docker/mysql/initdb/05-populate-gateway-test-user.sql ehdsi/NCP/mysql/initdb/05-populate-gateway-test-user.sql

# ncp_a
# diff_files ehealth/openncp-docker/ncp_a/config/context.xml ehdsi/NCP/ncp_a/config/context.xml
diff_files ehealth/openncp-docker/ncp_a/config/server.template.xml ehdsi/NCP/ncp_a/config/server.xml
diff_files ehealth/openncp-docker/ncp_a/ncpa.database.env ehdsi/NCP/ncp_a/ncpa.database.env
diff_files ehealth/openncp-docker/ncp_a/ncpa.env ehdsi/NCP/ncp_a/ncpa.env

# ncp_b
# diff_files ehealth/openncp-docker/ncp_b/config/context.xml ehdsi/NCP/ncp_b/config/context.xml
diff_files ehealth/openncp-docker/ncp_b/config/server.template.xml ehdsi/NCP/ncp_b/config/server.xml
diff_files ehealth/openncp-docker/ncp_b/ncpb.database.env ehdsi/NCP/ncp_b/ncpb.database.env
diff_files ehealth/openncp-docker/ncp_b/ncpb.env ehdsi/NCP/ncp_b/ncpb.env

# openncp-configuration
diff_files ehealth/openncp-docker/openncp-configuration/tm.properties ehdsi/NCP/openncp-configuration/tm.properties
diff_files ehealth/openncp-docker/openncp-configuration/tsam.properties ehdsi/NCP/openncp-configuration/tsam.properties

# openncp-openatna
# diff_files ehealth/openncp-docker/openncp-openatna/config/context.xml ehdsi/NCP/openncp-openatna/config/context.xml
diff_files ehealth/openncp-docker/openncp-openatna/config/server.template.xml ehdsi/NCP/openncp-openatna/config/server.xml
diff_files ehealth/openncp-docker/openncp-openatna/openatna.database.env ehdsi/NCP/openncp-openatna/openatna.database.env
diff_files ehealth/openncp-docker/openncp-openatna/openatna.env ehdsi/NCP/openncp-openatna/openatna.env

# openncp-translations-and-mappings
# diff ehealth/openncp-docker/openncp-translations-and-mappings/config/context.xml ehdsi/NCP/openncp-translations-and-mappings/config/context.xml -u > ehdsi/NCP/openncp-translations-and-mappings/config/context.xml.diff
diff_files ehealth/openncp-docker/openncp-translations-and-mappings/config/server.template.xml ehdsi/NCP/openncp-translations-and-mappings/config/server.xml
diff_files ehealth/openncp-docker/openncp-translations-and-mappings/translations-and-mappings.database.env ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.database.env
diff_files ehealth/openncp-docker/openncp-translations-and-mappings/translations-and-mappings.env ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.env

# openncp-trc-sts
# diff_files ehealth/openncp-docker/openncp-trc-sts/config/context.xml ehdsi/NCP/openncp-trc-sts/config/context.xml
diff_files ehealth/openncp-docker/openncp-trc-sts/config/server.template.xml ehdsi/NCP/openncp-trc-sts/config/server.xml
diff_files ehealth/openncp-docker/openncp-trc-sts/.database.env ehdsi/NCP/openncp-trc-sts/.database.env
diff_files ehealth/openncp-docker/openncp-trc-sts/.env ehdsi/NCP/openncp-trc-sts/.env

# openncp-tsam-exporter
# diff_files ehealth/openncp-docker/openncp-tsam-exporter/application.yml ehdsi/NCP/openncp-tsam-exporter/application.yml

# openncp-web-manager/openncp-web-manager-backend
# diff_files ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/config/context.xml ehdsi/NCP/openncp-web-manager/config/context.xml
diff_files ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/config/server.template.xml ehdsi/NCP/openncp-web-manager/config/server.xml
diff_files ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/.database.env ehdsi/NCP/openncp-web-manager/.database.env
diff_files ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/.env ehdsi/NCP/openncp-web-manager/.env
# diff_files ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/application-docker.yml ehdsi/NCP/openncp-web-manager/application-docker.yml

# tsam-synchronizer
diff_files ehealth/openncp-docker/openncp-tsam-sync/application.yaml ehdsi/NCP/tsam-synchronizer/application.yml

# openncp-configuration.properties
diff_files ehealth/openncp-docker/openncp-configuration-utility/openncp-configuration.properties ehdsi/NCP/openncp-configuration.properties

set -e

exit 0