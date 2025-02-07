import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataParser;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsFetchingService;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.GenericRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(LmsTestConfig.class)
public class LmsFetchingIT {

    @Autowired
    private LmsFetchingService lmsFetchingService;

    @Test
    void testDownloadFile() {
        String result = lmsFetchingService.getLmsDataFromServer("lms01.txt");
    }

    @Test
    void testCreationOfDatabases() {
        var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
        var repository = new GenericRepository<Lms02Data>(Lms02Data.class, "LMS02_DATA_TABLE", dataSource);
    }

    @Test
    void testDownloadAndParseAndStoreLms02() {
        String result = lmsFetchingService.getLmsDataFromServer("lms02.txt");
        List<Lms02Data> lms02Data = LmsDataParser.ParseLms02Data(result);

        var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
        var repository = new GenericRepository<Lms02Data>(Lms02Data.class, "LMS02_DATA_TABLE", dataSource);

        for (var lmsData : lms02Data) {
            repository.insert(lmsData);
        }

        List<Lms02Data> lms02DataFromDatabase = repository.getAll();
        Assertions.assertEquals(lms02Data.size(), lms02DataFromDatabase.size());
        for (var lmsDataRow : lms02Data) {
            Assertions.assertTrue(lms02DataFromDatabase.contains(lmsDataRow));
        }

    }

    @Test
    void testDownloadAndParseLms15() {
        String result = lmsFetchingService.getLmsDataFromServer("lms15.txt");
        List<Lms15Data> lms15Data = LmsDataParser.ParseLms15Data(result);

        var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
        var repository = new GenericRepository<Lms15Data>(Lms15Data.class, "LMS15_DATA_TABLE", dataSource);

        for (var lmsData : lms15Data) {
            repository.insert(lmsData);
        }

        List<Lms15Data> lms15DataFromDatabase = repository.getAll();
        Assertions.assertEquals(lms15Data.size(), lms15DataFromDatabase.size());
        for (var lmsDataRow : lms15Data) {
            Assertions.assertTrue(lms15DataFromDatabase.contains(lmsDataRow));
        }
    }

    @Test
    void testDownloadAndParseLms22() {
        String result = lmsFetchingService.getLmsDataFromServer("lms22.txt");
        List<Lms22Data> lms22data = LmsDataParser.ParseLms22Data(result);

        var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
        var repository = new GenericRepository<Lms22Data>(Lms22Data.class, "LMS22_DATA_TABLE", dataSource);

        for (var lmsData : lms22data) {
            repository.insert(lmsData);
        }

        List<Lms22Data> lms22DataFromDatabase = repository.getAll();
        Assertions.assertEquals(lms22data.size(), lms22DataFromDatabase.size());
        for (var lmsDataRow : lms22data) {
            Assertions.assertTrue(lms22DataFromDatabase.contains(lmsDataRow));
        }
    }


}
