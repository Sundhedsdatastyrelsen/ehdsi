package dk.vaccinationsregister.schemas._2013._12._01;
import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for PersonVaccinationPlanType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="PersonVaccinationPlanType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccinationPlanIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanIdentifierType"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonVaccinationPlanType", propOrder = {
 "vaccinationPlanIdentifier"
})
public class PersonVaccinationPlanType {
  @XmlElement(name = "VaccinationPlanIdentifier")
 protected long vaccinationPlanIdentifier;
  /**
 * Gets the value of the vaccinationPlanIdentifier property.
 * 
 */
 public long getVaccinationPlanIdentifier() {
 return vaccinationPlanIdentifier;
 }
  /**
 * Sets the value of the vaccinationPlanIdentifier property.
 * 
 */
 public void setVaccinationPlanIdentifier(long value) {
 this.vaccinationPlanIdentifier = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final PersonVaccinationPlanType.Builder<_B> _other) {
 _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 }
  public<_B >PersonVaccinationPlanType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new PersonVaccinationPlanType.Builder<_B>(_parentBuilder, this, true);
 }
  public PersonVaccinationPlanType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static PersonVaccinationPlanType.Builder<Void> builder() {
 return new PersonVaccinationPlanType.Builder<>(null, null, false);
 }
  public static<_B >PersonVaccinationPlanType.Builder<_B> copyOf(final PersonVaccinationPlanType _other) {
 final PersonVaccinationPlanType.Builder<_B> _newBuilder = new PersonVaccinationPlanType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final PersonVaccinationPlanType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 }
 }
  public<_B >PersonVaccinationPlanType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new PersonVaccinationPlanType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public PersonVaccinationPlanType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >PersonVaccinationPlanType.Builder<_B> copyOf(final PersonVaccinationPlanType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PersonVaccinationPlanType.Builder<_B> _newBuilder = new PersonVaccinationPlanType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static PersonVaccinationPlanType.Builder<Void> copyExcept(final PersonVaccinationPlanType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static PersonVaccinationPlanType.Builder<Void> copyOnly(final PersonVaccinationPlanType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final PersonVaccinationPlanType _storedValue;
 private long vaccinationPlanIdentifier;
  public Builder(final _B _parentBuilder, final PersonVaccinationPlanType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final PersonVaccinationPlanType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     }
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
  protected<_P extends PersonVaccinationPlanType >_P init(final _P _product) {
  _product.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
  return _product;
 }
  /**
 * Sets the new value of "vaccinationPlanIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanIdentifier
 *     New value of the "vaccinationPlanIdentifier" property.
 */
 public PersonVaccinationPlanType.Builder<_B> withVaccinationPlanIdentifier(final long vaccinationPlanIdentifier) {
  this.vaccinationPlanIdentifier = vaccinationPlanIdentifier;
  return this;
 }
  @Override
 public PersonVaccinationPlanType build() {
  if (_storedValue == null) {
   return this.init(new PersonVaccinationPlanType());
  } else {
   return ((PersonVaccinationPlanType) _storedValue);
  }
 }
  public PersonVaccinationPlanType.Builder<_B> copyOf(final PersonVaccinationPlanType _other) {
  _other.copyTo(this);
  return this;
 }
  public PersonVaccinationPlanType.Builder<_B> copyOf(final PersonVaccinationPlanType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends PersonVaccinationPlanType.Selector<PersonVaccinationPlanType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static PersonVaccinationPlanType.Select _root() {
  return new PersonVaccinationPlanType.Select();
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
