package dk.sundhedsdatastyrelsen.ncpeh.lms;

import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms02Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms14Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms15Data;
import dk.sundhedsdatastyrelsen.ncpeh.lms.formats.Lms22Data;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LmsScheduledUpdaterService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LmsScheduledUpdaterService.class);

    private LmsFetchingService lmsFetchingService;
    private LmsDataRepository lmsDataRepository;

    public LmsScheduledUpdaterService(LmsFetchingService lmsFetchingService, LmsDataRepository lmsDataRepository) {
        this.lmsFetchingService = lmsFetchingService;
        this.lmsDataRepository = lmsDataRepository;
    }

    @Scheduled(cron = "0 0 3 * * *") // Run at 03:00 daily
    public void fetchNewLmsData() {
        log.info("Updating LMS02");
        try {
            String lms02Result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FtpFileNames.LMS_02);
            List<Lms02Data> lms02Data = LmsDataParser.parseLms02Data(lms02Result);
            lmsDataRepository.updateLms02(lms02Data);
        } catch (Exception e) {
            log.error("Failed updating LMS02", e);
        }
        log.info("Finished updating LMS02");

        log.info("Updating LMS14");
        try {
            String lms14Result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FtpFileNames.LMS_14);
            List<Lms14Data> lms14Data = LmsDataParser.parseLms14Data(lms14Result);
            lmsDataRepository.updateLms14(lms14Data);
        } catch (Exception e) {
            log.error("Failed updating LMS14", e);
        }
        log.info("Finished updating LMS14");

        log.info("Updating LMS15");
        try {
            String lms15Result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FtpFileNames.LMS_15);
            List<Lms15Data> lms15Data = LmsDataParser.parseLms15Data(lms15Result);
            lmsDataRepository.updateLms15(lms15Data);
        } catch (Exception e) {
            log.error("Failed updating LMS15", e);
        }
        log.info("Finished updating LMS15");

        log.info("Updating LMS22");
        try {
            String lms22Result = lmsFetchingService.getLmsDataFromServer(LmsConstants.FtpFileNames.LMS_22);
            List<Lms22Data> lms22Data = LmsDataParser.parseLms22Data(lms22Result);
            lmsDataRepository.updateLms22(lms22Data);
        } catch (Exception e) {
            log.error("Failed updating LMS22", e);
        }
        log.info("Finished updating LMS22");


    }
}
