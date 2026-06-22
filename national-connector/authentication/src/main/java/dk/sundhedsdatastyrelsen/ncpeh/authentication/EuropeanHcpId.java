package dk.sundhedsdatastyrelsen.ncpeh.authentication;

/// Identity information about a healthcare professional, for recording in MinLog.
/// The information is extractable from an IDWS token.
public interface EuropeanHcpId {
    /// The "XSPA Subject" attribute value from the HCP token – i.e., the full name of the HCP
    String subjectId();
    ///  Country of treatment, i.e., the country in which the healthcare professional operates
    String countryOfTreatment();
}
