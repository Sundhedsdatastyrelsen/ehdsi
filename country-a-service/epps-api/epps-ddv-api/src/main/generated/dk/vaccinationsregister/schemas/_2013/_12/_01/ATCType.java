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
* <p>Java class for ATCType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="ATCType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="Code" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ATCCodeType" minOccurs="0"/>
*         <element name="Text" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ATCTextType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ATCType", propOrder = {
 "code",
 "text"
})
public class ATCType {
  @XmlElement(name = "Code")
 protected String code;
 @XmlElement(name = "Text")
 protected String text;
  /**
 * Gets the value of the code property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getCode() {
 return code;
 }
  /**
 * Sets the value of the code property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setCode(String value) {
 this.code = value;
 }
  /**
 * Gets the value of the text property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getText() {
 return text;
 }
  /**
 * Sets the value of the text property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setText(String value) {
 this.text = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final ATCType.Builder<_B> _other) {
 _other.code = this.code;
 _other.text = this.text;
 }
  public<_B >ATCType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new ATCType.Builder<_B>(_parentBuilder, this, true);
 }
  public ATCType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static ATCType.Builder<Void> builder() {
 return new ATCType.Builder<>(null, null, false);
 }
  public static<_B >ATCType.Builder<_B> copyOf(final ATCType _other) {
 final ATCType.Builder<_B> _newBuilder = new ATCType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final ATCType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree codePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("code"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(codePropertyTree!= null):((codePropertyTree == null)||(!codePropertyTree.isLeaf())))) {
  _other.code = this.code;
 }
 final PropertyTree textPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("text"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(textPropertyTree!= null):((textPropertyTree == null)||(!textPropertyTree.isLeaf())))) {
  _other.text = this.text;
 }
 }
  public<_B >ATCType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new ATCType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public ATCType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >ATCType.Builder<_B> copyOf(final ATCType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final ATCType.Builder<_B> _newBuilder = new ATCType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static ATCType.Builder<Void> copyExcept(final ATCType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static ATCType.Builder<Void> copyOnly(final ATCType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final ATCType _storedValue;
 private String code;
 private String text;
  public Builder(final _B _parentBuilder, final ATCType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.code = _other.code;
     this.text = _other.text;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final ATCType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree codePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("code"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(codePropertyTree!= null):((codePropertyTree == null)||(!codePropertyTree.isLeaf())))) {
        this.code = _other.code;
     }
     final PropertyTree textPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("text"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(textPropertyTree!= null):((textPropertyTree == null)||(!textPropertyTree.isLeaf())))) {
        this.text = _other.text;
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
  protected<_P extends ATCType >_P init(final _P _product) {
  _product.code = this.code;
  _product.text = this.text;
  return _product;
 }
  /**
 * Sets the new value of "code" (any previous value will be replaced)
 * 
 * @param code
 *     New value of the "code" property.
 */
 public ATCType.Builder<_B> withCode(final String code) {
  this.code = code;
  return this;
 }
  /**
 * Sets the new value of "text" (any previous value will be replaced)
 * 
 * @param text
 *     New value of the "text" property.
 */
 public ATCType.Builder<_B> withText(final String text) {
  this.text = text;
  return this;
 }
  @Override
 public ATCType build() {
  if (_storedValue == null) {
   return this.init(new ATCType());
  } else {
   return ((ATCType) _storedValue);
  }
 }
  public ATCType.Builder<_B> copyOf(final ATCType _other) {
  _other.copyTo(this);
  return this;
 }
  public ATCType.Builder<_B> copyOf(final ATCType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends ATCType.Selector<ATCType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static ATCType.Select _root() {
  return new ATCType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, ATCType.Selector<TRoot, TParent>> code = null;
 private com.kscs.util.jaxb.Selector<TRoot, ATCType.Selector<TRoot, TParent>> text = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.code!= null) {
   products.put("code", this.code.init());
  }
  if (this.text!= null) {
   products.put("text", this.text.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, ATCType.Selector<TRoot, TParent>> code() {
  return ((this.code == null)?this.code = new com.kscs.util.jaxb.Selector<>(this._root, this, "code"):this.code);
 }
  public com.kscs.util.jaxb.Selector<TRoot, ATCType.Selector<TRoot, TParent>> text() {
  return ((this.text == null)?this.text = new com.kscs.util.jaxb.Selector<>(this._root, this, "text"):this.text);
 }
  }
 }
