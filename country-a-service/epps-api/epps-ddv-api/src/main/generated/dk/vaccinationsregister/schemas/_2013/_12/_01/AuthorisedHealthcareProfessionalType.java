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
* <p>Java class for AuthorisedHealthcareProfessionalType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="AuthorisedHealthcareProfessionalType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="AuthorisationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisationIdentifierType"/>
*         <element name="Name" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisedHealthcareProfessionalNameType"/>
*         <element name="SpecialityCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SpecialityCodeType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorisedHealthcareProfessionalType", propOrder = {
 "authorisationIdentifier",
 "name",
 "specialityCode"
})
public class AuthorisedHealthcareProfessionalType {
  @XmlElement(name = "AuthorisationIdentifier", required = true)
 protected String authorisationIdentifier;
 @XmlElement(name = "Name", required = true)
 protected String name;
 @XmlElement(name = "SpecialityCode")
 protected String specialityCode;
  /**
 * Gets the value of the authorisationIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getAuthorisationIdentifier() {
 return authorisationIdentifier;
 }
  /**
 * Sets the value of the authorisationIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setAuthorisationIdentifier(String value) {
 this.authorisationIdentifier = value;
 }
  /**
 * Gets the value of the name property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getName() {
 return name;
 }
  /**
 * Sets the value of the name property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setName(String value) {
 this.name = value;
 }
  /**
 * Gets the value of the specialityCode property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getSpecialityCode() {
 return specialityCode;
 }
  /**
 * Sets the value of the specialityCode property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setSpecialityCode(String value) {
 this.specialityCode = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final AuthorisedHealthcareProfessionalType.Builder<_B> _other) {
 _other.authorisationIdentifier = this.authorisationIdentifier;
 _other.name = this.name;
 _other.specialityCode = this.specialityCode;
 }
  public<_B >AuthorisedHealthcareProfessionalType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new AuthorisedHealthcareProfessionalType.Builder<_B>(_parentBuilder, this, true);
 }
  public AuthorisedHealthcareProfessionalType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static AuthorisedHealthcareProfessionalType.Builder<Void> builder() {
 return new AuthorisedHealthcareProfessionalType.Builder<>(null, null, false);
 }
  public static<_B >AuthorisedHealthcareProfessionalType.Builder<_B> copyOf(final AuthorisedHealthcareProfessionalType _other) {
 final AuthorisedHealthcareProfessionalType.Builder<_B> _newBuilder = new AuthorisedHealthcareProfessionalType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final AuthorisedHealthcareProfessionalType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree authorisationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisationIdentifierPropertyTree!= null):((authorisationIdentifierPropertyTree == null)||(!authorisationIdentifierPropertyTree.isLeaf())))) {
  _other.authorisationIdentifier = this.authorisationIdentifier;
 }
 final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
  _other.name = this.name;
 }
 final PropertyTree specialityCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("specialityCode"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(specialityCodePropertyTree!= null):((specialityCodePropertyTree == null)||(!specialityCodePropertyTree.isLeaf())))) {
  _other.specialityCode = this.specialityCode;
 }
 }
  public<_B >AuthorisedHealthcareProfessionalType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new AuthorisedHealthcareProfessionalType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public AuthorisedHealthcareProfessionalType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >AuthorisedHealthcareProfessionalType.Builder<_B> copyOf(final AuthorisedHealthcareProfessionalType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final AuthorisedHealthcareProfessionalType.Builder<_B> _newBuilder = new AuthorisedHealthcareProfessionalType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static AuthorisedHealthcareProfessionalType.Builder<Void> copyExcept(final AuthorisedHealthcareProfessionalType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static AuthorisedHealthcareProfessionalType.Builder<Void> copyOnly(final AuthorisedHealthcareProfessionalType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final AuthorisedHealthcareProfessionalType _storedValue;
 private String authorisationIdentifier;
 private String name;
 private String specialityCode;
  public Builder(final _B _parentBuilder, final AuthorisedHealthcareProfessionalType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.authorisationIdentifier = _other.authorisationIdentifier;
     this.name = _other.name;
     this.specialityCode = _other.specialityCode;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final AuthorisedHealthcareProfessionalType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree authorisationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisationIdentifierPropertyTree!= null):((authorisationIdentifierPropertyTree == null)||(!authorisationIdentifierPropertyTree.isLeaf())))) {
        this.authorisationIdentifier = _other.authorisationIdentifier;
     }
     final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
        this.name = _other.name;
     }
     final PropertyTree specialityCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("specialityCode"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(specialityCodePropertyTree!= null):((specialityCodePropertyTree == null)||(!specialityCodePropertyTree.isLeaf())))) {
        this.specialityCode = _other.specialityCode;
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
  protected<_P extends AuthorisedHealthcareProfessionalType >_P init(final _P _product) {
  _product.authorisationIdentifier = this.authorisationIdentifier;
  _product.name = this.name;
  _product.specialityCode = this.specialityCode;
  return _product;
 }
  /**
 * Sets the new value of "authorisationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param authorisationIdentifier
 *     New value of the "authorisationIdentifier" property.
 */
 public AuthorisedHealthcareProfessionalType.Builder<_B> withAuthorisationIdentifier(final String authorisationIdentifier) {
  this.authorisationIdentifier = authorisationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "name" (any previous value will be replaced)
 * 
 * @param name
 *     New value of the "name" property.
 */
 public AuthorisedHealthcareProfessionalType.Builder<_B> withName(final String name) {
  this.name = name;
  return this;
 }
  /**
 * Sets the new value of "specialityCode" (any previous value will be replaced)
 * 
 * @param specialityCode
 *     New value of the "specialityCode" property.
 */
 public AuthorisedHealthcareProfessionalType.Builder<_B> withSpecialityCode(final String specialityCode) {
  this.specialityCode = specialityCode;
  return this;
 }
  @Override
 public AuthorisedHealthcareProfessionalType build() {
  if (_storedValue == null) {
   return this.init(new AuthorisedHealthcareProfessionalType());
  } else {
   return ((AuthorisedHealthcareProfessionalType) _storedValue);
  }
 }
  public AuthorisedHealthcareProfessionalType.Builder<_B> copyOf(final AuthorisedHealthcareProfessionalType _other) {
  _other.copyTo(this);
  return this;
 }
  public AuthorisedHealthcareProfessionalType.Builder<_B> copyOf(final AuthorisedHealthcareProfessionalType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends AuthorisedHealthcareProfessionalType.Selector<AuthorisedHealthcareProfessionalType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static AuthorisedHealthcareProfessionalType.Select _root() {
  return new AuthorisedHealthcareProfessionalType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, AuthorisedHealthcareProfessionalType.Selector<TRoot, TParent>> authorisationIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, AuthorisedHealthcareProfessionalType.Selector<TRoot, TParent>> name = null;
 private com.kscs.util.jaxb.Selector<TRoot, AuthorisedHealthcareProfessionalType.Selector<TRoot, TParent>> specialityCode = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.authorisationIdentifier!= null) {
   products.put("authorisationIdentifier", this.authorisationIdentifier.init());
  }
  if (this.name!= null) {
   products.put("name", this.name.init());
  }
  if (this.specialityCode!= null) {
   products.put("specialityCode", this.specialityCode.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, AuthorisedHealthcareProfessionalType.Selector<TRoot, TParent>> authorisationIdentifier() {
  return ((this.authorisationIdentifier == null)?this.authorisationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "authorisationIdentifier"):this.authorisationIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, AuthorisedHealthcareProfessionalType.Selector<TRoot, TParent>> name() {
  return ((this.name == null)?this.name = new com.kscs.util.jaxb.Selector<>(this._root, this, "name"):this.name);
 }
  public com.kscs.util.jaxb.Selector<TRoot, AuthorisedHealthcareProfessionalType.Selector<TRoot, TParent>> specialityCode() {
  return ((this.specialityCode == null)?this.specialityCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "specialityCode"):this.specialityCode);
 }
  }
 }
