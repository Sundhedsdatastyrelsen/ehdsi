USE ehealth_eadc;

CREATE TABLE IF NOT EXISTS eTransaction
(
	Transaction_PK varchar(64) NOT NULL,
	Direction varchar(500) NULL,
	HomeISO varchar(500) NULL,
	HomeNCP_OID varchar(500) NULL,
	HomeHCID varchar(500) NULL,
	HomeHost varchar(500) NULL,
	HomeAddress varchar(500) NULL,
	SndISO varchar(500) NULL,
	SndNCP_OID varchar(500) NULL,
	SndHCID varchar(500) NULL,
	SndAddress varchar(500) NULL,
	SndHost varchar(500) NULL,
	SndMsgID varchar(500) NULL,
	ReceivingISO varchar(500) NULL,
	ReceivingNCP_OID varchar(500) NULL,
	ReceivingHost varchar(500) NULL,
	ReceivingAddr varchar(500) NULL,
	ReceivingMsgID varchar(500) NULL,
	TransactionCounter varchar(500) NULL,
	HumanRequestor varchar(500) NULL,
	UserId varchar(500) NULL,
	POC varchar(500) NULL,
	POC_ID varchar(500) NULL,
	AuthenticationLevel varchar(500) NULL,
	RequestAction varchar(500) NULL,
	ResponseAction varchar(500) NULL,
	ServiceType varchar(500) NULL,
	ServiceName varchar(500) NULL,
	StartTime varchar(500) NULL,
	EndTime varchar(500) NULL,
	Duration varchar(500) NULL,
	PRIMARY KEY (Transaction_PK)
) ENGINE=InnoDB CHARSET=utf8;

CREATE TABLE IF NOT EXISTS eTransactionData
(
	TransactionData_PK BIGINT NOT NULL AUTO_INCREMENT,
	Transaction_FK VARCHAR(64) NOT NULL,
	DataType VARCHAR(500) NULL,
	DataTypeName VARCHAR(500) NULL,
	DataValue VARCHAR(500) NULL,
	ValueDisplay VARCHAR(500) NULL,
	Void0 VARCHAR(500) NULL,
	Void1 VARCHAR(500) NULL,
	Void2 VARCHAR(500) NULL,
	Void3 VARCHAR(500) NULL,
	Void4 VARCHAR(500) NULL,
	Void5 VARCHAR(500) NULL,
	Void6 VARCHAR(500) NULL,
	Void7 VARCHAR(500) NULL,
	Void8 VARCHAR(500) NULL,
	Void9 VARCHAR(500) NULL,
	PRIMARY KEY (TransactionData_PK),
	KEY ITransactionData_PK (Transaction_FK),
	CONSTRAINT Transaction_FK__TransactionDataPK FOREIGN KEY(Transaction_FK) REFERENCES eTransaction(Transaction_PK)
) ENGINE=INNODB CHARSET=utf8;

CREATE TABLE IF NOT EXISTS eTransactionError (
TransactionError_PK bigint(20) NOT NULL AUTO_INCREMENT,
Transaction_FK varchar(64) NOT NULL,
ErrorDescription varchar(500) DEFAULT NULL,
PRIMARY KEY (TransactionError_PK),
KEY ITransactionError_PK (Transaction_FK),
CONSTRAINT Transaction_FK_TransactionErrorPK FOREIGN KEY (Transaction_FK) REFERENCES
eTransaction (Transaction_PK)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

