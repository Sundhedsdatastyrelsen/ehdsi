--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: src/main/resources/openncp-atna/db.changelog.xml
--  Ran at: 6/7/23, 3:16 PM
--  Against: null@offline:mysql?changeLogFile=/opt/ehealth/openncp-common-components/openncp-database-initializer/target/liquibase/databasechangelog-mysql.csv
--  Liquibase version: 4.10.0
--  *********************************************************************
--  Changeset src/main/resources/openncp-atna/db.changelog.xml::1::subigre
use ehealth_atna;

CREATE TABLE IF NOT EXISTS codes (codetype VARCHAR(31) NOT NULL, id BIGINT AUTO_INCREMENT NOT NULL, code VARCHAR(255) NULL, codeSystem VARCHAR(255) NULL, codeSystemName VARCHAR(255) NULL, displayName VARCHAR(255) NULL, originalText VARCHAR(255) NULL, type INT NULL, version INT NULL, CONSTRAINT PK_CODES PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS detail_types (id BIGINT AUTO_INCREMENT NOT NULL, type INT NULL, version INT NULL, CONSTRAINT PK_DETAIL_TYPES PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS errors (id BIGINT AUTO_INCREMENT NOT NULL, errorMessage VARCHAR(255) NULL, errorTimestamp datetime NULL, payload BLOB NULL, sourceIp VARCHAR(255) NULL, stackTrace BLOB NULL, version INT NULL, CONSTRAINT PK_ERRORS PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS objects (id BIGINT AUTO_INCREMENT NOT NULL, objectId VARCHAR(4000) NULL, objectName VARCHAR(255) NULL, objectSensitivity VARCHAR(255) NULL, objectTypeCode INT NULL, objectTypeCodeRole INT NULL, version INT NULL, objectIdTypeCode_id BIGINT NULL, CONSTRAINT PK_OBJECTS PRIMARY KEY (id), CONSTRAINT FKfyfue7oxuywpf6vhi2k88s3ag FOREIGN KEY (objectIdTypeCode_id) REFERENCES codes(id));
CREATE TABLE IF NOT EXISTS participants (id BIGINT AUTO_INCREMENT NOT NULL, alternativeUserId VARCHAR(255) NULL, userId VARCHAR(255) NULL, userName VARCHAR(255) NULL, version INT NULL, CONSTRAINT PK_PARTICIPANTS PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS message_objects (id BIGINT AUTO_INCREMENT NOT NULL, objectDataLifeCycle INT NULL, objectQuery BLOB NULL, object_id BIGINT NULL, CONSTRAINT PK_MESSAGE_OBJECTS PRIMARY KEY (id), CONSTRAINT FK2sbpity0lukij70rch7wotusq FOREIGN KEY (object_id) REFERENCES objects(id));
CREATE TABLE IF NOT EXISTS network_access_points (id BIGINT AUTO_INCREMENT NOT NULL, identifier VARCHAR(255) NULL, type INT NULL, version INT NULL, CONSTRAINT PK_NETWORK_ACCESS_POINTS PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS message_participants (id BIGINT AUTO_INCREMENT NOT NULL, userIsRequestor BIT(1) NULL, networkAccessPoint_id BIGINT NULL, participant_id BIGINT NULL, CONSTRAINT PK_MESSAGE_PARTICIPANTS PRIMARY KEY (id), CONSTRAINT FKku36awkwewy8t2ju6hmmw2pyv FOREIGN KEY (networkAccessPoint_id) REFERENCES network_access_points(id), CONSTRAINT FKk4um5vd0o6w4cyisrbuv3giux FOREIGN KEY (participant_id) REFERENCES participants(id));
CREATE TABLE IF NOT EXISTS sources (id BIGINT AUTO_INCREMENT NOT NULL, enterpriseSiteId VARCHAR(255) NULL, sourceId VARCHAR(255) NULL, version INT NULL, CONSTRAINT PK_SOURCES PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS message_sources (id BIGINT AUTO_INCREMENT NOT NULL, source_id BIGINT NULL, CONSTRAINT PK_MESSAGE_SOURCES PRIMARY KEY (id), CONSTRAINT FKgvurh01o22nssxssh47uriypw FOREIGN KEY (source_id) REFERENCES sources(id));
CREATE TABLE IF NOT EXISTS messages (id BIGINT AUTO_INCREMENT NOT NULL, eventActionCode VARCHAR(255) NULL, eventDateTime datetime NULL, eventOutcome INT NULL, messageContent BLOB NULL, sourceAddress VARCHAR(255) NULL, eventId_id BIGINT NULL, CONSTRAINT PK_MESSAGES PRIMARY KEY (id), CONSTRAINT FK25e2tvsr216gcrnnm8g8mjjs9 FOREIGN KEY (eventId_id) REFERENCES codes(id));
CREATE TABLE IF NOT EXISTS object_descriptions (id BIGINT AUTO_INCREMENT NOT NULL, accessionNumbers VARCHAR(255) NULL, mppsUids VARCHAR(255) NULL, version INT NULL, CONSTRAINT PK_OBJECT_DESCRIPTIONS PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS object_details (id BIGINT AUTO_INCREMENT NOT NULL, type VARCHAR(255) NULL, value BLOB NULL, CONSTRAINT PK_OBJECT_DETAILS PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS provisional_messages (id BIGINT AUTO_INCREMENT NOT NULL, content BLOB NULL, version INT NULL, CONSTRAINT PK_PROVISIONAL_MESSAGES PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS sop_classes (id BIGINT AUTO_INCREMENT NOT NULL, instanceUids VARCHAR(255) NULL, numberOfInstances INT NULL, sopId VARCHAR(255) NULL, version INT NULL, CONSTRAINT PK_SOP_CLASSES PRIMARY KEY (id));
CREATE TABLE IF NOT EXISTS descriptions_sop_classes (ObjectDescriptionEntity_id BIGINT NOT NULL, sopClasses_id BIGINT NOT NULL, CONSTRAINT FKo48ywchf7r2vp0h1brdfrqgt5 FOREIGN KEY (sopClasses_id) REFERENCES sop_classes(id), CONSTRAINT FKmfl2jl6ye3x1wm1x9rlg86th4 FOREIGN KEY (ObjectDescriptionEntity_id) REFERENCES object_descriptions(id), UNIQUE (sopClasses_id));
ALTER TABLE descriptions_sop_classes ADD PRIMARY KEY (ObjectDescriptionEntity_id, sopClasses_id);
CREATE TABLE IF NOT EXISTS sources_codes (SourceEntity_id BIGINT NOT NULL, sourceTypeCodes_id BIGINT NOT NULL, CONSTRAINT FKtgrtl1q9ask9p3lffxk2ml7ov FOREIGN KEY (SourceEntity_id) REFERENCES sources(id), CONSTRAINT FK4trv14tkssknvmk981xphfe9b FOREIGN KEY (sourceTypeCodes_id) REFERENCES codes(id), UNIQUE (sourceTypeCodes_id));
ALTER TABLE sources_codes ADD PRIMARY KEY (SourceEntity_id, sourceTypeCodes_id);
CREATE TABLE IF NOT EXISTS event_types_codes (MessageEntity_id BIGINT NULL, eventTypeCodes_id BIGINT NULL, CONSTRAINT FK5d391ir4nl5dunu63ou8axwat FOREIGN KEY (MessageEntity_id) REFERENCES messages(id), CONSTRAINT FK7wq9wpkhlyar32s651qp37xxg FOREIGN KEY (eventTypeCodes_id) REFERENCES codes(id));
ALTER TABLE event_types_codes ADD PRIMARY KEY (MessageEntity_id, eventTypeCodes_id);
CREATE TABLE IF NOT EXISTS messages_mobjects (MessageEntity_id BIGINT NULL, messageObjects_id BIGINT NOT NULL, CONSTRAINT FKeoa85h3a4u8pal49uab5e0o0x FOREIGN KEY (messageObjects_id) REFERENCES message_objects(id), CONSTRAINT FKoki02bkry1spcafvuiatye5cl FOREIGN KEY (MessageEntity_id) REFERENCES messages(id), UNIQUE (messageObjects_id));
ALTER TABLE messages_mobjects ADD PRIMARY KEY (MessageEntity_id, messageObjects_id);
CREATE TABLE IF NOT EXISTS messages_mparticipants (MessageEntity_id BIGINT NULL, messageParticipants_id BIGINT NOT NULL, CONSTRAINT FKfjf7riikc0yxuyy035vjqnuhv FOREIGN KEY (MessageEntity_id) REFERENCES messages(id), CONSTRAINT FKc65s1re3oidqjtnojw2y7wpne FOREIGN KEY (messageParticipants_id) REFERENCES message_participants(id), UNIQUE (messageParticipants_id));
ALTER TABLE messages_mparticipants ADD PRIMARY KEY (MessageEntity_id, messageParticipants_id);
CREATE TABLE IF NOT EXISTS messages_msources (MessageEntity_id BIGINT NULL, messageSources_id BIGINT NOT NULL, CONSTRAINT FKcanembxio6ajydtck27pj3128 FOREIGN KEY (MessageEntity_id) REFERENCES messages(id), CONSTRAINT FK7mtkxfbrj15cnxsdj071ant4i FOREIGN KEY (messageSources_id) REFERENCES message_sources(id), UNIQUE (messageSources_id));
ALTER TABLE messages_msources ADD PRIMARY KEY (MessageEntity_id, messageSources_id);
CREATE TABLE IF NOT EXISTS mobjects_details (MessageObjectEntity_id BIGINT NULL, details_id BIGINT NOT NULL, CONSTRAINT FKpjao4hpqmnwks4ie9ilsxfhax FOREIGN KEY (MessageObjectEntity_id) REFERENCES message_objects(id), CONSTRAINT FKns6dutobj42hykd0cwqidwcmh FOREIGN KEY (details_id) REFERENCES object_details(id), UNIQUE (details_id));
ALTER TABLE mobjects_details ADD PRIMARY KEY (MessageObjectEntity_id, details_id);
CREATE TABLE IF NOT EXISTS objects_descriptions (ObjectEntity_id BIGINT NOT NULL, objectDescriptions_id BIGINT NOT NULL, CONSTRAINT FKgdt0p1nbmpl92wctwcx1l081y FOREIGN KEY (objectDescriptions_id) REFERENCES object_descriptions(id), CONSTRAINT FKt42hs1642o6x9h2j5mmtmu21e FOREIGN KEY (ObjectEntity_id) REFERENCES objects(id), UNIQUE (objectDescriptions_id));
ALTER TABLE objects_descriptions ADD PRIMARY KEY (ObjectEntity_id, objectDescriptions_id);
CREATE TABLE IF NOT EXISTS objects_detail_types (ObjectEntity_id BIGINT NOT NULL, objectDetailTypes_id BIGINT NOT NULL, CONSTRAINT FKgimc70hjnuj9qkf3p0s0q3qmo FOREIGN KEY (objectDetailTypes_id) REFERENCES detail_types(id), CONSTRAINT FK4qbbl5uhnjqalam9hqtsckpgg FOREIGN KEY (ObjectEntity_id) REFERENCES objects(id), UNIQUE (objectDetailTypes_id));
ALTER TABLE objects_detail_types ADD PRIMARY KEY (ObjectEntity_id, objectDetailTypes_id);

--- From Einar 2024-03-18:
CREATE TABLE IF NOT EXISTS participants_codes (
  ParticipantEntity_id bigint(20) NOT NULL,
  participantTypeCodes_id bigint(20) NOT NULL,
  PRIMARY KEY (ParticipantEntity_id,participantTypeCodes_id),
  KEY FK6mo8s3p730mpu65trdu4akehf (participantTypeCodes_id),
  CONSTRAINT FK6mo8s3p730mpu65trdu4akehf FOREIGN KEY (participantTypeCodes_id) REFERENCES codes (id),
  CONSTRAINT FKth6nbhfn1lbkvtr5m942q305v FOREIGN KEY (ParticipantEntity_id) REFERENCES participants (id)
);
--- Instead of:
-- CREATE TABLE IF NOT EXISTS participants_codes (ParticipantEntity_id BIGINT NOT NULL, participantTypeCodes_id BIGINT NOT NULL, CONSTRAINT FK6mo8s3p730mpu65trdu4akehf FOREIGN KEY (participantTypeCodes_id) REFERENCES codes(id), CONSTRAINT FKth6nbhfn1lbkvtr5m942q305v FOREIGN KEY (ParticipantEntity_id) REFERENCES participants(id), UNIQUE (participantTypeCodes_id));
-- ALTER TABLE participants_codes ADD PRIMARY KEY (ParticipantEntity_id, participantTypeCodes_id);


--  Changeset src/main/resources/openncp-atna/db.changelog.xml::2::subigje
ALTER TABLE errors MODIFY payload LONGBLOB;
ALTER TABLE errors MODIFY stackTrace LONGBLOB;
ALTER TABLE messages MODIFY messageContent LONGBLOB;
ALTER TABLE message_objects MODIFY objectQuery LONGBLOB;
ALTER TABLE object_details MODIFY value LONGBLOB;
ALTER TABLE provisional_messages MODIFY content LONGBLOB;
--  Changeset src/main/resources/openncp-atna/db.changelog.xml::3::subigje
ALTER TABLE participants MODIFY userId VARCHAR(1000);
