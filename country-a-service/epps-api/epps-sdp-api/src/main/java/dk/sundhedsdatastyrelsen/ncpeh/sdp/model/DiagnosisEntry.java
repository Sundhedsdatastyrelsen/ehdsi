package dk.sundhedsdatastyrelsen.ncpeh.sdp.model;

public record DiagnosisEntry(
    String ekEncounter, //Reference to an Encounter with the patient (Original title: DW_EK_Kontakt)
    String skEncounter, //Reference to an Encounter with the patient (Original title: DW_SK_Kontakt)
    String skDiagnosis, //Reference to a diagnosis (not primary key, multiple rows have the same Diagnosis), (Original title: DW_SK_Diagnose)
    String skDiagnosisParent, //Reference to a diagnosis that is a parent
    String code, //Diagnosis SKS-Code (https://medinfo.dk/sks/brows.php)
    String text, //Diagnosis text
    String typeCode, //Short code for data, like "A" has a value in TypeText of Aktionsdiagnose
    String typeText,
    String laterDisproven //Values: Nej, Ja, Empty/Null
) {

}
