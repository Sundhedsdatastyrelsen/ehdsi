package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Value;

import java.util.List;

@Value
public class PatientSummaryPdf {
    List<PdfField> fields;
}
