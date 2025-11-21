package dk.sundhedsdatastyrelsen.ncpeh.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatePharmacyEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetDrugMedicationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GetPrescriptionRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PackageNumberType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.UndoEffectuationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.CreatePharmacyEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.GetDrugMedicationResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.StartEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e5.UndoEffectuationRequestType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.StartEffectuationResponseType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationResponseType;
import dk.nsi._2024._01._05.stamdataauthorization.AuthorizationType;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.EuropeanHcpIdwsToken;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.NspDgwsIdentity;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionDocumentIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL1Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Generator;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Input;
import dk.sundhedsdatastyrelsen.ncpeh.cda.EPrescriptionL3Mapper;
import dk.sundhedsdatastyrelsen.ncpeh.cda.MapperException;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Oid;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.client.AuthorizationRegistryClient;
import dk.sundhedsdatastyrelsen.ncpeh.client.FmkClientIdws;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.DataProvider;
import dk.sundhedsdatastyrelsen.ncpeh.locallms.PackageInfo;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ClassCodeDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.DocumentAssociationForEPrescriptionDocumentMetadataDto;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.EpsosDocumentDto;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.CountryAException;
import dk.sundhedsdatastyrelsen.ncpeh.service.exception.DataRequirementException;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.DispensationMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.EPrescriptionMetadataMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.mapping.PatientIdMapper;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRepository;
import dk.sundhedsdatastyrelsen.ncpeh.service.undo.UndoDispensationRow;
import freemarker.template.TemplateException;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.xml.bind.JAXBException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Document;

