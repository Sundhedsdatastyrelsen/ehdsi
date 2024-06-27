package eu.europa.ec.sante.ehdsi.portal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispenseRequest extends DocumentTrait {

    private String prescriptionId;
    private String productName;
    private PackageSize packageSize;
    private List<PackageSize> partPackageSizes;
    private long numberOfPackage;
    private String countryCode;
    private boolean substitution;
    private String patientDemographics;
    private ClinicalDocumentContent clinicalDocumentContent;

    public DispenseRequest() {
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public PackageSize getPackageSize()  {
        return packageSize;
    }

    public void setPackageSize(PackageSize packageSize)  {
        this.packageSize=packageSize;
    }

    public List<PackageSize> getPartPackageSizes() {
        return partPackageSizes;
    }

    public void setPartPackageSizes(List<PackageSize> partPackageSizes) {
        this.partPackageSizes = partPackageSizes;
    }

    public long getNumberOfPackage() {
        return numberOfPackage;
    }

    public void setNumberOfPackage(long numberOfPackage) {
        this.numberOfPackage = numberOfPackage;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isSubstitution() {
        return substitution;
    }

    public void setSubstitution(boolean substitution) {
        this.substitution = substitution;
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
}
