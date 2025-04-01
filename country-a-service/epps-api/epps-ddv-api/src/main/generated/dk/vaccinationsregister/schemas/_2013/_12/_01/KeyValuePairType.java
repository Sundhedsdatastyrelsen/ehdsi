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
* <p>Java class for KeyValuePairType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="KeyValuePairType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="Key" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         <element name="Value" type="{http://www.w3.org/2001/XMLSchema}string"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyValuePairType", propOrder = {
 "key",
 "value"
})
public class KeyValuePairType {
  @XmlElement(name = "Key", required = true)
 protected String key;
 @XmlElement(name = "Value", required = true)
 protected String value;
  /**
 * Gets the value of the key property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getKey() {
 return key;
 }
  /**
 * Sets the value of the key property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setKey(String value) {
 this.key = value;
 }
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
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final KeyValuePairType.Builder<_B> _other) {
 _other.key = this.key;
 _other.value = this.value;
 }
  public<_B >KeyValuePairType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new KeyValuePairType.Builder<_B>(_parentBuilder, this, true);
 }
  public KeyValuePairType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static KeyValuePairType.Builder<Void> builder() {
 return new KeyValuePairType.Builder<>(null, null, false);
 }
  public static<_B >KeyValuePairType.Builder<_B> copyOf(final KeyValuePairType _other) {
 final KeyValuePairType.Builder<_B> _newBuilder = new KeyValuePairType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final KeyValuePairType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree keyPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("key"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyPropertyTree!= null):((keyPropertyTree == null)||(!keyPropertyTree.isLeaf())))) {
  _other.key = this.key;
 }
 final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
  _other.value = this.value;
 }
 }
  public<_B >KeyValuePairType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new KeyValuePairType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public KeyValuePairType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >KeyValuePairType.Builder<_B> copyOf(final KeyValuePairType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final KeyValuePairType.Builder<_B> _newBuilder = new KeyValuePairType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static KeyValuePairType.Builder<Void> copyExcept(final KeyValuePairType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static KeyValuePairType.Builder<Void> copyOnly(final KeyValuePairType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final KeyValuePairType _storedValue;
 private String key;
 private String value;
  public Builder(final _B _parentBuilder, final KeyValuePairType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.key = _other.key;
     this.value = _other.value;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final KeyValuePairType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree keyPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("key"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyPropertyTree!= null):((keyPropertyTree == null)||(!keyPropertyTree.isLeaf())))) {
        this.key = _other.key;
     }
     final PropertyTree valuePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("value"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(valuePropertyTree!= null):((valuePropertyTree == null)||(!valuePropertyTree.isLeaf())))) {
        this.value = _other.value;
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
  protected<_P extends KeyValuePairType >_P init(final _P _product) {
  _product.key = this.key;
  _product.value = this.value;
  return _product;
 }
  /**
 * Sets the new value of "key" (any previous value will be replaced)
 * 
 * @param key
 *     New value of the "key" property.
 */
 public KeyValuePairType.Builder<_B> withKey(final String key) {
  this.key = key;
  return this;
 }
  /**
 * Sets the new value of "value" (any previous value will be replaced)
 * 
 * @param value
 *     New value of the "value" property.
 */
 public KeyValuePairType.Builder<_B> withValue(final String value) {
  this.value = value;
  return this;
 }
  @Override
 public KeyValuePairType build() {
  if (_storedValue == null) {
   return this.init(new KeyValuePairType());
  } else {
   return ((KeyValuePairType) _storedValue);
  }
 }
  public KeyValuePairType.Builder<_B> copyOf(final KeyValuePairType _other) {
  _other.copyTo(this);
  return this;
 }
  public KeyValuePairType.Builder<_B> copyOf(final KeyValuePairType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends KeyValuePairType.Selector<KeyValuePairType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static KeyValuePairType.Select _root() {
  return new KeyValuePairType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, KeyValuePairType.Selector<TRoot, TParent>> key = null;
 private com.kscs.util.jaxb.Selector<TRoot, KeyValuePairType.Selector<TRoot, TParent>> value = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.key!= null) {
   products.put("key", this.key.init());
  }
  if (this.value!= null) {
   products.put("value", this.value.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, KeyValuePairType.Selector<TRoot, TParent>> key() {
  return ((this.key == null)?this.key = new com.kscs.util.jaxb.Selector<>(this._root, this, "key"):this.key);
 }
  public com.kscs.util.jaxb.Selector<TRoot, KeyValuePairType.Selector<TRoot, TParent>> value() {
  return ((this.value == null)?this.value = new com.kscs.util.jaxb.Selector<>(this._root, this, "value"):this.value);
 }
  }
 }
