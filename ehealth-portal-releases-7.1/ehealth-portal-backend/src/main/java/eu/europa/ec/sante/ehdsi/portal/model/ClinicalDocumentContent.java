package eu.europa.ec.sante.ehdsi.portal.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClinicalDocumentContent extends ClinicalDocument {

    private byte[] content;

    private boolean nextOfKin;

    public ClinicalDocumentContent() {
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public boolean isNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(boolean nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("content", content)
                .append("nextOfKin", nextOfKin)
                .toString();
    }
}
