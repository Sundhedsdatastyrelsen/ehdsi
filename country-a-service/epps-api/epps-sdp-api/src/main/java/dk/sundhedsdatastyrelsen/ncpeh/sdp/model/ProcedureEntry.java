package dk.sundhedsdatastyrelsen.ncpeh.sdp.model;

import java.time.OffsetDateTime;

//Total rows: 1227867950
public record ProcedureEntry(
    String skProcedure, //Some kind of key, not primary key, as it is not distinct at all. 111587 distinct values (seems to be mostly integers) out of 1227867950 rows
    OffsetDateTime start, //(Proc_starttidspunkt)
    OffsetDateTime end, //(Proc_sluttidspunkt)
    String code, //Proc_kode
    String codeText, //Proc_kode_tekst
    String codeType, //Proc_kode_type
    String codeTypeText, //Proc_kode_type_tekst
    String cpr //Cprnummer //Maybe we can skip kontakt if this is on all views
    /* All fields, some are mapped
    [LPRindberetningssystem]
      ,[DW_EK_Procedureregistrering]
      ,[DW_SK_Procedureregistrering]
      ,[DW_SK_Procedure]
      ,[DW_EK_Kontakt]
      ,[DW_EK_Forloeb]
      ,[Proc_starttidspunkt]
      ,[Proc_sluttidspunkt]
      ,[Proc_kode]
      ,[Proc_kode_tekst]
      ,[Proc_kode_type]
      ,[Proc_kode_type_tekst]
      ,[Proc_parent_kode]
      ,[Proc_parent_kode_tekst]
      ,[Proc_parent_kode_type]
      ,[Proc_parent_kode_type_tekst]
      ,[Prod_enh]
      ,[Prod_enh_tekst]
      ,[Prod_inst]
      ,[Prod_inst_tekst]
      ,[DW_SK_Organisation_Producerende]
      ,[Prod_enh_geo_reg]
      ,[Prod_enh_geo_reg_tekst]
      ,[Prod_enh_org_reg]
      ,[Prod_enh_org_reg_tekst]
      ,[Prod_enh_ejertype]
      ,[Prod_enh_hovedspeciale_sor]
      ,[Prod_enh_hovedspeciale_shak]
      ,[Borger_foedselsdato]
      ,[Borger_koen]
      ,[Borger_koen_tekst]
      ,[Borger_bo_reg]
      ,[Borger_bo_reg_tekst]
      ,[Borger_bo_kom]
      ,[Borger_bo_kom_tekst]
      ,[Flag_proc_uden_kont]
      ,[Proc_LPR_entity_ID]
      ,[Proc_indb_tidspunkt]
      ,[DW_SK_Borger]
      ,[Cprnummer]
      ,[DW_SK_Sygehusophold]
      ,[DW_ID_Audit_insert]
      ,[DW_ID_Audit_update]
     */
) {
}
