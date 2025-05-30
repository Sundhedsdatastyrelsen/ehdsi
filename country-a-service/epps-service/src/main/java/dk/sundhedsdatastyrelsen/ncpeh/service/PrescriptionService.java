package dk.sundhedsdatastyrelsen.ncpeh.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationResponseType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationType;
import dk.nsp.test.idp.OrganizationIdentities;
import dk.nsp.test.idp.model.Identity;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL1Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Input;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.DocumentLevel;
import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClient;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.DataProvider;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ClassCodeDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.DataRequirementException;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.DispensationMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.EPrescriptionMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRow;
import freemarker.template.TemplateException;
import jakarta.xml.bind.JAXBException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.sql.DataSource;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Service
public class PrescriptionService {
    private static final String MAPPING_ERROR_MESSAGE = "Error mapping eDispensation CDA to request: %s";
    private final FmkClient fmkClient;
    private final UndoDispensationRepository undoDispensationRepository;
    private final DataProvider lmsDataProvider;
    private final AuthorizationRegistryClient authorizationRegistry;
    private final Cache<String, List<AuthorizationType>> authorizationRegistryCache = Caffeine.newBuilder()
        .maximumSize(500)
        .expireAfterWrite(Duration.ofMinutes(15))
        .build();

    public PrescriptionService(
        FmkClient fmkClient,
        UndoDispensationRepository undoDispensationRepository,
        @Qualifier("localLmsDataSource") DataSource lmsDataSource,
        AuthorizationRegistryClient authorizationRegistry
    ) {
        this.fmkClient = fmkClient;
        this.undoDispensationRepository = undoDispensationRepository;
        this.lmsDataProvider = new DataProvider(lmsDataSource);
        this.authorizationRegistry = authorizationRegistry;
    }

