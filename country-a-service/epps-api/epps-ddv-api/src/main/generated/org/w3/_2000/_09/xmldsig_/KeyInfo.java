package org.w3._2000._09.xmldsig_;
import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
* <p>Java class for anonymous complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType>
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <choice>
*         <element ref="{http://www.w3.org/2000/09/xmldsig#}KeyName"/>
*         <sequence>
*           <element ref="{http://www.w3.org/2000/09/xmldsig#}X509Data"/>
*         </sequence>
*       </choice>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "keyName",
 "x509Data"
})
@XmlRootElement(name = "KeyInfo")
public class KeyInfo {
  @XmlElement(name = "KeyName")
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 @XmlSchemaType(name = "NMTOKEN")
 protected String keyName;
 @XmlElement(name = "X509Data")
 protected X509Data x509Data;
  /**
 * Gets the value of the keyName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getKeyName() {
 return keyName;
 }
  /**
 * Sets the value of the keyName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setKeyName(String value) {
 this.keyName = value;
 }
  /**
 * Gets the value of the x509Data property.
 * 
 * @return
 *     possible object is
 *     {@link X509Data }
 *     
 */
 public X509Data getX509Data() {
 return x509Data;
 }
  /**
 * Sets the value of the x509Data property.
 * 
 * @param value
 *     allowed object is
 *     {@link X509Data }
 *     
 */
 public void setX509Data(X509Data value) {
 this.x509Data = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final KeyInfo.Builder<_B> _other) {
 _other.keyName = this.keyName;
 _other.x509Data = ((this.x509Data == null)?null:this.x509Data.newCopyBuilder(_other));
 }
  public<_B >KeyInfo.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new KeyInfo.Builder<_B>(_parentBuilder, this, true);
 }
  public KeyInfo.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static KeyInfo.Builder<Void> builder() {
 return new KeyInfo.Builder<>(null, null, false);
 }
  public static<_B >KeyInfo.Builder<_B> copyOf(final KeyInfo _other) {
 final KeyInfo.Builder<_B> _newBuilder = new KeyInfo.Builder<>(null, null, false);
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
 public<_B >void copyTo(final KeyInfo.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree keyNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyNamePropertyTree!= null):((keyNamePropertyTree == null)||(!keyNamePropertyTree.isLeaf())))) {
  _other.keyName = this.keyName;
 }
 final PropertyTree x509DataPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509Data"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509DataPropertyTree!= null):((x509DataPropertyTree == null)||(!x509DataPropertyTree.isLeaf())))) {
  _other.x509Data = ((this.x509Data == null)?null:this.x509Data.newCopyBuilder(_other, x509DataPropertyTree, _propertyTreeUse));
 }
 }
  public<_B >KeyInfo.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new KeyInfo.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public KeyInfo.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >KeyInfo.Builder<_B> copyOf(final KeyInfo _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final KeyInfo.Builder<_B> _newBuilder = new KeyInfo.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static KeyInfo.Builder<Void> copyExcept(final KeyInfo _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static KeyInfo.Builder<Void> copyOnly(final KeyInfo _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final KeyInfo _storedValue;
 private String keyName;
 private X509Data.Builder<KeyInfo.Builder<_B>> x509Data;
  public Builder(final _B _parentBuilder, final KeyInfo _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.keyName = _other.keyName;
     this.x509Data = ((_other.x509Data == null)?null:_other.x509Data.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final KeyInfo _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree keyNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyNamePropertyTree!= null):((keyNamePropertyTree == null)||(!keyNamePropertyTree.isLeaf())))) {
        this.keyName = _other.keyName;
     }
     final PropertyTree x509DataPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("x509Data"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(x509DataPropertyTree!= null):((x509DataPropertyTree == null)||(!x509DataPropertyTree.isLeaf())))) {
        this.x509Data = ((_other.x509Data == null)?null:_other.x509Data.newCopyBuilder(this, x509DataPropertyTree, _propertyTreeUse));
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
  protected<_P extends KeyInfo >_P init(final _P _product) {
  _product.keyName = this.keyName;
  _product.x509Data = ((this.x509Data == null)?null:this.x509Data.build());
  return _product;
 }
  /**
 * Sets the new value of "keyName" (any previous value will be replaced)
 * 
 * @param keyName
 *     New value of the "keyName" property.
 */
 public KeyInfo.Builder<_B> withKeyName(final String keyName) {
  this.keyName = keyName;
  return this;
 }
  /**
 * Sets the new value of "x509Data" (any previous value will be replaced)
 * 
 * @param x509Data
 *     New value of the "x509Data" property.
 */
 public KeyInfo.Builder<_B> withX509Data(final X509Data x509Data) {
  this.x509Data = ((x509Data == null)?null:new X509Data.Builder<>(this, x509Data, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "x509Data" property.
 * Use {@link org.w3._2000._09.xmldsig_.X509Data.Builder#end()} to return to the
 * current builder.
 * 
 * @return
 *     A new builder to build the value of the "x509Data" property.
 *     Use {@link org.w3._2000._09.xmldsig_.X509Data.Builder#end()} to return to the
 *     current builder.
 */
 public X509Data.Builder<? extends KeyInfo.Builder<_B>> withX509Data() {
  if (this.x509Data!= null) {
   return this.x509Data;
  }
  return this.x509Data = new X509Data.Builder<>(this, null, false);
 }
  @Override
 public KeyInfo build() {
  if (_storedValue == null) {
   return this.init(new KeyInfo());
  } else {
   return ((KeyInfo) _storedValue);
  }
 }
  public KeyInfo.Builder<_B> copyOf(final KeyInfo _other) {
  _other.copyTo(this);
  return this;
 }
  public KeyInfo.Builder<_B> copyOf(final KeyInfo.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends KeyInfo.Selector<KeyInfo.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static KeyInfo.Select _root() {
  return new KeyInfo.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, KeyInfo.Selector<TRoot, TParent>> keyName = null;
 private X509Data.Selector<TRoot, KeyInfo.Selector<TRoot, TParent>> x509Data = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.keyName!= null) {
   products.put("keyName", this.keyName.init());
  }
  if (this.x509Data!= null) {
   products.put("x509Data", this.x509Data.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, KeyInfo.Selector<TRoot, TParent>> keyName() {
  return ((this.keyName == null)?this.keyName = new com.kscs.util.jaxb.Selector<>(this._root, this, "keyName"):this.keyName);
 }
  public X509Data.Selector<TRoot, KeyInfo.Selector<TRoot, TParent>> x509Data() {
  return ((this.x509Data == null)?this.x509Data = new X509Data.Selector<>(this._root, this, "x509Data"):this.x509Data);
 }
  }
 }
