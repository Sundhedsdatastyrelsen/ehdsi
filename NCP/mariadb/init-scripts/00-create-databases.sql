#The current version of the Installation manual recommends to use the following database names:
#ConfMgr: ehealth_properties
#OPEN_ATNA: ehealth_atna
#TSAM: ehealth_ltrdb
#EADC: ehealth_eadc
#LOGS: ehealth_logs
#Portal: ehealth_portal

CREATE DATABASE IF NOT EXISTS ehealth_properties CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS ehealth_atna CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS ehealth_ltrdb CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS ehealth_eadc CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS ehealth_logs CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS ehealth_portal CHARACTER SET utf8 COLLATE utf8_general_ci;

# not used in this deployment


# CREATE DATABASE IF NOT EXISTS openncp_portal CHARACTER SET utf8 COLLATE utf8_general_ci;
# CREATE DATABASE IF NOT EXISTS loganalyzer CHARACTER SET utf8 COLLATE utf8_general_ci;
# CREATE DATABASE IF NOT EXISTS statping CHARACTER SET utf8 COLLATE utf8_general_ci;
