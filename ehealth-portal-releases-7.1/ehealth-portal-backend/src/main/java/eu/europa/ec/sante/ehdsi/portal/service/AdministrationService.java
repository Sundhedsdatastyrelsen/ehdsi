package eu.europa.ec.sante.ehdsi.portal.service;

import eu.europa.ec.sante.ehdsi.portal.model.SearchMaskResponse;

import java.util.List;

public interface AdministrationService {

    String fetchInternationalSearchMask(String countryCode);

    List<SearchMaskResponse> fetchAllInternationalSearchMask();
}
