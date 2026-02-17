# Create database
CREATE DATABASE IF NOT EXISTS `ehealth_ltrdb`;

USE `ehealth_ltrdb`;
--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: src/main/resources/openncp-ltrdb/db.changelog.xml
--  Ran at: 11/23/23, 9:47 AM
--  Against: null@offline:mysql?changeLogFile=D:\projects\work\dg_sante\ehealth\openncp-common-components\openncp-database-initializer\target/liquibase/databasechangelog-mysql.csv
--  Liquibase version: 4.10.0
--  *********************************************************************

--  Changeset src/main/resources/openncp-ltrdb/db.changelog.xml::1::subigre
CREATE TABLE code_system
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    oid           VARCHAR(255) NULL,
    url           VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(4000) NULL,
    CONSTRAINT PK_CODE_SYSTEM PRIMARY KEY (id)
);

CREATE TABLE code_system_version (id BIGINT AUTO_INCREMENT NOT NULL, code_system_id BIGINT NOT NULL, full_name VARCHAR(255) NULL, local_name VARCHAR(255) NULL, previous_version_id BIGINT NULL, effective_date datetime NULL, release_date datetime NULL, status VARCHAR(255) NULL, status_date datetime NULL, `description` VARCHAR(4000) NULL, copyright VARCHAR(255) NULL, source VARCHAR(255) NULL, CONSTRAINT PK_CODE_SYSTEM_VERSION PRIMARY KEY (id), CONSTRAINT FK_CODE_SYSTEM_VERSION_CODE_SYSTEM FOREIGN KEY (code_system_id) REFERENCES code_system(id));

CREATE TABLE code_system_concept (id BIGINT AUTO_INCREMENT NOT NULL, code VARCHAR(255) NULL, code_system_version_id BIGINT NOT NULL, status VARCHAR(255) NULL, status_date datetime NULL, `definition` VARCHAR(4000) NULL, copyright VARCHAR(255) NULL, source VARCHAR(255) NULL, CONSTRAINT PK_CODE_SYSTEM_CONCEPT PRIMARY KEY (id), CONSTRAINT FK_CODE_SYSTEM_CONCEPT_CODE_SYSTEM_VERSION FOREIGN KEY (code_system_version_id) REFERENCES code_system_version(id));

CREATE TABLE designation (id BIGINT AUTO_INCREMENT NOT NULL, code_system_concept_id BIGINT NOT NULL, designation VARCHAR(4000) NULL, language_code VARCHAR(255) NULL, type VARCHAR(255) NULL, is_preferred BIT(1) NULL, status VARCHAR(255) NULL, status_date datetime NULL, CONSTRAINT PK_DESIGNATION PRIMARY KEY (id), CONSTRAINT FK_DESIGNATION_CODE_SYSTEM_CONCEPT FOREIGN KEY (code_system_concept_id) REFERENCES code_system_concept(id));

CREATE TABLE transcoding_association (id BIGINT AUTO_INCREMENT NOT NULL, transcoding_association_id BIGINT NOT NULL, target_concept_id BIGINT NOT NULL, source_concept_id BIGINT NOT NULL, quality VARCHAR(255) NULL, status VARCHAR(255) NULL, status_date datetime NULL, CONSTRAINT PK_TRANSCODING_ASSOCIATION PRIMARY KEY (id), CONSTRAINT FK_TRANSCODING_ASSOCIATION_TARGET_CONCEPT FOREIGN KEY (target_concept_id) REFERENCES code_system_concept(id), CONSTRAINT FK_TRANSCODING_ASSOCIATION_SOURCE_CONCEPT FOREIGN KEY (source_concept_id) REFERENCES code_system_concept(id));

CREATE TABLE value_set (id BIGINT AUTO_INCREMENT NOT NULL, oid VARCHAR(255) NULL, epsos_name VARCHAR(255) NULL, `description` VARCHAR(4000) NULL, parent_code_system_id BIGINT NULL, CONSTRAINT PK_VALUE_SET PRIMARY KEY (id), CONSTRAINT FK_VALUE_SET_PARENT_CODE_SYSTEM FOREIGN KEY (parent_code_system_id) REFERENCES code_system(id));

CREATE TABLE value_set_version (id BIGINT AUTO_INCREMENT NOT NULL, value_set_id BIGINT NOT NULL, version_name VARCHAR(255) NULL, effective_date datetime NULL, release_date datetime NULL, status VARCHAR(255) NULL, status_date datetime NULL, `description` VARCHAR(4000) NULL, previous_version_id BIGINT NULL, CONSTRAINT PK_VALUE_SET_VERSION PRIMARY KEY (id), CONSTRAINT FK_VALUE_SET_VERSION_VALUE_SET FOREIGN KEY (value_set_id) REFERENCES value_set(id));

CREATE TABLE x_concept_value_set (code_system_concept_id BIGINT NOT NULL, value_set_version_id BIGINT NOT NULL, CONSTRAINT FK_X_CONCEPT_VALUE_SET_CODE_SYSTEM_CONCEPT FOREIGN KEY (code_system_concept_id) REFERENCES code_system_concept(id), CONSTRAINT FK_X_CONCEPT_VALUE_SET_VALUE_SET_VERSION FOREIGN KEY (value_set_version_id) REFERENCES value_set_version(id));

ALTER TABLE x_concept_value_set ADD PRIMARY KEY (code_system_concept_id, value_set_version_id);


