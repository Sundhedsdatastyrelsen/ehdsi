package dk.sdsd.ddv.dgws._2012._06;
import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import dk.sdsd.ddv.dgws._2010._08.OrgUsingID;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for WhiteListingHeader complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="WhiteListingHeader">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element ref="{http://www.sdsd.dk/dgws/2010/08}SystemOwnerName"/>
*         <element ref="{http://www.sdsd.dk/dgws/2010/08}SystemName"/>
*         <element ref="{http://www.sdsd.dk/dgws/2010/08}SystemVersion"/>
*         <element ref="{http://www.sdsd.dk/dgws/2010/08}OrgResponsibleName"/>
*         <element ref="{http://www.sdsd.dk/dgws/2010/08}OrgUsingName" minOccurs="0"/>
*         <element ref="{http://www.sdsd.dk/dgws/2010/08}OrgUsingID"/>
*         <element ref="{http://www.sdsd.dk/dgws/2010/08}RequestedRole"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WhiteListingHeader", propOrder = {
 "systemOwnerName",
 "systemName",
 "systemVersion",
 "orgResponsibleName",
 "orgUsingName",
 "orgUsingID",
 "requestedRole"
})
public class WhiteListingHeader {
  @XmlElement(name = "SystemOwnerName", namespace = "http://www.sdsd.dk/dgws/2010/08", required = true)
 protected String systemOwnerName;
 @XmlElement(name = "SystemName", namespace = "http://www.sdsd.dk/dgws/2010/08", required = true)
 protected String systemName;
 @XmlElement(name = "SystemVersion", namespace = "http://www.sdsd.dk/dgws/2010/08", required = true)
 protected String systemVersion;
 @XmlElement(name = "OrgResponsibleName", namespace = "http://www.sdsd.dk/dgws/2010/08", required = true)
 protected String orgResponsibleName;
 @XmlElement(name = "OrgUsingName", namespace = "http://www.sdsd.dk/dgws/2010/08")
 protected String orgUsingName;
 @XmlElement(name = "OrgUsingID", namespace = "http://www.sdsd.dk/dgws/2010/08", required = true)
 protected OrgUsingID orgUsingID;
 @XmlElement(name = "RequestedRole", namespace = "http://www.sdsd.dk/dgws/2010/08", required = true)
 protected String requestedRole;
  /**
 * Gets the value of the systemOwnerName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getSystemOwnerName() {
 return systemOwnerName;
 }
  /**
 * Sets the value of the systemOwnerName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setSystemOwnerName(String value) {
 this.systemOwnerName = value;
 }
  /**
 * Gets the value of the systemName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getSystemName() {
 return systemName;
 }
  /**
 * Sets the value of the systemName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setSystemName(String value) {
 this.systemName = value;
 }
  /**
 * Gets the value of the systemVersion property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getSystemVersion() {
 return systemVersion;
 }
  /**
 * Sets the value of the systemVersion property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setSystemVersion(String value) {
 this.systemVersion = value;
 }
  /**
 * Gets the value of the orgResponsibleName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getOrgResponsibleName() {
 return orgResponsibleName;
 }
  /**
 * Sets the value of the orgResponsibleName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setOrgResponsibleName(String value) {
 this.orgResponsibleName = value;
 }
  /**
 * Gets the value of the orgUsingName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getOrgUsingName() {
 return orgUsingName;
 }
  /**
 * Sets the value of the orgUsingName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setOrgUsingName(String value) {
 this.orgUsingName = value;
 }
  /**
 * Gets the value of the orgUsingID property.
 * 
 * @return
 *     possible object is
 *     {@link OrgUsingID }
 *     
 */
 public OrgUsingID getOrgUsingID() {
 return orgUsingID;
 }
  /**
 * Sets the value of the orgUsingID property.
 * 
 * @param value
 *     allowed object is
 *     {@link OrgUsingID }
 *     
 */
 public void setOrgUsingID(OrgUsingID value) {
 this.orgUsingID = value;
 }
  /**
 * Gets the value of the requestedRole property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getRequestedRole() {
 return requestedRole;
 }
  /**
 * Sets the value of the requestedRole property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setRequestedRole(String value) {
 this.requestedRole = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final WhiteListingHeader.Builder<_B> _other) {
 _other.systemOwnerName = this.systemOwnerName;
 _other.systemName = this.systemName;
 _other.systemVersion = this.systemVersion;
 _other.orgResponsibleName = this.orgResponsibleName;
 _other.orgUsingName = this.orgUsingName;
 _other.orgUsingID = ((this.orgUsingID == null)?null:this.orgUsingID.newCopyBuilder(_other));
 _other.requestedRole = this.requestedRole;
 }
  public<_B >WhiteListingHeader.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new WhiteListingHeader.Builder<_B>(_parentBuilder, this, true);
 }
  public WhiteListingHeader.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static WhiteListingHeader.Builder<Void> builder() {
 return new WhiteListingHeader.Builder<>(null, null, false);
 }
  public static<_B >WhiteListingHeader.Builder<_B> copyOf(final WhiteListingHeader _other) {
 final WhiteListingHeader.Builder<_B> _newBuilder = new WhiteListingHeader.Builder<>(null, null, false);
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
 public<_B >void copyTo(final WhiteListingHeader.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree systemOwnerNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemOwnerName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemOwnerNamePropertyTree!= null):((systemOwnerNamePropertyTree == null)||(!systemOwnerNamePropertyTree.isLeaf())))) {
  _other.systemOwnerName = this.systemOwnerName;
 }
 final PropertyTree systemNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemNamePropertyTree!= null):((systemNamePropertyTree == null)||(!systemNamePropertyTree.isLeaf())))) {
  _other.systemName = this.systemName;
 }
 final PropertyTree systemVersionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemVersion"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemVersionPropertyTree!= null):((systemVersionPropertyTree == null)||(!systemVersionPropertyTree.isLeaf())))) {
  _other.systemVersion = this.systemVersion;
 }
 final PropertyTree orgResponsibleNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("orgResponsibleName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(orgResponsibleNamePropertyTree!= null):((orgResponsibleNamePropertyTree == null)||(!orgResponsibleNamePropertyTree.isLeaf())))) {
  _other.orgResponsibleName = this.orgResponsibleName;
 }
 final PropertyTree orgUsingNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("orgUsingName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(orgUsingNamePropertyTree!= null):((orgUsingNamePropertyTree == null)||(!orgUsingNamePropertyTree.isLeaf())))) {
  _other.orgUsingName = this.orgUsingName;
 }
 final PropertyTree orgUsingIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("orgUsingID"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(orgUsingIDPropertyTree!= null):((orgUsingIDPropertyTree == null)||(!orgUsingIDPropertyTree.isLeaf())))) {
  _other.orgUsingID = ((this.orgUsingID == null)?null:this.orgUsingID.newCopyBuilder(_other, orgUsingIDPropertyTree, _propertyTreeUse));
 }
 final PropertyTree requestedRolePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("requestedRole"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(requestedRolePropertyTree!= null):((requestedRolePropertyTree == null)||(!requestedRolePropertyTree.isLeaf())))) {
  _other.requestedRole = this.requestedRole;
 }
 }
  public<_B >WhiteListingHeader.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new WhiteListingHeader.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public WhiteListingHeader.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >WhiteListingHeader.Builder<_B> copyOf(final WhiteListingHeader _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final WhiteListingHeader.Builder<_B> _newBuilder = new WhiteListingHeader.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static WhiteListingHeader.Builder<Void> copyExcept(final WhiteListingHeader _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static WhiteListingHeader.Builder<Void> copyOnly(final WhiteListingHeader _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final WhiteListingHeader _storedValue;
 private String systemOwnerName;
 private String systemName;
 private String systemVersion;
 private String orgResponsibleName;
 private String orgUsingName;
 private OrgUsingID.Builder<WhiteListingHeader.Builder<_B>> orgUsingID;
 private String requestedRole;
  public Builder(final _B _parentBuilder, final WhiteListingHeader _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.systemOwnerName = _other.systemOwnerName;
     this.systemName = _other.systemName;
     this.systemVersion = _other.systemVersion;
     this.orgResponsibleName = _other.orgResponsibleName;
     this.orgUsingName = _other.orgUsingName;
     this.orgUsingID = ((_other.orgUsingID == null)?null:_other.orgUsingID.newCopyBuilder(this));
     this.requestedRole = _other.requestedRole;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final WhiteListingHeader _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree systemOwnerNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemOwnerName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemOwnerNamePropertyTree!= null):((systemOwnerNamePropertyTree == null)||(!systemOwnerNamePropertyTree.isLeaf())))) {
        this.systemOwnerName = _other.systemOwnerName;
     }
     final PropertyTree systemNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemNamePropertyTree!= null):((systemNamePropertyTree == null)||(!systemNamePropertyTree.isLeaf())))) {
        this.systemName = _other.systemName;
     }
     final PropertyTree systemVersionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("systemVersion"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(systemVersionPropertyTree!= null):((systemVersionPropertyTree == null)||(!systemVersionPropertyTree.isLeaf())))) {
        this.systemVersion = _other.systemVersion;
     }
     final PropertyTree orgResponsibleNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("orgResponsibleName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(orgResponsibleNamePropertyTree!= null):((orgResponsibleNamePropertyTree == null)||(!orgResponsibleNamePropertyTree.isLeaf())))) {
        this.orgResponsibleName = _other.orgResponsibleName;
     }
     final PropertyTree orgUsingNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("orgUsingName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(orgUsingNamePropertyTree!= null):((orgUsingNamePropertyTree == null)||(!orgUsingNamePropertyTree.isLeaf())))) {
        this.orgUsingName = _other.orgUsingName;
     }
     final PropertyTree orgUsingIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("orgUsingID"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(orgUsingIDPropertyTree!= null):((orgUsingIDPropertyTree == null)||(!orgUsingIDPropertyTree.isLeaf())))) {
        this.orgUsingID = ((_other.orgUsingID == null)?null:_other.orgUsingID.newCopyBuilder(this, orgUsingIDPropertyTree, _propertyTreeUse));
     }
     final PropertyTree requestedRolePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("requestedRole"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(requestedRolePropertyTree!= null):((requestedRolePropertyTree == null)||(!requestedRolePropertyTree.isLeaf())))) {
        this.requestedRole = _other.requestedRole;
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
  protected<_P extends WhiteListingHeader >_P init(final _P _product) {
  _product.systemOwnerName = this.systemOwnerName;
  _product.systemName = this.systemName;
  _product.systemVersion = this.systemVersion;
  _product.orgResponsibleName = this.orgResponsibleName;
  _product.orgUsingName = this.orgUsingName;
  _product.orgUsingID = ((this.orgUsingID == null)?null:this.orgUsingID.build());
  _product.requestedRole = this.requestedRole;
  return _product;
 }
  /**
 * Sets the new value of "systemOwnerName" (any previous value will be replaced)
 * 
 * @param systemOwnerName
 *     New value of the "systemOwnerName" property.
 */
 public WhiteListingHeader.Builder<_B> withSystemOwnerName(final String systemOwnerName) {
  this.systemOwnerName = systemOwnerName;
  return this;
 }
  /**
 * Sets the new value of "systemName" (any previous value will be replaced)
 * 
 * @param systemName
 *     New value of the "systemName" property.
 */
 public WhiteListingHeader.Builder<_B> withSystemName(final String systemName) {
  this.systemName = systemName;
  return this;
 }
  /**
 * Sets the new value of "systemVersion" (any previous value will be replaced)
 * 
 * @param systemVersion
 *     New value of the "systemVersion" property.
 */
 public WhiteListingHeader.Builder<_B> withSystemVersion(final String systemVersion) {
  this.systemVersion = systemVersion;
  return this;
 }
  /**
 * Sets the new value of "orgResponsibleName" (any previous value will be replaced)
 * 
 * @param orgResponsibleName
 *     New value of the "orgResponsibleName" property.
 */
 public WhiteListingHeader.Builder<_B> withOrgResponsibleName(final String orgResponsibleName) {
  this.orgResponsibleName = orgResponsibleName;
  return this;
 }
  /**
 * Sets the new value of "orgUsingName" (any previous value will be replaced)
 * 
 * @param orgUsingName
 *     New value of the "orgUsingName" property.
 */
 public WhiteListingHeader.Builder<_B> withOrgUsingName(final String orgUsingName) {
  this.orgUsingName = orgUsingName;
  return this;
 }
  /**
 * Sets the new value of "orgUsingID" (any previous value will be replaced)
 * 
 * @param orgUsingID
 *     New value of the "orgUsingID" property.
 */
 public WhiteListingHeader.Builder<_B> withOrgUsingID(final OrgUsingID orgUsingID) {
  this.orgUsingID = ((orgUsingID == null)?null:new OrgUsingID.Builder<>(this, orgUsingID, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "orgUsingID" property.
 * Use {@link dk.sdsd.ddv.dgws._2010._08.OrgUsingID.Builder#end()} to return to the
 * current builder.
 * 
 * @return
 *     A new builder to build the value of the "orgUsingID" property.
 *     Use {@link dk.sdsd.ddv.dgws._2010._08.OrgUsingID.Builder#end()} to return to the
 *     current builder.
 */
 public OrgUsingID.Builder<? extends WhiteListingHeader.Builder<_B>> withOrgUsingID() {
  if (this.orgUsingID!= null) {
   return this.orgUsingID;
  }
  return this.orgUsingID = new OrgUsingID.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "requestedRole" (any previous value will be replaced)
 * 
 * @param requestedRole
 *     New value of the "requestedRole" property.
 */
 public WhiteListingHeader.Builder<_B> withRequestedRole(final String requestedRole) {
  this.requestedRole = requestedRole;
  return this;
 }
  @Override
 public WhiteListingHeader build() {
  if (_storedValue == null) {
   return this.init(new WhiteListingHeader());
  } else {
   return ((WhiteListingHeader) _storedValue);
  }
 }
  public WhiteListingHeader.Builder<_B> copyOf(final WhiteListingHeader _other) {
  _other.copyTo(this);
  return this;
 }
  public WhiteListingHeader.Builder<_B> copyOf(final WhiteListingHeader.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends WhiteListingHeader.Selector<WhiteListingHeader.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static WhiteListingHeader.Select _root() {
  return new WhiteListingHeader.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> systemOwnerName = null;
 private com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> systemName = null;
 private com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> systemVersion = null;
 private com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> orgResponsibleName = null;
 private com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> orgUsingName = null;
 private OrgUsingID.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> orgUsingID = null;
 private com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> requestedRole = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.systemOwnerName!= null) {
   products.put("systemOwnerName", this.systemOwnerName.init());
  }
  if (this.systemName!= null) {
   products.put("systemName", this.systemName.init());
  }
  if (this.systemVersion!= null) {
   products.put("systemVersion", this.systemVersion.init());
  }
  if (this.orgResponsibleName!= null) {
   products.put("orgResponsibleName", this.orgResponsibleName.init());
  }
  if (this.orgUsingName!= null) {
   products.put("orgUsingName", this.orgUsingName.init());
  }
  if (this.orgUsingID!= null) {
   products.put("orgUsingID", this.orgUsingID.init());
  }
  if (this.requestedRole!= null) {
   products.put("requestedRole", this.requestedRole.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> systemOwnerName() {
  return ((this.systemOwnerName == null)?this.systemOwnerName = new com.kscs.util.jaxb.Selector<>(this._root, this, "systemOwnerName"):this.systemOwnerName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> systemName() {
  return ((this.systemName == null)?this.systemName = new com.kscs.util.jaxb.Selector<>(this._root, this, "systemName"):this.systemName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> systemVersion() {
  return ((this.systemVersion == null)?this.systemVersion = new com.kscs.util.jaxb.Selector<>(this._root, this, "systemVersion"):this.systemVersion);
 }
  public com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> orgResponsibleName() {
  return ((this.orgResponsibleName == null)?this.orgResponsibleName = new com.kscs.util.jaxb.Selector<>(this._root, this, "orgResponsibleName"):this.orgResponsibleName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> orgUsingName() {
  return ((this.orgUsingName == null)?this.orgUsingName = new com.kscs.util.jaxb.Selector<>(this._root, this, "orgUsingName"):this.orgUsingName);
 }
  public OrgUsingID.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> orgUsingID() {
  return ((this.orgUsingID == null)?this.orgUsingID = new OrgUsingID.Selector<>(this._root, this, "orgUsingID"):this.orgUsingID);
 }
  public com.kscs.util.jaxb.Selector<TRoot, WhiteListingHeader.Selector<TRoot, TParent>> requestedRole() {
  return ((this.requestedRole == null)?this.requestedRole = new com.kscs.util.jaxb.Selector<>(this._root, this, "requestedRole"):this.requestedRole);
 }
  }
 }
