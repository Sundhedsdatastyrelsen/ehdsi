package eu.europa.ec.sante.openncp.portal.model;

public class SearchMaskResponse {

    private String countryCode;
    private String form;

    public SearchMaskResponse() {
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
