package dk.sundhedsdatastyrelsen.epportal.prescriptions

import java.time.LocalDateTime

/**
 * Document metadata for a prescription. May be L1 or L3.
 */
data class PrescriptionMetadata(
    val registryObjectId: String,
    val documentUniqueId: String,
    val repositoryUniqueId: String?,
    val homeCommunityId: String?,
    val formatCode: String?,
    val title: String?,
    /** The description actually holds the name of the prescribed medicine. */
    val description: String?,
    val authorName: String?,
    /** Date of creation of the prescription (`creationDate`), in UTC. */
    val effectiveTime: LocalDateTime?,
    val atcCode: String?,
    val atcName: String?,
    val doseFormCode: String?,
    val doseFormName: String?,
    val strength: String?,
    val substitution: String?,
    val dispensable: Boolean?,
)

data class PrescriptionListItem(
    val l3: PrescriptionMetadata,
    val l1: PrescriptionMetadata,
)
