package eu.europa.ec.sante.ehdsi.portal.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

public class NextOfKinTrait {

    private List<LivingSubjectId> livingSubjectIds;

    private String firstName;

    private String familyName;

    private String gender;

    private Date birthDate;

    private String addressStreet;

    private String addressCity;

    private String addressPostalCode;

    private String addressCountry;

    public NextOfKinTrait() {
    }

    public List<LivingSubjectId> getLivingSubjectIds() {
        return livingSubjectIds;
    }

    public void setLivingSubjectIds(List<LivingSubjectId> livingSubjectIds) {
        this.livingSubjectIds = livingSubjectIds;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("livingSubjectIds", livingSubjectIds)
                .append("firstName", firstName)
                .append("familyName", familyName)
                .append("gender", gender)
                .append("birthDate", birthDate)
                .append("addressStreet", addressStreet)
                .append("addressCity", addressCity)
                .append("addressPostalCode", addressPostalCode)
                .append("addressCountry", addressCountry)
                .toString();
    }
}
