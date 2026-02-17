<?xml version="1.0" encoding="utf-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron" xmlns:cda="urn:hl7-org:v3" xmlns:iso="http://purl.oclc.org/dsdl/schematron" xmlns:sch="http://www.ascc.net/xml/schematron" xmlns:xi="http://www.w3.org/2003/XInclude" queryBinding="xslt2">
  <title>Schematron schema for validating epSOS Patient Summary Document</title>
  <ns prefix="cda" uri="urn:hl7-org:v3" />
  <ns prefix="epsos" uri="urn:epsos-org:ep:medication" />
  <ns prefix="xsi" uri="http://www.w3.org/2001/XMLSchema-instance" />
  <phase id="errors">
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.1.3-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <!-- <title>epSOS Patient Summary Document Specification - errors validation phase</title>-->
      <rule context="/cda:ClinicalDocument">
        <!-- Verify that the template id is Patient Summary Document -->
        <assert test="//cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.1.3&quot;]">Error: (cf.&#194;&#167;1 epSOS_Implementation_v0.2.20100702.doc) The Patient Summary Document requires the Patient Summary Document template ID
        (1.3.6.1.4.1.12559.11.10.1.3.1.1.3).</assert>
        <report test="//cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.1.3&quot;]">Success: (cf.&#194;&#167;1 epSOS_Implementation_v0.2.20100702.doc) The Patient Summary Document correctly references the Patient Summary Document template ID
        (1.3.6.1.4.1.12559.11.10.1.3.1.1.3).</report>
        <!-- Verify that the correct code system is used (cf.§2 epSOS_Implementation_v0.2.20100702.doc)-->
        <assert test="cda:code[@code = &quot;RELMAD572&quot;]">Error: (cf.&#194;&#167;2 epSOS_Implementation_v0.2.20100702.doc) The document type code of a Medical Document shall be set to the value "RELMAD572".</assert>
        <report test="cda:code[@code = &quot;RELMAD572&quot;]">Success: (cf.&#194;&#167;2 epSOS_Implementation_v0.2.20100702.doc) The document type code of a Medical Document is correctly set to the value "RELMAD572".</report>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: (cf.&#194;&#167;2 epSOS_Implementation_v0.2.20100702.doc) The Patient Summary document type code shall be set to the value "2.16.840.1.113883.6.1" corresponding to
        the LOINC Code system OID.</assert>
        <report test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Success: (cf.&#194;&#167;2 epSOS_Implementation_v0.2.20100702.doc) The Patient Summary document type code is correctly set to the value "2.16.840.1.113883.6.1" corresponding
        to the LOINC Code system OID.</report>
        <assert test="//cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.2.3&quot;]">Error: (cf.&#194;&#167;13.1 epSOS_Implementation_v0.2.20100702.doc) The Patient Summary Document requires the Medication Summary Section name\template ID
        (1.3.6.1.4.1.12559.11.10.1.3.1.2.3).</assert>
        <report test="//cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.2.3&quot;]">Success: (cf.&#194;&#167;13.1 epSOS_Implementation_v0.2.20100702.doc) The Patient Summary Document correctly references the Medication Summary Section
        name\template ID (1.3.6.1.4.1.12559.11.10.1.3.1.2.3).</report>
      </rule>
    </pattern>
    <pattern xmlns:xi="http://www.w3.org/2003/XInclude" xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Medical Documents Specification - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.1.1&quot;]">
        <assert test="self::cda:ClinicalDocument">Error: IHE PCC Medical Document Specification template ID (1.3.6.1.4.1.19376.1.5.3.1.1.1) is in the wrong location. An IHE PCC Medical Document Specification template ID shall be only used on a
        ClinicalDocument.</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Medical Document Specification template ID (1.3.6.1.4.1.19376.1.5.3.1.1.1), the document type code must come from the LOINC code system
        (2.16.840.1.113883.6.1).</assert>
      </rule>
      <!--epSOS-->
      <!--(cf.epSOS_Implementation_v0-1.0.1.20100623.doc : 8  Common Header Data Elements)-->
      <!--     <rule context="/">
                <!-\- R1.11 Document Identification-\->
                <assert test="cda:ClinicalDocument and count(cda:ClinicalDocument)&lt;2"> 
                Error: R1.11 The Medical Document Specification
                template ID (1.3.6.1.4.1.19376.1.5.3.1.1.1) is in the wrong location. An epSOS
                Medical Document Specification template ID shall be only used on a ClinicalDocument.
                </assert>
                </rule>-->
      <!-- R1.11.1 Date of creation-->
      <rule context="/cda:ClinicalDocument">
        <assert test="(matches(cda:effectiveTime/@value,'\d{4}') or matches(cda:effectiveTime/@value,'\d{6}') or matches(cda:effectiveTime/@value,'\d{8}')) and cda:effectiveTime and count(cda:effectiveTime)&lt;2">Error:R1.11.1 Date of creation is
        required. The attribute "value" of the element "effectiveTime" shall contain 4,6 or 8 digits, it may be a partial date such as only the year. Expecting a string such as YYYY[MM[DD]]. The element "effectiveTime" cannot be repeated (cardinality
        [1..1]).</assert>
        <!-- R1.11.3 Document ID-->
        <assert test="cda:id and count(cda:id)&lt;2">Error:R1.11.3 Document ID is required. The element "id" cannot be repeated (cardinality [1..1]).</assert>
        <!-- R1.11.6 Clinical document code-->
        <assert test="cda:code and cda:code[@codeSystem='2.16.840.1.113883.6.1'] and cda:code/@code and count(cda:code)&lt;2">Error:R1.11.3 Clinical document code is required. The attributes "codeSystem" and "code" of the element "code" shall not be
        empty. The attribute "codeSystem" sould be "2.16.840.1.113883.6.1". The element "code" cannot be repeated (cardinality [1..1]).</assert>
        <!-- R1.11.7 Clinical document title-->
        <assert test="cda:title and (every $i in cda:title satisfies string-length(normalize-space($i)) &gt; 0) and count(cda:title)&lt;2">Error:R1.11.7 Clinical document title is required. The element "title" shall not be empty. The element "title"
        cannot be repeated (cardinality [1..1]).</assert>
        <!-- R1.11.8 Confidentiality code-->
        <assert test="cda:confidentialityCode and ((cda:confidentialityCode[@codeSystem='2.16.840.1.113883.5.25'] and cda:confidentialityCode/@code) or cda:confidentialityCode/@nullFlavor) and count(cda:confidentialityCode)&lt;2">Error:R1.11.8
        Confidentiality code is required. The attributes 'codeSystem' and 'code' of the element "confidentialityCode" shall not be empty otherwise the nullFlavor attribute shall be present. The attribute 'codeSystem' sould be '2.16.840.1.113883.5.25'.
        The element "confidentialityCode" cannot be repeated (cardinality [1..1]).</assert>
        <!-- R1.11.10 Document Language Code -->
        <assert test="cda:languageCode and cda:languageCode/@code and count(cda:languageCode)&lt;2">Error:R1.11.10 Document Language Code is required. The attribute'code' of the element "languageCode" shall not be empty. The element "languageCode" cannot
        be repeated (cardinality [1..1]).</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:legalAuthenticator/cda:assignedEntity">
        <!-- R1.11.9 Legal Authenticator Family Name/Surname -->
        <assert test="(cda:assignedPerson and cda:assignedPerson/cda:name and cda:assignedPerson/cda:name/cda:family and (every $i in cda:assignedPerson/cda:name/cda:family satisfies string-length(normalize-space($i)) &gt; 0)) or cda:assignedPerson/cda:name/@nullFlavor or cda:assignedPerson/@nullFlavor">
        Error: R1.11.9 Legal Authenticator Family Name/Surname is required (like R1.10.1). The element "family" shall not be empty.</assert>
        <!-- R1.11.9 Legal Authenticator Given Name-->
        <assert test="(cda:assignedPerson and cda:assignedPerson/cda:name and cda:assignedPerson/cda:name/cda:given and (every $i in cda:assignedPerson/cda:name/cda:given satisfies string-length(normalize-space($i)) &gt; 0)) or cda:assignedPerson/cda:name/@nullFlavor or cda:assignedPerson/@nullFlavor">
        Error: R1.11.9 Legal Authenticator Given Name is required (like R1.10.2). The element "given" shall not be empty.</assert>
        <!-- R1.11.9 Legal Authenticator ID number -->
        <assert test="(cda:id) or @nullFlavor">Error: R1.11.9 Legal Authenticator ID number is required (like R1.10.5).</assert>
        <assert test="(cda:code and cda:code/@code and cda:code/@codeSystem) or @nullFlavor or not(cda:code)">Error: R1.11.9 Legal Authenticator Code is optional (like R1.10.7). If it's present, the attributes 'code' and 'codeSystem' of the element
        "code" shall not be empty.</assert>
        <!-- R1.11.10 Legal Authenticator Facility -->
        <assert test="cda:representedOrganization">Error: R1.11.10 representedOrganization is required (like R1.10.9).</assert>
        <!-- R1.11.10 Legal Authenticator Facility name -->
        <assert test="(cda:representedOrganization/cda:name and (every $i in cda:representedOrganization/cda:name satisfies count(($i))&lt; 2) and ((every $i in cda:representedOrganization/cda:name satisfies string-length(normalize-space($i)) &gt; 0) or cda:representedOrganization/cda:name/@nullFlavor )) or cda:representedOrganization/@nullFlavor">
        Error: R1.11.10 Legal Authenticator Facility name is required (like R1.10.9.1). The element (name) shall not be empty otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.11.10 Legal Authenticator Facility ID -->
        <assert test="(cda:representedOrganization/cda:id and (every $i in cda:representedOrganization/cda:id satisfies count(($i))&lt; 2) and (cda:representedOrganization/cda:id/@root or cda:representedOrganization/cda:id/@nullFlavor)) or cda:representedOrganization/@nullFlavor">
        Error: R1.11.10 Legal Authenticator Facility ID is required (like R1.10.9.2). The attribute 'root' of the element (id) shall not be empty otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.11.10 Legal Authenticator Facility address -->
        <assert test="cda:representedOrganization/cda:addr">Error: R1.11.10 Legal Authenticator address is required (like R1.10.9.3).</assert>
      </rule>
      <!-- R1.11.9 Legal Authenticator Telecommunication-->
      <rule context="/cda:ClinicalDocument/cda:legalAuthenticator/cda:assignedEntity/cda:telecom">
        <assert test=" (matches(@value,'tel:\+[0-9/(/)/-]') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0) ) or (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0))">
        Error: R1.10.9.4.1/R1.10.9.4.2 HCP Telecom telephone/mail address is required. The attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like : example@exp.com and the Patient
        Contact's telephone number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960. The attribute 'use' shall not be empty.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient">
        <!-- R1.1 Patient Name-->
        <assert test="cda:name">Error: R1.1 Patient Name is required.</assert>
        <!-- R1.2 Gender-->
        <assert test="cda:administrativeGenderCode and count(cda:administrativeGenderCode)&lt;2">Error: R1.2 administrativeGenderCode is required. The element "administrativeGenderCode" cannot be repeated (cardinality [1..1]).</assert>
        <assert test="(cda:administrativeGenderCode[@codeSystem = &quot;2.16.840.1.113883.5.1&quot;] and not(@nullFlavor)) or cda:administrativeGenderCode[@nullFlavor='UNK']">Error: R1.2 Gender codeSystem shall be 2.16.840.1.113883.5.1 currently set to 
        <value-of select="*/cda:administrativeGenderCode/@codeSystem" />otherwise the nullFlavor attribute shall be set to 'UNK'.</assert>
        <assert test="cda:administrativeGenderCode[@code = 'UN' or @code = 'F' or @code = 'M' ] or cda:administrativeGenderCode[@nullFlavor='UNK']">Error: R1.2 The attribute 'code' of the element (gender) must be set to one of the following values : 'F',
        'M' or 'UN' otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.3 Date of Birth-->
        <assert test="cda:birthTime and count(cda:birthTime)&lt;2">Error: R1.3 Date of Birth is required. The element "birthTime" cannot be repeated (cardinality [1..1]).</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:name">
        <!-- R1.1.1 Family name/Surname-->
        <assert test="(cda:family and (every $i in cda:family satisfies string-length(normalize-space($i)) &gt; 0)) or @nullFlavor">Error: R1.1.1 Family name/Surname is required. The element (family) shall not be empty.</assert>
        <!-- R1.1.3 Given Name-->
        <assert test="(cda:given and (every $i in cda:given satisfies string-length(normalize-space($i)) &gt; 0)) or @nullFlavor">Error: R1.1.3 Given Name is required. The element (given) shall not be empty.</assert>
        <!-- R1.1.2 Prefix-->
        <assert test="(cda:prefix and (every $i in cda:prefix satisfies string-length(normalize-space($i)) &gt; 0)) or not(cda:prefix)">Error: R1.1.2 Prefix is optional, if the element "prefix" is present, it shall not be empty.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:birthTime">
        <!-- R1.3 Date of Birth-->
        <assert test="(matches(@value,'\d{4}') or matches(@value,'\d{6}') or matches(@value,'\d{8}'))">Error: R1.3 Date of Birth shall contain 4,6 or 8 digits. DOB may be a partial date such as only the year. Expecting a string such as
        YYYY[MM[DD]]</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole">
        <!-- R1.4 Primary Patient Identifier-->
        <assert test="cda:id and cda:id/@root">Error: R1.4.1 Primary Patient Identifier (Regional/National Health Id) is required. The attribute "root" of the element (id) shall not be empty.</assert>
        <!-- R1.5 Patient's Address-->
        <assert test="cda:addr">Error: R1.5 Patient's Address element is required.</assert>
        <!-- R1.6 Patient's Telecommunication-->
        <assert test="cda:telecom">Error: R1.6 The patient telephone or e-mail element is required.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:addr">
        <!-- R1.5 Patient's Address-->
        <assert test="(((cda:streetAddressLine) or (cda:city) or (cda:state) or (cda:country)) and not(@nullFlavor)) or ( (@nullFlavor ='NI') and (not(cda:streetAddressLine) and not(cda:city) and not(cda:state) and not(cda:country)))">Error: R1.5
        Patient's Address element is required. If there is no information, the nullFlavor attribute shall have a value of 'NI' and no address parts shall be present, otherwise there shall be no nullFlavor attribute, and at least one of the address parts
        listed below shall be present</assert>
        <!-- R1.5.1/R1.5.2 Patient's Street/Patient's Number of Street-->
        <assert test="@nullFlavor or ((every $i in cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:streetAddressLine/@nullFlavor)">Error: R1.5.1 Patient's Street is required. The element (streetAddressLine) shall not be
        empty, otherwise the nullFlavor attribute shall be present .</assert>
        <!-- R1.5.3 Patient's City-->
        <assert test="@nullFlavor or ((every $i in cda:city satisfies string-length(normalize-space($i)) &gt; 0) or cda:city/@nullFlavor)">Error: R1.5.3 Patient's City is required. The element (city) shall not be empty, otherwise the nullFlavor attribute
        shall be present.</assert>
        <!-- R1.5.4 Patient's Postal Code-->
        <assert test="@nullFlavor or ((every $i in cda:postalCode satisfies string-length(normalize-space($i)) &gt; 0) or cda:postalCode/@nullFlavor)">Error: R1.5.4 Patient's Postal Code is required. The element (postalCode) shall not be empty, otherwise
        the nullFlavor attribute shall be present.</assert>
        <!-- R1.5.5 Patient's State or Province-->
        <assert test="@nullFlavor or ((every $i in cda:state satisfies string-length(normalize-space($i)) &gt; 0) or cda:state/@nullFlavor)">Error: R1.5.4 Patient's State or Province is required. The element (state) shall not be empty, otherwise the
        nullFlavor attribute shall be present.</assert>
        <!-- R1.5.6 Patient's Country-->
        <assert test="@nullFlavor or ((every $i in cda:country satisfies string-length(normalize-space($i)) &gt; 0) or cda:country/@nullFlavor)">Error: R1.5.4 Patient's Country is required. The element (country) shall not be empty, otherwise the
        nullFlavor attribute shall be present.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:telecom">
        <!-- R1.6 Patient's Telecommunication-->
        <assert test=" ((@nullFlavor='NI') and not(@value) and not(@use)) or (not(@nullFlavor) and (@value and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0)))">Error: R1.6 The patient telephone or e-mail element is required. If
        there is no information, the nullFlavor attribute shall have a value of 'NI' and the "value" and "use" attributes shall be omitted, otherwise the nullFlavor attribute shall not be present, and the "value" and "use" attributes shall be
        present</assert>
        <!-- R1.6 Patient's Telecommunication-->
        <assert test="((@nullFlavor='NI') and not(@value) and not(@use)) or (not(@nullFlavor) and (matches (@value,'tel:\+[0-9/(/)/-]'))) or (not(@nullFlavor) and (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$')))">
        Error: R1.6 The Patient's telephone number(R1.6.1) or the Patient's e-mail address (R1.6.2) are miswritten : the Patient's e-mail address shall be like : example@exp.com and the Patient's telephone number shall be like : "tel:+13176307960" or
        "tel:+1(317)630-7960"</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Language Communication - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.2.1&quot;]">
        <!-- Verify that the template id is used on the appropriate type of object
   -->
        <assert test="../cda:languageCommunication">Error: In IHE PCC Language Communication (1.3.6.1.4.1.19376.1.5.3.1.2.1), the IHE PCC LanguageCommunication shall describe this information using the languageCommunication element.</assert>
        <!--Verify the Patient's preferred language (cf.epSOS_Implementation_v0-1.0.1.20100623.doc :  Common Header Data Elements R1.7) -->
        <assert test="cda:languageCode or ../cda:languageCommunication/@nullFlavor">Error: R1.7 In IHE PCC Language Communication (1.3.6.1.4.1.19376.1.5.3.1.2.1), the languageCode element describes the language code. It uses the same vocabulary described
        for the ClinicalDocument/languageCode element described in more detail in HL7 CRS: 2.1.1. This element "languageCode" is required. Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:languageCode">Success:R1.7 The Patient's preferred language element "languageCode" is present.</report>
        <assert test="not(cda:modeCode) or cda:modeCode[@codeSystem=&quot;2.16.840.1.113883.5.60&quot;]">Error: In IHE PCC Language Communication (1.3.6.1.4.1.19376.1.5.3.1.2.1), the modeCode element describes the mode of use, and is only necessary when
        there are differences between expressive and receptive abilities. This element is optional. When not present, the assumption is that any further detail provided within the languageCommunication element refers to all common modes of communication.
        The coding system used shall be the HL7 LanguageAbilityMode vocabulary when this element is communicated.</assert>
        <assert test="not(cda:proficiencyLevelCode) or cda:proficiencyLevelCode[@codeSystem=&quot;2.16.840.1.113883.5.61&quot;]">Error: In IHE PCC Language Communication (1.3.6.1.4.1.19376.1.5.3.1.2.1), the proficiencyLevelCode element describes the
        proficiency of the patient (with respect to the mode if specified). This element is optional. The coding system used shall be the HL7 LanguageProficiencyCode vocabulary when this element is communicated.</assert>
        <assert test="not(cda:preferenceInd) or cda:preferenceInd[@value=&quot;true&quot;] or cda:preferenceInd[@value=&quot;false&quot;]">Error: In IHE PCC Language Communication (1.3.6.1.4.1.19376.1.5.3.1.2.1), the preferenceInd shall be valued "true"
        if this language is the patient's preferred language for communication, or "false" if this is not the patient's preferred language.</assert>
      </rule>
      <!--epSOS-->
      <!--(cf.epSOS_Implementation_v0-1.0.1.20100623.doc : 8  Common Header Data Elements)-->
      <rule context="*[cda:templateId/@root='1.3.6.1.4.1.19376.1.5.3.1.2.1']/cda:languageCode">
        <assert test="(@code and (matches(@code,'^[a-z][a-z]\-[A-Z][A-Z]$')) and (not(@nullFlavor))) or @nullFlavor">Error: R1.7 The Patient's preferred language code element "languageCode\@code" SHALL be in the form nn-CC.The nn portion SHALL be an
        ISO-639-1 language code in lower case derived by the Value Set epSOSLanguage 1.3.6.1.4.1.12559.11.10.1.3.1.42.6. The CC portion, if present, SHALL be an ISO-3166 country code in upper case derived by the value Set epSOSCountry
        1.3.6.1.4.1.12559.11.10.1.3.1.42.4. Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="(@code and (matches(@code,'^[a-z][a-z]\-[A-Z][A-Z]$')))">Success:R1.7 The Patient's preferred language code element "languageCode\@code" is fullfilled.</report>
      </rule>
      <!--cf.R1.7 We can have multiple languageCommunication element-->
      <!--    <rule context='/cda:*'>
      <assert test='count(//cda:recordTarget//cda:languageCommunication) &lt; 2 or
                    count(//cda:recordTarget//cda:languageCommunication) = count(//cda:recordTarget//cda:languageCommunication/cda:preferenceInd) '>
         Error: In IHE PCC Language Communication (1.3.6.1.4.1.19376.1.5.3.1.2.1), the preferenceInd element shall be 
         present on all languageCommunication elements when more than one is provided.
      </assert>
      <assert
      test='count(*[cda:templateId/@root="1.3.6.1.4.1.19376.1.5.3.1.2.1"]) = 0 or
            count(//cda:languageCommunication/cda:preferenceInd[@value="true"]) &gt; 0'>
         Error: In IHE PCC Language Communication (1.3.6.1.4.1.19376.1.5.3.1.2.1), when present at least one languageCommunication
         element must have a preferred value of true.
      </assert>

    </rule>-->
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Patient Contacts - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.2.4&quot;]">
        <assert test="not(../cda:participant) or ../cda:participant[@typeCode=&quot;IND&quot;]">Error: In IHE PCC Patient Contacts (1.3.6.1.4.1.19376.1.5.3.1.2.4), contacts that are recorded as participant elements shall have the classCode attribute
        shall set to 'IND'.</assert>
        <assert test="not(../cda:participant) or cda:associatedEntity[@classCode = &quot;AGNT&quot; or @classCode = &quot;CAREGIVER&quot; or @classCode = &quot;ECON&quot; or @classCode = &quot;NOK&quot; or @classCode = &quot;PRS&quot;]">Error: In IHE PCC
        Patient Contacts (1.3.6.1.4.1.19376.1.5.3.1.2.4), the associatedEntity element identifies the type of contact. The classCode attribute shall be present, and contains a value from the set AGNT, CAREGIVER, ECON, NOK, or PRS to identify contacts
        that are agents of the patient, care givers, emergency contacts, next of kin, or other relations respectively.</assert>
        <assert test="not(../cda:participant) or not(cda:associatedEntity/cda:code) or cda:associatedEntity/cda:code[@code and @codeSystem=&quot;2.16.840.1.113883.5.111&quot;]">Error: In IHE PCC Patient Contacts (1.3.6.1.4.1.19376.1.5.3.1.2.4), in the
        associatedEntity code, the code attribute is required and comes from the HL7 PersonalRelationshipRoleType vocabulary. The codeSystem attribute is required and shall be represented exactly as shown.</assert>
      </rule>
      <!--epSOS-->
      <!--(cf.epSOS_Implementation_v0-1.0.1.20100623.doc : 8  Common Header Data Elements)-->
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient">
        <!-- R1.7.A Patient's Guardian-->
        <assert test="cda:guardian">Error: R1.7.A Patient's Guardian is required.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:guardian">
        <!-- R1.7.A Patient's Guardian-->
        <assert test="(@nullFlavor and not(@classCode) ) or (@classCode = 'GUARD' and not(@nullFlavor))">Error: R1.7.A Patient's Guardian is required.If there is no information, the nullFlavor attribute shall be present and the classCode attribute shall
        not be present, otherwise there shall be no nullFlavor attribute and The classCode attribute shall contains the value 'GUARD'.</assert>
        <!-- R1.7.A.3 Guardian's Address-->
        <assert test="cda:addr or @nullFlavor">Error: R1.7.A.3 Guardian's Address element is required.</assert>
        <!-- R1.7.A.4 Guardian's Telecommunication-->
        <assert test="cda:telecom or @nullFlavor">Error: R1.7.A.4 The Guardian telephone or e-mail element is required.</assert>
        <!-- R1.7.A.1 Guardian's-->
        <assert test="cda:guardianPerson or @nullFlavor">Error: R1.7.A.1 The "guardianPerson" element is required in 'guardian\' otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.7.A.1 Guardian's Name-->
        <assert test="cda:guardianPerson/cda:name or @nullFlavor or cda:guardianPerson/@nullFlavor">Error: R1.7.A.1 The guardian's "name" element is required in 'guardian\guardianPerson\' otherwise the nullFlavor attribute shall be present.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:guardian/cda:guardianPerson/cda:name">
        <!-- R1.7.A.1 Guardian's Family Name/Surname -->
        <assert test="(cda:family and (every $i in cda:family satisfies string-length(normalize-space($i)) &gt; 0) and not(cda:family[@nullFlavor])) or (cda:family/@nullFlavor) or @nullFlavor">Error: R1.7.A.1 Guardian's Family Name/Surname is
        required.The element (family) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.7.A.2 Guardian's Given Name -->
        <assert test="(cda:given and (every $i in cda:given satisfies string-length(normalize-space($i)) &gt; 0) and not(cda:given[@nullFlavor])) or (cda:given/@nullFlavor) or @nullFlavor">Error: R1.7.A.2 Guardian's Given Name is required.The element
        (given) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:guardian/cda:addr">
        <!-- R1.7.A.3 Guardian's Address-->
        <assert test="(((cda:streetAddressLine) or (cda:city) or (cda:state) or (cda:country)) and not(@nullFlavor)) or ((@nullFlavor='UNK') and (not(cda:streetAddressLine) and not(cda:city) and not(cda:state) and not(cda:country)))">Error: R1.7.A.3
        Guardian's Address element is required. If there is no information, the nullFlavor attribute shall be set to 'UNK' and no address parts shall be present, otherwise there shall be no nullFlavor attribute, and at least one of the address parts
        listed below shall be present.</assert>
        <!-- R1.7.A.3.1/R1.7.A.3.2 Guardian's Street/Guardian's Number of Street-->
        <assert test="@nullFlavor or ((every $i in cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:streetAddressLine/@nullFlavor)">Error: R1.7.A.3.1 Guardian's Street is required. The element (streetAddressLine) shall
        not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.7.A.3.2.3 Guardian's City-->
        <assert test="@nullFlavor or ((every $i in cda:city satisfies string-length(normalize-space($i)) &gt; 0) or cda:city/@nullFlavor)">Error: R1.7.A.3.2.3 Guardian's City is required. The element (city) shall not be empty, otherwise the nullFlavor
        attribute shall be present.</assert>
        <!-- R1.7.A.3.2.4 Guardian's Postal Code-->
        <assert test="@nullFlavor or ((every $i in cda:postalCode satisfies string-length(normalize-space($i)) &gt; 0) or cda:postalCode/@nullFlavor)">Error: R1.7.A.3.2.4 Guardian's Postal Code is required. The element (postalCode) shall not be empty,
        otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.7.A.3.2.5 Guardian's State or Province-->
        <assert test="@nullFlavor or ((every $i in cda:state satisfies string-length(normalize-space($i)) &gt; 0) or cda:state/@nullFlavor)">Error: R1.7.A.3.2.5 Guardian's State or Province is required. The element (state) shall not be empty, otherwise
        the nullFlavor attribute shall be present.</assert>
        <!-- R1.7.A.3.2.6 Guardian's Country-->
        <assert test="@nullFlavor or ((every $i in cda:country satisfies string-length(normalize-space($i)) &gt; 0) or cda:country/@nullFlavor)">Error: R1.7.A.3.2.6 Guardian's Country is required. The element (country) shall not be empty, otherwise the
        nullFlavor attribute shall be present.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:recordTarget/cda:patientRole/cda:patient/cda:guardian/cda:telecom">
        <!-- R1.7.A.4 Guardian's Telecommunication-->
        <assert test=" ((@nullFlavor='NI') and not(@value) and not(@use)) or (not(@nullFlavor) and (@value and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0)))">Error: R1.7.A.4 The Guardian telephone or e-mail element is required.
        If there is no information, the nullFlavor attribute shall have a value of 'NI' and the "value" and "use" attributes shall be omitted, otherwise the nullFlavor attribute shall not be present, and the "value" and "use" attributes shall be
        present.</assert>
        <!-- R1.7.A.4 Patient's Telecommunication-->
        <assert test="((@nullFlavor='NI') and not(@value) and not(@use)) or (not(@nullFlavor) and (matches (@value,'tel:\+[0-9/(/)/-]'))) or (not(@nullFlavor) and (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$')))">
        Error: R1.7.A.4 The Guardian's telephone number(R1.7.A.4.1) or the Guardian's e-mail address (R1.7.A.4.2) are miswritten : the Guardian's e-mail address shall be like : example@exp.com and the Guardian's telephone number shall be like :
        "tel:+13176307960" or "tel:+1(317)630-7960"</assert>
      </rule>
      <!-- R1.8 Contact Person-->
      <rule context="/cda:ClinicalDocument">
        <assert test="cda:participant">Error: R1.8 The "Participant" element is required in 'ClinicalDocument\'.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:participant">
        <assert test="cda:associatedEntity or @nullFlavor">Error: R1.8 The "associatedEntity" element is required in 'ClinicalDocument\participant\' .</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:participant[not(cda:functionCode/@code='PCP') or not(cda:functionCode/@codeSystem='2.16.840.1.113883.5.88')]/cda:associatedEntity">
        <!-- R1.8 Contact Person-->
        <assert test="cda:associatedPerson or @nullFlavor">Error: R1.8 The "associatedPerson" element is required in 'ClinicalDocument\participant\associatedEntity' .</assert>
        <!-- R1.8.4 Patient Contact's Telecommunication-->
        <assert test="cda:telecom or @nullFlavor">Error: R1.8.4 The Patient Contact's telephone or e-mail element is required.</assert>
        <!-- R1.8.3 Patient Contact's Address-->
        <assert test="cda:addr or @nullFlavor">Error: R1.8.3 The Patient Contact's Address is required.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:participant[not(cda:functionCode/@code='PCP') or not(cda:functionCode/@codeSystem='2.16.840.1.113883.5.88')]/cda:associatedEntity/cda:associatedPerson">
        <!-- R1.8.1 Patient Contact's Name-->
        <assert test="cda:name or @nullFlavor">Error: R1.8.1 The Patient Contact's name is required.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:participant[not(cda:functionCode/@code='PCP') or not(cda:functionCode/@codeSystem='2.16.840.1.113883.5.88')]/cda:associatedEntity/cda:associatedPerson/cda:name">
        <!-- R1.8.1 Patient Contact's Family Name/Surname -->
        <assert test="((cda:family and (every $i in cda:family satisfies string-length(normalize-space($i)) &gt; 0)) or (cda:family and cda:family/@nullFlavor)) or @nullFlavor">Error: R1.8.1 Patient Contact's Family Name/Surname is required.The element
        (family) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.8.2 Patient Contact's Given Name -->
        <assert test="((cda:given and (every $i in cda:given satisfies string-length(normalize-space($i)) &gt; 0) ) or (cda:given and cda:given/@nullFlavor)) or @nullFlavor">Error: R1.8.2 Patient Contact's Given Name is required.The element (given) shall
        not be empty, otherwise the nullFlavor attribute shall be present.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:participant[not(cda:functionCode/@code='PCP') or not(cda:functionCode/@codeSystem='2.16.840.1.113883.5.88')]/cda:associatedEntity/cda:addr">
        <!-- R1.8.3.1/R1.8.3.2 Patient Contact's Street/Patient Contact's Number of Street-->
        <assert test="@nullFlavor or ((every $i in cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:streetAddressLine/@nullFlavor)">Error: R1.7.A.3.1 Patient Contact's Street is required. The element (streetAddressLine)
        shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.8.3.3 Patient Contact's City-->
        <assert test="@nullFlavor or ((every $i in cda:city satisfies string-length(normalize-space($i)) &gt; 0) or cda:city/@nullFlavor)">Error: R1.8.3.3 Patient Contact's City is required. The element (city) shall not be empty, otherwise the nullFlavor
        attribute shall be present.</assert>
        <!-- R1.8.3.4 Patient Contact's Postal Code-->
        <assert test="@nullFlavor or ((every $i in cda:postalCode satisfies string-length(normalize-space($i)) &gt; 0) or cda:postalCode/@nullFlavor)">Error: R1.7.A.3.2.4 Patient Contact's Postal Code is required. The element (postalCode) shall not be
        empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.8.3.5 Patient Contact's State or Province-->
        <assert test="@nullFlavor or ((every $i in cda:state satisfies string-length(normalize-space($i)) &gt; 0) or cda:state/@nullFlavor)">Error: R1.7.A.3.2.5 Patient Contact's State or Province is required. The element (state) shall not be empty,
        otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.8.3.6 Patient Contact's Country-->
        <assert test="@nullFlavor or ((every $i in cda:country satisfies string-length(normalize-space($i)) &gt; 0) or cda:country/@nullFlavor)">Error: R1.7.A.3.2.6 Patient Contact's Country is required. The element (country) shall not be empty,
        otherwise the nullFlavor attribute shall be present.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:participant[not(cda:functionCode/@code='PCP') or not(cda:functionCode/@codeSystem='2.16.840.1.113883.5.88')]/cda:associatedEntity/cda:telecom">
        <!-- R1.8.4 The Patient Contact's Telecommunication-->
        <assert test=" ((@nullFlavor='UNK') and not(@value) and not(@use)) or (not(@nullFlavor) and (@value and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0)))">Error: R1.8.4 The Patient Contact's telephone or e-mail element is
        required. If there is no information, the nullFlavor attribute shall have a value of 'UNK' and the "value" and "use" attributes shall be omitted, otherwise the nullFlavor attribute shall not be present, and the "value" and "use" attributes shall
        be present</assert>
        <!-- R1.8.4 The Patient Contact's Telecommunication-->
        <assert test="((@nullFlavor='UNK') and not(@value) and not(@use)) or (not(@nullFlavor) and (matches (@value,'tel:\+[0-9/(/)/-]'))) or (not(@nullFlavor) and (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$')))">
        Error: R1.8.4 The Patient Contact's telephone number(R1.8.4.1) or the Patient Contact's e-mail address (R1.8.4.2) are miswritten : the Patient Contact's e-mail address shall be like : example@exp.com and the Patient Contact's telephone number
        shall be like : "tel:+13176307960" or "tel:+1(317)630-7960"</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:participant[not(cda:functionCode/@code='PCP') or not(cda:functionCode/@codeSystem='2.16.840.1.113883.5.88')]/cda:assignedEntity/cda:code">
        <!-- R1.8.6 Contact Relationship Type-->
        <assert test="@codeSystem='2.16.840.1.113883.11.19563'">Error: R1.8.6 Contact Relationship Type codeSystem attribute shall refer to the HL7:PersonalRelationshipRoleType "2.16.840.1.113883.11.19563".</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:participant[cda:functionCode/@code='PCP' and cda:functionCode/@codeSystem='2.16.840.1.113883.5.88']/cda:associatedEntity[@classCode='CAREGIVER']">
        <!-- R1.9 Preferd HCP/ Legal Organization-->
        <assert test="(cda:associatedPerson or cda:scopingOrganization) and not(cda:associatedPerson and cda:scopingOrganization)">Error: R1.9 Preferd HCP/ Legal Organization "associatedPerson" or "scopingOrganization" element is required.</assert>
        <!-- R1.9.1 Name of the prefered Legal Organization/HCP-->
        <assert test="(cda:scopingOrganization and cda:scopingOrganization/cda:name and (every $i in cda:scopingOrganization/cda:name satisfies count(($i))&lt; 2) and ((every $i in cda:scopingOrganization/cda:name satisfies string-length(normalize-space($i)) &gt; 0) or cda:scopingOrganization/cda:name/@nullFlavor) ) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor) or (cda:associatedPerson and cda:associatedPerson/cda:name and (every $i in cda:associatedPerson/cda:name satisfies count(($i))&lt; 2)) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor)">
        Error: R1.9.1 Name of the prefered Legal Organization/HCP is required. The element "name" shall be present in the element "associatedPerson" or in the element "scopingOrganization". The element "name" cannot be repeated (cardinality
        [1..1]).</assert>
        <!-- R1.9.1.1 Family Name/Surname of the prefered HCP-->
        <assert test="(cda:associatedPerson and cda:associatedPerson/cda:name/cda:family and (every $i in cda:associatedPerson/cda:name/cda:family satisfies count(($i))&lt; 2) and ((every $i in cda:associatedPerson/cda:name/cda:family satisfies string-length(normalize-space($i)) &gt; 0) or cda:associatedPerson/cda:name/cda:family/@nullFlavor)) or (cda:associatedPerson and cda:associatedPerson/cda:name/@nullFlavor) or (cda:scopingOrganization) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor)">
        Error: R1.9.1.1 Family Name/Surname of the prefered HCP is required.The element (family) shall not be empty, otherwise the nullFlavor attribute shall be present. The element (family) cannot be repeated as well (cardinality [1..1]).</assert>
        <!-- R1.9.1.2 Given Name of the prefered HCP-->
        <assert test="(cda:associatedPerson and cda:associatedPerson/cda:name/cda:given and (every $i in cda:associatedPerson/cda:name/cda:given satisfies count(($i))&lt; 2) and ((every $i in cda:associatedPerson/cda:name/cda:given satisfies string-length(normalize-space($i)) &gt; 0) or cda:associatedPerson/cda:name/cda:given/@nullFlavor)) or (cda:associatedPerson and cda:associatedPerson/cda:name/@nullFlavor) or (cda:scopingOrganization) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor)">
        Error: R1.9.1.2 Given Name of the prefered HCP is required.The element (given) shall not be empty, otherwise the nullFlavor attribute shall be present. The element (given) cannot be repeated as well (cardinality [1..1]).</assert>
        <!-- R1.9.2 Prefered HCP Legal Organization Address-->
        <assert test="(cda:scopingOrganization and cda:scopingOrganization/cda:addr) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor) or (cda:associatedPerson and cda:addr) or (cda:associatedPerson and @nullFlavor)">Error: R1.9.2
        Prefered HCP Legal Organization Address is required. The element "addr" shall be present in the element "associatedEntity" or in the element "scopingOrganization".</assert>
        <!-- R1.9.2.1/R1.9.2.2 Prefered HCP/Legal Organization Street/Number of Street-->
        <assert test="(cda:associatedPerson and cda:addr/cda:streetAddressLine and ((every $i in cda:addr/cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:addr/cda:streetAddressLine/@nullFlavor)) or (cda:associatedPerson and cda:addr/@nullFlavor) or (cda:associatedPerson and not(cda:addr)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/cda:streetAddressLine and ((every $i in cda:scopingOrganization/cda:addr/cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:scopingOrganization/cda:addr/cda:streetAddressLine/@nullFlavor)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/@nullFlavor) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor) or (cda:scopingOrganization and not(cda:scopingOrganization/cda:addr))">
        Error: R1.9.2.1/R1.9.2.2 Prefered HCP/Legal Organization Street/Number of Street is required. The element (streetAddressLine) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.9.2.3 Prefered HCP/Legal Organization City-->
        <assert test="(cda:associatedPerson and cda:addr/cda:city and ((every $i in cda:addr/cda:city satisfies string-length(normalize-space($i)) &gt; 0) or cda:addr/cda:city/@nullFlavor)) or (cda:associatedPerson and cda:addr/@nullFlavor) or (cda:associatedPerson and not(cda:addr)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/cda:city and ((every $i in cda:scopingOrganization/cda:addr/cda:city satisfies string-length(normalize-space($i)) &gt; 0) or cda:scopingOrganization/cda:addr/cda:city/@nullFlavor)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/@nullFlavor) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor) or (cda:scopingOrganization and not(cda:scopingOrganization/cda:addr))">
        Error: R1.9.2.3 Prefered HCP/Legal Organization City is required. The element (city) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.9.2.4 Prefered HCP/Legal Organization Postal Code-->
        <assert test="(cda:associatedPerson and cda:addr/cda:postalCode and ((every $i in cda:addr/cda:postalCode satisfies string-length(normalize-space($i)) &gt; 0) or cda:addr/cda:postalCode/@nullFlavor)) or (cda:associatedPerson and cda:associatedPerson/cda:addr/@nullFlavor) or (cda:associatedPerson and not(cda:addr)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/cda:postalCode and ((every $i in cda:scopingOrganization/cda:addr/cda:postalCode satisfies string-length(normalize-space($i)) &gt; 0) or cda:scopingOrganization/cda:addr/cda:postalCode/@nullFlavor)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/@nullFlavor) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor) or (cda:scopingOrganization and not(cda:scopingOrganization/cda:addr))">
        Error: R1.9.2.4 Prefered HCP/Legal Organization Postal Code is required. This element (postalCode) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.9.2.5 Prefered HCP/Legal Organization State or Province-->
        <assert test="(cda:associatedPerson and cda:addr/cda:state and ((every $i in cda:addr/cda:state satisfies string-length(normalize-space($i)) &gt; 0) or cda:addr/cda:state/@nullFlavor)) or (cda:associatedPerson and cda:addr/@nullFlavor) or (cda:associatedPerson and not(cda:addr)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/cda:state and ((every $i in cda:scopingOrganization/cda:addr/cda:state satisfies string-length(normalize-space($i)) &gt; 0) or cda:scopingOrganization/cda:addr/cda:state/@nullFlavor)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/@nullFlavor) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor) or (cda:scopingOrganization and not(cda:scopingOrganization/cda:addr))">
        Error: R1.9.2.5 Prefered HCP/Legal Organization State or Province is required. The element (state) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.9.2.6 Prefered HCP/Legal Organization Country-->
        <assert test="(cda:associatedPerson and cda:addr/cda:country and ((every $i in cda:addr/cda:country satisfies string-length(normalize-space($i)) &gt; 0) or cda:addr/cda:country/@nullFlavor)) or (cda:associatedPerson and cda:addr/@nullFlavor) or (cda:associatedPerson and not(cda:addr)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/cda:country and ((every $i in cda:scopingOrganization/cda:addr/cda:country satisfies string-length(normalize-space($i)) &gt; 0) or cda:scopingOrganization/cda:addr/cda:country/@nullFlavor)) or (cda:scopingOrganization and cda:scopingOrganization/cda:addr/@nullFlavor) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor) or (cda:scopingOrganization and not(cda:scopingOrganization/cda:addr))">
        Error: R1.9.2.6 Prefered HCP/Legal Organization Country is required. The element (country) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.9.3 Prefered HCP Legal Organization Telecom-->
        <assert test="(cda:scopingOrganization and cda:scopingOrganization/cda:telecom) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor) or (cda:associatedPerson and cda:telecom) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor)">
        Error: R1.9.3 Prefered HCP/Legal Organization Telecom is required. The element "telecom" shall be present in the element "associatedEntity" or in the element "scopingOrganization".</assert>
        <assert test="(cda:associatedPerson) or (cda:associatedPerson and cda:associatedPerson/@nullFlavor) or (cda:associatedPerson and not(cda:telecom)) or (cda:scopingOrganization) or (cda:scopingOrganization and cda:scopingOrganization/@nullFlavor) or (cda:scopingOrganization and not(cda:scopingOrganization/cda:telecom))">
        Error: R1.9.3 The Prefered HCP/Legal Organization telephone or e-mail "telecom" element is required. If there is no information, the nullFlavor attribute shall be present and the "value" and "use" attributes shall be omitted, otherwise the
        nullFlavor attribute shall not be present, and the "value" and "use" attributes shall be present</assert>
      </rule>
      <!-- R1.9.3 Prefered HCP Legal Organization Telecom-->
      <rule context="/cda:ClinicalDocument/cda:participant[cda:functionCode/@code='PCP' and cda:functionCode/@codeSystem='2.16.840.1.113883.5.88']/cda:associatedEntity[@classCode='CAREGIVER']/cda:telecom">
        <assert test=" (matches(@value,'tel:\+[0-9/(/)/-]') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0) ) or (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0)) or @nullFlavor = 'UNK'">
        Error: R1.9.3 HCP Telecom telephone/mail address is required. The attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like : example@exp.com and the Patient Contact's telephone
        number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960. The attribute 'use' shall not be empty.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:participant[cda:functionCode/@code='PCP' and cda:functionCode/@codeSystem='2.16.840.1.113883.5.88']/cda:associatedEntity[@classCode='CAREGIVER']/cda:scopingOrganization/cda:telecom">
        <assert test=" (matches(@value,'tel:\+[0-9/(/)/-]') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0) ) or (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0)) or @nullFlavor = 'UNK'">
        Error: R1.9.3 HCP Telecom telephone/mail address is required. The attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like : example@exp.com and the Patient Contact's telephone
        number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960. The attribute 'use' shall not be empty.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.3-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Healthcare Providers and Pharmacies - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.2.3&quot;]">
        <assert test="ancestor::cda:documentationOf/cda:serviceEvent[@classCode = &quot;PCPR&quot;]">Error: In IHE PCC Healthcare Providers and Pharmacies (1.3.6.1.4.1.19376.1.5.3.1.2.3), the documentationOf element shall be present. The serviceEvent
        element shall be present, and shall have a classCode attribute of 'PCPR'.</assert>
        <assert test="preceding-sibling::cda:effectiveTime/cda:low and preceding-sibling::cda:effectiveTime/cda:high">Error: In IHE PCC Healthcare Providers and Pharmacies (1.3.6.1.4.1.19376.1.5.3.1.2.3), in effectiveTime there shall be a low element
        which records the starting date of care provision, and a high element which records the ending date of care provision.</assert>
        <assert test="cda:time/cda:low and cda:time/cda:high">Error: In IHE PCC Healthcare Providers and Pharmacies (1.3.6.1.4.1.19376.1.5.3.1.2.3), the time element is used to show the time period over which the provider gave care to the patient. The
        low and high elements must be present, and indicate the time over which care was (or is to be) provided.</assert>
        <assert test="cda:assignedEntity">Error: In IHE PCC Healthcare Providers and Pharmacies (1.3.6.1.4.1.19376.1.5.3.1.2.3), the assignedEntity element contains elements that identify the individual provider, and shall be present.</assert>
        <assert test="cda:assignedEntity/cda:assignedPerson/cda:name or cda:assignedEntity/cda:representedOrganization">Error: In IHE PCC Healthcare Providers and Pharmacies (1.3.6.1.4.1.19376.1.5.3.1.2.3), the providers name shall be present. If not
        present, then the representedOrganization shall be present.</assert>
      </rule>
      <!--epSOS-->
      <!--(cf.epSOS_Implementation_v0-1.0.1.20100623.doc : 8  Common Header Data Elements)-->
      <rule context="/cda:ClinicalDocument">
        <!-- R1.10 HCP Identification ("ClinicalDocument\documentationOf\serviceEvent\performer" is optional, see the following rule)-->
        <assert test="cda:author/cda:assignedAuthor">Error: R1.10 HCP Information are required. The element "ClinicalDocument\author\assignedAuthor" shall be present.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:author/cda:assignedAuthor">
        <!-- R1.10.1 HCP Family Name/Surname -->
        <assert test="(cda:assignedPerson/cda:name/cda:family and (every $i in cda:assignedPerson/cda:name/cda:family satisfies string-length(normalize-space($i)) &gt; 0)) or (cda:assignedPerson/cda:name/@nullFlavor) or (cda:assignedPerson/@nullFlavor) or (not(cda:assignedPerson ))">
        Error: R1.10.1 HCP Family Name/Surname is required. The element (family) shall not be empty. The element (family) shall be present in "ClinicalDocument\author\assignedAuthor\assignedPerson\name\".</assert>
        <report test="cda:assignedPerson/cda:name/cda:family">Success: R1.10.1 HCP Family Name/Surname is present.</report>
        <!-- R1.10.2 HCP Given Name -->
        <assert test="(cda:assignedPerson/cda:name/cda:given and (every $i in cda:assignedPerson/cda:name/cda:given satisfies string-length(normalize-space($i)) &gt; 0)) or (cda:assignedPerson/cda:name/@nullFlavor) or (cda:assignedPerson/@nullFlavor) or (not(cda:assignedPerson))">
        Error: R1.10.2 HCP Given Name is required. The element (given) shall not be empty. The element (given) shall be present in "ClinicalDocument\author\assignedAuthor\assignedPerson\name\".</assert>
        <report test="cda:assignedPerson/cda:name/cda:given">Success: R1.10.2 HCP Given Name is present.</report>
        <!-- R1.10.5 HCP ID number -->
        <assert test="(cda:id) or (@nullFlavor) or (not(cda:assignedPerson))">Error: R1.10.5 HCP ID number is required. The element (id) shall be present in "ClinicalDocument\author\assignedAuthor\" .</assert>
        <report test="cda:id">Success: R1.10.5 HCP ID number is present.</report>
        <!-- R1.10.7 Specialty (Health Care Professional's Specialty) -->
        <assert test="(cda:code and cda:code/@code and cda:code/@codeSystem ) or (@nullFlavor) or (not(cda:code ))">Error: R1.10.7 HCP Specialty is optional. If the element (code) is present, the attributes (code\@code) and (code\@codeSystem) shall not
        be empty.</assert>
        <report test="cda:code">Success: R1.10.7 HCP Specialty is present.</report>
        <!-- R1.10.8 HCP Telecom  -->
        <assert test="(cda:telecom) or (@nullFlavor) or (not(cda:telecom))">Error: R1.10.8 HCP Telecom is optional. If the element (telecom) is present, the attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's
        e-mail address shall be like : example@exp.com and the Patient Contact's telephone number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960.</assert>
        <report test="cda:telecom">Success: R1.10.8 HCP Telecom is present.</report>
        <!-- R1.10.9.3.5 Healthcare Facility's Country -->
        <assert test="(cda:representedOrganization/cda:addr/cda:country and ((every $i in cda:representedOrganization/cda:addr/cda:country satisfies string-length(normalize-space($i)) &gt; 0)) and (every $i in cda:representedOrganization/cda:addr/cda:country satisfies count(($i))&lt; 2)) or (@nullFlavor) or (cda:representedOrganization/@nullFlavor) or (cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:representedOrganization))">
        Error: R1.10.9.3.5 Healthcare Facility's Country is required. The element (country) shall not be empty and cannot be repeated (cardinality [1..1]).The element (country) shall be present in
        "ClinicalDocument\author\assignedAuthor\representedOrganization\addr".</assert>
        <report test="cda:representedOrganization/cda:addr/cda:country">Success: R1.10.9.3.5 Healthcare Facility's Country is present.</report>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:documentationOf/cda:serviceEvent/cda:performer">
        <!-- R1.10.1 HCP Family Name/Surname -->
        <assert test="(cda:assignedEntity/cda:assignedPerson/cda:name/cda:family and (every $i in cda:assignedEntity/cda:assignedPerson/cda:name/cda:family satisfies string-length(normalize-space($i)) &gt; 0)) or (cda:assignedEntity/cda:assignedPerson/cda:name/@nullFlavor) or (cda:assignedEntity/cda:assignedPerson/@nullFlavor) or (cda:assignedEntity/@nullFlavor) or (not(cda:assignedEntity/cda:assignedPerson))">
        Error: R1.10.1 HCP Family Name/Surname is required. The element (family) shall not be empty. The element (family) shall be present in "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\assignedPerson\name\".</assert>
        <report test="cda:assignedEntity/cda:assignedPerson/cda:name/cda:family">Success: R1.10.1 HCP Family Name/Surname is present.</report>
        <!-- R1.10.2 HCP Given Name -->
        <assert test="(cda:assignedEntity/cda:assignedPerson/cda:name/cda:given and (every $i in cda:assignedEntity/cda:assignedPerson/cda:name/cda:given satisfies string-length(normalize-space($i)) &gt; 0)) or (cda:assignedEntity/cda:assignedPerson/cda:name/@nullFlavor) or (cda:assignedEntity/cda:assignedPerson/@nullFlavor) or (cda:assignedEntity/@nullFlavor) or (not(cda:assignedEntity/cda:assignedPerson))">
        Error: R1.10.2 HCP Given Name is required. The element (given) shall not be empty. The element (given) shall be present in "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\assignedPerson\name\".</assert>
        <report test="cda:assignedEntity/cda:assignedPerson/cda:name/cda:given">Success: R1.10.2 HCP Given Name is present.</report>
        <!-- R1.10.5 HCP ID number -->
        <assert test="(cda:assignedEntity/cda:id ) or (cda:assignedEntity/@nullFlavor) or (not(cda:assignedEntity/cda:assignedPerson))">Error: R1.10.5 HCP ID number is required. The element (id) shall be present in
        "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\".</assert>
        <report test="cda:assignedEntity/cda:id">Success: R1.10.5 HCP ID number is present.</report>
        <!-- R1.10.7 Specialty (Health Care Professional's Specialty) -->
        <assert test="(cda:assignedEntity/cda:code and cda:assignedEntity/cda:code/@code and cda:assignedEntity/cda:code/@codeSystem) or (cda:assignedEntity/@nullFlavor) or (not(cda:assignedEntity/cda:code))">Error: R1.10.7 HCP Specialty is optional. If
        the element (code) is present, the attributes (code\@code) and (code\@codeSystem) shall not be empty.</assert>
        <report test="cda:assignedEntity/cda:code">Success: R1.10.7 HCP Specialty is present.</report>
        <!-- R1.10.8 HCP Telecom  -->
        <assert test="(cda:assignedEntity/cda:telecom) or (cda:assignedEntity/@nullFlavor) or (not(cda:assignedEntity/cda:telecom))">Error: R1.10.8 HCP Telecom is optional. If the element (telecom) is present, the attributes (telecom\@value) and
        (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like : example@exp.com and the Patient Contact's telephone number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960.</assert>
        <report test="cda:assignedEntity/cda:telecom">Success: R1.10.8 HCP Telecom is present.</report>
        <!-- R1.10.9.3.5 Healthcare Facility's Country -->
        <assert test="(cda:assignedEntity/cda:representedOrganization/cda:addr/cda:country and ((every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:country satisfies string-length(normalize-space($i)) &gt; 0)) and (every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:country satisfies count(($i))&lt; 2)) or (cda:assignedEntity/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:assignedEntity/cda:representedOrganization))">
        Error: R1.10.9.3.5 Healthcare Facility's Country is required. The element (country) shall not be empty and cannot be repeated (cardinality [1..1]).The element (country) shall be present in
        "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\representedOrganization\addr".</assert>
        <report test="cda:assignedEntity/cda:representedOrganization/cda:addr/cda:country">Success: R1.10.9.3.5 Healthcare Facility's Country is present.</report>
      </rule>
      <!-- R1.10.8 HCP Telecom  -->
      <rule context="/cda:ClinicalDocument/cda:author/cda:assignedAuthor/cda:telecom">
        <assert test=" (matches(@value,'tel:\+[0-9/(/)/-]') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0) ) or (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0))">
        Error: R1.10.8 HCP Telecom telephone/mail address is required. The attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like : example@exp.com and the Patient Contact's telephone
        number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960. The attribute 'use' shall not be empty.</assert>
      </rule>
      <!-- R1.10.8 HCP Telecom  -->
      <rule context="/cda:ClinicalDocument/cda:documentationOf/cda:serviceEvent/cda:performer/cda:assignedEntity/cda:telecom">
        <assert test=" (matches(@value,'tel:\+[0-9/(/)/-]') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0) ) or (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0))">
        Error: R1.10.8 HCP Telecom telephone/mail address is required. The attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like : example@exp.com and the Patient Contact's telephone
        number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960. The attribute 'use' shall not be empty.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.1.PS-errors" xml:base="templates/PStemplates/errors/1.3.6.1.4.1.19376.1.5.3.1.1.1.PS.sch">
      <title>IHE PCC Medical Documents Specification - errors validation phase</title>
      <!--epSOS-->
      <!--(cf.epSOS_Implementation_v0-1.0.1.20100623.doc : 8  Common Header Data Elements)-->
      <!-- R1.11.2 Date of last update-->
      <rule context="/cda:ClinicalDocument/cda:documentationOf/cda:serviceEvent/cda:effectiveTime">
        <assert test="(cda:high and cda:high/@value and count(cda:high)&lt;2) or @nullFlavor">Error:R1.11.2 Date of last update is required. The attribute 'value' of the element "high" shall not be empty. The element "high" cannot be repeated
        (cardinality [1..1]).</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:legalAuthenticator/cda:assignedEntity/cda:representedOrganization/cda:addr">
        <!-- R1.11.10 Legal Authenticator Facility Address-->
        <assert test="(((cda:streetAddressLine) or (cda:city) or (cda:state) or (cda:country)) and not(@nullFlavor)) or ( (@nullFlavor) and (not(cda:streetAddressLine) and not(cda:city) and not(cda:state) and not(cda:country)))">Error: R1.11.10 Legal
        Authenticator Facility Address element is required. If there is no information, the nullFlavor attribute shall be present and no address parts shall be present, otherwise there shall be no nullFlavor attribute, and at least one of the address
        parts listed below shall be present</assert>
        <!-- R1.11.10 Legal Authenticator Facility Street/Number of Street-->
        <assert test="(cda:streetAddressLine and count(cda:streetAddressLine)&lt;2 and ((every $i in cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:streetAddressLine/@nullFlavor)) or @nullFlavor">Error: R1.11.10 Legal
        Authenticator Facility Street/Number of Street is required (cf.R1.10.9.3.1). The element (streetAddressLine) shall not be empty, otherwise the nullFlavor attribute shall be present .</assert>
        <!-- R1.11.10 Legal Authenticator Facility City-->
        <assert test="(cda:city and count(cda:city)&lt;2 and ((every $i in cda:city satisfies string-length(normalize-space($i)) &gt; 0) or cda:city/@nullFlavor)) or @nullFlavor">Error: R1.11.10 Legal Authenticator Facility City is required
        (cf.R1.10.9.3.2). The element (city) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.11.10 Legal Authenticator Facility Postal Code-->
        <assert test="(cda:postalCode and count(cda:postalCode)&lt;2 and ((every $i in cda:postalCode satisfies string-length(normalize-space($i)) &gt; 0) or cda:postalCode/@nullFlavor)) or @nullFlavor">Error: R1.11.10 Legal Authenticator Facility Postal
        Code is required (cf.R1.10.9.3.3). The element (postalCode) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.11.10 Legal Authenticator Facility State or Province-->
        <assert test="(cda:state and count(cda:state)&lt;2 and ((every $i in cda:state satisfies string-length(normalize-space($i)) &gt; 0) or cda:state/@nullFlavor)) or @nullFlavor">Error: R1.11.10 Legal Authenticator Facility State or Province is
        required (cf.R1.10.9.3.4). The element (state) shall not be empty, otherwise the nullFlavor attribute shall be present.</assert>
        <!-- R1.11.10 Legal Authenticator Facility Country-->
        <assert test="(cda:country and count(cda:country)&lt;2 and (every $i in cda:country satisfies string-length(normalize-space($i)) &gt; 0) ) or @nullFlavor">Error: R1.11.10 Legal Authenticator Facility Country is required (cf.R1.10.9.3.5). The
        element (country) shall not be empty.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:legalAuthenticator/cda:assignedEntity/cda:representedOrganization/cda:telecom">
        <!-- R1.11.10 Legal Authenticator Facility Telecommunication-->
        <assert test=" (matches(@value,'tel:\+[0-9/(/)/-]') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0) ) or (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0)) or @nullFlavor">
        Error: R1.11.10 Legal Authenticator Facility telephone number(cf.R1.10.9.4.1) or the Legal Authenticator Facility e-mail address (cf.R1.10.9.4.2) is required. The e-mail address shall be like : example@exp.com and the telephone number shall be
        like : "tel:+13176307960" or "tel:+1(317)630-7960". The attribute 'use' shall not be empty.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.3.PS-errors" xml:base="templates/PStemplates/errors/1.3.6.1.4.1.19376.1.5.3.1.2.3.PS.sch">
      <!--epSOS-->
      <!--(cf.epSOS_Implementation_v0-1.0.1.20100623.doc : 8  Common Header Data Elements)-->
      <rule context="/cda:ClinicalDocument"></rule>
      <rule context="/cda:ClinicalDocument/cda:author/cda:assignedAuthor">
        <!-- R1.10.6 HCP Profession (Health Care Professional's Specialty) -->
        <assert test="(parent::node()/cda:functionCode/@code and parent::node()/cda:functionCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.1']) or (@nullFlavor) or (parent::node() and not (parent::node()/cda:functionCode))">Error: R1.10.6 HCP
        Profession is Optional. If the element (functionCode) is present in "ClinicalDocument\author\". The attributes (functionCode\@code) and (functionCode\@codeSystem) shall not be empty and the @codeSystem shall be
        "1.3.6.1.4.1.12559.11.10.1.3.1.42.1".</assert>
        <report test="parent::node()/cda:functionCode/@code">Success: R1.10.6 HCP Profession is present.</report>
        <!-- R1.10.9.1 Healthcare Facility's name -->
        <assert test="(cda:representedOrganization/cda:name and ((every $i in cda:representedOrganization/cda:name satisfies string-length(normalize-space($i)) &gt; 0) or cda:representedOrganization/cda:name/@nullFlavor) and (every $i in cda:representedOrganization/cda:name satisfies count(($i))&lt; 2)) or (cda:representedOrganization/@nullFlavor) or (@nullFlavor) or (not(cda:representedOrganization))">
        Error: R1.10.9.1 Healthcare Facility's name is required. The element (name) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (name) shall be present in
        "ClinicalDocument\author\assignedAuthor\representedOrganization".</assert>
        <report test="cda:representedOrganization/cda:name">Success: R1.10.9.1 Healthcare Facility's name is present.</report>
        <!-- R1.10.9.2 Healthcare Facility's identifier -->
        <assert test="(cda:representedOrganization/cda:id and (every $i in cda:representedOrganization/cda:id satisfies count(($i))&lt; 2)) or (cda:representedOrganization/@nullFlavor) or (@nullFlavor) or (not(cda:representedOrganization))">Error:
        R1.10.9.2 Healthcare Facility's identifier is required. The element (id) cannot be repeated (cardinality [1..1]). The element (id) shall be present in "ClinicalDocument\author\assignedAuthor\representedOrganization".</assert>
        <report test="cda:representedOrganization/cda:id">Success: R1.10.9.2 Healthcare Facility's identifier is present.</report>
        <!-- R1.10.9.3.1 Healthcare Facility's Street -->
        <assert test="(cda:representedOrganization/cda:addr/cda:streetAddressLine and ((every $i in cda:representedOrganization/cda:addr/cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:representedOrganization/cda:addr/cda:streetAddressLine/@nullFlavor) and (every $i in cda:representedOrganization/cda:addr/cda:streetAddressLine satisfies count(($i))&lt; 2)) or (@nullFlavor) or (cda:representedOrganization/@nullFlavor) or (cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:representedOrganization))">
        Error: R1.10.9.3.1 Healthcare Facility's Street is required. The element (streetAddressLine) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (streetAddressLine)
        shall be present in "ClinicalDocument\author\assignedAuthor\representedOrganization\addr".</assert>
        <report test="cda:representedOrganization/cda:addr/cda:streetAddressLine">Success: R1.10.9.3.1 Healthcare Facility's Street is present.</report>
        <!-- R1.10.9.3.2 Healthcare Facility's City -->
        <assert test="(cda:representedOrganization/cda:addr/cda:city and ((every $i in cda:representedOrganization/cda:addr/cda:city satisfies string-length(normalize-space($i)) &gt; 0) or cda:representedOrganization/cda:addr/cda:city/@nullFlavor) and (every $i in cda:representedOrganization/cda:addr/cda:city satisfies count(($i))&lt; 2)) or (@nullFlavor) or (cda:representedOrganization/@nullFlavor) or (cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:representedOrganization))">
        Error: R1.10.9.3.2 Healthcare Facility's City is required. The element (city) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (city) shall be present in
        "ClinicalDocument\author\assignedAuthor\representedOrganization\addr".</assert>
        <report test="cda:representedOrganization/cda:addr/cda:city">Success: R1.10.9.3.2 Healthcare Facility's City is present.</report>
        <!-- R1.10.9.3.3 Healthcare Facility's State or Province -->
        <assert test="(cda:representedOrganization/cda:addr/cda:state and ((every $i in cda:representedOrganization/cda:addr/cda:state satisfies string-length(normalize-space($i)) &gt; 0) or cda:representedOrganization/cda:addr/cda:state/@nullFlavor) and (every $i in cda:representedOrganization/cda:addr/cda:state satisfies count(($i))&lt; 2)) or (@nullFlavor) or (cda:representedOrganization/@nullFlavor) or (cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:representedOrganization))">
        Error: R1.10.9.3.3 Healthcare Facility's State or Province is required. The element (state) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (state) shall be present
        in "ClinicalDocument\author\assignedAuthor\representedOrganization\addr".</assert>
        <report test="cda:representedOrganization/cda:addr/cda:state">Success: R1.10.9.3.3 Healthcare Facility's State or Province is present.</report>
        <!-- R1.10.9.3.4 Healthcare Facility's Zip or Postal Code -->
        <assert test="(cda:representedOrganization/cda:addr/cda:postalCode and ((every $i in cda:representedOrganization/cda:addr/cda:postalCode satisfies string-length(normalize-space($i)) &gt; 0) or cda:representedOrganization/cda:addr/cda:postalCode/@nullFlavor) and (every $i in cda:representedOrganization/cda:addr/cda:postalCode satisfies count(($i))&lt; 2)) or (@nullFlavor) or (cda:representedOrganization/@nullFlavor) or (cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:representedOrganization))">
        Error: R1.10.9.3.4 Healthcare Facility's Zip or Postal Code is required. The element (postalCode) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (postalCode) shall
        be present in "ClinicalDocument\author\assignedAuthor\representedOrganization\addr".</assert>
        <report test="cda:representedOrganization/cda:addr/cda:postalCode">Success: R1.10.9.3.4 Healthcare Facility's Zip or Postal Code is present.</report>
        <!-- R1.10.9.4.1 HCP Telecom telephone/mail address -->
        <assert test="(cda:representedOrganization/cda:telecom) or (@nullFlavor) or (cda:representedOrganization/@nullFlavor) or (not(cda:representedOrganization/cda:telecom))">Error: R1.10.9.4.1/R1.10.9.4.2 HCP Telecom telephone/mail address is
        optional. If the element "telecom" exists, the attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like : example@exp.com and the Patient Contact's telephone number shall be like
        : "tel:+13176307960" or "tel:+1(317)630-7960.</assert>
        <report test="cda:representedOrganization/cda:telecom">Success: R1.10.9.4.1/R1.10.9.4.2 HCP Telecom telephone/mail address is present.</report>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:documentationOf/cda:serviceEvent/cda:performer">
        <!-- R1.10.6 HCP Profession (Health Care Professional's Specialty) -->
        <assert test="(cda:functionCode/@code and cda:functionCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.1']) or @nullFlavor or (not(cda:functionCode))">Error: R1.10.6 HCP Profession is Optional. If the element (functionCode) is present in
        "\ClinicalDocument\documentationOf\serviceEvent\performer\". The attributes (functionCode\@code) and (functionCode\@codeSystem) shall not be empty and the @codeSystem shall be "1.3.6.1.4.1.12559.11.10.1.3.1.42.1".</assert>
        <report test="cda:functionCode/@code">Success: R1.10.6 HCP Profession is present.</report>
        <!-- R1.10.9.1 Healthcare Facility's name -->
        <assert test="(cda:assignedEntity/cda:representedOrganization/cda:name and ((every $i in cda:assignedEntity/cda:representedOrganization/cda:name satisfies string-length(normalize-space($i)) &gt; 0) or cda:assignedEntity/cda:representedOrganization/cda:name/@nullFlavor) and (every $i in cda:assignedEntity/cda:representedOrganization/cda:name satisfies count(($i))&lt; 2)) or (cda:assignedEntity/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/@nullFlavor) or (not(cda:assignedEntity/cda:representedOrganization))">
        Error: R1.10.9.1 Healthcare Facility's name is required. The element (name) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (name) shall be present in
        "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\representedOrganization".</assert>
        <report test="cda:assignedEntity/cda:representedOrganization/cda:name">Success: R1.10.9.1 Healthcare Facility's name is present.</report>
        <!-- R1.10.9.2 Healthcare Facility's identifier -->
        <assert test="(cda:assignedEntity/cda:representedOrganization/cda:id and (every $i in cda:assignedEntity/cda:representedOrganization/cda:id satisfies count(($i))&lt; 2)) or (cda:assignedEntity/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/@nullFlavor) or (not(cda:assignedEntity/cda:representedOrganization))">
        Error: R1.10.9.2 Healthcare Facility's identifier is required. The element (id) cannot be repeated (cardinality [1..1]). The element (id) shall be present in
        "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\representedOrganization".</assert>
        <report test="cda:assignedEntity/cda:representedOrganization/cda:id">Success: R1.10.9.2 Healthcare Facility's identifier is present.</report>
        <!-- R1.10.9.3.1 Healthcare Facility's Street -->
        <assert test="(cda:assignedEntity/cda:representedOrganization/cda:addr/cda:streetAddressLine and ((every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:assignedEntity/cda:representedOrganization/cda:addr/cda:streetAddressLine/@nullFlavor ) and (every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:streetAddressLine satisfies count(($i))&lt; 2)) or (cda:assignedEntity/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:assignedEntity/cda:representedOrganization))">
        Error: R1.10.9.3.1 Healthcare Facility's Street is required. The element (streetAddressLine) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (streetAddressLine)
        shall be present in "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\representedOrganization\addr".</assert>
        <report test="cda:assignedEntity/cda:representedOrganization/cda:addr/cda:streetAddressLine">Success: R1.10.9.3.1 Healthcare Facility's Street is present.</report>
        <!-- R1.10.9.3.2 Healthcare Facility's City -->
        <assert test="(cda:assignedEntity/cda:representedOrganization/cda:addr/cda:city and ((every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:city satisfies string-length(normalize-space($i)) &gt; 0) or cda:assignedEntity/cda:representedOrganization/cda:addr/cda:city/@nullFlavor ) and (every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:city satisfies count(($i))&lt; 2)) or (cda:assignedEntity/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:assignedEntity/cda:representedOrganization))">
        Error: R1.10.9.3.2 Healthcare Facility's City is required. The element (city) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (city) shall be present in
        "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\representedOrganization\addr".</assert>
        <report test="cda:assignedEntity/cda:representedOrganization/cda:addr/cda:city">Success: R1.10.9.3.2 Healthcare Facility's City is present.</report>
        <!-- R1.10.9.3.3 Healthcare Facility's State or Province -->
        <assert test="(cda:assignedEntity/cda:representedOrganization/cda:addr/cda:state and ((every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:state satisfies string-length(normalize-space($i)) &gt; 0) or cda:assignedEntity/cda:representedOrganization/cda:addr/cda:state/@nullFlavor ) and (every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:state satisfies count(($i))&lt; 2)) or (cda:assignedEntity/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:assignedEntity/cda:representedOrganization))">
        Error: R1.10.9.3.3 Healthcare Facility's State or Province is required. The element (state) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (state) shall be present
        in "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\representedOrganization\addr".</assert>
        <report test="cda:assignedEntity/cda:representedOrganization/cda:addr/cda:state">Success: R1.10.9.3.2 Healthcare Facility's State or Province is present.</report>
        <!-- R1.10.9.3.4 Healthcare Facility's Zip or Postal Code -->
        <assert test="(cda:assignedEntity/cda:representedOrganization/cda:addr/cda:postalCode and ((every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:postalCode satisfies string-length(normalize-space($i)) &gt; 0) or cda:assignedEntity/cda:representedOrganization/cda:addr/cda:postalCode/@nullFlavor) and (every $i in cda:assignedEntity/cda:representedOrganization/cda:addr/cda:postalCode satisfies count(($i))&lt; 2)) or (cda:assignedEntity/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/cda:addr/@nullFlavor) or (not(cda:assignedEntity/cda:representedOrganization))">
        Error: R1.10.9.3.4 Healthcare Facility's Zip or Postal Code is required. The element (postalCode) shall not be empty otherwise the nullFlavor attribute shall be present, and cannot be repeated (cardinality [1..1]). The element (postalCode) shall
        be present in "\ClinicalDocument\documentationOf\serviceEvent\performer\assignedEntity\representedOrganization\addr".</assert>
        <report test="cda:assignedEntity/cda:representedOrganization/cda:addr/cda:postalCode">Success: R1.10.9.3.2 Healthcare Facility's postalCode is present.</report>
        <!-- R1.10.9.4.1 HCP Telecom telephone/mail address -->
        <assert test="(cda:assignedEntity/cda:representedOrganization/cda:telecom) or (cda:assignedEntity/@nullFlavor) or (cda:assignedEntity/cda:representedOrganization/@nullFlavor) or (not(cda:assignedEntity/cda:representedOrganization/cda:telecom))">
        Error: R1.10.9.4.1/R1.10.9.4.2 HCP Telecom telephone/mail address is optional. If the element "telecom" exists, the attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like :
        example@exp.com and the Patient Contact's telephone number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960.</assert>
        <report test="cda:assignedEntity/cda:representedOrganization/cda:telecom">Success: R1.10.9.4.1/R1.10.9.4.2 HCP Telecom telephone/mail address is present.</report>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:author/cda:assignedAuthor/cda:representedOrganization/cda:telecom">
        <!-- R1.10.9.4.1 HCP Telecom telephone/mail address -->
        <assert test=" (matches(@value,'tel:\+[0-9/(/)/-]') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0) ) or (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0))">
        Error: R1.10.9.4.1/R1.10.9.4.2 HCP Telecom telephone/mail address is required. The attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like : example@exp.com and the Patient
        Contact's telephone number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960. The attribute 'use' shall not be empty.</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:documentationOf/cda:serviceEvent/cda:performer/cda:assignedEntity/cda:representedOrganization/cda:telecom">
        <!-- R1.10.9.4.1 HCP Telecom telephone/mail address -->
        <assert test=" (matches(@value,'tel:\+[0-9/(/)/-]') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0) ) or (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0))">
        Error: R1.10.9.4.1/R1.10.9.4.2 HCP Telecom telephone/mail address is required. The attributes (telecom\@value) and (telecom\@use) shall not be empty and so the Patient Contact's e-mail address shall be like : example@exp.com and the Patient
        Contact's telephone number shall be like : "tel:+13176307960" or "tel:+1(317)630-7960. The attribute 'use' shall not be empty.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.2.3-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <!--  <title>epSOS Medication Summary Section Specification - errors validation phase</title> -->
      <rule context="//cda:section[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.3']">
        <!-- Verify the document type code -->
        <assert test="cda:code[@code = '10160-0']">Error: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The Medication Summary Section type code of a Medical Summary Document must be set to the value "10160-0" which is the code
        corresponding to "History of medication use" in the loinc code system.</assert>
        <report test="cda:code[@code = '10160-0']">Success: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The Medication Summary Section type code of a Medical Summary Document is set to the value "10160-0" which is the code
        corresponding to "History of medication use" in the loinc code system.</report>
        <!-- Verify that the correct code system is used-->
        <assert test="cda:code[@codeSystem = '2.16.840.1.113883.6.1']">Error: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The document type code of a Medical Documents must be "2.16.840.1.113883.6.1" corresponding to the LOINC Code
        system OID</assert>
        <report test="cda:code[@codeSystem = '2.16.840.1.113883.6.1']">Success: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The document type code of a Medical Documents is "2.16.840.1.113883.6.1" corresponding to the LOINC Code system
        OID</report>
        <!-- Verify that the parents templateId are present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.8&quot;]">Error: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (2.16.840.1.113883.10.20.1.8) for Medication Summary Section shall
        be present.</assert>
        <report test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.8&quot;]">Success: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (2.16.840.1.113883.10.20.1.8) for Medication Summary Section is
        present.</report>
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.19&quot;]">Error: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.19376.1.5.3.1.3.19) for Medication Summary Section
        shall be present.</assert>
        <report test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.19&quot;]">Success: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.19376.1.5.3.1.3.19) for Medication Summary Section
        is present.</report>
        <!-- Verify that the Medication Item Entry Content Module templateId is present. -->
        <assert test="cda:entry/cda:substanceAdministration/cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.3.4&quot;]">Error: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The Medication Item Entry Content Module identifier
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) for Medication Summary Section shall be present.</assert>
        <report test="cda:entry/cda:substanceAdministration/cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.3.4&quot;]">Success: (cf.&#194;&#167;13.1.1 epSOS_Implementation_v0.2.20100702.doc) The Medication Item Entry Content Module identifier
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) for Medication Summary Section is present.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.19-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Medications Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.19&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Medications Section (1.3.6.1.4.1.19376.1.5.3.1.3.19), the Medications can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.8&quot;]">Error: In IHE PCC Medications Section (1.3.6.1.4.1.19376.1.5.3.1.3.19), the parent template identifier for Medications is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;10160-0&quot;]">Error: In IHE PCC Medications Section (1.3.6.1.4.1.19376.1.5.3.1.3.19), the section type code of a Medications must be 10160-0</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Medications Section (1.3.6.1.4.1.19376.1.5.3.1.3.19), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.7&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC Medications Section (1.3.6.1.4.1.19376.1.5.3.1.3.19), a Medications Section must contain Medications.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Medications - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7&quot;]">
        <assert test="cda:templateId[@root = &quot;2.16.840.1.113883.10.20.1.24&quot;]">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), all Medications entries use the templateId element specified (2.16.840.1.113883.10.20.1.24) to indicate
        that they are medication acts. This element is required.</assert>
        <assert test="count(cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.1&quot;] | cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.8&quot;] | cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.9&quot;] | cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.10&quot;] | cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.11&quot;]) = 1">
        Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), the &lt;templateId&gt; element identifies the Medications &lt;entry&gt; as a particular type of medication event, allowing for validation of the content. As a side effect, readers of
        the CDA can quickly locate and identify medication events. The templateId must use one of the values in the table in the specification.</assert>
        <assert test="not(cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.1&quot;]) or count(.//cda:substanceAdministration) = 0">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), template ID 1.3.6.1.4.1.19376.1.5.3.1.4.7.1
        indicates a "normal" &lt;substanceAdministration&gt; act that may not contain any subordinate &lt;substanceAdministration&gt; acts.</assert>
        <assert test="count(cda:id) = 1">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), a top level &lt;substanceAdministration&gt; element must be uniquely identified in Medications. If there is no explicit identifier for the observation
        in the source EMR system, a GUID may be used for the root attribute, and the extension may be omitted. Although HL7 allows for multiple identifiers, the profile requires that one and only one be used.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), the status of all Medications &lt;substanceAdministration&gt; elements must be "completed". The act has either occurred,
        or the request or order has been placed.</assert>
        <assert test="cda:consumable/cda:manufacturedProduct/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), the &lt;consumable&gt; element shall be present, and shall
        contain a &lt;manufacturedProduct&gt; entry conforming to the Product Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.2) template.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.53&quot;]">Error: In IHE PCC Product Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.2), the template ID for CCD parent (2.16.840.1.113883.10.20.1.53) is required.</assert>
        <assert test="cda:manufacturedMaterial/cda:code/cda:originalText/cda:reference">Error: In IHE PCC Product Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.2), the name and strength of the medication are specified in the elements under the
        &lt;manufacturedMaterial&gt; element. The &lt;originalText&gt; shall contain a &lt;reference&gt; whose URI value points to the generic name and strength of the medication, or just the generic name alone if strength is not relevant.</assert>
      </rule>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">
        <report test="cda:id">Success: R2.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Prescription Item ID element "id" is present.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.11-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.11&quot;]">
        <report test="//cda:substanceAdministation">Success: In IHE PCC Combination Medications: 1.3.6.1.4.1.19376.1.5.3.1.4.11), In the case of the prepackaged combination, it is sufficient to supply the name of the combination drug product, and its
        strength designation in a single "substanceAdministation" entry. The dosing information shall then be recorded as simply a count of administration units. .</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.3.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <!--  <title>epSOS Medication Item Entry Content Module Specification - errors validation phase</title> -->
      <rule context="//cda:entry/cda:substanceAdministration[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.4']">
        <!-- Verify that the parents templateId are present. -->
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7&quot;]">Error: (cf.&#194;&#167;13.1.1.2 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.19376.1.5.3.1.4.7) for Medication Item Entry
        Content Module shall be present.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.24&quot;]">Error: (cf.&#194;&#167;13.1.1.2 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (2.16.840.1.113883.10.20.1.24) for Medication Item Entry Content
        Module shall be present.</assert>
        <!--§7.2 Body Data elements -->
        <!--R4.9 Number of units per intake (cf.8.1.1.2.4.7, @value should not be in doseQuantity, so we require low and high element ) -->
        <assert test="cda:doseQuantity and ((cda:doseQuantity/cda:low/@value and cda:doseQuantity/cda:high/@value) or cda:doseQuantity/@nullFlavor)">Error: R4.9 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Number of units per intake value "doseQuantity"
        element is required. The element "low\@value" and "high\value" shall be present in the element "doseQuantity" otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:doseQuantity">Success: R4.9 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Number of units per intake "doseQuantity" is present</report>
        <report test="cda:doseQuantity/cda:low/@value and cda:doseQuantity/cda:high/@value">Success: R4.9 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Number of units per intake elements "doseQuantity\high\@value" and "doseQuantity\low\@value" are
        present.</report>
        <!--R4.10 Frequency of intake -->
        <assert test="(cda:effectiveTime[2][@operator='A'] and (cda:effectiveTime[2][@xsi:type='TS'] or cda:effectiveTime[2][@xsi:type='IVL_TS'] or cda:effectiveTime[2][@xsi:type='PIVL_TS'] or cda:effectiveTime[2][@xsi:type='EIVL_TS'])) or cda:effectiveTime[2]/@nullFlavor">
        Error: R4.10 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Frequency of intake element "effectiveTime[2]" is required. The attribute 'xsi:type' of the element "effectiveTime[2]" shall take a value between "TS, IVL_TS, PIVL_TS or EIVL_TS" and the
        attribute 'operator' shall take the value 'A'. Otherwise the nullFlavor attribute shall be present</assert>
        <report test="cda:effectiveTime[2]/@xsi:type">Success: R4.10 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Frequency of intake element "effectiveTime[2]" is present. The attribute 'xsi:type' is also present.</report>
        <!--R4.11 Duration of treatment -->
        <!--low -->
        <assert test="cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@nullFlavor or (cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@value and (matches(cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@value,'^\d{6,8}$') or matches(cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@value,'^\d{6,12}\-\d{2,4}$')))">
        Error: R4.11 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Duration of treatment element "low/@value" is required. The attribute 'value' of the element "low" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could
        contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@value">Success: R4.11 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Duration of treatment element "low/@value" is present for the effectiveTime[1]\@xsi:type='IVL_TS'.</report>
        <!--high -->
        <assert test="cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@nullFlavor or (cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@value and (matches(cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@value,'^\d{6,8}$') or matches(cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@value,'^\d{6,12}\-\d{2,4}$')))">
        Error: R4.11 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Duration of treatment element "high/@value" is required. The attribute 'value' of the element "high" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could
        contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@value">Success: R4.11 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Duration of treatment element "high/@value" is present for the effectiveTime[1]\@xsi:type='IVL_TS'.</report>
        <!-- R4.6 Pharmaceutical dose form (Optional) -->
        <assert test="(cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode and (every $i in cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode satisfies count(($i))&lt; 2) and cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode/@code and cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.2']) or (not(cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode) or cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode/@nullFlavor)">
        Error: R4.6 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Pharmaceutical dose form element "formCode" is Optional. If present, the attributes 'code' and 'codeSystem' of the element "formCode" shall be filled and the attribute 'codeSystem' shall be
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.2'. Otherwise the nullFlavor attribute shall be present. The element "formCode" shall have a cardinality [0..1].</assert>
        <!-- R4.7 Route of Administration (Optional) -->
        <assert test="(cda:routeCode and cda:routeCode/@code and cda:routeCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.12']) or (not(cda:routeCode) or cda:routeCode/@nullFlavor)">Error: R4.7 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Route of
        Administration element "routeCode" is Optional. If present, the attributes 'code' and 'codeSystem' of the element "formCode" shall be filled and the attribute 'codeSystem' shall be '1.3.6.1.4.1.12559.11.10.1.3.1.42.12'. Otherwise the nullFlavor
        attribute shall be present.</assert>
      </rule>
      <rule context="//cda:entry/cda:substanceAdministration[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']">
        <!-- R4.3 Active Ingredient -->
        <assert test="epsos:ingredient/epsos:code and (every $i in epsos:ingredient/epsos:code satisfies count(($i))&lt; 2) and ((epsos:ingredient/epsos:code/@code and epsos:ingredient/epsos:code/@codeSystem) or epsos:ingredient/epsos:code/@nullFlavor)">
        Error: R4.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Active Ingredient element "code" is required. The attributes 'code' and 'codeSystem' of the element "code" shall be filled. The element "code" shall not be empty" and cannot be repeated
        (cardinality [1..1])</assert>
        <report test="epsos:ingredient/epsos:code">Success: R4.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Active Ingredient element "code" is present.</report>
        <report test="epsos:ingredient/epsos:code/@code and epsos:ingredient/epsos:code/@codeSystem">Success: R4.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The attributes 'code' and 'codeSystem' of the Active Ingredient element "code" are filled.</report>
        <report test="(every $i in epsos:ingredient/epsos:code satisfies count(($i))&lt; 2)">Success: R4.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Active Ingredient element "code" is cardinality [1..1].</report>
        <!-- R4.4 Strength of the medicinal product -->
        <assert test="epsos:quantity and count(epsos:quantity)&lt;2">Error: R4.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Strength of the medicinal product element "quantity" is required. The element "quantity" shall have a cardinality [1..1].</assert>
        <report test="epsos:quantity">Success: R4.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Strength of the medicinal product element "quantity" is present.</report>
        <report test="count(epsos:quantity)&lt;2">Success: R4.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Strength of the medicinal product element "quantity" do have a cardinality [1..1].</report>
      </rule>
      <rule context="//cda:entry/cda:substanceAdministration[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial">
        <!-- R4.5 Medicinal product package (Optional) -->
        <assert test="(epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode and (every $i in epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode satisfies count(($i))&lt; 2) and epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode/@code and epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.3']) or (not(epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode) or epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode/@nullFlavor)">
        Error: R4.5 (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) The Medicinal product package element "formCode" is Optional. If present, the attributes 'code' and 'codeSystem' of the element "formCode" shall be filled and the attribute 'codeSystem' shall be
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.3'. Otherwise the nullFlavor attribute shall be present. The element "formCode" shall have a cardinality [0..1].</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Medications - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7&quot;]">
        <assert test="cda:templateId[@root = &quot;2.16.840.1.113883.10.20.1.24&quot;]">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), all Medications entries use the templateId element specified (2.16.840.1.113883.10.20.1.24) to indicate
        that they are medication acts. This element is required.</assert>
        <assert test="count(cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.1&quot;] | cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.8&quot;] | cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.9&quot;] | cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.10&quot;] | cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.11&quot;]) = 1">
        Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), the &lt;templateId&gt; element identifies the Medications &lt;entry&gt; as a particular type of medication event, allowing for validation of the content. As a side effect, readers of
        the CDA can quickly locate and identify medication events. The templateId must use one of the values in the table in the specification.</assert>
        <assert test="not(cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.1&quot;]) or count(.//cda:substanceAdministration) = 0">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), template ID 1.3.6.1.4.1.19376.1.5.3.1.4.7.1
        indicates a "normal" &lt;substanceAdministration&gt; act that may not contain any subordinate &lt;substanceAdministration&gt; acts.</assert>
        <assert test="count(cda:id) = 1">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), a top level &lt;substanceAdministration&gt; element must be uniquely identified in Medications. If there is no explicit identifier for the observation
        in the source EMR system, a GUID may be used for the root attribute, and the extension may be omitted. Although HL7 allows for multiple identifiers, the profile requires that one and only one be used.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), the status of all Medications &lt;substanceAdministration&gt; elements must be "completed". The act has either occurred,
        or the request or order has been placed.</assert>
        <assert test="cda:consumable/cda:manufacturedProduct/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">Error: In IHE PCC Medications (1.3.6.1.4.1.19376.1.5.3.1.4.7), the &lt;consumable&gt; element shall be present, and shall
        contain a &lt;manufacturedProduct&gt; entry conforming to the Product Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.2) template.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.53&quot;]">Error: In IHE PCC Product Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.2), the template ID for CCD parent (2.16.840.1.113883.10.20.1.53) is required.</assert>
        <assert test="cda:manufacturedMaterial/cda:code/cda:originalText/cda:reference">Error: In IHE PCC Product Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.2), the name and strength of the medication are specified in the elements under the
        &lt;manufacturedMaterial&gt; element. The &lt;originalText&gt; shall contain a &lt;reference&gt; whose URI value points to the generic name and strength of the medication, or just the generic name alone if strength is not relevant.</assert>
      </rule>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">
        <report test="cda:id">Success: R2.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Prescription Item ID element "id" is present.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.11-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.11&quot;]">
        <report test="//cda:substanceAdministation">Success: In IHE PCC Combination Medications: 1.3.6.1.4.1.19376.1.5.3.1.4.11), In the case of the prepackaged combination, it is sufficient to supply the name of the combination drug product, and its
        strength designation in a single "substanceAdministation" entry. The dosing information shall then be recorded as simply a count of administration units. .</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Normal Dosing - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.1&quot;]">
        <report test="//cda:substanceAdministation">Error: In IHE PCC Normal Dosing: 1.3.6.1.4.1.19376.1.5.3.1.4.7.1), medications that use this template identifier shall not use subordinate &lt;substanceAdministation&gt; acts.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Internal References - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.4.1&quot;]">
        <assert test="self::cda:act">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), for compatibility with the Clinical Statement model the Internal Reference shall always use the &lt;act&gt; class</assert>
        <assert test="cda:id[@root and @extension]">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the id element shall be present in Internal Reference. The root and extension attributes shall identify an element defined
        elsewhere in the same document.</assert>
        <assert test="cda:code">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the code element of Internal Reference shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.13-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Allergies and Other Adverse Reactions Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.13&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Allergies and Other Adverse Reactions Section (1.3.6.1.4.1.19376.1.5.3.1.3.13), the Allergies and Other Adverse Reactions can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.2&quot;]">Error: In IHE PCC Allergies and Other Adverse Reactions Section (1.3.6.1.4.1.19376.1.5.3.1.3.13), the parent template identifier for Allergies and Other Adverse
        Reactions is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;48765-2&quot;]">Error: In IHE PCC Allergies and Other Adverse Reactions Section (1.3.6.1.4.1.19376.1.5.3.1.3.13), the section type code of a Allergies and Other Adverse Reactions must be 48765-2</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Allergies and Other Adverse Reactions Section (1.3.6.1.4.1.19376.1.5.3.1.3.13), the section type code must come from the LOINC code system
        (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.3&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC Allergies and Other Adverse Reactions Section (1.3.6.1.4.1.19376.1.5.3.1.3.13), an Allergies and Other Adverse Reactions must contain Allergies and Intolerances Concern.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5.3-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Allergy and Intolerance Concern - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.3&quot;]">
        <assert test="cda:templateId[@root = &quot;2.16.840.1.113883.10.20.1.27&quot;] and cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.1&quot;]">Error: In IHE PCC Allergy and Intolerance Concern: 1.3.6.1.4.1.19376.1.5.3.1.4.5.3), the
        parent CCD template ID (2.16.840.1.113883.10.20.1.27) and the parent PCC template ID (1.3.6.1.4.1.19376.1.5.3.1.4.5.1) shall be present in the Allergy and Intolerance Concern.</assert>
        <assert test="(cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;false&quot;]//cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6&quot;]) or (cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;false&quot;]//cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6&quot;])">
        Error: In IHE PCC Allergy and Intolerance Concern: 1.3.6.1.4.1.19376.1.5.3.1.4.5.3), this Allergy and Intolerance Concern shall contain one or more allergy or intolerance entries that conform to the Allergy and Intolerance Entry. For CDA this
        SHALL be represented with the &lt;entryRelationship&gt; element. For HL7 Version 3 Messages, this SHALL be represented as a &lt;sourceOf&gt; element. The typeCode SHALL be 'SUBJ' and inversionInd SHALL be 'false'.</assert>
      </rule>
      <!--cf.epSOS_Implementation_v0.2.20100702.doc -->
      <!--$7.2 Body Data elements -->
      <rule context="//cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/cda:entryRelationship[@typeCode='SUBJ']/cda:observation[cda:templateId/@root='2.16.840.1.113883.10.20.1.18']/cda:entryRelationship[@typeCode='MFST']/cda:observation[cda:templateId/@root='2.16.840.1.113883.10.20.1.54']">

        <!-- R5.1 Allergy Display Name  -->
        <assert test="cda:code and ((cda:code/@displayName and cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.11']) or cda:code[@nullFlavor='NA'])">Error: R5.1 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy Display Name element "code" is required.
        The attribute 'displayName' shall be filled and 'codeSystem' shall take the value '1.3.6.1.4.1.12559.11.10.1.3.1.42.11' otherwise the nullFlavor attribute shall have a value of "NA".</assert>
        <report test="cda:code">Success: R5.1 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy Display Name element "code" is present.</report>
        <report test="cda:code/@displayName and cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.11']">Success: R5.1 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy Display Name element "code" attribute's 'displayName' is filled and 'codeSystem' is
        correctly set to the value '1.3.6.1.4.1.12559.11.10.1.3.1.42.11'.</report>
      </rule>
      <rule context="//cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/cda:entryRelationship[@typeCode='SUBJ']/cda:observation[cda:templateId/@root='2.16.840.1.113883.10.20.1.18']">
        <!-- R5.2 Allergy id code  -->
        <assert test="cda:code and (cda:code/@code or cda:code/@nullFlavor)">Error: R5.2 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy id code element "code" is required. The attribute 'code' of the element "code" shall be filled otherwise the nullFlavor
        attribute shall be present.</assert>
        <report test="cda:code">Success: R5.2 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy id code element "code" is present.</report>
        <report test="cda:code/@code">Success: R5.2 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy id code element "code" attribute's 'code' is filled.</report>
      </rule>
      <rule context="//cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/cda:entryRelationship[@typeCode='SUBJ']/cda:observation[cda:templateId/@root='2.16.840.1.113883.10.20.1.18']/cda:effectiveTime/cda:low">
        <!-- R5.3 Allergy Onset Date (Optional) -->
        <assert test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$'))) or (not(@value) or @nullFlavor)">Error: R5.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy Onset Date element "low/@value" is Optional. If present,
        the attributes 'value' shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor attribute
        shall be present.</assert>
      </rule>
      <rule context="//cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/cda:entryRelationship[@typeCode='SUBJ']/cda:observation[cda:templateId/@root='2.16.840.1.113883.10.20.1.18']/cda:participant[@typeCode='CSM']/cda:participantRole[@classCode='MANU']/cda:playingEntity[@classCode='MMAT']">

        <!-- R5.4 Allergy Agent Description  -->
        <assert test="cda:code and ((cda:code/@displayName and (cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.19'] or cda:code[@codeSystem='2.16.840.1.113883.6.73'])) or cda:code/@nullFlavor)">Error: R5.4 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The
        Allergy Agent Description element "code" is required. The attribute 'displayName' shall be filled and 'codeSystem' shall take the value '2.16.840.1.113883.6.73' If the allergenic agent is a medicament, if not, epSOSAllergenNoDrugs
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.19', otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:code">Success: R5.4 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy Agent Description element "code" is present.</report>
        <report test="cda:code/@displayName">Success: R5.4 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy Agent Description element "code" attribute's 'displayName' is filled.</report>
        <!-- R5.5 Allergy Agent Code  -->
        <assert test="cda:code and (cda:code/@code or cda:code/@nullFlavor)">Error: R5.5 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy Agent Code element "code" is required. The attribute 'code' of the element "code" shall be filled , otherwise the
        nullFlavor attribute shall be present.</assert>
        <report test="cda:code">Success: R5.5 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy Agent Code element "code" is present.</report>
        <report test="cda:code/@code">Success: R5.5 (1.3.6.1.4.1.19376.1.5.3.1.4.5.3) The Allergy Agent Code element "code" attribute's 'code' is filled.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Concern Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.1&quot;]">
        <assert test="../cda:act">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the Concern Entry can only be used on acts.</assert>
        <assert test="../cda:act[@classCode=&quot;ACT&quot;] and ../cda:act[@moodCode=&quot;EVN&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), all concerns reflect the act of recording (&lt;act classCode='ACT'&gt;) the event
        (moodCode='EVN') of being concerned about a problem, allergy or other issue about the patient condition.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.27&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the required template identifiers indicates this Concern Entry conforms to the Concern Content
        module. This content module inherits constraints from the HL7 CCD Template for problem acts, and so also includes that template identifier (2.16.840.1.113883.10.20.1.27).</assert>
        <assert test="cda:id">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the Concern Entry requires id element.</assert>
        <assert test="cda:code[@nullFlavor=&quot;NA&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the code is not applicable to a concern act (Concern Entry), and so shall be recorded as &lt;code
        nullFlavor='NA'/&gt;.</assert>
        <assert test="cda:statusCode[@code=&quot;active&quot; or @code=&quot;suspended&quot; or @code=&quot;aborted&quot; or @code=&quot;completed&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the statusCode associated with
        any concern must be one of the following values: active, suspended, aborted or completed.</assert>
        <assert test="cda:effectiveTime/cda:low">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the &lt;effectiveTime&gt; element records the starting and ending times during which the concern (Concern Entry) was active. The
        &lt;low&gt; element shall be present.</assert>
        <assert test="(cda:statusCode[@code=&quot;completed&quot; or @code=&quot;aborted&quot;] and cda:effectiveTime/cda:high) or (cda:statusCode[@code=&quot;active&quot; or @code=&quot;suspended&quot;] and not(cda:effectiveTime/cda:high))">Error: In
        IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the &lt;effectiveTime&gt; element records the starting and ending times during the concern (Concern Entry). The &lt;high&gt; element shall be present for concerns in the completed or
        aborted state, and shall not be present otherwise.</assert>
        <assert test="(cda:entryRelationship[@typeCode=&quot;SUBJ&quot;] and cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot; or @root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6&quot;]) or (cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;false&quot;] and cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot; or @root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6&quot;])">
        Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), each concern (Concern Entry) is about one or more related problems or allergies. This entry shall contain one or more problem or allergy entries that conform to the specification
        in section Problem Entry or Allergies and Intolerances. This is how a series of related observations can be grouped as a single concern. For CDA this SHALL be represented with the &lt;entryRelationship&gt; element. For HL7 Version 3 Messages,
        this SHALL be represented as a &lt;sourceOf&gt; element. The typeCode SHALL be SUBJ for both HL7 Version 3 and CDA. HL7 Version 3 additionally requires that inversionInd SHALL be false.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.6-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Allergies and Intolerances - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6&quot;]">
        <assert test="../cda:observation[@classCode=&quot;OBS&quot;]">Error: In IHE PCC Allergies and Intolerances (1.3.6.1.4.1.19376.1.5.3.1.4.6), allergies and intolerances are special kinds of problems, and so are also recorded in the CDA
        &lt;observation&gt; element, with classCode='OBS'.</assert>
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;]">Error: In IHE PCC Allergies and Intolerances (1.3.6.1.4.1.19376.1.5.3.1.4.6), this Allergies and Intolerances entry has a template identifier of
        1.3.6.1.4.1.19376.1.5.3.1.4.6, and is a subtype of the 1.3.6.1.4.1.19376.1.5.3.1.4.5 Problem Entry, and so must also conform to the rules of the problem entry, which has the template identifier of 1.3.6.1.4.1.19376.1.5.3.1.4.5. These elements are
        required.</assert>
        <assert test="cda:code[@code and @codeSystem]">Error: In IHE PCC Allergies and Intolerances (1.3.6.1.4.1.19376.1.5.3.1.4.6), the &lt;code&gt; element of Allergies and Intolerances represents the kind of allergy observation made, to a drug, food
        or environmental agent, and whether it is an allergy, non-allergy intolerance, or unknown class of intolerance (not known to be allergy or intolerance). The &lt;code&gt; element of an allergy entry shall be provided, and a code and codeSystem
        attribute shall be present.</assert>
        <assert test="(cda:value[@xsi:type=&quot;CD&quot;]) and (cda:value[@code and @codeSystem] or cda:value[not(@code) and not(@codeSystem)])">Error: In IHE PCC Allergies and Intolerances (1.3.6.1.4.1.19376.1.5.3.1.4.6), the &lt;value&gt; is a
        description of the allergy or adverse reaction. While the value may be a coded or an uncoded string, the type is always a coded value (xsi:type='CD'). If coded, the code and codeSystem attributes must be present. The codingSystem shall reference
        a controlled vocabulary describing allergies and adverse reactions. If uncoded, all attributes other than xsi:type='CD' must be absent.</assert>
        <assert test="not(cda:participant[@typeCode=&quot;CSM&quot;]/cda:participantRole[@classCode=&quot;MANU&quot;]/cda:playingEntity[@classCode=&quot;MMAT&quot;]) or cda:participant[@typeCode=&quot;CSM&quot;]/cda:participantRole[@classCode=&quot;MANU&quot;]/cda:playingEntity[@classCode=&quot;MMAT&quot;]/cda:code/cda:originalText/cda:reference">
        Error: In IHE PCC Allergies and Intolerances (1.3.6.1.4.1.19376.1.5.3.1.4.6), the &lt;code&gt; element shall be present. It may contain a code and codeSystem attribute to indicate the code for the substance causing the allergy or intolerance. It
        shall contain a &lt;reference&gt; to the &lt;originalText&gt; in the narrative where the substance is named.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;] and cda:entryRelationship/*/cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.54&quot;])">
        Error: In IHE PCC Allergies and Intolerances (1.3.6.1.4.1.19376.1.5.3.1.4.6), the entry contained with this Allergies and Intolerance entry relationship is some sort of problem that is a manifestation of the allergy. It is recorded using the
        Problem Entry structure (template identifier 1.3.6.1.4.1.19376.1.5.3.1.4.5), with the additional template identifier (2.16.840.1.113883.10.20.1.54) indicating that this problem is a reaction.</assert>
        <assert test="(not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) and not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;])) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Allergies and Intolerances (1.3.6.1.4.1.19376.1.5.3.1.4.6), an optional &lt;entryRelationship&gt; element may be present indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall contain
        a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). For CDA this SHALL be represented with the &lt;entryRelationship&gt; element. For HL7 Version 3 Messages, this SHALL be represented as a
        &lt;sourceOf&gt; element. The typeCode SHALL be 'SUBJ' and inversionInd SHALL be 'true'.</assert>
        <assert test="(not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) and not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;])) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Allergies and Intolerances (1.3.6.1.4.1.19376.1.5.3.1.4.6), an optional &lt;entryRelationship&gt; may be present indicating the clinical status of the allergy, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be 'REFR' and inversionInd SHALL be 'false'. For CDA this
        SHALL be represented with the &lt;entryRelationship&gt; element. For HL7 Version 3 Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="(not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) and not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;])) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Allergies and Intolerances (1.3.6.1.4.1.19376.1.5.3.1.4.6), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the allergy. When present, this
        &lt;entryRelationship&gt; element shall contain an entry conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the
        &lt;entryRelationship&gt; element. For HL7 Version 3 Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;]">
        <assert test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the basic pattern for reporting a problem uses the CDA &lt;observation&gt; element,
        setting the classCode='OBS' to represent that this is an observation of a problem, and the moodCode='EVN', to represent that this is an observation that has in fact taken place.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.28&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), &lt;templateId&gt; elements identify this under both IHE and CCD specifications
        (2.16.840.1.113883.10.20.1.28). This CCD template ID SHALL be included.</assert>
        <assert test="cda:id">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the specific Problem Entry observation being recorded must have an identifier (&lt;id&gt;) that shall be provided for tracking purposes. If the source EMR does
        not or cannot supply an intrinsic identifier, then a GUID shall be provided as the root, with no extension (e.g., &lt;id root='CE1215CD-69EC-4C7B-805F-569233C5E159'/&gt;). At least one identifier must be present, more than one may
        appear.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;text&gt; element is required and points to the text describing the problem being recorded; including any dates, comments, et cetera. The
        &lt;reference&gt; contains a URI in value attribute. This URI points to the free text description of the problem in the document that is being described.</assert>
        <assert test="cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), a clinical document normally records only those condition observation events that have been completed, not observations
        that are in any other state. Therefore, the &lt;statusCode&gt; shall always have code='completed' for Problem Entry.</assert>
        <report test="cda:effectiveTime/cda:width or cda:effectiveTime/cda:center">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), while CDA allows for multiple mechanisms to record this time interval (e.g. by low and high values, low
        and width, high and width, or center point and width), we are constraining Medical summaries (Problem Entry) to use only the low/high form.</report>
        <assert test="cda:effectiveTime/cda:low[@value or @nullFlavor]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the effectiveTime &lt;low&gt; value shall normally be present in Problem Entry. There are exceptions, such as for the
        case where the patient may be able to report that they had chicken pox, but are unsure when. In this case, the &lt;effectiveTime&gt; element shall have a &lt;low&gt; element with a nullFlavor attribute shall be present.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;value&gt; is the condition that was found. This element is required. While the value of Problem Entry code may be a coded
        or an un-coded string, the type is always a coded value (xsi:type='CD').</assert>
        <assert test="(cda:value[@code and @codeSystem]) or (not(cda:value[@code]) and not(cda:value[@codeSystem]))">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), if Problem Entry value is coded, the code and codeSystem attributes
        shall be present. If uncoded, all attributes other than xsi:type='CD' must be absent.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] | cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional severity entry relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:subjectOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a &lt;subjectOf&gt;
        element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Problem Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Health Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a
        &lt;sourceOf&gt; element.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Severity - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.55&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the templateId for CCD Severity (2.16.840.1.113883.10.20.1.55) must be present.</assert>
        <assert test="cda:code[@code=&quot;SEV&quot; and @codeSystem=&quot;2.16.840.1.113883.5.4&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the observation is of Severity, as indicated by the code element. This element is
        required. The code and codeSystem attributes shall be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the code attribute of &lt;statusCode&gt; for all Severity observations shall be completed. While the &lt;statusCode&gt;
        element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;] and (((cda:value[@codeSystem=&quot;2.16.840.1.113883.5.1063&quot; and (@code=&quot;H&quot; or @code=&quot;M&quot; or @code=&quot;L&quot;)]) ) or ( not(cda:value[@codeSystem]) ))">Error: In IHE PCC
        Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &gt;value&lt; element contains the level of severity. It is always represented using t'he CD datatype (xsi:type='CD'), even though the value may be a coded or uncoded string. If coded, it shall use
        the HL7 SeverityObservation vocabulary (codeSystem='2.16.840.1.113883.5.1063') containing three values (H, M, and L), representing high, moderate and low severity depending upon whether the severity is life threatening, presents noticeable
        adverse consequences, or is unlikely substantially effect the situation of the subject.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Status Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.57&quot;] and cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.50&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), CCD templateIds
        (2.16.840.1.113883.10.20.1.50 and 2.16.840.1.113883.10.20.1.57) for parents of Problem Status Observation shall exist.</assert>
        <assert test="cda:code[@code = &quot;33999-4&quot; and @codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the Problem Status Observation is of clinical status, as
        indicated by the &lt;code&gt; element. This element must be present. The code and codeSystem shall be recorded exactly.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element that points to the narrative text describing the clinical status. For
        CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing to the narrative section (see Linking Narrative and Coded Entries), rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt;
        element SHALL contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the code attribute of &lt;statusCode&gt; for all Problem Status observations shall be completed. While
        the &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot; and @codeSystem=&quot;2.16.840.1.113883.6.96&quot;] and cda:value[@code = &quot;55561003&quot; or @code = &quot;73425007&quot; or @code = &quot;90734009&quot; or @code = &quot;7087005&quot; or @code = &quot;255227004&quot; or @code = &quot;415684004&quot; or @code = &quot;410516002&quot; or @code = &quot;413322009&quot;]">
        Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;value&gt; element contains the clinical status. It is always represented using the CE datatype (xsi:type='CE'). It shall contain a code from the set of values
        from SNOMED CT listed in the profile.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Health Status - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.51&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the parent templateId (2.16.840.1.113883.10.20.1.51) for Health Status shall be present.</assert>
        <assert test="cda:code[@code=&quot;11323-3&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code element is required. The code and codeSystem attributes shall be
        recorded exactly as indicated. See: http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.1.2</assert>
        <assert test="cda:text">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing
        to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt; element shall contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code attribute of &lt;statusCode&gt; for all health status observations shall be completed. While the
        &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;value&gt; element contains the clinical status of the Health Status Observation. It is always represented using the CE
        datatype (xsi:type='CE').</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Comments - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.40&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the parent CCD templateId (2.16.840.1.113883.10.20.1.40) for Comments must be included.</assert>
        <assert test="cda:code[@code=&quot;48767-8&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code element for Comments is required. The code and codeSystem attributes shall
        be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code attribute of &lt;statusCode&gt; for all Comments shall be completed.</assert>
        <assert test="not(cda:author) or ( cda:author/cda:time and cda:author/cda:assignedAuthor/cda:id and cda:author/cda:assignedAuthor/cda:addr and cda:author/cda:assignedAuthor/cda:telecom and ( cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name or cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name))">
        Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the Comment may have an author. The time of the comment creation shall be recorded in the &lt;time&gt; element when the &lt;author&gt; element is present. The identifier of the author,
        and their address and telephone number must be present inside the &lt;id&gt;, &lt;addr&gt; and &lt;telecom&gt; elements when the &lt;author&gt; element is present. The author's and/or the organization's name must be present when the
        &lt;author&gt; element is present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.6.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6.1&quot;]">
        <assert test="self::cda:entryRelationship[@typeCode=&quot;MFST&quot;]">Error: In IHE PCC Product Entry (1.3.6.1.4.1.19376.1.5.3.1.4.6.1), An allergy entry can record the reactions that are manifestations of the allergy or in- tolerance. It uses a
        related entry "entryRelationship" that indicates the manifestations typeCode='MFST' the reported allergy or intolerance.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;]">
        <assert test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the basic pattern for reporting a problem uses the CDA &lt;observation&gt; element,
        setting the classCode='OBS' to represent that this is an observation of a problem, and the moodCode='EVN', to represent that this is an observation that has in fact taken place.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.28&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), &lt;templateId&gt; elements identify this under both IHE and CCD specifications
        (2.16.840.1.113883.10.20.1.28). This CCD template ID SHALL be included.</assert>
        <assert test="cda:id">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the specific Problem Entry observation being recorded must have an identifier (&lt;id&gt;) that shall be provided for tracking purposes. If the source EMR does
        not or cannot supply an intrinsic identifier, then a GUID shall be provided as the root, with no extension (e.g., &lt;id root='CE1215CD-69EC-4C7B-805F-569233C5E159'/&gt;). At least one identifier must be present, more than one may
        appear.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;text&gt; element is required and points to the text describing the problem being recorded; including any dates, comments, et cetera. The
        &lt;reference&gt; contains a URI in value attribute. This URI points to the free text description of the problem in the document that is being described.</assert>
        <assert test="cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), a clinical document normally records only those condition observation events that have been completed, not observations
        that are in any other state. Therefore, the &lt;statusCode&gt; shall always have code='completed' for Problem Entry.</assert>
        <report test="cda:effectiveTime/cda:width or cda:effectiveTime/cda:center">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), while CDA allows for multiple mechanisms to record this time interval (e.g. by low and high values, low
        and width, high and width, or center point and width), we are constraining Medical summaries (Problem Entry) to use only the low/high form.</report>
        <assert test="cda:effectiveTime/cda:low[@value or @nullFlavor]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the effectiveTime &lt;low&gt; value shall normally be present in Problem Entry. There are exceptions, such as for the
        case where the patient may be able to report that they had chicken pox, but are unsure when. In this case, the &lt;effectiveTime&gt; element shall have a &lt;low&gt; element with a nullFlavor attribute shall be present.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;value&gt; is the condition that was found. This element is required. While the value of Problem Entry code may be a coded
        or an un-coded string, the type is always a coded value (xsi:type='CD').</assert>
        <assert test="(cda:value[@code and @codeSystem]) or (not(cda:value[@code]) and not(cda:value[@codeSystem]))">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), if Problem Entry value is coded, the code and codeSystem attributes
        shall be present. If uncoded, all attributes other than xsi:type='CD' must be absent.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] | cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional severity entry relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:subjectOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a &lt;subjectOf&gt;
        element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Problem Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Health Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a
        &lt;sourceOf&gt; element.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Severity - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.55&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the templateId for CCD Severity (2.16.840.1.113883.10.20.1.55) must be present.</assert>
        <assert test="cda:code[@code=&quot;SEV&quot; and @codeSystem=&quot;2.16.840.1.113883.5.4&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the observation is of Severity, as indicated by the code element. This element is
        required. The code and codeSystem attributes shall be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the code attribute of &lt;statusCode&gt; for all Severity observations shall be completed. While the &lt;statusCode&gt;
        element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;] and (((cda:value[@codeSystem=&quot;2.16.840.1.113883.5.1063&quot; and (@code=&quot;H&quot; or @code=&quot;M&quot; or @code=&quot;L&quot;)]) ) or ( not(cda:value[@codeSystem]) ))">Error: In IHE PCC
        Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &gt;value&lt; element contains the level of severity. It is always represented using t'he CD datatype (xsi:type='CD'), even though the value may be a coded or uncoded string. If coded, it shall use
        the HL7 SeverityObservation vocabulary (codeSystem='2.16.840.1.113883.5.1063') containing three values (H, M, and L), representing high, moderate and low severity depending upon whether the severity is life threatening, presents noticeable
        adverse consequences, or is unlikely substantially effect the situation of the subject.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Status Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.57&quot;] and cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.50&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), CCD templateIds
        (2.16.840.1.113883.10.20.1.50 and 2.16.840.1.113883.10.20.1.57) for parents of Problem Status Observation shall exist.</assert>
        <assert test="cda:code[@code = &quot;33999-4&quot; and @codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the Problem Status Observation is of clinical status, as
        indicated by the &lt;code&gt; element. This element must be present. The code and codeSystem shall be recorded exactly.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element that points to the narrative text describing the clinical status. For
        CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing to the narrative section (see Linking Narrative and Coded Entries), rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt;
        element SHALL contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the code attribute of &lt;statusCode&gt; for all Problem Status observations shall be completed. While
        the &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot; and @codeSystem=&quot;2.16.840.1.113883.6.96&quot;] and cda:value[@code = &quot;55561003&quot; or @code = &quot;73425007&quot; or @code = &quot;90734009&quot; or @code = &quot;7087005&quot; or @code = &quot;255227004&quot; or @code = &quot;415684004&quot; or @code = &quot;410516002&quot; or @code = &quot;413322009&quot;]">
        Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;value&gt; element contains the clinical status. It is always represented using the CE datatype (xsi:type='CE'). It shall contain a code from the set of values
        from SNOMED CT listed in the profile.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Health Status - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.51&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the parent templateId (2.16.840.1.113883.10.20.1.51) for Health Status shall be present.</assert>
        <assert test="cda:code[@code=&quot;11323-3&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code element is required. The code and codeSystem attributes shall be
        recorded exactly as indicated. See: http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.1.2</assert>
        <assert test="cda:text">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing
        to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt; element shall contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code attribute of &lt;statusCode&gt; for all health status observations shall be completed. While the
        &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;value&gt; element contains the clinical status of the Health Status Observation. It is always represented using the CE
        datatype (xsi:type='CE').</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Comments - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.40&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the parent CCD templateId (2.16.840.1.113883.10.20.1.40) for Comments must be included.</assert>
        <assert test="cda:code[@code=&quot;48767-8&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code element for Comments is required. The code and codeSystem attributes shall
        be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code attribute of &lt;statusCode&gt; for all Comments shall be completed.</assert>
        <assert test="not(cda:author) or ( cda:author/cda:time and cda:author/cda:assignedAuthor/cda:id and cda:author/cda:assignedAuthor/cda:addr and cda:author/cda:assignedAuthor/cda:telecom and ( cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name or cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name))">
        Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the Comment may have an author. The time of the comment creation shall be recorded in the &lt;time&gt; element when the &lt;author&gt; element is present. The identifier of the author,
        and their address and telephone number must be present inside the &lt;id&gt;, &lt;addr&gt; and &lt;telecom&gt; elements when the &lt;author&gt; element is present. The author's and/or the organization's name must be present when the
        &lt;author&gt; element is present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.23-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Immunizations Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.23&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Immunizations Section (1.3.6.1.4.1.19376.1.5.3.1.3.23), the Immunizations can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.6&quot;]">Error: In IHE PCC Immunizations Section (1.3.6.1.4.1.19376.1.5.3.1.3.23), the parent template identifier for Immunizations is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;11369-6&quot;]">Error: In IHE PCC Immunizations Section (1.3.6.1.4.1.19376.1.5.3.1.3.23), the section type code of a Immunizations must be 11369-6</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Immunizations Section (1.3.6.1.4.1.19376.1.5.3.1.3.23), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.12&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC Immunizations Section (1.3.6.1.4.1.19376.1.5.3.1.3.23), a Immunizations must contain Immunization.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.12-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Immunizations - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.12&quot;]">
        <!--

Note: 2009/10/28.  The wiki version of this template has been modified to refer to both moodCode EVN and INT.
However, this change has not been made part of the TF yet and there is no language to forced these as the only
two moodCode options...

-->
        <assert test="@negationInd=&quot;true&quot; or @negationInd=&quot;false&quot;">Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), an Immunization Entry may be a record of why a specific immunization was not performed. In this case,
        negationInd shall be set to true, otherwise, it shall be false.</assert>
        <report test="@negationInd=&quot;true&quot; or @negationInd=&quot;false&quot;">Success: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), an Immunization Entry may be a record of why a specific immunization was not performed. In this
        case, negationInd is set to true.</report>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.24&quot;]">Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), Immunization shall record its parent CCD template ID (2.16.840.1.113883.10.20.1.24).</assert>
        <report test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.24&quot;]">Success: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), Immunization has recorded its parent CCD template ID (2.16.840.1.113883.10.20.1.24).</report>
        <assert test="cda:id">Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), an Immunization shall have an identifier (id).</assert>
        <report test="cda:id">Success: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), an Immunization do have an identifier (id)..</report>
        <assert test="cda:code[@code and @codeSystem]">Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), this required element records that the act was an immunization. The substance administration act must have a &lt;code&gt; element
        with code and codeSystem attributes present. If no coding system is used by the source, then simply record the code exactly as shown: &lt;code code='IMMUNIZ' codeSystem='2.16.840.1.113883.5.4' codeSystemName='ActCode'/&gt;.</assert>
        <report test="cda:code[@code and @codeSystem]">Success: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), this required element records that the act was an immunization. The substance administration act do have a &lt;code&gt; element
        with code and codeSystem attributes present. If no coding system is used by the source, then simply record the code exactly as shown: &lt;code code='IMMUNIZ' codeSystem='2.16.840.1.113883.5.4' codeSystemName='ActCode'/&gt;.</report>
        <assert test="cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), the statusCode shall be set to "completed" for all Immunizations.</assert>
        <report test="cda:statusCode[@code=&quot;completed&quot;]">Success: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), the statusCode is correctly set to the value "completed" for all Immunizations.</report>
        <assert test="cda:effectiveTime[@value or @nullFlavor]">Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), the effectiveTime element shall be present and shall contain a time value that indicates the date of the substance
        administration. If the date is unknown, this shall be recorded using the nullFlavor attribute, with the reason that the information is unknown being specified.</assert>
        <report test="cda:effectiveTime[@value or @nullFlavor]">Success: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), the effectiveTime element is present and contain a time value that indicates the date of the substance administration. If
        the date is unknown, this shall be recorded using the nullFlavor attribute, with the reason that the information is unknown being specified.</report>
        <assert test="cda:consumable//cda:manufacturedProduct//cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), the &lt;consumable&gt; element shall be present, and shall
        contain a &lt;manufacturedProduct&gt; entry conforming to the Product Entry template (1.3.6.1.4.1.19376.1.5.3.1.4.7.2).</assert>
        <report test="cda:consumable//cda:manufacturedProduct//cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">Success: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), the &lt;consumable&gt; element is present, and contains
        a &lt;manufacturedProduct&gt; entry conforming to the Product Entry template (1.3.6.1.4.1.19376.1.5.3.1.4.7.2).</report>
        <assert test="not(cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]) or (cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]//cda:observation/cda:id and cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]//cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.28&quot;] and cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]//cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;] and cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]//cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.54&quot;])">
        Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), an entryRelationship shall be used to identify adverse reactions caused by the immunization. In that case, an observation is required, and provides a pointer to the the adverse
        reaction caused by the immunization. An identifier (id) of the observation is required. It shall contain a conforming Problem Entry that also conforms to the CCD Reaction template.</assert>
        <report test="not(cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]) or (cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]//cda:observation/cda:id and cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]//cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.28&quot;] and cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]//cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;] and cda:entryRelationship[@inversionInd=&quot;false&quot; and @typeCode=&quot;CAUS&quot;]//cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.54&quot;])">
        Success: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), an entryRelationship is used to identify adverse reactions caused by the immunization. In that case, an observation is present, and provides a pointer to the the adverse reaction
        caused by the immunization. An identifier (id) of the observation is present. It contain a conforming Problem Entry that also conforms to the CCD Reaction template.</report>
      </rule>
      <!-- 
    Note that this rule context is for the entire 2.16.840.1.113883.10.20.1.46 CCD
    template Id.  "test" statements shall take care to limit the tests to
    only that template Id which takes place as a child of a
    1.3.6.1.4.1.19376.1.5.3.1.4.12 object.
-->
      <rule context="*[cda:templateId/@root='2.16.840.1.113883.10.20.1.46']">
        <!--

Not needed.  Already checked by CCD. 

    <assert test='cda:code[@code="30973-2" and @codeSystem="2.16.840.1.113883.6.1"]'>
       Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), in the optional entry relationship indicating
       that position of this immunization in a series of immunizations, the
       &lt;code&gt; element shall be present and must be recorded with
       the code and codeSystem in the guidelines (code='30973-2'
       displayName='Dose Number' codeSystem='2.16.840.1.113883.6.1'
       codeSystemName='LOINC'). This element
       indicates that the observation describes the dose number for
       the immunization.
    </assert>
-->
        <assert test="not(ancestor::*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.12&quot;]) or cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), in the optional entry relationship
        indicating that position of this immunization in a series of immunizations, the &lt;statusCode&gt; element shall be present, and must be recorded as completed.</assert>
        <report test="not(ancestor::*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.12&quot;]) or cda:statusCode[@code=&quot;completed&quot;]">Success: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), in the optional entry relationship
        indicating that position of this immunization in a series of immunizations, the &lt;statusCode&gt; element is present, and recorded as completed.</report>
        <!--
 
Not needed.  Already checked by CCD.

    <assert test='cda:value[@value]'>
       Error: In IHE PCC Immunizations (1.3.6.1.4.1.19376.1.5.3.1.4.12), in the optional entry relationship indicating
       that position of this immunization in a series of immunizations, the
       value element shall be present, and shall indicate the immunization
       series number in the value attribute. 
    </assert>
-->
      </rule>
      <!--R8.3 Vaccinations Dates (Optional) -->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.12&quot;]/cda:effectiveTime">
        <assert test="(@value and (matches(@value,'\d{4}') or matches(@value,'\d{6}') or matches(@value,'\d{8}') or matches(@value,'\d{12}') or matches(@value,'\d{14}'))) or @nullFlavor">Error: R8.3 (1.3.6.1.4.1.19376.1.5.3.1.4.12) Vaccinations Date
        element "effectiveTime" is Optional. If present the attribute 'value' shall have the date-time format (CCYYMMDDHHMMSS). Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="(@value and (matches(@value,'\d{4}') or matches(@value,'\d{6}') or matches(@value,'\d{8}') or matches(@value,'\d{12}') or matches(@value,'\d{14}')))">Success: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Vaccinations Date element
        "effectiveTime" is present. The attribute 'value' do have the date-time format (CCYYMMDDHHMMSS).</report>
      </rule>
      <!--R8.2 Vaccinations code (Optional)-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.12&quot;]/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/cda:code">
        <assert test="(@code and @code='1.3.6.1.4.1.12559.11.10.1.3.1.42.18') or (not(@code) or @nullFlavor)">Error: R8.2 (1.3.6.1.4.1.19376.1.5.3.1.4.12) Vaccinations code element "code/@code" is Optional. If present the attribute 'code' shall be
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.18'.</assert>
        <report test="@code and @code='1.3.6.1.4.1.12559.11.10.1.3.1.42.18'">Success: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Vaccinations code element "code/@code" is present. The attribute 'code' is correctly set to the value
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.18'.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.53&quot;]">Error: In IHE PCC Product Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.2), the template ID for CCD parent (2.16.840.1.113883.10.20.1.53) is required.</assert>
        <assert test="cda:manufacturedMaterial/cda:code/cda:originalText/cda:reference">Error: In IHE PCC Product Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.2), the name and strength of the medication are specified in the elements under the
        &lt;manufacturedMaterial&gt; element. The &lt;originalText&gt; shall contain a &lt;reference&gt; whose URI value points to the generic name and strength of the medication, or just the generic name alone if strength is not relevant.</assert>
      </rule>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.2&quot;]">
        <report test="cda:id">Success: R2.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Prescription Item ID element "id" is present.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;]">
        <assert test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the basic pattern for reporting a problem uses the CDA &lt;observation&gt; element,
        setting the classCode='OBS' to represent that this is an observation of a problem, and the moodCode='EVN', to represent that this is an observation that has in fact taken place.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.28&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), &lt;templateId&gt; elements identify this under both IHE and CCD specifications
        (2.16.840.1.113883.10.20.1.28). This CCD template ID SHALL be included.</assert>
        <assert test="cda:id">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the specific Problem Entry observation being recorded must have an identifier (&lt;id&gt;) that shall be provided for tracking purposes. If the source EMR does
        not or cannot supply an intrinsic identifier, then a GUID shall be provided as the root, with no extension (e.g., &lt;id root='CE1215CD-69EC-4C7B-805F-569233C5E159'/&gt;). At least one identifier must be present, more than one may
        appear.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;text&gt; element is required and points to the text describing the problem being recorded; including any dates, comments, et cetera. The
        &lt;reference&gt; contains a URI in value attribute. This URI points to the free text description of the problem in the document that is being described.</assert>
        <assert test="cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), a clinical document normally records only those condition observation events that have been completed, not observations
        that are in any other state. Therefore, the &lt;statusCode&gt; shall always have code='completed' for Problem Entry.</assert>
        <report test="cda:effectiveTime/cda:width or cda:effectiveTime/cda:center">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), while CDA allows for multiple mechanisms to record this time interval (e.g. by low and high values, low
        and width, high and width, or center point and width), we are constraining Medical summaries (Problem Entry) to use only the low/high form.</report>
        <assert test="cda:effectiveTime/cda:low[@value or @nullFlavor]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the effectiveTime &lt;low&gt; value shall normally be present in Problem Entry. There are exceptions, such as for the
        case where the patient may be able to report that they had chicken pox, but are unsure when. In this case, the &lt;effectiveTime&gt; element shall have a &lt;low&gt; element with a nullFlavor attribute shall be present.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;value&gt; is the condition that was found. This element is required. While the value of Problem Entry code may be a coded
        or an un-coded string, the type is always a coded value (xsi:type='CD').</assert>
        <assert test="(cda:value[@code and @codeSystem]) or (not(cda:value[@code]) and not(cda:value[@codeSystem]))">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), if Problem Entry value is coded, the code and codeSystem attributes
        shall be present. If uncoded, all attributes other than xsi:type='CD' must be absent.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] | cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional severity entry relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:subjectOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a &lt;subjectOf&gt;
        element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Problem Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Health Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a
        &lt;sourceOf&gt; element.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Severity - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.55&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the templateId for CCD Severity (2.16.840.1.113883.10.20.1.55) must be present.</assert>
        <assert test="cda:code[@code=&quot;SEV&quot; and @codeSystem=&quot;2.16.840.1.113883.5.4&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the observation is of Severity, as indicated by the code element. This element is
        required. The code and codeSystem attributes shall be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the code attribute of &lt;statusCode&gt; for all Severity observations shall be completed. While the &lt;statusCode&gt;
        element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;] and (((cda:value[@codeSystem=&quot;2.16.840.1.113883.5.1063&quot; and (@code=&quot;H&quot; or @code=&quot;M&quot; or @code=&quot;L&quot;)]) ) or ( not(cda:value[@codeSystem]) ))">Error: In IHE PCC
        Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &gt;value&lt; element contains the level of severity. It is always represented using t'he CD datatype (xsi:type='CD'), even though the value may be a coded or uncoded string. If coded, it shall use
        the HL7 SeverityObservation vocabulary (codeSystem='2.16.840.1.113883.5.1063') containing three values (H, M, and L), representing high, moderate and low severity depending upon whether the severity is life threatening, presents noticeable
        adverse consequences, or is unlikely substantially effect the situation of the subject.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Status Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.57&quot;] and cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.50&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), CCD templateIds
        (2.16.840.1.113883.10.20.1.50 and 2.16.840.1.113883.10.20.1.57) for parents of Problem Status Observation shall exist.</assert>
        <assert test="cda:code[@code = &quot;33999-4&quot; and @codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the Problem Status Observation is of clinical status, as
        indicated by the &lt;code&gt; element. This element must be present. The code and codeSystem shall be recorded exactly.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element that points to the narrative text describing the clinical status. For
        CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing to the narrative section (see Linking Narrative and Coded Entries), rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt;
        element SHALL contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the code attribute of &lt;statusCode&gt; for all Problem Status observations shall be completed. While
        the &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot; and @codeSystem=&quot;2.16.840.1.113883.6.96&quot;] and cda:value[@code = &quot;55561003&quot; or @code = &quot;73425007&quot; or @code = &quot;90734009&quot; or @code = &quot;7087005&quot; or @code = &quot;255227004&quot; or @code = &quot;415684004&quot; or @code = &quot;410516002&quot; or @code = &quot;413322009&quot;]">
        Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;value&gt; element contains the clinical status. It is always represented using the CE datatype (xsi:type='CE'). It shall contain a code from the set of values
        from SNOMED CT listed in the profile.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Health Status - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.51&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the parent templateId (2.16.840.1.113883.10.20.1.51) for Health Status shall be present.</assert>
        <assert test="cda:code[@code=&quot;11323-3&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code element is required. The code and codeSystem attributes shall be
        recorded exactly as indicated. See: http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.1.2</assert>
        <assert test="cda:text">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing
        to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt; element shall contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code attribute of &lt;statusCode&gt; for all health status observations shall be completed. While the
        &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;value&gt; element contains the clinical status of the Health Status Observation. It is always represented using the CE
        datatype (xsi:type='CE').</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Comments - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.40&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the parent CCD templateId (2.16.840.1.113883.10.20.1.40) for Comments must be included.</assert>
        <assert test="cda:code[@code=&quot;48767-8&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code element for Comments is required. The code and codeSystem attributes shall
        be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code attribute of &lt;statusCode&gt; for all Comments shall be completed.</assert>
        <assert test="not(cda:author) or ( cda:author/cda:time and cda:author/cda:assignedAuthor/cda:id and cda:author/cda:assignedAuthor/cda:addr and cda:author/cda:assignedAuthor/cda:telecom and ( cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name or cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name))">
        Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the Comment may have an author. The time of the comment creation shall be recorded in the &lt;time&gt; element when the &lt;author&gt; element is present. The identifier of the author,
        and their address and telephone number must be present inside the &lt;id&gt;, &lt;addr&gt; and &lt;telecom&gt; elements when the &lt;author&gt; element is present. The author's and/or the organization's name must be present when the
        &lt;author&gt; element is present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.3-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Supply Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.3&quot;]">
        <assert test="./@classCode=&quot;SPLY&quot; and (./@moodCode=&quot;INT&quot; or ./@moodCode=&quot;EVN&quot;)">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), the moodCode attribute shall be INT to reflect that a medication has
        been prescribed, or EVN to indicate that the prescription has been filled.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.34&quot;]">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), the parent CCD template ID (2.16.840.1.113883.10.20.1.34) shall be present.</assert>
        <assert test="cda:id">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), each supply entry shall have an identifier to uniquely identify the supply entry.</assert>
        <assert test="not(cda:quantity) or cda:quantity[@value]">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), the value attribute of quantity shall be present if quantity exists.</assert>
        <assert test="not(cda:author) or cda:author/cda:time[@value or @nullFlavor]">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), the &lt;time&gt; element must be present if author exists to indicate when the author created the
        prescription. If this information is unknown, it shall be recorded by setting the nullFlavor attribute.</assert>
        <assert test="not(cda:author) or cda:author/cda:assignedAuthor">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), the &lt;assignedAuthor&gt; element shall be present if an author exists in Supply Entry, and identifies the
        author.</assert>
        <assert test="not(cda:author) or cda:author/cda:assignedAuthor/cda:id">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), an identifier element shall be present for an assigned author if an author exists in Supply Entry.</assert>
        <assert test="not(cda:author) or cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name or cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), an
        &lt;assignedPerson&gt;/&lt;name&gt; and/or &lt;representedOriganization&gt;/&lt;name&gt; element shall be present if an author exists in Supply Entry. This element shall identify the prescriber or their organization.</assert>
        <assert test="not(cda:performer) or not(./@moodCode=&quot;INT&quot;) or cda:performer/cda:time[@value or @nullFlavor]">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), the &lt;time&gt; element shall be present if there is a
        performer in Supply Entry to indicate when the prescription was filled (moodCode='EVN'). If this information is unknown, it shall be recorded by setting the nullFlavor attribute.</assert>
        <assert test="not(cda:performer) or cda:performer/cda:assignedEntity">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), the &lt;assignedEntity&gt; element shall be present in a Supply Entry that contains a performer, and
        identifies the filler of the prescription.</assert>
        <assert test="not(cda:performer) or cda:performer/cda:assignedEntity/cda:assignedPerson/cda:name or cda:performer/cda:assignedEntity/cda:representedOrganization/cda:name">Error: In IHE PCC Supply Entry (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), an
        assignedPerson/name and/or representedOriganization/name element shall be present under the assignedEntity if a performer exists.</assert>
        <assert test="not(cda:entryRelationship[@typeCode=&quot;SUBJ&quot;]) or cda:entryRelationship[@typeCode=&quot;SUBJ&quot;]/cda:act[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.3.1&quot;]">Error: In IHE PCC Supply Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.7.3), an entry relationship may be present to provide the fulfillment instructions. When present, this entry relationship shall contain a Medication Fulfillment Instructions entry.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.3.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Medication Fulfillment Instructions - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.3.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.43&quot;]">Error: In IHE PCC Medication Fulfillment Instructions (1.3.6.1.4.1.19376.1.5.3.1.4.3.1), the parent CCD template ID (2.16.840.1.113883.10.20.1.43) for Medication
        Fulfillment Instructions shall exist.</assert>
        <assert test="cda:code[@code=&quot;FINSTRUCT&quot; and @codeSystem=&quot;1.3.6.1.4.1.19376.1.5.3.2&quot; and @codeSystemName=&quot;IHEActCode&quot;]">Error: In IHE PCC Medication Fulfillment Instructions (1.3.6.1.4.1.19376.1.5.3.1.4.3.1), the
        code for Medication Fulfillment Instructions shall be recorded exactly as specified: &lt;code code='FINSTRUCT' codeSystem='1.3.6.1.4.1.19376.1.5.3.2' codeSystemName='IHEActCode' /&gt;.</assert>
        <assert test="cda:text/cda:reference">Error: The &lt;text&gt; element of Medication Fulfillment Instructions contains a free text representation of the instruction. For CDA this SHALL contain a provides a &lt;reference&gt; element to the link
        text of the comment in the narrative portion of the document. The comment itself is not the act being coded, so it appears in the &lt;text&gt; of the &lt;observation&gt;, not as part of the &lt;code&gt;.</assert>
        <assert test="cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Medication Fulfillment Instructions (1.3.6.1.4.1.19376.1.5.3.1.4.3.1), the code attribute of &lt;statusCode&gt; for all Medication Fulfillment Instriction comments must
        be completed.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.8-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC History of Past Illness Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.8&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC History of Past Illness Section (1.3.6.1.4.1.19376.1.5.3.1.3.8), the History of Past Illness can only be used on sections.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;11348-0&quot;]">Error: In IHE PCC History of Past Illness Section (1.3.6.1.4.1.19376.1.5.3.1.3.8), the section type code of a History of Past Illness must be 11348-0</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC History of Past Illness Section (1.3.6.1.4.1.19376.1.5.3.1.3.8), the section type code of a History of Past Illness must come from the LOINC code system
        (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.2&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC History of Past Illness Section (1.3.6.1.4.1.19376.1.5.3.1.3.8), a History of Past Illness must contain Problem Concern Entry.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Concern Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.2&quot;]">
        <assert test="cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.1&quot;]">Error: In IHE PCC Problem Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.2), the Problem Concern Entry has a template identifier of 1.3.6.1.4.1.19376.1.5.3.1.4.5.2,
        and is a subtype of the Concern Entry, and so must also conform to that specification, with the template identifier of 1.3.6.1.4.1.19376.1.5.3.1.4.5.1. These elements are required and shall be recorded exactly as indicated.</assert>
        <assert test="(cda:entryRelationship[@typeCode = &quot;SUBJ&quot;] and cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;] and cda:entryRelationship[@inversionInd=&quot;false&quot;]) or (cda:subjectOf[@typeCode = &quot;SUBJ&quot;] and cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;] and cda:subjectOf[@inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.2), the Problem Concern Entry shall contain one or more problem entries that conform to the Problem Entry template 1.3.6.1.4.1.19376.1.5.3.1.4.5. For CDA this SHALL be
        represented with the &lt;entryRelationship&gt; element. For HL7 Version 3 Messages, this SHALL be represented as a &lt;subjectOf&gt; element. The typeCode SHALL be SUBJ and inversionInd SHALL be false.</assert>
        <!-- R11.3 Problem onset time -->
        <assert test="cda:effectiveTime/cda:low">Error: R11.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem onset time element "effectiveTime\low" is required.</assert>
        <report test="cda:effectiveTime/cda:low">Success: R11.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem onset time element "effectiveTime\low" is present.</report>
      </rule>
      <!-- R11.3 Problem onset time -->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.2&quot;]/cda:effectiveTime/cda:low">
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R11.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem onset time element "effectiveTime\low" is required. The attribute 'value'
        of the element "effectiveTime" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor
        attribute shall be present.</assert>
      </rule>
      <!--cf.epSOS_Implementation_v0.2.20100702.doc -->
      <!--§9 Body Data elements -->
      <rule context="//cda:entry/cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/cda:entryRelationship[@typeCode='SUBJ']/cda:observation[cda:templateId/@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']">
        <!-- R11.1 Problem/diagnosis description -->
        <assert test="cda:value and (cda:value/@displayName or cda:value/@nullFlavor)">Error: R11.1 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis description element "value\@displayName" is required. The attribute 'displayName' shall be filled,
        otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:value">Success: R11.1 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis description element "value" is present.</report>
        <report test="cda:value/@displayName">Success: R11.1 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis description element "value" attribute's 'displayName' is filled.</report>
        <!-- R11.2 Problem/diagnosis Code -->
        <assert test="cda:value and (cda:value[@code='1.3.6.1.4.1.12559.11.10.1.3.1.42.5'] or cda:value/@nullFlavor)">Error: R11.2 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis Code element "value\@code" is required. The attribute 'code' shall
        be '1.3.6.1.4.1.12559.11.10.1.3.1.42.5', otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:value">Success: R11.2 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis Code element "value" is present.</report>
        <report test="cda:value[@code='1.3.6.1.4.1.12559.11.10.1.3.1.42.5']">Success: R11.2 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis Code element "value" attribute's 'code' is correctly set to the value
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.5'.</report>
      </rule>
      <rule context="//cda:entry/cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/effectiveTime[@xsi:type='IVL_TS']/cda:low">
        <!-- R7.3 Problem Onset Time (Optional) -->
        <assert test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$'))) or @nullFlavor">Error: R7.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem Onset Time element "low/@value" is Optional. If present, the attribute
        'value' shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor attribute shall be
        present.</assert>
        <report test="matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')">Success: R7.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem Onset Date element "low/@value" is present. The attribute 'value' is well filled.</report>
      </rule>
      <rule context="//cda:entry/cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/effectiveTime[@xsi:type='IVL_TS']/cda:high">
        <!-- R7.4 Problem End Date (Optional) -->
        <assert test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$'))) or @nullFlavor">Error: R7.4 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem End Date element "high/@value" is Optional. If present, the attributes
        'value' shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor attribute shall be
        present.</assert>
        <report test="matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')">Success: R7.4 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem End Date element "high/@value" is present. The attribute 'value' is well filled.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Concern Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.1&quot;]">
        <assert test="../cda:act">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the Concern Entry can only be used on acts.</assert>
        <assert test="../cda:act[@classCode=&quot;ACT&quot;] and ../cda:act[@moodCode=&quot;EVN&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), all concerns reflect the act of recording (&lt;act classCode='ACT'&gt;) the event
        (moodCode='EVN') of being concerned about a problem, allergy or other issue about the patient condition.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.27&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the required template identifiers indicates this Concern Entry conforms to the Concern Content
        module. This content module inherits constraints from the HL7 CCD Template for problem acts, and so also includes that template identifier (2.16.840.1.113883.10.20.1.27).</assert>
        <assert test="cda:id">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the Concern Entry requires id element.</assert>
        <assert test="cda:code[@nullFlavor=&quot;NA&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the code is not applicable to a concern act (Concern Entry), and so shall be recorded as &lt;code
        nullFlavor='NA'/&gt;.</assert>
        <assert test="cda:statusCode[@code=&quot;active&quot; or @code=&quot;suspended&quot; or @code=&quot;aborted&quot; or @code=&quot;completed&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the statusCode associated with
        any concern must be one of the following values: active, suspended, aborted or completed.</assert>
        <assert test="cda:effectiveTime/cda:low">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the &lt;effectiveTime&gt; element records the starting and ending times during which the concern (Concern Entry) was active. The
        &lt;low&gt; element shall be present.</assert>
        <assert test="(cda:statusCode[@code=&quot;completed&quot; or @code=&quot;aborted&quot;] and cda:effectiveTime/cda:high) or (cda:statusCode[@code=&quot;active&quot; or @code=&quot;suspended&quot;] and not(cda:effectiveTime/cda:high))">Error: In
        IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the &lt;effectiveTime&gt; element records the starting and ending times during the concern (Concern Entry). The &lt;high&gt; element shall be present for concerns in the completed or
        aborted state, and shall not be present otherwise.</assert>
        <assert test="(cda:entryRelationship[@typeCode=&quot;SUBJ&quot;] and cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot; or @root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6&quot;]) or (cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;false&quot;] and cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot; or @root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6&quot;])">
        Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), each concern (Concern Entry) is about one or more related problems or allergies. This entry shall contain one or more problem or allergy entries that conform to the specification
        in section Problem Entry or Allergies and Intolerances. This is how a series of related observations can be grouped as a single concern. For CDA this SHALL be represented with the &lt;entryRelationship&gt; element. For HL7 Version 3 Messages,
        this SHALL be represented as a &lt;sourceOf&gt; element. The typeCode SHALL be SUBJ for both HL7 Version 3 and CDA. HL7 Version 3 additionally requires that inversionInd SHALL be false.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;]">
        <assert test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the basic pattern for reporting a problem uses the CDA &lt;observation&gt; element,
        setting the classCode='OBS' to represent that this is an observation of a problem, and the moodCode='EVN', to represent that this is an observation that has in fact taken place.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.28&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), &lt;templateId&gt; elements identify this under both IHE and CCD specifications
        (2.16.840.1.113883.10.20.1.28). This CCD template ID SHALL be included.</assert>
        <assert test="cda:id">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the specific Problem Entry observation being recorded must have an identifier (&lt;id&gt;) that shall be provided for tracking purposes. If the source EMR does
        not or cannot supply an intrinsic identifier, then a GUID shall be provided as the root, with no extension (e.g., &lt;id root='CE1215CD-69EC-4C7B-805F-569233C5E159'/&gt;). At least one identifier must be present, more than one may
        appear.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;text&gt; element is required and points to the text describing the problem being recorded; including any dates, comments, et cetera. The
        &lt;reference&gt; contains a URI in value attribute. This URI points to the free text description of the problem in the document that is being described.</assert>
        <assert test="cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), a clinical document normally records only those condition observation events that have been completed, not observations
        that are in any other state. Therefore, the &lt;statusCode&gt; shall always have code='completed' for Problem Entry.</assert>
        <report test="cda:effectiveTime/cda:width or cda:effectiveTime/cda:center">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), while CDA allows for multiple mechanisms to record this time interval (e.g. by low and high values, low
        and width, high and width, or center point and width), we are constraining Medical summaries (Problem Entry) to use only the low/high form.</report>
        <assert test="cda:effectiveTime/cda:low[@value or @nullFlavor]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the effectiveTime &lt;low&gt; value shall normally be present in Problem Entry. There are exceptions, such as for the
        case where the patient may be able to report that they had chicken pox, but are unsure when. In this case, the &lt;effectiveTime&gt; element shall have a &lt;low&gt; element with a nullFlavor attribute shall be present.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;value&gt; is the condition that was found. This element is required. While the value of Problem Entry code may be a coded
        or an un-coded string, the type is always a coded value (xsi:type='CD').</assert>
        <assert test="(cda:value[@code and @codeSystem]) or (not(cda:value[@code]) and not(cda:value[@codeSystem]))">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), if Problem Entry value is coded, the code and codeSystem attributes
        shall be present. If uncoded, all attributes other than xsi:type='CD' must be absent.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] | cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional severity entry relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:subjectOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a &lt;subjectOf&gt;
        element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Problem Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Health Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a
        &lt;sourceOf&gt; element.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Severity - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.55&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the templateId for CCD Severity (2.16.840.1.113883.10.20.1.55) must be present.</assert>
        <assert test="cda:code[@code=&quot;SEV&quot; and @codeSystem=&quot;2.16.840.1.113883.5.4&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the observation is of Severity, as indicated by the code element. This element is
        required. The code and codeSystem attributes shall be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the code attribute of &lt;statusCode&gt; for all Severity observations shall be completed. While the &lt;statusCode&gt;
        element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;] and (((cda:value[@codeSystem=&quot;2.16.840.1.113883.5.1063&quot; and (@code=&quot;H&quot; or @code=&quot;M&quot; or @code=&quot;L&quot;)]) ) or ( not(cda:value[@codeSystem]) ))">Error: In IHE PCC
        Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &gt;value&lt; element contains the level of severity. It is always represented using t'he CD datatype (xsi:type='CD'), even though the value may be a coded or uncoded string. If coded, it shall use
        the HL7 SeverityObservation vocabulary (codeSystem='2.16.840.1.113883.5.1063') containing three values (H, M, and L), representing high, moderate and low severity depending upon whether the severity is life threatening, presents noticeable
        adverse consequences, or is unlikely substantially effect the situation of the subject.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Status Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.57&quot;] and cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.50&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), CCD templateIds
        (2.16.840.1.113883.10.20.1.50 and 2.16.840.1.113883.10.20.1.57) for parents of Problem Status Observation shall exist.</assert>
        <assert test="cda:code[@code = &quot;33999-4&quot; and @codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the Problem Status Observation is of clinical status, as
        indicated by the &lt;code&gt; element. This element must be present. The code and codeSystem shall be recorded exactly.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element that points to the narrative text describing the clinical status. For
        CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing to the narrative section (see Linking Narrative and Coded Entries), rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt;
        element SHALL contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the code attribute of &lt;statusCode&gt; for all Problem Status observations shall be completed. While
        the &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot; and @codeSystem=&quot;2.16.840.1.113883.6.96&quot;] and cda:value[@code = &quot;55561003&quot; or @code = &quot;73425007&quot; or @code = &quot;90734009&quot; or @code = &quot;7087005&quot; or @code = &quot;255227004&quot; or @code = &quot;415684004&quot; or @code = &quot;410516002&quot; or @code = &quot;413322009&quot;]">
        Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;value&gt; element contains the clinical status. It is always represented using the CE datatype (xsi:type='CE'). It shall contain a code from the set of values
        from SNOMED CT listed in the profile.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Health Status - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.51&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the parent templateId (2.16.840.1.113883.10.20.1.51) for Health Status shall be present.</assert>
        <assert test="cda:code[@code=&quot;11323-3&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code element is required. The code and codeSystem attributes shall be
        recorded exactly as indicated. See: http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.1.2</assert>
        <assert test="cda:text">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing
        to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt; element shall contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code attribute of &lt;statusCode&gt; for all health status observations shall be completed. While the
        &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;value&gt; element contains the clinical status of the Health Status Observation. It is always represented using the CE
        datatype (xsi:type='CE').</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Comments - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.40&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the parent CCD templateId (2.16.840.1.113883.10.20.1.40) for Comments must be included.</assert>
        <assert test="cda:code[@code=&quot;48767-8&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code element for Comments is required. The code and codeSystem attributes shall
        be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code attribute of &lt;statusCode&gt; for all Comments shall be completed.</assert>
        <assert test="not(cda:author) or ( cda:author/cda:time and cda:author/cda:assignedAuthor/cda:id and cda:author/cda:assignedAuthor/cda:addr and cda:author/cda:assignedAuthor/cda:telecom and ( cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name or cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name))">
        Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the Comment may have an author. The time of the comment creation shall be recorded in the &lt;time&gt; element when the &lt;author&gt; element is present. The identifier of the author,
        and their address and telephone number must be present inside the &lt;id&gt;, &lt;addr&gt; and &lt;telecom&gt; elements when the &lt;author&gt; element is present. The author's and/or the organization's name must be present when the
        &lt;author&gt; element is present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.12-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Coded List of Surgeries Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.12&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
    object -->
        <assert test="../cda:section">Error: In IHE PCC Coded List of Surgeries Section: 1.3.6.1.4.1.19376.1.5.3.1.3.12, the Coded List of Surgeries can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.11&quot;]">Error: In IHE PCC Coded List of Surgeries Section: 1.3.6.1.4.1.19376.1.5.3.1.3.12, the parent template identifier for Coded List of Surgeries is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;47519-4&quot;]">Error: In IHE PCC Coded List of Surgeries Section: 1.3.6.1.4.1.19376.1.5.3.1.3.12, the section type code of a Coded List of Surgeries must be 47519-4</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Coded List of Surgeries Section: 1.3.6.1.4.1.19376.1.5.3.1.3.12, the section type code must come from the LOINC code system
        (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.19&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC Coded List of Surgeries Section: 1.3.6.1.4.1.19376.1.5.3.1.3.12, a Coded List of Surgeries must contain Procedure Entry.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.11-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC List of Surgeries Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.11&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC List of Surgeries Section (1.3.6.1.4.1.19376.1.5.3.1.3.11), the List of Surgeries can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.12&quot;]">Error: In IHE PCC List of Surgeries Section (1.3.6.1.4.1.19376.1.5.3.1.3.11), the parent template identifier for List of Surgeries is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;47519-4&quot;]">Error: In IHE PCC List of Surgeries Section (1.3.6.1.4.1.19376.1.5.3.1.3.11), the section type code of a List of Surgeries must be 47519-4</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC List of Surgeries Section (1.3.6.1.4.1.19376.1.5.3.1.3.11), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.19-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Procedure Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.19&quot;]">
        <assert test="self::cda:procedure[@classCode=&quot;PROC&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), the classCode for Procedure Entry shall be "PROC" and the element is procedure.</assert>
        <assert test="not(./@moodCode=&quot;EVN&quot;) or cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.29&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), when the Procedure Entry is in event mood (moodCode='EVN'), this
        entry conforms to the CCD template 2.16.840.1.113883.10.20.1.29.</assert>
        <assert test="not(./@moodCode=&quot;INT&quot;) or cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.25&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), when the Procedure Entry is in intent mood (moodCode='INT'),
        this entry conforms to the CCD template 2.16.840.1.113883.10.20.1.25.</assert>
        <assert test="cda:id">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), a Procedure Entry shall contain an id element.</assert>
        <assert test="cda:code">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), a Procedure Entry shall contain a code element.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), a Procedure Entry text element shall contain a reference to the narrative text describing the procedure.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot; or @code = &quot;active&quot; or @code = &quot;aborted&quot; or @code = &quot;cancelled&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), the &lt;statusCode&gt;
        element shall be present when used to describe a Procedure Event. It shall have the value 'completed' for procedures that have been completed, and 'active' for procedures that are still in progress. Procedures that were stopped prior to
        completion shall use the value 'aborted', and procedures that were cancelled before being started shall use the value 'cancelled'.</assert>
        <assert test="not(./@moodCode=&quot;INT&quot;) or (cda:effectiveTime or cda:priorityCode)">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), in Procedure Entry the priorityCode element shall be present in INT mood when
        effectiveTime is not provided, it may be present in other moods. It indicates the priority of the procedure.</assert>
        <!--cf.epSOS_Implementation_v0.2.20100702.doc -->
        <!--$7.2 Body Data elements -->
        <!-- R10.1 Procedure description  -->
        <assert test="cda:code and ((cda:code/@displayName and cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.10']) or cda:code/@nullFlavor)">Error: R10.1 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure description element "code" is required. The
        attribute 'displayName' shall be filled and 'codeSystem' shall take the value '1.3.6.1.4.1.12559.11.10.1.3.1.42.10', otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:code">Success: R10.1 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure description element "code" is present.</report>
        <report test="cda:code/@displayName and cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.10']">Success: R10.1 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure description element "code" attribute's 'displayName' is filled and 'codeSystem' is
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.10'.</report>
        <!-- R10.2 Procedure code  -->
        <assert test="cda:code and (cda:code/@code or cda:code/@nullFlavor)">Error: R10.2 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure code element "code" is required. The attribute 'displayName' shall be filled, otherwise the nullFlavor attribute
        shall be present.</assert>
        <report test="cda:code">Success: R10.2 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure code element "code" is present.</report>
        <report test="cda:code/@displayName">Success: R10.2 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure code element "code" attribute's 'displayName' is filled.</report>
        <!-- R10.3 Procedure date  -->
        <assert test="cda:effectiveTime">Error: R10.3 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure date element "effectiveTime" is required.</assert>
        <report test="cda:effectiveTime">Success: R10.3 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure date element "effectiveTime" is present.</report>
      </rule>
      <!-- R10.3 Procedure date  -->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.19&quot;]/cda:effectiveTime">
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R10.3 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure date element "effectiveTime/@value" is required. The attribute 'value'
        of the element "effectiveTime" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor
        attribute shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Internal References - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.4.1&quot;]">
        <assert test="self::cda:act">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), for compatibility with the Clinical Statement model the Internal Reference shall always use the &lt;act&gt; class</assert>
        <assert test="cda:id[@root and @extension]">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the id element shall be present in Internal Reference. The root and extension attributes shall identify an element defined
        elsewhere in the same document.</assert>
        <assert test="cda:code">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the code element of Internal Reference shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC External References - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.4&quot;]">
        <assert test="self::cda:act[@classCode=&quot;ACT&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC External References (1.3.6.1.4.1.19376.1.5.3.1.4.4), the External Reference is an act that refers to documentation of an act
        (classCode='ACT'), that previously occurred (moodCode='EVN').</assert>
        <assert test="cda:id">Error: In IHE PCC External References (1.3.6.1.4.1.19376.1.5.3.1.4.4), the reference is an act of itself, and must be uniquely identified with an id element.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.6-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Active Problems Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.6&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Active Problems Section (1.3.6.1.4.1.19376.1.5.3.1.3.6), the Active Problems can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.11&quot;]">Error: In IHE PCC Active Problems Section (1.3.6.1.4.1.19376.1.5.3.1.3.6), the parent template identifier (2.16.840.1.113883.10.20.1.11) for Active Problems is not
        present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;11450-4&quot;]">Error: In IHE PCC Active Problems Section (1.3.6.1.4.1.19376.1.5.3.1.3.6), the section type code of Active Problems must be 11450-4</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Active Problems Section (1.3.6.1.4.1.19376.1.5.3.1.3.6), the section type code of Active Problems must come from the LOINC code system
        (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.2&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC Active Problems Section (1.3.6.1.4.1.19376.1.5.3.1.3.6), Active Problems must contain Problem Concern Entry.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Concern Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.2&quot;]">
        <assert test="cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.1&quot;]">Error: In IHE PCC Problem Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.2), the Problem Concern Entry has a template identifier of 1.3.6.1.4.1.19376.1.5.3.1.4.5.2,
        and is a subtype of the Concern Entry, and so must also conform to that specification, with the template identifier of 1.3.6.1.4.1.19376.1.5.3.1.4.5.1. These elements are required and shall be recorded exactly as indicated.</assert>
        <assert test="(cda:entryRelationship[@typeCode = &quot;SUBJ&quot;] and cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;] and cda:entryRelationship[@inversionInd=&quot;false&quot;]) or (cda:subjectOf[@typeCode = &quot;SUBJ&quot;] and cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;] and cda:subjectOf[@inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.2), the Problem Concern Entry shall contain one or more problem entries that conform to the Problem Entry template 1.3.6.1.4.1.19376.1.5.3.1.4.5. For CDA this SHALL be
        represented with the &lt;entryRelationship&gt; element. For HL7 Version 3 Messages, this SHALL be represented as a &lt;subjectOf&gt; element. The typeCode SHALL be SUBJ and inversionInd SHALL be false.</assert>
        <!-- R11.3 Problem onset time -->
        <assert test="cda:effectiveTime/cda:low">Error: R11.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem onset time element "effectiveTime\low" is required.</assert>
        <report test="cda:effectiveTime/cda:low">Success: R11.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem onset time element "effectiveTime\low" is present.</report>
      </rule>
      <!-- R11.3 Problem onset time -->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.2&quot;]/cda:effectiveTime/cda:low">
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R11.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem onset time element "effectiveTime\low" is required. The attribute 'value'
        of the element "effectiveTime" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor
        attribute shall be present.</assert>
      </rule>
      <!--cf.epSOS_Implementation_v0.2.20100702.doc -->
      <!--§9 Body Data elements -->
      <rule context="//cda:entry/cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/cda:entryRelationship[@typeCode='SUBJ']/cda:observation[cda:templateId/@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']">
        <!-- R11.1 Problem/diagnosis description -->
        <assert test="cda:value and (cda:value/@displayName or cda:value/@nullFlavor)">Error: R11.1 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis description element "value\@displayName" is required. The attribute 'displayName' shall be filled,
        otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:value">Success: R11.1 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis description element "value" is present.</report>
        <report test="cda:value/@displayName">Success: R11.1 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis description element "value" attribute's 'displayName' is filled.</report>
        <!-- R11.2 Problem/diagnosis Code -->
        <assert test="cda:value and (cda:value[@code='1.3.6.1.4.1.12559.11.10.1.3.1.42.5'] or cda:value/@nullFlavor)">Error: R11.2 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis Code element "value\@code" is required. The attribute 'code' shall
        be '1.3.6.1.4.1.12559.11.10.1.3.1.42.5', otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:value">Success: R11.2 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis Code element "value" is present.</report>
        <report test="cda:value[@code='1.3.6.1.4.1.12559.11.10.1.3.1.42.5']">Success: R11.2 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem/diagnosis Code element "value" attribute's 'code' is correctly set to the value
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.5'.</report>
      </rule>
      <rule context="//cda:entry/cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/effectiveTime[@xsi:type='IVL_TS']/cda:low">
        <!-- R7.3 Problem Onset Time (Optional) -->
        <assert test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$'))) or @nullFlavor">Error: R7.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem Onset Time element "low/@value" is Optional. If present, the attribute
        'value' shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor attribute shall be
        present.</assert>
        <report test="matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')">Success: R7.3 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem Onset Date element "low/@value" is present. The attribute 'value' is well filled.</report>
      </rule>
      <rule context="//cda:entry/cda:act[cda:templateId/@root='2.16.840.1.113883.10.20.1.27']/effectiveTime[@xsi:type='IVL_TS']/cda:high">
        <!-- R7.4 Problem End Date (Optional) -->
        <assert test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$'))) or @nullFlavor">Error: R7.4 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem End Date element "high/@value" is Optional. If present, the attributes
        'value' shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor attribute shall be
        present.</assert>
        <report test="matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')">Success: R7.4 (1.3.6.1.4.1.19376.1.5.3.1.4.5.2) The Problem End Date element "high/@value" is present. The attribute 'value' is well filled.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Concern Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5.1&quot;]">
        <assert test="../cda:act">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the Concern Entry can only be used on acts.</assert>
        <assert test="../cda:act[@classCode=&quot;ACT&quot;] and ../cda:act[@moodCode=&quot;EVN&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), all concerns reflect the act of recording (&lt;act classCode='ACT'&gt;) the event
        (moodCode='EVN') of being concerned about a problem, allergy or other issue about the patient condition.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.27&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the required template identifiers indicates this Concern Entry conforms to the Concern Content
        module. This content module inherits constraints from the HL7 CCD Template for problem acts, and so also includes that template identifier (2.16.840.1.113883.10.20.1.27).</assert>
        <assert test="cda:id">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the Concern Entry requires id element.</assert>
        <assert test="cda:code[@nullFlavor=&quot;NA&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the code is not applicable to a concern act (Concern Entry), and so shall be recorded as &lt;code
        nullFlavor='NA'/&gt;.</assert>
        <assert test="cda:statusCode[@code=&quot;active&quot; or @code=&quot;suspended&quot; or @code=&quot;aborted&quot; or @code=&quot;completed&quot;]">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the statusCode associated with
        any concern must be one of the following values: active, suspended, aborted or completed.</assert>
        <assert test="cda:effectiveTime/cda:low">Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the &lt;effectiveTime&gt; element records the starting and ending times during which the concern (Concern Entry) was active. The
        &lt;low&gt; element shall be present.</assert>
        <assert test="(cda:statusCode[@code=&quot;completed&quot; or @code=&quot;aborted&quot;] and cda:effectiveTime/cda:high) or (cda:statusCode[@code=&quot;active&quot; or @code=&quot;suspended&quot;] and not(cda:effectiveTime/cda:high))">Error: In
        IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), the &lt;effectiveTime&gt; element records the starting and ending times during the concern (Concern Entry). The &lt;high&gt; element shall be present for concerns in the completed or
        aborted state, and shall not be present otherwise.</assert>
        <assert test="(cda:entryRelationship[@typeCode=&quot;SUBJ&quot;] and cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot; or @root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6&quot;]) or (cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;false&quot;] and cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot; or @root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.6&quot;])">
        Error: In IHE PCC Concern Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5.1), each concern (Concern Entry) is about one or more related problems or allergies. This entry shall contain one or more problem or allergy entries that conform to the specification
        in section Problem Entry or Allergies and Intolerances. This is how a series of related observations can be grouped as a single concern. For CDA this SHALL be represented with the &lt;entryRelationship&gt; element. For HL7 Version 3 Messages,
        this SHALL be represented as a &lt;sourceOf&gt; element. The typeCode SHALL be SUBJ for both HL7 Version 3 and CDA. HL7 Version 3 additionally requires that inversionInd SHALL be false.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.5-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.5&quot;]">
        <assert test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the basic pattern for reporting a problem uses the CDA &lt;observation&gt; element,
        setting the classCode='OBS' to represent that this is an observation of a problem, and the moodCode='EVN', to represent that this is an observation that has in fact taken place.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.28&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), &lt;templateId&gt; elements identify this under both IHE and CCD specifications
        (2.16.840.1.113883.10.20.1.28). This CCD template ID SHALL be included.</assert>
        <assert test="cda:id">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the specific Problem Entry observation being recorded must have an identifier (&lt;id&gt;) that shall be provided for tracking purposes. If the source EMR does
        not or cannot supply an intrinsic identifier, then a GUID shall be provided as the root, with no extension (e.g., &lt;id root='CE1215CD-69EC-4C7B-805F-569233C5E159'/&gt;). At least one identifier must be present, more than one may
        appear.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;text&gt; element is required and points to the text describing the problem being recorded; including any dates, comments, et cetera. The
        &lt;reference&gt; contains a URI in value attribute. This URI points to the free text description of the problem in the document that is being described.</assert>
        <assert test="cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), a clinical document normally records only those condition observation events that have been completed, not observations
        that are in any other state. Therefore, the &lt;statusCode&gt; shall always have code='completed' for Problem Entry.</assert>
        <report test="cda:effectiveTime/cda:width or cda:effectiveTime/cda:center">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), while CDA allows for multiple mechanisms to record this time interval (e.g. by low and high values, low
        and width, high and width, or center point and width), we are constraining Medical summaries (Problem Entry) to use only the low/high form.</report>
        <assert test="cda:effectiveTime/cda:low[@value or @nullFlavor]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the effectiveTime &lt;low&gt; value shall normally be present in Problem Entry. There are exceptions, such as for the
        case where the patient may be able to report that they had chicken pox, but are unsure when. In this case, the &lt;effectiveTime&gt; element shall have a &lt;low&gt; element with a nullFlavor attribute shall be present.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;]">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), the &lt;value&gt; is the condition that was found. This element is required. While the value of Problem Entry code may be a coded
        or an un-coded string, the type is always a coded value (xsi:type='CD').</assert>
        <assert test="(cda:value[@code and @codeSystem]) or (not(cda:value[@code]) and not(cda:value[@codeSystem]))">Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), if Problem Entry value is coded, the code and codeSystem attributes
        shall be present. If uncoded, all attributes other than xsi:type='CD' must be absent.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] | cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional severity entry relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]) or (cda:subjectOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;] and cda:subjectOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the severity of the problem. When present, this &lt;entryRelationship&gt; element shall
        contain a severity observation conforming to the Severity entry template (1.3.6.1.4.1.19376.1.5.3.1.4.1). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a &lt;subjectOf&gt;
        element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Problem Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; element may be present in Problem Entry indicating the clinical status of the problem, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Problem Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.1). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="count(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] | cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) &lt;= 1">Error: In IHE PCC Problem Entry
        (1.3.6.1.4.1.19376.1.5.3.1.4.5), only one optional Health Status Observation relationship is allowed.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:entryRelationship[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For CDA this SHALL
        be represented with the &lt;entryRelationship&gt; element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;] and cda:sourceOf[@typeCode=&quot;REFR&quot; and @inversionInd=&quot;false&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), an optional &lt;entryRelationship&gt; may be present in Problem Entry referencing the health status of the patient, e.g., resolved, in remission, active. When present, this
        &lt;entryRelationship&gt; element shall contain a clinical status observation conforming to the Health Status Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.1.2). The typeCode SHALL be REFR and inversionInd SHALL be false. For HL7 Version 3
        Messages, this SHALL be represented as a &lt;sourceOf&gt; element.</assert>
        <assert test="not(cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:entryRelationship/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:entryRelationship[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For CDA this SHALL be represented with the &lt;entryRelationship&gt;
        element.</assert>
        <assert test="not(cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]) or (cda:sourceOf/*/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;] and cda:sourceOf[@typeCode=&quot;SUBJ&quot; and @inversionInd=&quot;true&quot;])">
        Error: In IHE PCC Problem Entry (1.3.6.1.4.1.19376.1.5.3.1.4.5), one or more optional &lt;entryRelationship&gt; elements may be present providing an additional comments (annotations) for the condition. When present, this &lt;entryRelationship&gt;
        element shall contain a comment observation conforming to the Comment entry template (1.3.6.1.4.1.19376.1.5.3.1.4.2). The typeCode SHALL be SUBJ and inversionInd SHALL be true. For HL7 Version 3 Messages, this SHALL be represented as a
        &lt;sourceOf&gt; element.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Severity - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.55&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the templateId for CCD Severity (2.16.840.1.113883.10.20.1.55) must be present.</assert>
        <assert test="cda:code[@code=&quot;SEV&quot; and @codeSystem=&quot;2.16.840.1.113883.5.4&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the observation is of Severity, as indicated by the code element. This element is
        required. The code and codeSystem attributes shall be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the code attribute of &lt;statusCode&gt; for all Severity observations shall be completed. While the &lt;statusCode&gt;
        element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CD&quot;] and (((cda:value[@codeSystem=&quot;2.16.840.1.113883.5.1063&quot; and (@code=&quot;H&quot; or @code=&quot;M&quot; or @code=&quot;L&quot;)]) ) or ( not(cda:value[@codeSystem]) ))">Error: In IHE PCC
        Severity (1.3.6.1.4.1.19376.1.5.3.1.4.1), the &gt;value&lt; element contains the level of severity. It is always represented using t'he CD datatype (xsi:type='CD'), even though the value may be a coded or uncoded string. If coded, it shall use
        the HL7 SeverityObservation vocabulary (codeSystem='2.16.840.1.113883.5.1063') containing three values (H, M, and L), representing high, moderate and low severity depending upon whether the severity is life threatening, presents noticeable
        adverse consequences, or is unlikely substantially effect the situation of the subject.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Problem Status Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.57&quot;] and cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.50&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), CCD templateIds
        (2.16.840.1.113883.10.20.1.50 and 2.16.840.1.113883.10.20.1.57) for parents of Problem Status Observation shall exist.</assert>
        <assert test="cda:code[@code = &quot;33999-4&quot; and @codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the Problem Status Observation is of clinical status, as
        indicated by the &lt;code&gt; element. This element must be present. The code and codeSystem shall be recorded exactly.</assert>
        <assert test="cda:text">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;observation&gt; element shall contain a &lt;text&gt; element that points to the narrative text describing the clinical status. For
        CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing to the narrative section (see Linking Narrative and Coded Entries), rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt;
        element SHALL contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the code attribute of &lt;statusCode&gt; for all Problem Status observations shall be completed. While
        the &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot; and @codeSystem=&quot;2.16.840.1.113883.6.96&quot;] and cda:value[@code = &quot;55561003&quot; or @code = &quot;73425007&quot; or @code = &quot;90734009&quot; or @code = &quot;7087005&quot; or @code = &quot;255227004&quot; or @code = &quot;415684004&quot; or @code = &quot;410516002&quot; or @code = &quot;413322009&quot;]">
        Error: In IHE PCC Problem Status Observation (1.3.6.1.4.1.19376.1.5.3.1.4.1.1), the &lt;value&gt; element contains the clinical status. It is always represented using the CE datatype (xsi:type='CE'). It shall contain a code from the set of values
        from SNOMED CT listed in the profile.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.1.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Health Status - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.1.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.51&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the parent templateId (2.16.840.1.113883.10.20.1.51) for Health Status shall be present.</assert>
        <assert test="cda:code[@code=&quot;11323-3&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code element is required. The code and codeSystem attributes shall be
        recorded exactly as indicated. See: http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.1.2</assert>
        <assert test="cda:text">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element pointing
        to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity. For HL7 Version 3 Messages, the &lt;text&gt; element shall contain the full narrative text.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the code attribute of &lt;statusCode&gt; for all health status observations shall be completed. While the
        &lt;statusCode&gt; element is required in all acts to record the status of the act, the only sensible value of this element in this context is completed.</assert>
        <assert test="cda:value[@xsi:type=&quot;CE&quot;]">Error: In IHE PCC Health Status (1.3.6.1.4.1.19376.1.5.3.1.4.1.2), the &lt;value&gt; element contains the clinical status of the Health Status Observation. It is always represented using the CE
        datatype (xsi:type='CE').</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Comments - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.2&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.40&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the parent CCD templateId (2.16.840.1.113883.10.20.1.40) for Comments must be included.</assert>
        <assert test="cda:code[@code=&quot;48767-8&quot; and @codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code element for Comments is required. The code and codeSystem attributes shall
        be recorded exactly as indicated.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the &lt;observation&gt; element shall contain a &lt;text&gt; element. For CDA, the &lt;text&gt; elements shall contain a &lt;reference&gt; element
        pointing to the narrative where the severity is recorded, rather than duplicate text to avoid ambiguity.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the code attribute of &lt;statusCode&gt; for all Comments shall be completed.</assert>
        <assert test="not(cda:author) or ( cda:author/cda:time and cda:author/cda:assignedAuthor/cda:id and cda:author/cda:assignedAuthor/cda:addr and cda:author/cda:assignedAuthor/cda:telecom and ( cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name or cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name))">
        Error: In IHE PCC Comments (1.3.6.1.4.1.19376.1.5.3.1.4.2), the Comment may have an author. The time of the comment creation shall be recorded in the &lt;time&gt; element when the &lt;author&gt; element is present. The identifier of the author,
        and their address and telephone number must be present inside the &lt;id&gt;, &lt;addr&gt; and &lt;telecom&gt; elements when the &lt;author&gt; element is present. The author's and/or the organization's name must be present when the
        &lt;author&gt; element is present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC History of Present Illness Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.4&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC History of Present Illness Section (1.3.6.1.4.1.19376.1.5.3.1.3.4), the History of Present Illness can only be used on sections.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;10164-2&quot;]">Error: In IHE PCC History of Present Illness Section (1.3.6.1.4.1.19376.1.5.3.1.3.4), the section type code of a History of Present Illness must be 10164-2</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC History of Present Illness Section (1.3.6.1.4.1.19376.1.5.3.1.3.4), the section type code must come from the LOINC code system
        (2.16.840.1.113883.6.1).</assert>
        <!--cf.epSOS_Implementation_v0.2.20100702.doc -->
        <!--$7.2 Body Data elements -->
        <!-- R11.1 Problem/diagnosis description -->
        <assert test="cda:text and ((every $i in cda:text satisfies string-length(normalize-space($i)) &gt; 0) or cda:text/@nullFlavor)">Error: R11.1 (1.3.6.1.4.1.19376.1.5.3.1.3.4) The Problem/diagnosis description element "text" is required and shall
        be filled, otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:text">Success: R11.1 (1.3.6.1.4.1.19376.1.5.3.1.3.4) The Problem/diagnosis description element "text" is present.</report>
        <report test="(every $i in cda:text satisfies string-length(normalize-space($i)) &gt; 0)">Success: R11.1 (1.3.6.1.4.1.19376.1.5.3.1.3.4) The Problem/diagnosis description element "text" is filled.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.2.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <!-- <title>epSOS Medical Devices Coded Section Specification - errors validation phase</title>-->
      <rule context="//cda:section[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.4']">
        <!-- Verify the document type code -->
        <assert test="cda:code[@code = '46264-8']">Error: (cf.&#194;&#167;13.1.15 epSOS_Implementation_v0.2.20100702.doc) The Medical Devices Coded Section type code must be set to the value "46264-8" which is the code corresponding to "HISTORY OF
        MEDICAL DEVICE USE" in the loinc code system.</assert>
        <report test="cda:code[@code = '46264-8']">Success: (cf.&#194;&#167;13.1.15 epSOS_Implementation_v0.2.20100702.doc) The Medical Devices Coded Section type code must be set to the value "46264-8" which is the code corresponding to "HISTORY OF
        MEDICAL DEVICE USE" in the loinc code system.</report>
        <!-- Verify that the correct code system is used-->
        <assert test="cda:code[@codeSystem = '2.16.840.1.113883.6.1']">Error: (cf.&#194;&#167;13.1.15 epSOS_Implementation_v0.2.20100702.doc) The document type code of a Medical Documents must be "2.16.840.1.113883.6.1" corresponding to the LOINC Code
        system OID</assert>
        <report test="cda:code[@codeSystem = '2.16.840.1.113883.6.1']">Success: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The document type code of a Medical Documents is correctly set to "2.16.840.1.113883.6.1" corresponding to the
        LOINC Code system OID</report>
        <!-- Verify that the parents templateId are present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.11383.10.20.1.7&quot;]">Error: (cf.&#194;&#167;13.1.15 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (2.16.840.1.11383.10.20.1.7) for The Medical Devices Coded Section
        is not present.</assert>
        <report test="cda:templateId[@root=&quot;2.16.840.1.11383.10.20.1.7&quot;]">Success: (cf.&#194;&#167;13.1.15 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (2.16.840.1.11383.10.20.1.7) for The Medical Devices Coded Section
        is present.</report>
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5&quot;]">Error: (cf.&#194;&#167;13.1.15 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5) for The Medical Devices
        Coded Section is not present.</assert>
        <report test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5&quot;]">Success: (cf.&#194;&#167;13.1.15 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5) for The Medical Devices
        Coded Section is present.</report>
        <!-- Verify that the  Medical Devices Entry Content Module templateId is present. -->
        <assert test="cda:entry/cda:supply[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.5']">Error: (cf.&#194;&#167;13.1.15 epSOS_Implementation_v0.2.20100702.doc) the Medical Devices Entry Content Module template identifier
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) for The Medical Devices Coded Section is not present.</assert>
        <report test="cda:entry/cda:supply[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.5']">Success: (cf.&#194;&#167;13.1.15 epSOS_Implementation_v0.2.20100702.doc) the Medical Devices Entry Content Module template identifier
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) for The Medical Devices Coded Section is present.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Medical Devices Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
    object -->
        <assert test="../cda:section">Error: In IHE PCC Medical Devices Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5), the Medical Devices can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.11383.10.20.1.7&quot;]">Error: In IHE PCC Medical Devices Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5), the parent template identifier for Medical Devices is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;46264-8&quot;]">Error: In IHE PCC Medical Devices Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5), the section type code of a Medical Devices must be 46264-8</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Medical Devices Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.5), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.3.5-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <!-- <title>epSOS Medical Devices Entry Content Module Specification - errors validation phase</title>-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.3.5&quot;]">
        <!-- R12.1 Device and Implant description -->
        <assert test="cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code and (cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code/@displayName or cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code/@nullFlavor)">
        Error: R11.1 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The Device and Implant description element "code\@displayName" is required. The attribute 'displayName' shall be filled, otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code">Success: R11.1 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The Device and Implant description element "code\@displayName" is present.</report>
        <report test="cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code/@displayName">Success: R11.1 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The Device and Implant description element "code" attribute's 'displayName' is
        filled.</report>
        <!-- R12.2 Device and Implant code-->
        <assert test="cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code and (cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code[@code='1.3.6.1.4.1.12559.11.10.1.3.1.42.8'] or cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code/@nullFlavor)">
        Error: R11.1 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The Device and Implant code element "code\@code" is required. The attribute 'code' shall have a value '1.3.6.1.4.1.12559.11.10.1.3.1.42.8', otherwise the nullFlavor attribute shall be
        present.</assert>
        <report test="cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code">Success: R11.1 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The Device and Implant code element "code\@code" is present.</report>
        <report test="cda:participant[@typeCode='DEV']/cda:participantRole/cda:playingDevice/cda:code[@code='1.3.6.1.4.1.12559.11.10.1.3.1.42.8']">Success: R11.1 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The Device and Implant code element "code" attribute's
        'code' is correctly set to the value '1.3.6.1.4.1.12559.11.10.1.3.1.42.8'.</report>
        <!-- R12.3 Device Implant date-->
        <assert test="cda:effectiveTime">Error: R12.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The Device and Implant date element "effectiveTime\@value" is required.</assert>
        <report test="cda:effectiveTime">Success: R12.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The Device and Implant code element "effectiveTime" is present.</report>
      </rule>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.3.5&quot;]/cda:effectiveTime">
        <!-- R12.3 Device Implant date-->
        <assert test="(matches(@value,'\d{4}') or matches(@value,'\d{6}') or matches(@value,'\d{8}') or matches(@value,'\d{12}') or matches(@value,'\d{14}')) or @nullFlavor">Error: R12.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The Device and Implant date
        element "effectiveTime\@value" is required. The attribute 'value' shall have the date-time format (CCYYMMDDHHMMSS). Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="(matches(@value,'\d{4}') or matches(@value,'\d{6}') or matches(@value,'\d{8}') or matches(@value,'\d{12}') or matches(@value,'\d{14}'))">Success: R12.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.5) The attribute 'value' of the element
        "effectiveTime" is correctly set to the date-time format (CCYYMMDDHHMMSS).</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.13.2.11-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Procedures Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.1.13.2.11&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Procedures Section (1.3.6.1.4.1.19376.1.5.3.1.1.13.2.11), the Procedures can only be used on sections.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;29544-3&quot;]">Error: In IHE PCC Procedures Section (1.3.6.1.4.1.19376.1.5.3.1.1.13.2.11), the section type code of a Procedures must be 29544-3.</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Procedures Section (1.3.6.1.4.1.19376.1.5.3.1.1.13.2.11), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.19&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC Procedures Section (1.3.6.1.4.1.19376.1.5.3.1.1.13.2.11), a Procedures and Interventions Section must contain a Procedures Entry.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.19-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Procedure Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.19&quot;]">
        <assert test="self::cda:procedure[@classCode=&quot;PROC&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), the classCode for Procedure Entry shall be "PROC" and the element is procedure.</assert>
        <assert test="not(./@moodCode=&quot;EVN&quot;) or cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.29&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), when the Procedure Entry is in event mood (moodCode='EVN'), this
        entry conforms to the CCD template 2.16.840.1.113883.10.20.1.29.</assert>
        <assert test="not(./@moodCode=&quot;INT&quot;) or cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.25&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), when the Procedure Entry is in intent mood (moodCode='INT'),
        this entry conforms to the CCD template 2.16.840.1.113883.10.20.1.25.</assert>
        <assert test="cda:id">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), a Procedure Entry shall contain an id element.</assert>
        <assert test="cda:code">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), a Procedure Entry shall contain a code element.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), a Procedure Entry text element shall contain a reference to the narrative text describing the procedure.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot; or @code = &quot;active&quot; or @code = &quot;aborted&quot; or @code = &quot;cancelled&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), the &lt;statusCode&gt;
        element shall be present when used to describe a Procedure Event. It shall have the value 'completed' for procedures that have been completed, and 'active' for procedures that are still in progress. Procedures that were stopped prior to
        completion shall use the value 'aborted', and procedures that were cancelled before being started shall use the value 'cancelled'.</assert>
        <assert test="not(./@moodCode=&quot;INT&quot;) or (cda:effectiveTime or cda:priorityCode)">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), in Procedure Entry the priorityCode element shall be present in INT mood when
        effectiveTime is not provided, it may be present in other moods. It indicates the priority of the procedure.</assert>
        <!--cf.epSOS_Implementation_v0.2.20100702.doc -->
        <!--$7.2 Body Data elements -->
        <!-- R10.1 Procedure description  -->
        <assert test="cda:code and ((cda:code/@displayName and cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.10']) or cda:code/@nullFlavor)">Error: R10.1 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure description element "code" is required. The
        attribute 'displayName' shall be filled and 'codeSystem' shall take the value '1.3.6.1.4.1.12559.11.10.1.3.1.42.10', otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:code">Success: R10.1 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure description element "code" is present.</report>
        <report test="cda:code/@displayName and cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.10']">Success: R10.1 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure description element "code" attribute's 'displayName' is filled and 'codeSystem' is
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.10'.</report>
        <!-- R10.2 Procedure code  -->
        <assert test="cda:code and (cda:code/@code or cda:code/@nullFlavor)">Error: R10.2 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure code element "code" is required. The attribute 'displayName' shall be filled, otherwise the nullFlavor attribute
        shall be present.</assert>
        <report test="cda:code">Success: R10.2 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure code element "code" is present.</report>
        <report test="cda:code/@displayName">Success: R10.2 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure code element "code" attribute's 'displayName' is filled.</report>
        <!-- R10.3 Procedure date  -->
        <assert test="cda:effectiveTime">Error: R10.3 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure date element "effectiveTime" is required.</assert>
        <report test="cda:effectiveTime">Success: R10.3 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure date element "effectiveTime" is present.</report>
      </rule>
      <!-- R10.3 Procedure date  -->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.19&quot;]/cda:effectiveTime">
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R10.3 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure date element "effectiveTime/@value" is required. The attribute 'value'
        of the element "effectiveTime" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor
        attribute shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Internal References - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.4.1&quot;]">
        <assert test="self::cda:act">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), for compatibility with the Clinical Statement model the Internal Reference shall always use the &lt;act&gt; class</assert>
        <assert test="cda:id[@root and @extension]">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the id element shall be present in Internal Reference. The root and extension attributes shall identify an element defined
        elsewhere in the same document.</assert>
        <assert test="cda:code">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the code element of Internal Reference shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.9.50-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Health Maintenance Care Plan Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.1.9.50&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
                object -->
        <assert test="../cda:section">Error: In IHE PCC Health Maintenance Care Plan Section (1.3.6.1.4.1.19376.1.5.3.1.1.9.50), the Care Plan can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.31&quot;]">Error: In IHE PCC Health Maintenance Care Plan Section (1.3.6.1.4.1.19376.1.5.3.1.1.9.50), the parent template identifier for Care Plan is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;18776-5&quot;]">Error: In IHE PCC Health Maintenance Care Plan Section (1.3.6.1.4.1.19376.1.5.3.1.1.9.50), the section type code of a Care Plan must be 18776-5</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Health Maintenance Care Plan Section (1.3.6.1.4.1.19376.1.5.3.1.1.9.50), the section type code must come from the LOINC code system
        (2.16.840.1.113883.6.1).</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.31-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Care Plan Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.31&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Care Plan Section (1.3.6.1.4.1.19376.1.5.3.1.3.31), the Care Plan can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.10&quot;]">Error: In IHE PCC Care Plan Section (1.3.6.1.4.1.19376.1.5.3.1.3.31), the parent template identifier for Care Plan is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;18776-5&quot;]">Error: In IHE PCC Care Plan Section (1.3.6.1.4.1.19376.1.5.3.1.3.31), the section type code of a Care Plan must be 18776-5</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Care Plan Section (1.3.6.1.4.1.19376.1.5.3.1.3.31), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.17-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Functional Status Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.17&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Functional Status Section (1.3.6.1.4.1.19376.1.5.3.1.3.17), the Functional Status can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.5&quot;]">Error: In IHE PCC Functional Status Section (1.3.6.1.4.1.19376.1.5.3.1.3.17), the parent template identifier for Functional Status is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;47420-5&quot;]">Error: In IHE PCC Functional Status Section (1.3.6.1.4.1.19376.1.5.3.1.3.17), the section type code of a Functional Status must be 47420-5</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Functional Status Section (1.3.6.1.4.1.19376.1.5.3.1.3.17), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.16.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Coded Social History Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.16.1&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Coded Social History Section (1.3.6.1.4.1.19376.1.5.3.1.3.16.1), the Coded Social History can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.16&quot;]">Error: In IHE PCC Coded Social History Section (1.3.6.1.4.1.19376.1.5.3.1.3.16.1), the parent template identifier for Social History is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;29762-2&quot;]">Error: In IHE PCC Coded Social History Section (1.3.6.1.4.1.19376.1.5.3.1.3.16.1), the section type code of a Coded Social History must be 29762-2</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Coded Social History Section (1.3.6.1.4.1.19376.1.5.3.1.3.16.1), the section type code must come from the LOINC code system
        (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.4&quot;]">Error: In IHE PCC Coded Social History Section (1.3.6.1.4.1.19376.1.5.3.1.3.16.1), Social History Observation is required.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.16-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Social History Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.16&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Social History Section (1.3.6.1.4.1.19376.1.5.3.1.3.16), the Social History can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.15&quot;]">Error: In IHE PCC Social History Section (1.3.6.1.4.1.19376.1.5.3.1.3.16), the parent template identifier for Social History is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;29762-2&quot;]">Error: In IHE PCC Social History Section (1.3.6.1.4.1.19376.1.5.3.1.3.16), the section type code of a Social History must be 29762-2</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Social History Section (1.3.6.1.4.1.19376.1.5.3.1.3.16), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Social History Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.4&quot;]">
        <assert test="cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13&quot;">Error: In IHE PCC Social History Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.4), the parent template (Simple Observation) is required.</assert>
        <assert test="cda:templateId/@root=&quot;2.16.840.1.113883.10.20.1.33&quot;">Error: In IHE PCC Social History Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.4), the parent template HL7 CCD Social History is required and identifies this as a Social
        History observation.</assert>
      </rule>
      <!--TODO There is no attribute 'code'  (defined by the xsd) in the element "value" (when the data type is 'PQ')-->
      <!--     <rule context='*[cda:templateId/@root="1.3.6.1.4.1.19376.1.5.3.1.4.13.4"][cda:code/@code="229819007"]/cda:value'>   
       <!-\-cf.epSOS_Implementation_v0.2.20100702.doc -\->
       <!-\- R15.1 Smoke (Optional) -\->
       <assert test="(string-length(@code)&gt; 0 and matches(@codeSystem,'1.3.6.1.4.1.12559.11.10.1.3.1.42.14')) or @nullFlavor">
          Error: R15.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) The Smoke element "value/@code" is Optional. If present and the code/@code is "229819007" the attribute 'code' of the element "value" shall be filled and the attribute 'codeSystem' shall be '1.3.6.1.4.1.12559.11.10.1.3.1.42.14'. Otherwise the nullFlavor attribute shall be present. 
       </assert>    
       </rule>-->
      <!-- R15.2 Social History Reference date range : SMOKE -->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.4&quot;][cda:code/@code=&quot;229819007&quot;]/cda:effectiveTime[@xsi:type=&quot;IVL_TS&quot;]/cda:low">
        <!--low-->
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (SMOKE) element "low" is Optional. If present
        the attribute 'value' of the element "low" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the
        nullFlavor attribute shall be present.</assert>
        <report test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Success: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (SMOKE) element "low" is present, and the attribute 'value'
        is well filled.</report>
      </rule>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.4&quot;][cda:code/@code=&quot;229819007&quot;]/cda:effectiveTime[@xsi:type=&quot;IVL_TS&quot;]/cda:high">
        <!--high-->
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (SMOKE) element "high" is Optional. If present
        the attribute 'value' of the element "high" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the
        nullFlavor attribute shall be present.</assert>
        <report test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Success: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (SMOKE) element "high" is present, and the attribute 'value'
        is well filled.</report>
      </rule>
      <!-- R15.2 Social History Reference date range : ALCOHOL -->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.4&quot;][cda:code/@code=&quot;160573003&quot;]/cda:effectiveTime[@xsi:type=&quot;IVL_TS&quot;]/cda:low">
        <!--low-->
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (ALCOHOL) element "low" is Optional. If present
        the attribute 'value' of the element "low" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the
        nullFlavor attribute shall be present.</assert>
        <report test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Success: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (ALCOHOL) element "low" is present, and the attribute 'value'
        is well filled.</report>
      </rule>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.4&quot;][cda:code/@code=&quot;160573003&quot;]/cda:effectiveTime[@xsi:type=&quot;IVL_TS&quot;]/cda:high">
        <!--high-->
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (ALCOHOL) element "high" is Optional. If present
        the attribute 'value' of the element "high" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the
        nullFlavor attribute shall be present.</assert>
        <report test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Success: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (ALCOHOL) element "high" is present, and the attribute
        'value' is well filled.</report>
      </rule>
      <!-- R15.2 Social History Reference date range : DIET -->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.4&quot;][cda:code/@code=&quot;364393001&quot;]/cda:effectiveTime[@xsi:type=&quot;IVL_TS&quot;]/cda:low">
        <!--low-->
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (DIET) element "low" is Optional. If present the
        attribute 'value' of the element "low" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the
        nullFlavor attribute shall be present.</assert>
        <report test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Success: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (DIET) element "low" is present, and the attribute 'value' is
        well filled.</report>
      </rule>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.4&quot;][cda:code/@code=&quot;364393001&quot;]/cda:effectiveTime[@xsi:type=&quot;IVL_TS&quot;]/cda:high">
        <!--high-->
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (DIET) element "high" is Optional. If present
        the attribute 'value' of the element "high" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the
        nullFlavor attribute shall be present.</assert>
        <report test="(@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Success: R15.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Social History Reference date range (DIET) element "high" is present, and the attribute 'value'
        is well filled.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Simple Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13&quot;]">
        <assert test="cda:id">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), each Simple Observation shall have an &lt;id&gt; identifier.</assert>
        <assert test="cda:code">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), Simple Observations shall have a code describing what was measured.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), each observation measurement entry may contain a text element providing the free text that provides the same information as the
        observation within the narrative portion of the document with a text element. For CDA based uses of Simple Observations, this element SHALL be present, and SHALL contain a reference element that points to the related string in the narrative
        portion of the document.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), the status code of all Simple Observations shall be completed.</assert>
        <assert test="cda:effectiveTime[@value or @nullFlavor]">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), the &lt;effectiveTime&gt; element shall be present in Simple Observations, and shall record the date and time when the
        measurement was taken. This element shall be precise to the day. If the date and time is unknown, this element shall record that using the nullFlavor attribute.</assert>
        <assert test="cda:value">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), the value of the Simple Observation shall be recorded using a data type appropriate to the observation.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13.6-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.6&quot;]">
        <assert test="cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.13&quot;] and cda:templateId[@root = &quot;2.16.840.1.113883.10.20.1.31&quot;]">Error: In IHE PCC Blood Type Observation: 1.3.6.1.4.1.19376.1.5.3.1.4.13.6), the parent CCD
        template ID (2.16.840.1.113883.10.20.1.31) and the parent PCC template ID (1.3.6.1.4.1.19376.1.5.3.1.4.13) shall be present in the Blood Type Observation.</assert>
        <assert test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), This "templateId" elements identify this as a blood type observation. They
        shall be present in the "observation" element as another event (moodCode='EVN') observing (&lt;observation classCode='OBS'&gt;). See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6</assert>
        <report test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) The blood type observation is present in the "observation" element as
        another event (moodCode='EVN') observing (&lt;observation classCode='OBS'&gt;).</report>
        <assert test="cda:code and cda:code[@code=&quot;882.1&quot;] and cda:code[@codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), The "repeatNumber" element shall not be present
        in a blood type observation. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6..</assert>
        <report test="cda:code">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) "code" element is present.</report>
        <report test="cda:code and cda:code[@code=&quot;882.1&quot;] and cda:code[@codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) 'code' attribute is 882-1 and 'codeSystem'
        attribute is correctly set to the value '2.16.840.1.113883.6.1'.</report>
        <assert test="not(cda:repeatNumber) and not(cda:interpretationCode) and not(cda:methodCode) and not(cda:targetSiteCode)">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), The "repeatNumber", "interpretationCode",
        "methodCode" and "targetSiteCode" elements shall not be present in a blood type observation. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6.</assert>
        <report test="not(cda:repeatNumber) and not(cda:interpretationCode) and not(cda:methodCode) and not(cda:targetSiteCode)">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) any of the "repeatNumber",
        "interpretationCode", "methodCode" and "targetSiteCode" are present.</report>
        <assert test="cda:value and cda:value[@xsi:type=&quot;CE&quot;] and cda:value/@code">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), The "value" element shall be present and shall use the CE data type. The code
        attribute shall be valued using a vocabulary that supports encoding of blood types. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6.</assert>
        <report test="cda:value">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) "value" element is present.</report>
        <report test="cda:value[@xsi:type='CE'] and cda:value/@code">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) 'xsi:type' attribute is CE data type and 'code' attribute is filled.</report>
      </rule>
      <!--////Optional-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.6&quot;][cda:code/@code='34530-6']/cda:value">
        <assert test="(@code and @codeSystem=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.42.20&quot;) or (not(@code) or @nullFlavor)">Error: R18.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.6),the Blood Type Observation element "value" is optioanl. If it's present the
        attribute 'code' shall be filled and the attribute 'codeSystem' shall be '1.3.6.1.4.1.12559.11.10.1.3.1.42.20'. Otherwise the nullFlavor attribute shall be present. See
        http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6.</assert>
      </rule>
      <!--R18.2 Date of observation-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.6&quot;][cda:code/@code='34530-6']/cda:effectiveTime">
        <assert test="(@value and (matches(@value,'\d{4}') or matches(@value,'\d{6}') or matches(@value,'\d{8}') or matches(@value,'\d{12}') or matches(@value,'\d{14}'))) or @nullFlavor">Error: R18.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) Date of observation
        element "effectiveTime" is Optional. If present the attribute 'value' shall have the date-time format (CCYYMMDDHHMMSS). Otherwise the nullFlavor attribute shall be present.</assert>
      </rule>
      <!--Optional/////-->
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.5.3.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Pregnancy History Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.1.5.3.4&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Pregnancy History Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.4), the Pregnancy History can only be used on sections.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;10162-6&quot;]">Error: In IHE PCC Pregnancy History Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.4), the section type code of a Pregnancy History must be 10162-6</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Pregnancy History Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.4), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.5&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC Pregnancy History Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.4), a Pregnancy History must contain Pregnancy Observation.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13.5-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Pregnancy Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.5&quot;]">
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13&quot;]">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the parent template Id (1.3.6.1.4.1.19376.1.5.3.1.4.13) of Pregnancy Observation shall
        exist.</assert>
        <assert test="cda:code[@codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), a Pregnancy Observation shall have a LOINC code describing what facet of patient's pregnancy
        history is being recorded.</assert>
        <assert test="not(cda:code[@code=&quot;11449-6&quot;]) or (cda:code[@code=&quot;11449-6&quot;] and cda:value[@xsi:type=&quot;CE&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 11449-6 shall be recorded using a CE datatype.</assert>
        <assert test="not(cda:code[@code=&quot;8678-5&quot;]) or (cda:code[@code=&quot;8678-5&quot;] and cda:value[@xsi:type=&quot;CE&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 8678-5 shall be recorded using a CE datatype.</assert>
        <assert test="not(cda:code[@code=&quot;8665-2&quot;]) or (cda:code[@code=&quot;8665-2&quot;] and cda:value[@xsi:type=&quot;TS&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 8665-2 shall be recorded using a TS datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11636-8&quot;]) or (cda:code[@code=&quot;11636-8&quot;] and (cda:value[@xsi:type=&quot;QTY&quot;] or cda:value[@xsi:type=&quot;INT&quot;] or cda:value[@xsi:type=&quot;MO&quot;] or cda:value[@xsi:type=&quot;TS&quot;] or cda:value[@xsi:type=&quot;REAL&quot;] or cda:value[@xsi:type=&quot;PQ&quot;] or cda:value[@xsi:type=&quot;RTO&quot;]))">
        Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a LOINC code of 11636-8 shall be recorded using a QTY datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11637-6&quot;]) or (cda:code[@code=&quot;11637-6&quot;] and (cda:value[@xsi:type=&quot;QTY&quot;] or cda:value[@xsi:type=&quot;INT&quot;] or cda:value[@xsi:type=&quot;MO&quot;] or cda:value[@xsi:type=&quot;TS&quot;] or cda:value[@xsi:type=&quot;REAL&quot;] or cda:value[@xsi:type=&quot;PQ&quot;] or cda:value[@xsi:type=&quot;RTO&quot;]))">
        Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a LOINC code of 11637-6 shall be recorded using a QTY datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11638-4&quot;]) or (cda:code[@code=&quot;11638-4&quot;] and (cda:value[@xsi:type=&quot;QTY&quot;] or cda:value[@xsi:type=&quot;INT&quot;] or cda:value[@xsi:type=&quot;MO&quot;] or cda:value[@xsi:type=&quot;TS&quot;] or cda:value[@xsi:type=&quot;REAL&quot;] or cda:value[@xsi:type=&quot;PQ&quot;] or cda:value[@xsi:type=&quot;RTO&quot;]))">
        Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a LOINC code of 11638-4 shall be recorded using a QTY datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11639-2&quot;]) or (cda:code[@code=&quot;11639-2&quot;] and (cda:value[@xsi:type=&quot;QTY&quot;] or cda:value[@xsi:type=&quot;INT&quot;] or cda:value[@xsi:type=&quot;MO&quot;] or cda:value[@xsi:type=&quot;TS&quot;] or cda:value[@xsi:type=&quot;REAL&quot;] or cda:value[@xsi:type=&quot;PQ&quot;] or cda:value[@xsi:type=&quot;RTO&quot;]))">
        Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a LOINC code of 11639-2 shall be recorded using a QTY datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11640-0&quot;]) or (cda:code[@code=&quot;11640-0&quot;] and (cda:value[@xsi:type=&quot;QTY&quot;] or cda:value[@xsi:type=&quot;INT&quot;] or cda:value[@xsi:type=&quot;MO&quot;] or cda:value[@xsi:type=&quot;TS&quot;] or cda:value[@xsi:type=&quot;REAL&quot;] or cda:value[@xsi:type=&quot;PQ&quot;] or cda:value[@xsi:type=&quot;RTO&quot;]))">
        Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a LOINC code of 11640-0 shall be recorded using a QTY datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11778-8&quot;]) or (cda:code[@code=&quot;11778-8&quot;] and cda:value[@xsi:type=&quot;TS&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 11778-8 shall be recorded using a TS datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11779-6&quot;]) or (cda:code[@code=&quot;11779-6&quot;] and cda:value[@xsi:type=&quot;TS&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 11779-6 shall be recorded using a TS datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11780-4&quot;]) or (cda:code[@code=&quot;11780-4&quot;] and cda:value[@xsi:type=&quot;TS&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 11780-4 shall be recorded using a TS datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11884-4&quot;]) or (cda:code[@code=&quot;11884-4&quot;] and cda:value[@xsi:type=&quot;PQ&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 11884-4 shall be recorded using a PQ datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11885-1&quot;]) or (cda:code[@code=&quot;11885-1&quot;] and cda:value[@xsi:type=&quot;PQ&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 11885-1 shall be recorded using a PQ datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11886-9&quot;]) or (cda:code[@code=&quot;11886-9&quot;] and cda:value[@xsi:type=&quot;PQ&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 11886-9 shall be recorded using a PQ datatype.</assert>
        <assert test="not(cda:code[@code=&quot;11887-7&quot;]) or (cda:code[@code=&quot;11887-7&quot;] and cda:value[@xsi:type=&quot;PQ&quot;])">Error: In IHE PCC Pregnancy Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.5), the Pregnancy Observation with a
        LOINC code of 11887-7 shall be recorded using a PQ datatype.</assert>
      </rule>
      <!--Optional-->
      <!--R16.1 Expected Date of Delivery-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.5&quot;][cda:code/@code='11778-8']/cda:value">
        <assert test="(@value and (matches(@value,'\d{4}') or matches(@value,'\d{6}') or matches(@value,'\d{8}') or matches(@value,'\d{12}') or matches(@value,'\d{14}'))) or @nullFlavor">Error: R16.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.5) Expected Date of
        Delivery element "value" is Optional. If present the attribute 'value' shall have the date-time format (CCYYMMDDHHMMSS). Otherwise the nullFlavor attribute shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Simple Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13&quot;]">
        <assert test="cda:id">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), each Simple Observation shall have an &lt;id&gt; identifier.</assert>
        <assert test="cda:code">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), Simple Observations shall have a code describing what was measured.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), each observation measurement entry may contain a text element providing the free text that provides the same information as the
        observation within the narrative portion of the document with a text element. For CDA based uses of Simple Observations, this element SHALL be present, and SHALL contain a reference element that points to the related string in the narrative
        portion of the document.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), the status code of all Simple Observations shall be completed.</assert>
        <assert test="cda:effectiveTime[@value or @nullFlavor]">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), the &lt;effectiveTime&gt; element shall be present in Simple Observations, and shall record the date and time when the
        measurement was taken. This element shall be precise to the day. If the date and time is unknown, this element shall record that using the nullFlavor attribute.</assert>
        <assert test="cda:value">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), the value of the Simple Observation shall be recorded using a data type appropriate to the observation.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13.6-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.6&quot;]">
        <assert test="cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.13&quot;] and cda:templateId[@root = &quot;2.16.840.1.113883.10.20.1.31&quot;]">Error: In IHE PCC Blood Type Observation: 1.3.6.1.4.1.19376.1.5.3.1.4.13.6), the parent CCD
        template ID (2.16.840.1.113883.10.20.1.31) and the parent PCC template ID (1.3.6.1.4.1.19376.1.5.3.1.4.13) shall be present in the Blood Type Observation.</assert>
        <assert test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), This "templateId" elements identify this as a blood type observation. They
        shall be present in the "observation" element as another event (moodCode='EVN') observing (&lt;observation classCode='OBS'&gt;). See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6</assert>
        <report test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) The blood type observation is present in the "observation" element as
        another event (moodCode='EVN') observing (&lt;observation classCode='OBS'&gt;).</report>
        <assert test="cda:code and cda:code[@code=&quot;882.1&quot;] and cda:code[@codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), The "repeatNumber" element shall not be present
        in a blood type observation. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6..</assert>
        <report test="cda:code">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) "code" element is present.</report>
        <report test="cda:code and cda:code[@code=&quot;882.1&quot;] and cda:code[@codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) 'code' attribute is 882-1 and 'codeSystem'
        attribute is correctly set to the value '2.16.840.1.113883.6.1'.</report>
        <assert test="not(cda:repeatNumber) and not(cda:interpretationCode) and not(cda:methodCode) and not(cda:targetSiteCode)">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), The "repeatNumber", "interpretationCode",
        "methodCode" and "targetSiteCode" elements shall not be present in a blood type observation. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6.</assert>
        <report test="not(cda:repeatNumber) and not(cda:interpretationCode) and not(cda:methodCode) and not(cda:targetSiteCode)">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) any of the "repeatNumber",
        "interpretationCode", "methodCode" and "targetSiteCode" are present.</report>
        <assert test="cda:value and cda:value[@xsi:type=&quot;CE&quot;] and cda:value/@code">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), The "value" element shall be present and shall use the CE data type. The code
        attribute shall be valued using a vocabulary that supports encoding of blood types. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6.</assert>
        <report test="cda:value">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) "value" element is present.</report>
        <report test="cda:value[@xsi:type='CE'] and cda:value/@code">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) 'xsi:type' attribute is CE data type and 'code' attribute is filled.</report>
      </rule>
      <!--////Optional-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.6&quot;][cda:code/@code='34530-6']/cda:value">
        <assert test="(@code and @codeSystem=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.42.20&quot;) or (not(@code) or @nullFlavor)">Error: R18.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.6),the Blood Type Observation element "value" is optioanl. If it's present the
        attribute 'code' shall be filled and the attribute 'codeSystem' shall be '1.3.6.1.4.1.12559.11.10.1.3.1.42.20'. Otherwise the nullFlavor attribute shall be present. See
        http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6.</assert>
      </rule>
      <!--R18.2 Date of observation-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.6&quot;][cda:code/@code='34530-6']/cda:effectiveTime">
        <assert test="(@value and (matches(@value,'\d{4}') or matches(@value,'\d{6}') or matches(@value,'\d{8}') or matches(@value,'\d{12}') or matches(@value,'\d{14}'))) or @nullFlavor">Error: R18.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) Date of observation
        element "effectiveTime" is Optional. If present the attribute 'value' shall have the date-time format (CCYYMMDDHHMMSS). Otherwise the nullFlavor attribute shall be present.</assert>
      </rule>
      <!--Optional/////-->
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Coded Vital Signs Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
     object -->
        <assert test="../cda:section">Error: In IHE PCC Coded Vital Signs Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2), the Coded Vital Signs can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.25&quot;]">Error: In IHE PCC Coded Vital Signs Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2), the parent template identifier for Coded Vital Signs is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;8716-3&quot;]">Error: In IHE PCC Coded Vital Signs Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2), the section type code of a Coded Vital Signs must be 8716-3</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Coded Vital Signs Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.1&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC Coded Vital Signs Section (1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2), a Coded Vital Signs must contain Vital Signs Organizer.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Vital Signs Organizer - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.1&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.32&quot;] and cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.35&quot;]">Error: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the Vital Signs Organizer
        shall have template IDs (2.16.840.1.113883.10.20.1.32 and 2.16.840.1.113883.10.20.1.35) to indicate that it inherits constraints from the ASTM/HL7 CCD Specification for Vital signs.</assert>
        <report test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.32&quot;] and cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.35&quot;]">Success: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the Vital Signs
        Organizer do have template IDs (2.16.840.1.113883.10.20.1.32 and 2.16.840.1.113883.10.20.1.35) to indicate that it inherits constraints from the ASTM/HL7 CCD Specification for Vital signs.</report>
        <assert test="cda:id">Error: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the Vital Signs Organizer shall have an &lt;id&gt; element.</assert>
        <report test="cda:id">Success: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the Vital Signs Organizer do have an &lt;id&gt; element.</report>
        <assert test="cda:code[@code=&quot;46680005&quot; and @displayName=&quot;Vital signs&quot; and @codeSystem=&quot;2.16.840.1.113883.6.96&quot; and @codeSystemName=&quot;SNOMED CT&quot;]">Error: In IHE PCC Vital Signs Organizer
        (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the &lt;code&gt; element of Vital Signs Organizer shall be recorded as shown to indicate that this organizer captures information about patient vital signs. &lt;code code='46680005' displayName='Vital signs'
        codeSystem='2.16.840.1.113883.6.96' codeSystemName='SNOMED CT'/&gt;</assert>
        <report test="cda:code[@code=&quot;46680005&quot; and @displayName=&quot;Vital signs&quot; and @codeSystem=&quot;2.16.840.1.113883.6.96&quot; and @codeSystemName=&quot;SNOMED CT&quot;]">Success: In IHE PCC Vital Signs Organizer
        (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the &lt;code&gt; element of Vital Signs Organizer is been recorded as shown to indicate that this organizer captures information about patient vital signs. &lt;code code='46680005' displayName='Vital signs'
        codeSystem='2.16.840.1.113883.6.96' codeSystemName='SNOMED CT'/&gt;.</report>
        <assert test="cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the Vital Signs Organizer observations shall have all been completed.</assert>
        <report test="cda:statusCode[@code=&quot;completed&quot;]">Success: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the Vital Signs Organizer observations have all been 'completed'.</report>
        <assert test="cda:effectiveTime">Error: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the effective time element shall be present in Vital Signs Organizer to indicate when the measurement was taken.</assert>
        <report test="cda:effectiveTime">Success: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the effective time element is present in Vital Signs Organizer to indicate when the measurement was taken.</report>
        <assert test="cda:component/cda:observation/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.2&quot;]">Error: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the Vital Signs Organizer shall have one or more
        &lt;component&gt; elements that are &lt;observation&gt; elements using the Vital Signs Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.13.2).</assert>
        <report test="cda:component/cda:observation/cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.2&quot;]">Success: In IHE PCC Vital Signs Organizer (1.3.6.1.4.1.19376.1.5.3.1.4.13.1), the Vital Signs Organizer do have one or more
        &lt;component&gt; elements that are &lt;observation&gt; elements using the Vital Signs Observation template (1.3.6.1.4.1.19376.1.5.3.1.4.13.2).</report>
      </rule>
      <!-- R17.1.1 Blood pressure : Systolic (Optional)-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.1&quot;]/cda:component/cda:observation[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.2&quot;][cda:code/@code=&quot;8480-6&quot;]/cda:value">
        <assert test="(@nullFlavor or (@value and @unit))">Error: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Blood pressure (Systolic) element "value" is Optional. If present the attributes 'value' and 'unit' shall be filled. Otherwise the nullFlavor
        attribute shall be present.</assert>
        <report test="@value and @unit">Success: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Blood pressure (Systolic) element "value" is present. The attributes 'value' and 'unit' are well filled.</report>
      </rule>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.1&quot;]/cda:component/cda:observation[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.2&quot;][cda:code/@code=&quot;8480-6&quot;]">
        <assert test="cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.21']">Error: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Blood pressure (Systolic). If the attribute 'code' of the element "code" is '8480-6' than the attribute 'codeSystem'
        shall be '1.3.6.1.4.1.12559.11.10.1.3.1.42.21'.</assert>
        <report test="cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.21']">Success: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Blood pressure (Systolic). the attribute 'codeSystem' of the element "code" is correctly set to the value
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.21' .</report>
      </rule>
      <!--Diastolic (Optional)-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.1&quot;]/cda:component/cda:observation[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.2&quot;][cda:code/@code=&quot;8462-4&quot;]/cda:value">
        <assert test="(@nullFlavor or (@value and @unit))">Error: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Blood pressure (Diastolic) element "value" is Optional. If present the attributes 'value' and 'unit' shall be filled. Otherwise the nullFlavor
        attribute shall be present.</assert>
        <report test="@value and @unit">Success: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Blood pressure (Diastolic) element "value" is present. The attributes 'value' and 'unit' are well filled.</report>
      </rule>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.1&quot;]/cda:component/cda:observation[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.2&quot;][cda:code/@code=&quot;8462-4&quot;]">
        <assert test="cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.21']">Error: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Blood pressure (Diastolic). If the attribute 'code' of the element "code" is '8462-4' than the attribute 'codeSystem'
        shall be '1.3.6.1.4.1.12559.11.10.1.3.1.42.21'.</assert>
        <report test="cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.21']">Success: R17.1.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.4) Blood pressure (Diastolic). the attribute 'codeSystem' of the element "code" is '1.3.6.1.4.1.12559.11.10.1.3.1.42.21'
        .</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Vital Signs Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.2&quot;]">
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13&quot;] and cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.31&quot;]">Error: In IHE PCC Vital Signs Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), Vital Signs
        Observation shall include parent template IDs to indicate that it inherits constraints from the ASTM/HL7 CCD Specification for Vital signs (2.16.840.1.113883.10.20.1.31), and the constraints of this specification
        (1.3.6.1.4.1.19376.1.5.3.1.4.13).</assert>
        <assert test="cda:code[@code = &quot;9279-1&quot; or @code = &quot;8867-4&quot; or @code = &quot;2710-2&quot; or @code = &quot;8480-6&quot; or @code = &quot;8462-4&quot; or @code = &quot;8310-5&quot; or @code = &quot;8302-2&quot; or @code = &quot;8306-3&quot; or @code = &quot;8287-5&quot; or @code = &quot;3141-9&quot;]">
        Error: In IHE PCC Vital Signs Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a Vital Signs Observation shall use a LOINC code from the table in the specification. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.2</assert>
        <assert test="cda:value[@xsi:type=&quot;PQ&quot;]">Error: In IHE PCC Vital Signs Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), the &lt;value&gt; element shall be present in Vital Signs Observation, and shall be of the appropriate data type
        specified for measure in the table. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.2</assert>
        <assert test="not(cda:code[@code=&quot;9279-1&quot;]) or cda:value[@unit=&quot;/min&quot;]">Error: In IHE PCC Vital Signs Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 9279-1 shall use units = "/min".</assert>
        <assert test="not(cda:code[@code=&quot;8867-4&quot;]) or cda:value[@unit=&quot;/min&quot;]">Error: In IHE PCC Vital Signs Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 8867-4 shall use units = "/min".</assert>
        <assert test="not(cda:code[@code=&quot;2710-2&quot;]) or cda:value[@unit=&quot;%&quot;]">Error: In IHE PCC Vital Signs Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 2710-2 shall use units = "%".</assert>
        <assert test="not(cda:code[@code=&quot;8480-6&quot;]) or cda:value[@unit=&quot;mm[Hg]&quot;]">Error: In IHE PCC Vital Signs Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 8480-6 shall use units = "mm[Hg]".</assert>
        <assert test="not(cda:code[@code=&quot;8462-4&quot;]) or cda:value[@unit=&quot;mm[Hg]&quot;]">Error: In IHE PCC Vital Signs Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 8462-4 shall use units = "mm[Hg]".</assert>
        <assert test="not(cda:code[@code=&quot;8310-5&quot;]) or cda:value[@unit=&quot;Cel&quot; or @unit=&quot;[degF]&quot;]">Error: In IHE PCC Vital Signs Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 8310-5 shall use units = "Cel" or
        "[degF]".</assert>
        <assert test="not(cda:code[@code=&quot;8302-2&quot;]) or cda:value[@unit=&quot;m&quot; or @unit=&quot;cm&quot; or @unit=&quot;[in_us]&quot; or @unit=&quot;[in_uk]&quot;]">Error: In IHE PCC Vital Signs Observation
        (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 8302-2 shall use units = "m" or "cm" or "[in_us]" or "[in_uk]".</assert>
        <assert test="not(cda:code[@code=&quot;8306-3&quot;]) or cda:value[@unit=&quot;m&quot; or @unit=&quot;cm&quot; or @unit=&quot;[in_us]&quot; or @unit=&quot;[in_uk]&quot;]">Error: In IHE PCC Vital Signs Observation
        (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 8306-3 shall use units = "m" or "cm" or "[in_us]" or "[in_uk]".</assert>
        <assert test="not(cda:code[@code=&quot;8287-5&quot;]) or cda:value[@unit=&quot;m&quot; or @unit=&quot;cm&quot; or @unit=&quot;[in_us]&quot; or @unit=&quot;[in_uk]&quot;]">Error: In IHE PCC Vital Signs Observation
        (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 8287-5 shall use units = "m" or "cm" or "[in_us]" or "[in_uk]".</assert>
        <assert test="not(cda:code[@code=&quot;3141-9&quot;]) or cda:value[@unit=&quot;kg&quot; or @unit=&quot;g&quot; or @unit=&quot;[lb_av]&quot; or @unit=&quot;[oz_av]&quot;]">Error: In IHE PCC Vital Signs Observation
        (1.3.6.1.4.1.19376.1.5.3.1.4.13.2), a code of 3141-9 shall use units = "kg" or "g" or "[lb_av]" or "[oz_av]".</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Simple Observation - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13&quot;]">
        <assert test="cda:id">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), each Simple Observation shall have an &lt;id&gt; identifier.</assert>
        <assert test="cda:code">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), Simple Observations shall have a code describing what was measured.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), each observation measurement entry may contain a text element providing the free text that provides the same information as the
        observation within the narrative portion of the document with a text element. For CDA based uses of Simple Observations, this element SHALL be present, and SHALL contain a reference element that points to the related string in the narrative
        portion of the document.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot;]">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), the status code of all Simple Observations shall be completed.</assert>
        <assert test="cda:effectiveTime[@value or @nullFlavor]">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), the &lt;effectiveTime&gt; element shall be present in Simple Observations, and shall record the date and time when the
        measurement was taken. This element shall be precise to the day. If the date and time is unknown, this element shall record that using the nullFlavor attribute.</assert>
        <assert test="cda:value">Error: In IHE PCC Simple Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13), the value of the Simple Observation shall be recorded using a data type appropriate to the observation.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.13.6-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.6&quot;]">
        <assert test="cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.13&quot;] and cda:templateId[@root = &quot;2.16.840.1.113883.10.20.1.31&quot;]">Error: In IHE PCC Blood Type Observation: 1.3.6.1.4.1.19376.1.5.3.1.4.13.6), the parent CCD
        template ID (2.16.840.1.113883.10.20.1.31) and the parent PCC template ID (1.3.6.1.4.1.19376.1.5.3.1.4.13) shall be present in the Blood Type Observation.</assert>
        <assert test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), This "templateId" elements identify this as a blood type observation. They
        shall be present in the "observation" element as another event (moodCode='EVN') observing (&lt;observation classCode='OBS'&gt;). See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6</assert>
        <report test="self::cda:observation[@classCode=&quot;OBS&quot; and @moodCode=&quot;EVN&quot;]">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) The blood type observation is present in the "observation" element as
        another event (moodCode='EVN') observing (&lt;observation classCode='OBS'&gt;).</report>
        <assert test="cda:code and cda:code[@code=&quot;882.1&quot;] and cda:code[@codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), The "repeatNumber" element shall not be present
        in a blood type observation. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6..</assert>
        <report test="cda:code">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) "code" element is present.</report>
        <report test="cda:code and cda:code[@code=&quot;882.1&quot;] and cda:code[@codeSystem=&quot;2.16.840.1.113883.6.1&quot;]">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) 'code' attribute is 882-1 and 'codeSystem'
        attribute is correctly set to the value '2.16.840.1.113883.6.1'.</report>
        <assert test="not(cda:repeatNumber) and not(cda:interpretationCode) and not(cda:methodCode) and not(cda:targetSiteCode)">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), The "repeatNumber", "interpretationCode",
        "methodCode" and "targetSiteCode" elements shall not be present in a blood type observation. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6.</assert>
        <report test="not(cda:repeatNumber) and not(cda:interpretationCode) and not(cda:methodCode) and not(cda:targetSiteCode)">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) any of the "repeatNumber",
        "interpretationCode", "methodCode" and "targetSiteCode" are present.</report>
        <assert test="cda:value and cda:value[@xsi:type=&quot;CE&quot;] and cda:value/@code">Error: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6), The "value" element shall be present and shall use the CE data type. The code
        attribute shall be valued using a vocabulary that supports encoding of blood types. See http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6.</assert>
        <report test="cda:value">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) "value" element is present.</report>
        <report test="cda:value[@xsi:type='CE'] and cda:value/@code">Success: In IHE PCC Blood Type Observation (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) 'xsi:type' attribute is CE data type and 'code' attribute is filled.</report>
      </rule>
      <!--////Optional-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.6&quot;][cda:code/@code='34530-6']/cda:value">
        <assert test="(@code and @codeSystem=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.42.20&quot;) or (not(@code) or @nullFlavor)">Error: R18.1 (1.3.6.1.4.1.19376.1.5.3.1.4.13.6),the Blood Type Observation element "value" is optioanl. If it's present the
        attribute 'code' shall be filled and the attribute 'codeSystem' shall be '1.3.6.1.4.1.12559.11.10.1.3.1.42.20'. Otherwise the nullFlavor attribute shall be present. See
        http://wiki.ihe.net/index.php?title=1.3.6.1.4.1.19376.1.5.3.1.4.13.6.</assert>
      </rule>
      <!--R18.2 Date of observation-->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.13.6&quot;][cda:code/@code='34530-6']/cda:effectiveTime">
        <assert test="(@value and (matches(@value,'\d{4}') or matches(@value,'\d{6}') or matches(@value,'\d{8}') or matches(@value,'\d{12}') or matches(@value,'\d{14}'))) or @nullFlavor">Error: R18.2 (1.3.6.1.4.1.19376.1.5.3.1.4.13.6) Date of observation
        element "effectiveTime" is Optional. If present the attribute 'value' shall have the date-time format (CCYYMMDDHHMMSS). Otherwise the nullFlavor attribute shall be present.</assert>
      </rule>
      <!--Optional/////-->
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.25-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Vital Signs Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.25&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
    object -->
        <assert test="../cda:section">Error: In IHE PCC Vital Signs Section (1.3.6.1.4.1.19376.1.5.3.1.3.25), the Vital Signs can only be used on sections.</assert>
        <!-- Verify that the parent templateId is also present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.16&quot;]">Error: In IHE PCC Vital Signs Section (1.3.6.1.4.1.19376.1.5.3.1.3.25), the parent template identifier for Vital Signs is not present.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;8716-3&quot;]">Error: In IHE PCC Vital Signs Section (1.3.6.1.4.1.19376.1.5.3.1.3.25), the section type code of a Vital Signs must be 8716-3</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Vital Signs Section (1.3.6.1.4.1.19376.1.5.3.1.3.25), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.28-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Coded Results Section - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.28&quot;]">
        <!-- Verify that the template id is used on the appropriate type of
    object -->
        <assert test="../cda:section">Error: In IHE PCC Coded Results Section (1.3.6.1.4.1.19376.1.5.3.1.3.28), the Coded Results can only be used on sections.</assert>
        <!-- Verify the section type code -->
        <assert test="cda:code[@code = &quot;30954-2&quot;]">Error: In IHE PCC Coded Results Section (1.3.6.1.4.1.19376.1.5.3.1.3.28), the section type code of a Coded Results must be 30954-2</assert>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: In IHE PCC Coded Results Section (1.3.6.1.4.1.19376.1.5.3.1.3.28), the section type code must come from the LOINC code system (2.16.840.1.113883.6.1).</assert>
        <assert test=".//cda:templateId[@root = &quot;1.3.6.1.4.1.19376.1.5.3.1.4.19&quot;]">
        <!-- Verify that all required data elements are present -->
        Error: In IHE PCC Coded Results Section (1.3.6.1.4.1.19376.1.5.3.1.3.28), the Coded Results Section must contain a(n) Procedure Entry Entry.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.19-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Procedure Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.19&quot;]">
        <assert test="self::cda:procedure[@classCode=&quot;PROC&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), the classCode for Procedure Entry shall be "PROC" and the element is procedure.</assert>
        <assert test="not(./@moodCode=&quot;EVN&quot;) or cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.29&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), when the Procedure Entry is in event mood (moodCode='EVN'), this
        entry conforms to the CCD template 2.16.840.1.113883.10.20.1.29.</assert>
        <assert test="not(./@moodCode=&quot;INT&quot;) or cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.25&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), when the Procedure Entry is in intent mood (moodCode='INT'),
        this entry conforms to the CCD template 2.16.840.1.113883.10.20.1.25.</assert>
        <assert test="cda:id">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), a Procedure Entry shall contain an id element.</assert>
        <assert test="cda:code">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), a Procedure Entry shall contain a code element.</assert>
        <assert test="cda:text/cda:reference">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), a Procedure Entry text element shall contain a reference to the narrative text describing the procedure.</assert>
        <assert test="cda:statusCode[@code = &quot;completed&quot; or @code = &quot;active&quot; or @code = &quot;aborted&quot; or @code = &quot;cancelled&quot;]">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), the &lt;statusCode&gt;
        element shall be present when used to describe a Procedure Event. It shall have the value 'completed' for procedures that have been completed, and 'active' for procedures that are still in progress. Procedures that were stopped prior to
        completion shall use the value 'aborted', and procedures that were cancelled before being started shall use the value 'cancelled'.</assert>
        <assert test="not(./@moodCode=&quot;INT&quot;) or (cda:effectiveTime or cda:priorityCode)">Error: In IHE PCC Procedure Entry (1.3.6.1.4.1.19376.1.5.3.1.4.19), in Procedure Entry the priorityCode element shall be present in INT mood when
        effectiveTime is not provided, it may be present in other moods. It indicates the priority of the procedure.</assert>
        <!--cf.epSOS_Implementation_v0.2.20100702.doc -->
        <!--$7.2 Body Data elements -->
        <!-- R10.1 Procedure description  -->
        <assert test="cda:code and ((cda:code/@displayName and cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.10']) or cda:code/@nullFlavor)">Error: R10.1 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure description element "code" is required. The
        attribute 'displayName' shall be filled and 'codeSystem' shall take the value '1.3.6.1.4.1.12559.11.10.1.3.1.42.10', otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:code">Success: R10.1 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure description element "code" is present.</report>
        <report test="cda:code/@displayName and cda:code[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.10']">Success: R10.1 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure description element "code" attribute's 'displayName' is filled and 'codeSystem' is
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.10'.</report>
        <!-- R10.2 Procedure code  -->
        <assert test="cda:code and (cda:code/@code or cda:code/@nullFlavor)">Error: R10.2 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure code element "code" is required. The attribute 'displayName' shall be filled, otherwise the nullFlavor attribute
        shall be present.</assert>
        <report test="cda:code">Success: R10.2 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure code element "code" is present.</report>
        <report test="cda:code/@displayName">Success: R10.2 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure code element "code" attribute's 'displayName' is filled.</report>
        <!-- R10.3 Procedure date  -->
        <assert test="cda:effectiveTime">Error: R10.3 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure date element "effectiveTime" is required.</assert>
        <report test="cda:effectiveTime">Success: R10.3 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure date element "effectiveTime" is present.</report>
      </rule>
      <!-- R10.3 Procedure date  -->
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.19&quot;]/cda:effectiveTime">
        <assert test="@nullFlavor or (@value and (matches(@value,'^\d{6,8}$') or matches(@value,'^\d{6,12}\-\d{2,4}$')))">Error: R10.3 (1.3.6.1.4.1.19376.1.5.3.1.4.19) The Procedure date element "effectiveTime/@value" is required. The attribute 'value'
        of the element "effectiveTime" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor
        attribute shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC Internal References - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.4.1&quot;]">
        <assert test="self::cda:act">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), for compatibility with the Clinical Statement model the Internal Reference shall always use the &lt;act&gt; class</assert>
        <assert test="cda:id[@root and @extension]">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the id element shall be present in Internal Reference. The root and extension attributes shall identify an element defined
        elsewhere in the same document.</assert>
        <assert test="cda:code">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the code element of Internal Reference shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch">
      <title>IHE PCC External References - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.4&quot;]">
        <assert test="self::cda:act[@classCode=&quot;ACT&quot; and @moodCode=&quot;EVN&quot;]">Error: In IHE PCC External References (1.3.6.1.4.1.19376.1.5.3.1.4.4), the External Reference is an act that refers to documentation of an act
        (classCode='ACT'), that previously occurred (moodCode='EVN').</assert>
        <assert test="cda:id">Error: In IHE PCC External References (1.3.6.1.4.1.19376.1.5.3.1.4.4), the reference is an act of itself, and must be uniquely identified with an id element.</assert>
      </rule>
    </pattern>
  </phase>
  <phase id="warnings">
    <!--        <xi:include parse="xml" href="templates/warnings/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch"
            xpointer="xmlns(x=http://purl.oclc.org/dsdl/schematron) xpointer(//x:pattern)">
            <xi:fallback>
                <!-\- xi:include error : file not found :  templates/warnings/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch -\->
            </xi:fallback>
        </xi:include>-->
  </phase>
  <phase id="notes">
    <!--        <xi:include parse="xml" href="templates/notes/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch"
            xpointer="xmlns(x=http://purl.oclc.org/dsdl/schematron) xpointer(//x:pattern)">
            <xi:fallback>
                <!-\- xi:include error : file not found :  templates/notes/1.3.6.1.4.1.12559.11.10.1.3.1.1.3.sch -\->
            </xi:fallback>
        </xi:include>-->
  </phase>
</schema>
