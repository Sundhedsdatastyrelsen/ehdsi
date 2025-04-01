package dk.oio.rep.itst_dk.xml.schemas._2006._01._17;
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
* <p>Java class for PersonNameStructureType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="PersonNameStructureType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/}PersonGivenName"/>
*         <element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/}PersonMiddleName" minOccurs="0"/>
*         <element ref="{http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/}PersonSurnameName"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonNameStructureType", propOrder = {
 "personGivenName",
 "personMiddleName",
 "personSurnameName"
})
public class PersonNameStructureType {
  @XmlElement(name = "PersonGivenName", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/", required = true)
 protected String personGivenName;
 @XmlElement(name = "PersonMiddleName", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/")
 protected String personMiddleName;
 @XmlElement(name = "PersonSurnameName", namespace = "http://rep.oio.dk/ebxml/xml/schemas/dkcc/2003/02/13/", required = true)
 protected String personSurnameName;
  /**
 * Gets the value of the personGivenName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getPersonGivenName() {
 return personGivenName;
 }
  /**
 * Sets the value of the personGivenName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setPersonGivenName(String value) {
 this.personGivenName = value;
 }
  /**
 * Gets the value of the personMiddleName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getPersonMiddleName() {
 return personMiddleName;
 }
  /**
 * Sets the value of the personMiddleName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setPersonMiddleName(String value) {
 this.personMiddleName = value;
 }
  /**
 * Gets the value of the personSurnameName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getPersonSurnameName() {
 return personSurnameName;
 }
  /**
 * Sets the value of the personSurnameName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setPersonSurnameName(String value) {
 this.personSurnameName = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final PersonNameStructureType.Builder<_B> _other) {
 _other.personGivenName = this.personGivenName;
 _other.personMiddleName = this.personMiddleName;
 _other.personSurnameName = this.personSurnameName;
 }
  public<_B >PersonNameStructureType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new PersonNameStructureType.Builder<_B>(_parentBuilder, this, true);
 }
  public PersonNameStructureType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static PersonNameStructureType.Builder<Void> builder() {
 return new PersonNameStructureType.Builder<>(null, null, false);
 }
  public static<_B >PersonNameStructureType.Builder<_B> copyOf(final PersonNameStructureType _other) {
 final PersonNameStructureType.Builder<_B> _newBuilder = new PersonNameStructureType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final PersonNameStructureType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree personGivenNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personGivenName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personGivenNamePropertyTree!= null):((personGivenNamePropertyTree == null)||(!personGivenNamePropertyTree.isLeaf())))) {
  _other.personGivenName = this.personGivenName;
 }
 final PropertyTree personMiddleNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personMiddleName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personMiddleNamePropertyTree!= null):((personMiddleNamePropertyTree == null)||(!personMiddleNamePropertyTree.isLeaf())))) {
  _other.personMiddleName = this.personMiddleName;
 }
 final PropertyTree personSurnameNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personSurnameName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personSurnameNamePropertyTree!= null):((personSurnameNamePropertyTree == null)||(!personSurnameNamePropertyTree.isLeaf())))) {
  _other.personSurnameName = this.personSurnameName;
 }
 }
  public<_B >PersonNameStructureType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new PersonNameStructureType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public PersonNameStructureType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >PersonNameStructureType.Builder<_B> copyOf(final PersonNameStructureType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PersonNameStructureType.Builder<_B> _newBuilder = new PersonNameStructureType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static PersonNameStructureType.Builder<Void> copyExcept(final PersonNameStructureType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static PersonNameStructureType.Builder<Void> copyOnly(final PersonNameStructureType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final PersonNameStructureType _storedValue;
 private String personGivenName;
 private String personMiddleName;
 private String personSurnameName;
  public Builder(final _B _parentBuilder, final PersonNameStructureType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.personGivenName = _other.personGivenName;
     this.personMiddleName = _other.personMiddleName;
     this.personSurnameName = _other.personSurnameName;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final PersonNameStructureType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree personGivenNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personGivenName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personGivenNamePropertyTree!= null):((personGivenNamePropertyTree == null)||(!personGivenNamePropertyTree.isLeaf())))) {
        this.personGivenName = _other.personGivenName;
     }
     final PropertyTree personMiddleNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personMiddleName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personMiddleNamePropertyTree!= null):((personMiddleNamePropertyTree == null)||(!personMiddleNamePropertyTree.isLeaf())))) {
        this.personMiddleName = _other.personMiddleName;
     }
     final PropertyTree personSurnameNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personSurnameName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personSurnameNamePropertyTree!= null):((personSurnameNamePropertyTree == null)||(!personSurnameNamePropertyTree.isLeaf())))) {
        this.personSurnameName = _other.personSurnameName;
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
  protected<_P extends PersonNameStructureType >_P init(final _P _product) {
  _product.personGivenName = this.personGivenName;
  _product.personMiddleName = this.personMiddleName;
  _product.personSurnameName = this.personSurnameName;
  return _product;
 }
  /**
 * Sets the new value of "personGivenName" (any previous value will be replaced)
 * 
 * @param personGivenName
 *     New value of the "personGivenName" property.
 */
 public PersonNameStructureType.Builder<_B> withPersonGivenName(final String personGivenName) {
  this.personGivenName = personGivenName;
  return this;
 }
  /**
 * Sets the new value of "personMiddleName" (any previous value will be replaced)
 * 
 * @param personMiddleName
 *     New value of the "personMiddleName" property.
 */
 public PersonNameStructureType.Builder<_B> withPersonMiddleName(final String personMiddleName) {
  this.personMiddleName = personMiddleName;
  return this;
 }
  /**
 * Sets the new value of "personSurnameName" (any previous value will be replaced)
 * 
 * @param personSurnameName
 *     New value of the "personSurnameName" property.
 */
 public PersonNameStructureType.Builder<_B> withPersonSurnameName(final String personSurnameName) {
  this.personSurnameName = personSurnameName;
  return this;
 }
  @Override
 public PersonNameStructureType build() {
  if (_storedValue == null) {
   return this.init(new PersonNameStructureType());
  } else {
   return ((PersonNameStructureType) _storedValue);
  }
 }
  public PersonNameStructureType.Builder<_B> copyOf(final PersonNameStructureType _other) {
  _other.copyTo(this);
  return this;
 }
  public PersonNameStructureType.Builder<_B> copyOf(final PersonNameStructureType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends PersonNameStructureType.Selector<PersonNameStructureType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static PersonNameStructureType.Select _root() {
  return new PersonNameStructureType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, PersonNameStructureType.Selector<TRoot, TParent>> personGivenName = null;
 private com.kscs.util.jaxb.Selector<TRoot, PersonNameStructureType.Selector<TRoot, TParent>> personMiddleName = null;
 private com.kscs.util.jaxb.Selector<TRoot, PersonNameStructureType.Selector<TRoot, TParent>> personSurnameName = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.personGivenName!= null) {
   products.put("personGivenName", this.personGivenName.init());
  }
  if (this.personMiddleName!= null) {
   products.put("personMiddleName", this.personMiddleName.init());
  }
  if (this.personSurnameName!= null) {
   products.put("personSurnameName", this.personSurnameName.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, PersonNameStructureType.Selector<TRoot, TParent>> personGivenName() {
  return ((this.personGivenName == null)?this.personGivenName = new com.kscs.util.jaxb.Selector<>(this._root, this, "personGivenName"):this.personGivenName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PersonNameStructureType.Selector<TRoot, TParent>> personMiddleName() {
  return ((this.personMiddleName == null)?this.personMiddleName = new com.kscs.util.jaxb.Selector<>(this._root, this, "personMiddleName"):this.personMiddleName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PersonNameStructureType.Selector<TRoot, TParent>> personSurnameName() {
  return ((this.personSurnameName == null)?this.personSurnameName = new com.kscs.util.jaxb.Selector<>(this._root, this, "personSurnameName"):this.personSurnameName);
 }
  }
 }
