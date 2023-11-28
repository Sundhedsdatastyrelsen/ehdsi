curl --insecure \
     --cert test/client-dk.crt \
     --key test/client-dk.key \
     --location 'https://localhost:6443/openncp-ws-server/services/XCPD_Service/' \
     --header 'Content-Type: application/soap+xml' \
     --header 'SOAPAction: urn:hl7-org:v3:PRPA_IN201305UV02:CrossGatewayPatientDiscovery' \
     --data '<?xml version="1.0" encoding="utf-8"?>
<soap12:Envelope xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
<soap12:Header xmlns:addressing="http://www.w3.org/2005/08/addressing" xmlns:spirit="spirit:soap:transaction:1.0">
    <addressing:Action soap12:mustUnderstand="1">urn:hl7-org:v3:PRPA_IN201305UV02:CrossGatewayPatientDiscovery</addressing:Action>
    <addressing:MessageID>uuid:C3F5A03D-1A0C-4F62-ADC7-F3C007CD50CF</addressing:MessageID>
    <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
      <saml2:Assertion xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion" xmlns:xs="http://www.w3.org/2001/XMLSchema" ID="_70e21708-5d2a-4c68-91a1-da667980f338" IssueInstant="2014-11-10T21:18:13.506Z" Version="2.0">
        <saml2:Issuer Format="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified">urn:tiani-spirit:sts
        </saml2:Issuer>
        <ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
          <ds:SignedInfo>
            <ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
            <ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#rsa-sha1"/>
            <ds:Reference URI="#_70e21708-5d2a-4c68-91a1-da667980f338">
              <ds:Transforms>
                <ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
                <ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#">
                  <ec:InclusiveNamespaces xmlns:ec="http://www.w3.org/2001/10/xml-exc-c14n#" PrefixList="xs"/>
                </ds:Transform>
              </ds:Transforms>
              <ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1"/>
              <ds:DigestValue>4HlJr9mA72OMob2KH6iN6gDhGAw=</ds:DigestValue>
            </ds:Reference>
          </ds:SignedInfo>
          <ds:SignatureValue>
                        1hgPAOfbgj1bXEUAL9COrbDLRW5VjdsRcpr3KAKWur9k/cxZNYid+jyOh9Cag3jhfkqPP9eeUZhZ
                        EwGd1LHnzKWB5hDJ5+2HT3kCL16gv3H02hbzRXqqhts8uTSvDXV8qrV6RmK+Vez5h2/+oliG4m3V
                        Mn89Y5ZpMeawJZDTjKz3t+y4lQ7JRkdaDtpFklCGAy+tB6/zA9yxJzwcRNNF8SXspPTtYbYjLUAQ
                        pOM+vRJZeOP3ML6oCjxNKh2dgyUpZV5bnNcGKAQA2GYi8qXlhkoqCmYrIonf4Bz6Yr5pSxtAhXoO
                        5gPaVdndTUTbTz1VLZd2RuSQJCSealx95fzfrA==
                    </ds:SignatureValue>
        </ds:Signature>
        <saml2:Subject>
          <saml2:NameID Format="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress">bryantj@inhs.org
          </saml2:NameID>
          <saml2:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:bearer"/>
        </saml2:Subject>
        <saml2:Conditions NotBefore="2023-11-28T14:18:13.506Z" NotOnOrAfter="2023-11-28T17:18:13.506Z">
          <saml2:AudienceRestriction>
            <saml2:Audience>http://ihe.connecthaton.XUA/X-ServiceProvider-IHE-Connectathon</saml2:Audience>
          </saml2:AudienceRestriction>
        </saml2:Conditions>
        <saml2:AuthnStatement AuthnInstant="2014-11-10T21:18:13.506Z" SessionIndex="123456">
          <saml2:AuthnContext>
            <saml2:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:Password
                        </saml2:AuthnContextClassRef>
          </saml2:AuthnContext>
        </saml2:AuthnStatement>
        <saml2:AttributeStatement>
          <saml2:Attribute FriendlyName="XSPA Subject" Name="urn:oasis:names:tc:xspa:1.0:subject:subject-id" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri">
            <saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">Bryant Jerimiah
                        </saml2:AttributeValue>
          </saml2:Attribute>
          <saml2:Attribute FriendlyName="XSPA Organization" Name="urn:oasis:names:tc:xspa:1.0:subject:organization" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri">
            <saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">INHS
                        </saml2:AttributeValue>
          </saml2:Attribute>
          <saml2:Attribute FriendlyName="XSPA Organization ID" Name="urn:oasis:names:tc:xspa:1.0:subject:organization-id" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri">
            <saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">2.16.840.1.113883.3.607
                        </saml2:AttributeValue>
          </saml2:Attribute>
          <saml2:Attribute FriendlyName="XCA Home Community ID" Name="urn:ihe:iti:xca:2010:homeCommunityId" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri">
            <saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:anyURI">urn:oid:2.16.840.1.113883.3.607.1.990.2.1.1.21
                        </saml2:AttributeValue>
          </saml2:Attribute>
          <saml2:Attribute Name="urn:oasis:names:tc:xacml:2.0:subject:role">
            <saml2:AttributeValue>
              <Role xmlns="urn:hl7-org:v3" code="112247003" codeSystem="2.16.840.1.113883.6.96" codeSystemName="SNOMED_CT" displayName="Medical Doctor"/>
            </saml2:AttributeValue>
          </saml2:Attribute>
          <saml2:Attribute Name="urn:oasis:names:tc:xacml:1.0:resource:resource-id" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri">
            <saml2:AttributeValue>2121238989</saml2:AttributeValue>
          </saml2:Attribute>
          <saml2:Attribute Name="urn:oasis:names:tc:xspa:1.0:subject:purposeofuse">
            <saml2:AttributeValue>
              <PurposeOfUse xmlns="urn:hl7-org:v3" code="TREATMENT" codeSystem="2.16.840.1.113883.3.18.7.1" codeSystemName="nhin-purpose" displayName="Treatment"/>
            </saml2:AttributeValue>
          </saml2:Attribute>
        </saml2:AttributeStatement>
      </saml2:Assertion>
    </wsse:Security>
  </soap12:Header>
  <soap12:Body>
