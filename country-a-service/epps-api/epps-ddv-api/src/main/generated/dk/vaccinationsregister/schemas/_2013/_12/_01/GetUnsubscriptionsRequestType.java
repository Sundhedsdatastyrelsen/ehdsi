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
* <p>Java class for GetUnsubscriptionsRequestType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="GetUnsubscriptionsRequestType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
*         <element name="IncludeStatus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetUnsubscriptionsRequestType", propOrder = {
 "personCivilRegistrationIdentifier",
 "includeStatus"
})
public class GetUnsubscriptionsRequestType {
  @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
 protected String personCivilRegistrationIdentifier;
 @XmlElement(name = "IncludeStatus")
 protected Boolean includeStatus;
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
 * Gets the value of the includeStatus property.
 * 
 * @return
 *     possible object is
 *     {@link Boolean }
 *     
 */
 public Boolean isIncludeStatus() {
 return includeStatus;
 }
  /**
 * Sets the value of the includeStatus property.
 * 
 * @param value
 *     allowed object is
 *     {@link Boolean }
 *     
 */
 public void setIncludeStatus(Boolean value) {
 this.includeStatus = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final GetUnsubscriptionsRequestType.Builder<_B> _other) {
 _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 _other.includeStatus = this.includeStatus;
 }
  public<_B >GetUnsubscriptionsRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new GetUnsubscriptionsRequestType.Builder<_B>(_parentBuilder, this, true);
 }
  public GetUnsubscriptionsRequestType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static GetUnsubscriptionsRequestType.Builder<Void> builder() {
 return new GetUnsubscriptionsRequestType.Builder<>(null, null, false);
 }
  public static<_B >GetUnsubscriptionsRequestType.Builder<_B> copyOf(final GetUnsubscriptionsRequestType _other) {
 final GetUnsubscriptionsRequestType.Builder<_B> _newBuilder = new GetUnsubscriptionsRequestType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final GetUnsubscriptionsRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
  _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 }
 final PropertyTree includeStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeStatus"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeStatusPropertyTree!= null):((includeStatusPropertyTree == null)||(!includeStatusPropertyTree.isLeaf())))) {
  _other.includeStatus = this.includeStatus;
 }
 }
  public<_B >GetUnsubscriptionsRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new GetUnsubscriptionsRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public GetUnsubscriptionsRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >GetUnsubscriptionsRequestType.Builder<_B> copyOf(final GetUnsubscriptionsRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final GetUnsubscriptionsRequestType.Builder<_B> _newBuilder = new GetUnsubscriptionsRequestType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static GetUnsubscriptionsRequestType.Builder<Void> copyExcept(final GetUnsubscriptionsRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static GetUnsubscriptionsRequestType.Builder<Void> copyOnly(final GetUnsubscriptionsRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final GetUnsubscriptionsRequestType _storedValue;
 private String personCivilRegistrationIdentifier;
 private Boolean includeStatus;
  public Builder(final _B _parentBuilder, final GetUnsubscriptionsRequestType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     this.includeStatus = _other.includeStatus;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final GetUnsubscriptionsRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     }
     final PropertyTree includeStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeStatus"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeStatusPropertyTree!= null):((includeStatusPropertyTree == null)||(!includeStatusPropertyTree.isLeaf())))) {
        this.includeStatus = _other.includeStatus;
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
  protected<_P extends GetUnsubscriptionsRequestType >_P init(final _P _product) {
  _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
  _product.includeStatus = this.includeStatus;
  return _product;
 }
  /**
 * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
 * will be replaced)
 * 
 * @param personCivilRegistrationIdentifier
 *     New value of the "personCivilRegistrationIdentifier" property.
 */
 public GetUnsubscriptionsRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
  this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "includeStatus" (any previous value will be replaced)
 * 
 * @param includeStatus
 *     New value of the "includeStatus" property.
 */
 public GetUnsubscriptionsRequestType.Builder<_B> withIncludeStatus(final Boolean includeStatus) {
  this.includeStatus = includeStatus;
  return this;
 }
  @Override
 public GetUnsubscriptionsRequestType build() {
  if (_storedValue == null) {
   return this.init(new GetUnsubscriptionsRequestType());
  } else {
   return ((GetUnsubscriptionsRequestType) _storedValue);
  }
 }
  public GetUnsubscriptionsRequestType.Builder<_B> copyOf(final GetUnsubscriptionsRequestType _other) {
  _other.copyTo(this);
  return this;
 }
  public GetUnsubscriptionsRequestType.Builder<_B> copyOf(final GetUnsubscriptionsRequestType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends GetUnsubscriptionsRequestType.Selector<GetUnsubscriptionsRequestType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static GetUnsubscriptionsRequestType.Select _root() {
  return new GetUnsubscriptionsRequestType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, GetUnsubscriptionsRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, GetUnsubscriptionsRequestType.Selector<TRoot, TParent>> includeStatus = null;
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
  if (this.includeStatus!= null) {
   products.put("includeStatus", this.includeStatus.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, GetUnsubscriptionsRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
  return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, GetUnsubscriptionsRequestType.Selector<TRoot, TParent>> includeStatus() {
  return ((this.includeStatus == null)?this.includeStatus = new com.kscs.util.jaxb.Selector<>(this._root, this, "includeStatus"):this.includeStatus);
 }
  }
 }
