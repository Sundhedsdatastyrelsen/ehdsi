package dk.sundhedsdatastyrelsen.ncpeh.sdp.model;

import java.time.OffsetDateTime;

//Total rows: 401789412
public record EncounterEntry(
    String ekEncounter, //Reference to an Encounter with the patient (Original title: DW_EK_Kontakt)
    String skEncounter, //Reference to an Encounter with the patient (Original title: DW_SK_Kontakt)
    String cpr, //(Original title: CPRnummer)
    OffsetDateTime start, //(Original title: Kont_starttidspunkt)
    OffsetDateTime end, //(Original title: Kont_sluttidspunkt)
    String typeCode, //(Original title: Kont_Type)
    String typeText, //(Original title: Kont_type_tekst)
    String priorityCode, //(Original title: Prioritet)
    String priorityText, //(Original title: Prioritet_tekst)
    String reasonCode, //(Original title: Kont_aarsag)
    String reasonText, //(Original title: Kont_aarsag_tekst)
    String diagnosisCode, //(Original title: Adiag)
    String diagnosisText, //(Original title: Adiag_tekst)
    String patientTypeCode, //(Original title: Kont_patient_type)
    String patientTypeText, //(Original title: Kont_patient_type_text)
    OffsetDateTime birthday, //(Original title: Borger_foedselsdato)
    String gender, //(Original title: Borger_koen) //Single Letter, M or F (or "Unknown" - 7 letters)
    String treatmentType //(Original title: Kont_beh_type)
    /* Totale Kolonner, udvalgte med
    [Lprindberetningssystem]
      ,[DW_EK_Kontakt]
      ,[DW_SK_Kontakt]
      ,[Kontakt_ID]
      ,[DW_EK_Forloeb]
      ,[DW_EK_Helbredsforloeb]
      ,[DW_SK_Sygehusophold]
      ,[DW_EK_Borger]
      ,[DW_SK_Borger]
      ,[CPRnummer]
      ,[Kont_starttidspunkt]
      ,[Kont_sluttidspunkt]
      ,[Beh_starttidspunkt]
      ,[Kont_henv_tidspunkt]
      ,[Kont_patient_type]
      ,[Kont_patient_type_tekst]
      ,[Kont_type]
      ,[Kont_type_tekst]
      ,[Prioritet]
      ,[Prioritet_tekst]
      ,[Kont_aarsag]
      ,[Kont_aarsag_tekst]
      ,[Adiag]
      ,[Adiag_tekst]
      ,[Kont_fritvalg]
      ,[Kont_fritvalg_tekst]
      ,[Kont_henv_aarsag]
      ,[Kont_henv_aarsag_tekst]
      ,[Kont_henv_maade]
      ,[Kont_henv_maade_tekst]
      ,[Kont_henv_instans]
      ,[Kont_henv_instans_tekst]
      ,[Kont_ans]
      ,[Kont_ans_tekst]
      ,[Kont_ans_hovedspec]
      ,[Kont_ans_hovedspec_shak]
      ,[Kont_ans_inst]
      ,[Kont_ans_inst_tekst]
      ,[Kont_ans_org_reg]
      ,[Kont_ans_org_reg_tekst]
      ,[Kont_ans_geo_reg]
      ,[Kont_ans_geo_reg_tekst]
      ,[Kont_inst_ejertype]
      ,[Kont_fir_kode]
      ,[Kont_fir_tekst]
      ,[Borger_bo_kom]
      ,[Borger_bo_kom_tekst]
      ,[Borger_bo_reg]
      ,[Borger_bo_reg_tekst]
      ,[Borger_bet_kom]
      ,[Borger_bet_kom_tekst]
      ,[Borger_bet_geo_reg]
      ,[Borger_bet_geo_reg_tekst]
      ,[Borger_foedselsdato]
      ,[Borger_alder_aar_ind]
      ,[Borger_alder_aar_ud]
      ,[Borger_koen]
      ,[Flag_kont_afsluttet]
      ,[Flag_proc_uden_kont]
      ,[Flag_ikke_regist_kont]
      ,[Flag_kont_finansiering_off]
      ,[Flag_kont_finansiering_prv]
      ,[Flag_kont_sgh_type_off]
      ,[Flag_kont_sgh_type_prv]
      ,[Flag_kont_sgh_type_hosp]
      ,[Kont_beh_type]
      ,[Kont_LPR_entity_ID]
      ,[Kont_indb_tidspunkt]
      ,[Borger_doedsdato]
      ,[DW_ID_Audit_insert]
      ,[DW_ID_Audit_update]
     */
) {
}
