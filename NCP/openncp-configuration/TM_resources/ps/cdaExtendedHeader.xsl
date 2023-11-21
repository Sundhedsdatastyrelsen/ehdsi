<?xml version="1.0"  ?>

	


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>
	

	
	<!--- Extended HEADER ELEMENT -->
	
	<!-- patient address -->
	
	<xsl:variable
name="patientWholeAddress"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole"/>

	
	<xsl:variable
name="patientStreet"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:addr/n1:streetAddressLine"/>

<xsl:variable
name="patientNumberStreet"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:addr/n1:streetAddressLine"/>
<xsl:variable
name="patientCity"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:addr/n1:city"/>
<xsl:variable
name="patientPostalCode"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:addr/n1:postalCode"/>
<xsl:variable
name="patientState"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:addr/n1:state"/>
	
	<xsl:variable
name="patientCountry"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:addr/n1:country"/>
	

<!-- patient telecomunication -->
<xsl:variable
name="patientTelNumber"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:telecom[1]/@value"/>
<xsl:variable
name="patientTelNumberUse"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:telecom[1]/@use"/>

	<xsl:variable
name="patientEmail"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:telecom[2]/@value"/>
<xsl:variable
name="patientEmailUse"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:telecom[2]/@use"/>


<xsl:variable
name="patientPreferLang"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:languageCommunication/n1:languageCode"/>

<!-- guardian -->

<xsl:variable
name="patientGuardian"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian"/>

<xsl:variable
name="patientGuardianFamilyName"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:guardianPerson/n1:name/n1:family"/>
<xsl:variable
name="patientGuardianGivenName"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:guardianPerson/n1:name/n1:given"/>

<xsl:variable
name="patientGuardianAddress"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr"/>

<xsl:variable
name="patientGuardianStreet"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:streetAddressLine"/>


<xsl:variable
name="patientGuardianCity"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:city"/>

<xsl:variable
name="patientGuardianPostalCode"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:postalCode"/>

 
<xsl:variable
name="patientGuardianState"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:state"/>

<xsl:variable
name="patientGuardianCountry"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:country"/>


<xsl:variable
name="patientGuardianTelecom"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:telecom"/>

<xsl:variable
name="patientGuardianTelecomTelephone"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:telecom[1]/@value"/>


<xsl:variable
name="patientGuardianTelecomTelephoneUse"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:telecom[1]/@use"/>


<xsl:variable
name="patientGuardianTelecomEmail"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:telecom[2]/@value"/>

<xsl:variable
name="patientGuardianTelecomEmailUse"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:guardian/n1:addr/n1:telecom[2]/@use"/>

<!-- contact person-->
<xsl:variable
name="patientContactPerson"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:associatedPerson"/>

<xsl:variable
name="patientContactFamilyName"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:associatedPerson/n1:name/n1:family"/>


<xsl:variable
name="patientContactGivenName"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:associatedPerson/n1:name/n1:given"/>


<!-- Patient  Contact's Address-->
<xsl:variable
name="patientContactAddress"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr"/>
<xsl:variable
name="patientContactStreet"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:streetAddressLine"/>

<xsl:variable
name="patientContactStreetNumber"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:streetAddressLine"/>

<xsl:variable
name="patientContactCity"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:city"/>

<xsl:variable
name="patientContactPostalCode"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:postalCode"/>


<xsl:variable
name="patientContactState"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:state"/>

<xsl:variable
name="patientContactCountry"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:country"/>

<!--Patient Contact's Telecommunication	-->

<xsl:variable
name="patientContactTelecom"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:telecom"/>


<xsl:variable
name="patientContactTelephone"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:telecom[1]/@value"/>

<xsl:variable
name="patientContactTelephoneUse"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:telecom[1]/@use"/>

<xsl:variable
name="patientContactEmail"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:telecom[2]/@value"/>

<xsl:variable
name="patientContactEmailUse"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:telecom[2]/@use"/>

