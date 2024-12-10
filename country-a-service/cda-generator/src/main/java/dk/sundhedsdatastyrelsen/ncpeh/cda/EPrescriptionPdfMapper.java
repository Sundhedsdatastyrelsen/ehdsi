package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Author;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionL3;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EPrescriptionPdf;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.EhdsiUnit;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.Patient;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.PdfField;
import org.apache.commons.lang3.StringUtils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EPrescriptionPdfMapper {
    private EPrescriptionPdfMapper() {
    }

    /**
     * Map a L3 model to a L1 Model, consisting of PDF Fields to be written on a PDF page
     *
     * @param dataModel - A L3 Datamodel
     * @return A list of PdfField objects, consisting of Coordinates and an array of strings intended as seperate lines for each string
     */
    public static EPrescriptionPdf map(EPrescriptionL3 dataModel) {
        return new EPrescriptionPdf(List.of(
            new PdfField(50, 445, ordination(dataModel), 60),
            new PdfField(50, 610, patientLines(dataModel.getPatient()), 60),
            new PdfField(50, 710, authorLines((dataModel.getAuthor())), 40),
            new PdfField(390, 650, List.of(String.format("ID: %s", dataModel.getPrescriptionId().getExtension())), 40),
            new PdfField(50, 185, dateLines(dataModel.getEffectiveTimeOffsetDateTime()), 40)
        ));
    }

    private static String packageLine(EPrescriptionL3 model) {
        var quantity = model.getPackageQuantityLong();
        var packagePlural = quantity > 1 ? "pakker" : "pakke";
        var unit = switch (model.getProduct().getSize().getUnit()) {
            case EhdsiUnit.WithCode u -> u.getCode();
            case EhdsiUnit.WithTranslation u -> u.getTranslation();
        };
        return String.format(
            "%s %s à %s %s %s",
            quantity,
            packagePlural,
            model.getProduct().getSize().getValue(),
            unit,
            model.getProduct().getFormCode().getDisplayName());
    }

    private static List<String> ordination(EPrescriptionL3 model) {
        // Example:
        // Pinex 500mg (Paracetemol)
        // 1 pakke à 100 stk filmovertrukne tabletter
        //
        // mod smerter
        //
        // Dosering: 1 tablet 3 gange daglig

        var lines = new ArrayList<String>();
        lines.add(String.format(
            "%s %s (%s)",
            model.getProduct().getName(),
            model.getProduct().getDescription(),
            model.getProduct().getAtcCode().getDisplayName()));
        lines.add(packageLine(model));
        lines.add("");
        lines.add(model.getIndicationText());
        lines.add("");
        lines.add(String.format("Dosering: %s", model.getEntryText()));
        return lines;
    }

    private static List<String> patientLines(Patient patient) {
        var patientLines = new ArrayList<String>();
        var cpr =
            patient.getId().getExtension().substring(0, 6)
            + "-"
            + patient.getId().getExtension().substring(6, 10);
        var nameCprLine = StringUtils.rightPad(patient.getName().getFullName(), 30) + " " + cpr;
        patientLines.add(nameCprLine);
        for (String addressLine : patient.getAddress().getStreetAddressLines()) {
            addToListIfNotNullOrEmpty(patientLines, addressLine);
        }
        addToListIfNotNullOrEmpty(patientLines, constructPostalCityLine(
            patient.getAddress().getPostalCode(),
            patient.getAddress().getCity()));
        addToListIfNotNullOrEmpty(patientLines, patient.getAddress().getCountryCode());
        return patientLines;
    }

    private static List<String> authorLines(Author author) {
        var authorLines = new ArrayList<String>();
        addToListIfNotNullOrEmpty(authorLines, author.getName().getFullName());
        addToListIfNotNullOrEmpty(authorLines, author.getOrganization().getName());
        if (author.getOrganization().getAddress() != null) {
            for (String addressLine : author.getOrganization().getAddress().getStreetAddressLines()) {
                addToListIfNotNullOrEmpty(authorLines, addressLine);
            }
            addToListIfNotNullOrEmpty(authorLines, constructPostalCityLine(
                author.getOrganization().getAddress().getPostalCode(),
                author.getOrganization().getAddress().getCity()));
            addToListIfNotNullOrEmpty(authorLines, author.getOrganization().getAddress().getCountryCode());
        }
        return authorLines;
    }

    private static List<String> dateLines(OffsetDateTime dateTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return List.of(fmt.format(dateTime));
    }

    private static String constructPostalCityLine(String postalCode, String cityName) {
        var finalLine = "";
        if (postalCode != null && !postalCode.isEmpty()) {
            finalLine += postalCode + " ";
        }
        if (cityName != null && !cityName.isEmpty()) {
            finalLine += cityName;
        }
        return finalLine.trim();
    }

    private static void addToListIfNotNullOrEmpty(List<String> list, String value) {
        if (value != null && !value.isEmpty()) {
            list.add(value);
        }
    }
}
