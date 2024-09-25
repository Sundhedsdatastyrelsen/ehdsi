package dk.nsp.epps.mock.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DocumentTrait extends PatientTrait {

    private String homeCommunityId;

    private String documentIdentifier;

    private String repositoryId;

    private String classCode;

    private boolean nextOfKin;

    private String patientDemographics;

    private ClinicalDocumentContent clinicalDocumentContent;

    public DocumentTrait() {
        // Empty default constructor.
    }

    public String getHomeCommunityId() {
        return homeCommunityId;
    }

    public void setHomeCommunityId(String homeCommunityId) {
        this.homeCommunityId = homeCommunityId;
    }

    public String getDocumentIdentifier() {
        return documentIdentifier;
    }

    public void setDocumentIdentifier(String documentIdentifier) {
        this.documentIdentifier = documentIdentifier;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public boolean isNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(boolean nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getPatientDemographics() {
        return patientDemographics;
    }

    public void setPatientDemographics(String patientDemographics) {
        this.patientDemographics = patientDemographics;
    }

    public ClinicalDocumentContent getClinicalDocumentContent() {
        return clinicalDocumentContent;
    }

    public void setClinicalDocumentContent(ClinicalDocumentContent clinicalDocumentContent) {
        this.clinicalDocumentContent = clinicalDocumentContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("homeCommunityId", homeCommunityId)
                .append("documentIdentifier", documentIdentifier)
                .append("repositoryId", repositoryId)
                .append("nextOfKin", nextOfKin)
                .append("classCode", classCode)
                .toString();
    }
}
