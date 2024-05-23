USE ehealth_ltrdb;

DROP TABLE IF EXISTS x_concept_value_set;
DROP TABLE IF EXISTS value_set_version;
DROP TABLE IF EXISTS value_set;
DROP TABLE IF EXISTS designation;
DROP TABLE IF EXISTS transcoding_association;
DROP TABLE IF EXISTS code_system_concept;
DROP TABLE IF EXISTS code_system_version;
DROP TABLE IF EXISTS code_system;

CREATE TABLE code_system (
  id          BIGINT AUTO_INCREMENT NOT NULL,
  oid         VARCHAR(255),
  name        VARCHAR(255),
  description VARCHAR(4000),
  PRIMARY KEY (id)
);

CREATE TABLE code_system_version (
  id                  BIGINT AUTO_INCREMENT NOT NULL,
  full_name           VARCHAR(255),
  local_name          VARCHAR(255),
  previous_version_id BIGINT,
  effective_date      DATETIME,
  release_date        DATETIME,
  status              VARCHAR(255),
  status_date         DATETIME,
  description         VARCHAR(4000),
  copyright           VARCHAR(255),
  source              VARCHAR(255),
  code_system_id      BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE code_system_concept (
  id                     BIGINT AUTO_INCREMENT NOT NULL,
  code                   VARCHAR(255),
  status                 VARCHAR(255),
  status_date            DATETIME,
  definition             VARCHAR(4000),
  code_system_version_id BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE designation (
  id                     BIGINT AUTO_INCREMENT NOT NULL,
  designation            VARCHAR(4000),
  language_code          VARCHAR(255),
  type                   VARCHAR(255),
  is_preferred           BOOLEAN,
  status                 VARCHAR(255),
  status_date            DATETIME,
  code_system_concept_id BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE transcoding_association (
  id                         BIGINT AUTO_INCREMENT NOT NULL,
  transcoding_association_id BIGINT                NOT NULL,
  target_concept_id          BIGINT,
  source_concept_id          BIGINT,
  quality                    VARCHAR(255),
  status                     VARCHAR(255),
  status_date                DATETIME,
  PRIMARY KEY (id)
);

CREATE TABLE value_set (
  id                    BIGINT AUTO_INCREMENT NOT NULL,
  oid                   VARCHAR(255),
  epsos_name            VARCHAR(255),
  description           VARCHAR(4000),
  parent_code_system_id BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE value_set_version (
  id                  BIGINT AUTO_INCREMENT NOT NULL,
  version_name        VARCHAR(255),
  effective_date      DATETIME,
  release_date        DATETIME,
  status              VARCHAR(255),
  status_date         DATETIME,
  description         VARCHAR(4000),
  previous_version_id BIGINT,
  value_set_id        BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE x_concept_value_set (
  code_system_concept_id BIGINT NOT NULL,
  value_set_version_id   BIGINT NOT NULL,
  PRIMARY KEY (code_system_concept_id, value_set_version_id)
);

ALTER TABLE code_system_concept
  ADD CONSTRAINT fk_code_system_version_id
FOREIGN KEY (code_system_version_id)
REFERENCES code_system_version (id);

ALTER TABLE code_system_version
  ADD CONSTRAINT fk_code_system_id
FOREIGN KEY (code_system_id)
REFERENCES code_system (id);

ALTER TABLE designation
  ADD CONSTRAINT fk_des_code_system_concept_id
FOREIGN KEY (code_system_concept_id)
REFERENCES code_system_concept (id);

ALTER TABLE transcoding_association
  ADD CONSTRAINT fk_source_concept_id
FOREIGN KEY (source_concept_id)
REFERENCES code_system_concept (id);

ALTER TABLE transcoding_association
  ADD CONSTRAINT fk_target_concept_id
FOREIGN KEY (target_concept_id)
REFERENCES code_system_concept (id);

ALTER TABLE value_set
  ADD CONSTRAINT fk_parent_code_system_id
FOREIGN KEY (parent_code_system_id)
REFERENCES code_system (id);

ALTER TABLE value_set_version
  ADD CONSTRAINT fk_previous_version_id
FOREIGN KEY (previous_version_id)
REFERENCES value_set_version (id);

ALTER TABLE value_set_version
  ADD CONSTRAINT fk_value_set_id
FOREIGN KEY (value_set_id)
REFERENCES value_set (id);

ALTER TABLE x_concept_value_set
  ADD CONSTRAINT fk_con_code_system_concept_id
FOREIGN KEY (code_system_concept_id) REFERENCES code_system_concept (id);

ALTER TABLE x_concept_value_set
  ADD CONSTRAINT fk_value_set_version_id
FOREIGN KEY (value_set_version_id)
REFERENCES value_set_version (id);