<xsl:variable
name="patientParticipantTypeCode"
select="/n1:ClinicalDocument/n1:participant[@typeCode='IND']/n1:associatedEntity[@classCode='NOK']"/>

<xsl:variable
name="patientContactRelationshipType"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:code"/>



<!--Prefered HCP/ Legal Organization -->

<xsl:variable
name="preferedHCPLegalOrgan"
select="/n1:ClinicalDocument/n1:participant/n1:functionCode[@code='PCP'][@codeSystem='2.16.840.1.113883.5.88']/../n1:associatedEntity[@classCode='PRS']/n1:scopingOrganization"/>



<xsl:variable
name="preferedHCPLegalOrgan2"
select="/n1:ClinicalDocument/n1:participant/n1:functionCode[@code='PCP'][@codeSystem='2.16.840.1.113883.5.88']/../n1:associatedEntity[@classCode='PRS']/n1:associatedPerson"/>

<xsl:variable
name="preferedHCPLegalOrgName"
select="/n1:ClinicalDocument/n1:participant/n1:functionCode[@code='PCP'][@codeSystem='2.16.840.1.113883.5.88']/../n1:associatedEntity[@classCode='PRS']/n1:scopingOrganization/n1:name"/>


<xsl:variable
name="preferedHCPLegalOrg2Name"
select="/n1:ClinicalDocument/n1:participant/n1:functionCode[@code='PCP'][@codeSystem='2.16.840.1.113883.5.88']/../n1:associatedEntity[@classCode='PRS']/n1:associatedPerson/n1:name"/>



<xsl:variable
name="preferedHCPLegalOrg2FamilyName"
select="/n1:ClinicalDocument/n1:participant/n1:functionCode[@code='PCP'][@codeSystem='2.16.840.1.113883.5.88']/../n1:associatedEntity[@classCode='PRS']/n1:associatedPerson/n1:name/n1:family"/>

 
<xsl:variable
name="preferedHCPLegalOrg2GivenName"
select="/n1:ClinicalDocument/n1:participant/n1:functionCode[@code='PCP'][@codeSystem='2.16.840.1.113883.5.88']/../n1:associatedEntity[@classCode='PRS']/n1:associatedPerson/n1:name/n1:given"/>


<!--Prefered HCP/ Legal Organization Address-->
<xsl:variable
name="preferedHCPLegalOrg"
select="/n1:ClinicalDocument/n1:participant/n1:functionCode[@code='PCP'][@codeSystem='2.16.840.1.113883.5.88']/../n1:associatedEntity"/>

<xsl:variable
name="preferedHCPLegalOrg2"
select="/n1:ClinicalDocument/n1:participant/n1:functionCode[@code='PCP'][@codeSystem='2.16.840.1.113883.5.88']/../n1:associatedEntity/n1:scopingOrganization"/>


<xsl:variable
name="preferedHCPLegalOrgAddress"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr"/>

<xsl:variable
name="preferedHCPLegalOrgAddress2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:addr"/>


<xsl:variable
name="preferedHCPLegalOrgAddressStreet"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:streetAddressLine"/>

<xsl:variable
name="preferedHCPLegalOrgAddress2Street"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:addr/n1:streetAddressLine"/>


<xsl:variable
name="preferedHCPLegalOrgAddressStreetNumber"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:streetAddressLine"/>

<xsl:variable
name="preferedHCPLegalOrgAddress2StreetNumber"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:addr/n1:streetAddressLine"/>



<xsl:variable
name="preferedHCPLegalOrgCity"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:city"/>

<xsl:variable
name="preferedHCPLegalOrgCity2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:addr/n1:city"/>

<xsl:variable
name="preferedHCPLegalOrgPostalCode"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:postalCode"/>

<xsl:variable
name="preferedHCPLegalOrgPostalCode2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:addr/n1:postalCode"/>


<xsl:variable
name="preferedHCPLegalOrgPostalState"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:state"/>

<xsl:variable
name="preferedHCPLegalOrgPostalState2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:addr/n1:state"/>



