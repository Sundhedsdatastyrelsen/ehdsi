package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationException;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.AuthenticationService;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.config.OptOutConfig;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DiscardDispensationRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForPatientSummaryDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.FindDocumentsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.FindPatientsResponseDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFetchDocumentRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFindEPrescriptionDocumentsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.PostFindPatientsRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.SubmitDispensationRequestDto;
import dk.sundhedsdatastyrelsen.ncpeh.optout.OptOutService;
import dk.sundhedsdatastyrelsen.ncpeh.optout.OptOutServiceImpl;
import dk.sundhedsdatastyrelsen.ncpeh.service.CprService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PatientSummaryService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService;
import dk.sundhedsdatastyrelsen.ncpeh.service.PrescriptionService.PrescriptionFilter;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@RestController
public class Controller {
    private final PrescriptionService prescriptionService;
    private final PatientSummaryService patientSummaryService;
    private final CprService cprService;
    private final AuthenticationService authenticationService;
    private final NspDgwsIdentity.System systemIdentity;
    private final OptOutService optOutService;

    public Controller(
        PrescriptionService prescriptionService,
        PatientSummaryService patientSummaryService,
        CprService cprService,
        AuthenticationService authenticationService,
        NspDgwsIdentity.System systemIdentity,
        OptOutConfig optOutConfig
    ) {
        this.prescriptionService = prescriptionService;
        this.patientSummaryService = patientSummaryService;
        this.cprService = cprService;
        this.authenticationService = authenticationService;
        this.systemIdentity = systemIdentity;

        if (optOutConfig.disabled()) {
            log.warn("Opt-out integration is disabled. This should not happen in production!");
            this.optOutService = OptOutService.never();
        } else {
            this.optOutService = new OptOutServiceImpl(optOutConfig.config());
        }
    }

    @PostMapping(path = "/api/find-patients/")
    public FindPatientsResponseDto findPatients(
        @Valid @RequestBody PostFindPatientsRequestDto params
    ) {
        return cprService.findPatients(params.getPatientIds());
    }

