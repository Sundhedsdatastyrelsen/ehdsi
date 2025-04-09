package dk.sundhedsdatastyrelsen.ncpeh.service;

import dk.sundhedsdatastyrelsen.ncpeh.locallms.DataProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class LmsDataProvider extends DataProvider {
    public LmsDataProvider(@Qualifier("localLmsDataSource") DataSource dataSource) {
        super(dataSource);
    }
}
