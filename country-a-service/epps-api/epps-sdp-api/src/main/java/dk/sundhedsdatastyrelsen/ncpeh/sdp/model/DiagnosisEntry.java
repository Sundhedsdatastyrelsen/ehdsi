package dk.sundhedsdatastyrelsen.ncpeh.sdp.model;

public record DiagnosisEntry(
    String EkEncounter, //Reference to an Encounter with the patient (Original title: DW_EK_Kontakt)
    String SkEncounter, //Reference to an Encounter with the patient (Original title: DW_SK_Kontakt)
    String SkDiagnosis, //Reference to a diagnosis (not primary key, multiple rows have the same Diagnosis), (Original title: DW_SK_Diagnose)
    String SkDiagnosisParent, //Reference to a diagnosis that is a parent
    String Code, //Diagnosis SKS-Code (https://medinfo.dk/sks/brows.php)
    String Text, //Diagnosis text
    String TypeCode, //Short code for data, like "A" has a value in TypeText of Aktionsdiagnose
    String TypeText,
    String LaterDisproven, //Values: Nej, Ja, Empty/Null
    
) {

}
