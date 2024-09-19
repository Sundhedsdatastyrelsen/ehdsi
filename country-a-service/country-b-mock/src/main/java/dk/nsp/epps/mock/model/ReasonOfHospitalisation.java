package dk.nsp.epps.mock.model;

public class ReasonOfHospitalisation {

    private String code;
    private String text;

    public ReasonOfHospitalisation(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

}
