package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.w3c.dom.Element;

import java.time.Instant;

/**
 * An IDWS XUA token from SOSI STS which can be used to gain access to a service within a short period of time.
 */
public record EuropeanHcpIdwsToken(
    Element assertion,
    String audience,
    Instant created,
    Instant expires,
    ///  MinLog requires a quite specific ID for the modificator. eHDSICountryOfTreatment:Subject/NameId.
    // TODO There might also be restrictions on how long this field can be, but that's later. 50 total, apparently.
    String authorMinLogId
) {}
