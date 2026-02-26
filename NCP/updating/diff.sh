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

# Create all the diff files
# atna
diff ehealth/openncp-docker/openncp-configuration/ATNA_resources/ArrConnections.xml ehdsi/NCP/atna-resources/ArrConnections.template.xml -u > ehdsi/NCP/atna-resources/ArrConnections.template.xml.diff
diff ehealth/openncp-docker/openncp-configuration/ATNA_resources/openatna.properties ehdsi/NCP/atna-resources/openatna.properties -u > ehdsi/NCP/atna-resources/openatna.properties.diff

# .env
diff ehealth/openncp-docker/.env ehdsi/NCP/env_default/.env -u > ehdsi/NCP/env_default/.env.diff

# mysql
# diff ehealth/openncp-docker/mysql/initdb/01-ehealth-properties.sql ehdsi/NCP/mysql/initdb/01-ehealth-properties.sql -u > ehdsi/NCP/mysql/initdb/01-ehealth-properties.sql.diff
# diff ehealth/openncp-docker/mysql/initdb/02-atna.sql ehdsi/NCP/mysql/initdb/02-atna.sql -u > ehdsi/NCP/mysql/initdb/02-atna.sql.diff
# diff ehealth/openncp-docker/mysql/initdb/03-ltrdb.sql ehdsi/NCP/mysql/initdb/03-ltrdb.sql -u > ehdsi/NCP/mysql/initdb/03-ltrdb.sql.diff
# diff ehealth/openncp-docker/mysql/initdb/04-eadc.sql ehdsi/NCP/mysql/initdb/04-eadc.sql -u > ehdsi/NCP/mysql/initdb/04-eadc.sql.diff
# diff ehealth/openncp-docker/mysql/initdb/05-populate-gateway-test-user.sql ehdsi/NCP/mysql/initdb/05-populate-gateway-test-user.sql -u > ehdsi/NCP/mysql/initdb/05-populate-gateway-test-user.sql.diff

# ncp_a
# diff ehealth/openncp-docker/ncp_a/config/context.xml ehdsi/NCP/ncp_a/config/context.xml -u > ehdsi/NCP/ncp_a/config/context.xml.diff
diff ehealth/openncp-docker/ncp_a/config/server.template.xml ehdsi/NCP/ncp_a/config/server.xml -u > ehdsi/NCP/ncp_a/config/server.xml.diff
diff ehealth/openncp-docker/ncp_a/ncpa.database.env ehdsi/NCP/ncp_a/ncpa.database.env -u > ehdsi/NCP/ncp_a/ncpa.database.env.diff
diff ehealth/openncp-docker/ncp_a/ncpa.env ehdsi/NCP/ncp_a/ncpa.env -u > ehdsi/NCP/ncp_a/ncpa.env.diff

# ncp_b
# diff ehealth/openncp-docker/ncp_b/config/context.xml ehdsi/NCP/ncp_b/config/context.xml -u > ehdsi/NCP/ncp_b/config/context.xml.diff
diff ehealth/openncp-docker/ncp_b/config/server.template.xml ehdsi/NCP/ncp_b/config/server.xml -u > ehdsi/NCP/ncp_b/config/server.xml.diff
diff ehealth/openncp-docker/ncp_b/ncpb.database.env ehdsi/NCP/ncp_b/ncpb.database.env -u > ehdsi/NCP/ncp_b/ncpb.database.env.diff
diff ehealth/openncp-docker/ncp_b/ncpb.env ehdsi/NCP/ncp_b/ncpb.env -u > ehdsi/NCP/ncp_b/ncpb.env.diff

# openncp-configuration
diff ehealth/openncp-docker/openncp-configuration/tm.properties ehdsi/NCP/openncp-configuration/tm.properties -u > ehdsi/NCP/openncp-configuration/tm.properties.diff
diff ehealth/openncp-docker/openncp-configuration/tsam.properties ehdsi/NCP/openncp-configuration/tsam.properties -u > ehdsi/NCP/openncp-configuration/tsam.properties.diff

