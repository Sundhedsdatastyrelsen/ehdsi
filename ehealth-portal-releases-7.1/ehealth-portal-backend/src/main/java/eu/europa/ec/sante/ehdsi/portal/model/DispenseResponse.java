package eu.europa.ec.sante.ehdsi.portal.model;

public class DispenseResponse {

    String dispenseId;
    String prescriptionId;
    String productName;
    String packageSize;
    String status;
    long numberOfPackage;
    boolean substitution;

    public DispenseResponse() {
    }

    public String getDispenseId() {
        return dispenseId;
    }

    public void setDispenseId(String dispenseId) {
        this.dispenseId = dispenseId;
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

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getNumberOfPackage() {
        return numberOfPackage;
    }

    public void setNumberOfPackage(long numberOfPackage) {
        this.numberOfPackage = numberOfPackage;
    }

    public boolean isSubstitution() {
        return substitution;
    }

    public void setSubstitution(boolean substitution) {
        this.substitution = substitution;
    }
}
