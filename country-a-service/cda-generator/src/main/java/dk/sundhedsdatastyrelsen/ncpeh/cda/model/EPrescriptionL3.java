package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class EPrescriptionL3 {
    /**
     * Unique ID identifying the CDA document (not the prescription itself).
     */
    @NonNull CdaId documentId;
    @NonNull String title;
    @NonNull CdaId prescriptionId;

    /**
     * "[...] the date and time at which this document was created as an electronic document."
     */
    @NonNull OffsetDateTime effectiveTime;

    /**
     * The.. (this).. <effectiveTime> element encodes the start and stop time of the medication regimen or the length of the medication regimen. This an interval of time (xsi:type='IVL_TS'), and must be specified as shown. This is an additional constraint placed upon CDA Release 2.0 by this profile, and simplifies the exchange of start/stop/length and frequency information between EMR systems. If no information is available for the dosage period, a nullFlavor attribute has to be provided with the value 'UNK'.
     *
     * Case 1: specified interval
     * The <low> and <high> values of the first <effectiveTime> element represent the start and stop times for the medication. The <low> value represents the start time, and the <high> value represents the stop time.
     *
     * In case of unbounded period (continuous therapy) the <high> element will be valued with the nullFlavor attribute to NA.
     *
     * The <high> value records the end of the medication regime according to the information provided in the prescription or order. For example, if the prescription is for enough medication to last 30 days, then the high value should contain a date that is 30 days later than the <low> value. The rationale is that a provider, seeing an un-refilled prescription would normally assume that the medication is no longer being taken, even if the intent of the treatment plan is to continue the medication indefinitely.
     *
     * Case 2: 'floating' period
     * If the start and/or stop time is unknown, but the length of the medication regimen is known, it shall be indicated in the <width> value.
     */

    OffsetDateTime medicationStartTime;
    OffsetDateTime medicationEndTime;

    /**
     * The element specifies the route of administration using the EDQM route of administration vocabulary. A code must
     * be specified if the route is known, and the displayName attribute should be specified. If the route is unknown,
     * this element shall not be sent.
     */
    CdaCode administrationRoute;

    @NonNull Author author;

    @NonNull String indicationText;

    @NonNull Patient patient;
    /**
     * "Time of signing the document"
     * <a href="https://art-decor.ehdsi.eu/publication/epSOS/epsos-html-20240126T203601/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.1.1-2022-09-12T133927.html">
     * ART-DECOR</a>
     */
    @NonNull OffsetDateTime signatureTime;
    @NonNull CdaId parentDocumentId;
    @NonNull String entryText;
    @NonNull Product product;
    @NonNull Long packageQuantity;
    @NonNull Boolean substitutionAllowed;

    public String getEffectiveTime() {
        return Utils.cdaDateTime(effectiveTime);
    }
    public OffsetDateTime getEffectiveTimeOffsetDateTime() {
        return effectiveTime;
    }
    public String getSignatureTime() {
        return Utils.cdaDateTime(signatureTime);
    }
    public String getPackageQuantity() {
        return Long.toString(packageQuantity);
    }
    public String getMedicationStartTime()
    {
        if (medicationStartTime != null) {
            return Utils.cdaDate(medicationStartTime);
        }
        return null;
    }

    public String getMedicationEndTime()
    {
        if (medicationEndTime != null) {
            return Utils.cdaDate(medicationEndTime);
        }
        return null;
    }

    public long getPackageQuantityLong() {
        return packageQuantity;
    }
}