<xsl:variable
name="preferedHCPLegalOrgPostalCountry"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:addr/n1:country"/>

<xsl:variable
name="preferedHCPLegalOrgPostalCountry2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:addr/n1:country"/>




<!--Prefered HCP/ Legal Organization Telecommunication-->

<xsl:variable
name="preferedHCPLegalOrgTelecom"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:telecom"/>

<xsl:variable
name="preferedHCPLegalOrgTelecom2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/telecom"/>

<xsl:variable
name="preferedHCPLegalOrgTelephone"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:telecom[1]/@value"/>
<xsl:variable
name="preferedHCPLegalOrgTelephoneUse"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:telecom[1]/@use"/>


<xsl:variable
name="preferedHCPLegalOrgTelephone2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:telecom[1]/@value"/>
<xsl:variable
name="preferedHCPLegalOrgTelephoneUse2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:telecom[1]/@use"/>





<xsl:variable
name="preferedHCPLegalOrgEmail"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:telecom[2]/@value"/>
<xsl:variable
name="preferedHCPLegalOrgEmailUse"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:scopingOrganization/n1:telecom[2]/@use"/>


<xsl:variable
name="preferedHCPLegalOrgEmail2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:telecom[2]/@value"/>
<xsl:variable
name="preferedHCPLegalOrgEmailUse2"
select="/n1:ClinicalDocument/n1:participant/n1:associatedEntity/n1:telecom[2]/@use"/>



<!--HCP Identification-->


<xsl:variable
name="HCPFamilyName"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:family"/>

<xsl:variable
name="HCPFamilyName2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:assignedPerson/n1:name/n1:family"/>


<xsl:variable
name="HCPGivenName"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:given"/>

<xsl:variable
name="HCPGivenName2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:assignedPerson/n1:name/n1:given"/>

<xsl:variable
name="HCPPrefix"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:prefix"/>

<xsl:variable
name="HCPPrefix2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:assignedPerson/n1:name/n1:prefix"/>


<xsl:variable
name="HCPSuffix"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:suffix"/>

<xsl:variable
name="HCPSuffix2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:assignedPerson/n1:name/n1:suffix"/>


<xsl:variable
name="HCPId"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:id"/>

<xsl:variable
name="HCPId2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:id"/>


<xsl:variable
name="HCPProfession"
select="/n1:ClinicalDocument/n1:author/n1:functionCode"/>

<xsl:variable
name="HCPProfession2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:functionCode"/>


<xsl:variable
name="HCPSpecialty"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:code"/>

<xsl:variable
name="HCPSpecialty2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:code"/>

<!--HCP Telecom	-->


<xsl:variable
name="HCPTelecom"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:telecom"/>

<xsl:variable
name="HCPTelecom2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:telecom"/>


<xsl:variable
name="HCPTelephoneNo"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:telecom[1]/@value"/>
<xsl:variable
name="HCPTelephoneNoUse"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:telecom[1]/@use"/>

<xsl:variable
name="HCPTelephoneNo2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:telecom[1]/@value"/>

<xsl:variable
name="HCPTelephoneNoUse2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:telecom[1]/@use"/>


<xsl:variable
name="HCPEmail"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:telecom[2]/@value"/>
<xsl:variable
name="HCPEmailUse"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:telecom[2]/@use"/>

<xsl:variable
name="HCPEmail2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:telecom[2]/@value"/>

<xsl:variable
name="HCPEmailUse2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:telecom[2]/@use"/>



<!-- Authoring device -->
<xsl:variable
name="AuthoringDveviceName"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedAuthoringDevice"/>

<!--

<xsl:variable
name="AuthoringDveviceName2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:assignedPerson/n1:name"/>

-->



<!--Healthcare Facility -->

<xsl:variable
name="HealthCareFacility"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization"/>

<xsl:variable
name="HealthCareFacility2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization"/>

<xsl:variable
name="HealthCareFacilityName"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:name"/>

