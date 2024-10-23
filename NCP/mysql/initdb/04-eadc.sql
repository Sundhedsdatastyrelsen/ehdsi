# Create database
CREATE DATABASE IF NOT EXISTS `ehealth_eadc`;

USE `ehealth_eadc`;

--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: src/main/resources/openncp-eadc/db.changelog.xml
--  Ran at: 11/23/23, 9:47 AM
--  Against: null@offline:mysql?changeLogFile=D:\projects\work\dg_sante\ehealth\openncp-common-components\openncp-database-initializer\target/liquibase/databasechangelog-mysql.csv
--  Liquibase version: 4.10.0
--  *********************************************************************

--  Changeset src/main/resources/openncp-eadc/db.changelog.xml::1::subigre
CREATE TABLE eTransaction (Transaction_PK VARCHAR(64) NOT NULL, Direction VARCHAR(500) NULL, HomeISO VARCHAR(500) NULL, HomeHCID VARCHAR(500) NULL, HomeHost VARCHAR(500) NULL, HomeAddress VARCHAR(500) NULL, SndISO VARCHAR(500) NULL, SndNCP_OID VARCHAR(500) NULL, SndHCID VARCHAR(500) NULL, SndAddress VARCHAR(500) NULL, SndHost VARCHAR(500) NULL, SndMsgID VARCHAR(500) NULL, ReceivingISO VARCHAR(500) NULL, ReceivingNCP_OID VARCHAR(500) NULL, ReceivingHost VARCHAR(500) NULL, ReceivingAddr VARCHAR(500) NULL, ReceivingMsgID VARCHAR(500) NULL, TransactionCounter VARCHAR(500) NULL, HumanRequestor VARCHAR(500) NULL, UserId VARCHAR(500) NULL, POC VARCHAR(500) NULL, POC_ID VARCHAR(500) NULL, AuthenticationLevel VARCHAR(500) NULL, RequestAction VARCHAR(500) NULL, ResponseAction VARCHAR(500) NULL, ServiceType VARCHAR(500) NULL, ServiceName VARCHAR(500) NULL, StartTime VARCHAR(500) NULL, EndTime VARCHAR(500) NULL, Duration VARCHAR(500) NULL, CONSTRAINT PK_ETRANSACTION PRIMARY KEY (Transaction_PK));

CREATE TABLE eTransactionData (TransactionData_PK BIGINT AUTO_INCREMENT NOT NULL, Transaction_FK VARCHAR(64) NOT NULL, DataType VARCHAR(500) NULL, DataTypeName VARCHAR(500) NULL, DataValue VARCHAR(500) NULL, ValueDisplay VARCHAR(500) NULL, Void0 VARCHAR(500) NULL, Void1 VARCHAR(500) NULL, Void2 VARCHAR(500) NULL, Void3 VARCHAR(500) NULL, Void4 VARCHAR(500) NULL, Void5 VARCHAR(500) NULL, Void6 VARCHAR(500) NULL, Void7 VARCHAR(500) NULL, Void8 VARCHAR(500) NULL, Void9 VARCHAR(500) NULL, CONSTRAINT PK_ETRANSACTIONDATA PRIMARY KEY (TransactionData_PK), CONSTRAINT Transaction_FK__TransactionDataPK FOREIGN KEY (Transaction_FK) REFERENCES eTransaction(Transaction_PK));

--  Changeset src/main/resources/openncp-eadc/db.changelog.xml::2::jdethise
ALTER TABLE eTransaction ADD HomeNCP_OID VARCHAR(500) NULL;

--  Changeset src/main/resources/openncp-eadc/db.changelog.xml::3::miorial
CREATE TABLE eTransactionError (TransactionError_PK BIGINT AUTO_INCREMENT NOT NULL, Transaction_FK VARCHAR(64) NOT NULL, ErrorDescription VARCHAR(2000) NULL, CONSTRAINT PK_ETRANSACTIONERROR PRIMARY KEY (TransactionError_PK), CONSTRAINT Transaction_FK__TransactionErrorPK FOREIGN KEY (Transaction_FK) REFERENCES eTransaction(Transaction_PK));

