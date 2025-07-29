package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaId;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Name;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import freemarker.template.TemplateException;
import io.opentelemetry.instrumentation.annotations.WithSpan;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class PatientSummaryL3Generator {
    private PatientSummaryL3Generator() {
    }

    public static String generate(PatientSummaryL3 dataModel) throws IOException, TemplateException {
        var template = FreemarkerConfiguration.config().getTemplate("patient-summary-cda-l3.ftlx");
        var writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }

    @WithSpan
    public static String generate(PatientSummaryInput input) throws MapperException, TemplateException, IOException {
        var model = inputToModel(input);
        return generate(model);
    }

    private static PatientSummaryL3 inputToModel(PatientSummaryInput input) {
        return PatientSummaryL3.builder()
            .documentId(CdaId.fromDocumentId(input.documentId()))
            .effectiveTime(OffsetDateTime.now())
            // TODO is the title correct?
            .title("Patient Summary")
            // TODO get the patient data somewhere instead of fake data.
            .patient(Patient.builder()
                .birthTime(LocalDate.of(1997, 3, 3))
                .genderCode(CdaCode.builder()
                    .codeSystem(Oid.ADMINISTRATIVE_GENDER)
                    .codeSystemVersion("913-20091020")
                    .code("F")
                    .displayName("Female")
                    .build())
                .id(new CdaId(Oid.DK_CPR, "0303970000"))
                .name(Name.fromFullName("Ghita Nørby"))
                // TODO address
                .build())
            .preferredHp(input.preferredHealthProfessional())
            .build();
    }
}
