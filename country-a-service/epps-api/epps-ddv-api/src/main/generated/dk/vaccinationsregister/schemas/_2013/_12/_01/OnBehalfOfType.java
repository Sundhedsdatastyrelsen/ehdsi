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
* <p>Java class for OnBehalfOfType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="OnBehalfOfType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="AuthorisationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisationIdentifierType"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OnBehalfOfType", propOrder = {
 "authorisationIdentifier"
})
public class OnBehalfOfType {
  @XmlElement(name = "AuthorisationIdentifier", required = true)
 protected String authorisationIdentifier;
  /**
 * Gets the value of the authorisationIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getAuthorisationIdentifier() {
 return authorisationIdentifier;
 }
  /**
 * Sets the value of the authorisationIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setAuthorisationIdentifier(String value) {
 this.authorisationIdentifier = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final OnBehalfOfType.Builder<_B> _other) {
 _other.authorisationIdentifier = this.authorisationIdentifier;
 }
  public<_B >OnBehalfOfType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new OnBehalfOfType.Builder<_B>(_parentBuilder, this, true);
 }
  public OnBehalfOfType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static OnBehalfOfType.Builder<Void> builder() {
 return new OnBehalfOfType.Builder<>(null, null, false);
 }
  public static<_B >OnBehalfOfType.Builder<_B> copyOf(final OnBehalfOfType _other) {
 final OnBehalfOfType.Builder<_B> _newBuilder = new OnBehalfOfType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final OnBehalfOfType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree authorisationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisationIdentifierPropertyTree!= null):((authorisationIdentifierPropertyTree == null)||(!authorisationIdentifierPropertyTree.isLeaf())))) {
  _other.authorisationIdentifier = this.authorisationIdentifier;
 }
 }
  public<_B >OnBehalfOfType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new OnBehalfOfType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public OnBehalfOfType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >OnBehalfOfType.Builder<_B> copyOf(final OnBehalfOfType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final OnBehalfOfType.Builder<_B> _newBuilder = new OnBehalfOfType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static OnBehalfOfType.Builder<Void> copyExcept(final OnBehalfOfType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static OnBehalfOfType.Builder<Void> copyOnly(final OnBehalfOfType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final OnBehalfOfType _storedValue;
 private String authorisationIdentifier;
  public Builder(final _B _parentBuilder, final OnBehalfOfType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.authorisationIdentifier = _other.authorisationIdentifier;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final OnBehalfOfType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree authorisationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisationIdentifierPropertyTree!= null):((authorisationIdentifierPropertyTree == null)||(!authorisationIdentifierPropertyTree.isLeaf())))) {
        this.authorisationIdentifier = _other.authorisationIdentifier;
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
  protected<_P extends OnBehalfOfType >_P init(final _P _product) {
  _product.authorisationIdentifier = this.authorisationIdentifier;
  return _product;
 }
  /**
 * Sets the new value of "authorisationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param authorisationIdentifier
 *     New value of the "authorisationIdentifier" property.
 */
 public OnBehalfOfType.Builder<_B> withAuthorisationIdentifier(final String authorisationIdentifier) {
  this.authorisationIdentifier = authorisationIdentifier;
  return this;
 }
  @Override
 public OnBehalfOfType build() {
  if (_storedValue == null) {
   return this.init(new OnBehalfOfType());
  } else {
   return ((OnBehalfOfType) _storedValue);
  }
 }
  public OnBehalfOfType.Builder<_B> copyOf(final OnBehalfOfType _other) {
  _other.copyTo(this);
  return this;
 }
  public OnBehalfOfType.Builder<_B> copyOf(final OnBehalfOfType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends OnBehalfOfType.Selector<OnBehalfOfType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static OnBehalfOfType.Select _root() {
  return new OnBehalfOfType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, OnBehalfOfType.Selector<TRoot, TParent>> authorisationIdentifier = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.authorisationIdentifier!= null) {
   products.put("authorisationIdentifier", this.authorisationIdentifier.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, OnBehalfOfType.Selector<TRoot, TParent>> authorisationIdentifier() {
  return ((this.authorisationIdentifier == null)?this.authorisationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "authorisationIdentifier"):this.authorisationIdentifier);
 }
  }
 }
