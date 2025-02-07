package dk.sundhedsdatastyrelsen.ncpeh.lms;

import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms14Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LmsScheduledUpdaterService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LmsScheduledUpdaterService.class);
    @Autowired
    private LmsFetchingService lmsFetchingService;
    @Autowired
    private LmsDataRepository lmsDataRepository;

    @Scheduled(cron = "0 0 3 * * *") // Run at 03:00 daily
    public void fetchNewLmsData() {
        log.info("Updating LMS02");
        String lms02Result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_02);
        List<Lms02Data> lms02Data = LmsDataParser.ParseLms02Data(lms02Result);
        lmsDataRepository.updateLms02(lms02Data);
        log.info("Finished updating LMS02");

        log.info("Updating LMS14");
        String lms14Result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_14);
        List<Lms14Data> lms14Data = LmsDataParser.ParseLms14Data(lms14Result);
        lmsDataRepository.updateLms14(lms14Data);
        log.info("Finished updating LMS14");

        log.info("Updating LMS15");
        String lms15Result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_15);
        List<Lms15Data> lms15Data = LmsDataParser.ParseLms15Data(lms15Result);
        lmsDataRepository.updateLms15(lms15Data);
        log.info("Finished updating LMS15");

        log.info("Updating LMS22");
        String lms22Result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FTP_FILE_NAMES.LMS_22);
        List<Lms22Data> lms22Data = LmsDataParser.ParseLms22Data(lms22Result);
        lmsDataRepository.updateLms22(lms22Data);
        log.info("Finished updating LMS22");


    }
}
