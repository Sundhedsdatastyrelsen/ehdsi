CREATE DATABASE IF NOT EXISTS ehealth_properties;
CREATE DATABASE IF NOT EXISTS ehealth_atna;
CREATE DATABASE IF NOT EXISTS ehealth_eadc;

CREATE USER IF NOT EXISTS 'myUsername'@'%' IDENTIFIED BY 'myPassword';

GRANT ALL PRIVILEGES ON ehealth_properties.* to 'myUsername'@'%';
GRANT ALL PRIVILEGES ON ehealth_atna.* to 'myUsername'@'%';
GRANT ALL PRIVILEGES ON ehealth_eadc.* to 'myUsername'@'%';
