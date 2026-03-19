package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Address;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PatientSummaryL3;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PatientSummaryPdfGenerator {
    private static final PDFont FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private static final PDFont FONT_BOLD = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final int TITLE_FONT_SIZE = 16;
    private static final int BODY_FONT_SIZE = 11;
    private static final float MARGIN_LEFT = 50f;
    private static final float START_Y = 790f;
    private static final float TITLE_LINE_HEIGHT = TITLE_FONT_SIZE * 1.5f;
    private static final float BODY_LINE_HEIGHT = BODY_FONT_SIZE * 1.5f;
    private static final String SEPARATOR = "----------------------------------------";

    private PatientSummaryPdfGenerator() {
    }

    public static byte[] generate(PatientSummaryL3 model) {
        try (var pdfDocument = new PDDocument()) {
            var page = new PDPage(PDRectangle.A4);
            pdfDocument.addPage(page);
            writeContent(model, page, pdfDocument);
            pdfDocument.getDocumentInformation().getCOSObject().removeItem(COSName.CREATION_DATE);
            pdfDocument.getDocumentCatalog().setMetadata(null);
            var fixedId = new COSString(new byte[16]);
            var idArray = new COSArray();
            idArray.add(fixedId); //Set permanent ID
            idArray.add(fixedId); //Set revision ID
            pdfDocument.getDocument().getTrailer().setItem(COSName.ID, idArray);
            var baos = new ByteArrayOutputStream();
            pdfDocument.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Could not generate patient summary PDF", e);
        }
    }

    private static void writeContent(PatientSummaryL3 model, PDPage page, PDDocument doc) throws IOException {
        try (var stream = new PDPageContentStream(doc, page)) {
            stream.beginText();

            // Title
            stream.setFont(FONT_BOLD, TITLE_FONT_SIZE);
            stream.newLineAtOffset(MARGIN_LEFT, START_Y);
            stream.showText(model.getTitle());
            stream.newLineAtOffset(0, -TITLE_LINE_HEIGHT);

            // Document metadata
            stream.setFont(FONT, BODY_FONT_SIZE);
            stream.showText("Generated:   " + formatDateForDisplay(model.getEffectiveTime()));
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
            stream.showText("Document ID: " + model.getDocumentId().getExtension());
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT * 1.5f);

            // Patient section
            stream.setFont(FONT_BOLD, BODY_FONT_SIZE);
            stream.showText("PATIENT");
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT * 0.6f);
            stream.setFont(FONT, BODY_FONT_SIZE);
            stream.showText(SEPARATOR);
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);

            var patient = model.getPatient();
            stream.showText("Name:          " + patient.getName().getFullName());
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
            stream.showText("CPR:           " + formatCpr(patient.getId().getExtension()));
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
            stream.showText("Date of birth: " + formatDate(patient.getBirthTime()));
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
            var genderDisplay = patient.getGenderCode().getDisplayName();
            if (genderDisplay != null) {
                stream.showText("Gender:        " + genderDisplay);
                stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
            }
            if (patient.getAddress() != null) {
                for (var line : addressLines("Address:", patient.getAddress())) {
                    stream.showText(line);
                    stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
                }
            }

            // Preferred HP section
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT * 0.5f);
            stream.setFont(FONT_BOLD, BODY_FONT_SIZE);
            stream.showText("PREFERRED HEALTH PROFESSIONAL");
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT * 0.6f);
            stream.setFont(FONT, BODY_FONT_SIZE);
            stream.showText(SEPARATOR);
            stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);

            var hp = model.getPreferredHp();
            if (hp == null) {
                stream.showText("No preferred health professional on record.");
                stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
            } else {
                if (hp.getName() != null) {
                    stream.showText("Name:          " + hp.getName().getFullName());
                    stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
                }
                if (hp.getAddress() != null) {
                    for (var line : addressLines("Address:", hp.getAddress())) {
                        stream.showText(line);
                        stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
                    }
                }
                for (var telecom : hp.getTelecoms()) {
                    var phoneNumber = telecom.getValue().replaceFirst("^tel:", "");
                    stream.showText("Phone:         " + phoneNumber);
                    stream.newLineAtOffset(0, -BODY_LINE_HEIGHT);
                }
            }

            stream.endText();
        }
    }

    private static List<String> addressLines(String label, Address address) {
        var lines = new ArrayList<String>();
        var streets = StreamSupport.stream(address.getStreetAddressLines().spliterator(), false)
            .filter(s -> s != null && !s.isBlank())
            .toList();
        var cityLine = List.of(address.getPostalCode(), address.getCity()).stream()
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
        var allAddressLines = new ArrayList<String>();
        allAddressLines.addAll(streets);
        if (!cityLine.isBlank()) {
            allAddressLines.add(cityLine);
        }
        if (address.getCountryCode() != null) {
            allAddressLines.add(address.getCountryCode());
        }
        if (!allAddressLines.isEmpty()) {
            lines.add(label + "       " + allAddressLines.get(0));
            for (int i = 1; i < allAddressLines.size(); i++) {
                lines.add("               " + allAddressLines.get(i));
            }
        }
        return lines;
    }

    private static String formatCpr(String cpr) {
        if (cpr != null && cpr.length() >= 10) {
            return cpr.substring(0, 6) + "-" + cpr.substring(6, 10);
        }
        return cpr != null ? cpr : "";
    }

    private static String formatDate(String cdaDate) {
        // cdaDate is in yyyyMMdd format
        if (cdaDate != null && cdaDate.length() >= 8) {
            return cdaDate.substring(0, 4) + "-" + cdaDate.substring(4, 6) + "-" + cdaDate.substring(6, 8);
        }
        return cdaDate != null ? cdaDate : "";
    }

    private static String formatDateForDisplay(String cdaDateTime) {
        // cdaDateTime is in yyyyMMddHHmmssZ format; extract and reformat the date part
        if (cdaDateTime != null && cdaDateTime.length() >= 8) {
            return cdaDateTime.substring(0, 4) + "-" + cdaDateTime.substring(4, 6) + "-" + cdaDateTime.substring(6, 8);
        }
        return cdaDateTime != null ? cdaDateTime : "";
    }
}
