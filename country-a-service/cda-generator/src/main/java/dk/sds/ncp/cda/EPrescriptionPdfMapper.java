package dk.sds.ncp.cda;

import dk.sds.ncp.cda.model.Author;
import dk.sds.ncp.cda.model.EPrescriptionL3;
import dk.sds.ncp.cda.model.EPrescriptionPdf;
import dk.sds.ncp.cda.model.Patient;
import dk.sds.ncp.cda.model.PdfField;
import dk.sds.ncp.cda.model.Product;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EPrescriptionPdfMapper {
    private EPrescriptionPdfMapper() {}

    /**
     * Map a L3 model to a L1 Model, consisting of PDF Fields to be written on a PDF page
     *
     * @param dataModel - A L3 Datamodel
     * @return A list of PdfField objects, consisting of Coordinates and an array of strings intended as seperate lines for each string
     */
    public static EPrescriptionPdf map(EPrescriptionL3 dataModel) {
        return new EPrescriptionPdf(List.of(
            new PdfField(50, 445, productLines(dataModel.getProduct()), 60),
            new PdfField(50, 610, patientLines(dataModel.getPatient()), 60),
            new PdfField(50, 710, authorLines((dataModel.getAuthor())), 40),
            new PdfField(390, 650, List.of(String.format("ID: %s", dataModel.getPrescriptionId().getExtension())), 40),
            new PdfField(50, 185, dateLines(dataModel.getEffectiveTimeOffsetDateTime()), 40)
        ));
    }

    private static List<String> productLines(Product product) {
        return List.of(product.getName(), product.getDescription());
    }

    private static List<String> patientLines(Patient patient) {
        var patientLines = new ArrayList<String>();
        addToListIfNotNullOrEmpty(patientLines, patient.getName().getFullName());
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