    @PostMapping(path = "/api/find-eprescription-documents/")
    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        @Valid @RequestBody PostFindEPrescriptionDocumentsRequestDto params
    ) {
        checkOptOut(params.getPatientId(), OptOutService.Service.EPRESCRIPTION);
        var filter = PrescriptionFilter.fromRootedId(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
        return prescriptionService.findEPrescriptionDocuments(
            params.getPatientId(),
            filter,
            this.getFmkToken(params.getSoapHeader()));
    }

    /// There is only one patient summary per patient, but the MyHealth@EU api functions like a document repository, so
    /// we have to let them search for that one document. We could also have returned it directly in the NCP adapter,
    /// but we want all the business logic in this application.
    @PostMapping(path = "/api/find-patient-summary-document/")
    public DocumentAssociationForPatientSummaryDocumentMetadataDto findPatientSummaryDocument(
        @Valid @RequestBody FindDocumentsRequestDto params
    ) {
        checkOptOut(params.getPatientId(), OptOutService.Service.PATIENT_SUMMARY);
        // TODO should maybe be a user identity instead? It's not used in patient summary yet.
        return patientSummaryService.getDocumentMetadata(params.getPatientId(), systemIdentity);
    }

    @PostMapping(path = "/api/fetch-document/")
    public List<EpsosDocumentDto> fetchDocument(
        @Valid @RequestBody PostFetchDocumentRequestDto params
    ) {
        var repoId = params.getRepositoryId();
        if (Objects.equals(repoId, Oid.DK_EPRESCRIPTION_REPOSITORY_ID.value)) {
            checkOptOut(params.getPatientId(), OptOutService.Service.EPRESCRIPTION);
            var filter = PrescriptionFilter.fromRootedId(params.getDocumentId(), params.getCreatedBefore(), params.getCreatedAfter());
            return prescriptionService.getPrescriptions(params.getPatientId(), filter, this.getFmkToken(params.getSoapHeader()));
        } else if (Objects.equals(repoId, Oid.DK_PATIENT_SUMMARY_REPOSITORY_ID.value)) {
            checkOptOut(params.getPatientId(), OptOutService.Service.PATIENT_SUMMARY);
            return patientSummaryService.getPatientSummary(
                params.getPatientId(),
                params.getDocumentId(),
                systemIdentity,
                // TODO pick out the right identity from the soap header.
                "MT^94e9cd39-f9c2-434c-9069-ee8bd81b11c1");
        }

        // TODO better exception
        throw new RuntimeException("Unknown repository id " + repoId);
    }

    @PostMapping(path = "/api/edispensation/submit")
    public void submitDispensation(
        @Valid @RequestBody SubmitDispensationRequestDto request
    ) {
        checkOptOut(request.getPatientId(), OptOutService.Service.EPRESCRIPTION);
        try {
            prescriptionService.submitDispensation(
                request.getPatientId(),
                Utils.readXmlDocument(request.getDocument()),
                this.getFmkToken(request.getSoapHeader()));
        } catch (SAXException e) {
            log.error("Could not read XML document in request", e);
        } catch (Exception e) {
            log.error("Dispensation failed", e);
            // Debug logging so we can see the full document in development.
            log.debug("patient id {}, class code {}", request.getPatientId(), request.getClassCode().toString());
            log.debug("SOAP Header: {}", request.getSoapHeader());
            log.debug("Request document: {}", request.getDocument());
            throw e;
        }
    }

    @PostMapping(path = "/api/edispensation/discard")
    public void discardDispensation(
        @Valid @RequestBody DiscardDispensationRequestDto request
    ) {
        try {
            prescriptionService.undoDispensation(
                request.getDiscardDispenseDetails().getPatientId(),
                Utils.readXmlDocument(request.getDispensationToDiscard().getDocument()),
                this.getFmkToken(request.getSoapHeader()));
        } catch (SAXException e) {
            log.error("Could not read XML document in request", e);
        } catch (Exception e) {
            log.error("Dispensation discard failed.", e);
            // Debug logging so we can see the full document in development.
            log.debug(
                "patient id {}, class code {}",
                request.getDispensationToDiscard().getPatientId(),
                request.getDispensationToDiscard().getClassCode().toString());
            log.debug("SOAP Header: {}", request.getSoapHeader());
            log.debug("Request document: {}", request.getDispensationToDiscard().getDocument());
            throw e;
        }
    }

    private EuropeanHcpIdwsToken getFmkToken(String soapHeader) {
        try {
            return this.authenticationService.xcaSoapHeaderToIdwsToken(hackyWorkaroundForFmkBugRequiringSpecificRole(soapHeader), "https://fmk");
        } catch (AuthenticationException e) {
            throw new CountryAException(401, "Could not authenticate.", e);
        }
    }

    private void checkOptOut(String cpr, OptOutService.Service service) {
        if (optOutService.hasOptedOut(cpr, service)) {
            throw new CountryAException(400, "Citizen has opted out of service: %s".formatted(service));
        }
    }

    /// DO NOT PUT THIS INTO PRODUCTION
    ///
    /// FMK wrongly requires a specific role element and a specific healthcare facility type.  We hardcode these to
    /// be able to perform tests with FMK.
    private String hackyWorkaroundForFmkBugRequiringSpecificRole(String soapHeader) {
        // - replace <Role.../> element with one accepted by FMK
        var rolePattern = Pattern.compile("<Role[^>]+>");
        var fmkAcceptsThisRoleElement = "<Role xmlns=\"urn:hl7-org:v3\" code=\"2262\" codeSystem=\"2.16.840.1.113883.2.9.6.2.7\" codeSystemName=\"ISCO\" displayName=\"Pharmacists\"/>";

        var withHardcodedRole = rolePattern.matcher(soapHeader).replaceFirst(fmkAcceptsThisRoleElement);

        // - replace Attribute with name urn:ehdsi:names:subject:healthcare-facility-type
        // to "Pharmacy"
        var facilityTypePattern = Pattern.compile("<saml2:Attribute\\b[^>]*\\bName\\s*=\\s*\"urn:ehdsi:names:subject:healthcare-facility-type\"[^>]*>.*?</saml2:Attribute>");
        var fmkAcceptsThisFacilityTypeElement = "<saml2:Attribute FriendlyName=\"eHealth DSI Healthcare Facility Type\" Name=\"urn:ehdsi:names:subject:healthcare-facility-type\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\"><saml2:AttributeValue xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xsd:string\">Pharmacy</saml2:AttributeValue></saml2:Attribute>";
        return facilityTypePattern.matcher(withHardcodedRole).replaceFirst(fmkAcceptsThisFacilityTypeElement);
    }

    // Example of soapHeader before replacements
    // <soapenv:Header xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope" xmlns:wsa="http://www.w3.org/2005/08/addressing"><addressing:To xmlns:addressing="http://www.w3.org/2005/08/addressing" soapenv:mustUnderstand="1">https://ncp-training.ehdsi.sundhedsdata.dk:8443/openncp-ws-server/services/XCA_Service</addressing:To><addressing:Action xmlns:addressing="http://www.w3.org/2005/08/addressing" soapenv:mustUnderstand="1">urn:ihe:iti:2007:CrossGatewayQuery</addressing:Action><addressing:MessageID xmlns:addressing="http://www.w3.org/2005/08/addressing">urn:uuid:7131ec3a-f0e3-4960-8873-dfff3f9e82c2</addressing:MessageID><addressing:ReplyTo xmlns:addressing="http://www.w3.org/2005/08/addressing"><addressing:Address>http://www.w3.org/2005/08/addressing/anonymous</addressing:Address></addressing:ReplyTo><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"><saml2:Assertion xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion" xmlns:xsd="http://www.w3.org/2001/XMLSchema" ID="_c82b7e98-a5db-41ad-bb97-9073c60e0b06" IssueInstant="2025-09-01T12:55:09.654Z" Version="2.0"><saml2:Issuer NameQualifier="urn:ehdsi:assertions:hcp">urn:idp:DK:countryB</saml2:Issuer><ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
    //<ds:SignedInfo>
    //<ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
    //<ds:SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
    //<ds:Reference URI="#_c82b7e98-a5db-41ad-bb97-9073c60e0b06">
    //<ds:Transforms>
    //<ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
    //<ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"><ec:InclusiveNamespaces xmlns:ec="http://www.w3.org/2001/10/xml-exc-c14n#" PrefixList="xsd"/></ds:Transform>
    //</ds:Transforms>
    //<ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
    //<ds:DigestValue>wgk1rHKx01KRxYGaLuwkIHZTE2N1ZtOWANZtLdSVo1I=</ds:DigestValue>
    //</ds:Reference>
    //</ds:SignedInfo>
    //<ds:SignatureValue>
    //ZyY2dHNDAFqmM/3s1Ex5ZQMQQF7OVw/LgwTRr2DHR1m4oE0DeR9wZowZMVm8t7Ukd+sYglwbYd85&#xD;
    //DS4acOwB5SIuKqkAd6/KD652+M5pgCtg2i0UMSn8DDmGOuZiM6HN3QiSBeumd9hO7URnMK14yzwv&#xD;
    //y5K0L/riSzzJjf2uJ07LD3FO5Z3J2pdXol6iustLNSAkhJhM2eUHOsIQoh0DKMevxqR0Fwo0NQTv&#xD;
    //YIzMXkNGA2YC7FTm6Ejb1ZdYYHenoUarP+UdeMuDwiTj0gnJXhObwGHme/Z05gJkOB1qh/SXItzi&#xD;
    //nJbShCQ3NZ5KQN6hUZOBd4HXoCPR3qW2C43mkPDSYk8Kw7j7FqXhbXKfhVCkQiLs8sne3RopCR7b&#xD;
    //k2tefERu9UVFybMHjXZL23UONuJYKUxjytP2ZaQXJg2B1bkiT11Ilh2JKc2Lbpvh3+CYdV1raE+g&#xD;
    //rlrOw1SVjvt6nlWnsIoFyHdiXdooDiTix16REjw7fb+iCZsd3Plcu6IEpKeZ070s+PtrR+0dYnGQ&#xD;
    //eZ0bTEvcGqTB3f5oaJSI8zz/aPmobu0s2NKMFVkdgjqFAYJc4MQSEyZ52mGt/aoxiKXmapc0ufxh&#xD;
    //hgJjJaNJVkrVjdESCQv7Y78vRiS3N+pZCtbH/3bVMsD4B/rvKBHGvW0VHda8lPUwCJDvXva9HD4=
    //</ds:SignatureValue>
    //<ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIEKTCCAxGgAwIBAgICAMUwDQYJKoZIhvcNAQENBQAwUTELMAkGA1UEBhMCQkUxHDAaBgNVBAoME0V1cm9wZWFuIENvbW1pc3Npb24xETAPBgNVBAMMCEVIRFNJIENBMREwDwYDVQQLDAhERyBTYW50ZTAeFw0yNDEwMDcxMzM2NTNaFw0zNDEwMDcxMzM2NTNaMF4xCzAJBgNVBAYTAkRLMSkwJwYDVQQKDCBUaGUgRGFuaXNoIEhlYWx0aCBEYXRhIEF1dGhvcml0eTEkMCIGA1UEAwwbR1JQOkVIRUFMVEhfTkNQX0JPT1RDQU1QX0RLMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtpXCcVejHR1AGvKA06LaxGWwyXRQw4U3W118l5RU+BNdOgD7nvC0ujvi05ESdnL7SKrGeVosh1qyyIBGbNCdZTYqGsYLkIkiRb0cP/YRCCfW/NuEcW22sdezVgPGbv1vgIEaINkDkXHM4s5yZEi55JyBuNGG19ghNfwxnhgqge54GdLhOPct5K+m06AgGuRTEU4mR/uBRdM4sue0WfOVkw4bF9JTeNoU5YI0qp1q8nSsuJAwsOGaNPd7Q0garSeT8WXl/q8Z5RijdnUcPnznvMbri+JhryVH9n33S6ejFQ06mQHswn1006ZJkoGXVtv7VCmVtzUZtO34p1w8HiVnRpQ/K907uJEMkY5kOxrzpuMv0hf7veKGMSDwyW1hgd9UHV/fk4rtKY5LfNueFWjpXNcvW1YpRZHSCa7T0a3HvrnIC2HaZszt8ALX5RtTmqH7nyAhHHUX2eY6bGseuM1+x+55n215DuK7rV6kMVd4taOQcbmeTmtwQp6Kc4oUXpYDUTpUu+xlzV9thDTqnl6cnwXuGHb1b9s6TvXv2ouiQ4RV91u+XY1+YLdEKHtAKVfekaWQU//vdeRqORfEpj4PbzoIQsEgW01/xLMzgw2BsMtiHE9+yk+v/ljzFxGDdgmryCu6ODX4Hol5jSXwtGHa5KDl9zZTwtSSZp2axxURinECAwEAATANBgkqhkiG9w0BAQ0FAAOCAQEAEPj3ChZY4indo6n6dUxqcZWmXizDYWHW1KCbac4SXMfpv4lOfUK/lbuuDxGZEIdYDf1A4kelBfky9sS5k1zsPnn8O6wHboLKbFiXZzOElfa0uSjY+IO3Fe73xvnW762xickZ8g5EdMhm+wUy5PWbUFMYruyiOAVScTCP90Kp9DCDYBuHUd6KzNF5V458QrKLb7uNruK0hoXrExHMjbAeM5hIFfDz477Y84Suh6XSNA1istQ8Stfw1H8hSIiFrRbPf+57IfLWQ/SYM/ghY4ZGIiEn1dXBpSeyjlAeU3sr4rxCm1zCDd8DniYcITTXrwND/3V/5FmtSUX4V4Oh7MXinA==</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature><saml2:Subject><saml2:NameID Format="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress">house@ehdsi.eu</saml2:NameID><saml2:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:sender-vouches"/></saml2:Subject><saml2:Conditions NotBefore="2025-09-01T12:55:09.654Z" NotOnOrAfter="2025-09-01T16:55:09.654Z"><saml2:AudienceRestriction><saml2:Audience>urn:ehdsi:assertions.audience:x-border</saml2:Audience></saml2:AudienceRestriction></saml2:Conditions><saml2:AuthnStatement AuthnInstant="2025-09-01T12:55:09.654Z"><saml2:AuthnContext><saml2:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:SmartcardPKI</saml2:AuthnContextClassRef></saml2:AuthnContext></saml2:AuthnStatement><saml2:AttributeStatement><saml2:Attribute FriendlyName="HCI Identifier" Name="urn:ihe:iti:xca:2010:homeCommunityId" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oid:1.2.208</saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="NPI Identifier" Name="urn:oasis:names:tc:xspa:1.0:subject:npi" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">Denmark</saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="XSPA Subject" Name="urn:oasis:names:tc:xspa:1.0:subject:subject-id" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">John House</saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="XSPA Role" Name="urn:oasis:names:tc:xacml:2.0:subject:role" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue><Role xmlns="urn:hl7-org:v3" code="221" codeSystem="2.16.840.1.113883.2.9.6.2.7" codeSystemName="ISCO" displayName="Medical Doctors"/></saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="XSPA Organization" Name="urn:oasis:names:tc:xspa:1.0:subject:organization" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">eHealth OpenNCP EU Portal</saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="XSPA Organization ID" Name="urn:oasis:names:tc:xspa:1.0:subject:organization-id" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:hl7ii:1.2.3.4:ABCD</saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="eHealth DSI Healthcare Facility Type" Name="urn:ehdsi:names:subject:healthcare-facility-type" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">Resident Physician</saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="XSPA Purpose Of Use" Name="urn:oasis:names:tc:xspa:1.0:subject:purposeofuse" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue><PurposeOfUse xmlns="urn:hl7-org:v3" code="TREATMENT" codeSystem="3bc18518-d305-46c2-a8d6-94bd59856e9e" codeSystemName="eHDSI XSPA PurposeOfUse" displayName="TREATMENT"/></saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="XSPA Locality" Name="urn:oasis:names:tc:xspa:1.0:environment:locality" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">eHDSI EU Testing MedCare Center</saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="Hl7 Permissions" Name="urn:oasis:names:tc:xspa:1.0:subject:hl7:permission" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-003</saml2:AttributeValue><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-004</saml2:AttributeValue><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-005</saml2:AttributeValue><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-006</saml2:AttributeValue><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-010</saml2:AttributeValue><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PRD-016</saml2:AttributeValue><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PPD-032</saml2:AttributeValue><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PPD-033</saml2:AttributeValue><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">urn:oasis:names:tc:xspa:1.0:subject:hl7:permission:PPD-046</saml2:AttributeValue></saml2:Attribute></saml2:AttributeStatement></saml2:Assertion><saml2:Assertion xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion" xmlns:xsd="http://www.w3.org/2001/XMLSchema" ID="_e3dc4e43-d1ad-4710-b8d6-deba02b6ef74" IssueInstant="2025-09-01T12:55:09.921Z" Version="2.0"><saml2:Issuer NameQualifier="urn:ehdsi:assertions:trc">urn:initgw:DK:countryB</saml2:Issuer><ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
    //<ds:SignedInfo>
    //<ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
    //<ds:SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
    //<ds:Reference URI="#_e3dc4e43-d1ad-4710-b8d6-deba02b6ef74">
    //<ds:Transforms>
    //<ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
    //<ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"><ec:InclusiveNamespaces xmlns:ec="http://www.w3.org/2001/10/xml-exc-c14n#" PrefixList="xsd"/></ds:Transform>
    //</ds:Transforms>
    //<ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
    //<ds:DigestValue>IotEXihhnlZYN3LXj0eH8pe7Co9wlFYkat7JMGFwzS8=</ds:DigestValue>
    //</ds:Reference>
    //</ds:SignedInfo>
    //<ds:SignatureValue>
    //mg+2z/sH1aLIYNkBwwWEpxlzXXvQc110B4WoWI4anEnLtz7cphTm2cypFr8KneQMCMHTSfhwCxqb&#xD;
    //J2p2Dew2sI8/iyc0ESYs/JD8GfHzMFBKjRE6DdbrnkVOnhlfg+kl1sK4BwhwxsotYbEJFktszW3M&#xD;
    //fhm9OmxCOfX75GGIfv0YouIDa14pG+5bHYEAYbCDwARdJ13/uDOo1zImMuFtxlIF1RbL0UcB3lbV&#xD;
    //0X4r2YrbDu6iZBlc4LLG66d90m4BpQzGc/5NCjGTB5FemNZnQVokWUtky+4LTFCAUEW2wZk9slLD&#xD;
    //N1myW71BfV/5A0kkQjteuiox2lF3qjSxdMtb/3JFcuGpegple4tYSnAe4G2QTXwnHdMZMvDQsDII&#xD;
    //NRB/zV4Pdfrl4NjRfjWl5yBdVehwZIblfC1CQ3CzHZ2qKAlwNHxynFdBBWS1ymvohZ+nyBmHY49y&#xD;
    //EKDlHK76ndD6LX/W5pYU4Ag4T+UrakGzalqwpa0oVfsJ++KDGBESaeuOt2ar1yeEX5gu4KKmCJRO&#xD;
    //MZhuizBgizqQM6RuzgvYsod4Vh+5H4xd/G+eYheVLe2ILhbKitY1kfvzyan2VSHo//jneEZ04dbC&#xD;
    //ox4U15xS2+Nr0fmlbpWzDx/jGvxKhHD7isXrDf5jlPpHTJfNr1KhwTey/RkJSWujGX0gwY6IiTk=
    //</ds:SignatureValue>
    //<ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIIEKTCCAxGgAwIBAgICAMUwDQYJKoZIhvcNAQENBQAwUTELMAkGA1UEBhMCQkUxHDAaBgNVBAoME0V1cm9wZWFuIENvbW1pc3Npb24xETAPBgNVBAMMCEVIRFNJIENBMREwDwYDVQQLDAhERyBTYW50ZTAeFw0yNDEwMDcxMzM2NTNaFw0zNDEwMDcxMzM2NTNaMF4xCzAJBgNVBAYTAkRLMSkwJwYDVQQKDCBUaGUgRGFuaXNoIEhlYWx0aCBEYXRhIEF1dGhvcml0eTEkMCIGA1UEAwwbR1JQOkVIRUFMVEhfTkNQX0JPT1RDQU1QX0RLMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtpXCcVejHR1AGvKA06LaxGWwyXRQw4U3W118l5RU+BNdOgD7nvC0ujvi05ESdnL7SKrGeVosh1qyyIBGbNCdZTYqGsYLkIkiRb0cP/YRCCfW/NuEcW22sdezVgPGbv1vgIEaINkDkXHM4s5yZEi55JyBuNGG19ghNfwxnhgqge54GdLhOPct5K+m06AgGuRTEU4mR/uBRdM4sue0WfOVkw4bF9JTeNoU5YI0qp1q8nSsuJAwsOGaNPd7Q0garSeT8WXl/q8Z5RijdnUcPnznvMbri+JhryVH9n33S6ejFQ06mQHswn1006ZJkoGXVtv7VCmVtzUZtO34p1w8HiVnRpQ/K907uJEMkY5kOxrzpuMv0hf7veKGMSDwyW1hgd9UHV/fk4rtKY5LfNueFWjpXNcvW1YpRZHSCa7T0a3HvrnIC2HaZszt8ALX5RtTmqH7nyAhHHUX2eY6bGseuM1+x+55n215DuK7rV6kMVd4taOQcbmeTmtwQp6Kc4oUXpYDUTpUu+xlzV9thDTqnl6cnwXuGHb1b9s6TvXv2ouiQ4RV91u+XY1+YLdEKHtAKVfekaWQU//vdeRqORfEpj4PbzoIQsEgW01/xLMzgw2BsMtiHE9+yk+v/ljzFxGDdgmryCu6ODX4Hol5jSXwtGHa5KDl9zZTwtSSZp2axxURinECAwEAATANBgkqhkiG9w0BAQ0FAAOCAQEAEPj3ChZY4indo6n6dUxqcZWmXizDYWHW1KCbac4SXMfpv4lOfUK/lbuuDxGZEIdYDf1A4kelBfky9sS5k1zsPnn8O6wHboLKbFiXZzOElfa0uSjY+IO3Fe73xvnW762xickZ8g5EdMhm+wUy5PWbUFMYruyiOAVScTCP90Kp9DCDYBuHUd6KzNF5V458QrKLb7uNruK0hoXrExHMjbAeM5hIFfDz477Y84Suh6XSNA1istQ8Stfw1H8hSIiFrRbPf+57IfLWQ/SYM/ghY4ZGIiEn1dXBpSeyjlAeU3sr4rxCm1zCDd8DniYcITTXrwND/3V/5FmtSUX4V4Oh7MXinA==</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature><saml2:Subject><saml2:NameID Format="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress">house@ehdsi.eu</saml2:NameID><saml2:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:sender-vouches"/></saml2:Subject><saml2:Conditions NotBefore="2025-09-01T12:55:09.921Z" NotOnOrAfter="2025-09-01T14:55:09.921Z"><saml2:AudienceRestriction><saml2:Audience>urn:ehdsi:assertions.audience:x-border</saml2:Audience></saml2:AudienceRestriction></saml2:Conditions><saml2:Advice><saml2:AssertionIDRef>_c82b7e98-a5db-41ad-bb97-9073c60e0b06</saml2:AssertionIDRef></saml2:Advice><saml2:AuthnStatement AuthnInstant="2025-09-01T12:55:09.921Z"><saml2:AuthnContext><saml2:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession</saml2:AuthnContextClassRef></saml2:AuthnContext></saml2:AuthnStatement><saml2:AttributeStatement><saml2:Attribute FriendlyName="XSPA Subject" Name="urn:oasis:names:tc:xspa:1.0:subject:subject-id" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">0410009234^^^&amp;1.2.208.176.1.2&amp;ISO</saml2:AttributeValue></saml2:Attribute><saml2:Attribute FriendlyName="XSPA Purpose Of Use" Name="urn:oasis:names:tc:xspa:1.0:subject:purposeofuse" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri"><saml2:AttributeValue><PurposeOfUse xmlns="urn:hl7-org:v3" code="TREATMENT" codeSystem="3bc18518-d305-46c2-a8d6-94bd59856e9e" codeSystemName="eHDSI XSPA PurposeOfUse" displayName="TREATMENT"/></saml2:AttributeValue></saml2:Attribute></saml2:AttributeStatement></saml2:Assertion></wsse:Security></soapenv:Header>
}
