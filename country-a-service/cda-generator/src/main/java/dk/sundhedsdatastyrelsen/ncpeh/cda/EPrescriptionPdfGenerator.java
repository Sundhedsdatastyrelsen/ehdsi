package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionPdf;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PdfField;
import lombok.NonNull;
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
import java.util.stream.Stream;

public class EPrescriptionPdfGenerator {
    private static final PDFont FONT = new PDType1Font(Standard14Fonts.FontName.COURIER);
    private static final Integer FONT_SIZE = 14;
    private static final String TEMPLATE = "/pdfTemplates/Receptdesign.pdf";

    private EPrescriptionPdfGenerator() {
    }

    private static PDDocument initializeDocument() {
        try (var template = EPrescriptionPdfGenerator.class.getResourceAsStream(TEMPLATE)) {
            if (template == null) {
                throw new IllegalStateException("Could not find resource: " + TEMPLATE);
            }
            return Loader.loadPDF(template.readAllBytes());
        } catch (IOException e) {
            throw new IllegalStateException("Malformed or missing PDF template", e);
        }
    }

    public static byte[] generate(EPrescriptionPdf pdfModel) {
        try (var pdfDocument = initializeDocument()) {
            var pdfPage = pdfDocument.getPage(0);
            for (PdfField field : pdfModel.getFields()) {
                writeField(field, pdfPage, pdfDocument);
            }
            return saveBytes(pdfDocument);
        } catch (IOException e) {
            throw new IllegalStateException(
                String.format(
                    "Malformed of missing PDF file in resources path '%s'",
                    TEMPLATE), e);
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
                // wrap long lines, breaking up very long words if necessary
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

    private static byte[] saveBytes(PDDocument pdfDocument) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            pdfDocument.save(baos);
        } catch (IOException e) {
            throw new IllegalStateException("Could not generate PDF document", e);
        }
        return baos.toByteArray();
    }

}
