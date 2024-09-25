package dk.nsp.epps.mock.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DiscardRequest extends DocumentTrait {

    private String dispenseId;

    private String dispenseName;

    private boolean nextOfKin;

    public DiscardRequest() {
        // Empty default constructor.
    }

    public String getDispenseId() {
        return dispenseId;
    }

    public void setDispenseId(String dispenseId) {
        this.dispenseId = dispenseId;
    }

    public String getDispenseName() {
        return dispenseName;
    }

    public void setDispenseName(String dispenseName) {
        this.dispenseName = dispenseName;
    }

    public boolean isNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(boolean nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dispenseId", dispenseId)
                .append("dispenseName", dispenseName)
                .append("nextOfKin", nextOfKin)
                .toString();
    }
}
