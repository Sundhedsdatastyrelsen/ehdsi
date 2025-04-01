package org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0;
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
*       <sequence>
*         <element ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd}Username"/>
*         <element ref="{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd}Password"/>
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
 "username",
 "password"
})
@XmlRootElement(name = "UsernameToken")
public class UsernameToken {
  @XmlElement(name = "Username", required = true)
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 @XmlSchemaType(name = "NCName")
 protected String username;
 @XmlElement(name = "Password", required = true)
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 @XmlSchemaType(name = "NCName")
 protected String password;
  /**
 * Gets the value of the username property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getUsername() {
 return username;
 }
  /**
 * Sets the value of the username property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setUsername(String value) {
 this.username = value;
 }
  /**
 * Gets the value of the password property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getPassword() {
 return password;
 }
  /**
 * Sets the value of the password property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setPassword(String value) {
 this.password = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final UsernameToken.Builder<_B> _other) {
 _other.username = this.username;
 _other.password = this.password;
 }
  public<_B >UsernameToken.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new UsernameToken.Builder<_B>(_parentBuilder, this, true);
 }
  public UsernameToken.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static UsernameToken.Builder<Void> builder() {
 return new UsernameToken.Builder<>(null, null, false);
 }
  public static<_B >UsernameToken.Builder<_B> copyOf(final UsernameToken _other) {
 final UsernameToken.Builder<_B> _newBuilder = new UsernameToken.Builder<>(null, null, false);
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
 public<_B >void copyTo(final UsernameToken.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree usernamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("username"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(usernamePropertyTree!= null):((usernamePropertyTree == null)||(!usernamePropertyTree.isLeaf())))) {
  _other.username = this.username;
 }
 final PropertyTree passwordPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("password"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(passwordPropertyTree!= null):((passwordPropertyTree == null)||(!passwordPropertyTree.isLeaf())))) {
  _other.password = this.password;
 }
 }
  public<_B >UsernameToken.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new UsernameToken.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public UsernameToken.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >UsernameToken.Builder<_B> copyOf(final UsernameToken _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final UsernameToken.Builder<_B> _newBuilder = new UsernameToken.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static UsernameToken.Builder<Void> copyExcept(final UsernameToken _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static UsernameToken.Builder<Void> copyOnly(final UsernameToken _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final UsernameToken _storedValue;
 private String username;
 private String password;
  public Builder(final _B _parentBuilder, final UsernameToken _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.username = _other.username;
     this.password = _other.password;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final UsernameToken _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree usernamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("username"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(usernamePropertyTree!= null):((usernamePropertyTree == null)||(!usernamePropertyTree.isLeaf())))) {
        this.username = _other.username;
     }
     final PropertyTree passwordPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("password"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(passwordPropertyTree!= null):((passwordPropertyTree == null)||(!passwordPropertyTree.isLeaf())))) {
        this.password = _other.password;
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
  protected<_P extends UsernameToken >_P init(final _P _product) {
  _product.username = this.username;
  _product.password = this.password;
  return _product;
 }
  /**
 * Sets the new value of "username" (any previous value will be replaced)
 * 
 * @param username
 *     New value of the "username" property.
 */
 public UsernameToken.Builder<_B> withUsername(final String username) {
  this.username = username;
  return this;
 }
  /**
 * Sets the new value of "password" (any previous value will be replaced)
 * 
 * @param password
 *     New value of the "password" property.
 */
 public UsernameToken.Builder<_B> withPassword(final String password) {
  this.password = password;
  return this;
 }
  @Override
 public UsernameToken build() {
  if (_storedValue == null) {
   return this.init(new UsernameToken());
  } else {
   return ((UsernameToken) _storedValue);
  }
 }
  public UsernameToken.Builder<_B> copyOf(final UsernameToken _other) {
  _other.copyTo(this);
  return this;
 }
  public UsernameToken.Builder<_B> copyOf(final UsernameToken.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends UsernameToken.Selector<UsernameToken.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static UsernameToken.Select _root() {
  return new UsernameToken.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, UsernameToken.Selector<TRoot, TParent>> username = null;
 private com.kscs.util.jaxb.Selector<TRoot, UsernameToken.Selector<TRoot, TParent>> password = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.username!= null) {
   products.put("username", this.username.init());
  }
  if (this.password!= null) {
   products.put("password", this.password.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, UsernameToken.Selector<TRoot, TParent>> username() {
  return ((this.username == null)?this.username = new com.kscs.util.jaxb.Selector<>(this._root, this, "username"):this.username);
 }
  public com.kscs.util.jaxb.Selector<TRoot, UsernameToken.Selector<TRoot, TParent>> password() {
  return ((this.password == null)?this.password = new com.kscs.util.jaxb.Selector<>(this._root, this, "password"):this.password);
 }
  }
 }
