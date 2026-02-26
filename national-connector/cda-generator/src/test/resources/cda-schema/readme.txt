This zip file packages CDA Schema with Pharma extensions.


This package supplements the published CDA R2 base standard with updated Schema definitions.

This package was prepared by HL7 IPS Project Team. 
It is based on the ELGA XML schema created by Tony Schaller, medshare GmbH, Switzerland.


========================
Contents of the package:

readme.txt:	
  This file
  
CDA_Pharma.xsd:
   XML schema for message type POCD_MT000040.Pharma

POCD_MT000040_Pharma.xsd:
   Schema defining the elements and attributes for message type POCD_MT000040 and the extensions used by the Pharma templates

extPHARM/COCT_MT230100UV01_extPHARM.xsd:
   Modified COCT_MT230100UV01 schema used as source for the CDA extensions applied to the POCD_MT000040.Material class

extPHARM/hl7v3_extPHARM.xsd:
   Vocabulary for the HL7 Pharmacy extension

coreschemas/datatypes.xsd:
   Schema defining the CDA data types. 

coreschemas/datatypes-base.xsd:
   Schema defining base data types. Modified to support the extensions.

coreschemas/NarrativeBlock.xsd:
   Schema defining constructs allowed in the CDA narrative block.

coreschemas/voc.xsd:
   Schema defining allowed vocabulary values. 

March 14, 2022