# openncp-openatna
# diff ehealth/openncp-docker/openncp-openatna/config/context.xml ehdsi/NCP/openncp-openatna/config/context.xml -u > ehdsi/NCP/openncp-openatna/config/context.xml.diff
diff ehealth/openncp-docker/openncp-openatna/config/server.template.xml ehdsi/NCP/openncp-openatna/config/server.xml -u > ehdsi/NCP/openncp-openatna/config/server.xml.diff
diff ehealth/openncp-docker/openncp-openatna/openatna.database.env ehdsi/NCP/openncp-openatna/openatna.database.env -u > ehdsi/NCP/openncp-openatna/openatna.database.env.diff
diff ehealth/openncp-docker/openncp-openatna/openatna.env ehdsi/NCP/openncp-openatna/openatna.env -u > ehdsi/NCP/openncp-openatna/openatna.env.diff

# openncp-translations-and-mappings
# diff ehealth/openncp-docker/openncp-translations-and-mappings/config/context.xml ehdsi/NCP/openncp-translations-and-mappings/config/context.xml -u > ehdsi/NCP/openncp-translations-and-mappings/config/context.xml.diff
diff ehealth/openncp-docker/openncp-translations-and-mappings/config/server.template.xml ehdsi/NCP/openncp-translations-and-mappings/config/server.xml -u > ehdsi/NCP/openncp-translations-and-mappings/config/server.xml.diff
diff ehealth/openncp-docker/openncp-translations-and-mappings/translations-and-mappings.database.env ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.database.env -u > ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.database.env.diff
diff ehealth/openncp-docker/openncp-translations-and-mappings/translations-and-mappings.env ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.env -u > ehdsi/NCP/openncp-translations-and-mappings/translations-and-mappings.env.diff

# openncp-trc-sts
# diff ehealth/openncp-docker/openncp-trc-sts/config/context.xml ehdsi/NCP/openncp-trc-sts/config/context.xml -u > ehdsi/NCP/openncp-trc-sts/config/context.xml.diff
diff ehealth/openncp-docker/openncp-trc-sts/config/server.template.xml ehdsi/NCP/openncp-trc-sts/config/server.xml -u > ehdsi/NCP/openncp-trc-sts/config/server.xml.diff
diff ehealth/openncp-docker/openncp-trc-sts/.database.env ehdsi/NCP/openncp-trc-sts/.database.env -u > ehdsi/NCP/openncp-trc-sts/.database.env.diff
diff ehealth/openncp-docker/openncp-trc-sts/.env ehdsi/NCP/openncp-trc-sts/.env -u > ehdsi/NCP/openncp-trc-sts/.env.diff

# openncp-tsam-exporter
# diff ehealth/openncp-docker/openncp-tsam-exporter/application.yml ehdsi/NCP/openncp-tsam-exporter/application.yml -u > ehdsi/NCP/openncp-tsam-exporter/application.yml.diff

# openncp-web-manager/openncp-web-manager-backend
# diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/config/context.xml ehdsi/NCP/openncp-web-manager/config/context.xml -u > ehdsi/NCP/openncp-web-manager/config/context.xml.diff
diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/config/server.template.xml ehdsi/NCP/openncp-web-manager/config/server.xml -u > ehdsi/NCP/openncp-web-manager/config/server.xml.diff
diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/.database.env ehdsi/NCP/openncp-web-manager/.database.env -u > ehdsi/NCP/openncp-web-manager/.database.env.diff
diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/.env ehdsi/NCP/openncp-web-manager/.env -u > ehdsi/NCP/openncp-web-manager/.env.diff
# diff ehealth/openncp-docker/openncp-web-manager/openncp-web-manager-backend/application-docker.yml ehdsi/NCP/openncp-web-manager/application-docker.yml -u > ehdsi/NCP/openncp-web-manager/application-docker.yml.diff

# tsam-synchronizer
diff ehealth/openncp-docker/openncp-tsam-sync/application.yaml ehdsi/NCP/tsam-synchronizer/application.yml -u > ehdsi/NCP/tsam-synchronizer/application.yml.diff

# openncp-configuration.properties
diff ehealth/openncp-docker/openncp-configuration-utility/openncp-configuration.properties ehdsi/NCP/openncp-configuration.properties -u > ehdsi/NCP/openncp-configuration.properties.diff