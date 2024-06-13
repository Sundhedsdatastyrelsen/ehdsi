package dk.sds.ncp.cda;

import dk.sds.ncp.cda.model.*;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EPrescriptionL1Mapper {
    /**
     * Map a L3 model to a L1 Model, consisting of PDF Fields to be written on a PDF page
     * @param dataModel - A L3 Datamodel
     * @return A list of PdfField objects, consisting of Coordinates and an array of strings intended as seperate lines for each string
     */
    public static List<PdfField> Map(EPrescriptionL3 dataModel){
        return Arrays.asList(
            new PdfField(50,445,GetProductLines(dataModel.getProduct())),
            new PdfField(50,610,GetPatientLines(dataModel.getPatient())),
            new PdfField(50,710,GetAuthorLines((dataModel.getAuthor()))),
            new PdfField(390,650,new String[]{String.format("ID: %s",dataModel.getPrescriptionId().getExtension())}),
            new PdfField(50,185, GetDateLine(dataModel.getEffectiveTimeOffsetDateTime()))
        );
    }

    private static String[] GetProductLines(Product product){
        return new String[]{product.getName(),product.getDescription()};
    }

    private static String[] GetPatientLines(Patient patient){
        List<String> patientLines = new ArrayList<String>();
        AddToListIfNotNullOrEmpty(patientLines,patient.getName().getFullName());
        for(String addressLine : patient.getAddress().getStreetAddressLines()){
            AddToListIfNotNullOrEmpty(patientLines,addressLine);
        }
        AddToListIfNotNullOrEmpty(patientLines,ConstructPostalCityLine(patient.getAddress().getPostalCode(),patient.getAddress().getCity()));
        AddToListIfNotNullOrEmpty(patientLines,patient.getAddress().getCountryCode());
        return patientLines.toArray(new String[0]);
    }

    private static String[] GetAuthorLines(Author author){
        List<String> authorLines = new ArrayList<String>();
        AddToListIfNotNullOrEmpty(authorLines,author.getName().getFullName());
        AddToListIfNotNullOrEmpty(authorLines,author.getOrganization().getName());
        for(String addressLine : author.getOrganization().getAddress().getStreetAddressLines()){
            AddToListIfNotNullOrEmpty(authorLines,addressLine);
        }
        AddToListIfNotNullOrEmpty(authorLines,ConstructPostalCityLine(author.getOrganization().getAddress().getPostalCode(),author.getOrganization().getAddress().getCity()));
        AddToListIfNotNullOrEmpty(authorLines,author.getOrganization().getAddress().getCountryCode());
        return authorLines.toArray(new String[0]);
    }

    private static String[] GetDateLine(OffsetDateTime dateTime){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new String[]{fmt.format(dateTime)};
    }

    private static String ConstructPostalCityLine(String PostalCode, String CityName){
        var finalLine = "";
        if(PostalCode != null && !PostalCode.isEmpty()){
            finalLine += PostalCode+" ";
        }
        if(CityName != null && !CityName.isEmpty()){
            finalLine += CityName;
        }
        return finalLine.trim();
    }

    private static void AddToListIfNotNullOrEmpty(List<String> list, String value){
        if(value != null && !value.isEmpty()){
            list.add(value);
        }
    }


}