    public record PrescriptionFilter(
        String documentId,
        OffsetDateTime createdBefore,
        OffsetDateTime createdAfter
    ) {
        public static PrescriptionFilter none() {
            return new PrescriptionFilter(null, null, null);
        }

        public Stream<Pair<Integer, PrescriptionType>> validPrescriptionIndexes(@NonNull List<PrescriptionType> list) {
            return IntStream.range(0, list.size())
                .mapToObj(idx -> Pair.of(idx, list.get(idx)))
                .filter(pair -> apply(pair.getRight()));
        }

        private boolean apply(PrescriptionType prescription) {
            var authorisationDateTime = Utils.convertToOffsetDateTime(prescription.getAuthorisationDateTime());
            return (documentId == null || EPrescriptionDocumentIdMapper.possibleIds(String.valueOf(prescription.getIdentifier()))
                .contains(documentId))
                && (createdBefore == null || authorisationDateTime.isBefore(createdBefore))
                && (createdAfter == null || authorisationDateTime.isAfter(createdAfter));
        }
    }

    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        String patientId,
        PrescriptionFilter filter,
        Identity caller
    ) {
        try {
            String cpr = PatientIdMapper.toCpr(patientId);
            final var request = GetPrescriptionRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
                .withIncludeOpenPrescriptions().end()
                .build();
            var fmkResponse = fmkClient.getPrescription(request, caller);

            log.debug("Found {} prescriptions", fmkResponse.getPrescription().size());

            var validPrescriptions = filter.validPrescriptionIndexes(fmkResponse.getPrescription()).toList();
            return validPrescriptions.stream()
                .map(pair -> EPrescriptionMapper.mapMeta(
                    patientId,
                    fmkResponse,
                    pair.getLeft(),
                    lmsDataProvider.packageInfo(pair.getRight()
                        .getPackageRestriction()
                        .getPackageNumber()
                        .getValue())))
                .filter(Objects::nonNull)
                .toList();
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public List<EpsosDocumentDto> getPrescriptions(String patientId, PrescriptionFilter filter, Identity caller) {
        var input = assembleEPrescriptionInput(patientId, filter, caller).findFirst().orElse(null);
        DocumentLevel documentLevel;
        try {
            documentLevel = EPrescriptionDocumentIdMapper.parseDocumentLevel(filter.documentId());
            var cda = switch (documentLevel) {
                case LEVEL3 -> EPrescriptionL3Generator.generate(input);
                case LEVEL1 -> EPrescriptionL1Generator.generate(input);
            };

            return List.of(new EpsosDocumentDto(patientId, cda, ClassCodeDto._57833_6));
        } catch (MapperException | TemplateException | IOException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private Stream<EPrescriptionL3Input> assembleEPrescriptionInput(String patientId, PrescriptionFilter filter, Identity caller) {
        String cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        try {
            GetPrescriptionResponseType fmkResponse = fmkClient.getPrescription(request, caller);

            log.debug("Found {} prescriptions for {}", fmkResponse.getPrescription().size(), cpr);

            var validPrescriptions = filter.validPrescriptionIndexes(fmkResponse.getPrescription()).toList();

            var drugMedicationIds = validPrescriptions.stream().map(Pair::getRight)
                .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
                .toList();

            var drugMedications = getDrugMedicationResponse(cpr, drugMedicationIds, caller);

            return validPrescriptions.stream().map(pair -> {
                try {
                    var prescription = pair.getRight();
                    var packageFormCode = lmsDataProvider.packageFormCode(prescription
                        .getPackageRestriction()
                        .getPackageNumber()
                        .getValue());
                    var manufacturerOrganizationName = lmsDataProvider.manufacturerOrganizationName(
                        prescription.getDrug().getIdentifier().getValue()
                    );
                    var authorizations = authorizationRegistryCache.get(
                        EPrescriptionL3Mapper.getAuthorizedHealthcareProfessional(prescription)
                            .getAuthorisationIdentifier(),
                        id -> getAuthorizationByIdentifierCode(id, OrganizationIdentities.sundhedsdatastyrelsen())
                            .getAutorisation());

                    return new EPrescriptionL3Input(
                        fmkResponse,
                        pair.getLeft(),
                        drugMedications,
                        Optional.ofNullable(authorizations).orElse(List.of()),
                        packageFormCode,
                        manufacturerOrganizationName);
                } catch (MapperException e) {
                    throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get packageFormCode.");
                }
            });
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve prescriptions from FMK");
        }
    }

    private @NonNull AuthorizationResponseType getAuthorizationByIdentifierCode(String authorizationIdentifier, Identity caller) {
        try {
            return authorizationRegistry.requestByAuthorizationCode(authorizationIdentifier, caller);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get authorizationType.");
        }
    }

    public void submitDispensation(@NonNull String patientId, @NonNull Document dispensationCda, Identity caller) {
        StartEffectuationResponseType response;
        String eDispensationCdaId;
        try {
            eDispensationCdaId = DispensationMapper.cdaId(dispensationCda);
        } catch (MapperException e) {
            throw new DataRequirementException("Invalid CDA ID value", e);
        }

        try {
            var prescriptionId = DispensationMapper.prescriptionId(dispensationCda);
            var prescriptionResponse = fmkClient.getPrescription(
                GetPrescriptionRequestType.builder()
                    .withPersonIdentifier().withSource("CPR").withValue(PatientIdMapper.toCpr(patientId)).end()
                    .withIncludeOpenPrescriptions().end()
                    .build(),
                caller);
            var prescription = prescriptionResponse.getPrescription()
                .stream()
                .filter(p -> p.getIdentifier() == prescriptionId)
                .findFirst()
                .orElseThrow(() -> new CountryAException(HttpStatus.NOT_FOUND, "Could not find prescription to dispense"));
            var isDispensable = DispensationAllowed.isDispensationAllowed(
                prescription,
                lmsDataProvider.packageInfo(prescription.getPackageRestriction()
                    .getPackageNumber()
                    .getValue()));
            if (!isDispensable) {
                throw new CountryAException(HttpStatus.BAD_REQUEST, "Prescription is not allowed to be dispensed");
            }
        } catch (XPathExpressionException | MapperException | JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not fetch prescription to dispense", e);
        }

        try {
            log.info("Start FMK effectuation");
            response = fmkClient.startEffectuation(
                DispensationMapper.startEffectuationRequest(patientId, dispensationCda),
                caller);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "StartEffectuation failed", e);
        } catch (MapperException e) {
            throw new DataRequirementException(String.format(MAPPING_ERROR_MESSAGE, e.getMessage()), e);
        }

        CreatePharmacyEffectuationResponseType effectuationResponse;
        try {
            log.info("Create FMK pharmacy effectuation");
            effectuationResponse = fmkClient.createPharmacyEffectuation(
                DispensationMapper.createPharmacyEffectuationRequest(
                    patientId,
                    dispensationCda,
                    response),
                caller);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "CreatePharmacyEffectuation failed", e);
        } catch (MapperException e) {
            throw new DataRequirementException(String.format(MAPPING_ERROR_MESSAGE, e.getMessage()), e);
        }

        for (var effectuation : effectuationResponse.getEffectuation()) {
            // There should only be one in our case, but FMK supports multiple effectuations per request
            log.info("Store effectuation undo information");
            try {
                undoDispensationRepository.insert(UndoDispensationRow.fromCdaId(
                    eDispensationCdaId,
                    effectuation.getEffectuationIdentifier(),
                    effectuation.getOrderIdentifier()
                ));
            } catch (Exception e) {
                // We should not fail the submitDispensation request here because of a database error,
                // because the dispensation has been recorded in FMK at this point.
                log.error("Storing undo information for dispensation failed", e);
            }
        }
    }

    public void undoDispensation(@NonNull String patientId, Document cdaToDiscard, Identity caller) {
        String eDispensationCdaId;
        try {
            eDispensationCdaId = DispensationMapper.cdaId(cdaToDiscard);
        } catch (MapperException e) {
            throw new DataRequirementException("Invalid CDA ID value", e);
        }
        var undoInfo = undoDispensationRepository.findByCdaId(eDispensationCdaId);

        if (undoInfo == null) {
            throw new CountryAException(HttpStatus.NOT_FOUND, "No undo information found for given eDispensation CDA id");
        }

        UndoEffectuationRequestType undoEffectuationRequest;
        try {
            undoEffectuationRequest = DispensationMapper.createUndoEffectuationRequest(
                patientId,
                cdaToDiscard,
                undoInfo.orderId(),
                undoInfo.effectuationId()
            );
        } catch (MapperException e) {
            throw new DataRequirementException(MAPPING_ERROR_MESSAGE, e);
        }
        log.info("Requesting effectuation undo");
        UndoEffectuationResponseType undoResponse;
        try {
            undoResponse = fmkClient.undoEffectuation(undoEffectuationRequest, caller);
        } catch (JAXBException e) {
            throw new CountryAException(HttpStatus.INTERNAL_SERVER_ERROR, "UndoEffectuation call to FMK failed", e);
        }

        var cancelledEffectuationCount = undoResponse.getPrescription()
            .stream()
            .flatMap(p -> p.getOrder().stream())
            .mapToLong(o -> o.getEffectuation().size())
            .sum();
        if (cancelledEffectuationCount < 1) {
            throw new CountryAException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error cancelling effectuation, nothing was cancelled");
        }
        if (cancelledEffectuationCount > 1) {
            log.error(
                "More than one ({}) FMK effectuations was cancelled where it should be exactly one",
                cancelledEffectuationCount);
        }
        log.info("Undo effectuation successful, removing undo information");
        undoDispensationRepository.deleteByCdaId(eDispensationCdaId);
    }

    public GetDrugMedicationResponseType getDrugMedicationResponse(String cpr, List<Long> drugMedicationId, Identity caller) throws JAXBException {
        var drugMedicationRequest = GetDrugMedicationRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIdentifier(drugMedicationId)
            .withIncludePrescriptions(false)
            .withIncludeEffectuations(false)
            .build();

        log.debug("Looking up DrugMedication  info for {}", cpr);
        GetDrugMedicationResponseType fmkResponse = fmkClient.getDrugMedication(drugMedicationRequest, caller);
        log.debug(
            "Found {} prescriptions for drug medication ID {}", fmkResponse.getDrugMedication()
                .size(), drugMedicationId);
        return fmkResponse;
    }
}
