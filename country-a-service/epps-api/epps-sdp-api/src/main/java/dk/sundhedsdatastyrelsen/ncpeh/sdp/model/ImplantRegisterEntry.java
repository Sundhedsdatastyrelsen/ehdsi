package dk.sundhedsdatastyrelsen.ncpeh.sdp.model;

import java.time.OffsetDateTime;

public record ImplantRegisterEntry(
    String cpr, // Patient ID
    String RegionCode, //Regionskode
    String ReportingSor, //SOR code for reporting entity (Sundhedsvæsenets Organisationsregistor)
    String ReportingShak, //SHAK code for reporting entity (likely to be null if not from hospital) (Sygehus-afdelingsklassifikationer)
    String OwnerType, //Possible values: region, privat, Ukendt
    OffsetDateTime OperationDate,
    String OperationSksCode, //Operation SKS code, lookup in https://medinfo.dk/sks/brows.php, This field contains a free text mix of codes and text, like: "KHAD60 - Udskiftning af brystprotese + KHAD50 - Fjernelse af brystprotese + KWAB01 - Implantat fjernet + KWAA01 - Implantat indsat" or "KWAA01 - Implantat indsat + KNFB20 - Primær indsættelse af ucementeret totalprotese i hofteled"
    String Status, //Values: Empty/Null, "Ændret Registrering", "Slettet registrering", "Implantat indsat", "Ingen registrering", "Implantat udtaget"
    Integer NumberOfImplants,
    Boolean RightSideImplantet, //This and the 2 following are exclusive. Some exists where all are false, but none where more than 1 is true
    Boolean LeftSideImplantet,
    Boolean DoubleSidedImplant,
    String ProductNumber, //Sometimes null/empty, or just "." or "-". Sometimes actual product numbers (that includes symbols)
    String BatchNumber, //Same rules as above
    String SerialNumber,
    String RegistryStandard, //Contains free text description of how it was registered. Sometimes references to standards like GS1, or DS1, but numerous spelling errors denote that this is not a coded field.
    OffsetDateTime RegistryTime,
    OffsetDateTime ProductionTime,
    OffsetDateTime ExpiryTime, //Expiry for the implant, not the data
    Boolean Error
) {

}
