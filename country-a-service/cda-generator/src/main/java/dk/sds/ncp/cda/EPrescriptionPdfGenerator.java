package dk.sds.ncp.cda;

import dk.sds.ncp.cda.model.EPrescriptionPdf;
import dk.sds.ncp.cda.model.PdfField;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EPrescriptionPdfGenerator {
    private static final Integer FONT_SIZE = 14;
    private static final String TEMPLATE = "/pdfTemplates/Receptdesign.pdf"; //Load file from resources folder, based on the name input

    private static PDDocument initializeDocument() {
        try (var template = EPrescriptionPdfGenerator.class.getResourceAsStream(TEMPLATE)) {
            if (template == null) {
                throw new RuntimeException("Could not find resource: " + TEMPLATE);
            }
            return Loader.loadPDF(template.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Malformed or missing PDF template", e);
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
            throw new RuntimeException(String.format(
                "Malformed of missing PDF file in resources path '%s'",
                TEMPLATE), e);
        }
    }

    private static void writeField(PdfField field, PDPage pdfPage, PDDocument pdfDocument) {
        if (pdfDocument == null || pdfPage == null) {
            throw new RuntimeException("Error writing field, document not initialized");
        }
        try {
            writeTextAtCoordinates(
                field.getContent(),
                field.getXCoordinate(),
                field.getYCoordinate(),
                pdfDocument,
                pdfPage);
        } catch (IOException e) {
            throw new RuntimeException(String.format(
                "Error writing field content %s to document",
                String.join(";", field.getContent())), e);
        }
    }

    private static byte[] saveBytes(PDDocument pdfDocument) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            pdfDocument.save(baos);
        } catch (IOException e) {
            throw new RuntimeException("Could not generate PDF document", e);
        }
        return baos.toByteArray();
    }

    private static void writeTextAtCoordinates(String[] Text, Integer xCoordinate, Integer yCoordinate, PDDocument pdfDocument, PDPage pdfPage) throws IOException {
        // For each line in the array, we print a new line, shifted one fontsize down
        for (int lineNo = 0; lineNo < Text.length; lineNo++) {
            PDPageContentStream contentStream = new PDPageContentStream(
                pdfDocument,
                pdfPage,
                PDPageContentStream.AppendMode.APPEND,
                true);
            contentStream.beginText();

            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), FONT_SIZE);
            contentStream.newLineAtOffset(xCoordinate, yCoordinate - FONT_SIZE * lineNo);
            contentStream.showText(Text[lineNo]);

            contentStream.endText();
            contentStream.close();
        }
    }
}
