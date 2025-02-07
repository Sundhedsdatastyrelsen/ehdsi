import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsConstants;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataParser;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataRepository;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsFetchingService;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsScheduledUpdaterService;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms14Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.GenericRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(LmsTestConfig.class)
public class LmsFetchingIT {

    @Autowired
    private LmsFetchingService lmsFetchingService;

    @Autowired
    private LmsDataRepository lmsDataRepository;

    @Autowired
    private LmsScheduledUpdaterService lmsScheduledUpdaterService;

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

        lmsDataRepository.updateLms02(lms02Data);

        List<Lms02Data> databaseData = lmsDataRepository.getAllLms02();
        Assertions.assertEquals(lms02Data.size(), databaseData.size());
        for (var lmsDataRow : lms02Data) {
            Assertions.assertTrue(databaseData.contains(lmsDataRow));
        }

    }

    @Test
    void testDownloadAndParseLms14() {
        String result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_14);
        List<Lms14Data> lms14Data = LmsDataParser.ParseLms14Data(result);

        lmsDataRepository.updateLms14(lms14Data);


        List<Lms14Data> databaseData = lmsDataRepository.getAllLms14();
        Assertions.assertEquals(lms14Data.size(), databaseData.size());
        for (var lmsDataRow : lms14Data) {
            Assertions.assertTrue(databaseData.contains(lmsDataRow));
        }
    }

    @Test
    void testDownloadAndParseLms15() {
        String result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_15);
        List<Lms15Data> lms15Data = LmsDataParser.ParseLms15Data(result);

        lmsDataRepository.updateLms15(lms15Data);


        List<Lms15Data> databaseData = lmsDataRepository.getAllLms15();
        Assertions.assertEquals(lms15Data.size(), databaseData.size());
        for (var lmsDataRow : lms15Data) {
            Assertions.assertTrue(databaseData.contains(lmsDataRow));
        }
    }

    @Test
    void testDownloadAndParseLms22() {
        String result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_22);
        List<Lms22Data> lms22data = LmsDataParser.ParseLms22Data(result);

        lmsDataRepository.updateLms22(lms22data);

        List<Lms22Data> databaseData = lmsDataRepository.getAllLms22();
        Assertions.assertEquals(lms22data.size(), databaseData.size());
        for (var lmsDataRow : lms22data) {
            Assertions.assertTrue(databaseData.contains(lmsDataRow));
        }
    }

    @Test
    void testScheduledUpdate() {
        var lms02 = lmsDataRepository.getAllLms02();
        var lms14 = lmsDataRepository.getAllLms14();
        var lms15 = lmsDataRepository.getAllLms15();
        var lms22 = lmsDataRepository.getAllLms22();

        Assertions.assertEquals(0, lms02.size());
        Assertions.assertEquals(0, lms14.size());
        Assertions.assertEquals(0, lms15.size());
        Assertions.assertEquals(0, lms22.size());

        lmsScheduledUpdaterService.fetchNewLmsData();

        lms02 = lmsDataRepository.getAllLms02();
        lms14 = lmsDataRepository.getAllLms14();
        lms15 = lmsDataRepository.getAllLms15();
        lms22 = lmsDataRepository.getAllLms22();

        Assertions.assertNotEquals(0, lms02.size());
        Assertions.assertNotEquals(0, lms14.size());
        Assertions.assertNotEquals(0, lms15.size());
        Assertions.assertNotEquals(0, lms22.size());
    }


}
