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
* <p>Java class for DeleteVaccinationType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="DeleteVaccinationType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccinationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationIdentifierType"/>
*         <element name="VaccinationVersionIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationVersionIdentifierType"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteVaccinationType", propOrder = {
 "vaccinationIdentifier",
 "vaccinationVersionIdentifier"
})
public class DeleteVaccinationType {
  @XmlElement(name = "VaccinationIdentifier")
 protected long vaccinationIdentifier;
 @XmlElement(name = "VaccinationVersionIdentifier")
 protected long vaccinationVersionIdentifier;
  /**
 * Gets the value of the vaccinationIdentifier property.
 * 
 */
 public long getVaccinationIdentifier() {
 return vaccinationIdentifier;
 }
  /**
 * Sets the value of the vaccinationIdentifier property.
 * 
 */
 public void setVaccinationIdentifier(long value) {
 this.vaccinationIdentifier = value;
 }
  /**
 * Gets the value of the vaccinationVersionIdentifier property.
 * 
 */
 public long getVaccinationVersionIdentifier() {
 return vaccinationVersionIdentifier;
 }
  /**
 * Sets the value of the vaccinationVersionIdentifier property.
 * 
 */
 public void setVaccinationVersionIdentifier(long value) {
 this.vaccinationVersionIdentifier = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final DeleteVaccinationType.Builder<_B> _other) {
 _other.vaccinationIdentifier = this.vaccinationIdentifier;
 _other.vaccinationVersionIdentifier = this.vaccinationVersionIdentifier;
 }
  public<_B >DeleteVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new DeleteVaccinationType.Builder<_B>(_parentBuilder, this, true);
 }
  public DeleteVaccinationType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static DeleteVaccinationType.Builder<Void> builder() {
 return new DeleteVaccinationType.Builder<>(null, null, false);
 }
  public static<_B >DeleteVaccinationType.Builder<_B> copyOf(final DeleteVaccinationType _other) {
 final DeleteVaccinationType.Builder<_B> _newBuilder = new DeleteVaccinationType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final DeleteVaccinationType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationIdentifierPropertyTree!= null):((vaccinationIdentifierPropertyTree == null)||(!vaccinationIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationIdentifier = this.vaccinationIdentifier;
 }
 final PropertyTree vaccinationVersionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationVersionIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationVersionIdentifierPropertyTree!= null):((vaccinationVersionIdentifierPropertyTree == null)||(!vaccinationVersionIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationVersionIdentifier = this.vaccinationVersionIdentifier;
 }
 }
  public<_B >DeleteVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new DeleteVaccinationType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public DeleteVaccinationType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >DeleteVaccinationType.Builder<_B> copyOf(final DeleteVaccinationType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final DeleteVaccinationType.Builder<_B> _newBuilder = new DeleteVaccinationType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static DeleteVaccinationType.Builder<Void> copyExcept(final DeleteVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static DeleteVaccinationType.Builder<Void> copyOnly(final DeleteVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final DeleteVaccinationType _storedValue;
 private long vaccinationIdentifier;
 private long vaccinationVersionIdentifier;
  public Builder(final _B _parentBuilder, final DeleteVaccinationType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccinationIdentifier = _other.vaccinationIdentifier;
     this.vaccinationVersionIdentifier = _other.vaccinationVersionIdentifier;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final DeleteVaccinationType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationIdentifierPropertyTree!= null):((vaccinationIdentifierPropertyTree == null)||(!vaccinationIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationIdentifier = _other.vaccinationIdentifier;
     }
     final PropertyTree vaccinationVersionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationVersionIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationVersionIdentifierPropertyTree!= null):((vaccinationVersionIdentifierPropertyTree == null)||(!vaccinationVersionIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationVersionIdentifier = _other.vaccinationVersionIdentifier;
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
  protected<_P extends DeleteVaccinationType >_P init(final _P _product) {
  _product.vaccinationIdentifier = this.vaccinationIdentifier;
  _product.vaccinationVersionIdentifier = this.vaccinationVersionIdentifier;
  return _product;
 }
  /**
 * Sets the new value of "vaccinationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationIdentifier
 *     New value of the "vaccinationIdentifier" property.
 */
 public DeleteVaccinationType.Builder<_B> withVaccinationIdentifier(final long vaccinationIdentifier) {
  this.vaccinationIdentifier = vaccinationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccinationVersionIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationVersionIdentifier
 *     New value of the "vaccinationVersionIdentifier" property.
 */
 public DeleteVaccinationType.Builder<_B> withVaccinationVersionIdentifier(final long vaccinationVersionIdentifier) {
  this.vaccinationVersionIdentifier = vaccinationVersionIdentifier;
  return this;
 }
  @Override
 public DeleteVaccinationType build() {
  if (_storedValue == null) {
   return this.init(new DeleteVaccinationType());
  } else {
   return ((DeleteVaccinationType) _storedValue);
  }
 }
  public DeleteVaccinationType.Builder<_B> copyOf(final DeleteVaccinationType _other) {
  _other.copyTo(this);
  return this;
 }
  public DeleteVaccinationType.Builder<_B> copyOf(final DeleteVaccinationType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends DeleteVaccinationType.Selector<DeleteVaccinationType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static DeleteVaccinationType.Select _root() {
  return new DeleteVaccinationType.Select();
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
