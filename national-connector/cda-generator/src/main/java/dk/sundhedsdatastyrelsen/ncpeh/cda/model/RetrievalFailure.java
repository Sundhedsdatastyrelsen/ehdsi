package dk.sundhedsdatastyrelsen.ncpeh.cda.model;

// An enum is sufficient because we only need to indicate retrieval failure,
// not store additional error details. For now. If needed in the future this can be converted into data structure to preserve
// details about the failure.
public enum RetrievalFailure {
    UNAVAILABLE
}
