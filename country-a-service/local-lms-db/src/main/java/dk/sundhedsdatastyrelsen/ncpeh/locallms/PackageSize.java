package dk.sundhedsdatastyrelsen.ncpeh.locallms;

import java.math.BigDecimal;

/// The numerical data about package size, currently only used in tests to validate our assumptions.
public record PackageSize(String packageNumber, BigDecimal numericalPackageSize, Integer numberOfSubPackages) {
}
