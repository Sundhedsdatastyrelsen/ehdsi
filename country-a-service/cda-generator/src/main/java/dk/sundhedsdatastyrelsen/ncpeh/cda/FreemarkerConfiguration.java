package dk.sundhedsdatastyrelsen.ncpeh.cda;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfiguration {
    private static final Configuration config;
    static {
        config = new Configuration(Configuration.VERSION_2_3_32);
        config.setClassLoaderForTemplateLoading(FreemarkerConfiguration.class.getClassLoader(), "/templates");
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
        config.setWrapUncheckedExceptions(true);
        config.setFallbackOnNullLoopVariable(false);
        config.setRecognizeStandardFileExtensions(true);
    }

    public static Configuration config() {
        return config;
    }
}