import javax.sql.DataSource;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class PrescriptionService {
    private static final String MAPPING_ERROR_MESSAGE = "Error mapping eDispensation CDA to request: %s";
    private final FmkClientIdws fmkClient;
    private final UndoDispensationRepository undoDispensationRepository;
    private final DataProvider lmsDataProvider;
    private final AuthorizationRegistryClient authorizationRegistry;
    private final Cache<String, List<AuthorizationType>> authorizationRegistryCache = Caffeine.newBuilder()
        .maximumSize(500)
        .expireAfterWrite(Duration.ofMinutes(15))
        .build();
    private final NspDgwsIdentity.System systemIdentity;

    public PrescriptionService(
        String fmkEndpointUrl,
        SigningCertificate signingCertificate,
        UndoDispensationRepository undoDispensationRepository,
        DataSource lmsDataSource,
        AuthorizationRegistryClient authorizationRegistry,
        NspDgwsIdentity.System systemIdentity
    ) {
        this.systemIdentity = systemIdentity;
        try {
            this.fmkClient = new FmkClientIdws(signingCertificate.getCertificateAndKey().privateKey(), fmkEndpointUrl);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Malformed FMK endpoint", e);
        }
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

        public static PrescriptionFilter fromRootedId(String rootedDocumentId, OffsetDateTime createdBefore, OffsetDateTime createdAfter) {
            var documentId = CdaId.fromDocumentId(rootedDocumentId);
            if (documentId != null && documentId.getRootOid() != Oid.DK_EPRESCRIPTION_REPOSITORY_ID) {
                throw new CountryAException(400, "Document repository in document ID is not ePrescription.");
            }
            return new PrescriptionFilter(
                Optional.ofNullable(documentId)
                    .map(CdaId::getExtension)
                    .orElse(null),
                createdBefore,
                createdAfter);
        }
    }

    @WithSpan
    public List<DocumentAssociationForEPrescriptionDocumentMetadataDto> findEPrescriptionDocuments(
        String patientId,
        PrescriptionFilter filter,
        EuropeanHcpIdwsToken token
    ) {
        try {
            var cpr = PatientIdMapper.toCpr(patientId);
            final var request = GetPrescriptionRequestType.builder()
                .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
                .withIncludeOpenPrescriptions().end()
                .withIncludeEffectuations(true)
                .build();
            var fmkResponse = fmkClient.getPrescription(request, token);

            log.debug("Found {} prescriptions", fmkResponse.getPrescription().size());

            var validPrescriptions = filter.validPrescriptionIndexes(fmkResponse.getPrescription()).toList();
            return validPrescriptions.stream()
                .map(pair -> EPrescriptionMetadataMapper.mapMeta(
                    patientId,
                    fmkResponse,
                    pair.getLeft(),
                    Optional.ofNullable(pair.getRight())
                        .map(PrescriptionType::getPackageRestriction)
                        .map(PackageRestrictionType::getPackageNumber)
                        .map(PackageNumberType::getValue)
                        .map(lmsDataProvider::packageInfo)
                        .orElse(null)))
                .filter(Objects::nonNull)
                .toList();
        } catch (JAXBException e) {
            throw new CountryAException(500, e);
        }
    }

    @WithSpan
    public List<EpsosDocumentDto> getPrescriptions(String patientId, PrescriptionFilter filter, EuropeanHcpIdwsToken token) {
        var input = assembleEPrescriptionInput(patientId, filter, token).findFirst().orElse(null);
        if (input == null) {
            throw new CountryAException(404, "Requested prescription not found.");
        }
        try {
            var documentLevel = EPrescriptionDocumentIdMapper.parseDocumentLevel(filter.documentId());
            var cda = switch (documentLevel) {
                case LEVEL3 -> EPrescriptionL3Generator.generate(input);
                case LEVEL1 -> EPrescriptionL1Generator.generate(input);
            };

            return List.of(new EpsosDocumentDto(patientId, cda, ClassCodeDto._57833_6));
        } catch (MapperException | TemplateException | IOException e) {
            throw new CountryAException(500, e);
        }
    }

    @WithSpan
    private Stream<EPrescriptionL3Input> assembleEPrescriptionInput(String patientId, PrescriptionFilter filter, EuropeanHcpIdwsToken token) {
        var cpr = PatientIdMapper.toCpr(patientId);
        final var request = GetPrescriptionRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIncludeOpenPrescriptions().end()
            .build();
        try {
            var fmkResponse = fmkClient.getPrescription(request, token);
            var validPrescriptions = filter.validPrescriptionIndexes(fmkResponse.getPrescription()).toList();

            var drugMedicationIds = validPrescriptions.stream().map(Pair::getRight)
                .map(PrescriptionType::getAttachedToDrugMedicationIdentifier)
                .toList();

            var drugMedications = getDrugMedicationResponse(cpr, drugMedicationIds, token);

            return validPrescriptions.stream().map(pair -> {
                try {
                    var prescription = pair.getRight();
                    var packageInfo = Optional.ofNullable(prescription.getPackageRestriction())
                        .map(PackageRestrictionType::getPackageNumber)
                        .map(PackageNumberType::getValue)
                        .map(lmsDataProvider::packageInfo);
                    var manufacturerOrganizationName = Optional.ofNullable(prescription.getDrug().getIdentifier())
                        .map(DrugIdentifierType::getValue)
                        .map(lmsDataProvider::manufacturerOrganizationName);
                    var authorizations = authorizationRegistryCache.get(
                        EPrescriptionL3Mapper.getAuthorizedHealthcareProfessional(prescription)
                            .getAuthorisationIdentifier(),
                        id -> getAuthorizationByIdentifierCode(id, systemIdentity)
                            .getAutorisation());

                    return new EPrescriptionL3Input(
                        fmkResponse,
                        pair.getLeft(),
                        drugMedications,
                        Optional.ofNullable(authorizations).orElse(List.of()),
                        packageInfo.map(PackageInfo::packageFormCode).orElse(null),
                        packageInfo.map(PackageInfo::numberOfSubPackages).orElse(null),
                        manufacturerOrganizationName.orElse(null));
                } catch (MapperException e) {
                    throw new CountryAException(500, "Could not get packageFormCode.");
                }
            });
        } catch (JAXBException e) {
            throw new CountryAException(500, "Could not retrieve prescriptions from FMK");
        }
    }

    private @NonNull AuthorizationResponseType getAuthorizationByIdentifierCode(String authorizationIdentifier, NspDgwsIdentity caller) {
        try {
            return authorizationRegistry.requestByAuthorizationCode(authorizationIdentifier, caller);
        } catch (JAXBException e) {
            throw new CountryAException(500, "Could not get authorizationType.");
        }
    }

    public void submitDispensation(@NonNull String patientId, @NonNull Document dispensationCda, EuropeanHcpIdwsToken token) {
        submitDispensation(patientId, dispensationCda, token, false);
    }

    /// @hidden public for testing.
    @WithSpan
    public void submitDispensation(@NonNull String patientId, @NonNull Document dispensationCda, EuropeanHcpIdwsToken token, boolean forceDispensationError) {
        var helper = new SubmitDispensationHelper();
        var eDispensationCdaId = SubmitDispensationHelper.getEDispensationId(dispensationCda);

        // Get the prescription from FMK to check that we can dispense it.
        helper.validateDispensable(patientId, dispensationCda, token);

        // Start the effectuation. This locks the prescription to us.
        log.info("Start FMK effectuation");
        var startEffectuation = helper.lockEffectuation(patientId, dispensationCda, token);

        try {
            // Dispense it. This may also close the prescription.
            log.info("Create FMK pharmacy effectuation");
            if (forceDispensationError) {
                throw new IllegalStateException("Forced dispensation error");
            }
            var dispensation = helper.dispense(patientId, dispensationCda, token, startEffectuation.response());

            // If we didn't terminate, release the lock on the prescription again.
            if (!dispensation.request().getPrescription().getFirst().isTerminate()) {
                log.info("Release lock on prescription, it was not terminated.");
                helper.releaseEffectuation(token, startEffectuation);
            }

            // Add effectuation to undo repo to support undo workflow.
            for (var effectuation : dispensation.response().getEffectuation()) {
                // There should only be one in our case, but FMK supports multiple effectuations per request
                log.info("Store effectuation undo information");
                helper.storeUndoInformation(effectuation, eDispensationCdaId);
            }
        } catch (Exception e) {
            // No matter the exception, we release the lock on the effectuation if dispensation fails. Otherwise
            // all subsequent attempts to dispense will fail.
            try {
                fmkClient.abortEffectuation(
                    DispensationMapper.abortEffectuationRequest(startEffectuation.request(), startEffectuation.response()),
                    token);
            } catch (Exception e2) {
                // Nothing to do but log here.
                log.error("Couldn't abort the effectuation. Error: ", e2);
            }
            // Rethrow the original exception.
            throw e;
        }
    }

    @WithSpan
    public void undoDispensation(@NonNull String patientId, Document cdaToDiscard, EuropeanHcpIdwsToken token) {
        String eDispensationCdaId;
        try {
            eDispensationCdaId = DispensationMapper.cdaId(cdaToDiscard);
        } catch (MapperException e) {
            throw new DataRequirementException("Invalid CDA ID value", e);
        }
        var undoInfo = undoDispensationRepository.findByCdaId(eDispensationCdaId);

        if (undoInfo == null) {
            throw new CountryAException(404, "No undo information found for given eDispensation CDA id");
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
            undoResponse = fmkClient.undoEffectuation(undoEffectuationRequest, token);
        } catch (JAXBException e) {
            throw new CountryAException(500, "UndoEffectuation call to FMK failed", e);
        }

        var cancelledEffectuationCount = undoResponse.getPrescription()
            .stream()
            .flatMap(p -> p.getOrder().stream())
            .mapToLong(o -> o.getEffectuation().size())
            .sum();
        if (cancelledEffectuationCount < 1) {
            throw new CountryAException(
                500,
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

    @WithSpan
    public GetDrugMedicationResponseType getDrugMedicationResponse(String cpr, List<Long> drugMedicationId, EuropeanHcpIdwsToken token) throws JAXBException {
        var drugMedicationRequest = GetDrugMedicationRequestType.builder()
            .withPersonIdentifier().withSource("CPR").withValue(cpr).end()
            .withIdentifier(drugMedicationId)
            .withIncludePrescriptions(false)
            .withIncludeEffectuations(false)
            .build();

        log.debug("Looking up DrugMedication info");
        GetDrugMedicationResponseType fmkResponse = fmkClient.getDrugMedication(drugMedicationRequest, token);
        log.debug(
            "Found {} prescriptions for drug medication ID {}", fmkResponse.getDrugMedication()
                .size(), drugMedicationId);
        return fmkResponse;
    }

    private class SubmitDispensationHelper {
        record ReqRes<TReq, TRes>(TReq request, TRes response) {
        }

        private void storeUndoInformation(CreatePharmacyEffectuationResponseType.Effectuation effectuation, String eDispensationCdaId) {
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

        private void releaseEffectuation(
            EuropeanHcpIdwsToken token,
            ReqRes<StartEffectuationRequestType, StartEffectuationResponseType> startEffectuation
        ) {
            try {
                fmkClient.abortEffectuation(
                    DispensationMapper.abortEffectuationRequest(startEffectuation.request(), startEffectuation.response()),
                    token);
            } catch (JAXBException e) {
                throw new CountryAException(500, "Abort effectuation failed", e);
            } catch (MapperException e) {
                throw new DataRequirementException(String.format(MAPPING_ERROR_MESSAGE, e.getMessage()), e);
            }
        }

        private ReqRes<CreatePharmacyEffectuationRequestType, CreatePharmacyEffectuationResponseType> dispense(
            String patientId,
            Document dispensationCda,
            EuropeanHcpIdwsToken token,
            StartEffectuationResponseType startEffectuationResponse
        ) {
            try {
                var createEffectuationRequest = DispensationMapper.createPharmacyEffectuationRequest(
                    patientId,
                    dispensationCda,
                    startEffectuationResponse);
                var effectuationResponse = fmkClient.createPharmacyEffectuation(createEffectuationRequest, token);
                return new ReqRes<>(createEffectuationRequest, effectuationResponse);
            } catch (JAXBException e) {
                throw new CountryAException(500, "CreatePharmacyEffectuation failed", e);
            } catch (MapperException e) {
                throw new DataRequirementException(String.format(MAPPING_ERROR_MESSAGE, e.getMessage()), e);
            }
        }

        private ReqRes<StartEffectuationRequestType, StartEffectuationResponseType> lockEffectuation(
            String patientId,
            Document dispensationCda,
            EuropeanHcpIdwsToken token
        ) {
            try {
                var startEffectuationRequest = DispensationMapper.startEffectuationRequest(patientId, dispensationCda);
                var startEffectuationResponse = fmkClient.startEffectuation(startEffectuationRequest, token);
                return new ReqRes<>(startEffectuationRequest, startEffectuationResponse);
            } catch (JAXBException e) {
                throw new CountryAException(500, "StartEffectuation failed", e);
            } catch (MapperException e) {
                throw new DataRequirementException(String.format(MAPPING_ERROR_MESSAGE, e.getMessage()), e);
            }
        }

        private void validateDispensable(String patientId, Document dispensationCda, EuropeanHcpIdwsToken token) {
            try {
                var prescriptionId = DispensationMapper.prescriptionId(dispensationCda);
                var prescriptionResponse = fmkClient.getPrescription(
                    GetPrescriptionRequestType.builder()
                        .withPersonIdentifier().withSource("CPR").withValue(PatientIdMapper.toCpr(patientId)).end()
                        .withIncludeOpenPrescriptions().end()
                        .withIncludeEffectuations(true)
                        .build(),
                    token);
                var prescription = prescriptionResponse.getPrescription()
                    .stream()
                    .filter(p -> p.getIdentifier() == prescriptionId)
                    .findFirst()
                    .orElseThrow(() -> new CountryAException(404, "Could not find prescription to dispense"));
                var dispensationAllowedError = DispensationAllowed.getDispensationRestrictions(
                    prescription,
                    lmsDataProvider.packageInfo(prescription.getPackageRestriction()
                        .getPackageNumber()
                        .getValue()));
                if (null != dispensationAllowedError) {
                    throw new CountryAException(400, "Prescription is not allowed to be dispensed. " + dispensationAllowedError);
                }
            } catch (XPathExpressionException | MapperException | JAXBException e) {
                throw new CountryAException(500, "Could not fetch prescription to dispense", e);
            }
        }

        private static String getEDispensationId(Document dispensationCda) {
            String eDispensationCdaId;
            try {
                eDispensationCdaId = DispensationMapper.cdaId(dispensationCda);
            } catch (MapperException e) {
                throw new DataRequirementException("Invalid CDA ID value", e);
            }
            return eDispensationCdaId;
        }
    }
}
