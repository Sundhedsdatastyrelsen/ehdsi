package dk.nsp.epps.mock.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class PortalRequest {

    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("countryCode", countryCode)
                .toString();
    }
}
