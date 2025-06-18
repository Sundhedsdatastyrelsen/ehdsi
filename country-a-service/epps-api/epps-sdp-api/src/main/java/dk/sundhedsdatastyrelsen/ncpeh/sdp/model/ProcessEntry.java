package dk.sundhedsdatastyrelsen.ncpeh.sdp.model;

import java.time.OffsetDateTime;

//Total rows: 196655688
public record ProcessEntry(
    String cpr, //CPRnummer
    OffsetDateTime start, //Forl_starttidspunkt
    OffsetDateTime end, //Forl_sluttidspunkt
    OffsetDateTime contactTime, //Forl_henv_tidspunkt
    String labelCode, //Forl_label
    String labelCodeText //Forl_label_tekst

    /*
    All fields, some are mapped
     [LPRindberetningssystem]
      ,[DW_EK_Forloeb]
      ,[DW_SK_Forloeb]
      ,[DW_EK_Forloeb_forrige]
      ,[DW_SK_Forloeb_forrige]
      ,[DW_EK_Helbredsforloeb]
      ,[DW_EK_Borger]
      ,[DW_SK_Borger_forl]
      ,[CPRnummer]
      ,[Helbredsforl_starttidspunkt]
      ,[Helbredsforl_sluttidspunkt]
      ,[Forl_starttidspunkt]
      ,[Forl_sluttidspunkt]
      ,[Forl_label]
      ,[Forl_label_tekst]
      ,[Forl_ref_type]
      ,[Forl_ref_type_tekst]
      ,[Forl_henv_tidspunkt]
      ,[Forl_fritvalg]
      ,[Forl_fritvalg_tekst]
      ,[Forl_henv_aarsag]
      ,[Forl_henv_aarsag_tekst]
      ,[Forl_henv_maade]
      ,[Forl_henv_maade_tekst]
      ,[Forl_henv_instans]
      ,[Forl_henv_instans_tekst]
      ,[Forl_afslut_maade]
      ,[Forl_afslut_maade_tekst]
      ,[Forl_ans]
      ,[Forl_ans_tekst]
      ,[Forl_ans_inst]
      ,[Forl_ans_inst_tekst]
      ,[Forl_ans_org_reg]
      ,[Forl_ans_org_reg_tekst]
      ,[Forl_ans_geo_reg]
      ,[Forl_ans_geo_reg_tekst]
      ,[Forl_inst_ejertype]
      ,[Forl_LPR_entity_ID]
      ,[Forl_indb_tidspunkt]
      ,[DW_ID_Audit_insert]
      ,[DW_ID_Audit_update]
     */
) {
}
