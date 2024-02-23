package dk.openncp.nationalconnector.xcpd;

import dk.nsp.epps.ApiException;
import dk.nsp.epps.api.DefaultApi;
import dk.nsp.epps.api.model.Gender;
import dk.nsp.epps.api.model.PostFindPatientsRequest;
import dk.openncp.nationalconnector.CountryAService;
import dk.openncp.nationalconnector.Utils;
import dk.openncp.nationalconnector.xca.DocumentSearch;
import eu.epsos.protocolterminators.ws.server.common.NationalConnectorInterface;
import eu.epsos.protocolterminators.ws.server.exception.NIException;
import eu.epsos.protocolterminators.ws.server.xcpd.PatientSearchInterfaceWithDemographics;
import eu.europa.ec.sante.ehdsi.constant.error.OpenNCPErrorCode;
import eu.europa.ec.sante.ehdsi.openncp.assertionvalidator.exceptions.InsufficientRightsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import tr.com.srdc.epsos.data.model.PatientDemographics;
import tr.com.srdc.epsos.data.model.PatientId;
import tr.com.srdc.epsos.util.XMLUtil;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        if(date == null) return null;
        return Date.from(date.atStartOfDay(ZoneId.of("UTC")).toInstant());
    }

    static PatientId toPatientId(String id) {
        final var pattern = Pattern.compile("(.*)\\^\\^\\^(.*)");
        final var matcher = pattern.matcher(id);
        if (matcher.find()) {
            return new PatientId(matcher.group(1), matcher.group(2));
        }
        // If the string does not contain three carets then we assume it is a CPR number
        return new PatientId("DKCPR", id);
    }

    static PatientDemographics toEpsosPatient(dk.nsp.epps.api.model.PatientDemographics patient) {
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
            logger.warn("Unable to parse email value returned from Country A service");
        }
        try {
            if (patient.getTelephone() != null) {
                result.setTelephone(patient.getTelephone());
            }
        } catch (ParseException e) {
            logger.warn("Unable to parse telephone number returned from Country A service");
        }
        return result;
    }

    static List<PatientDemographics> getPatientDemographicsFromCountryA(List<PatientId> patientIds, Element soapHeader, DefaultApi countryAApi) throws NIException {
        try {
            // We only accept patient ids with root "DKCPR", otherwise we throw.
            final var pidsValidInvalid = patientIds.stream().collect(Collectors.partitioningBy(pid -> "DKCPR".equals(pid.getRoot())));
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
            final var response = countryAApi.postFindPatients(request);
            logger.info("Successfully retrieved patient demographics from Country A service.");
            return response.getPatients().stream()
                    .map(PatientSearch::toEpsosPatient)
                    .collect(Collectors.toList());
        } catch (ApiException e) {
            throw new NIException(OpenNCPErrorCode.ERROR_PI_GENERIC, e.getCode() > 0
                    ? "Bad response from Country A service"
                    : "Missing response from Country A service"
            );
        }
    }

    @Override
    public List<PatientDemographics> getPatientDemographics(List<PatientId> patientIds) throws NIException {
        return getPatientDemographicsFromCountryA(patientIds, this.soapHeader, CountryAService.api());
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
                    List.of(new PatientId("DKCPR", "1111111118")),
                    XMLUtil.parseContent("<SomeXml/>").getDocumentElement(),
                    CountryAService.api());
            return;
        } catch (NIException e) {
            throw new RuntimeException(e);
        }
    }
}
