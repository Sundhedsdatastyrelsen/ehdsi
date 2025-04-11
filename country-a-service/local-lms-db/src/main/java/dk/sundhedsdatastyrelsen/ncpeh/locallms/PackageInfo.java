package dk.sundhedsdatastyrelsen.ncpeh.locallms;

public record PackageInfo(
    String drugId,
    String dispensationRegulationCode,
    String packageFormCode
) {
}
