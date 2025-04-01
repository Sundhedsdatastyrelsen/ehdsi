package dk.vaccinationsregister.schemas._2013._12._01;
import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for EffectuatedType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="EffectuatedType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="EffectuatedDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
*         <element name="EffectuatedByName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedByNameType" minOccurs="0"/>
*         <element name="AuthorisationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AuthorisationIdentifierType" minOccurs="0"/>
*         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType" minOccurs="0"/>
*         <element name="EffectuatedByOrganisationName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedByOrganisationNameType" minOccurs="0"/>
*         <element name="EffectuatedByOrganisationName2" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedByOrganisationName2Type" minOccurs="0"/>
*         <element name="EffectuatedByOrganisationType" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedByOrganisationTypeType" minOccurs="0"/>
*         <element name="EffectuatedByOrganisationNumber" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedByOrganisationNumberType" minOccurs="0"/>
*         <element name="EffectuatedInCountryCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedInCountryCodeType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EffectuatedType", propOrder = {
 "effectuatedDateTime",
 "effectuatedByName",
 "authorisationIdentifier",
 "personCivilRegistrationIdentifier",
 "effectuatedByOrganisationName",
 "effectuatedByOrganisationName2",
 "effectuatedByOrganisationType",
 "effectuatedByOrganisationNumber",
 "effectuatedInCountryCode"
})
public class EffectuatedType {
  @XmlElement(name = "EffectuatedDateTime", required = true)
 @XmlSchemaType(name = "dateTime")
 protected XMLGregorianCalendar effectuatedDateTime;
 @XmlElement(name = "EffectuatedByName")
 protected String effectuatedByName;
 @XmlElement(name = "AuthorisationIdentifier")
 protected String authorisationIdentifier;
 @XmlElement(name = "PersonCivilRegistrationIdentifier")
 protected String personCivilRegistrationIdentifier;
 @XmlElement(name = "EffectuatedByOrganisationName")
 protected String effectuatedByOrganisationName;
 @XmlElement(name = "EffectuatedByOrganisationName2")
 protected String effectuatedByOrganisationName2;
 @XmlElement(name = "EffectuatedByOrganisationType")
 protected String effectuatedByOrganisationType;
 @XmlElement(name = "EffectuatedByOrganisationNumber")
 protected String effectuatedByOrganisationNumber;
 @XmlElement(name = "EffectuatedInCountryCode")
 protected String effectuatedInCountryCode;
  /**
 * Gets the value of the effectuatedDateTime property.
 * 
 * @return
 *     possible object is
 *     {@link XMLGregorianCalendar }
 *     
 */
 public XMLGregorianCalendar getEffectuatedDateTime() {
 return effectuatedDateTime;
 }
  /**
 * Sets the value of the effectuatedDateTime property.
 * 
 * @param value
 *     allowed object is
 *     {@link XMLGregorianCalendar }
 *     
 */
 public void setEffectuatedDateTime(XMLGregorianCalendar value) {
 this.effectuatedDateTime = value;
 }
  /**
 * Gets the value of the effectuatedByName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getEffectuatedByName() {
 return effectuatedByName;
 }
  /**
 * Sets the value of the effectuatedByName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setEffectuatedByName(String value) {
 this.effectuatedByName = value;
 }
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
 * Gets the value of the effectuatedByOrganisationName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getEffectuatedByOrganisationName() {
 return effectuatedByOrganisationName;
 }
  /**
 * Sets the value of the effectuatedByOrganisationName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setEffectuatedByOrganisationName(String value) {
 this.effectuatedByOrganisationName = value;
 }
  /**
 * Gets the value of the effectuatedByOrganisationName2 property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getEffectuatedByOrganisationName2() {
 return effectuatedByOrganisationName2;
 }
  /**
 * Sets the value of the effectuatedByOrganisationName2 property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setEffectuatedByOrganisationName2(String value) {
 this.effectuatedByOrganisationName2 = value;
 }
  /**
 * Gets the value of the effectuatedByOrganisationType property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getEffectuatedByOrganisationType() {
 return effectuatedByOrganisationType;
 }
  /**
 * Sets the value of the effectuatedByOrganisationType property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setEffectuatedByOrganisationType(String value) {
 this.effectuatedByOrganisationType = value;
 }
  /**
 * Gets the value of the effectuatedByOrganisationNumber property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getEffectuatedByOrganisationNumber() {
 return effectuatedByOrganisationNumber;
 }
  /**
 * Sets the value of the effectuatedByOrganisationNumber property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setEffectuatedByOrganisationNumber(String value) {
 this.effectuatedByOrganisationNumber = value;
 }
  /**
 * Gets the value of the effectuatedInCountryCode property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getEffectuatedInCountryCode() {
 return effectuatedInCountryCode;
 }
  /**
 * Sets the value of the effectuatedInCountryCode property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setEffectuatedInCountryCode(String value) {
 this.effectuatedInCountryCode = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final EffectuatedType.Builder<_B> _other) {
 _other.effectuatedDateTime = ((this.effectuatedDateTime == null)?null:((XMLGregorianCalendar) this.effectuatedDateTime.clone()));
 _other.effectuatedByName = this.effectuatedByName;
 _other.authorisationIdentifier = this.authorisationIdentifier;
 _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 _other.effectuatedByOrganisationName = this.effectuatedByOrganisationName;
 _other.effectuatedByOrganisationName2 = this.effectuatedByOrganisationName2;
 _other.effectuatedByOrganisationType = this.effectuatedByOrganisationType;
 _other.effectuatedByOrganisationNumber = this.effectuatedByOrganisationNumber;
 _other.effectuatedInCountryCode = this.effectuatedInCountryCode;
 }
  public<_B >EffectuatedType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new EffectuatedType.Builder<_B>(_parentBuilder, this, true);
 }
  public EffectuatedType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static EffectuatedType.Builder<Void> builder() {
 return new EffectuatedType.Builder<>(null, null, false);
 }
  public static<_B >EffectuatedType.Builder<_B> copyOf(final EffectuatedType _other) {
 final EffectuatedType.Builder<_B> _newBuilder = new EffectuatedType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final EffectuatedType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree effectuatedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedDateTime"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedDateTimePropertyTree!= null):((effectuatedDateTimePropertyTree == null)||(!effectuatedDateTimePropertyTree.isLeaf())))) {
  _other.effectuatedDateTime = ((this.effectuatedDateTime == null)?null:((XMLGregorianCalendar) this.effectuatedDateTime.clone()));
 }
 final PropertyTree effectuatedByNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByNamePropertyTree!= null):((effectuatedByNamePropertyTree == null)||(!effectuatedByNamePropertyTree.isLeaf())))) {
  _other.effectuatedByName = this.effectuatedByName;
 }
 final PropertyTree authorisationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisationIdentifierPropertyTree!= null):((authorisationIdentifierPropertyTree == null)||(!authorisationIdentifierPropertyTree.isLeaf())))) {
  _other.authorisationIdentifier = this.authorisationIdentifier;
 }
 final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
  _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 }
 final PropertyTree effectuatedByOrganisationNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationNamePropertyTree!= null):((effectuatedByOrganisationNamePropertyTree == null)||(!effectuatedByOrganisationNamePropertyTree.isLeaf())))) {
  _other.effectuatedByOrganisationName = this.effectuatedByOrganisationName;
 }
 final PropertyTree effectuatedByOrganisationName2PropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationName2"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationName2PropertyTree!= null):((effectuatedByOrganisationName2PropertyTree == null)||(!effectuatedByOrganisationName2PropertyTree.isLeaf())))) {
  _other.effectuatedByOrganisationName2 = this.effectuatedByOrganisationName2;
 }
 final PropertyTree effectuatedByOrganisationTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationType"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationTypePropertyTree!= null):((effectuatedByOrganisationTypePropertyTree == null)||(!effectuatedByOrganisationTypePropertyTree.isLeaf())))) {
  _other.effectuatedByOrganisationType = this.effectuatedByOrganisationType;
 }
 final PropertyTree effectuatedByOrganisationNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationNumber"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationNumberPropertyTree!= null):((effectuatedByOrganisationNumberPropertyTree == null)||(!effectuatedByOrganisationNumberPropertyTree.isLeaf())))) {
  _other.effectuatedByOrganisationNumber = this.effectuatedByOrganisationNumber;
 }
 final PropertyTree effectuatedInCountryCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedInCountryCode"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedInCountryCodePropertyTree!= null):((effectuatedInCountryCodePropertyTree == null)||(!effectuatedInCountryCodePropertyTree.isLeaf())))) {
  _other.effectuatedInCountryCode = this.effectuatedInCountryCode;
 }
 }
  public<_B >EffectuatedType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new EffectuatedType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public EffectuatedType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >EffectuatedType.Builder<_B> copyOf(final EffectuatedType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final EffectuatedType.Builder<_B> _newBuilder = new EffectuatedType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static EffectuatedType.Builder<Void> copyExcept(final EffectuatedType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static EffectuatedType.Builder<Void> copyOnly(final EffectuatedType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final EffectuatedType _storedValue;
 private XMLGregorianCalendar effectuatedDateTime;
 private String effectuatedByName;
 private String authorisationIdentifier;
 private String personCivilRegistrationIdentifier;
 private String effectuatedByOrganisationName;
 private String effectuatedByOrganisationName2;
 private String effectuatedByOrganisationType;
 private String effectuatedByOrganisationNumber;
 private String effectuatedInCountryCode;
  public Builder(final _B _parentBuilder, final EffectuatedType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.effectuatedDateTime = ((_other.effectuatedDateTime == null)?null:((XMLGregorianCalendar) _other.effectuatedDateTime.clone()));
     this.effectuatedByName = _other.effectuatedByName;
     this.authorisationIdentifier = _other.authorisationIdentifier;
     this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     this.effectuatedByOrganisationName = _other.effectuatedByOrganisationName;
     this.effectuatedByOrganisationName2 = _other.effectuatedByOrganisationName2;
     this.effectuatedByOrganisationType = _other.effectuatedByOrganisationType;
     this.effectuatedByOrganisationNumber = _other.effectuatedByOrganisationNumber;
     this.effectuatedInCountryCode = _other.effectuatedInCountryCode;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final EffectuatedType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree effectuatedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedDateTime"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedDateTimePropertyTree!= null):((effectuatedDateTimePropertyTree == null)||(!effectuatedDateTimePropertyTree.isLeaf())))) {
        this.effectuatedDateTime = ((_other.effectuatedDateTime == null)?null:((XMLGregorianCalendar) _other.effectuatedDateTime.clone()));
     }
     final PropertyTree effectuatedByNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByNamePropertyTree!= null):((effectuatedByNamePropertyTree == null)||(!effectuatedByNamePropertyTree.isLeaf())))) {
        this.effectuatedByName = _other.effectuatedByName;
     }
     final PropertyTree authorisationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("authorisationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(authorisationIdentifierPropertyTree!= null):((authorisationIdentifierPropertyTree == null)||(!authorisationIdentifierPropertyTree.isLeaf())))) {
        this.authorisationIdentifier = _other.authorisationIdentifier;
     }
     final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     }
     final PropertyTree effectuatedByOrganisationNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationNamePropertyTree!= null):((effectuatedByOrganisationNamePropertyTree == null)||(!effectuatedByOrganisationNamePropertyTree.isLeaf())))) {
        this.effectuatedByOrganisationName = _other.effectuatedByOrganisationName;
     }
     final PropertyTree effectuatedByOrganisationName2PropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationName2"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationName2PropertyTree!= null):((effectuatedByOrganisationName2PropertyTree == null)||(!effectuatedByOrganisationName2PropertyTree.isLeaf())))) {
        this.effectuatedByOrganisationName2 = _other.effectuatedByOrganisationName2;
     }
     final PropertyTree effectuatedByOrganisationTypePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationType"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationTypePropertyTree!= null):((effectuatedByOrganisationTypePropertyTree == null)||(!effectuatedByOrganisationTypePropertyTree.isLeaf())))) {
        this.effectuatedByOrganisationType = _other.effectuatedByOrganisationType;
     }
     final PropertyTree effectuatedByOrganisationNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedByOrganisationNumber"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedByOrganisationNumberPropertyTree!= null):((effectuatedByOrganisationNumberPropertyTree == null)||(!effectuatedByOrganisationNumberPropertyTree.isLeaf())))) {
        this.effectuatedByOrganisationNumber = _other.effectuatedByOrganisationNumber;
     }
     final PropertyTree effectuatedInCountryCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedInCountryCode"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedInCountryCodePropertyTree!= null):((effectuatedInCountryCodePropertyTree == null)||(!effectuatedInCountryCodePropertyTree.isLeaf())))) {
        this.effectuatedInCountryCode = _other.effectuatedInCountryCode;
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
  protected<_P extends EffectuatedType >_P init(final _P _product) {
  _product.effectuatedDateTime = this.effectuatedDateTime;
  _product.effectuatedByName = this.effectuatedByName;
  _product.authorisationIdentifier = this.authorisationIdentifier;
  _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
  _product.effectuatedByOrganisationName = this.effectuatedByOrganisationName;
  _product.effectuatedByOrganisationName2 = this.effectuatedByOrganisationName2;
  _product.effectuatedByOrganisationType = this.effectuatedByOrganisationType;
  _product.effectuatedByOrganisationNumber = this.effectuatedByOrganisationNumber;
  _product.effectuatedInCountryCode = this.effectuatedInCountryCode;
  return _product;
 }
  /**
 * Sets the new value of "effectuatedDateTime" (any previous value will be
 * replaced)
 * 
 * @param effectuatedDateTime
 *     New value of the "effectuatedDateTime" property.
 */
 public EffectuatedType.Builder<_B> withEffectuatedDateTime(final XMLGregorianCalendar effectuatedDateTime) {
  this.effectuatedDateTime = effectuatedDateTime;
  return this;
 }
  /**
 * Sets the new value of "effectuatedByName" (any previous value will be replaced)
 * 
 * @param effectuatedByName
 *     New value of the "effectuatedByName" property.
 */
 public EffectuatedType.Builder<_B> withEffectuatedByName(final String effectuatedByName) {
  this.effectuatedByName = effectuatedByName;
  return this;
 }
  /**
 * Sets the new value of "authorisationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param authorisationIdentifier
 *     New value of the "authorisationIdentifier" property.
 */
 public EffectuatedType.Builder<_B> withAuthorisationIdentifier(final String authorisationIdentifier) {
  this.authorisationIdentifier = authorisationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
 * will be replaced)
 * 
 * @param personCivilRegistrationIdentifier
 *     New value of the "personCivilRegistrationIdentifier" property.
 */
 public EffectuatedType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
  this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "effectuatedByOrganisationName" (any previous value will
 * be replaced)
 * 
 * @param effectuatedByOrganisationName
 *     New value of the "effectuatedByOrganisationName" property.
 */
 public EffectuatedType.Builder<_B> withEffectuatedByOrganisationName(final String effectuatedByOrganisationName) {
  this.effectuatedByOrganisationName = effectuatedByOrganisationName;
  return this;
 }
  /**
 * Sets the new value of "effectuatedByOrganisationName2" (any previous value will
 * be replaced)
 * 
 * @param effectuatedByOrganisationName2
 *     New value of the "effectuatedByOrganisationName2" property.
 */
 public EffectuatedType.Builder<_B> withEffectuatedByOrganisationName2(final String effectuatedByOrganisationName2) {
  this.effectuatedByOrganisationName2 = effectuatedByOrganisationName2;
  return this;
 }
  /**
 * Sets the new value of "effectuatedByOrganisationType" (any previous value will
 * be replaced)
 * 
 * @param effectuatedByOrganisationType
 *     New value of the "effectuatedByOrganisationType" property.
 */
 public EffectuatedType.Builder<_B> withEffectuatedByOrganisationType(final String effectuatedByOrganisationType) {
  this.effectuatedByOrganisationType = effectuatedByOrganisationType;
  return this;
 }
  /**
 * Sets the new value of "effectuatedByOrganisationNumber" (any previous value will
 * be replaced)
 * 
 * @param effectuatedByOrganisationNumber
 *     New value of the "effectuatedByOrganisationNumber" property.
 */
 public EffectuatedType.Builder<_B> withEffectuatedByOrganisationNumber(final String effectuatedByOrganisationNumber) {
  this.effectuatedByOrganisationNumber = effectuatedByOrganisationNumber;
  return this;
 }
  /**
 * Sets the new value of "effectuatedInCountryCode" (any previous value will be
 * replaced)
 * 
 * @param effectuatedInCountryCode
 *     New value of the "effectuatedInCountryCode" property.
 */
 public EffectuatedType.Builder<_B> withEffectuatedInCountryCode(final String effectuatedInCountryCode) {
  this.effectuatedInCountryCode = effectuatedInCountryCode;
  return this;
 }
  @Override
 public EffectuatedType build() {
  if (_storedValue == null) {
   return this.init(new EffectuatedType());
  } else {
   return ((EffectuatedType) _storedValue);
  }
 }
  public EffectuatedType.Builder<_B> copyOf(final EffectuatedType _other) {
  _other.copyTo(this);
  return this;
 }
  public EffectuatedType.Builder<_B> copyOf(final EffectuatedType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends EffectuatedType.Selector<EffectuatedType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static EffectuatedType.Select _root() {
  return new EffectuatedType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedDateTime = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByName = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> authorisationIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByOrganisationName = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByOrganisationName2 = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByOrganisationType = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByOrganisationNumber = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedInCountryCode = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.effectuatedDateTime!= null) {
   products.put("effectuatedDateTime", this.effectuatedDateTime.init());
  }
  if (this.effectuatedByName!= null) {
   products.put("effectuatedByName", this.effectuatedByName.init());
  }
  if (this.authorisationIdentifier!= null) {
   products.put("authorisationIdentifier", this.authorisationIdentifier.init());
  }
  if (this.personCivilRegistrationIdentifier!= null) {
   products.put("personCivilRegistrationIdentifier", this.personCivilRegistrationIdentifier.init());
  }
  if (this.effectuatedByOrganisationName!= null) {
   products.put("effectuatedByOrganisationName", this.effectuatedByOrganisationName.init());
  }
  if (this.effectuatedByOrganisationName2 != null) {
   products.put("effectuatedByOrganisationName2", this.effectuatedByOrganisationName2 .init());
  }
  if (this.effectuatedByOrganisationType!= null) {
   products.put("effectuatedByOrganisationType", this.effectuatedByOrganisationType.init());
  }
  if (this.effectuatedByOrganisationNumber!= null) {
   products.put("effectuatedByOrganisationNumber", this.effectuatedByOrganisationNumber.init());
  }
  if (this.effectuatedInCountryCode!= null) {
   products.put("effectuatedInCountryCode", this.effectuatedInCountryCode.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedDateTime() {
  return ((this.effectuatedDateTime == null)?this.effectuatedDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedDateTime"):this.effectuatedDateTime);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByName() {
  return ((this.effectuatedByName == null)?this.effectuatedByName = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedByName"):this.effectuatedByName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> authorisationIdentifier() {
  return ((this.authorisationIdentifier == null)?this.authorisationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "authorisationIdentifier"):this.authorisationIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
  return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByOrganisationName() {
  return ((this.effectuatedByOrganisationName == null)?this.effectuatedByOrganisationName = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedByOrganisationName"):this.effectuatedByOrganisationName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByOrganisationName2() {
  return ((this.effectuatedByOrganisationName2 == null)?this.effectuatedByOrganisationName2 = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedByOrganisationName2"):this.effectuatedByOrganisationName2);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByOrganisationType() {
  return ((this.effectuatedByOrganisationType == null)?this.effectuatedByOrganisationType = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedByOrganisationType"):this.effectuatedByOrganisationType);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedByOrganisationNumber() {
  return ((this.effectuatedByOrganisationNumber == null)?this.effectuatedByOrganisationNumber = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedByOrganisationNumber"):this.effectuatedByOrganisationNumber);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedType.Selector<TRoot, TParent>> effectuatedInCountryCode() {
  return ((this.effectuatedInCountryCode == null)?this.effectuatedInCountryCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedInCountryCode"):this.effectuatedInCountryCode);
 }
  }
 }
