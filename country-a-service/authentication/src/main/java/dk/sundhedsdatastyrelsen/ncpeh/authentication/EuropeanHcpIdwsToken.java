package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.w3c.dom.Element;

import java.time.Instant;

public record EuropeanHcpIdwsToken(
    Element assertion,
    String audience,
    Instant created,
    Instant expires
) {}
