package dk.vaccinationsregister.schemas._2013._12._01;
import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for HealthInsuranceImportFlagType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="HealthInsuranceImportFlagType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HealthInsuranceImportFlagType")
public class HealthInsuranceImportFlagType {
   /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final HealthInsuranceImportFlagType.Builder<_B> _other) {
 }
  public<_B >HealthInsuranceImportFlagType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new HealthInsuranceImportFlagType.Builder<_B>(_parentBuilder, this, true);
 }
  public HealthInsuranceImportFlagType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static HealthInsuranceImportFlagType.Builder<Void> builder() {
 return new HealthInsuranceImportFlagType.Builder<>(null, null, false);
 }
  public static<_B >HealthInsuranceImportFlagType.Builder<_B> copyOf(final HealthInsuranceImportFlagType _other) {
 final HealthInsuranceImportFlagType.Builder<_B> _newBuilder = new HealthInsuranceImportFlagType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder);
 return _newBuilder;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final HealthInsuranceImportFlagType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 }
  public<_B >HealthInsuranceImportFlagType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new HealthInsuranceImportFlagType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public HealthInsuranceImportFlagType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >HealthInsuranceImportFlagType.Builder<_B> copyOf(final HealthInsuranceImportFlagType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final HealthInsuranceImportFlagType.Builder<_B> _newBuilder = new HealthInsuranceImportFlagType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static HealthInsuranceImportFlagType.Builder<Void> copyExcept(final HealthInsuranceImportFlagType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static HealthInsuranceImportFlagType.Builder<Void> copyOnly(final HealthInsuranceImportFlagType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final HealthInsuranceImportFlagType _storedValue;
  public Builder(final _B _parentBuilder, final HealthInsuranceImportFlagType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final HealthInsuranceImportFlagType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public _B end() {
  return this._parentBuilder;
 }
  protected<_P extends HealthInsuranceImportFlagType >_P init(final _P _product) {
  return _product;
 }
  @Override
 public HealthInsuranceImportFlagType build() {
  if (_storedValue == null) {
   return this.init(new HealthInsuranceImportFlagType());
  } else {
   return ((HealthInsuranceImportFlagType) _storedValue);
  }
 }
  public HealthInsuranceImportFlagType.Builder<_B> copyOf(final HealthInsuranceImportFlagType _other) {
  _other.copyTo(this);
  return this;
 }
  public HealthInsuranceImportFlagType.Builder<_B> copyOf(final HealthInsuranceImportFlagType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends HealthInsuranceImportFlagType.Selector<HealthInsuranceImportFlagType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static HealthInsuranceImportFlagType.Select _root() {
  return new HealthInsuranceImportFlagType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
   public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  return products;
 }
  }
 }
