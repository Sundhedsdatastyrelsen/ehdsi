package org.w3._2000._09.xmldsig_;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
*         <element ref="{http://www.w3.org/2000/09/xmldsig#}CanonicalizationMethod"/>
*         <element ref="{http://www.w3.org/2000/09/xmldsig#}SignatureMethod"/>
*         <element ref="{http://www.w3.org/2000/09/xmldsig#}Reference" maxOccurs="unbounded"/>
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
 "canonicalizationMethod",
 "signatureMethod",
 "reference"
})
@XmlRootElement(name = "SignedInfo")
public class SignedInfo {
  @XmlElement(name = "CanonicalizationMethod", required = true)
 protected CanonicalizationMethod canonicalizationMethod;
 @XmlElement(name = "SignatureMethod", required = true)
 protected SignatureMethod signatureMethod;
 @XmlElement(name = "Reference", required = true)
 protected List<Reference> reference;
  /**
 * Gets the value of the canonicalizationMethod property.
 * 
 * @return
 *     possible object is
 *     {@link CanonicalizationMethod }
 *     
 */
 public CanonicalizationMethod getCanonicalizationMethod() {
 return canonicalizationMethod;
 }
  /**
 * Sets the value of the canonicalizationMethod property.
 * 
 * @param value
 *     allowed object is
 *     {@link CanonicalizationMethod }
 *     
 */
 public void setCanonicalizationMethod(CanonicalizationMethod value) {
 this.canonicalizationMethod = value;
 }
  /**
 * Gets the value of the signatureMethod property.
 * 
 * @return
 *     possible object is
 *     {@link SignatureMethod }
 *     
 */
 public SignatureMethod getSignatureMethod() {
 return signatureMethod;
 }
  /**
 * Sets the value of the signatureMethod property.
 * 
 * @param value
 *     allowed object is
 *     {@link SignatureMethod }
 *     
 */
 public void setSignatureMethod(SignatureMethod value) {
 this.signatureMethod = value;
 }
  /**
 * Gets the value of the reference property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the reference property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getReference().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link Reference }
 * 
 * 
 * @return
 *     The value of the reference property.
 */
 public List<Reference> getReference() {
 if (reference == null) {
  reference = new ArrayList<>();
 }
 return this.reference;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final SignedInfo.Builder<_B> _other) {
 _other.canonicalizationMethod = ((this.canonicalizationMethod == null)?null:this.canonicalizationMethod.newCopyBuilder(_other));
 _other.signatureMethod = ((this.signatureMethod == null)?null:this.signatureMethod.newCopyBuilder(_other));
 if (this.reference == null) {
  _other.reference = null;
 } else {
  _other.reference = new ArrayList<>();
  for (Reference _item: this.reference) {
   _other.reference.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >SignedInfo.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new SignedInfo.Builder<_B>(_parentBuilder, this, true);
 }
  public SignedInfo.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static SignedInfo.Builder<Void> builder() {
 return new SignedInfo.Builder<>(null, null, false);
 }
  public static<_B >SignedInfo.Builder<_B> copyOf(final SignedInfo _other) {
 final SignedInfo.Builder<_B> _newBuilder = new SignedInfo.Builder<>(null, null, false);
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
 public<_B >void copyTo(final SignedInfo.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree canonicalizationMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("canonicalizationMethod"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(canonicalizationMethodPropertyTree!= null):((canonicalizationMethodPropertyTree == null)||(!canonicalizationMethodPropertyTree.isLeaf())))) {
  _other.canonicalizationMethod = ((this.canonicalizationMethod == null)?null:this.canonicalizationMethod.newCopyBuilder(_other, canonicalizationMethodPropertyTree, _propertyTreeUse));
 }
 final PropertyTree signatureMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signatureMethod"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signatureMethodPropertyTree!= null):((signatureMethodPropertyTree == null)||(!signatureMethodPropertyTree.isLeaf())))) {
  _other.signatureMethod = ((this.signatureMethod == null)?null:this.signatureMethod.newCopyBuilder(_other, signatureMethodPropertyTree, _propertyTreeUse));
 }
 final PropertyTree referencePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reference"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(referencePropertyTree!= null):((referencePropertyTree == null)||(!referencePropertyTree.isLeaf())))) {
  if (this.reference == null) {
   _other.reference = null;
  } else {
   _other.reference = new ArrayList<>();
   for (Reference _item: this.reference) {
     _other.reference.add(((_item == null)?null:_item.newCopyBuilder(_other, referencePropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >SignedInfo.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new SignedInfo.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public SignedInfo.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >SignedInfo.Builder<_B> copyOf(final SignedInfo _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final SignedInfo.Builder<_B> _newBuilder = new SignedInfo.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static SignedInfo.Builder<Void> copyExcept(final SignedInfo _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static SignedInfo.Builder<Void> copyOnly(final SignedInfo _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final SignedInfo _storedValue;
 private CanonicalizationMethod.Builder<SignedInfo.Builder<_B>> canonicalizationMethod;
 private SignatureMethod.Builder<SignedInfo.Builder<_B>> signatureMethod;
 private List<Reference.Builder<SignedInfo.Builder<_B>>> reference;
  public Builder(final _B _parentBuilder, final SignedInfo _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.canonicalizationMethod = ((_other.canonicalizationMethod == null)?null:_other.canonicalizationMethod.newCopyBuilder(this));
     this.signatureMethod = ((_other.signatureMethod == null)?null:_other.signatureMethod.newCopyBuilder(this));
     if (_other.reference == null) {
        this.reference = null;
     } else {
        this.reference = new ArrayList<>();
        for (Reference _item: _other.reference) {
             this.reference.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final SignedInfo _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree canonicalizationMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("canonicalizationMethod"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(canonicalizationMethodPropertyTree!= null):((canonicalizationMethodPropertyTree == null)||(!canonicalizationMethodPropertyTree.isLeaf())))) {
        this.canonicalizationMethod = ((_other.canonicalizationMethod == null)?null:_other.canonicalizationMethod.newCopyBuilder(this, canonicalizationMethodPropertyTree, _propertyTreeUse));
     }
     final PropertyTree signatureMethodPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signatureMethod"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signatureMethodPropertyTree!= null):((signatureMethodPropertyTree == null)||(!signatureMethodPropertyTree.isLeaf())))) {
        this.signatureMethod = ((_other.signatureMethod == null)?null:_other.signatureMethod.newCopyBuilder(this, signatureMethodPropertyTree, _propertyTreeUse));
     }
     final PropertyTree referencePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reference"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(referencePropertyTree!= null):((referencePropertyTree == null)||(!referencePropertyTree.isLeaf())))) {
        if (_other.reference == null) {
             this.reference = null;
        } else {
             this.reference = new ArrayList<>();
             for (Reference _item: _other.reference) {
                     this.reference.add(((_item == null)?null:_item.newCopyBuilder(this, referencePropertyTree, _propertyTreeUse)));
             }
        }
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
  protected<_P extends SignedInfo >_P init(final _P _product) {
  _product.canonicalizationMethod = ((this.canonicalizationMethod == null)?null:this.canonicalizationMethod.build());
  _product.signatureMethod = ((this.signatureMethod == null)?null:this.signatureMethod.build());
  if (this.reference!= null) {
   final List<Reference> reference = new ArrayList<>(this.reference.size());
   for (Reference.Builder<SignedInfo.Builder<_B>> _item: this.reference) {
     reference.add(_item.build());
   }
   _product.reference = reference;
  }
  return _product;
 }
  /**
 * Sets the new value of "canonicalizationMethod" (any previous value will be
 * replaced)
 * 
 * @param canonicalizationMethod
 *     New value of the "canonicalizationMethod" property.
 */
 public SignedInfo.Builder<_B> withCanonicalizationMethod(final CanonicalizationMethod canonicalizationMethod) {
  this.canonicalizationMethod = ((canonicalizationMethod == null)?null:new CanonicalizationMethod.Builder<>(this, canonicalizationMethod, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "canonicalizationMethod" property.
 * Use {@link org.w3._2000._09.xmldsig_.CanonicalizationMethod.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "canonicalizationMethod" property.
 *     Use {@link org.w3._2000._09.xmldsig_.CanonicalizationMethod.Builder#end()} to
 *     return to the current builder.
 */
 public CanonicalizationMethod.Builder<? extends SignedInfo.Builder<_B>> withCanonicalizationMethod() {
  if (this.canonicalizationMethod!= null) {
   return this.canonicalizationMethod;
  }
  return this.canonicalizationMethod = new CanonicalizationMethod.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "signatureMethod" (any previous value will be replaced)
 * 
 * @param signatureMethod
 *     New value of the "signatureMethod" property.
 */
 public SignedInfo.Builder<_B> withSignatureMethod(final SignatureMethod signatureMethod) {
  this.signatureMethod = ((signatureMethod == null)?null:new SignatureMethod.Builder<>(this, signatureMethod, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "signatureMethod" property.
 * Use {@link org.w3._2000._09.xmldsig_.SignatureMethod.Builder#end()} to return to
 * the current builder.
 * 
 * @return
 *     A new builder to build the value of the "signatureMethod" property.
 *     Use {@link org.w3._2000._09.xmldsig_.SignatureMethod.Builder#end()} to return to
 *     the current builder.
 */
 public SignatureMethod.Builder<? extends SignedInfo.Builder<_B>> withSignatureMethod() {
  if (this.signatureMethod!= null) {
   return this.signatureMethod;
  }
  return this.signatureMethod = new SignatureMethod.Builder<>(this, null, false);
 }
  /**
 * Adds the given items to the value of "reference"
 * 
 * @param reference
 *     Items to add to the value of the "reference" property
 */
 public SignedInfo.Builder<_B> addReference(final Iterable<? extends Reference> reference) {
  if (reference!= null) {
   if (this.reference == null) {
     this.reference = new ArrayList<>();
   }
   for (Reference _item: reference) {
     this.reference.add(new Reference.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "reference" (any previous value will be replaced)
 * 
 * @param reference
 *     New value of the "reference" property.
 */
 public SignedInfo.Builder<_B> withReference(final Iterable<? extends Reference> reference) {
  if (this.reference!= null) {
   this.reference.clear();
  }
  return addReference(reference);
 }
  /**
 * Adds the given items to the value of "reference"
 * 
 * @param reference
 *     Items to add to the value of the "reference" property
 */
 public SignedInfo.Builder<_B> addReference(Reference... reference) {
  addReference(Arrays.asList(reference));
  return this;
 }
  /**
 * Sets the new value of "reference" (any previous value will be replaced)
 * 
 * @param reference
 *     New value of the "reference" property.
 */
 public SignedInfo.Builder<_B> withReference(Reference... reference) {
  withReference(Arrays.asList(reference));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "Reference" property.
 * Use {@link org.w3._2000._09.xmldsig_.Reference.Builder#end()} to return to the
 * current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "Reference" property.
 *     Use {@link org.w3._2000._09.xmldsig_.Reference.Builder#end()} to return to the
 *     current builder.
 */
 public Reference.Builder<? extends SignedInfo.Builder<_B>> addReference() {
  if (this.reference == null) {
   this.reference = new ArrayList<>();
  }
  final Reference.Builder<SignedInfo.Builder<_B>> reference_Builder = new Reference.Builder<>(this, null, false);
  this.reference.add(reference_Builder);
  return reference_Builder;
 }
  @Override
 public SignedInfo build() {
  if (_storedValue == null) {
   return this.init(new SignedInfo());
  } else {
   return ((SignedInfo) _storedValue);
  }
 }
  public SignedInfo.Builder<_B> copyOf(final SignedInfo _other) {
  _other.copyTo(this);
  return this;
 }
  public SignedInfo.Builder<_B> copyOf(final SignedInfo.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends SignedInfo.Selector<SignedInfo.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static SignedInfo.Select _root() {
  return new SignedInfo.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private CanonicalizationMethod.Selector<TRoot, SignedInfo.Selector<TRoot, TParent>> canonicalizationMethod = null;
 private SignatureMethod.Selector<TRoot, SignedInfo.Selector<TRoot, TParent>> signatureMethod = null;
 private Reference.Selector<TRoot, SignedInfo.Selector<TRoot, TParent>> reference = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.canonicalizationMethod!= null) {
   products.put("canonicalizationMethod", this.canonicalizationMethod.init());
  }
  if (this.signatureMethod!= null) {
   products.put("signatureMethod", this.signatureMethod.init());
  }
  if (this.reference!= null) {
   products.put("reference", this.reference.init());
  }
  return products;
 }
  public CanonicalizationMethod.Selector<TRoot, SignedInfo.Selector<TRoot, TParent>> canonicalizationMethod() {
  return ((this.canonicalizationMethod == null)?this.canonicalizationMethod = new CanonicalizationMethod.Selector<>(this._root, this, "canonicalizationMethod"):this.canonicalizationMethod);
 }
  public SignatureMethod.Selector<TRoot, SignedInfo.Selector<TRoot, TParent>> signatureMethod() {
  return ((this.signatureMethod == null)?this.signatureMethod = new SignatureMethod.Selector<>(this._root, this, "signatureMethod"):this.signatureMethod);
 }
  public Reference.Selector<TRoot, SignedInfo.Selector<TRoot, TParent>> reference() {
  return ((this.reference == null)?this.reference = new Reference.Selector<>(this._root, this, "reference"):this.reference);
 }
  }
 }
