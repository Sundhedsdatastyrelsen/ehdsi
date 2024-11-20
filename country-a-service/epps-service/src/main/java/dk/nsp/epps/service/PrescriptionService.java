package dk.nsp.epps.service;

import dk.dkma.medicinecard.xml_schema._2015._06._01.GetDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.DrugMedicationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsp.epps.client.FmkClient;
import dk.nsp.epps.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.nsp.epps.ncp.api.EpsosDocumentDto;
import dk.nsp.epps.service.exception.CountryAException;
import dk.nsp.epps.service.mapping.DispensationMapper;
import dk.nsp.epps.service.mapping.EPrescriptionMapper;
import dk.nsp.epps.service.mapping.PatientIdMapper;
import dk.nsp.test.idp.model.Identity;
import dk.sds.ncp.cda.EPrescriptionDocumentIdMapper;
import dk.sds.ncp.cda.MapperException;
import jakarta.xml.bind.JAXBException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final FmkClient fmkClient;
    private final EPrescriptionMapper ePrescriptionMapper;

    public record PrescriptionFilter(
        String documentId,
        OffsetDateTime createdBefore,
        OffsetDateTime createdAfter
    ) {
        public static PrescriptionFilter none() {
            return new PrescriptionFilter(null, null, null);
        }

        public IntStream validPrescriptionIndexes(@NonNull List<PrescriptionType> list) {
            return IntStream.range(0, list.size())
                .filter(idx -> apply(list.get(idx)));
        }

        private boolean apply(PrescriptionType prescription) {
            var authorisationDateTime = toOffsetDateTime(prescription.getAuthorisationDateTime());
            return (documentId == null || EPrescriptionDocumentIdMapper.possibleIds(String.valueOf(prescription.getIdentifier()))
                .contains(documentId))
                && (createdBefore == null || authorisationDateTime.isBefore(createdBefore))
                && (createdAfter == null || authorisationDateTime.isAfter(createdAfter));
        }

        private OffsetDateTime toOffsetDateTime(@NonNull XMLGregorianCalendar xml) {
            return xml.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
        }
    }

    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        String patientId,
        PrescriptionFilter filter,
        Identity caller
    ) throws JAXBException {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        log.debug("undoDispensation: looking up prescription information");
        GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, caller);
        log.debug("undoDispensation: Found {} prescriptions", fmkResponse.getPrescription().size());
        return ePrescriptionMapper.mapMeta(cpr, filter, fmkResponse);
    }

    public List<EpsosDocumentDto> getPrescriptions(String patientId, PrescriptionFilter filter, Identity caller) throws JAXBException {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        log.debug("Looking up info for {}", cpr);
        GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, caller);
        log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);

        var prescriptions = filter.validPrescriptionIndexes(fmkResponse.getPrescription())
            .mapToObj(idx -> fmkResponse.getPrescription().get(idx))
            .toList();

        var drugMedicationIds = prescriptions.stream().map(PrescriptionType::getAttachedToDrugMedicationIdentifier).toList();

        var drugMedications = getDrugMedication(patientId,drugMedicationIds,caller);
        return ePrescriptionMapper.mapResponse(cpr, filter, fmkResponse, drugMedications);
    }

    public void submitDispensation(@NonNull String patientId, @NonNull Document dispensationCda, Identity caller) throws MapperException {
        StartEffectuationResponseType response;
        var dispensationMapper = new DispensationMapper();
        try {
            response = fmkClient.startEffectuation(
                dispensationMapper.startEffectuationRequest(patientId, dispensationCda),
                caller);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "StartEffectuation failed", e);
        }

        try {
            fmkClient.createPharmacyEffectuation(
                dispensationMapper.createPharmacyEffectuationRequest(
                    patientId,
                    dispensationCda,
                    response),
                caller);
        } catch (JAXBException e) {
            // TODO: Cancel effectuation flow
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "CreatePharmacyEffectuation failed", e);
        }
    }

    public UndoEffectuationResponseType undoDispensation(@NonNull String patientId, Document cdaToDiscard, Identity caller) throws JAXBException, MapperException {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeAllPrescriptions().end()
            .withIncludeEffectuations(true)
            .build();
        log.debug("Looking up info for {}", cpr);
        GetPrescriptionResponseType fmkResponse = fmkClient.getPrescriptionWithConsent(request, caller);

        var dispensationMapper = new DispensationMapper();
        UndoEffectuationRequestType undoEffectuationRequest = dispensationMapper.createUndoEffectuationRequest(patientId, cdaToDiscard, fmkResponse);
        UndoEffectuationResponseType undoResponse = fmkClient.undoEffectuation(undoEffectuationRequest, caller);

        //Validate undone dispensation by getting the EffectuationId that has been undone
        var effectuationWasCancelled = undoResponse.getPrescription()
            .stream()
            .flatMap(p -> p.getOrder().stream())
            .flatMap(o -> o.getEffectuation().stream())
            .count() == 1;
        if (!effectuationWasCancelled) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Error cancelling effectuation, nothing was cancelled");
        }
        return undoResponse;
    }

    public GetDrugMedicationResponseType getDrugMedication(String patientId, List<Long> drugMedicationId, Identity caller) throws JAXBException {
        String cpr = PatientIdMapper.toCpr(patientId);

        var drugMedicationRequest = GetDrugMedicationRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIdentifier(drugMedicationId)
            .withIncludePrescriptions(false)
            .withIncludeEffectuations(false)
            .build();

        log.debug("Looking up DrugMedication  info for {}", cpr);
        GetDrugMedicationResponseType fmkResponse = fmkClient.getDrugMedication(drugMedicationRequest, caller);
        log.debug("Found {} prescriptions for drug medication ID {}", fmkResponse.getDrugMedication()
            .size(), drugMedicationId);
        return fmkResponse;
    }
}
