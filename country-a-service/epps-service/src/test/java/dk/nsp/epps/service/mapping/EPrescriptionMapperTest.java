package dk.nsp.epps.service.mapping;

import dk.nsp.epps.mocks.fmk.data.FmkMockDataFactory;
import freemarker.template.TemplateExceptionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EPrescriptionMapperTest {
    private static EPrescriptionMapper mapper;

    @BeforeAll
    public static void setup() throws Exception {
        mapper = new EPrescriptionMapper(freemarkerCfg());
        mapper.initTemplate();
    }

    @Test
    public void test() throws Exception {
        var response = FmkMockDataFactory.getPrescriptionResponse();
        var result = mapper.mapResponse(response);
        System.out.println(result.get(0).getDocument());
    }

    private static freemarker.template.Configuration freemarkerCfg() {
        var cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_32);
        cfg.setClassLoaderForTemplateLoading(EPrescriptionMapperTest.class.getClassLoader(), "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        return cfg;
    }



}
