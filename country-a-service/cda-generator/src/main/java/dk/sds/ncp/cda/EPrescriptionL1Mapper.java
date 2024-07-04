package dk.sds.ncp.cda;

import dk.sds.ncp.cda.model.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EPrescriptionL1Mapper {
    /**
     * Map a L3 model to a L1 Model, consisting of PDF Fields to be written on a PDF page
     *
     * @param dataModel - A L3 Datamodel
     * @return A list of PdfField objects, consisting of Coordinates and an array of strings intended as seperate lines for each string
     */
    public static List<PdfField> map(EPrescriptionL3 dataModel) {
        return List.of(
            new PdfField(50, 445, productLines(dataModel.getProduct())),
            new PdfField(50, 610, patientLines(dataModel.getPatient())),
            new PdfField(50, 710, authorLines((dataModel.getAuthor()))),
            new PdfField(390, 650, new String[]{String.format("ID: %s", dataModel.getPrescriptionId().getExtension())}),
            new PdfField(50, 185, dateLines(dataModel.getEffectiveTimeOffsetDateTime()))
        );
    }

    private static String[] productLines(Product product) {
        return new String[]{product.getName(), product.getDescription()};
    }

    private static String[] patientLines(Patient patient) {
        List<String> patientLines = new ArrayList<String>();
        addToListIfNotNullOrEmpty(patientLines, patient.getName().getFullName());
        for (String addressLine : patient.getAddress().getStreetAddressLines()) {
            addToListIfNotNullOrEmpty(patientLines, addressLine);
        }
        addToListIfNotNullOrEmpty(patientLines, constructPostalCityLine(patient.getAddress().getPostalCode(), patient.getAddress().getCity()));
        addToListIfNotNullOrEmpty(patientLines, patient.getAddress().getCountryCode());
        return patientLines.toArray(new String[0]);
    }

    private static String[] authorLines(Author author) {
        List<String> authorLines = new ArrayList<String>();
        addToListIfNotNullOrEmpty(authorLines, author.getName().getFullName());
        addToListIfNotNullOrEmpty(authorLines, author.getOrganization().getName());
        for (String addressLine : author.getOrganization().getAddress().getStreetAddressLines()) {
            addToListIfNotNullOrEmpty(authorLines, addressLine);
        }
        addToListIfNotNullOrEmpty(authorLines, constructPostalCityLine(author.getOrganization().getAddress().getPostalCode(), author.getOrganization().getAddress().getCity()));
        addToListIfNotNullOrEmpty(authorLines, author.getOrganization().getAddress().getCountryCode());
        return authorLines.toArray(new String[0]);
    }

    private static String[] dateLines(OffsetDateTime dateTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new String[]{fmt.format(dateTime)};
    }

    private static String constructPostalCityLine(String PostalCode, String CityName) {
        var finalLine = "";
        if (PostalCode != null && !PostalCode.isEmpty()) {
            finalLine += PostalCode + " ";
        }
        if (CityName != null && !CityName.isEmpty()) {
            finalLine += CityName;
        }
        return finalLine.trim();
    }

    private static void addToListIfNotNullOrEmpty(List<String> list, String value) {
        if (value != null && !value.isEmpty()) {
            list.add(value);
        }
    }
}
