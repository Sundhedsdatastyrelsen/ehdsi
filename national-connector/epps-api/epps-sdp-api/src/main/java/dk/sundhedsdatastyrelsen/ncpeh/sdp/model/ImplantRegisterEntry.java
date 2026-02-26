package dk.sundhedsdatastyrelsen.ncpeh.sdp.model;

import java.time.OffsetDateTime;

//Total rows: 2943536
public record ImplantRegisterEntry(
    String cpr, // Patient ID
    String regionCode, //Regionskode
    String reportingSor, //SOR code for reporting entity (Sundhedsvæsenets Organisationsregistor)
    String reportingShak, //SHAK code for reporting entity (likely to be null if not from hospital) (Sygehus-afdelingsklassifikationer)
    String ownerType, //Possible values: region, privat, Ukendt
    OffsetDateTime operationDate,
    String operationSksCode, //Operation SKS code, lookup in https://medinfo.dk/sks/brows.php, This field contains a free text mix of codes and text, like: "KHAD60 - Udskiftning af brystprotese + KHAD50 - Fjernelse af brystprotese + KWAB01 - Implantat fjernet + KWAA01 - Implantat indsat" or "KWAA01 - Implantat indsat + KNFB20 - Primær indsættelse af ucementeret totalprotese i hofteled"
    String status, //Values: Empty/Null, "Ændret Registrering", "Slettet registrering", "Implantat indsat", "Ingen registrering", "Implantat udtaget"
    Integer numberOfImplants,
    Boolean rightSideImplantet, //This and the 2 following are exclusive. Some exists where all are false, but none where more than 1 is true
    Boolean leftSideImplantet,
    Boolean doubleSidedImplant,
    String productNumber, //Sometimes null/empty, or just "." or "-". Sometimes actual product numbers (that includes symbols)
    String batchNumber, //Same rules as above
    String serialNumber,
    String registryStandard, //Contains free text description of how it was registered. Sometimes references to standards like GS1, or DS1, but numerous spelling errors denote that this is not a coded field.
    OffsetDateTime registryTime, //TODO: Clarify, registration of the data or the implant
    OffsetDateTime implantProductionTime,
    OffsetDateTime expiryTime, //Expiry for the implant, not the data
    Boolean error //TODO: Clarify: What is this?
) {

}
