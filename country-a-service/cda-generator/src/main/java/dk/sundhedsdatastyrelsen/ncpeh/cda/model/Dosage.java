package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

import lombok.Value;

public sealed interface Dosage permits Dosage.Normal, Dosage.Split {

    /**
     * Normal Dosing, template id 1.3.6.1.4.1.19376.1.5.3.1.4.7.1
     * <p/>
     * This is used in the case where one dose quantity is sufficient.
     */
    sealed interface Normal extends Dosage permits Dosage.Normal.IntervalBased, Dosage.Normal.Tabular, Dosage.Normal.EventBased {
        /**
         * An interval based dosage, e.g. "2 pills 3 times per day".
         */
        @Value
        class IntervalBased implements Dosage.Normal {}

        /**
         * A tabular dosage, e.g. "1 dose at: 2027-01-01T09:00:00Z and 2027-01-03T17:00:00".
         */
        @Value
        class Tabular implements Dosage.Normal {}

        /**
         * Event based dosage, e.g. "2 pills after breakfast"
         */
        @Value
        class EventBased implements Dosage.Normal {}
    }

    /**
     * Split Dosing, template id 1.3.6.1.4.1.19376.1.5.3.1.4.9
     * <p/>
     * When a dosage has different quantities for different dosage times (e.g. "1 pill in the morning, 2 in the evening")
     * we need to use split dosing, i.e. have subordinate substanceAdministration elements.  This is because there is
     * at most one quantity element per substanceAdministration.
     */
    @Value
    class Split implements Dosage {
        // something to put in the parent SubstanceAdministration, and then
        // a list of things to put in subsequent entryRelationship>SubstanceAdministrations
    }
}
