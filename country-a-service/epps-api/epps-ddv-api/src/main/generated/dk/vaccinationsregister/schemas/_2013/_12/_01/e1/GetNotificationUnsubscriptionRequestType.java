package dk.vaccinationsregister.schemas._2013._12._01.e1;
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
* <p>Java class for GetNotificationUnsubscriptionRequestType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="GetNotificationUnsubscriptionRequestType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
*         <element name="IncludeRecipients" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}IncludeRecipientsType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetNotificationUnsubscriptionRequestType", propOrder = {
 "personCivilRegistrationIdentifier",
 "includeRecipients"
})
public class GetNotificationUnsubscriptionRequestType {
  @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
 protected String personCivilRegistrationIdentifier;
 @XmlElement(name = "IncludeRecipients")
 protected IncludeRecipientsType includeRecipients;
  /**
 * Gets the value of the personCivilRegistrationIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getPersonCivilRegistrationIdentifier() {
 return personCivilRegistrationIdentifier;
 }
  /**
 * Sets the value of the personCivilRegistrationIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setPersonCivilRegistrationIdentifier(String value) {
 this.personCivilRegistrationIdentifier = value;
 }
  /**
 * Gets the value of the includeRecipients property.
 * 
 * @return
 *     possible object is
 *     {@link IncludeRecipientsType }
 *     
 */
 public IncludeRecipientsType getIncludeRecipients() {
 return includeRecipients;
 }
  /**
 * Sets the value of the includeRecipients property.
 * 
 * @param value
 *     allowed object is
 *     {@link IncludeRecipientsType }
 *     
 */
 public void setIncludeRecipients(IncludeRecipientsType value) {
 this.includeRecipients = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final GetNotificationUnsubscriptionRequestType.Builder<_B> _other) {
 _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 _other.includeRecipients = ((this.includeRecipients == null)?null:this.includeRecipients.newCopyBuilder(_other));
 }
  public<_B >GetNotificationUnsubscriptionRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new GetNotificationUnsubscriptionRequestType.Builder<_B>(_parentBuilder, this, true);
 }
  public GetNotificationUnsubscriptionRequestType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static GetNotificationUnsubscriptionRequestType.Builder<Void> builder() {
 return new GetNotificationUnsubscriptionRequestType.Builder<>(null, null, false);
 }
  public static<_B >GetNotificationUnsubscriptionRequestType.Builder<_B> copyOf(final GetNotificationUnsubscriptionRequestType _other) {
 final GetNotificationUnsubscriptionRequestType.Builder<_B> _newBuilder = new GetNotificationUnsubscriptionRequestType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final GetNotificationUnsubscriptionRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
  _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 }
 final PropertyTree includeRecipientsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeRecipients"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeRecipientsPropertyTree!= null):((includeRecipientsPropertyTree == null)||(!includeRecipientsPropertyTree.isLeaf())))) {
  _other.includeRecipients = ((this.includeRecipients == null)?null:this.includeRecipients.newCopyBuilder(_other, includeRecipientsPropertyTree, _propertyTreeUse));
 }
 }
  public<_B >GetNotificationUnsubscriptionRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new GetNotificationUnsubscriptionRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public GetNotificationUnsubscriptionRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >GetNotificationUnsubscriptionRequestType.Builder<_B> copyOf(final GetNotificationUnsubscriptionRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final GetNotificationUnsubscriptionRequestType.Builder<_B> _newBuilder = new GetNotificationUnsubscriptionRequestType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static GetNotificationUnsubscriptionRequestType.Builder<Void> copyExcept(final GetNotificationUnsubscriptionRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static GetNotificationUnsubscriptionRequestType.Builder<Void> copyOnly(final GetNotificationUnsubscriptionRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final GetNotificationUnsubscriptionRequestType _storedValue;
 private String personCivilRegistrationIdentifier;
 private IncludeRecipientsType.Builder<GetNotificationUnsubscriptionRequestType.Builder<_B>> includeRecipients;
  public Builder(final _B _parentBuilder, final GetNotificationUnsubscriptionRequestType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     this.includeRecipients = ((_other.includeRecipients == null)?null:_other.includeRecipients.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final GetNotificationUnsubscriptionRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     }
     final PropertyTree includeRecipientsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeRecipients"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeRecipientsPropertyTree!= null):((includeRecipientsPropertyTree == null)||(!includeRecipientsPropertyTree.isLeaf())))) {
        this.includeRecipients = ((_other.includeRecipients == null)?null:_other.includeRecipients.newCopyBuilder(this, includeRecipientsPropertyTree, _propertyTreeUse));
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
  protected<_P extends GetNotificationUnsubscriptionRequestType >_P init(final _P _product) {
  _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
  _product.includeRecipients = ((this.includeRecipients == null)?null:this.includeRecipients.build());
  return _product;
 }
  /**
 * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
 * will be replaced)
 * 
 * @param personCivilRegistrationIdentifier
 *     New value of the "personCivilRegistrationIdentifier" property.
 */
 public GetNotificationUnsubscriptionRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
  this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "includeRecipients" (any previous value will be replaced)
 * 
 * @param includeRecipients
 *     New value of the "includeRecipients" property.
 */
 public GetNotificationUnsubscriptionRequestType.Builder<_B> withIncludeRecipients(final IncludeRecipientsType includeRecipients) {
  this.includeRecipients = ((includeRecipients == null)?null:new IncludeRecipientsType.Builder<>(this, includeRecipients, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "includeRecipients" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.e1.IncludeRecipientsType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "includeRecipients" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.e1.IncludeRecipientsType.Builder#end()}
 *     to return to the current builder.
 */
 public IncludeRecipientsType.Builder<? extends GetNotificationUnsubscriptionRequestType.Builder<_B>> withIncludeRecipients() {
  if (this.includeRecipients!= null) {
   return this.includeRecipients;
  }
  return this.includeRecipients = new IncludeRecipientsType.Builder<>(this, null, false);
 }
  @Override
 public GetNotificationUnsubscriptionRequestType build() {
  if (_storedValue == null) {
   return this.init(new GetNotificationUnsubscriptionRequestType());
  } else {
   return ((GetNotificationUnsubscriptionRequestType) _storedValue);
  }
 }
  public GetNotificationUnsubscriptionRequestType.Builder<_B> copyOf(final GetNotificationUnsubscriptionRequestType _other) {
  _other.copyTo(this);
  return this;
 }
  public GetNotificationUnsubscriptionRequestType.Builder<_B> copyOf(final GetNotificationUnsubscriptionRequestType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends GetNotificationUnsubscriptionRequestType.Selector<GetNotificationUnsubscriptionRequestType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static GetNotificationUnsubscriptionRequestType.Select _root() {
  return new GetNotificationUnsubscriptionRequestType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, GetNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
 private IncludeRecipientsType.Selector<TRoot, GetNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> includeRecipients = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.personCivilRegistrationIdentifier!= null) {
   products.put("personCivilRegistrationIdentifier", this.personCivilRegistrationIdentifier.init());
  }
  if (this.includeRecipients!= null) {
   products.put("includeRecipients", this.includeRecipients.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, GetNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
  return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
 }
  public IncludeRecipientsType.Selector<TRoot, GetNotificationUnsubscriptionRequestType.Selector<TRoot, TParent>> includeRecipients() {
  return ((this.includeRecipients == null)?this.includeRecipients = new IncludeRecipientsType.Selector<>(this._root, this, "includeRecipients"):this.includeRecipients);
 }
  }
 }