<PRPA_IN201305UV02 ITSVersion="XML_1.0" xmlns="urn:hl7-org:v3">
    <id extension="4733101d-aaa4-49af-be0f-bf8e16c1a97d" root="4ad4e0af-59bc-4233-aa50-27ff21883104"/>
    <creationTime value="20120531131021.710+0300"/>
    <versionCode code="V3PR1"/>
    <interactionId extension="PRPA_IN201305UV02" root="2.16.840.1.113883.1.6"/>
    <processingCode code="P"/>
    <processingModeCode code="T"/>
    <acceptAckCode code="AL"/>
    <receiver typeCode="RCV">
        <device classCode="DEV" determinerCode="INSTANCE">
            <id root="2.16.17.710.820.1000.990.1"/>
            <asAgent classCode="AGNT">
                <representedOrganization classCode="ORG" determinerCode="INSTANCE">
                    <id root="2.16.17.710.820.1000.990.1"/>
                </representedOrganization>
            </asAgent>
        </device>
    </receiver>
    <sender typeCode="SND">
        <device classCode="DEV" determinerCode="INSTANCE">
            <id root="2.16.999.1000.990.1"/>
            <asAgent classCode="AGNT">
                <representedOrganization classCode="ORG" determinerCode="INSTANCE">
                    <id root="2.16.999.1000.990.1"/>
                </representedOrganization>
            </asAgent>
        </device>
    </sender>
    <controlActProcess classCode="CACT" moodCode="EVN">
        <code code="PRPA_TE201305UV02" codeSystemName="2.16.840.1.113883.1.6"/>
        <authorOrPerformer typeCode="AUT">
            <assignedPerson classCode="ASSIGNED"/>
        </authorOrPerformer>
        <queryByParameter>
            <queryId root="1.263507841149"/>
            <statusCode code="new"/>
            <responseModalityCode code="R"/>
            <responsePriorityCode code="I"/>
            <matchCriterionList/>
            <parameterList>
                <livingSubjectId>
                    <value extension="1" root="2.16.999.1000.990.1"/>
                    <semanticsText/>
                </livingSubjectId>
            </parameterList>
        </queryByParameter>
    </controlActProcess>
</PRPA_IN201305UV02>


  </soap12:Body>
</soap12:Envelope>
'
