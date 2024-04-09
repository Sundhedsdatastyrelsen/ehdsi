package dk.nsp.epps.service.mapping;

import dk.nsp.epps.mocks.fmk.data.FmkMockDataFactory;
import dk.nsp.epps.service.PrescriptionService.PrescriptionFilter;
import freemarker.template.TemplateExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EPrescriptionMapperTest {
    private static EPrescriptionMapper mapper;

    @BeforeAll
    public static void setup() throws Exception {
        mapper = new EPrescriptionMapper(freemarkerCfg(), "repoid");
    }

    @Test
    public void testExpectedNumberOfEpsosDocuments() throws Exception {
        var response = FmkMockDataFactory.getPrescriptionResponse();
        var result = mapper.mapResponse("DKCPR^^^1111111118", new PrescriptionFilter( null, null, null, null), response);
        Assertions.assertEquals(1, result.size());
    }

    private static freemarker.template.Configuration freemarkerCfg() {
        var cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_32);
        cfg.setClassLoaderForTemplateLoading(EPrescriptionMapperTest.class.getClassLoader(), "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setRecognizeStandardFileExtensions(true);
        return cfg;
    }
}
