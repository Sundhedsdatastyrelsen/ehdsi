package eu.europa.ec.sante.ehdsi.portal.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class FilterParameters {

    private Date createdBefore;
    private Date createdAfter;
    private Long maximumSize;

    public Date getCreatedBefore() {
        return createdBefore;
    }

    public void setCreatedBefore(Date createdBefore) {
        this.createdBefore = createdBefore;
    }

    public Date getCreatedAfter() {
        return createdAfter;
    }

    public void setCreatedAfter(Date createdAfter) {
        this.createdAfter = createdAfter;
    }

    public Long getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Long maximumSize) {
        this.maximumSize = maximumSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("createdBefore", createdBefore)
                .append("createdAfter", createdAfter)
                .append("maximumSize", maximumSize)
                .toString();
    }

}
