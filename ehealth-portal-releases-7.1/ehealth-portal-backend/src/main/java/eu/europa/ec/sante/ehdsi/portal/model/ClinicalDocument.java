package eu.europa.ec.sante.ehdsi.portal.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClinicalDocument {

    private String identifier;

    private String repositoryId;

    private String homeCommunityId;

    private String name;

    private String formatCode;

    private String mimeType;

    public ClinicalDocument() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getHomeCommunityId() {
        return homeCommunityId;
    }

    public void setHomeCommunityId(String homeCommunityId) {
        this.homeCommunityId = homeCommunityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFormatCode() { return formatCode; }

    public void setFormatCode(String formatCode) { this.formatCode = formatCode; }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identifier", identifier)
                .append("repositoryId", repositoryId)
                .append("homeCommunityId", homeCommunityId)
                .append("name", name)
                .append("mimeType", mimeType)
                .append("formatCode", formatCode)
                .toString();
    }
}
