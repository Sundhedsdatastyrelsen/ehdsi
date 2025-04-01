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
* <p>Java class for RequestVaccinationPassportHardCopyRequestType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="RequestVaccinationPassportHardCopyRequestType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
*         <element name="VaccinationPlanCategory" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanCategoryType"/>
*         <element name="RecipientAddress" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PostalAddressType" minOccurs="0"/>
*         <element name="IncludeVaccinations" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
*         <element name="IncludeRestitutions" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestVaccinationPassportHardCopyRequestType", propOrder = {
 "personCivilRegistrationIdentifier",
 "vaccinationPlanCategory",
 "recipientAddress",
 "includeVaccinations",
 "includeRestitutions"
})
public class RequestVaccinationPassportHardCopyRequestType {
  @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
 protected String personCivilRegistrationIdentifier;
 @XmlElement(name = "VaccinationPlanCategory", required = true)
 protected String vaccinationPlanCategory;
 @XmlElement(name = "RecipientAddress")
 protected PostalAddressType recipientAddress;
 @XmlElement(name = "IncludeVaccinations")
 protected Boolean includeVaccinations;
 @XmlElement(name = "IncludeRestitutions")
 protected Boolean includeRestitutions;
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
 * Gets the value of the vaccinationPlanCategory property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccinationPlanCategory() {
 return vaccinationPlanCategory;
 }
  /**
 * Sets the value of the vaccinationPlanCategory property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccinationPlanCategory(String value) {
 this.vaccinationPlanCategory = value;
 }
  /**
 * Gets the value of the recipientAddress property.
 * 
 * @return
 *     possible object is
 *     {@link PostalAddressType }
 *     
 */
 public PostalAddressType getRecipientAddress() {
 return recipientAddress;
 }
  /**
 * Sets the value of the recipientAddress property.
 * 
 * @param value
 *     allowed object is
 *     {@link PostalAddressType }
 *     
 */
 public void setRecipientAddress(PostalAddressType value) {
 this.recipientAddress = value;
 }
  /**
 * Gets the value of the includeVaccinations property.
 * 
 * @return
 *     possible object is
 *     {@link Boolean }
 *     
 */
 public Boolean isIncludeVaccinations() {
 return includeVaccinations;
 }
  /**
 * Sets the value of the includeVaccinations property.
 * 
 * @param value
 *     allowed object is
 *     {@link Boolean }
 *     
 */
 public void setIncludeVaccinations(Boolean value) {
 this.includeVaccinations = value;
 }
  /**
 * Gets the value of the includeRestitutions property.
 * 
 * @return
 *     possible object is
 *     {@link Boolean }
 *     
 */
 public Boolean isIncludeRestitutions() {
 return includeRestitutions;
 }
  /**
 * Sets the value of the includeRestitutions property.
 * 
 * @param value
 *     allowed object is
 *     {@link Boolean }
 *     
 */
 public void setIncludeRestitutions(Boolean value) {
 this.includeRestitutions = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final RequestVaccinationPassportHardCopyRequestType.Builder<_B> _other) {
 _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 _other.vaccinationPlanCategory = this.vaccinationPlanCategory;
 _other.recipientAddress = ((this.recipientAddress == null)?null:this.recipientAddress.newCopyBuilder(_other));
 _other.includeVaccinations = this.includeVaccinations;
 _other.includeRestitutions = this.includeRestitutions;
 }
  public<_B >RequestVaccinationPassportHardCopyRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new RequestVaccinationPassportHardCopyRequestType.Builder<_B>(_parentBuilder, this, true);
 }
  public RequestVaccinationPassportHardCopyRequestType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static RequestVaccinationPassportHardCopyRequestType.Builder<Void> builder() {
 return new RequestVaccinationPassportHardCopyRequestType.Builder<>(null, null, false);
 }
  public static<_B >RequestVaccinationPassportHardCopyRequestType.Builder<_B> copyOf(final RequestVaccinationPassportHardCopyRequestType _other) {
 final RequestVaccinationPassportHardCopyRequestType.Builder<_B> _newBuilder = new RequestVaccinationPassportHardCopyRequestType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final RequestVaccinationPassportHardCopyRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
  _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 }
 final PropertyTree vaccinationPlanCategoryPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanCategory"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanCategoryPropertyTree!= null):((vaccinationPlanCategoryPropertyTree == null)||(!vaccinationPlanCategoryPropertyTree.isLeaf())))) {
  _other.vaccinationPlanCategory = this.vaccinationPlanCategory;
 }
 final PropertyTree recipientAddressPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("recipientAddress"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(recipientAddressPropertyTree!= null):((recipientAddressPropertyTree == null)||(!recipientAddressPropertyTree.isLeaf())))) {
  _other.recipientAddress = ((this.recipientAddress == null)?null:this.recipientAddress.newCopyBuilder(_other, recipientAddressPropertyTree, _propertyTreeUse));
 }
 final PropertyTree includeVaccinationsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeVaccinations"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeVaccinationsPropertyTree!= null):((includeVaccinationsPropertyTree == null)||(!includeVaccinationsPropertyTree.isLeaf())))) {
  _other.includeVaccinations = this.includeVaccinations;
 }
 final PropertyTree includeRestitutionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeRestitutions"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeRestitutionsPropertyTree!= null):((includeRestitutionsPropertyTree == null)||(!includeRestitutionsPropertyTree.isLeaf())))) {
  _other.includeRestitutions = this.includeRestitutions;
 }
 }
  public<_B >RequestVaccinationPassportHardCopyRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new RequestVaccinationPassportHardCopyRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public RequestVaccinationPassportHardCopyRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >RequestVaccinationPassportHardCopyRequestType.Builder<_B> copyOf(final RequestVaccinationPassportHardCopyRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final RequestVaccinationPassportHardCopyRequestType.Builder<_B> _newBuilder = new RequestVaccinationPassportHardCopyRequestType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static RequestVaccinationPassportHardCopyRequestType.Builder<Void> copyExcept(final RequestVaccinationPassportHardCopyRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static RequestVaccinationPassportHardCopyRequestType.Builder<Void> copyOnly(final RequestVaccinationPassportHardCopyRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final RequestVaccinationPassportHardCopyRequestType _storedValue;
 private String personCivilRegistrationIdentifier;
 private String vaccinationPlanCategory;
 private PostalAddressType.Builder<RequestVaccinationPassportHardCopyRequestType.Builder<_B>> recipientAddress;
 private Boolean includeVaccinations;
 private Boolean includeRestitutions;
  public Builder(final _B _parentBuilder, final RequestVaccinationPassportHardCopyRequestType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     this.vaccinationPlanCategory = _other.vaccinationPlanCategory;
     this.recipientAddress = ((_other.recipientAddress == null)?null:_other.recipientAddress.newCopyBuilder(this));
     this.includeVaccinations = _other.includeVaccinations;
     this.includeRestitutions = _other.includeRestitutions;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final RequestVaccinationPassportHardCopyRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     }
     final PropertyTree vaccinationPlanCategoryPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanCategory"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanCategoryPropertyTree!= null):((vaccinationPlanCategoryPropertyTree == null)||(!vaccinationPlanCategoryPropertyTree.isLeaf())))) {
        this.vaccinationPlanCategory = _other.vaccinationPlanCategory;
     }
     final PropertyTree recipientAddressPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("recipientAddress"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(recipientAddressPropertyTree!= null):((recipientAddressPropertyTree == null)||(!recipientAddressPropertyTree.isLeaf())))) {
        this.recipientAddress = ((_other.recipientAddress == null)?null:_other.recipientAddress.newCopyBuilder(this, recipientAddressPropertyTree, _propertyTreeUse));
     }
     final PropertyTree includeVaccinationsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeVaccinations"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeVaccinationsPropertyTree!= null):((includeVaccinationsPropertyTree == null)||(!includeVaccinationsPropertyTree.isLeaf())))) {
        this.includeVaccinations = _other.includeVaccinations;
     }
     final PropertyTree includeRestitutionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("includeRestitutions"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(includeRestitutionsPropertyTree!= null):((includeRestitutionsPropertyTree == null)||(!includeRestitutionsPropertyTree.isLeaf())))) {
        this.includeRestitutions = _other.includeRestitutions;
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
  protected<_P extends RequestVaccinationPassportHardCopyRequestType >_P init(final _P _product) {
  _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
  _product.vaccinationPlanCategory = this.vaccinationPlanCategory;
  _product.recipientAddress = ((this.recipientAddress == null)?null:this.recipientAddress.build());
  _product.includeVaccinations = this.includeVaccinations;
  _product.includeRestitutions = this.includeRestitutions;
  return _product;
 }
  /**
 * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
 * will be replaced)
 * 
 * @param personCivilRegistrationIdentifier
 *     New value of the "personCivilRegistrationIdentifier" property.
 */
 public RequestVaccinationPassportHardCopyRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
  this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanCategory" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanCategory
 *     New value of the "vaccinationPlanCategory" property.
 */
 public RequestVaccinationPassportHardCopyRequestType.Builder<_B> withVaccinationPlanCategory(final String vaccinationPlanCategory) {
  this.vaccinationPlanCategory = vaccinationPlanCategory;
  return this;
 }
  /**
 * Sets the new value of "recipientAddress" (any previous value will be replaced)
 * 
 * @param recipientAddress
 *     New value of the "recipientAddress" property.
 */
 public RequestVaccinationPassportHardCopyRequestType.Builder<_B> withRecipientAddress(final PostalAddressType recipientAddress) {
  this.recipientAddress = ((recipientAddress == null)?null:new PostalAddressType.Builder<>(this, recipientAddress, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "recipientAddress" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.PostalAddressType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "recipientAddress" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.PostalAddressType.Builder#end()}
 *     to return to the current builder.
 */
 public PostalAddressType.Builder<? extends RequestVaccinationPassportHardCopyRequestType.Builder<_B>> withRecipientAddress() {
  if (this.recipientAddress!= null) {
   return this.recipientAddress;
  }
  return this.recipientAddress = new PostalAddressType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "includeVaccinations" (any previous value will be
 * replaced)
 * 
 * @param includeVaccinations
 *     New value of the "includeVaccinations" property.
 */
 public RequestVaccinationPassportHardCopyRequestType.Builder<_B> withIncludeVaccinations(final Boolean includeVaccinations) {
  this.includeVaccinations = includeVaccinations;
  return this;
 }
  /**
 * Sets the new value of "includeRestitutions" (any previous value will be
 * replaced)
 * 
 * @param includeRestitutions
 *     New value of the "includeRestitutions" property.
 */
 public RequestVaccinationPassportHardCopyRequestType.Builder<_B> withIncludeRestitutions(final Boolean includeRestitutions) {
  this.includeRestitutions = includeRestitutions;
  return this;
 }
  @Override
 public RequestVaccinationPassportHardCopyRequestType build() {
  if (_storedValue == null) {
   return this.init(new RequestVaccinationPassportHardCopyRequestType());
  } else {
   return ((RequestVaccinationPassportHardCopyRequestType) _storedValue);
  }
 }
  public RequestVaccinationPassportHardCopyRequestType.Builder<_B> copyOf(final RequestVaccinationPassportHardCopyRequestType _other) {
  _other.copyTo(this);
  return this;
 }
  public RequestVaccinationPassportHardCopyRequestType.Builder<_B> copyOf(final RequestVaccinationPassportHardCopyRequestType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends RequestVaccinationPassportHardCopyRequestType.Selector<RequestVaccinationPassportHardCopyRequestType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static RequestVaccinationPassportHardCopyRequestType.Select _root() {
  return new RequestVaccinationPassportHardCopyRequestType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> vaccinationPlanCategory = null;
 private PostalAddressType.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> recipientAddress = null;
 private com.kscs.util.jaxb.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> includeVaccinations = null;
 private com.kscs.util.jaxb.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> includeRestitutions = null;
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
  if (this.vaccinationPlanCategory!= null) {
   products.put("vaccinationPlanCategory", this.vaccinationPlanCategory.init());
  }
  if (this.recipientAddress!= null) {
   products.put("recipientAddress", this.recipientAddress.init());
  }
  if (this.includeVaccinations!= null) {
   products.put("includeVaccinations", this.includeVaccinations.init());
  }
  if (this.includeRestitutions!= null) {
   products.put("includeRestitutions", this.includeRestitutions.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
  return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> vaccinationPlanCategory() {
  return ((this.vaccinationPlanCategory == null)?this.vaccinationPlanCategory = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanCategory"):this.vaccinationPlanCategory);
 }
  public PostalAddressType.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> recipientAddress() {
  return ((this.recipientAddress == null)?this.recipientAddress = new PostalAddressType.Selector<>(this._root, this, "recipientAddress"):this.recipientAddress);
 }
  public com.kscs.util.jaxb.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> includeVaccinations() {
  return ((this.includeVaccinations == null)?this.includeVaccinations = new com.kscs.util.jaxb.Selector<>(this._root, this, "includeVaccinations"):this.includeVaccinations);
 }
  public com.kscs.util.jaxb.Selector<TRoot, RequestVaccinationPassportHardCopyRequestType.Selector<TRoot, TParent>> includeRestitutions() {
  return ((this.includeRestitutions == null)?this.includeRestitutions = new com.kscs.util.jaxb.Selector<>(this._root, this, "includeRestitutions"):this.includeRestitutions);
 }
  }
 }
