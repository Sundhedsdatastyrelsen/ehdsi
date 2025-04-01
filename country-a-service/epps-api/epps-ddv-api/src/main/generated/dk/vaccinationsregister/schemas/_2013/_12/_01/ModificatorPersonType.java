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
* <p>Java class for ModificatorPersonType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="ModificatorPersonType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="Name" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PersonNameType"/>
*         <element name="PersonIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PersonIdentifierType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModificatorPersonType", propOrder = {
 "name",
 "personIdentifier"
})
public class ModificatorPersonType {
  @XmlElement(name = "Name", required = true)
 protected PersonNameType name;
 @XmlElement(name = "PersonIdentifier")
 protected String personIdentifier;
  /**
 * Gets the value of the name property.
 * 
 * @return
 *     possible object is
 *     {@link PersonNameType }
 *     
 */
 public PersonNameType getName() {
 return name;
 }
  /**
 * Sets the value of the name property.
 * 
 * @param value
 *     allowed object is
 *     {@link PersonNameType }
 *     
 */
 public void setName(PersonNameType value) {
 this.name = value;
 }
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
 public<_B >void copyTo(final ModificatorPersonType.Builder<_B> _other) {
 _other.name = ((this.name == null)?null:this.name.newCopyBuilder(_other));
 _other.personIdentifier = this.personIdentifier;
 }
  public<_B >ModificatorPersonType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new ModificatorPersonType.Builder<_B>(_parentBuilder, this, true);
 }
  public ModificatorPersonType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static ModificatorPersonType.Builder<Void> builder() {
 return new ModificatorPersonType.Builder<>(null, null, false);
 }
  public static<_B >ModificatorPersonType.Builder<_B> copyOf(final ModificatorPersonType _other) {
 final ModificatorPersonType.Builder<_B> _newBuilder = new ModificatorPersonType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final ModificatorPersonType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
  _other.name = ((this.name == null)?null:this.name.newCopyBuilder(_other, namePropertyTree, _propertyTreeUse));
 }
 final PropertyTree personIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personIdentifierPropertyTree!= null):((personIdentifierPropertyTree == null)||(!personIdentifierPropertyTree.isLeaf())))) {
  _other.personIdentifier = this.personIdentifier;
 }
 }
  public<_B >ModificatorPersonType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new ModificatorPersonType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public ModificatorPersonType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >ModificatorPersonType.Builder<_B> copyOf(final ModificatorPersonType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final ModificatorPersonType.Builder<_B> _newBuilder = new ModificatorPersonType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static ModificatorPersonType.Builder<Void> copyExcept(final ModificatorPersonType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static ModificatorPersonType.Builder<Void> copyOnly(final ModificatorPersonType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final ModificatorPersonType _storedValue;
 private PersonNameType.Builder<ModificatorPersonType.Builder<_B>> name;
 private String personIdentifier;
  public Builder(final _B _parentBuilder, final ModificatorPersonType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.name = ((_other.name == null)?null:_other.name.newCopyBuilder(this));
     this.personIdentifier = _other.personIdentifier;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final ModificatorPersonType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
        this.name = ((_other.name == null)?null:_other.name.newCopyBuilder(this, namePropertyTree, _propertyTreeUse));
     }
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
  protected<_P extends ModificatorPersonType >_P init(final _P _product) {
  _product.name = ((this.name == null)?null:this.name.build());
  _product.personIdentifier = this.personIdentifier;
  return _product;
 }
  /**
 * Sets the new value of "name" (any previous value will be replaced)
 * 
 * @param name
 *     New value of the "name" property.
 */
 public ModificatorPersonType.Builder<_B> withName(final PersonNameType name) {
  this.name = ((name == null)?null:new PersonNameType.Builder<>(this, name, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the "name"
 * property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.PersonNameType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "name" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.PersonNameType.Builder#end()} to
 *     return to the current builder.
 */
 public PersonNameType.Builder<? extends ModificatorPersonType.Builder<_B>> withName() {
  if (this.name!= null) {
   return this.name;
  }
  return this.name = new PersonNameType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "personIdentifier" (any previous value will be replaced)
 * 
 * @param personIdentifier
 *     New value of the "personIdentifier" property.
 */
 public ModificatorPersonType.Builder<_B> withPersonIdentifier(final String personIdentifier) {
  this.personIdentifier = personIdentifier;
  return this;
 }
  @Override
 public ModificatorPersonType build() {
  if (_storedValue == null) {
   return this.init(new ModificatorPersonType());
  } else {
   return ((ModificatorPersonType) _storedValue);
  }
 }
  public ModificatorPersonType.Builder<_B> copyOf(final ModificatorPersonType _other) {
  _other.copyTo(this);
  return this;
 }
  public ModificatorPersonType.Builder<_B> copyOf(final ModificatorPersonType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends ModificatorPersonType.Selector<ModificatorPersonType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static ModificatorPersonType.Select _root() {
  return new ModificatorPersonType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private PersonNameType.Selector<TRoot, ModificatorPersonType.Selector<TRoot, TParent>> name = null;
 private com.kscs.util.jaxb.Selector<TRoot, ModificatorPersonType.Selector<TRoot, TParent>> personIdentifier = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.name!= null) {
   products.put("name", this.name.init());
  }
  if (this.personIdentifier!= null) {
   products.put("personIdentifier", this.personIdentifier.init());
  }
  return products;
 }
  public PersonNameType.Selector<TRoot, ModificatorPersonType.Selector<TRoot, TParent>> name() {
  return ((this.name == null)?this.name = new PersonNameType.Selector<>(this._root, this, "name"):this.name);
 }
  public com.kscs.util.jaxb.Selector<TRoot, ModificatorPersonType.Selector<TRoot, TParent>> personIdentifier() {
  return ((this.personIdentifier == null)?this.personIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personIdentifier"):this.personIdentifier);
 }
  }
 }
