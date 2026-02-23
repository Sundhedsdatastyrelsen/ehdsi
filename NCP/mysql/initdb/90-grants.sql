-- Fix privileges in the database.

GRANT SELECT ON *.* TO 'root'@'%';
GRANT SELECT ON *.* TO '{{DB_USERNAME}}'@'%';

GRANT ALL PRIVILEGES ON ehealth_properties.* TO '{{DB_USERNAME}}'@'%';
GRANT ALL PRIVILEGES ON ehealth_eadc.* TO '{{DB_USERNAME}}'@'%';
GRANT ALL PRIVILEGES ON ehealth_logs.* TO '{{DB_USERNAME}}'@'%';
GRANT ALL PRIVILEGES ON ehealth_ltrdb.* TO '{{DB_USERNAME}}'@'%';
GRANT ALL PRIVILEGES ON ehealth_atna.* TO '{{DB_USERNAME}}'@'%';

FLUSH PRIVILEGES;
