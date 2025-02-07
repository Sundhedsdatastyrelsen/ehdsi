import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataParser;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsFetchingService;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.GenericRepository;
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
        List<Lms02Data> lms02 = LmsDataParser.ParseLms02Data(result);

        var dataSource = new SingleConnectionDataSource("jdbc:sqlite::memory:", true);
        var repository = new GenericRepository<Lms02Data>(Lms02Data.class, "LMS02_DATA_TABLE", dataSource);

        int no = 0;
        for (var lmsData : lms02) {

            try {
                repository.insert(lmsData);
            } catch (Exception e) {
                var test = "1";
            }
            no++;
        }

        List<Lms02Data> lms02DataFromDatabase = repository.getAll();

    }

    @Test
    void testDownloadAndParseLms15() {
        String result = lmsFetchingService.getLmsDataFromServer("lms15.txt");
        List<Lms15Data> lms15 = LmsDataParser.ParseLms15Data(result);
    }

    @Test
    void testDownloadAndParseLms22() {
        String result = lmsFetchingService.getLmsDataFromServer("lms22.txt");
        List<Lms22Data> lms22 = LmsDataParser.ParseLms22Data(result);
    }


}
