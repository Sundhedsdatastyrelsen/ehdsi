package eu.europa.ec.sante.openncp.portal.service;

import eu.europa.ec.sante.openncp.portal.model.SearchMaskResponse;

import java.util.List;

public interface AdministrationService {

    String fetchInternationalSearchMask(String countryCode);

    List<SearchMaskResponse> fetchAllInternationalSearchMask();
}
