package eu.europa.ec.sante.openncp.portal.model;

import eu.europa.ec.sante.openncp.core.client.PatientDemographics;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class PatientDetail {

    private String identifier;

    private String root;

    private String extension;

    private String firstName;

    private String familyName;

    private Date birthDate;

    private String gender;

    private String addressStreet;

    private String addressPostalCode;

    private String addressCity;

    private String addressCountry;

    private boolean nextOfKin;

    private PatientDemographics patientDemographics;

    public PatientDetail() {
        //  Empty Default Constructor
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public boolean isNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(boolean nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public PatientDemographics getPatientDemographics() {
        return patientDemographics;
    }

    public void setPatientDemographics(PatientDemographics patientDemographics) {
        this.patientDemographics = patientDemographics;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("identifier", identifier)
                .append("root", root)
                .append("extension", extension)
                .append("firstName", firstName)
                .append("familyName", familyName)
                .append("birthDate", birthDate)
                .append("gender", gender)
                .append("addressStreet", addressStreet)
                .append("addressPostalCode", addressPostalCode)
                .append("addressCity", addressCity)
                .append("addressCountry", addressCountry)
                .append("nextOfKin", nextOfKin)
                .toString();
    }
}
