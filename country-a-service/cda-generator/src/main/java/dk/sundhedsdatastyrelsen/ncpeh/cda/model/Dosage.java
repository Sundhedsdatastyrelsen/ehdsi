package dk.sundhedsdatastyrelsen.ncpeh.cda.model;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Either;
import dk.sundhedsdatastyrelsen.ncpeh.cda.Utils;
import lombok.NonNull;
import lombok.Value;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Locale;
public sealed interface Dosage {
 DecimalFormat decimalFormat = new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.US));
  @NonNull
 String getTag();
  @NonNull
 String getUnstructuredText();
  Quantity getQuantity();
  @Value
 class Unstructured implements Dosage {
 String tag = "Unstructured";
 @NonNull String unstructuredText;
 /// Reason is for logging and testing purposes, so we can see why something became unstructured.
 String reason;
  public Quantity getQuantity() {
  return null;
 }
 }
  /**
 * An periodic interval based dosage, e.g. "2 pills 3 times per day".
 */
 @Value
 class PeriodicInterval implements Dosage {
 String tag = "PeriodicInterval";
 @NonNull String unstructuredText;
 /// Institution specified means that the timing is not precise. This is to distinguish between 'every 8 hours'
 /// and '3 times per day'. `true` = Not important/3 times per day. `false` = Important. Follow times precisely.
 ///
 /// In DK model, this is distinguished by whether there are "time" elements on the doses.
 boolean institutionSpecified;
 @NonNull Period period;
 Quantity quantity;
 }
  /// An event-interval based dosage, e.g. "1 pill after each meal" or "1 pill after dinner".
 ///
 /// This isn't very useful, because it's implicitly "once per day", you can't express things like
 /// "one after breakfast and one after dinner".
 @Value
 class EventInterval implements Dosage {
 String tag = "EventInterval";
 @NonNull String unstructuredText;
 @NonNull EventEnum event;
 Quantity quantity;
 }
  /// A dose taken only once
 @Value
 class Once implements Dosage {
 String tag = "Once";
 @NonNull String unstructuredText;
 @NonNull Either<LocalDate, ZonedDateTime> timeValue;
 Quantity quantity;
  public String getTimeValue() {
  // TODO unsure about the formatting of the ZonedDateTime value. In art-decor, it's specified as `CCYYMMDDHHMMSS`,
  //  but elsewhere we use the offset version.
  return timeValue.match(Utils::cdaDate, Utils::cdaTs);
 }
 }
  /// A dose that doesn't have a time element, but does have a quantity.
 @Value
 class Unbounded implements Dosage {
 String tag = "Unbounded";
 @NonNull String unstructuredText;
 Quantity quantity;
 }
  /**
 * Period of time used for dosage.
 * <p>
 * Can be used to describe things like "twice per day" (unit: "d", value: .5) or every 4 hours (unit: "h", value: 4).
 * <p>
 * In art-decor, called "Physical quantity" https://wiki.art-decor.org/index.php?title=DTr1_PQ, but part of Periodic
 * Interval of Timezones (https://wiki.art-decor.org/index.php?title=DTr1_PIVL_TS).
 */
 sealed interface Period {
 @NonNull
 String getTag();
  /// Simple period that only supports a fixed period of time between each dose.
 ///
 /// Other options would be including `<phase>` elements or `median` elements, see art-decor.
 @Value
 class Simple implements Period {
  String tag = "Simple";
    /**
  * Unit of time. It is not specified what values are allowed, but examples cite "h" and "d", so I think it's
  * intended to be from the UCUM codes seen here: https://ucum.org/ucum, as they are used elsewhere in cda. The
  * possibly interesting values from that set are "min" (minute), "h" (hour), "d" (day), "wk" (week), and "mo"
  * (month).
  */
  @NonNull String unit;
    /**
  * Can be fractional (eg 0.5, 0.25) or whole (eg 2, 3).
  */
  @NonNull BigDecimal value;
    public String getValue() {
   return decimalFormat.format(value);
  }
 }
 }
  /**
 * Quantity of dosage.
 * <p>
 * This can take one of several forms. A null flavor, simple with just a number (value), a number and a unit, min
 * and max with translations etc. We have the translations of the units elsewhere, so we do not need to code them
 * here. We start with the simplest and extend as needed.
 * <p>
 * Dose quantity is described <a href="https://art-decor.ehdsi.eu/publication/epsos-html-20240422T073854/tmp-1.3.6.1.4.1.12559.11.10.1.3.1.3.2-2023-07-03T135239.html">here</a>.
 */
 @Value
 class Quantity {
 @NonNull BigDecimal value;
 @NonNull Unit unit;
 /// If min value is set, treat value as max value.
 BigDecimal minValue;
  public String getValue() {
  return decimalFormat.format(value);
 }
  public String getMinValue() {
  return decimalFormat.format(minValue != null ? minValue : value);
 }
 }
  /// The Unit of a Dosage. Examples are "ml", "pust", "påsmøringer".
 ///
 /// The field is `<Dosage><UnitText(s)>` in the FMK data, and it is a free text field. So we will always need
 /// a 'translated' option, which just outputs the text. We could do some heuristics later to add things like
 /// 'ml' from the ehdsiQuantityUnit dataset.
 interface Unit {
 @NonNull
 String getTag();
  @Value
 class Translated implements Unit {
  @NonNull String tag = "Translated";
  @NonNull String translation;
 }
 }
  enum EventEnum {
 /// - before meal (from lat. ante cibus)
 AC("AC"),
 /// - before lunch (from lat. ante cibus diurnus)
 ACD("ACD"),
 /// - before breakfast (from lat. ante cibus matutinus)
 ACM("ACM"),
 /// - before dinner (from lat. ante cibus vespertinus)
 ACV("ACV"),
 /// - Prior to beginning a regular period of extended sleep (this would exclude naps). Note that this might occur at different times of day depending on a person's regular sleep schedule.
 HS("HS"),
 /// - between meals (from lat. inter cibus)
 IC("IC"),
 /// - between lunch and dinner
 ICD("ICD"),
 /// - between breakfast and lunch
 ICM("ICM"),
 /// - between dinner and the hour of sleep
 ICV("ICV"),
 /// - after meal (from lat. post cibus)
 PC("PC"),
 /// - after lunch (from lat. post cibus diurnus)
 PCD("PCD"),
 /// - after breakfast (from lat. post cibus matutinus)
 PCM("PCM"),
 /// - after dinner (from lat. post cibus vespertinus)
 PCV("PCV");
  private final String value;
  EventEnum(String v) {
  value = v;
 }
  public String value() {
  return value;
 }
  public static EventEnum fromValue(String v) {
  for (EventEnum ee : EventEnum.values()) {
   if (ee.value.equals(v)) {
     return ee;
   }
  }
  throw new IllegalArgumentException(v);
 }
 }
}
