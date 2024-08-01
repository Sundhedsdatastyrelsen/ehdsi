package eu.europa.ec.sante.openncp.portal.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class MedicationDispensed implements Serializable {

    private static final long serialVersionUID = 8235682380235702521L;
    private String dispensedId;
    private String document;
    private Date effectiveTime;

    public MedicationDispensed() {
    }

    public String getDispensedId() {
        return dispensedId;
    }

    public void setDispensedId(String dispensedId) {
        this.dispensedId = dispensedId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public static class DiscardResponse {

        private String status;

        public DiscardResponse() {

        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("status", status)
                    .toString();
        }
    }
}
