package dk.sundhedsdatastyrelsen.ncpeh;

import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlNamespace;
import dk.sundhedsdatastyrelsen.ncpeh.base.utils.XmlUtils;
import dk.sundhedsdatastyrelsen.ncpeh.client.NspClientDgws;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Fmk;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.Sosi;
import dk.sundhedsdatastyrelsen.ncpeh.testing.shared.TestIdentities;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RegressionIT {
    @Test
    void verifyFmkLogsToMinLog() {
        // This test is here to ensure that FMK continues logging to minlog.
        // See https://jira.nspop.dk/browse/NCPEH-172 for a time when it didn't, and we didn't notice it.
        // Since there is a delay from the dispensation to the logging, and we don't control how long that is,
        // the approach is to check whether there are logs from yesterday's nightly test.

        // Make the request
        var ns = new XmlNamespace("min", "http://www.sundhedsdatastyrelsen.dk/minlog/xml.schema/2025/03/12/minlog2-lookup.xsd");
        // We don't need to look up minlog in any other places, so we just create the request here directly.

//        <min:LogStatementOnBehalfOfRequest xmlns:min="http://www.sundhedsdatastyrelsen.dk/minlog/xml.schema/2025/03/12/minlog2-lookup.xsd">
//            <min:OnBehalfOfPersonId source="CPR">2106004032</min:OnBehalfOfPersonId>
//            <min:Grouping>None</min:Grouping>
//            <min:Details>All</min:Details>
//            <min:Chronologic>true</min:Chronologic>
//            <min:FromDateTime>2026-07-17T14:38:23.613+02:00</min:FromDateTime>
//            <min:ToDateTime>2026-07-20T14:38:23.619+02:00</min:ToDateTime>
//          </min:LogStatementOnBehalfOfRequest>

        // Så vidt jeg kan gennemskue kan vi ikke bruge LogStatementOnBehalfOf, fordi det kun returnerer log statements
        // sundhedspersonen selv har stået for. Ikke 100% sikker, men tror det.
        // Så vi skal bruge den anden.

        var doc = XmlUtils.newDocument();
        var request = XmlUtils.appendChild(doc, ns, "LogStatementForCPRPersonRequest");
        var pid = XmlUtils.appendChild(request, ns, "PersonId", Fmk.cprKarl);
        pid.setAttribute("source", "CPR");
        XmlUtils.appendChild(request, ns, "Grouping", "None");
        XmlUtils.appendChild(request, ns, "Details", "All");
        XmlUtils.appendChild(request, ns, "Chronologic", "true");
        XmlUtils.appendChild(request, ns, "FromDateTime", "2026-07-17T14:38:23.613+02:00");
        XmlUtils.appendChild(request, ns, "ToDateTime", "2026-07-20T14:38:23.613+02:00");

        // Send the request
        var response = NspClientDgws.request(
            URI.create("https://test2-cnsp.ekstern-test.nspop.dk:8443/minlog2-lookupid/20250312/LookupidService"),
            request,
            "GetLogStatementsForCPRPersonWithID_20250312",
            Sosi.authenticationService.nspDgwsIdentityToAssertion(TestIdentities.lægeCharlesBabbage));

        // Check whether the foreign minlog entry shows up
        assertThat(response, is(not(nullValue())));
    }
}