<xsl:variable
name="HealthCareFacilityName2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:name"/>


<xsl:variable
name="HealthCareFacilityId"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:id"/>

<xsl:variable
name="HealthCareFacilityId2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:id"/>


<!-- Healthcare Facility’s Address -->


<xsl:variable
name="HealthCareFacilityAddress"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:addr"/>

<xsl:variable
name="HealthCareFacilityAddress2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:addr"/>



<xsl:variable
name="HealthCareFacilityStreet"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:addr/n1:streetAddressLine"/>

<xsl:variable
name="HealthCareFacilityStreet2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:addr/n1:streetAddressLine"/>


<xsl:variable
name="HealthCareFacilityCity"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:addr/n1:city"/>

<xsl:variable
name="HealthCareFacilityCity2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:addr/n1:city"/>


<xsl:variable
name="HealthCareFacilityState"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:addr/n1:state"/>

<xsl:variable
name="HealthCareFacilityState2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:addr/n1:state"/>


<xsl:variable
name="HealthCareFacilityPostalCode"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:addr/n1:postalCode"/>

<xsl:variable
name="HealthCareFacilityPostalCode2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:addr/n1:postalCode"/>


<xsl:variable
name="HealthCareFacilityPostalCountry"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:addr/n1:country"/>

<xsl:variable
name="HealthCareFacilityPostalCountry2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:addr/n1:country"/>




<!--Healthcare Facilities’s Telecom	-->


<xsl:variable
name="HealthCareFacilityTelecom"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:telecom"/>

<xsl:variable
name="HealthCareFacilityTelecom2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:telecom"/>


<xsl:variable
name="HealthCareFacilityTelephone"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:telecom[1]/@value"/>


<xsl:variable
name="HealthCareFacilityTelephoneUse"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:telecom[1]/@use"/>


<xsl:variable
name="HealthCareFacilityTelephone2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:telecom[1]/@value"/>


<xsl:variable
name="HealthCareFacilityTelephoneUse2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:telecom[1]/@use"/>


<xsl:variable
name="HealthCareFacilityEmail"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:telecom[2]/@value"/>


<xsl:variable
name="HealthCareFacilityEmailUse"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:representedOrganization/n1:telecom[2]/@use"/>


<xsl:variable
name="HealthCareFacilityEmail2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:telecom[2]/@value"/>


<xsl:variable
name="HealthCareFacilityEmailUse2"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:telecom[2]/@use"/>





<!--Document identification	-->
<xsl:variable
name="DocumentIdentification"
select="/n1:ClinicalDocument"/>
<xsl:variable
name="DocumentId"
select="/n1:ClinicalDocument/n1:id"/>


<xsl:variable
name="DocumentOrigin"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson"/>

<xsl:variable
name="DocumentOrigin2"
select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedAuthoringDevice"/>


<xsl:variable
name="ClinicalDocumentCode"
select="/n1:ClinicalDocument/n1:code"/>
<xsl:variable
name="ClinicalDocumentTitle"
select="/n1:ClinicalDocument/n1:title"/>
<xsl:variable
name="ConfidentialityCode"
select="/n1:ClinicalDocument/n1:confidentialityCode/@code"/>

<!-- Legal Othenticator -->

<xsl:variable
name="LegalAuthenticator"
select="/n1:ClinicalDocument/n1:legalAuthenticator/n1:assignedEntity/n1:assignedPerson"/>

<xsl:variable
name="LegalAuthenticator2"
select="/n1:ClinicalDocument/n1:legalAuthenticator/n1:assignedEntity/n1:representedOrganization"/>

<xsl:variable
name="DocumentLanguageCode"
select="/n1:ClinicalDocument/n1:languageCode"/>



