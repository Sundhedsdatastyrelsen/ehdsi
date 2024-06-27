package eu.europa.ec.sante.ehdsi.portal.model;

public class EPDocumentDetail extends DocumentDetail {

    private String atcCode;
    private String atcText;
    private String doseFormCode;
    private String doseFormText;
    private String strength;
    private String substitution;
    private String description;
    private boolean dispensable;

    public EPDocumentDetail() {}

    public String getAtcCode() { return atcCode; }

    public void setAtcCode(String atcCode) { this.atcCode = atcCode; }

    public String getAtcText() { return atcText; }

    public void setAtcText(String atcText) { this.atcText = atcText; }

    public String getDoseFormCode() { return doseFormCode; }

    public void setDoseFormCode(String doseFormCode) { this.doseFormCode = doseFormCode; }

    public String getDoseFormText() { return doseFormText; }

    public void setDoseFormText(String doseFormText) { this.doseFormText = doseFormText; }

    public String getStrength() { return strength; }

    public void setStrength(String strength) { this.strength = strength; }

    public String getSubstitution() { return substitution; }

    public void setSubstitution(String substitution) { this.substitution = substitution; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public boolean isDispensable() { return dispensable; }

    public void setDispensable(boolean dispensable) { this.dispensable = dispensable; }
}