package dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.xcpd;

import dk.sundhedsdatastyrelsen.ncpeh.ApiException;
import dk.sundhedsdatastyrelsen.ncpeh.api.DefaultApi;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.Gender;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.PostFindPatientsRequest;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.NationalConnectorService;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.Utils;
import dk.sundhedsdatastyrelsen.ncpeh.nationalconnector.xca.DocumentSearch;
import eu.europa.ec.sante.openncp.common.error.OpenNCPErrorCode;
import eu.europa.ec.sante.openncp.core.common.ihe.NationalConnectorInterface;
import eu.europa.ec.sante.openncp.core.common.ihe.assertionvalidator.exceptions.InsufficientRightsException;
import eu.europa.ec.sante.openncp.core.common.ihe.datamodel.PatientDemographics;
import eu.europa.ec.sante.openncp.core.common.ihe.datamodel.PatientId;
import eu.europa.ec.sante.openncp.core.common.ihe.exception.NIException;
import eu.europa.ec.sante.openncp.core.common.ihe.transformation.util.XmlUtil;
import eu.europa.ec.sante.openncp.core.server.api.ihe.xcpd.PatientSearchInterfaceWithDemographics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PatientSearch implements NationalConnectorInterface, PatientSearchInterfaceWithDemographics {
    private static final Logger logger = LoggerFactory.getLogger(DocumentSearch.class);

    /**
     * For use when logging clinical and personal data
     */
    private static final Logger loggerClinical = LoggerFactory.getLogger("LOGGER_CLINICAL");

    private Element soapHeader;

    @Override
    public void setPatientDemographics(PatientDemographics patientDemographics) {

    }

    @Override
    public String getPatientId(String s) throws NIException, InsufficientRightsException {
        return null;
    }

    static PatientDemographics.Gender toEpsosGender(@Nullable Gender gender) {
        if (gender == null) {
            return null;
        }
        switch (gender) {
            case FEMALE:
                return PatientDemographics.Gender.FEMALE;
            case MALE:
                return PatientDemographics.Gender.MALE;
            case UNDIFFERENTIATED:
                return PatientDemographics.Gender.UNDIFFERENTIATED;
            default:
                throw new IllegalArgumentException();
        }
    }


    /**
     * Inherently unsafe, we don't know if everyone agrees on offset.
     */
    static Date localDateToUtilDate(LocalDate date) {
        if (date == null) return null;
        return Date.from(date.atStartOfDay(ZoneId.of("UTC")).toInstant());
    }

    static PatientId toPatientId(String id) {
        final var pattern = Pattern.compile("(.*)\\^\\^\\^(.*)");
        final var matcher = pattern.matcher(id);
        if (matcher.find()) {
            return new PatientId(matcher.group(1), matcher.group(2));
        }
        // If the string does not contain three carets then we assume it is a CPR number
        return new PatientId("1.2.208.176.1.2", id);
    }

    static PatientDemographics toEpsosPatient(dk.sundhedsdatastyrelsen.ncpeh.api.model.PatientDemographics patient) {
        final var result = new PatientDemographics();
        final var patientId = toPatientId(patient.getIdList().get(0));
        result.setId(patientId.getExtension());
        result.setHomeCommunityId(patientId.getRoot());
        result.setFamilyName(patient.getFamilyName());
        result.setGivenName(patient.getGivenName());
        result.setStreetAddress(patient.getStreetAddress());
        result.setPostalCode(patient.getPostalCode());
        result.setCity(patient.getCity());
        result.setCountry(patient.getCountry());
        result.setAdministrativeGender(toEpsosGender(patient.getAdministrativeGender()));
        result.setBirthDate(localDateToUtilDate(patient.getBirthDate()));
        try {
            if (patient.getEmail() != null) {
                result.setEmail(patient.getEmail());
            }
        } catch (ParseException e) {
            logger.warn("Unable to parse email value in response");
        }
        try {
            if (patient.getTelephone() != null) {
                result.setTelephone(patient.getTelephone());
            }
        } catch (ParseException e) {
            logger.warn("Unable to parse telephone number in response");
        }
        return result;
    }

    static List<PatientDemographics> getPatientDemographicsFromCountryA(List<PatientId> patientIds, Element soapHeader, DefaultApi nationalConnectorApi) throws NIException {
        try {
            // We only accept patient ids with root "1.2.208.176.1.2", otherwise we throw.
            final var pidsValidInvalid = patientIds.stream().collect(Collectors.partitioningBy(pid -> "1.2.208.176.1.2".equals(
                pid.getRoot())));
            if (!pidsValidInvalid.get(false).isEmpty()) {
                throw new NIException(OpenNCPErrorCode.ERROR_PI_GENERIC, String.format(
                    "Unknown patient identifier root(s): %s",
                    pidsValidInvalid.get(false).stream().map(PatientId::getRoot).collect(Collectors.joining(","))));
            }
            final var cprNumbers = pidsValidInvalid.get(true).stream().map(PatientId::getExtension).collect(Collectors.toList());
            final var request = new PostFindPatientsRequest()
                .soapHeader(Utils.elementToString(soapHeader))
                .patientIds(cprNumbers);
            logger.info("Retrieving patient demographics from Country A service...");
            final var response = nationalConnectorApi.postFindPatients(request);
            logger.info("Successfully retrieved patient demographics from Country A service.");
            return response.getPatients().stream()
                .map(PatientSearch::toEpsosPatient)
                .collect(Collectors.toList());
        } catch (ApiException e) {
            throw new NIException(OpenNCPErrorCode.ERROR_PI_GENERIC, e.getCode() > 0
                ? String.format("Error, status: %s body: %s", e.getCode(), e.getResponseBody())
                : "Missing response from service"
            );
        }
    }

    @Override
    public List<PatientDemographics> getPatientDemographics(List<PatientId> patientIds) throws NIException {
        return getPatientDemographicsFromCountryA(patientIds, this.soapHeader, NationalConnectorService.api());
    }

    @Override
    public void setSOAPHeader(Element soapHeader) {
        this.soapHeader = soapHeader;
    }

    /**
     * For testing in IDE
     */
    public static void main(String[] args) throws Exception {
        try {
            var foo = getPatientDemographicsFromCountryA(
                List.of(new PatientId("1.2.208.176.1.2", "1111111118")),
                XmlUtil.parseContent("<SomeXml/>").getDocumentElement(),
                NationalConnectorService.api());
            return;
        } catch (NIException e) {
            throw new RuntimeException(e);
        }
    }
}
