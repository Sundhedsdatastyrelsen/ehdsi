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
* <p>Java class for GetCallersPermissionsToPersonType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="GetCallersPermissionsToPersonType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PersonIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PersonIdentifierType"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCallersPermissionsToPersonType", propOrder = {
 "personIdentifier"
})
public class GetCallersPermissionsToPersonType {
  @XmlElement(name = "PersonIdentifier", required = true)
 protected String personIdentifier;
  /**
 * Gets the value of the personIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getPersonIdentifier() {
 return personIdentifier;
 }
  /**
 * Sets the value of the personIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setPersonIdentifier(String value) {
 this.personIdentifier = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final GetCallersPermissionsToPersonType.Builder<_B> _other) {
 _other.personIdentifier = this.personIdentifier;
 }
  public<_B >GetCallersPermissionsToPersonType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new GetCallersPermissionsToPersonType.Builder<_B>(_parentBuilder, this, true);
 }
  public GetCallersPermissionsToPersonType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static GetCallersPermissionsToPersonType.Builder<Void> builder() {
 return new GetCallersPermissionsToPersonType.Builder<>(null, null, false);
 }
  public static<_B >GetCallersPermissionsToPersonType.Builder<_B> copyOf(final GetCallersPermissionsToPersonType _other) {
 final GetCallersPermissionsToPersonType.Builder<_B> _newBuilder = new GetCallersPermissionsToPersonType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final GetCallersPermissionsToPersonType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree personIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personIdentifierPropertyTree!= null):((personIdentifierPropertyTree == null)||(!personIdentifierPropertyTree.isLeaf())))) {
  _other.personIdentifier = this.personIdentifier;
 }
 }
  public<_B >GetCallersPermissionsToPersonType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new GetCallersPermissionsToPersonType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public GetCallersPermissionsToPersonType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >GetCallersPermissionsToPersonType.Builder<_B> copyOf(final GetCallersPermissionsToPersonType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final GetCallersPermissionsToPersonType.Builder<_B> _newBuilder = new GetCallersPermissionsToPersonType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static GetCallersPermissionsToPersonType.Builder<Void> copyExcept(final GetCallersPermissionsToPersonType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static GetCallersPermissionsToPersonType.Builder<Void> copyOnly(final GetCallersPermissionsToPersonType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final GetCallersPermissionsToPersonType _storedValue;
 private String personIdentifier;
  public Builder(final _B _parentBuilder, final GetCallersPermissionsToPersonType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.personIdentifier = _other.personIdentifier;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final GetCallersPermissionsToPersonType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree personIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personIdentifierPropertyTree!= null):((personIdentifierPropertyTree == null)||(!personIdentifierPropertyTree.isLeaf())))) {
        this.personIdentifier = _other.personIdentifier;
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
  protected<_P extends GetCallersPermissionsToPersonType >_P init(final _P _product) {
  _product.personIdentifier = this.personIdentifier;
  return _product;
 }
  /**
 * Sets the new value of "personIdentifier" (any previous value will be replaced)
 * 
 * @param personIdentifier
 *     New value of the "personIdentifier" property.
 */
 public GetCallersPermissionsToPersonType.Builder<_B> withPersonIdentifier(final String personIdentifier) {
  this.personIdentifier = personIdentifier;
  return this;
 }
  @Override
 public GetCallersPermissionsToPersonType build() {
  if (_storedValue == null) {
   return this.init(new GetCallersPermissionsToPersonType());
  } else {
   return ((GetCallersPermissionsToPersonType) _storedValue);
  }
 }
  public GetCallersPermissionsToPersonType.Builder<_B> copyOf(final GetCallersPermissionsToPersonType _other) {
  _other.copyTo(this);
  return this;
 }
  public GetCallersPermissionsToPersonType.Builder<_B> copyOf(final GetCallersPermissionsToPersonType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends GetCallersPermissionsToPersonType.Selector<GetCallersPermissionsToPersonType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static GetCallersPermissionsToPersonType.Select _root() {
  return new GetCallersPermissionsToPersonType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, GetCallersPermissionsToPersonType.Selector<TRoot, TParent>> personIdentifier = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.personIdentifier!= null) {
   products.put("personIdentifier", this.personIdentifier.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, GetCallersPermissionsToPersonType.Selector<TRoot, TParent>> personIdentifier() {
  return ((this.personIdentifier == null)?this.personIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personIdentifier"):this.personIdentifier);
 }
  }
 }
