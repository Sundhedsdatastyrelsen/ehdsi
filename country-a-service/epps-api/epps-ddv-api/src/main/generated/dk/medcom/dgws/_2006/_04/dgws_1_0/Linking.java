package dk.medcom.dgws._2006._04.dgws_1_0;
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
*         <element ref="{http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd}FlowID" minOccurs="0"/>
*         <element ref="{http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd}MessageID"/>
*         <element ref="{http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd}InResponseToMessageID" minOccurs="0"/>
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
 "flowID",
 "messageID",
 "inResponseToMessageID"
})
@XmlRootElement(name = "Linking")
public class Linking {
  @XmlElement(name = "FlowID")
 protected String flowID;
 @XmlElement(name = "MessageID", required = true)
 protected String messageID;
 @XmlElement(name = "InResponseToMessageID")
 protected String inResponseToMessageID;
  /**
 * Gets the value of the flowID property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getFlowID() {
 return flowID;
 }
  /**
 * Sets the value of the flowID property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setFlowID(String value) {
 this.flowID = value;
 }
  /**
 * Gets the value of the messageID property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getMessageID() {
 return messageID;
 }
  /**
 * Sets the value of the messageID property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setMessageID(String value) {
 this.messageID = value;
 }
  /**
 * Gets the value of the inResponseToMessageID property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getInResponseToMessageID() {
 return inResponseToMessageID;
 }
  /**
 * Sets the value of the inResponseToMessageID property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setInResponseToMessageID(String value) {
 this.inResponseToMessageID = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final Linking.Builder<_B> _other) {
 _other.flowID = this.flowID;
 _other.messageID = this.messageID;
 _other.inResponseToMessageID = this.inResponseToMessageID;
 }
  public<_B >Linking.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new Linking.Builder<_B>(_parentBuilder, this, true);
 }
  public Linking.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static Linking.Builder<Void> builder() {
 return new Linking.Builder<>(null, null, false);
 }
  public static<_B >Linking.Builder<_B> copyOf(final Linking _other) {
 final Linking.Builder<_B> _newBuilder = new Linking.Builder<>(null, null, false);
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
 public<_B >void copyTo(final Linking.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree flowIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("flowID"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(flowIDPropertyTree!= null):((flowIDPropertyTree == null)||(!flowIDPropertyTree.isLeaf())))) {
  _other.flowID = this.flowID;
 }
 final PropertyTree messageIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("messageID"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(messageIDPropertyTree!= null):((messageIDPropertyTree == null)||(!messageIDPropertyTree.isLeaf())))) {
  _other.messageID = this.messageID;
 }
 final PropertyTree inResponseToMessageIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("inResponseToMessageID"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(inResponseToMessageIDPropertyTree!= null):((inResponseToMessageIDPropertyTree == null)||(!inResponseToMessageIDPropertyTree.isLeaf())))) {
  _other.inResponseToMessageID = this.inResponseToMessageID;
 }
 }
  public<_B >Linking.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new Linking.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public Linking.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >Linking.Builder<_B> copyOf(final Linking _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final Linking.Builder<_B> _newBuilder = new Linking.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static Linking.Builder<Void> copyExcept(final Linking _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static Linking.Builder<Void> copyOnly(final Linking _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final Linking _storedValue;
 private String flowID;
 private String messageID;
 private String inResponseToMessageID;
  public Builder(final _B _parentBuilder, final Linking _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.flowID = _other.flowID;
     this.messageID = _other.messageID;
     this.inResponseToMessageID = _other.inResponseToMessageID;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final Linking _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree flowIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("flowID"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(flowIDPropertyTree!= null):((flowIDPropertyTree == null)||(!flowIDPropertyTree.isLeaf())))) {
        this.flowID = _other.flowID;
     }
     final PropertyTree messageIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("messageID"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(messageIDPropertyTree!= null):((messageIDPropertyTree == null)||(!messageIDPropertyTree.isLeaf())))) {
        this.messageID = _other.messageID;
     }
     final PropertyTree inResponseToMessageIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("inResponseToMessageID"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(inResponseToMessageIDPropertyTree!= null):((inResponseToMessageIDPropertyTree == null)||(!inResponseToMessageIDPropertyTree.isLeaf())))) {
        this.inResponseToMessageID = _other.inResponseToMessageID;
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
  protected<_P extends Linking >_P init(final _P _product) {
  _product.flowID = this.flowID;
  _product.messageID = this.messageID;
  _product.inResponseToMessageID = this.inResponseToMessageID;
  return _product;
 }
  /**
 * Sets the new value of "flowID" (any previous value will be replaced)
 * 
 * @param flowID
 *     New value of the "flowID" property.
 */
 public Linking.Builder<_B> withFlowID(final String flowID) {
  this.flowID = flowID;
  return this;
 }
  /**
 * Sets the new value of "messageID" (any previous value will be replaced)
 * 
 * @param messageID
 *     New value of the "messageID" property.
 */
 public Linking.Builder<_B> withMessageID(final String messageID) {
  this.messageID = messageID;
  return this;
 }
  /**
 * Sets the new value of "inResponseToMessageID" (any previous value will be
 * replaced)
 * 
 * @param inResponseToMessageID
 *     New value of the "inResponseToMessageID" property.
 */
 public Linking.Builder<_B> withInResponseToMessageID(final String inResponseToMessageID) {
  this.inResponseToMessageID = inResponseToMessageID;
  return this;
 }
  @Override
 public Linking build() {
  if (_storedValue == null) {
   return this.init(new Linking());
  } else {
   return ((Linking) _storedValue);
  }
 }
  public Linking.Builder<_B> copyOf(final Linking _other) {
  _other.copyTo(this);
  return this;
 }
  public Linking.Builder<_B> copyOf(final Linking.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends Linking.Selector<Linking.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static Linking.Select _root() {
  return new Linking.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, Linking.Selector<TRoot, TParent>> flowID = null;
 private com.kscs.util.jaxb.Selector<TRoot, Linking.Selector<TRoot, TParent>> messageID = null;
 private com.kscs.util.jaxb.Selector<TRoot, Linking.Selector<TRoot, TParent>> inResponseToMessageID = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.flowID!= null) {
   products.put("flowID", this.flowID.init());
  }
  if (this.messageID!= null) {
   products.put("messageID", this.messageID.init());
  }
  if (this.inResponseToMessageID!= null) {
   products.put("inResponseToMessageID", this.inResponseToMessageID.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, Linking.Selector<TRoot, TParent>> flowID() {
  return ((this.flowID == null)?this.flowID = new com.kscs.util.jaxb.Selector<>(this._root, this, "flowID"):this.flowID);
 }
  public com.kscs.util.jaxb.Selector<TRoot, Linking.Selector<TRoot, TParent>> messageID() {
  return ((this.messageID == null)?this.messageID = new com.kscs.util.jaxb.Selector<>(this._root, this, "messageID"):this.messageID);
 }
  public com.kscs.util.jaxb.Selector<TRoot, Linking.Selector<TRoot, TParent>> inResponseToMessageID() {
  return ((this.inResponseToMessageID == null)?this.inResponseToMessageID = new com.kscs.util.jaxb.Selector<>(this._root, this, "inResponseToMessageID"):this.inResponseToMessageID);
 }
  }
 }
