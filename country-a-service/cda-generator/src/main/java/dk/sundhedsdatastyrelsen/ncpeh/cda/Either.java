package dk.sundhedsdatastyrelsen.ncpeh.cda;
import lombok.Getter;
import java.util.function.Function;
/// Models that you either one or the other.
public class Either<TLeft, TRight> {
 @Getter final TLeft left;
 @Getter final TRight right;
 final boolean isLeft;
  private Either(TLeft left, TRight right, boolean isLeft) {
 this.left = left;
 this.right = right;
 this.isLeft = isLeft;
 }
  public boolean isLeft() {
 return isLeft;
 }
  public boolean isRight() {
 return !isLeft;
 }
  /// Matches either side to produce a single output type. Could also be called `fold` or `reduce`.
 public <U> U match(Function<TLeft, U> leftMapper, Function<TRight, U> rightMapper) {
 return isLeft() ? leftMapper.apply(left) : rightMapper.apply(right);
 }
  /// Create a new Either which has the left side.
 public static <TLeft, TRight> Either<TLeft, TRight> ofLeft(TLeft left) {
 return new Either<>(left, null, true);
 }
  /// Create a new Either which has the right side.
 public static <TLeft, TRight> Either<TLeft, TRight> ofRight(TRight right) {
 return new Either<>(null, right, false);
 }
}
