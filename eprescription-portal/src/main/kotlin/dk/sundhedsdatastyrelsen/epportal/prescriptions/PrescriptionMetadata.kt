package dk.sundhedsdatastyrelsen.epportal.prescriptions

import java.time.LocalDateTime

/**
 * One XDS `ExtrinsicObject` as a prescription.
 */
data class PrescriptionMetadata(
    /** Used for associations. */
    val registryObjectId: String,
    /** The document's unique ID, used for retrieval. */
    val documentUniqueId: String,
    /** The XDS repository holding the document (`repositoryUniqueId` slot). */
    val repositoryUniqueId: String?,
    /** The `home` community id attribute on the `ExtrinsicObject`, if present. */
    val homeCommunityId: String?,
    /** The raw `formatCode` classification value, e.g. `urn:epsos:ep:pre:2010`. */
    val formatCode: String?,
    /** Title of the prescription document (`Name/LocalizedString`). */
    val title: String?,
    /** Description of the prescription document (`Description/LocalizedString`). */
    val description: String?,
    /** Name of the prescribing author (`authorPerson` slot on an author `Classification`). */
    val authorName: String?,
    /** Date of creation of the prescription, with time if given. */
    val effectiveTime: LocalDateTime?,
    /** ATC code, if present. */
    val atcCode: String?,
    /** Display name for the ATC code. */
    val atcName: String?,
    /** Dose form code (EDQM), if present. */
    val doseFormCode: String?,
    /** Display name for the dose form. */
    val doseFormName: String?,
    /** Strength of the medication, as a display string (e.g. "10 mg"). */
    val strength: String?,
    /**
     * The workflow status event code (e.g. `urn:ihe:iti:xdw:2011:eventCode:open` /
     * `:closed`), if present.
     * This might mean "dispensable", not sure yet.
     */
    val workflowStatus: String?,
)

/**
 * An ePrescription with L1 and L3. We assume we always get both.
 */
data class PrescriptionListItem(
    /** The structured XML (L3) rendition of this prescription. */
    val l3: PrescriptionMetadata?,
    /** The unstructured PDF (L1) rendition of this prescription. */
    val l1: PrescriptionMetadata?,
)
