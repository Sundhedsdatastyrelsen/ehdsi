package dk.sundhedsdatastyrelsen.ncpeh.service;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.nsp.test.idp.model.Identity;
import dk.sundhedsdatastyrelsen.ncpeh.client.FskClient;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.EPrescriptionMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class InformationRecordService {
    private final FskClient fskClient;

    public InformationRecordService(FskClient fskClient) {
        this.fskClient = fskClient;
    }

    public String findInformationCardDetails(
        String patientId,
        Identity caller
    ) {
        try {
            String cpr = PatientIdMapper.toCpr(patientId);

            final var request = AdhocQueryRequest.builder()
                .withResponseOption(ResponseOptionType.builder()
                    .withReturnType("LeafClass")
                    .withReturnComposedObjects(true)
                    .build())
                .withAdhocQuery(AdhocQueryType.builder()
                    .withId("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d")
                    .withSlot(SlotType1.builder()
                        .withName("$XDSDocumentEntryPatientId")
                        .withValueList(ValueListType.builder()
                            .withValue("'" + cpr + "^^^&1.2.208.176.1.2&ISO'")
                            .build())
                        .build(),
                    SlotType1.builder()
                        .withName("$XDSDocumentEntryType")
                        .withValueList(ValueListType.builder()
                            .withValue("('urn:uuid:34268e47-fdf5-41a6-ba33-82133c465248')",
                            "('urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1')")
                            .build())
                        .build(),
                    SlotType1.builder()
                        .withName("$XDSDocumentEntryStatus")
                        .withValueList(ValueListType.builder()
                            .withValue("('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')")
                            .build())
                        .build(),
                    SlotType1.builder()
                        .withName("$XDSDocumentEntryFormatCode")
                        .withValueList(ValueListType.builder()
                            .withValue("('urn:ad:dk:medcom:pdc-v3.0:full^^1.2.208.184.100.10')")
                            .build())
                        .build())
                    .build())
                .build();

            var fskResponse = fskClient.list(request, caller);
            return fskResponse.toString();
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

}


