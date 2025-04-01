package oasis.names.tc.saml._2_0.assertion;
import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
*         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}ConfirmationMethod"/>
*         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}SubjectConfirmationData"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "confirmationMethod",
 "subjectConfirmationData"
})
@XmlRootElement(name = "SubjectConfirmation")
public class SubjectConfirmation {
  @XmlElement(name = "ConfirmationMethod", required = true)
 protected String confirmationMethod;
 @XmlElement(name = "SubjectConfirmationData", required = true)
 protected SubjectConfirmationData subjectConfirmationData;
  /**
 * Gets the value of the confirmationMethod property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getConfirmationMethod() {
 return confirmationMethod;
 }
  /**
 * Sets the value of the confirmationMethod property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setConfirmationMethod(String value) {
 this.confirmationMethod = value;
 }
  /**
 * Gets the value of the subjectConfirmationData property.
 * 
 * @return
 *     possible object is
 *     {@link SubjectConfirmationData }
 *     
 */
 public SubjectConfirmationData getSubjectConfirmationData() {
 return subjectConfirmationData;
 }
  /**
 * Sets the value of the subjectConfirmationData property.
 * 
 * @param value
 *     allowed object is
 *     {@link SubjectConfirmationData }
 *     
 */
 public void setSubjectConfirmationData(SubjectConfirmationData value) {
 this.subjectConfirmationData = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final SubjectConfirmation.Builder<_B> _other) {
 _other.confirmationMethod = this.confirmationMethod;
 _other.subjectConfirmationData = ((this.subjectConfirmationData == null)?null:this.subjectConfirmationData.newCopyBuilder(_other));
 }
  public<_B >SubjectConfirmation.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new SubjectConfirmation.Builder<_B>(_parentBuilder, this, true);
 }
  public SubjectConfirmation.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static SubjectConfirmation.Builder<Void> builder() {
 return new SubjectConfirmation.Builder<>(null, null, false);
 }
  public static<_B >SubjectConfirmation.Builder<_B> copyOf(final SubjectConfirmation _other) {
 final SubjectConfirmation.Builder<_B> _newBuilder = new SubjectConfirmation.Builder<>(null, null, false);
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
 public<_B >void copyTo(final SubjectConfirmation.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree confirmationMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("confirmationMethod"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(confirmationMethodPropertyTree!= null):((confirmationMethodPropertyTree == null)||(!confirmationMethodPropertyTree.isLeaf())))) {
  _other.confirmationMethod = this.confirmationMethod;
 }
 final PropertyTree subjectConfirmationDataPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("subjectConfirmationData"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(subjectConfirmationDataPropertyTree!= null):((subjectConfirmationDataPropertyTree == null)||(!subjectConfirmationDataPropertyTree.isLeaf())))) {
  _other.subjectConfirmationData = ((this.subjectConfirmationData == null)?null:this.subjectConfirmationData.newCopyBuilder(_other, subjectConfirmationDataPropertyTree, _propertyTreeUse));
 }
 }
  public<_B >SubjectConfirmation.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new SubjectConfirmation.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public SubjectConfirmation.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >SubjectConfirmation.Builder<_B> copyOf(final SubjectConfirmation _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final SubjectConfirmation.Builder<_B> _newBuilder = new SubjectConfirmation.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static SubjectConfirmation.Builder<Void> copyExcept(final SubjectConfirmation _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static SubjectConfirmation.Builder<Void> copyOnly(final SubjectConfirmation _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final SubjectConfirmation _storedValue;
 private String confirmationMethod;
 private SubjectConfirmationData.Builder<SubjectConfirmation.Builder<_B>> subjectConfirmationData;
  public Builder(final _B _parentBuilder, final SubjectConfirmation _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.confirmationMethod = _other.confirmationMethod;
     this.subjectConfirmationData = ((_other.subjectConfirmationData == null)?null:_other.subjectConfirmationData.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final SubjectConfirmation _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree confirmationMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("confirmationMethod"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(confirmationMethodPropertyTree!= null):((confirmationMethodPropertyTree == null)||(!confirmationMethodPropertyTree.isLeaf())))) {
        this.confirmationMethod = _other.confirmationMethod;
     }
     final PropertyTree subjectConfirmationDataPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("subjectConfirmationData"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(subjectConfirmationDataPropertyTree!= null):((subjectConfirmationDataPropertyTree == null)||(!subjectConfirmationDataPropertyTree.isLeaf())))) {
        this.subjectConfirmationData = ((_other.subjectConfirmationData == null)?null:_other.subjectConfirmationData.newCopyBuilder(this, subjectConfirmationDataPropertyTree, _propertyTreeUse));
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
  protected<_P extends SubjectConfirmation >_P init(final _P _product) {
  _product.confirmationMethod = this.confirmationMethod;
  _product.subjectConfirmationData = ((this.subjectConfirmationData == null)?null:this.subjectConfirmationData.build());
  return _product;
 }
  /**
 * Sets the new value of "confirmationMethod" (any previous value will be replaced)
 * 
 * @param confirmationMethod
 *     New value of the "confirmationMethod" property.
 */
 public SubjectConfirmation.Builder<_B> withConfirmationMethod(final String confirmationMethod) {
  this.confirmationMethod = confirmationMethod;
  return this;
 }
  /**
 * Sets the new value of "subjectConfirmationData" (any previous value will be
 * replaced)
 * 
 * @param subjectConfirmationData
 *     New value of the "subjectConfirmationData" property.
 */
 public SubjectConfirmation.Builder<_B> withSubjectConfirmationData(final SubjectConfirmationData subjectConfirmationData) {
  this.subjectConfirmationData = ((subjectConfirmationData == null)?null:new SubjectConfirmationData.Builder<>(this, subjectConfirmationData, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "subjectConfirmationData" property.
 * Use {@link
 * oasis.names.tc.saml._2_0.assertion.SubjectConfirmationData.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "subjectConfirmationData" property.
 *     Use {@link
 *     oasis.names.tc.saml._2_0.assertion.SubjectConfirmationData.Builder#end()} to
 *     return to the current builder.
 */
 public SubjectConfirmationData.Builder<? extends SubjectConfirmation.Builder<_B>> withSubjectConfirmationData() {
  if (this.subjectConfirmationData!= null) {
   return this.subjectConfirmationData;
  }
  return this.subjectConfirmationData = new SubjectConfirmationData.Builder<>(this, null, false);
 }
  @Override
 public SubjectConfirmation build() {
  if (_storedValue == null) {
   return this.init(new SubjectConfirmation());
  } else {
   return ((SubjectConfirmation) _storedValue);
  }
 }
  public SubjectConfirmation.Builder<_B> copyOf(final SubjectConfirmation _other) {
  _other.copyTo(this);
  return this;
 }
  public SubjectConfirmation.Builder<_B> copyOf(final SubjectConfirmation.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends SubjectConfirmation.Selector<SubjectConfirmation.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static SubjectConfirmation.Select _root() {
  return new SubjectConfirmation.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, SubjectConfirmation.Selector<TRoot, TParent>> confirmationMethod = null;
 private SubjectConfirmationData.Selector<TRoot, SubjectConfirmation.Selector<TRoot, TParent>> subjectConfirmationData = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.confirmationMethod!= null) {
   products.put("confirmationMethod", this.confirmationMethod.init());
  }
  if (this.subjectConfirmationData!= null) {
   products.put("subjectConfirmationData", this.subjectConfirmationData.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, SubjectConfirmation.Selector<TRoot, TParent>> confirmationMethod() {
  return ((this.confirmationMethod == null)?this.confirmationMethod = new com.kscs.util.jaxb.Selector<>(this._root, this, "confirmationMethod"):this.confirmationMethod);
 }
  public SubjectConfirmationData.Selector<TRoot, SubjectConfirmation.Selector<TRoot, TParent>> subjectConfirmationData() {
  return ((this.subjectConfirmationData == null)?this.subjectConfirmationData = new SubjectConfirmationData.Selector<>(this._root, this, "subjectConfirmationData"):this.subjectConfirmationData);
 }
  }
 }
