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
import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.UsernameToken;
import org.w3._2000._09.xmldsig_.KeyInfo;
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
*         <element ref="{http://www.w3.org/2000/09/xmldsig#}KeyInfo"/>
*         <element ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd}UsernameToken"/>
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
 "keyInfo",
 "usernameToken"
})
@XmlRootElement(name = "SubjectConfirmationData")
public class SubjectConfirmationData {
  @XmlElement(name = "KeyInfo", namespace = "http://www.w3.org/2000/09/xmldsig#")
 protected KeyInfo keyInfo;
 @XmlElement(name = "UsernameToken", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
 protected UsernameToken usernameToken;
  /**
 * Gets the value of the keyInfo property.
 * 
 * @return
 *     possible object is
 *     {@link KeyInfo }
 *     
 */
 public KeyInfo getKeyInfo() {
 return keyInfo;
 }
  /**
 * Sets the value of the keyInfo property.
 * 
 * @param value
 *     allowed object is
 *     {@link KeyInfo }
 *     
 */
 public void setKeyInfo(KeyInfo value) {
 this.keyInfo = value;
 }
  /**
 * Gets the value of the usernameToken property.
 * 
 * @return
 *     possible object is
 *     {@link UsernameToken }
 *     
 */
 public UsernameToken getUsernameToken() {
 return usernameToken;
 }
  /**
 * Sets the value of the usernameToken property.
 * 
 * @param value
 *     allowed object is
 *     {@link UsernameToken }
 *     
 */
 public void setUsernameToken(UsernameToken value) {
 this.usernameToken = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final SubjectConfirmationData.Builder<_B> _other) {
 _other.keyInfo = ((this.keyInfo == null)?null:this.keyInfo.newCopyBuilder(_other));
 _other.usernameToken = ((this.usernameToken == null)?null:this.usernameToken.newCopyBuilder(_other));
 }
  public<_B >SubjectConfirmationData.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new SubjectConfirmationData.Builder<_B>(_parentBuilder, this, true);
 }
  public SubjectConfirmationData.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static SubjectConfirmationData.Builder<Void> builder() {
 return new SubjectConfirmationData.Builder<>(null, null, false);
 }
  public static<_B >SubjectConfirmationData.Builder<_B> copyOf(final SubjectConfirmationData _other) {
 final SubjectConfirmationData.Builder<_B> _newBuilder = new SubjectConfirmationData.Builder<>(null, null, false);
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
 public<_B >void copyTo(final SubjectConfirmationData.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree keyInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyInfo"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyInfoPropertyTree!= null):((keyInfoPropertyTree == null)||(!keyInfoPropertyTree.isLeaf())))) {
  _other.keyInfo = ((this.keyInfo == null)?null:this.keyInfo.newCopyBuilder(_other, keyInfoPropertyTree, _propertyTreeUse));
 }
 final PropertyTree usernameTokenPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("usernameToken"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(usernameTokenPropertyTree!= null):((usernameTokenPropertyTree == null)||(!usernameTokenPropertyTree.isLeaf())))) {
  _other.usernameToken = ((this.usernameToken == null)?null:this.usernameToken.newCopyBuilder(_other, usernameTokenPropertyTree, _propertyTreeUse));
 }
 }
  public<_B >SubjectConfirmationData.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new SubjectConfirmationData.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public SubjectConfirmationData.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >SubjectConfirmationData.Builder<_B> copyOf(final SubjectConfirmationData _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final SubjectConfirmationData.Builder<_B> _newBuilder = new SubjectConfirmationData.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static SubjectConfirmationData.Builder<Void> copyExcept(final SubjectConfirmationData _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static SubjectConfirmationData.Builder<Void> copyOnly(final SubjectConfirmationData _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final SubjectConfirmationData _storedValue;
 private KeyInfo.Builder<SubjectConfirmationData.Builder<_B>> keyInfo;
 private UsernameToken.Builder<SubjectConfirmationData.Builder<_B>> usernameToken;
  public Builder(final _B _parentBuilder, final SubjectConfirmationData _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.keyInfo = ((_other.keyInfo == null)?null:_other.keyInfo.newCopyBuilder(this));
     this.usernameToken = ((_other.usernameToken == null)?null:_other.usernameToken.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final SubjectConfirmationData _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree keyInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("keyInfo"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(keyInfoPropertyTree!= null):((keyInfoPropertyTree == null)||(!keyInfoPropertyTree.isLeaf())))) {
        this.keyInfo = ((_other.keyInfo == null)?null:_other.keyInfo.newCopyBuilder(this, keyInfoPropertyTree, _propertyTreeUse));
     }
     final PropertyTree usernameTokenPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("usernameToken"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(usernameTokenPropertyTree!= null):((usernameTokenPropertyTree == null)||(!usernameTokenPropertyTree.isLeaf())))) {
        this.usernameToken = ((_other.usernameToken == null)?null:_other.usernameToken.newCopyBuilder(this, usernameTokenPropertyTree, _propertyTreeUse));
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
  protected<_P extends SubjectConfirmationData >_P init(final _P _product) {
  _product.keyInfo = ((this.keyInfo == null)?null:this.keyInfo.build());
  _product.usernameToken = ((this.usernameToken == null)?null:this.usernameToken.build());
  return _product;
 }
  /**
 * Sets the new value of "keyInfo" (any previous value will be replaced)
 * 
 * @param keyInfo
 *     New value of the "keyInfo" property.
 */
 public SubjectConfirmationData.Builder<_B> withKeyInfo(final KeyInfo keyInfo) {
  this.keyInfo = ((keyInfo == null)?null:new KeyInfo.Builder<>(this, keyInfo, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "keyInfo" property.
 * Use {@link org.w3._2000._09.xmldsig_.KeyInfo.Builder#end()} to return to the
 * current builder.
 * 
 * @return
 *     A new builder to build the value of the "keyInfo" property.
 *     Use {@link org.w3._2000._09.xmldsig_.KeyInfo.Builder#end()} to return to the
 *     current builder.
 */
 public KeyInfo.Builder<? extends SubjectConfirmationData.Builder<_B>> withKeyInfo() {
  if (this.keyInfo!= null) {
   return this.keyInfo;
  }
  return this.keyInfo = new KeyInfo.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "usernameToken" (any previous value will be replaced)
 * 
 * @param usernameToken
 *     New value of the "usernameToken" property.
 */
 public SubjectConfirmationData.Builder<_B> withUsernameToken(final UsernameToken usernameToken) {
  this.usernameToken = ((usernameToken == null)?null:new UsernameToken.Builder<>(this, usernameToken, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "usernameToken" property.
 * Use {@link
 * org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.UsernameToken.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "usernameToken" property.
 *     Use {@link
 *     org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.UsernameToken.Builder#end()}
 *     to return to the current builder.
 */
 public UsernameToken.Builder<? extends SubjectConfirmationData.Builder<_B>> withUsernameToken() {
  if (this.usernameToken!= null) {
   return this.usernameToken;
  }
  return this.usernameToken = new UsernameToken.Builder<>(this, null, false);
 }
  @Override
 public SubjectConfirmationData build() {
  if (_storedValue == null) {
   return this.init(new SubjectConfirmationData());
  } else {
   return ((SubjectConfirmationData) _storedValue);
  }
 }
  public SubjectConfirmationData.Builder<_B> copyOf(final SubjectConfirmationData _other) {
  _other.copyTo(this);
  return this;
 }
  public SubjectConfirmationData.Builder<_B> copyOf(final SubjectConfirmationData.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends SubjectConfirmationData.Selector<SubjectConfirmationData.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static SubjectConfirmationData.Select _root() {
  return new SubjectConfirmationData.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private KeyInfo.Selector<TRoot, SubjectConfirmationData.Selector<TRoot, TParent>> keyInfo = null;
 private UsernameToken.Selector<TRoot, SubjectConfirmationData.Selector<TRoot, TParent>> usernameToken = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.keyInfo!= null) {
   products.put("keyInfo", this.keyInfo.init());
  }
  if (this.usernameToken!= null) {
   products.put("usernameToken", this.usernameToken.init());
  }
  return products;
 }
  public KeyInfo.Selector<TRoot, SubjectConfirmationData.Selector<TRoot, TParent>> keyInfo() {
  return ((this.keyInfo == null)?this.keyInfo = new KeyInfo.Selector<>(this._root, this, "keyInfo"):this.keyInfo);
 }
  public UsernameToken.Selector<TRoot, SubjectConfirmationData.Selector<TRoot, TParent>> usernameToken() {
  return ((this.usernameToken == null)?this.usernameToken = new UsernameToken.Selector<>(this._root, this, "usernameToken"):this.usernameToken);
 }
  }
 }
