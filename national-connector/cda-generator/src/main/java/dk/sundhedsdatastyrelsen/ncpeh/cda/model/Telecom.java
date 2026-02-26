package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

/// This describes communication channels. It is implemented from the hl7:telecom element described at https://art-decor.ehdsi.eu/publication/epsos-html-20240422T073854/tmp-2.16.840.1.113883.3.1937.777.11.10.100-2024-04-10T122640.html.
@Value
@Builder
@With
public class Telecom {
    /// The "use" of the communication channel. For patients, this should probably be "MOBILE_CONTACT" or "PRIMARY_HOME",
    /// for professionals this should probably be "WORK_PLACE".
    @NonNull Use use;

    /// The value of the communication channel.
    ///
    /// Examples are "tel:+4572216800", "mailto:teste@teste.pt". So with what looks like HTML prefixes to anchor tags,
    /// with country code, no spaces etc.
    @NonNull String value;

    /// Possible values for "use" for communication channels.
    ///
    /// There are more options listed in a comment below, but I didn't add them because I don't think we will need them.
    public enum Use {
        MOBILE_CONTACT("MC"),
        PRIMARY_HOME("HP"),
        WORK_PLACE("WP");

        public final String value;

        Use(String value) {
            this.value = value;
        }
//         Possible values:
//
//            AS answering service
//            EC emergency contact
//            H home address
//            HP primary home
//            HV vacation home
//            MC mobile contact
//            PG pager
//            WP work place
//            TMP temporary address
//            PHYS physical visit address
//            PST postal address
    }
}