<!--Custodian-->
<xsl:variable
name="patientCustodian"
select="/n1:ClinicalDocument/n1:custodian/n1:assignedCustodian/n1:representedCustodianOrganization"/>

	
	<xsl:template name="extendedCdaHeader">
	 
	 
	 <table class="header_table">
				<tbody>
	
	
	
	<!-- Patient Contact Info-->
		<tr >
						<th >
						&#160;
						</th>
						<td>&#160;</td>
		</tr>
					<tr >
						<td >
							<!--Patient-->
							<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'51'"/>
							</xsl:call-template>
							<xsl:text>-</xsl:text>
							<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'12'"/>
							</xsl:call-template>
						</td>
						<td >
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="$patientWholeAddress"/>
								</xsl:call-template>
			
						
					
						</td>
					</tr>
					
	
	
	<!-- Preferred HCP/Legal organization to contact -->
	
					<tr >
						<th >
							<!-- Preferred HCP/Legal organization to contact-->
							<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'54'"/>
							</xsl:call-template>
						</th>
						<td >
						<!-- show person's name and if exists organization name -->
							<xsl:value-of select="$preferedHCPLegalOrg2Name/n1:given"/>&#160;
							<xsl:value-of select="$preferedHCPLegalOrg2Name/n1:family"/>&#160;
							<xsl:if test="$preferedHCPLegalOrgName">
								,<xsl:value-of select="$preferedHCPLegalOrgName/n1:given"/>&#160;
								<xsl:value-of select="$preferedHCPLegalOrgName/n1:family"/>
							</xsl:if>
						</td>
					</tr>
					
				<tr >
						<td >
						<!-- Contact Information-->
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'12'"/>
							</xsl:call-template>
						</td>
						<td >
						
						
							<xsl:choose>
							<!-- first person's addres.. then if not exist show org address -->
					<xsl:when test="$preferedHCPLegalOrg">
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="$preferedHCPLegalOrg"/>
								</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="$preferedHCPLegalOrg2"/>
								</xsl:call-template>
					</xsl:otherwise>
					
					</xsl:choose>
						
					
						</td>
					</tr>
					
					
					<!-- Author(HCP) -->
						<xsl:call-template name="authorsHCP"/>
										
								
					
					
					
					
						<!-- Authoring Device -->
								<tr >
						<th >
							<!-- Authoring Device -->
							<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'8'"/>
							</xsl:call-template>
						</th>
						<td>
						<!-- show person's name and if exists organization name -->
							<xsl:value-of select="$AuthoringDveviceName/n1:name/n1:given"/>&#160;
							<xsl:value-of select="$AuthoringDveviceName/n1:name/n1:family"/>&#160;
							
						</td>
					</tr>
					
				<tr >
						<td >
						<!-- Contact Information-->
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'12'"/>
							</xsl:call-template>
						</td>
						<td >
					
							<xsl:choose>
					<xsl:when test="$AuthoringDveviceName/n1:addr">
					
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="$AuthoringDveviceName"/>
								</xsl:call-template>
					</xsl:when>
					
		
					
					</xsl:choose>
					
				
					
						</td>
					</tr>
					
					
					
					
					<!-- Legal Authenticator -->
					
						<tr >
						<th >
						<!-- Legal Authenticator-->
						
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'40'"/>
							</xsl:call-template>
						</th>
						<td >
					
								<xsl:value-of  select="$LegalAuthenticator/n1:name/n1:given"/>&#160;
								<xsl:value-of  select="$LegalAuthenticator/n1:name/n1:family"/>&#160;
									<xsl:if test="$LegalAuthenticator2/n1:name">
											,<xsl:value-of select="$LegalAuthenticator2/n1:name"/>&#160;
										
								</xsl:if>
					
					
						</td>
					</tr>
					<tr>
					<td> <!-- Contact Information-->
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'12'"/>
							</xsl:call-template> </td>
					<td>
					<xsl:choose>
					<xsl:when test="n1:legalAuthenticator/n1:assignedEntity/n1:addr | n1:legalAuthenticator/n1:assignedEntity/n1:telecom">
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="n1:legalAuthenticator/n1:assignedEntity"/>
								</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="$LegalAuthenticator2"/>
								</xsl:call-template>
					</xsl:otherwise>
					
					</xsl:choose>
					</td>
					</tr>
					
					
					
					
					
						<!-- Other Contacts-->
					
					
						<tr >
						<th >
							<!-- Other Contacts-->
							<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'49'"/>
							</xsl:call-template>
						</th>
						<td >
						
						<!-- 
										<xsl:for-each select="/n1:ClinicalDocument/n1:participant/n1:functionCode[not(@code='PCP')]/../n1:associatedEntity[@classCode='PRS']/n1:associatedPerson">
							<xsl:value-of select="n1:name/n1:given"/>&#160;
							<xsl:value-of select="n1:name/n1:family"/>&#160;,
						</xsl:for-each>
					
					
						<xsl:for-each select="/n1:ClinicalDocument/n1:participant/n1:functionCode[not(@code='PCP')]/../n1:associatedEntity[@classCode='PRS']/n1:scopingOrganization">
							<xsl:value-of select="n1:name/n1:given"/>&#160;
							<xsl:value-of select="n1:name/n1:family"/>&#160;,
						</xsl:for-each>
					
					-->
					<!-- <ul>
					<xsl:for-each select="/n1:ClinicalDocument/n1:participant/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.2.4']/../n1:associatedEntity">
						
						<xsl:if test="n1:associatedPerson or n1:scopingOrganization">
						 <li><xsl:value-of select="n1:associatedPerson/n1:name/n1:given"/>&#160;
						   <xsl:value-of select="n1:associatedPerson/n1:name/n1:family"/>&#160; 
						   <xsl:value-of select="n1:scopingOrganization/n1:name"/>&#160;
							
						   </li>
						   </xsl:if>
						</xsl:for-each>
					</ul>
					
				-->
					
					
					
						</td>
					</tr>
					
				<tr >
						<td >
						<!-- Contact Information-->
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'12'"/>
							</xsl:call-template>
						</td>
						<td >
						<!-- 
						<xsl:for-each select="/n1:ClinicalDocument/n1:participant/n1:functionCode[not(@code='PCP')]/../n1:associatedEntity[@classCode='PRS']/n1:associatedPerson">
							<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="./.."/>
								</xsl:call-template><br/>
						</xsl:for-each>
					
					
						<xsl:for-each select="/n1:ClinicalDocument/n1:participant/n1:functionCode[not(@code='PCP')]/../n1:associatedEntity[@classCode='PRS']/n1:scopingOrganization">
							<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="./.."/>
								</xsl:call-template><br/>
						</xsl:for-each>
					
						 -->				
						<ul> 
						 <xsl:for-each select="/n1:ClinicalDocument/n1:participant/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.2.4']/../n1:associatedEntity">
						
						 <xsl:if test="not(../n1:functionCode) or not(../n1:functionCode/@code='PCP')"  >
							<xsl:if test="n1:associatedPerson or n1:scopingOrganization">
							
								<li> 
									<xsl:if test="n1:associatedPerson">
								
										<xsl:value-of select="n1:associatedPerson/n1:name/n1:given"/>&#160;
						   				<xsl:value-of select="n1:associatedPerson/n1:name/n1:family"/>
						   			</xsl:if>
						   			<xsl:if test="n1:associatedPerson and n1:scopingOrganization">
						   				&#160;
						   			</xsl:if> 
						   			<xsl:value-of select="n1:scopingOrganization/n1:name"/><br/>
							   		<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="."/>
									</xsl:call-template><br/>
								</li>
							</xsl:if>
							</xsl:if>
						</xsl:for-each>
					</ul>
					
					
						</td>
					</tr>
					
					
					<!-- Guardian-->
					
						<tr >
						<th >
						<!-- Guardian-->
						
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'35'"/>
							</xsl:call-template>
						</th>
						<td >
							<xsl:value-of select="$patientGuardian/n1:guardianPerson/n1:name/n1:given"/>&#160;
							<xsl:value-of select="$patientGuardian/n1:guardianPerson/n1:name/n1:family"/>&#160;
							
							
							
					
						</td>
					</tr>
					<tr>
					<td> <!-- Contact Information-->
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'12'"/>
							</xsl:call-template> </td>
					<td>
					
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="$patientGuardian"/>
								</xsl:call-template>
			
					</td>
					</tr>
					
					
						<!-- Custodian-->
					
						<tr >
						<th >
						<!-- Custodian-->
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'16'"/>
							</xsl:call-template>
						</th>
						<td >
						
						<xsl:value-of  select="$patientCustodian/n1:name"/>
						</td>
					</tr>
					<tr>
					<td> <!-- Contact Information-->
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'12'"/>
							</xsl:call-template> </td>
					<td>
									
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="$patientCustodian"/>
								</xsl:call-template>
			
					</td>
					</tr>
					
					
					
				</tbody>
			</table>
		
	 </xsl:template>
	
	
	<xsl:template  name="authorsHCP">
	
	<xsl:for-each select="/n1:ClinicalDocument/n1:author">
	
	<xsl:variable
