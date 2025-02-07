package dk.sundhedsdatastyrelsen.ncpeh.lms.formats;

import dk.sundhedsdatastyrelsen.ncpeh.lms.parsing.FixedWidthField;
import dk.sundhedsdatastyrelsen.ncpeh.lms.sql.DatabasePrimaryKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Lms02Data {
    @DatabasePrimaryKey
    @FixedWidthField(start = 0, length = 11)
    private String DrugId;
    @FixedWidthField(start = 11, length = 6)
    private String ProductNumber; //Varenummer
    @FixedWidthField(start = 17, length = 3)
    private String AlphabeticSequence; //Alfabetisk sekvensnummer
    @FixedWidthField(start = 20, length = 6)
    private String PackagePartProductNumber; //Varenummer for delpakning
    @FixedWidthField(start = 26, length = 3)
    private String NumberOfPackageParts; //Antal delpakninger

    @FixedWidthField(start = 29, length = 30)
    private String PackageSizeText; //Pakningsstørrelse, klartekst
    @FixedWidthField(start = 59, length = 8)
    private String PackageSizeNumber; //Pakningsstørrelse, numerisk
    @FixedWidthField(start = 67, length = 2)
    private String PackageSizeUnit; //Pakningsstørrelse, enhed

    @FixedWidthField(start = 69, length = 4)
    private String PackagingType; //Emballagetype
    @FixedWidthField(start = 73, length = 5)
    private String DeliveryStatement; //Udleveringsbestemmelse
    @FixedWidthField(start = 78, length = 5)
    private String DeliverySpecialty; //Udleveringsspeciale
    @FixedWidthField(start = 83, length = 2)
    private String MedicalSubsidyCode; //Medicintilskudskode
    @FixedWidthField(start = 85, length = 5)
    private String MedicalSubsidyClause; //Klausul for medicintilskud
    @FixedWidthField(start = 90, length = 9)
    private String DddPerPackage; //Antal DDD pr. pakning

    @FixedWidthField(start = 99, length = 2)
    private String StorageTimeNumber; //Opbevaringstid, numerisk
    @FixedWidthField(start = 101, length = 1)
    private String StorageTimeUnit; //Opbevaringstid, enhed
    @FixedWidthField(start = 102, length = 1)
    private String StorageTerm; //Opbevaringsbetingelser

    @FixedWidthField(start = 103, length = 8)
    private String CreatedDate; //OprettelsesDato
    @FixedWidthField(start = 111, length = 8)
    private String PriceChangedDate; //Dato for seneste prisændring
    @FixedWidthField(start = 119, length = 8)
    private String RetiredDate; //Udgået dato

    @FixedWidthField(start = 127, length = 1)
    private String AipComputationCode; //Beregningskode AIT -> reg.pris
    @FixedWidthField(start = 128, length = 1)
    private String PackageAcceptedForSubsidy; //Pakning optaget i tilskudsgruppe
    @FixedWidthField(start = 129, length = 1)
    private String ProductionFee; //Færdigfremstillingsgebyr
    @FixedWidthField(start = 130, length = 1)
    private String SafetyFeatures;
    @FixedWidthField(start = 131, length = 6)
    private String PackageDistributor; //Pakningsdistributør
}
