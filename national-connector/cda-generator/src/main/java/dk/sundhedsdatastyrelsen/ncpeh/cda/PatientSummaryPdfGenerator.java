package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PdfField;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PatientSummaryPdfGenerator {
    private static final PDFont FONT = new PDType1Font(Standard14Fonts.FontName.COURIER);
    private static final int FONT_SIZE = 14;
    private static final String TEMPLATE = "/pdfTemplates/Receptdesign.pdf";

    private PatientSummaryPdfGenerator() {
    }

    public static byte[] generate(PatientSummaryL3 model) {
        var fields = List.of(
            new PdfField(50, 710, List.of(model.getTitle()), 60),
            new PdfField(50, 610, patientLines(model), 60),
            new PdfField(50, 445, preferredHpLines(model), 60),
            new PdfField(390, 650, List.of("ID: " + model.getDocumentId().getExtension()), 40),
            new PdfField(50, 185, List.of(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(model.getEffectiveTime())), 40)
        );
        return generatePdf(fields);
    }

    private static List<String> patientLines(PatientSummaryL3 model) {
        var patient = model.getPatient();
        var cpr = patient.getId().getExtension();
        if (cpr != null && cpr.length() >= 10) {
            cpr = cpr.substring(0, 6) + "-" + cpr.substring(6, 10);
        }
        var lines = new ArrayList<String>();
        lines.add(StringUtils.rightPad(patient.getName().getFullName(), 30) + " " + cpr);
        return lines;
    }

    private static List<String> preferredHpLines(PatientSummaryL3 model) {
        var hp = model.getPreferredHp();
        if (hp == null) {
            return List.of();
        }
        var lines = new ArrayList<String>();
        if (hp.getName() != null) {
            lines.add(hp.getName().getFullName());
        }
        if (hp.getAddress() != null) {
            for (var line : hp.getAddress().getStreetAddressLines()) {
                if (line != null && !line.isBlank()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    private static byte[] generatePdf(List<PdfField> fields) {
        try (var templateStream = PatientSummaryPdfGenerator.class.getResourceAsStream(TEMPLATE)) {
            if (templateStream == null) {
                throw new IllegalStateException("Could not find resource: " + TEMPLATE);
            }
            try (var pdfDocument = Loader.loadPDF(templateStream.readAllBytes())) {
                var pdfPage = pdfDocument.getPage(0);
                for (var field : fields) {
                    writeField(field, pdfPage, pdfDocument);
                }
                var baos = new ByteArrayOutputStream();
                pdfDocument.save(baos);
                return baos.toByteArray();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not generate patient summary PDF", e);
        }
    }

    private static void writeField(@NonNull PdfField field, @NonNull PDPage pdfPage, PDDocument pdfDocument) {
        try (var stream = new PDPageContentStream(
            pdfDocument,
            pdfPage,
            PDPageContentStream.AppendMode.APPEND,
            true
        )) {
            stream.beginText();
            stream.setNonStrokingColor(Color.BLACK);
            stream.setFont(FONT, FONT_SIZE);
            stream.newLineAtOffset(field.x(), field.y());
            var lines = field.lines().stream()
                .flatMap(l ->
                    l == null || l.isEmpty()
                        ? Stream.of("")
                        : WordUtils.wrap(l, field.wrapLength(), "\n", true).lines()
                )
                .toList();
            for (var line : lines) {
                stream.showText(line);
                stream.newLineAtOffset(0, -FONT_SIZE);
            }
            stream.endText();
        } catch (IOException e) {
            throw new IllegalStateException("Error writing field content to document", e);
        }
    }
}
