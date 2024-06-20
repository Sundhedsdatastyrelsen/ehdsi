package eu.europa.ec.sante.openncp.portal.model;

public class Concept {

    private String code;

    private String displayName;
    private String codeSystem;

    private String codeSystemName;

    public Concept() {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystemId(String codeSystem) {
        this.codeSystem = codeSystem;
    }

    public String getCodeSystemName() {
        return codeSystemName;
    }

    public void setCodeSystemName(String codeSystemName) {
        this.codeSystemName = codeSystemName;
    }
}
