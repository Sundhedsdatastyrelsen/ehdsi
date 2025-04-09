package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.locallms.DataProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LmsDataProvider extends DataProvider {
    public LmsDataProvider(@Value("${local-lms.jdbcUrl}") String jdbcUrl) {
        super(jdbcUrl);
    }
}
