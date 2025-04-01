package oasis.names.tc.saml._2_0.assertion;
import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for anonymous complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType>
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AttributeValue"/>
*       </sequence>
*       <attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
*       <attribute name="NameFormat" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "attributeValue"
})
@XmlRootElement(name = "Attribute")
public class Attribute {
  @XmlElement(name = "AttributeValue", required = true)
 protected String attributeValue;
 @XmlAttribute(name = "Name", required = true)
 protected String name;
 @XmlAttribute(name = "NameFormat")
 @XmlSchemaType(name = "anyURI")
 protected String nameFormat;
  /**
 * Gets the value of the attributeValue property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getAttributeValue() {
 return attributeValue;
 }
  /**
 * Sets the value of the attributeValue property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setAttributeValue(String value) {
 this.attributeValue = value;
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
 * Gets the value of the nameFormat property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getNameFormat() {
 return nameFormat;
 }
  /**
 * Sets the value of the nameFormat property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setNameFormat(String value) {
 this.nameFormat = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final Attribute.Builder<_B> _other) {
 _other.attributeValue = this.attributeValue;
 _other.name = this.name;
 _other.nameFormat = this.nameFormat;
 }
  public<_B >Attribute.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new Attribute.Builder<_B>(_parentBuilder, this, true);
 }
  public Attribute.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static Attribute.Builder<Void> builder() {
 return new Attribute.Builder<>(null, null, false);
 }
  public static<_B >Attribute.Builder<_B> copyOf(final Attribute _other) {
 final Attribute.Builder<_B> _newBuilder = new Attribute.Builder<>(null, null, false);
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
 public<_B >void copyTo(final Attribute.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree attributeValuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("attributeValue"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(attributeValuePropertyTree!= null):((attributeValuePropertyTree == null)||(!attributeValuePropertyTree.isLeaf())))) {
  _other.attributeValue = this.attributeValue;
 }
 final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
  _other.name = this.name;
 }
 final PropertyTree nameFormatPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("nameFormat"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(nameFormatPropertyTree!= null):((nameFormatPropertyTree == null)||(!nameFormatPropertyTree.isLeaf())))) {
  _other.nameFormat = this.nameFormat;
 }
 }
  public<_B >Attribute.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new Attribute.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public Attribute.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >Attribute.Builder<_B> copyOf(final Attribute _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final Attribute.Builder<_B> _newBuilder = new Attribute.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static Attribute.Builder<Void> copyExcept(final Attribute _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static Attribute.Builder<Void> copyOnly(final Attribute _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final Attribute _storedValue;
 private String attributeValue;
 private String name;
 private String nameFormat;
  public Builder(final _B _parentBuilder, final Attribute _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.attributeValue = _other.attributeValue;
     this.name = _other.name;
     this.nameFormat = _other.nameFormat;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final Attribute _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree attributeValuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("attributeValue"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(attributeValuePropertyTree!= null):((attributeValuePropertyTree == null)||(!attributeValuePropertyTree.isLeaf())))) {
        this.attributeValue = _other.attributeValue;
     }
     final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
        this.name = _other.name;
     }
     final PropertyTree nameFormatPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("nameFormat"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(nameFormatPropertyTree!= null):((nameFormatPropertyTree == null)||(!nameFormatPropertyTree.isLeaf())))) {
        this.nameFormat = _other.nameFormat;
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
  protected<_P extends Attribute >_P init(final _P _product) {
  _product.attributeValue = this.attributeValue;
  _product.name = this.name;
  _product.nameFormat = this.nameFormat;
  return _product;
 }
  /**
 * Sets the new value of "attributeValue" (any previous value will be replaced)
 * 
 * @param attributeValue
 *     New value of the "attributeValue" property.
 */
 public Attribute.Builder<_B> withAttributeValue(final String attributeValue) {
  this.attributeValue = attributeValue;
  return this;
 }
  /**
 * Sets the new value of "name" (any previous value will be replaced)
 * 
 * @param name
 *     New value of the "name" property.
 */
 public Attribute.Builder<_B> withName(final String name) {
  this.name = name;
  return this;
 }
  /**
 * Sets the new value of "nameFormat" (any previous value will be replaced)
 * 
 * @param nameFormat
 *     New value of the "nameFormat" property.
 */
 public Attribute.Builder<_B> withNameFormat(final String nameFormat) {
  this.nameFormat = nameFormat;
  return this;
 }
  @Override
 public Attribute build() {
  if (_storedValue == null) {
   return this.init(new Attribute());
  } else {
   return ((Attribute) _storedValue);
  }
 }
  public Attribute.Builder<_B> copyOf(final Attribute _other) {
  _other.copyTo(this);
  return this;
 }
  public Attribute.Builder<_B> copyOf(final Attribute.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends Attribute.Selector<Attribute.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static Attribute.Select _root() {
  return new Attribute.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, Attribute.Selector<TRoot, TParent>> attributeValue = null;
 private com.kscs.util.jaxb.Selector<TRoot, Attribute.Selector<TRoot, TParent>> name = null;
 private com.kscs.util.jaxb.Selector<TRoot, Attribute.Selector<TRoot, TParent>> nameFormat = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.attributeValue!= null) {
   products.put("attributeValue", this.attributeValue.init());
  }
  if (this.name!= null) {
   products.put("name", this.name.init());
  }
  if (this.nameFormat!= null) {
   products.put("nameFormat", this.nameFormat.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, Attribute.Selector<TRoot, TParent>> attributeValue() {
  return ((this.attributeValue == null)?this.attributeValue = new com.kscs.util.jaxb.Selector<>(this._root, this, "attributeValue"):this.attributeValue);
 }
  public com.kscs.util.jaxb.Selector<TRoot, Attribute.Selector<TRoot, TParent>> name() {
  return ((this.name == null)?this.name = new com.kscs.util.jaxb.Selector<>(this._root, this, "name"):this.name);
 }
  public com.kscs.util.jaxb.Selector<TRoot, Attribute.Selector<TRoot, TParent>> nameFormat() {
  return ((this.nameFormat == null)?this.nameFormat = new com.kscs.util.jaxb.Selector<>(this._root, this, "nameFormat"):this.nameFormat);
 }
  }
 }
