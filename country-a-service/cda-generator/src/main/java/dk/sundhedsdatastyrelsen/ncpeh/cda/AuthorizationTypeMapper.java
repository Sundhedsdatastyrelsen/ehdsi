package dk.sundhedsdatastyrelsen.ncpeh.cda;

import dk.nsi.__.stamdata._3.AuthorizationType;
import dk.sundhedsdatastyrelsen.ncpeh.cda.model.CdaCode;

public class AuthorizationTypeMapper {
    private AuthorizationTypeMapper() {
    }

    /*
    Danish codes, taken from https://www.nspop.dk/display/public/web/Autorisation
    0	Erstatningsautorisation
4498	Optiker
5151	Fysioterapeut
5152	Social- og sundhedsassistent
5153	Ergoterapeut
5155	Fodterapeut
5158	Radiograf
5159	Bioanalytiker
5166	Sygeplejerske
5175	Jordemoder
5176	Kontaktlinseoptiker
5176	Optometrist
5265	Kiropraktor
5431	Tandplejer
5432	Klinisk tandtekniker
5433	Tandlæge
5451	Klinisk diætist
7170	Læge
9495	Bandagist
A511	Osteopat
B511	Behandlerfarmaceut
C511	Ambulancebehandler

ehdsi codes, taken from https://art-decor.ehdsi.eu/publication/epsos-html-20240422T073854/voc-1.3.6.1.4.1.12559.11.10.1.3.1.42.1-2020-04-22T155100.html

"Value Set eHDSIHealthcareProfessionalRole - 1.3.6.1.4.1.12559.11.10.1.3.1.42.1 2020-04-22T15:51:00";;;;;;;;
Level;Type;Code;DisplayName;CodeSystem;CodeSystemName;CodeSystemVersion;Flexibility;Exception
0;L;22;"Health professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;221;"Medical doctors";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2211;"Generalist medical practitioners";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2212;"Specialist medical practitioners";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;222;"Nursing and midwifery professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2221;"Nursing professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2222;"Midwifery professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;223;"Traditional and complementary medicine professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;224;"Paramedical practitioners";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;225;Veterinarians;2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;226;"Other health professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2261;Dentists;2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2262;Pharmacists;2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2263;"Environmental and occupational health and hygiene professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2264;Physiotherapists;2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2265;"Dieticians and nutritionists";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2266;"Audiologists and speech therapists";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2267;"Optometrists and ophthalmic opticians";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;2269;"Health professionals not elsewhere classified";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;32;"Health associate professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;321;"Medical and pharmaceutical technicians";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3211;"Medical imaging and therapeutic equipment technicians";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3212;"Medical and pathology laboratory technicians";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3213;"Pharmaceutical technicians and assistants";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3214;"Medical and dental prosthetic technicians";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;322;"Nursing and midwifery associate professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3221;"Nursing associate professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3222;"Midwifery associate professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;323;"Traditional and complementary medicine associate professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;325;"Other health associate professionals";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3251;"Dental assistants and therapists";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3252;"Medical records and health information technicians";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3253;"Community health workers";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3254;"Dispensing opticians";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3255;"Physiotherapy technicians and assistants";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3256;"Medical assistants";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3257;"Environmental and occupational health inspectors and associates";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3258;"Ambulance workers";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
0;L;3259;"Health associate professionals not elsewhere classified";2.16.840.1.113883.2.9.6.2.7;ISCO;;;false;
     */

    public static CdaCode mapAuthorizationType(AuthorizationType authorizationType) {
        var builder = CdaCode.builder().codeSystem(Oid.ISCO);

        if (authorizationType == null) {
            return builder.code("22").displayName("Health Professionals").build();
        }

        return (switch (authorizationType.getEducationCode()) {
            // Optiker
            case "4498" -> builder.code("2267").displayName("Optometrists and ophthalmic opticians");
            // Fysioterapeut
            case "5151" -> builder.code("2264").displayName("Physiotherapists");
            // Social og -sundhedsassistent
            case "5152" -> builder.code("2221").displayName("Nursing professionals");
            // Ergoterapeut
            case "5153" ->
                builder.code("2263").displayName("Environmental and occupational health and hygiene professionals");
            // Fodterapeut
            case "5155" -> builder.code("325").displayName("Other health associate professionals");
            // Radiograf
            case "5158" -> builder.code("3211").displayName("Medical imaging and therapeutic equipment technicians");
            // Bioanalytiker
            case "5159" -> builder.code("3212").displayName("Medical and pathology laboratory technicians");
            // Sygeplejerske
            case "5166" -> builder.code("2221").displayName("Nursing professionals");
            // Jordemoder
            case "5175" -> builder.code("2222").displayName("Midwifery professionals");
            // Kontaktlinseoptiker / Optometrist
            case "5176" -> builder.code("2267").displayName("Optometrists and ophthalmic opticians");
            // Kiropraktor
            case "5265" -> builder.code("325").displayName("Other health associate professionals");
            // Tandplejer
            case "5431" -> builder.code("3251").displayName("Dental assistants and therapists");
            // Klinisk tandtekniker
            case "5432" -> builder.code("3214").displayName("Medical and dental prosthetic technicians");
            // Tandlæge
            case "5433" -> builder.code("2261").displayName("Dentists");
            // Klinisk diætist
            case "5451" -> builder.code("2265").displayName("Dieticians and nutritionists");
            // Læge
            case "7170" -> builder.code("221").displayName("Medical doctors");
            // Bandagist
            case "9495" -> builder.code("3259").displayName("Health associate professionals not elsewhere classified");
            // Osteopat
            case "A511" -> builder.code("3259").displayName("Health associate professionals not elsewhere classified");
            // Behandlerfarmaceut
            case "B511" -> builder.code("3212").displayName("Pharmaceutical technicians and assistants");
            // Ambulancebehandler
            case "C511" -> builder.code("3258").displayName("Ambulance workers");

            default -> builder.code("22").displayName("Health Professionals");
        }).build();
    }
}
