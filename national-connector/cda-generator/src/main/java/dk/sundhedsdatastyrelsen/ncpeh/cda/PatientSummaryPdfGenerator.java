package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryPdf;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PdfField;
import lombok.NonNull;
import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

public class PatientSummaryPdfGenerator {
    private static final PDFont FONT =
        new PDType1Font(Standard14Fonts.FontName.COURIER);

    private static final PDFont FONT_BOLD =
        new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD);

    private static final int TITLE_FONT_SIZE = 16;
    private static final int BODY_FONT_SIZE = 11;

    private static final float MARGIN_LEFT = 50f;
    private static final float MARGIN_BOTTOM = 50f;
    private static final float START_Y = 790f;

    private static final float TITLE_LINE_HEIGHT =
        TITLE_FONT_SIZE * 1.5f;

    private static final float BODY_LINE_HEIGHT =
        BODY_FONT_SIZE * 1.5f;

    private PatientSummaryPdfGenerator() {
    }

    public static byte[] generate(PatientSummaryPdf pdfModel) {
        try (var pdfDocument = new PDDocument()) {
            var writer = new PdfWriter(pdfDocument);

            for (var field : pdfModel.getFields()) {
                writer.writeField(field);
            }

            return saveBytes(pdfDocument);
        } catch (IOException e) {
            throw new IllegalStateException("Could not generate Patient Summary PDF", e);
        }
    }

    private static byte[] saveBytes(PDDocument pdfDocument) {
        var output = new ByteArrayOutputStream();

        try {
            pdfDocument.save(output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Could not save Patient Summary PDF", e);
        }
    }

    private static final class PdfWriter {
        private final PDDocument document;

        private PDPage page;
        private float currentY;
        private boolean firstPage;

        private PdfWriter(PDDocument document) {
            this.document = document;
            this.firstPage = true;
            newPage();
        }

        private void writeField(@NonNull PdfField field) {
            /*
             * On the first page we respect the Y coordinate from the mapper,
             * but never move upwards and overwrite content already written.
             *
             * On later pages content simply continues from START_Y.
             */
            if (firstPage) {
                currentY = Math.min(currentY, field.y());
            }

            var lines = field.lines().stream()
                .flatMap(line -> wrapLine(line, field.wrapLength())).toList();

            for (var line : lines) {
                ensureSpace(BODY_LINE_HEIGHT);

                writeLine(field.x(), currentY, line, FONT, BODY_FONT_SIZE);

                currentY -= BODY_LINE_HEIGHT;
            }

            // Small gap between fields.
            currentY -= BODY_LINE_HEIGHT;
        }

        private Stream<String> wrapLine(String line, int wrapLength) {
            if (line == null || line.isEmpty()) {
                return Stream.of("");
            }

            return WordUtils.wrap(line, wrapLength, "\n", true).lines();
        }

        private void ensureSpace(float requiredHeight) {
            if (currentY - requiredHeight < MARGIN_BOTTOM) {
                newPage();
                firstPage = false;
            }
        }

        private void newPage() {
            page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            currentY = START_Y;
        }

        private void writeLine(float x, float y, String line, PDFont font, int fontSize) {
            try (var stream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                stream.beginText();
                stream.setNonStrokingColor(Color.BLACK);
                stream.setFont(font, fontSize);
                stream.newLineAtOffset(x, y);
                stream.showText(line);
                stream.endText();
            } catch (IOException e) {
                throw new IllegalStateException("Error writing field content to Patient Summary PDF", e);
            }
        }
    }
}
