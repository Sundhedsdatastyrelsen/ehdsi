package dk.vaccinationsregister.schemas._2013._12._01;
import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
/**
* <p>Java class for OrganisationIdentifierType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="OrganisationIdentifierType">
*   <simpleContent>
*     <extension base="<http://vaccinationsregister.dk/schemas/2013/12/01>OrganisationIdentifierValueType">
*       <attribute name="source" use="required" type="{http://vaccinationsregister.dk/schemas/2013/12/01}OrganisationIdentifierSourceType" />
*     </extension>
*   </simpleContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganisationIdentifierType", propOrder = {
 "value"
})
public class OrganisationIdentifierType {
  @XmlValue
 protected String value;
 @XmlAttribute(name = "source", required = true)
 protected String source;
  /**
 * Gets the value of the value property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getValue() {
 return value;
 }
  /**
 * Sets the value of the value property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setValue(String value) {
 this.value = value;
 }
  /**
 * Gets the value of the source property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getSource() {
 return source;
 }
  /**
 * Sets the value of the source property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setSource(String value) {
 this.source = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final OrganisationIdentifierType.Builder<_B> _other) {
 _other.value = this.value;
 _other.source = this.source;
 }
  public<_B >OrganisationIdentifierType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new OrganisationIdentifierType.Builder<_B>(_parentBuilder, this, true);
 }
  public OrganisationIdentifierType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static OrganisationIdentifierType.Builder<Void> builder() {
 return new OrganisationIdentifierType.Builder<>(null, null, false);
 }
  public static<_B >OrganisationIdentifierType.Builder<_B> copyOf(final OrganisationIdentifierType _other) {
 final OrganisationIdentifierType.Builder<_B> _newBuilder = new OrganisationIdentifierType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final OrganisationIdentifierType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
  _other.value = this.value;
 }
 final PropertyTree sourcePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("source"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(sourcePropertyTree!= null):((sourcePropertyTree == null)||(!sourcePropertyTree.isLeaf())))) {
  _other.source = this.source;
 }
 }
  public<_B >OrganisationIdentifierType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new OrganisationIdentifierType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public OrganisationIdentifierType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >OrganisationIdentifierType.Builder<_B> copyOf(final OrganisationIdentifierType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final OrganisationIdentifierType.Builder<_B> _newBuilder = new OrganisationIdentifierType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static OrganisationIdentifierType.Builder<Void> copyExcept(final OrganisationIdentifierType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static OrganisationIdentifierType.Builder<Void> copyOnly(final OrganisationIdentifierType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final OrganisationIdentifierType _storedValue;
 private String value;
 private String source;
  public Builder(final _B _parentBuilder, final OrganisationIdentifierType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.value = _other.value;
     this.source = _other.source;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final OrganisationIdentifierType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
        this.value = _other.value;
     }
     final PropertyTree sourcePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("source"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(sourcePropertyTree!= null):((sourcePropertyTree == null)||(!sourcePropertyTree.isLeaf())))) {
        this.source = _other.source;
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
  protected<_P extends OrganisationIdentifierType >_P init(final _P _product) {
  _product.value = this.value;
  _product.source = this.source;
  return _product;
 }
  /**
 * Sets the new value of "value" (any previous value will be replaced)
 * 
 * @param value
 *     New value of the "value" property.
 */
 public OrganisationIdentifierType.Builder<_B> withValue(final String value) {
  this.value = value;
  return this;
 }
  /**
 * Sets the new value of "source" (any previous value will be replaced)
 * 
 * @param source
 *     New value of the "source" property.
 */
 public OrganisationIdentifierType.Builder<_B> withSource(final String source) {
  this.source = source;
  return this;
 }
  @Override
 public OrganisationIdentifierType build() {
  if (_storedValue == null) {
   return this.init(new OrganisationIdentifierType());
  } else {
   return ((OrganisationIdentifierType) _storedValue);
  }
 }
  public OrganisationIdentifierType.Builder<_B> copyOf(final OrganisationIdentifierType _other) {
  _other.copyTo(this);
  return this;
 }
  public OrganisationIdentifierType.Builder<_B> copyOf(final OrganisationIdentifierType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends OrganisationIdentifierType.Selector<OrganisationIdentifierType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static OrganisationIdentifierType.Select _root() {
  return new OrganisationIdentifierType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, OrganisationIdentifierType.Selector<TRoot, TParent>> value = null;
 private com.kscs.util.jaxb.Selector<TRoot, OrganisationIdentifierType.Selector<TRoot, TParent>> source = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.value!= null) {
   products.put("value", this.value.init());
  }
  if (this.source!= null) {
   products.put("source", this.source.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, OrganisationIdentifierType.Selector<TRoot, TParent>> value() {
  return ((this.value == null)?this.value = new com.kscs.util.jaxb.Selector<>(this._root, this, "value"):this.value);
 }
  public com.kscs.util.jaxb.Selector<TRoot, OrganisationIdentifierType.Selector<TRoot, TParent>> source() {
  return ((this.source == null)?this.source = new com.kscs.util.jaxb.Selector<>(this._root, this, "source"):this.source);
 }
  }
 }
