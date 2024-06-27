package eu.europa.ec.sante.ehdsi.portal.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClinicalDocumentType {

    String classCode;
    String codeSystemId;
    String value;
    String scope;

    public ClinicalDocumentType() {
        // Empty default constructor.
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getCodeSystemId() {
        return codeSystemId;
    }

    public void setCodeSystemId(String codeSystemId) {
        this.codeSystemId = codeSystemId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("classCode", classCode)
                .append("codeSystemId", codeSystemId)
                .append("value", value)
                .append("scope", scope)
                .toString();
    }
}