name="hcpCounter"
select="position()"/>
	
	<xsl:variable
name="HCPIdentificationAuthor"
select="/n1:ClinicalDocument/n1:author[$hcpCounter]/n1:assignedAuthor"/>


<xsl:variable
name="HCPIdentificationPerformer"
select="/n1:ClinicalDocument/n1:documentationOf[$hcpCounter]/n1:serviceEvent/n1:performer/n1:assignedEntity"/>


<xsl:variable
name="HCPName"
select="/n1:ClinicalDocument/n1:author[$hcpCounter]/n1:assignedAuthor/n1:assignedPerson/n1:name"/>


<xsl:variable
name="HCPOrgName"
select="/n1:ClinicalDocument/n1:author[$hcpCounter]/n1:assignedAuthor/n1:representedOrganization/n1:name"/>



<xsl:variable
name="HCPName2"
select="/n1:ClinicalDocument/n1:documentationOf[$hcpCounter]/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:assignedPerson/n1:name"/>


<xsl:variable
name="HCPName2Org"
select="/n1:ClinicalDocument/n1:documentationOf[$hcpCounter]/n1:serviceEvent/n1:performer/n1:assignedEntity/n1:representedOrganization/n1:name"/>

	
	<tr >
						<th >
							<!-- Author (HCP) -->
							<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'7'"/>
							</xsl:call-template>
						</th>
						<td>
						<xsl:choose>
						<xsl:when test="$HCPName">
						<!-- show person's name and if exists organization name -->
							<xsl:value-of select="$HCPName/n1:given"/>&#160;
							<xsl:value-of select="$HCPName/n1:family"/>&#160;
							<xsl:if test="$HCPOrgName">
								,<xsl:value-of select="$HCPOrgName"/>&#160;
								
							</xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<xsl:if test="$HCPName2">
								<xsl:value-of select="$HCPName2/n1:given"/>&#160;
								<xsl:value-of select="$HCPName2/n1:family"/>
								<xsl:value-of select="$HCPOrgName"/>&#160;
								<xsl:value-of select="$HCPName2Org"/>&#160;
						</xsl:if>
						</xsl:otherwise>
						</xsl:choose>
						</td>
					</tr>
					
				<tr >
						<td >
						<!-- Contact Information-->
						<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'12'"/>
							</xsl:call-template>
						</td>
						<td >
					
							<xsl:choose>
					<xsl:when test="$HCPIdentificationAuthor/n1:addr">
					
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="$HCPIdentificationAuthor"/>
								</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
					
						<xsl:call-template name="show-contactInfo">
										<xsl:with-param name="contact" select="$HCPIdentificationPerformer"/>
								</xsl:call-template>
					</xsl:otherwise>
					
					</xsl:choose>
					
				
					
						</td>
					</tr>
	</xsl:for-each>
	</xsl:template>
	
</xsl:stylesheet>