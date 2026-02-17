<?xml version="1.0" encoding="utf-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron" xmlns:cda="urn:hl7-org:v3" xmlns:iso="http://purl.oclc.org/dsdl/schematron" xmlns:sch="http://www.ascc.net/xml/schematron" xmlns:xi="http://www.w3.org/2003/XInclude" queryBinding="xslt2">
  <title>Schematron schema for validating epSOS ePrescription Document</title>
  <ns prefix="cda" uri="urn:hl7-org:v3" />
  <ns prefix="epsos" uri="urn:epsos-org:ep:medication" />
  <ns prefix="xsi" uri="http://www.w3.org/2001/XMLSchema-instance" />
  <phase id="errors">
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.1.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <!-- <title>epSOS ePrescription Documents Specification - errors validation phase</title>-->
      <rule context="/cda:ClinicalDocument">
        <!-- R2 Verify that the template id is ePrescription Document -->
        <assert test="//cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.1.1&quot;]">Error: R2 The ePrescription Document requires the ePrescription Document template ID (1.3.6.1.4.1.12559.11.10.1.3.1.1.1).</assert>
        <report test="//cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.1.1&quot;]">Success : R2 ePrescription Document template ID (1.3.6.1.4.1.12559.11.10.1.3.1.1.1) is included.</report>
        <!-- Verify that the correct code system is used (cf.§2 epSOS_Implementation_v0.2.20100702.doc)-->
        <assert test="cda:code[@code = &quot;57833-6&quot;]">Error: (cf.&#194;&#167;2 epSOS_Implementation_v0.2.20100702.doc) The ePrescription document type code must be set to the value "57833-6" which is the code corresponding to "Prescription for
        medical equipment or product" in the loinc code system.</assert>
        <report test="cda:code[@code = &quot;57833-6&quot;]">Success: (cf.&#194;&#167;2 epSOS_Implementation_v0.2.20100702.doc) The ePrescription document type code is correctly set to the value "57833-6" which is the code corresponding to "Prescription
        for medical equipment or product" in the loinc code system.</report>
        <assert test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Error: (cf.&#194;&#167;2 epSOS_Implementation_v0.2.20100702.doc) The ePrescription document type code must be set to the value "2.16.840.1.113883.6.1" corresponding to the
        LOINC Code system OID.</assert>
        <report test="cda:code[@codeSystem = &quot;2.16.840.1.113883.6.1&quot;]">Success: (cf.&#194;&#167;2 epSOS_Implementation_v0.2.20100702.doc) The ePrescription document type code is correctly set to the value "2.16.840.1.113883.6.1" corresponding
        to the LOINC Code system OID.</report>
        <assert test="//cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.2.1&quot;]">Error: (cf.&#194;&#167;13.1 epSOS_Implementation_v0.2.20100702.doc) The ePrescription Document requires the ePrescription Section name\template ID
        (1.3.6.1.4.1.12559.11.10.1.3.1.2.1).</assert>
        <report test="//cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.2.1&quot;]">Success: (cf.&#194;&#167;13.1 epSOS_Implementation_v0.2.20100702.doc) The ePrescription Document correctly includes the ePrescription Section name\template ID
        (1.3.6.1.4.1.12559.11.10.1.3.1.2.1).</report>
      </rule>
    </pattern>
    <pattern xmlns:xi="http://www.w3.org/2003/XInclude" xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.3-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.1.eP-errors" xml:base="templates/ePtemplates/errors/1.3.6.1.4.1.19376.1.5.3.1.1.1.eP.sch">
      <title>IHE PCC Medical Documents Specification - errors validation phase</title>
      <!--epSOS-->
      <!--(cf.epSOS_Implementation_v0-1.0.1.20100623.doc : 8  Common Header Data Elements)-->
      <!-- R1.11.2 Date of last update-->
      <rule context="/cda:ClinicalDocument/cda:documentationOf/cda:serviceEvent/cda:effectiveTime">
        <assert test="(cda:high and cda:high/@value and count(cda:high)&lt;2) or not(cda:high) or @nullFlavor">Error:R1.11.2 Date of last update is optional. If it's present, the attribute 'value' of the element "high" shall not be empty. The element
        "high" cannot be repeated (cardinality [1..1]).</assert>
      </rule>
      <rule context="/cda:ClinicalDocument/cda:legalAuthenticator/cda:assignedEntity/cda:representedOrganization/cda:addr">
        <!-- R1.11.10 Legal Authenticator Facility Address-->
        <assert test="(((cda:streetAddressLine) or (cda:city) or (cda:state) or (cda:country)) and not(@nullFlavor)) or ( (@nullFlavor) and (not(cda:streetAddressLine) and not(cda:city) and not(cda:state) and not(cda:country)))">Error: R1.11.10 Legal
        Authenticator Facility Address element is required. If there is no information, the nullFlavor attribute shall be present and no address parts shall be present, otherwise there shall be no nullFlavor attribute, and at least one of the address
        parts listed below shall be present</assert>
        <!-- R1.11.10 Legal Authenticator Facility Street/Number of Street-->
        <assert test="(cda:streetAddressLine and ((every $i in cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:streetAddressLine/@nullFlavor) and count(cda:streetAddressLine)&lt;2 ) or @nullFlavor">Error: R1.11.10 Legal
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
        <assert test=" (matches(@value,'tel:\+[0-9/(/)/-]') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0)) or (matches (@value,'^mailto:([a-zA-Z0-9]+(([\.\-_]?[a-zA-Z0-9]+)+)?)@(([a-zA-Z0-9]+[\.\-_])+[a-zA-Z]{2,4})$') and (every $i in @use satisfies string-length(normalize-space($i)) &gt; 0)) or @nullFlavor">
        Error: R1.11.10 Legal Authenticator Facility telephone number(cf.R1.10.9.4.1) or the Legal Authenticator Facility e-mail address (cf.R1.10.9.4.2) are miswritten : the e-mail address shall be like : example@exp.com and the telephone number shall
        be like : "tel:+13176307960" or "tel:+1(317)630-7960"</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.3.eP-errors" xml:base="templates/ePtemplates/errors/1.3.6.1.4.1.19376.1.5.3.1.2.3.eP.sch">
      <!--epSOS-->
      <!--(cf.epSOS_Implementation_v0-1.0.1.20100623.doc : 8  Common Header Data Elements)-->
      <rule context="//cda:ClinicalDocument"></rule>
      <rule context="/cda:ClinicalDocument/cda:author/cda:assignedAuthor">
        <!-- R1.10.6 HCP Profession (Health Care Professional's Specialty) -->
        <assert test="(parent::node()/cda:functionCode/@code and parent::node()/cda:functionCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.1']) or (@nullFlavor) or (parent::node() and not (cda:assignedPerson))">Error: R1.10.6 HCP Profession is
        required. The element (functionCode) shall be present in "ClinicalDocument\author\". The attributes (functionCode\@code) and (functionCode\@codeSystem) shall not be empty and the @codeSystem shall be "1.3.6.1.4.1.12559.11.10.1.3.1.42.1".</assert>
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
        <assert test="(cda:functionCode/@code and cda:functionCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.1']) or @nullFlavor or (not(cda:assignedEntity/cda:assignedPerson))">Error: R1.10.6 HCP Profession is required. The element (functionCode)
        shall be present in "\ClinicalDocument\documentationOf\serviceEvent\performer\". The attributes (functionCode\@code) and (functionCode\@codeSystem) shall not be empty and the @codeSystem shall be "1.3.6.1.4.1.12559.11.10.1.3.1.42.1".</assert>
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.2.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <!--  <title>epSOS Prescription Section Specification - errors validation phase</title> -->
      <rule context="//cda:section[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.1']">
        <!-- Verify the document type code -->
        <assert test="cda:code[@code = '10160-0']">Error: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The Prescription Section type code of a ePrescription Documents must be set to the value "10160-0" which is the code corresponding to
        "History of medication use" in the loinc code system.</assert>
        <report test="cda:code[@code = '10160-0']">Success: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The Prescription Section type code of a ePrescription Documents is correctly set to the value "10160-0" which is the code
        corresponding to "History of medication use" in the loinc code system.</report>
        <!-- Verify that the correct code system is used-->
        <assert test="cda:code[@codeSystem = '2.16.840.1.113883.6.1']">Error: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The document type code of a Medical Documents must be "2.16.840.1.113883.6.1" corresponding to the LOINC Code
        system OID</assert>
        <report test="cda:code[@codeSystem = '2.16.840.1.113883.6.1']">Success: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The document type code of a Medical Documents is correctly set to "2.16.840.1.113883.6.1" corresponding to the
        LOINC Code system OID</report>
        <!-- Verify that the parents templateId are present. -->
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.8&quot;]">Error: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (2.16.840.1.113883.10.20.1.8) for Prescription Section is not
        present.</assert>
        <report test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.8&quot;]">Success: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (2.16.840.1.113883.10.20.1.8) for Prescription Section is
        present.</report>
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.19&quot;]">Error: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.19376.1.5.3.1.3.19) for Prescription Section shall
        be present.</assert>
        <report test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.3.19&quot;]">Success: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.19376.1.5.3.1.3.19) for Prescription Section is
        present.</report>
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.2.3&quot;]">Error: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.12559.11.10.1.3.1.2.3) for Prescription Section
        shall be present.</assert>
        <report test="cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.2.3&quot;]">Success: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.12559.11.10.1.3.1.2.3) for Prescription Section
        is present.</report>
        <!-- Verify that the Prescription Item Entry Content Module templateId is present. -->
        <assert test="cda:entry/cda:substanceAdministration/cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.3.2&quot;]">Error: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The Prescription Item Entry Content Module identifier
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) for Prescription Section shall be present.</assert>
        <report test="cda:entry/cda:substanceAdministration/cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.3.2&quot;]">Success: (cf.&#194;&#167;13.1.2 epSOS_Implementation_v0.2.20100702.doc) The Prescription Item Entry Content Module identifier
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) for Prescription Section is present.</report>
        <!--R2.1 Prescription ID -->
        <assert test="cda:id and count(cda:id)&lt;2">Error: R2.1 The Prescription ID element is required. The element "id" cannot be repeated (cardinality [1..1]).</assert>
        <report test="cda:id">Success: R2.1 The Prescription ID element is present.</report>
        <report test="count(cda:id)&lt;2">Success: R2.1 The Prescription ID element cannot be repeated (cardinality [1..1]).</report>
        <!--R2.2 Prescriber -->
        <assert test="cda:author and count(cda:author)&lt;2">Error: R2.2 The Prescriber element is required. The element "author" shall be present and it cannot be repeated (cardinality [1..1]).</assert>
        <report test="cda:author">Success: R2.2 The Prescriber element is required. The element "author" is present.</report>
        <report test="count(cda:author)&lt;2">Success: R2.2 The Prescriber element is required. The element "author" is not repeated (cardinality [1..1]).</report>
        <assert test="cda:author/cda:assignedAuthor or cda:author/@nullFlavor or not(cda:author)">Error: R2.2 The Prescriber "assignedAuthor" element is required. The element "assignedAuthor" shall be present in "author\".</assert>
        <assert test="cda:author/cda:assignedAuthor/cda:assignedPerson or cda:author/cda:assignedAuthor/@nullFlavor or not(cda:author/cda:assignedAuthor)">Error: R2.2 The Prescriber "assignedPerson" element is required. The element "assignedPerson" shall
        be present in "author\assignedAuthor\".</assert>
        <assert test="cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name or cda:author/cda:assignedAuthor/cda:assignedPerson/@nullFlavor or not(cda:author/cda:assignedAuthor/cda:assignedPerson)">Error: R2.2 The Prescriber "name" element is
        required. The element "name" shall be present in "author\assignedAuthor\assignedPerson".</assert>
        <!-- R2.2 Prescriber Family Name/Surname -->
        <assert test="(cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name/cda:family and (every $i in cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name/cda:family satisfies string-length(normalize-space($i))&gt; 0)) or cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name/@nullFlavor or not(cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name)">
        Error: R2.2 The Prescriber Family Name/Surname element is required. The element (family) shall not be empty.</assert>
        <report test="cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name/cda:family">Success: R2.2 The Prescriber Family Name/Surname element is present.</report>
        <!-- R2.2 Prescriber Given Name -->
        <assert test="(cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name/cda:given and (every $i in cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name/cda:given satisfies string-length(normalize-space($i)) &gt; 0)) or cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name/@nullFlavor or not(cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name)">
        Error: R2.2 The Prescriber Given Name element is required. The element (given) shall not be empty.</assert>
        <report test="cda:author/cda:assignedAuthor/cda:assignedPerson/cda:name/cda:given">Success: R2.2 The Prescriber Given Name element is present.</report>
        <!-- R2.2 Prescriber ID number -->
        <assert test="(cda:author/cda:assignedAuthor/cda:id) or cda:author/cda:assignedAuthor/@nullFlavor or not(cda:author/cda:assignedAuthor/cda:assignedPerson)">Error: R2.2 The Prescriber ID number element is required.</assert>
        <report test="cda:author/cda:assignedAuthor/cda:id">Success: R2.2 The Prescriber ID number element is present.</report>
        <!-- R2.2 Prescriber Profession -->
        <assert test="(cda:author/cda:functionCode and cda:author/cda:functionCode/@codeSystem and cda:author/cda:functionCode/@code ) or cda:author/@nullFlavor or not(cda:author/cda:assignedAuthor/cda:assignedPerson)">Error: R2.2 The Prescriber
        Profession element is required. The attributes (functionCode\@code) and (functionCode\@codeSystem) shall not be empty.</assert>
        <report test="cda:author/cda:functionCode">Success: R2.2 The Prescriber Profession element is present.</report>
        <!-- R2.2 Prescriber Facility -->
        <assert test="cda:author/cda:assignedAuthor/cda:representedOrganization or cda:author/cda:assignedAuthor/@nullFlavor or not(cda:author/cda:assignedAuthor)">Error: R2.2 The Prescriber "representedOrganization" element is required. The element
        "representedOrganization" shall be present in "author\assignedAuthor\".</assert>
        <!-- R2.2 Prescriber Facility Name  -->
        <assert test="(cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name and (every $i in cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name satisfies count(($i))&lt; 2) and ((every $i in cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name satisfies string-length(normalize-space($i)) &gt; 0) or cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name/@nullFlavor)) or cda:author/cda:assignedAuthor/cda:representedOrganization/@nullFlavor or not(cda:author/cda:assignedAuthor/cda:representedOrganization)">
        Error: R2.2 The Prescriber Facility Name "name" element is required. The element (name) shall not be empty. The element "name" shall be present in "author\assignedAuthor\representedOrganization\".</assert>
        <report test="cda:author/cda:assignedAuthor/cda:representedOrganization/cda:name">Success: R2.2 The Prescriber Facility Name "name" element is present.</report>
        <!-- R2.2 Prescriber Facility Identifier  -->
        <assert test="(cda:author/cda:assignedAuthor/cda:representedOrganization/cda:id and (every $i in cda:author/cda:assignedAuthor/cda:representedOrganization/cda:id satisfies count(($i))&lt; 2) and (cda:author/cda:assignedAuthor/cda:representedOrganization/cda:id/@root or cda:author/cda:assignedAuthor/cda:representedOrganization/cda:id/@nullFlavor)) or cda:author/cda:assignedAuthor/cda:representedOrganization/@nullFlavor or not(cda:author/cda:assignedAuthor/cda:representedOrganization)">
        Error: R2.2 The Prescriber Facility Identifier "id" element is required. The attribute 'root' of the element (id) shall not be empty. The element "id" shall be present in "author\assignedAuthor\representedOrganization\".</assert>
        <report test="cda:author/cda:assignedAuthor/cda:representedOrganization/cda:id">Success: R2.2 The Prescriber Facility Identifier "id" element is present.</report>
        <!-- R2.2 Prescriber Facility Address  -->
        <assert test="(cda:author/cda:assignedAuthor/cda:representedOrganization/cda:addr and (every $i in cda:author/cda:assignedAuthor/cda:representedOrganization/cda:addr satisfies count(($i))&lt; 2)) or cda:author/cda:assignedAuthor/cda:representedOrganization/@nullFlavor or not(cda:author/cda:assignedAuthor/cda:representedOrganization)">
        Error: R2.2 The Prescriber Facility Address "addr" element is required. The element "addr" shall be present in "author\assignedAuthor\representedOrganization\".</assert>
        <report test="cda:author/cda:assignedAuthor/cda:representedOrganization/cda:addr">Success: R2.2 The Prescriber Facility Address "addr" element is present.</report>
      </rule>
      <rule context="//cda:section[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.1']/cda:author/cda:assignedAuthor/cda:representedOrganization/cda:addr">
        <!-- R2.2 Prescriber Facility Street  -->
        <assert test="(cda:streetAddressLine and count(cda:streetAddressLine)&lt;2 and ((every $i in cda:streetAddressLine satisfies string-length(normalize-space($i)) &gt; 0) or cda:streetAddressLine/@nullFlavor)) or @nullFlavor">Error: R2.2 The
        Prescriber Facility Street "streetAddressLine" element is required. The element "streetAddressLine" shall not be empty otherwise the nullFlavor attribute shall be present. The element "streetAddressLine" cannot be repeated (cardinality
        [1..1])</assert>
        <!-- R2.2 Prescriber Facility City  -->
        <assert test="(cda:city and count(cda:city)&lt;2 and ((every $i in cda:city satisfies string-length(normalize-space($i)) &gt; 0) or cda:city/@nullFlavor)) or @nullFlavor">Error: R2.2 The Prescriber Facility City "city" element is required. The
        element "city" shall not be empty otherwise the nullFlavor attribute shall be present. The element "city" cannot be repeated (cardinality [1..1])</assert>
        <!-- R2.2 Prescriber Facility State or Province  -->
        <assert test="(cda:state and count(cda:state)&lt;2 and ((every $i in cda:state satisfies string-length(normalize-space($i)) &gt; 0) or cda:state/@nullFlavor)) or @nullFlavor">Error: R2.2 The Prescriber Facility State or Province "state" element
        is required. The element "state" shall not be empty otherwise the nullFlavor attribute shall be present. The element "state" cannot be repeated (cardinality [1..1])</assert>
        <!-- R2.2 Prescriber Facility Postal Code  -->
        <assert test="(cda:postalCode and count(cda:postalCode)&lt;2 and ((every $i in cda:postalCode satisfies string-length(normalize-space($i)) &gt; 0) or cda:postalCode/@nullFlavor)) or @nullFlavor">Error: R2.2 The Prescriber Facility Postal Code
        "postalCode" element is required. The element "postalCode" shall not be empty otherwise the nullFlavor attribute shall be present. The element "postalCode" cannot be repeated (cardinality [1..1])</assert>
        <!-- R2.2 Prescriber Facility Country  -->
        <assert test="(cda:country and count(cda:country)&lt;2 and (every $i in cda:country satisfies string-length(normalize-space($i)) &gt; 0) ) or @nullFlavor">Error: R2.2 The Prescriber Facility Country "country" element is required. The element
        "country" shall not be empty and cannot be repeated (cardinality [1..1])</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.19-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.11-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.11&quot;]">
        <report test="//cda:substanceAdministation">Success: In IHE PCC Combination Medications: 1.3.6.1.4.1.19376.1.5.3.1.4.11), In the case of the prepackaged combination, it is sufficient to supply the name of the combination drug product, and its
        strength designation in a single "substanceAdministation" entry. The dosing information shall then be recorded as simply a count of administration units. .</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.2.3-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.3.19-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.11-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.11&quot;]">
        <report test="//cda:substanceAdministation">Success: In IHE PCC Combination Medications: 1.3.6.1.4.1.19376.1.5.3.1.4.11), In the case of the prepackaged combination, it is sufficient to supply the name of the combination drug product, and its
        strength designation in a single "substanceAdministation" entry. The dosing information shall then be recorded as simply a count of administration units. .</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.3.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.11-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.11&quot;]">
        <report test="//cda:substanceAdministation">Success: In IHE PCC Combination Medications: 1.3.6.1.4.1.19376.1.5.3.1.4.11), In the case of the prepackaged combination, it is sufficient to supply the name of the combination drug product, and its
        strength designation in a single "substanceAdministation" entry. The dosing information shall then be recorded as simply a count of administration units. .</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Normal Dosing - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.1&quot;]">
        <report test="//cda:substanceAdministation">Error: In IHE PCC Normal Dosing: 1.3.6.1.4.1.19376.1.5.3.1.4.7.1), medications that use this template identifier shall not use subordinate &lt;substanceAdministation&gt; acts.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Internal References - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.4.1&quot;]">
        <assert test="self::cda:act">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), for compatibility with the Clinical Statement model the Internal Reference shall always use the &lt;act&gt; class</assert>
        <assert test="cda:id[@root and @extension]">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the id element shall be present in Internal Reference. The root and extension attributes shall identify an element defined
        elsewhere in the same document.</assert>
        <assert test="cda:code">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the code element of Internal Reference shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.3.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <!--  <title>epSOS Prescription Item Entry Content Module Specification - errors validation phase</title> -->
      <rule context="//cda:entry/cda:substanceAdministration[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.2']">
        <!-- Verify that the parents templateId are present. -->
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.12559.11.10.1.3.1.3.4&quot;]">Error: (cf.&#194;&#167;13.1.2.4 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.12559.11.10.1.3.1.3.4) for Prescription Item
        Entry Content Module shall be present.</assert>
        <assert test="cda:templateId[@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7&quot;]">Error: (cf.&#194;&#167;13.1.2.4 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (1.3.6.1.4.1.19376.1.5.3.1.4.7) for Prescription Item Entry
        Content Module shall be present.</assert>
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.24&quot;]">Error: (cf.&#194;&#167;13.1.2.4 epSOS_Implementation_v0.2.20100702.doc) The parent template identifier (2.16.840.1.113883.10.20.1.24) for Prescription Item Entry
        Content Module shall be present.</assert>
        <!--$9 Body Data elements -->
        <!-- R2.4 Prescription Item ID -->
        <assert test="cda:id and count(cda:id)&lt;2">Error: R2.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Prescription Item ID is required. The element "id" cannot be repeated (cardinality [1..1]).</assert>
        <report test="cda:id and count(cda:id)&lt;2">Success: R2.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Prescription Item ID element "id" is present and have a cardinality [1..1].</report>
        <!-- R4.2 Brand name of the medicinal product prescribed in country A -->
        <assert test="cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/cda:name and (every $i in cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/cda:name satisfies string-length(normalize-space($i)) &gt; 0)">Error: R4.2
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Brand name of the medicinal product element "name" is required. The element "name" shall be filled.</assert>
        <report test="cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/cda:name">Success: R4.2 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Brand name of the medicinal product element "name" is present.</report>
        <report test="(every $i in cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/cda:name satisfies string-length(normalize-space($i)) &gt; 0)">Success: R4.2 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Brand name of the medicinal product
        element "name" is filled.</report>
        <!-- R4.5 Medicinal product package -->
        <assert test="cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:asContent">Error: R4.5 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The "asContent" element is required.</assert>
        <report test="cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:asContent">Success: R4.5 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The "asContent" element is present.</report>
        <!-- R4.6 Pharmaceutical dose form -->
        <assert test="cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode and (every $i in cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode satisfies count(($i))&lt; 2) and cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode/@code and cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.2']">
        Error: R4.6 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Pharmaceutical dose form element "formCode" is required. The attributes 'code' and 'codeSystem' of the element "formCode" shall be filled and the attribute 'codeSystem' shall be
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.2'. The element "formCode" shall have a cardinality [1..1].</assert>
        <report test="cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode and (every $i in cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode satisfies count(($i))&lt; 2)">Success: R4.6
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Pharmaceutical dose form element "formCode" is present and do have a cardinality [1..1].</report>
        <report test="cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode/@code and cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:formCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.2']">Success: R4.6
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The attributes 'code' and 'codeSystem' of the Pharmaceutical dose form element "formCode" are filled and the 'codeSystem' is correctly set to the value '1.3.6.1.4.1.12559.11.10.1.3.1.42.2'.</report>
        <!-- R4.7 Route of Administration (Optional) -->
        <assert test="(cda:routeCode and cda:routeCode/@code and cda:routeCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.12']) or (not(cda:routeCode) or cda:routeCode/@nullFlavor)">Error: R4.7 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Route of
        Administration element "routeCode" is Optional. If present, the attributes 'code' and 'codeSystem' of the element "formCode" shall be filled and the attribute 'codeSystem' shall be '1.3.6.1.4.1.12559.11.10.1.3.1.42.12'. Otherwise the nullFlavor
        attribute shall be present.</assert>
        <!--R4.9 Number of units per intake (cf.8.1.1.2.4.7, @value should not be in doseQuantity, so we require low and high element ) -->
        <assert test="cda:doseQuantity and ((cda:doseQuantity/cda:low/@value and cda:doseQuantity/cda:high/@value) or cda:doseQuantity/@nullFlavor)">Error: R4.9 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Number of units per intake value "doseQuantity"
        element is required. The element "low\@value" and "high\value" shall be present in the element "doseQuantity" otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:doseQuantity">Success: R4.9 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Number of units per intake "doseQuantity" is present</report>
        <report test="cda:doseQuantity/cda:low/@value and cda:doseQuantity/cda:high/@value">Success: R4.9 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Number of units per intake elements "doseQuantity\high\@value" and "doseQuantity\low\@value" are
        present.</report>
        <!--R4.10 Frequency of intake -->
        <assert test="(cda:effectiveTime[2][@operator='A'] and (cda:effectiveTime[2][@xsi:type='TS'] or cda:effectiveTime[2][@xsi:type='IVL_TS'] or cda:effectiveTime[2][@xsi:type='PIVL_TS'] or cda:effectiveTime[2][@xsi:type='EIVL_TS'])) or cda:effectiveTime[2]/@nullFlavor">
        Error: R4.10 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Frequency of intake element "effectiveTime[2]" is required. The attribute 'xsi:type' of the element "effectiveTime[2]" shall take a value between "TS, IVL_TS, PIVL_TS or EIVL_TS" and the
        attribute 'operator' shall take the value 'A'. Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:effectiveTime[2]/@xsi:type">Success: R4.10 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Frequency of intake element "effectiveTime[2]" is present. The attribute 'xsi:type' is also present.</report>
        <!--R4.11 Duration of treatment -->
        <!--low -->
        <assert test="cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@nullFlavor or (cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@value and (matches(cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@value,'^\d{6,8}$') or matches(cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@value,'^\d{6,12}\-\d{2,4}$')))">
        Error: R4.11 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Duration of treatment element "low/@value" is required. The attribute 'value' of the element "low" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could
        contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:low/@value">Success: R4.11 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Duration of treatment element "low/@value" is present for the effectiveTime[1]\@xsi:type='IVL_TS'.</report>
        <!--high -->
        <assert test="cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@nullFlavor or (cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@value and (matches(cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@value,'^\d{6,8}$') or matches(cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@value,'^\d{6,12}\-\d{2,4}$')))">
        Error: R4.11 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Duration of treatment element "high/@value" is required. The attribute 'value' of the element "high" shall contain 6 to 8 digits exemple : March 01, 2000 will be "20000301 or 200003", or could
        contain the date end the time, exmple : March 01, 2000 @ 3:00 PM will be "200003011500-0700". Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="cda:effectiveTime[1][@xsi:type='IVL_TS']/cda:high/@value">Success: R4.11 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Duration of treatment element "high/@value" is present for the effectiveTime[1]\@xsi:type='IVL_TS'.</report>
      </rule>
      <rule context="//cda:entry/cda:substanceAdministration[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.2']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']">
        <!-- R4.3 Active Ingredient -->
        <assert test="epsos:ingredient/epsos:code and epsos:ingredient/epsos:code/@code and epsos:ingredient/epsos:code/@codeSystem and (every $i in epsos:ingredient/epsos:code satisfies count(($i))&lt; 2)">Error: R4.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2)
        The Active Ingredient element "code" is required. The attributes 'code' and 'codeSystem' of the element "code" shall be filled. The element "code" shall have a cardinality [1..1].</assert>
        <report test="epsos:ingredient/epsos:code and (every $i in epsos:ingredient/epsos:code satisfies count(($i))&lt; 2)">Success: R4.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Active Ingredient element "code" is present and do have cardinality
        [1..1].</report>
        <report test="epsos:ingredient/epsos:code/@code and epsos:ingredient/epsos:code/@codeSystem">Success: R4.3 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The attributes 'code' and 'codeSystem' of the Active Ingredient element "code" are filled.</report>
        <!-- R4.4 Strength of the medicinal product -->
        <assert test="epsos:quantity and count(epsos:quantity)&lt;2">Error: R4.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Strength of the medicinal product element "quantity" is required. The element "quantity" shall have a cardinality [1..1].</assert>
        <report test="epsos:quantity and count(epsos:quantity)&lt;2">Success: R4.4 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Strength of the medicinal product element "quantity" is present and do have a cardinality [1..1].</report>
      </rule>
      <rule context="//cda:entry/cda:substanceAdministration[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.2']/cda:consumable/cda:manufacturedProduct/cda:manufacturedMaterial">
        <!-- R4.5 Medicinal product package -->
        <assert test="epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode and (every $i in epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode satisfies count(($i))&lt; 2) and epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode/@code and epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.3']">
        Error: R4.5 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Medicinal product package element "formCode" is required. The attributes 'code' and 'codeSystem' of the element "formCode" shall be filled and the attribute 'codeSystem' shall be
        '1.3.6.1.4.1.12559.11.10.1.3.1.42.3'. The element "formCode" shall have a cardinality [1..1].</assert>
        <report test="epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode and (every $i in epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode satisfies count(($i))&lt; 2)">Success: R4.5 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The
        Medicinal product package element "formCode" is present and do have a cardinality [1..1].</report>
        <report test="epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode/@code and epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.42.3']">Success: R4.5
        (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The attributes 'code' and 'codeSystem' of the Medicinal product package element "formCode" are filled and the 'codeSystem' is correctly set to the value '1.3.6.1.4.1.12559.11.10.1.3.1.42.3'.</report>
        <!-- R4.8.1 package size -->
        <assert test="epsos:asContent/epsos:quantity/epsos:numerator and epsos:asContent/epsos:quantity/epsos:numerator/@value and epsos:asContent/epsos:quantity/epsos:numerator/@unit">Error: R4.8.1 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The package size
        element "numerator" is required. The attributes 'value' and 'unit' of the element "numerator" shall be filled.</assert>
        <report test="epsos:asContent/epsos:quantity/epsos:numerator">Success: R4.8.1 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The package size element "numerator" is present.</report>
        <report test="epsos:asContent/epsos:quantity/epsos:numerator/@value and epsos:asContent/epsos:quantity/epsos:numerator/@unit">Success: R4.8.1 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The attributes 'value' and 'unit' of the package size element
        "numerator" are filled.</report>
      </rule>
      <rule context="//cda:entry/cda:substanceAdministration[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.2']/cda:entryRelationship[@typeCode='COMP']/cda:supply[@moodCode= 'RQO' and cda:independentInd/@value='false']">
        <!-- R4.8 Number of packages -->
        <assert test="cda:quantity and cda:quantity/@value and cda:quantity/@unit">Error: R4.8 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Number of packages element "quantity" is required.</assert>
        <report test="cda:quantity">Success: R4.8 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Number of packages element "quantity" is present.</report>
        <report test="cda:quantity/@value and cda:quantity/@unit">Success: R4.8 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The attributes 'value' and 'unit' of The Number of packages element "quantity" are filled.</report>
      </rule>
      <rule context="//cda:entry/cda:substanceAdministration[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.2']/cda:participant[@typeCode='AUT']/cda:participantRole[@classCode='LIC']/cda:scopingEntity[@classCode='ORG']/cda:id">
        <!-- R2.3.2 Prescribing Credentialing Organization Identifier (Optional)-->
        <assert test="@root or @nullFlavor">Error: R2.3.2 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Prescribing Credentialing Organization Identifier is optional. If present the attribute 'root' shall be filled. Otherwise the nullFlavor attribute shall be
        present.</assert>
        <report test="@root">Success: R2.3.2 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The attribute 'root' of The Prescribing Credentialing Organization Identifier element "id" is well filled.</report>
      </rule>
      <rule context="//cda:entry/cda:substanceAdministration[cda:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.2']/cda:entryRelationship[@typeCode='SUBJ'][@inversionInd='true']/cda:observation[@classCode='OBS']/cda:value">
        <!-- R4.16 Substitution (Optional)-->
        <assert test="@nullFlavor or (@xsi:type='BL' and (@value='false' or @value='true'))">Error: R4.16 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Substitution element "value" is optional. If present the attribute 'value' shall take the value 'true' or
        'false' and 'xsi:type' shall be 'BL'. Otherwise the nullFlavor attribute shall be present.</assert>
        <report test="@xsi:type='BL' and (@value='false' or @value='true')">Success: R4.16 (1.3.6.1.4.1.12559.11.10.1.3.1.3.2) The Substitution element "value" is present. The attribute 'value' is correctly set to the value 'true' or 'false' and
        'xsi:type' is correctly set to the value 'BL'.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.11-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.11&quot;]">
        <report test="//cda:substanceAdministation">Success: In IHE PCC Combination Medications: 1.3.6.1.4.1.19376.1.5.3.1.4.11), In the case of the prepackaged combination, it is sufficient to supply the name of the combination drug product, and its
        strength designation in a single "substanceAdministation" entry. The dosing information shall then be recorded as simply a count of administration units. .</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.12559.11.10.1.3.1.3.4-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.2-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.11-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Product Entry - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.11&quot;]">
        <report test="//cda:substanceAdministation">Success: In IHE PCC Combination Medications: 1.3.6.1.4.1.19376.1.5.3.1.4.11), In the case of the prepackaged combination, it is sufficient to supply the name of the combination drug product, and its
        strength designation in a single "substanceAdministation" entry. The dosing information shall then be recorded as simply a count of administration units. .</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Normal Dosing - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.1&quot;]">
        <report test="//cda:substanceAdministation">Error: In IHE PCC Normal Dosing: 1.3.6.1.4.1.19376.1.5.3.1.4.7.1), medications that use this template identifier shall not use subordinate &lt;substanceAdministation&gt; acts.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Internal References - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.4.1&quot;]">
        <assert test="self::cda:act">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), for compatibility with the Clinical Statement model the Internal Reference shall always use the &lt;act&gt; class</assert>
        <assert test="cda:id[@root and @extension]">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the id element shall be present in Internal Reference. The root and extension attributes shall identify an element defined
        elsewhere in the same document.</assert>
        <assert test="cda:code">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the code element of Internal Reference shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.7.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Normal Dosing - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.7.1&quot;]">
        <report test="//cda:substanceAdministation">Error: In IHE PCC Normal Dosing: 1.3.6.1.4.1.19376.1.5.3.1.4.7.1), medications that use this template identifier shall not use subordinate &lt;substanceAdministation&gt; acts.</report>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.4.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Internal References - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.4.1&quot;]">
        <assert test="self::cda:act">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), for compatibility with the Clinical Statement model the Internal Reference shall always use the &lt;act&gt; class</assert>
        <assert test="cda:id[@root and @extension]">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the id element shall be present in Internal Reference. The root and extension attributes shall identify an element defined
        elsewhere in the same document.</assert>
        <assert test="cda:code">Error: In IHE PCC Internal References (1.3.6.1.4.1.19376.1.5.3.1.4.4.1), the code element of Internal Reference shall be present.</assert>
      </rule>
    </pattern>
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.3.1-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.4.3-errors" xml:base="templates/errors/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch">
      <title>IHE PCC Patient Medication Instructions - errors validation phase</title>
      <rule context="*[cda:templateId/@root=&quot;1.3.6.1.4.1.19376.1.5.3.1.4.3&quot;]">
        <assert test="cda:templateId[@root=&quot;2.16.840.1.113883.10.20.1.49&quot;]">Error: In IHE PCC Patient Medication Instructions (1.3.6.1.4.1.19376.1.5.3.1.4.3), the parent CCD template ID (2.16.840.1.113883.10.20.1.49) of Patient Medication
        Instructions shall be present.</assert>
        <assert test="cda:code[@code=&quot;PINSTRUCT&quot; and @codeSystem=&quot;1.3.6.1.4.1.19376.1.5.3.2&quot; and @codeSystemName=&quot;IHEActCode&quot;]">Error: In IHE PCC Patient Medication Instructions (1.3.6.1.4.1.19376.1.5.3.1.4.3), the code for
        Patient Medication Instructions shall be recorded exactly as specified: &lt;code code='PINSTRUCT' codeSystem='1.3.6.1.4.1.19376.1.5.3.2' codeSystemName='IHEActCode' /&gt;.</assert>
        <assert test="cda:text/cda:reference">Error: The &lt;text&gt; element indicates the text of the comment in Patient Medication Instructions. For CDA, this SHALL be represented as a &lt;reference&gt; element that points at the narrative portion of
        the document. The comment itself is not the act being coded, so it appears in the &lt;text&gt; of the &lt;observation&gt;, not as part of the &lt;code&gt;.</assert>
        <assert test="cda:statusCode[@code=&quot;completed&quot;]">Error: In IHE PCC Patient Medication Instructions (1.3.6.1.4.1.19376.1.5.3.1.4.3), the code attribute of &lt;statusCode&gt; for all comments in Patient Medication Instructions must be
        completed.</assert>
      </rule>
    </pattern>
  </phase>
  <phase id="warnings">
    <!--        <xi:include parse="xml" href="templates/warnings/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch"
            xpointer="xmlns(x=http://purl.oclc.org/dsdl/schematron) xpointer(//x:pattern)">
            <xi:fallback>
                <!-\- xi:include error : file not found :  templates/warnings/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch -\->
            </xi:fallback>
        </xi:include>-->
  </phase>
  <phase id="notes">
    <!--<xi:include parse="xml" href="templates/notes/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch"
            xpointer="xmlns(x=http://purl.oclc.org/dsdl/schematron) xpointer(//x:pattern)">
            <xi:fallback>
                <!-\- xi:include error : file not found :  templates/notes/1.3.6.1.4.1.12559.11.10.1.3.1.1.1.sch -\->
            </xi:fallback>
        </xi:include>-->
  </phase>
</schema>
