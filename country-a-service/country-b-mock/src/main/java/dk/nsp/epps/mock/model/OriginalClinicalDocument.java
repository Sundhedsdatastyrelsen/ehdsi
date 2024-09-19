package dk.nsp.epps.mock.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OriginalClinicalDocument extends ClinicalDocument {

    public OriginalClinicalDocument() {}

    private String classCode;
    private BigInteger size;
    private List<Author> authors =  new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date eventDate;

    private ReasonOfHospitalisation reasonOfHospitalisation;

    public String getClassCode() { return classCode; }

    public void setClassCode(String classCode) { this.classCode = classCode;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public ReasonOfHospitalisation getReasonOfHospitalisation() {
        return reasonOfHospitalisation;
    }

    public void setReasonOfHospitalisation(ReasonOfHospitalisation reasonOfHospitalisation) {
        this.reasonOfHospitalisation = reasonOfHospitalisation;
    }
}
