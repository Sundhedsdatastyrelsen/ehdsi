<?xml version="1.0" encoding="utf-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron" xmlns:cda="urn:hl7-org:v3" xmlns:iso="http://purl.oclc.org/dsdl/schematron" xmlns:sch="http://www.ascc.net/xml/schematron" xmlns:xi="http://www.w3.org/2003/XInclude" queryBinding="xslt2">
  <title>CTX: 14110: XDS-SD Wrapper Format - HL7 CDA R2 (Schematron)</title>
  <ns prefix="cda" uri="urn:hl7-org:v3" />
  <ns prefix="epsos" uri="urn:epsos-org:ep:medication" />
  <ns prefix="xsi" uri="http://www.w3.org/2001/XMLSchema-instance" />
  <ns prefix="crs" uri="urn:hl7-org:crs" />
  <phase id="errors">
    <pattern name="ClinicalDocument child-less elements Reference ITI TF-1:7.1.8.2.1">
      <rule context="cda:ClinicalDocument">
        <!-- ClinicalDocument/id is present, but  with no extension -->
        <assert test="cda:id">ERR: ClinicalDocument/id is missing</assert>
        <report test="cda:id">CTX: ClinicalDocument/id exists (PASS)</report>
        <assert test="cda:id[@extension]">CTX: ClinicalDocument/id@extension is empty (PASS)</assert>
        <report test="cda:id[@extension]">ERR: ClinicalDocument/id is should have no extension</report>
        <assert test="cda:effectiveTime[@value]">ERR: ClinicalDocument/effectiveTime is missing</assert>
        <report test="cda:effectiveTime[@value]">CTX: ClinicalDocument/effectiveTime exists (PASS)</report>
        <!-- ClinicalDocument/CconfidentialityCode@code and confidentialityCode@codeSystem are present -->
        <assert test="cda:confidentialityCode[@code]">ERR: ClinicalDocument/confidentialityCode@code is missing</assert>
        <report test="cda:confidentialityCode[@code]">CTX: ClinicalDocument/confidentialityCode@code exists (PASS)</report>
        <assert test="cda:confidentialityCode[@codeSystem]">ERR: ClinicalDocument/confidentialityCode@codeSystem is missing</assert>
        <report test="cda:confidentialityCode[@codeSystem]">CTX: ClinicalDocument/confidentialityCode@codeSystem exists (PASS)</report>
        <!-- ClinicalDocument/languageCode is present -->
        <assert test="cda:languageCode[@code]">ERR: ClinicalDocument/languageCode@code is missing</assert>
        <report test="cda:languageCode[@code]">CTX: ClinicalDocument/languageCode@code exists (PASS)</report>
      </rule>
    </pattern>
    <pattern name="ClinicalDocument/recordTarget elements Reference ITI TF-1:7.1.8.2.2">
      <rule context="cda:ClinicalDocument">
        <!-- ClinicalDocument/recordTarget/patientRole/id is present with root and extension attributes -->
        <assert test="cda:recordTarget[cda:patientRole[cda:id[@root]]]">ERR: ClinicalDocument/recordTarget/patientRole/id@root is missing</assert>
        <report test="cda:recordTarget[cda:patientRole[cda:id[@root]]]">CTX: ClinicalDocument/recordTarget/patientRole/id@root exists (PASS)</report>
        <assert test="cda:recordTarget[cda:patientRole[cda:id[@extension]]]">ERR: ClinicalDocument/recordTarget/patientRole/id@extension is missing</assert>
        <report test="cda:recordTarget[cda:patientRole[cda:id[@extension]]]">CTX: ClinicalDocument/recordTarget/patientRole/id@extension exists (PASS)</report>
        <!-- ClinicalDocument/recordTarget/patientRole/addr is present with at least country element -->
        <assert test="cda:recordTarget[cda:patientRole[cda:addr[cda:country]]]">ERR: ClinicalDocument/recordTarget/patientRole/addr/country is missing</assert>
        <report test="cda:recordTarget[cda:patientRole[cda:addr[cda:country]]]">CTX: ClinicalDocument/recordTarget/patientRole/addr/country exists (PASS)</report>
        <!-- ClinicalDocument/recordTarget/patientRole/patient/name is present with at least a given and family subelement -->
        <assert test="cda:recordTarget[cda:patientRole[cda:patient[cda:name[cda:given]]]]">ERR: ClinicalDocument/recordTarget/patientRole/patient/name/given is missing</assert>
        <report test="cda:recordTarget[cda:patientRole[cda:patient[cda:name[cda:given]]]]">CTX: ClinicalDocument/recordTarget/patientRole/patient/name/given exists (PASS)</report>
        <assert test="cda:recordTarget[cda:patientRole[cda:patient[cda:name[cda:family]]]]">ERR: ClinicalDocument/recordTarget/patientRole/patient/name/family is missing</assert>
        <report test="cda:recordTarget[cda:patientRole[cda:patient[cda:name[cda:family]]]]">CTX: ClinicalDocument/recordTarget/patientRole/patient/name/family exists (PASS)</report>
        <!-- ClinicalDocument/recordTarget/patientRole/patient/administrativeGenderCode is present -->
        <assert test="cda:recordTarget[cda:patientRole[cda:patient[cda:administrativeGenderCode]]]">ERR: ClinicalDocument/recordTarget/patientRole/patient/administrativeGenderCode is missing</assert>
        <report test="cda:recordTarget[cda:patientRole[cda:patient[cda:administrativeGenderCode]]]">CTX: ClinicalDocument/recordTarget/patient/administrativeGenderCode exists (PASS)</report>
        <!-- ClinicalDocument/recordTarget/patientRole/patient/birthTime is present  with precision to the year-->
        <assert test="cda:recordTarget[cda:patientRole[cda:patient[cda:birthTime[@value]]]]">ERR: ClinicalDocument/recordTarget/patientRole/patient/birthTime is missing</assert>
        <report test="cda:recordTarget[cda:patientRole[cda:patient[cda:birthTime[@value]]]]">CTX: ClinicalDocument/recordTarget/patient/birthTime exists (PASS)</report>
      </rule>
    </pattern>
    <!-- ClinicalDocument/recordTarget/author (original):  no checks for these elements; only required if known Ref. ITI TF-1:7.1.8.2.3-->
    <pattern name="ClinicalDocument/author - scanner - elements -- Reference ITI TF-1:7.1.8.2.4">
      <rule context="cda:ClinicalDocument">
        <assert test="cda:author">ERR: ClinicalDocument/author is missing</assert>
        <report test="cda:author">CTX: ClinicalDocument/author exists (PASS)</report>
        <!-- ClinicalDocument/author/time -->
        <assert test="cda:author[cda:time]">ERR: ClinicalDocument/author/time is missing</assert>
        <report test="cda:author[cda:time]">CTX: ClinicalDocument/author/time exists (PASS)</report>
        <!--       NOT WORKING
        <assert test="cda:author[cda:time[@value]]=cda:effectiveTime[@value]" > ERR: ClinicalDocument/author/time must equal ClinicalDocument/effectiveTime </assert>
      
      <report test="cda:author[cda:time[@value]]=cda:effectiveTime[@value]"> CTX: ClinicalDocument/author/time matches ClinicalDocument/effectiveTime (PASS) </report>
      -->
        <!-- ClinicalDocument/author/assignedAuthor/id@root -->
        <assert test="cda:author[cda:assignedAuthor[cda:id[@root]]]">ERR: ClinicalDocument/author/assignedAuthor/id@root is missing</assert>
        <report test="cda:author[cda:assignedAuthor[cda:id[@root]]]">CTX: ClinicalDocument/author/assignedAuthor/id@root exists (PASS)</report>
        <!-- ClinicalDocument/author/assignedAuthor//assignedAuthoringDevice/code -->
        <assert test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code]]]">ERR: ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/code is missing</assert>
        <report test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code]]]">CTX: ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/code exists (PASS)</report>
        <assert test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@codeSystem='1.2.840.10008.2.16.4']]]]">ERR: ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/code@codeSystem wrong value</assert>
        <report test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@codeSystem='1.2.840.10008.2.16.4']]]]">CTX: ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/code@codeSystem correct (PASS)</report>
        <assert test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@code='CAPTURE']]]] or cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@code='WST']]]]">ERR:
        ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/code@code wrong value</assert>
        <report test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@code='CAPTURE']]]] or cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@code='WST']]]]">CTX:
        ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/code@code correct (PASS)</report>
        <assert test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@displayName='Image Capture']]]] or cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@displayName='Workstation']]]]">ERR:
        ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/code@displayName wrong value</assert>
        <report test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@displayName='ImageCapture']]]] or cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:code[@displayName='Workstation']]]]">CTX:
        ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/code@displayName correct (PASS)</report>
        <!-- ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/manufacturerModelName -->
        <assert test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:manufacturerModelName]]]">ERR: ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/manufacturerModelName is missing</assert>
        <report test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:manufacturerModelName]]]">CTX: ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/manufacturerModelName exists (PASS)</report>
        <!-- ClinicalDocument/author/assignedAuthor//assignedAuthoringDevice/softwareName -->
        <assert test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:softwareName]]]">ERR: ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/softwareName is missing</assert>
        <report test="cda:author[cda:assignedAuthor[cda:assignedAuthoringDevice[cda:softwareName]]]">CTX: ClinicalDocument/author/assignedAuthor/assignedAuthoringDevice/softwareModelName exists (PASS)</report>
        <!-- ClinicalDocument/author/assignedAuthor/representedOrganization@root -->
        <assert test="cda:author[cda:assignedAuthor[cda:representedOrganization[cda:id[@root]]]]">ERR: ClinicalDocument/author/assignedAuthor/representedOrganization@root is missing</assert>
        <report test="cda:author[cda:assignedAuthor[cda:representedOrganization[cda:id[@root]]]]">CTX: ClinicalDocument/author/assignedAuthor/representedOrganization@root exists (PASS)</report>
      </rule>
    </pattern>
    <!-- ClinicalDocument/recordTarget/author (original):  no checks for these elements; only required if known Ref. ITI TF-1:7.1.8.2.3-->
    <pattern name="ClinicalDocument/dataEnterer elements -- Reference ITI TF-1:7.1.8.2.5">
      <rule context="cda:ClinicalDocument">
        <assert test="cda:dataEnterer">ERR: ClinicalDocument/dataEnterer is missing</assert>
        <report test="cda:dataEnterer">CTX: ClinicalDocument/dataEnterer exists (PASS)</report>
        <!-- ClinicalDocument/dataEnterer/time -->
        <assert test="cda:dataEnterer[cda:time]">ERR: ClinicalDocument/dataEnterer/time is missing</assert>
        <report test="cda:dataEnterer[cda:time]">CTX: ClinicalDocument/dataEnterer/time exists (PASS)</report>
        <!-- ClinicalDocument/dataEnterer/assignedEntity/id@root and @extension -->
        <assert test="cda:dataEnterer[cda:assignedEntity[cda:id[@root]]]">ERR: ClinicalDocument/dataEnterer/assignedEntity/id@root is missing</assert>
        <report test="cda:dataEnterer[cda:assignedEntity[cda:id[@root]]]">CTX: ClinicalDocument/dataEnterer/assignedEntity/id@root exists (PASS)</report>
        <assert test="cda:dataEnterer[cda:assignedEntity[cda:id[@extension]]]">ERR: ClinicalDocument/dataEnterer/assignedEntity/id@extension is missing</assert>
        <report test="cda:dataEnterer[cda:assignedEntity[cda:id[@extension]]]">CTX: ClinicalDocument/dataEnterer/assignedEntity/id@extension exists (PASS)</report>
      </rule>
    </pattern>
    <pattern name="ClinicalDocument/custodian elements -- Reference ITI TF-1:7.1.8.2.6">
      <rule context="cda:ClinicalDocument">
        <assert test="cda:custodian">ERR: ClinicalDocument/custodian is missing</assert>
        <report test="cda:custodian">CTX: ClinicalDocument/custodian exists (PASS)</report>
        <!-- ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization/name and addr-->
        <assert test="cda:custodian[cda:assignedCustodian[cda:representedCustodianOrganization[cda:name]]]">ERR: ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization/name is missing</assert>
        <report test="cda:custodian[cda:assignedCustodian[cda:representedCustodianOrganization[cda:name]]]">CTX: ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization/name exists (PASS)</report>
        <assert test="cda:custodian[cda:assignedCustodian[cda:representedCustodianOrganization[cda:addr]]]">ERR: ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization/addr is missing</assert>
        <report test="cda:custodian[cda:assignedCustodian[cda:representedCustodianOrganization[cda:addr]]]">CTX: ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization/addr exists (PASS)</report>
        <assert test="cda:custodian[cda:assignedCustodian[cda:representedCustodianOrganization[cda:addr[cda:country]]]]">ERR: ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization/addr/country is missing</assert>
        <report test="cda:custodian[cda:assignedCustodian[cda:representedCustodianOrganization[cda:addr[cda:country]]]]">CTX: ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization/addr/country exists (PASS)</report>
      </rule>
    </pattern>
    <!-- ClinicalDocument/legalAuthenticator:  no checks for these elements; not required Ref. ITI TF-1:7.1.8.2.7-->
    <!-- ClinicalDocument/documentationOf:  no checks for these elements; only required if known Ref. ITI TF-1:7.1.8.2.8-->
    <pattern name="ClinicalDocument/component/nonXMLBody -- Reference ITI TF-1:7.1.8.2.9">
      <rule context="cda:ClinicalDocument">
        <assert test="cda:component[cda:nonXMLBody]">ERR: ClinicalDocument/component/nonXMLBody is missing</assert>
        <report test="cda:component[cda:nonXMLBody]">CTX: ClinicalDocument/component/nonXMLBody exists (PASS)</report>
        <!-- ClinicalDocument/component/nonXMLBody/text -->
        <assert test="cda:component[cda:nonXMLBody[cda:text[@mediaType='application/pdf']]] or cda:component[cda:nonXMLBody[cda:text[@mediaType='text/plain']]]">ERR: ClinicalDocument/component/nonXMLBody/text@mediaType wrong value</assert>
        <report test="cda:component[cda:nonXMLBody[cda:text[@mediaType='application/pdf']]] or cda:component[cda:nonXMLBody[cda:text[@mediaType='text/plain']]]">CTX: ClinicalDocument/component/nonXMLBody/text@mediaType correct (PASS)</report>
        <assert test="cda:component[cda:nonXMLBody[cda:text[@representation='B64']]]">ERR: ClinicalDocument/component/nonXMLBody/text@representation must be B64</assert>
        <report test="cda:component[cda:nonXMLBody[cda:text[@representation='B64']]]">CTX: ClinicalDocument/component/nonXMLBody/text@representation correct (PASS)</report>
      </rule>
    </pattern>
    <pattern xmlns:xi="http://www.w3.org/2003/XInclude" xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.1.1-errors" xml:base="templates/errors/header.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.1-errors" xml:base="templates/errors/header.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.4-errors" xml:base="templates/errors/header.sch">
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
    <pattern xmlns="http://purl.oclc.org/dsdl/schematron" id="p-1.3.6.1.4.1.19376.1.5.3.1.2.3-errors" xml:base="templates/errors/header.sch">
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
  </phase>
</schema>
