GRANT SELECT ON *.* TO 'root'@'%';
GRANT SELECT ON *.* TO '{{DB_USERNAME}}'@'%';

GRANT ALL PRIVILEGES ON ehealth_properties.* TO '{{DB_USERNAME}}'@'%';
GRANT ALL PRIVILEGES ON ehealth_eadc.* TO '{{DB_USERNAME}}'@'%';
GRANT ALL PRIVILEGES ON ehealth_logs.* TO '{{DB_USERNAME}}'@'%';
GRANT ALL PRIVILEGES ON ehealth_ltrdb.* TO '{{DB_USERNAME}}'@'%';
GRANT ALL PRIVILEGES ON ehealth_atna.* TO '{{DB_USERNAME}}'@'%';

#not used at the moment
#GRANT ALL PRIVILEGES ON openncp_portal.* TO ''@'%';
#GRANT ALL PRIVILEGES ON loganalyzer.* TO ''@'%';
#GRANT ALL PRIVILEGES ON statping.* TO ''@'%';
#GRANT ALL PRIVILEGES ON openncp_users.* TO ''@'%';

FLUSH PRIVILEGES;
