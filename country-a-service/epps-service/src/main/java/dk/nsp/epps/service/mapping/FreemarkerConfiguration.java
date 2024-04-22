package dk.nsp.epps.service.mapping;

import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfiguration {
    private static freemarker.template.Configuration config;
    static {
        config = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_32);
        config.setClassLoaderForTemplateLoading(FreemarkerConfiguration.class.getClassLoader(), "/templates");
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
        config.setWrapUncheckedExceptions(true);
        config.setFallbackOnNullLoopVariable(false);
        config.setRecognizeStandardFileExtensions(true);
    }

    public static freemarker.template.Configuration config() {
        return config;
    }
}
