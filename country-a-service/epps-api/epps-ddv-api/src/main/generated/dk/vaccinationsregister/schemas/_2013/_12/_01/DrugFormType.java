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
* <p>Java class for DrugFormType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="DrugFormType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="DrugFormCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugFormCodeType" minOccurs="0"/>
*         <element name="DrugFormText" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DrugFormTextType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrugFormType", propOrder = {
 "drugFormCode",
 "drugFormText"
})
public class DrugFormType {
  @XmlElement(name = "DrugFormCode")
 protected String drugFormCode;
 @XmlElement(name = "DrugFormText")
 protected String drugFormText;
  /**
 * Gets the value of the drugFormCode property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getDrugFormCode() {
 return drugFormCode;
 }
  /**
 * Sets the value of the drugFormCode property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setDrugFormCode(String value) {
 this.drugFormCode = value;
 }
  /**
 * Gets the value of the drugFormText property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getDrugFormText() {
 return drugFormText;
 }
  /**
 * Sets the value of the drugFormText property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setDrugFormText(String value) {
 this.drugFormText = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final DrugFormType.Builder<_B> _other) {
 _other.drugFormCode = this.drugFormCode;
 _other.drugFormText = this.drugFormText;
 }
  public<_B >DrugFormType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new DrugFormType.Builder<_B>(_parentBuilder, this, true);
 }
  public DrugFormType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static DrugFormType.Builder<Void> builder() {
 return new DrugFormType.Builder<>(null, null, false);
 }
  public static<_B >DrugFormType.Builder<_B> copyOf(final DrugFormType _other) {
 final DrugFormType.Builder<_B> _newBuilder = new DrugFormType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final DrugFormType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree drugFormCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugFormCode"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugFormCodePropertyTree!= null):((drugFormCodePropertyTree == null)||(!drugFormCodePropertyTree.isLeaf())))) {
  _other.drugFormCode = this.drugFormCode;
 }
 final PropertyTree drugFormTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugFormText"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugFormTextPropertyTree!= null):((drugFormTextPropertyTree == null)||(!drugFormTextPropertyTree.isLeaf())))) {
  _other.drugFormText = this.drugFormText;
 }
 }
  public<_B >DrugFormType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new DrugFormType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public DrugFormType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >DrugFormType.Builder<_B> copyOf(final DrugFormType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final DrugFormType.Builder<_B> _newBuilder = new DrugFormType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static DrugFormType.Builder<Void> copyExcept(final DrugFormType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static DrugFormType.Builder<Void> copyOnly(final DrugFormType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final DrugFormType _storedValue;
 private String drugFormCode;
 private String drugFormText;
  public Builder(final _B _parentBuilder, final DrugFormType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.drugFormCode = _other.drugFormCode;
     this.drugFormText = _other.drugFormText;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final DrugFormType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree drugFormCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugFormCode"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugFormCodePropertyTree!= null):((drugFormCodePropertyTree == null)||(!drugFormCodePropertyTree.isLeaf())))) {
        this.drugFormCode = _other.drugFormCode;
     }
     final PropertyTree drugFormTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("drugFormText"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(drugFormTextPropertyTree!= null):((drugFormTextPropertyTree == null)||(!drugFormTextPropertyTree.isLeaf())))) {
        this.drugFormText = _other.drugFormText;
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
  protected<_P extends DrugFormType >_P init(final _P _product) {
  _product.drugFormCode = this.drugFormCode;
  _product.drugFormText = this.drugFormText;
  return _product;
 }
  /**
 * Sets the new value of "drugFormCode" (any previous value will be replaced)
 * 
 * @param drugFormCode
 *     New value of the "drugFormCode" property.
 */
 public DrugFormType.Builder<_B> withDrugFormCode(final String drugFormCode) {
  this.drugFormCode = drugFormCode;
  return this;
 }
  /**
 * Sets the new value of "drugFormText" (any previous value will be replaced)
 * 
 * @param drugFormText
 *     New value of the "drugFormText" property.
 */
 public DrugFormType.Builder<_B> withDrugFormText(final String drugFormText) {
  this.drugFormText = drugFormText;
  return this;
 }
  @Override
 public DrugFormType build() {
  if (_storedValue == null) {
   return this.init(new DrugFormType());
  } else {
   return ((DrugFormType) _storedValue);
  }
 }
  public DrugFormType.Builder<_B> copyOf(final DrugFormType _other) {
  _other.copyTo(this);
  return this;
 }
  public DrugFormType.Builder<_B> copyOf(final DrugFormType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends DrugFormType.Selector<DrugFormType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static DrugFormType.Select _root() {
  return new DrugFormType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, DrugFormType.Selector<TRoot, TParent>> drugFormCode = null;
 private com.kscs.util.jaxb.Selector<TRoot, DrugFormType.Selector<TRoot, TParent>> drugFormText = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.drugFormCode!= null) {
   products.put("drugFormCode", this.drugFormCode.init());
  }
  if (this.drugFormText!= null) {
   products.put("drugFormText", this.drugFormText.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, DrugFormType.Selector<TRoot, TParent>> drugFormCode() {
  return ((this.drugFormCode == null)?this.drugFormCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugFormCode"):this.drugFormCode);
 }
  public com.kscs.util.jaxb.Selector<TRoot, DrugFormType.Selector<TRoot, TParent>> drugFormText() {
  return ((this.drugFormText == null)?this.drugFormText = new com.kscs.util.jaxb.Selector<>(this._root, this, "drugFormText"):this.drugFormText);
 }
  }
 }
