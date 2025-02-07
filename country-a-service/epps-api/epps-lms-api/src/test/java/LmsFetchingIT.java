import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsConstants;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataParser;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataRepository;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsFetchingService;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms14Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.GenericRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;

@SpringJUnitConfig(LmsTestConfig.class)
public class LmsFetchingIT {

    @Autowired
    private LmsFetchingService lmsFetchingService;

    @Autowired
    private LmsDataRepository lmsDataRepository;

    @Test
    void testDownloadFile() {
        String result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_02);
    }

    @Test
    void testCreationOfDatabases() {
        var dataSource = new LmsTestConfig().dataSource();
        var jdbcTemplate = new JdbcTemplate(dataSource);
        var repository = new GenericRepository<Lms02Data>(Lms02Data.class, LmsConstants.DATABASE_TABLE_NAMES.LMS_02, jdbcTemplate);
    }

    @Test
    void testDownloadAndParseAndStoreLms02() {
        String result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_02);
        List<Lms02Data> lms02Data = LmsDataParser.ParseLms02Data(result);

        lmsDataRepository.UpdateLms02(lms02Data);

        List<Lms02Data> databaseData = lmsDataRepository.GetAllLms02();
        Assertions.assertEquals(lms02Data.size(), databaseData.size());
        for (var lmsDataRow : lms02Data) {
            Assertions.assertTrue(databaseData.contains(lmsDataRow));
        }

    }

    @Test
    void testDownloadAndParseLms14() {
        String result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_14);
        List<Lms14Data> lms14Data = LmsDataParser.ParseLms14Data(result);

        lmsDataRepository.UpdateLms14(lms14Data);


        List<Lms14Data> databaseData = lmsDataRepository.GetAllLms14();
        Assertions.assertEquals(lms14Data.size(), databaseData.size());
        for (var lmsDataRow : lms14Data) {
            Assertions.assertTrue(databaseData.contains(lmsDataRow));
        }
    }

    @Test
    void testDownloadAndParseLms15() {
        String result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_15);
        List<Lms15Data> lms15Data = LmsDataParser.ParseLms15Data(result);

        lmsDataRepository.UpdateLms15(lms15Data);


        List<Lms15Data> databaseData = lmsDataRepository.GetAllLms15();
        Assertions.assertEquals(lms15Data.size(), databaseData.size());
        for (var lmsDataRow : lms15Data) {
            Assertions.assertTrue(databaseData.contains(lmsDataRow));
        }
    }

    @Test
    void testDownloadAndParseLms22() {
        String result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_22);
        List<Lms22Data> lms22data = LmsDataParser.ParseLms22Data(result);

        lmsDataRepository.UpdateLms22(lms22data);

        List<Lms22Data> databaseData = lmsDataRepository.GetAllLms22();
        Assertions.assertEquals(lms22data.size(), databaseData.size());
        for (var lmsDataRow : lms22data) {
            Assertions.assertTrue(databaseData.contains(lmsDataRow));
        }
    }


}
