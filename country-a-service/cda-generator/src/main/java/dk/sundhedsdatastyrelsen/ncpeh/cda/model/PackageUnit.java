package dk.sundhedsdatastyrelsen.ncpeh.cda.model;
import lombok.NonNull;
import lombok.Value;
public sealed interface PackageUnit {
 /**
 * Short class name because Freemarker cannot match on type.
 */
 String getTag();
  @Value
 class WithCode implements PackageUnit {
 String tag = "WithCode";
 @NonNull String code;
 }
  @Value
 class WithTranslation implements PackageUnit {
 String tag = "WithTranslation";
 @NonNull String translation;
 }
}
