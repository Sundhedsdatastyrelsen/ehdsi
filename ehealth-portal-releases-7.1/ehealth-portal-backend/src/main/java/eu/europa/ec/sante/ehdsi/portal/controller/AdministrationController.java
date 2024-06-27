package eu.europa.ec.sante.ehdsi.portal.controller;

import eu.europa.ec.sante.ehdsi.portal.model.SearchMaskResponse;
import eu.europa.ec.sante.ehdsi.portal.service.AdministrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AdministrationController {

    private final Logger logger = LoggerFactory.getLogger(AdministrationController.class);
    private final AdministrationService administrationService;

    @Autowired
    public AdministrationController(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    @GetMapping(value = "/admin/ism/fetch")
    public ResponseEntity<String> fetchInternationalSearchMask(@RequestParam String countryCode) {

        logger.info("[REST] Fetch International Search Mask");
        String searchMask = administrationService.fetchInternationalSearchMask(countryCode);
        return ResponseEntity
                .ok()
                .body(searchMask);
    }

    @GetMapping(value = "/admin/ism/fetch_all")
    public ResponseEntity<List<SearchMaskResponse>> fetchAllInternationalSearchMask() {

        logger.info("[REST] Fetch International Search Mask");
        List<SearchMaskResponse> searchMaskResponseList = administrationService.fetchAllInternationalSearchMask();
        return ResponseEntity
                .ok()
                .body(searchMaskResponseList);
    }
}
