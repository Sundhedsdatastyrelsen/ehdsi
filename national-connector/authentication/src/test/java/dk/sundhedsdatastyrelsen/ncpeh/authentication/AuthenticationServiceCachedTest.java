package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import com.github.benmanes.caffeine.cache.Ticker;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.test.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AuthenticationServiceCachedTest {
    final Clock defaultClock = Clock.systemUTC();

    @Test
    void cacheReuses() throws Exception {
        var cachedService = new AuthenticationServiceCached(
            new AuthServiceStub(defaultClock, 120),
            "issuer",
            defaultClock,
            Ticker.systemTicker());
        var token1 = cachedService.xcaSoapHeaderToIdwsToken(miniSoapHeader, "audience");
        var token2 = cachedService.xcaSoapHeaderToIdwsToken(miniSoapHeader, "audience");
        assertThat(token1, is(sameInstance(token2)));
    }

    @Test
    void cacheMissCreatesNewInstance() throws Exception {
        var cachedService = new AuthenticationServiceCached(
            new AuthServiceStub(defaultClock, 120),
            "issuer",
            defaultClock,
            Ticker.systemTicker());
        var token1 = cachedService.xcaSoapHeaderToIdwsToken(miniSoapHeader, "audience");
        var token2 = cachedService.xcaSoapHeaderToIdwsToken(miniSoapHeader, "another audience");
        assertThat(token1, is(not(sameInstance(token2))));
    }

    @Test
    void cacheWorksForRecordedSoapHeader() throws Exception {
        var soapHeader = TestUtils.slurp(TestUtils.resource("openncp_soap_header.xml"));
        var cachedService = new AuthenticationServiceCached(
            new AuthServiceStub(defaultClock, 120),
            "issuer",
            defaultClock,
            Ticker.systemTicker());
        var token1 = cachedService.xcaSoapHeaderToIdwsToken(soapHeader, "audience");
        var token2 = cachedService.xcaSoapHeaderToIdwsToken(soapHeader, "audience");
        assertThat(token1, is(sameInstance(token2)));
    }

    @Test
    void comparingRawSamlAttributesWorks() throws Exception {
        var headerWithRawAttribute = """
            <soap:Header xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
                <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                     <saml:Assertion xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion">
                         <saml:Issuer NameQualifier="urn:ehdsi:assertions:hcp"></saml:Issuer>
                         <ds:X509Certificate>
                            MIIEKTCCAxGgAwIBAgICAMUwDQYJKoZIhvcNAQENBQAwUTELMAkGA1UEBhMCQkUxHDAaBgNVBAoME0V1cm9wZWFuIENvbW1pc3Npb24xETAPBgNVBAMMCEVIRFNJIENBMREwDwYDVQQLDAhERyBTYW50ZTAeFw0yNDEwMDcxMzM2NTNaFw0zNDEwMDcxMzM2NTNaMF4xCzAJBgNVBAYTAkRLMSkwJwYDVQQKDCBUaGUgRGFuaXNoIEhlYWx0aCBEYXRhIEF1dGhvcml0eTEkMCIGA1UEAwwbR1JQOkVIRUFMVEhfTkNQX0JPT1RDQU1QX0RLMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtpXCcVejHR1AGvKA06LaxGWwyXRQw4U3W118l5RU+BNdOgD7nvC0ujvi05ESdnL7SKrGeVosh1qyyIBGbNCdZTYqGsYLkIkiRb0cP/YRCCfW/NuEcW22sdezVgPGbv1vgIEaINkDkXHM4s5yZEi55JyBuNGG19ghNfwxnhgqge54GdLhOPct5K+m06AgGuRTEU4mR/uBRdM4sue0WfOVkw4bF9JTeNoU5YI0qp1q8nSsuJAwsOGaNPd7Q0garSeT8WXl/q8Z5RijdnUcPnznvMbri+JhryVH9n33S6ejFQ06mQHswn1006ZJkoGXVtv7VCmVtzUZtO34p1w8HiVnRpQ/K907uJEMkY5kOxrzpuMv0hf7veKGMSDwyW1hgd9UHV/fk4rtKY5LfNueFWjpXNcvW1YpRZHSCa7T0a3HvrnIC2HaZszt8ALX5RtTmqH7nyAhHHUX2eY6bGseuM1+x+55n215DuK7rV6kMVd4taOQcbmeTmtwQp6Kc4oUXpYDUTpUu+xlzV9thDTqnl6cnwXuGHb1b9s6TvXv2ouiQ4RV91u+XY1+YLdEKHtAKVfekaWQU//vdeRqORfEpj4PbzoIQsEgW01/xLMzgw2BsMtiHE9+yk+v/ljzFxGDdgmryCu6ODX4Hol5jSXwtGHa5KDl9zZTwtSSZp2axxURinECAwEAATANBgkqhkiG9w0BAQ0FAAOCAQEAEPj3ChZY4indo6n6dUxqcZWmXizDYWHW1KCbac4SXMfpv4lOfUK/lbuuDxGZEIdYDf1A4kelBfky9sS5k1zsPnn8O6wHboLKbFiXZzOElfa0uSjY+IO3Fe73xvnW762xickZ8g5EdMhm+wUy5PWbUFMYruyiOAVScTCP90Kp9DCDYBuHUd6KzNF5V458QrKLb7uNruK0hoXrExHMjbAeM5hIFfDz477Y84Suh6XSNA1istQ8Stfw1H8hSIiFrRbPf+57IfLWQ/SYM/ghY4ZGIiEn1dXBpSeyjlAeU3sr4rxCm1zCDd8DniYcITTXrwND/3V/5FmtSUX4V4Oh7MXinA==
                         </ds:X509Certificate>
                         <saml:AttributeStatement>
                             <saml:Attribute>
                                 some attribute
                             </saml:Attribute>
                         </saml:AttributeStatement>
                     </saml:Assertion>
                     <saml:Assertion xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion">
                         <saml:Issuer NameQualifier="urn:ehdsi:assertions:trc"></saml:Issuer>
                     </saml:Assertion>
                </wsse:Security>
            </soap:Header>
            """;
        var headerWithAnotherRawAttribute = headerWithRawAttribute.replaceAll("some attribute", "another attribute");

        var cachedService = new AuthenticationServiceCached(
            new AuthServiceStub(defaultClock, 120),
            "issuer",
            defaultClock,
            Ticker.systemTicker());
        var token = cachedService.xcaSoapHeaderToIdwsToken(headerWithRawAttribute, "audience");
        assertThat(cachedService.xcaSoapHeaderToIdwsToken(headerWithRawAttribute, "audience"), is(sameInstance(token)));
        assertThat(cachedService.xcaSoapHeaderToIdwsToken(headerWithAnotherRawAttribute, "audience"), is(not(sameInstance(token))));
    }

    @Test
    void evicts30SecsBefore() throws Exception {
        var clock = Clock.fixed(Instant.parse("2022-06-11T11:12:13Z"), ZoneOffset.UTC);
        var ticker = new TickerStub();
        var cacheService = new AuthenticationServiceCached(
            new AuthServiceStub(clock, 120),
            "issuer",
            clock,
            ticker);
        var token = cacheService.xcaSoapHeaderToIdwsToken(miniSoapHeader, "audience");
        assertThat(cacheService.xcaSoapHeaderToIdwsToken(miniSoapHeader, "audience"), is(sameInstance(token)));
        ticker.advance(89);
        assertThat(cacheService.xcaSoapHeaderToIdwsToken(miniSoapHeader, "audience"), is(sameInstance(token)));
        ticker.advance(1);
        assertThat(cacheService.xcaSoapHeaderToIdwsToken(miniSoapHeader, "audience"), is(not(sameInstance(token))));
    }

    final String miniSoapHeader = """
        <soap:Header xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
            <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
                 <saml:Assertion xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion">
                     <saml:Issuer NameQualifier="urn:ehdsi:assertions:hcp"></saml:Issuer>
                     <ds:X509Certificate>
                        MIIEKTCCAxGgAwIBAgICAMUwDQYJKoZIhvcNAQENBQAwUTELMAkGA1UEBhMCQkUxHDAaBgNVBAoME0V1cm9wZWFuIENvbW1pc3Npb24xETAPBgNVBAMMCEVIRFNJIENBMREwDwYDVQQLDAhERyBTYW50ZTAeFw0yNDEwMDcxMzM2NTNaFw0zNDEwMDcxMzM2NTNaMF4xCzAJBgNVBAYTAkRLMSkwJwYDVQQKDCBUaGUgRGFuaXNoIEhlYWx0aCBEYXRhIEF1dGhvcml0eTEkMCIGA1UEAwwbR1JQOkVIRUFMVEhfTkNQX0JPT1RDQU1QX0RLMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtpXCcVejHR1AGvKA06LaxGWwyXRQw4U3W118l5RU+BNdOgD7nvC0ujvi05ESdnL7SKrGeVosh1qyyIBGbNCdZTYqGsYLkIkiRb0cP/YRCCfW/NuEcW22sdezVgPGbv1vgIEaINkDkXHM4s5yZEi55JyBuNGG19ghNfwxnhgqge54GdLhOPct5K+m06AgGuRTEU4mR/uBRdM4sue0WfOVkw4bF9JTeNoU5YI0qp1q8nSsuJAwsOGaNPd7Q0garSeT8WXl/q8Z5RijdnUcPnznvMbri+JhryVH9n33S6ejFQ06mQHswn1006ZJkoGXVtv7VCmVtzUZtO34p1w8HiVnRpQ/K907uJEMkY5kOxrzpuMv0hf7veKGMSDwyW1hgd9UHV/fk4rtKY5LfNueFWjpXNcvW1YpRZHSCa7T0a3HvrnIC2HaZszt8ALX5RtTmqH7nyAhHHUX2eY6bGseuM1+x+55n215DuK7rV6kMVd4taOQcbmeTmtwQp6Kc4oUXpYDUTpUu+xlzV9thDTqnl6cnwXuGHb1b9s6TvXv2ouiQ4RV91u+XY1+YLdEKHtAKVfekaWQU//vdeRqORfEpj4PbzoIQsEgW01/xLMzgw2BsMtiHE9+yk+v/ljzFxGDdgmryCu6ODX4Hol5jSXwtGHa5KDl9zZTwtSSZp2axxURinECAwEAATANBgkqhkiG9w0BAQ0FAAOCAQEAEPj3ChZY4indo6n6dUxqcZWmXizDYWHW1KCbac4SXMfpv4lOfUK/lbuuDxGZEIdYDf1A4kelBfky9sS5k1zsPnn8O6wHboLKbFiXZzOElfa0uSjY+IO3Fe73xvnW762xickZ8g5EdMhm+wUy5PWbUFMYruyiOAVScTCP90Kp9DCDYBuHUd6KzNF5V458QrKLb7uNruK0hoXrExHMjbAeM5hIFfDz477Y84Suh6XSNA1istQ8Stfw1H8hSIiFrRbPf+57IfLWQ/SYM/ghY4ZGIiEn1dXBpSeyjlAeU3sr4rxCm1zCDd8DniYcITTXrwND/3V/5FmtSUX4V4Oh7MXinA==
                     </ds:X509Certificate>
                 </saml:Assertion>
                 <saml:Assertion xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion">
                     <saml:Issuer NameQualifier="urn:ehdsi:assertions:trc"></saml:Issuer>
                 </saml:Assertion>
            </wsse:Security>
        </soap:Header>
        """;

    static class AuthServiceStub implements AuthenticationService {
        private final Clock clock;
        private final int expireAfter;

        public AuthServiceStub(Clock clock, int expireAfter) {
            this.clock = clock;
            this.expireAfter = expireAfter;
        }

        @Override
        public EuropeanHcpIdwsToken xcaSoapHeaderToIdwsToken(String soapHeader, String audience) throws AuthenticationException {
            return new EuropeanHcpIdwsToken(
                null,
                Double.toString(Math.random()),
                clock.instant(),
                clock.instant().plusSeconds(expireAfter));
        }

        @Override
        public DgwsAssertion nspDgwsIdentityToAssertion(NspDgwsIdentity identity) throws AuthenticationException {
            return new DgwsAssertion(
                XmlUtils.newDocument().createElement("abc" + Math.random()),
                clock.instant().plusSeconds(expireAfter));
        }
    }

    static class TickerStub implements Ticker {
        long value;

        @Override
        public long read() {
            // nanoseconds
            return value;
        }

        public void advance(long seconds) {
            this.value += (seconds * 1000000000);
        }
    }
}
