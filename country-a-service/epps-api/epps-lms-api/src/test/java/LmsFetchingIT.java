import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsDataParser;
import dk.sundhedsdatastyrelsen.ncpeh.lms.LmsFetchingService;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
