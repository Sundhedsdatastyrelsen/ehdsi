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
* <p>Java class for VaccinationType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="VaccinationType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccinationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationIdentifierType"/>
*         <element name="VaccinationVersionIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationVersionIdentifierType"/>
*         <element name="Modified" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType" minOccurs="0"/>
*         <element name="Created" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CreatedType"/>
*         <element name="Reviewed" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ReviewedType" minOccurs="0"/>
*         <element name="Reported" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ReportedType" minOccurs="0"/>
*         <element name="Vaccine" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccineType"/>
*         <element name="VaccinationCredibility" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationCredibilityEnumType"/>
*         <element name="SSIDrug" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SSIDrugType" minOccurs="0"/>
*         <element name="BatchNumber" type="{http://vaccinationsregister.dk/schemas/2013/12/01}BatchNumberType" minOccurs="0"/>
*         <element name="CoverageDuration" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CoverageDurationType" minOccurs="0"/>
*         <element name="DosageOptionIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DosageOptionIdentifierType" minOccurs="0"/>
*         <element name="DosageText" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DosageTextType" minOccurs="0"/>
*         <element name="EffectuatedDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
*         <element name="EffectuatedPlannedItem" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatedPlannedItemType" minOccurs="0"/>
*         <element name="ServiceCode" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ServiceCodeType" minOccurs="0"/>
*         <element name="ServiceCodeRemarkText" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ServiceCodeRemarkTextType" minOccurs="0"/>
*         <element name="ConfirmedByPrescriptionServer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
*         <element name="ActiveStatus" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
*         <element name="IsPrevious" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
*         <element name="NegativeConsentIndicator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}NegativeConsentIndicatorType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinationType", propOrder = {
 "vaccinationIdentifier",
 "vaccinationVersionIdentifier",
 "modified",
 "created",
 "reviewed",
 "reported",
 "vaccine",
 "vaccinationCredibility",
 "ssiDrug",
 "batchNumber",
 "coverageDuration",
 "dosageOptionIdentifier",
 "dosageText",
 "effectuatedDateTime",
 "effectuatedPlannedItem",
 "serviceCode",
 "serviceCodeRemarkText",
 "confirmedByPrescriptionServer",
 "activeStatus",
 "isPrevious",
 "negativeConsentIndicator"
})
public class VaccinationType {
  @XmlElement(name = "VaccinationIdentifier")
 protected long vaccinationIdentifier;
 @XmlElement(name = "VaccinationVersionIdentifier")
 protected long vaccinationVersionIdentifier;
 @XmlElement(name = "Modified")
 protected ModifiedType modified;
 @XmlElement(name = "Created", required = true)
 protected CreatedType created;
 @XmlElement(name = "Reviewed")
 protected ReviewedType reviewed;
 @XmlElement(name = "Reported")
 protected ReportedType reported;
 @XmlElement(name = "Vaccine", required = true)
 protected VaccineType vaccine;
 @XmlElement(name = "VaccinationCredibility", required = true)
 @XmlSchemaType(name = "string")
 protected VaccinationCredibilityEnumType vaccinationCredibility;
 @XmlElement(name = "SSIDrug")
 protected SSIDrugType ssiDrug;
 @XmlElement(name = "BatchNumber")
 protected String batchNumber;
 @XmlElement(name = "CoverageDuration")
 protected String coverageDuration;
 @XmlElement(name = "DosageOptionIdentifier")
 protected Long dosageOptionIdentifier;
 @XmlElement(name = "DosageText")
 protected String dosageText;
 @XmlElement(name = "EffectuatedDateTime")
 @XmlSchemaType(name = "dateTime")
 protected XMLGregorianCalendar effectuatedDateTime;
 @XmlElement(name = "EffectuatedPlannedItem")
 protected EffectuatedPlannedItemType effectuatedPlannedItem;
 @XmlElement(name = "ServiceCode")
 protected String serviceCode;
 @XmlElement(name = "ServiceCodeRemarkText")
 protected String serviceCodeRemarkText;
 @XmlElement(name = "ConfirmedByPrescriptionServer")
 protected boolean confirmedByPrescriptionServer;
 @XmlElement(name = "ActiveStatus")
 protected boolean activeStatus;
 @XmlElement(name = "IsPrevious")
 protected boolean isPrevious;
 @XmlElement(name = "NegativeConsentIndicator")
 protected Boolean negativeConsentIndicator;
  /**
 * Gets the value of the vaccinationIdentifier property.
 * 
 */
 public long getVaccinationIdentifier() {
 return vaccinationIdentifier;
 }
  /**
 * Sets the value of the vaccinationIdentifier property.
 * 
 */
 public void setVaccinationIdentifier(long value) {
 this.vaccinationIdentifier = value;
 }
  /**
 * Gets the value of the vaccinationVersionIdentifier property.
 * 
 */
 public long getVaccinationVersionIdentifier() {
 return vaccinationVersionIdentifier;
 }
  /**
 * Sets the value of the vaccinationVersionIdentifier property.
 * 
 */
 public void setVaccinationVersionIdentifier(long value) {
 this.vaccinationVersionIdentifier = value;
 }
  /**
 * Gets the value of the modified property.
 * 
 * @return
 *     possible object is
 *     {@link ModifiedType }
 *     
 */
 public ModifiedType getModified() {
 return modified;
 }
  /**
 * Sets the value of the modified property.
 * 
 * @param value
 *     allowed object is
 *     {@link ModifiedType }
 *     
 */
 public void setModified(ModifiedType value) {
 this.modified = value;
 }
  /**
 * Gets the value of the created property.
 * 
 * @return
 *     possible object is
 *     {@link CreatedType }
 *     
 */
 public CreatedType getCreated() {
 return created;
 }
  /**
 * Sets the value of the created property.
 * 
 * @param value
 *     allowed object is
 *     {@link CreatedType }
 *     
 */
 public void setCreated(CreatedType value) {
 this.created = value;
 }
  /**
 * Gets the value of the reviewed property.
 * 
 * @return
 *     possible object is
 *     {@link ReviewedType }
 *     
 */
 public ReviewedType getReviewed() {
 return reviewed;
 }
  /**
 * Sets the value of the reviewed property.
 * 
 * @param value
 *     allowed object is
 *     {@link ReviewedType }
 *     
 */
 public void setReviewed(ReviewedType value) {
 this.reviewed = value;
 }
  /**
 * Gets the value of the reported property.
 * 
 * @return
 *     possible object is
 *     {@link ReportedType }
 *     
 */
 public ReportedType getReported() {
 return reported;
 }
  /**
 * Sets the value of the reported property.
 * 
 * @param value
 *     allowed object is
 *     {@link ReportedType }
 *     
 */
 public void setReported(ReportedType value) {
 this.reported = value;
 }
  /**
 * Gets the value of the vaccine property.
 * 
 * @return
 *     possible object is
 *     {@link VaccineType }
 *     
 */
 public VaccineType getVaccine() {
 return vaccine;
 }
  /**
 * Sets the value of the vaccine property.
 * 
 * @param value
 *     allowed object is
 *     {@link VaccineType }
 *     
 */
 public void setVaccine(VaccineType value) {
 this.vaccine = value;
 }
  /**
 * Gets the value of the vaccinationCredibility property.
 * 
 * @return
 *     possible object is
 *     {@link VaccinationCredibilityEnumType }
 *     
 */
 public VaccinationCredibilityEnumType getVaccinationCredibility() {
 return vaccinationCredibility;
 }
  /**
 * Sets the value of the vaccinationCredibility property.
 * 
 * @param value
 *     allowed object is
 *     {@link VaccinationCredibilityEnumType }
 *     
 */
 public void setVaccinationCredibility(VaccinationCredibilityEnumType value) {
 this.vaccinationCredibility = value;
 }
  /**
 * Gets the value of the ssiDrug property.
 * 
 * @return
 *     possible object is
 *     {@link SSIDrugType }
 *     
 */
 public SSIDrugType getSSIDrug() {
 return ssiDrug;
 }
  /**
 * Sets the value of the ssiDrug property.
 * 
 * @param value
 *     allowed object is
 *     {@link SSIDrugType }
 *     
 */
 public void setSSIDrug(SSIDrugType value) {
 this.ssiDrug = value;
 }
  /**
 * Gets the value of the batchNumber property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getBatchNumber() {
 return batchNumber;
 }
  /**
 * Sets the value of the batchNumber property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setBatchNumber(String value) {
 this.batchNumber = value;
 }
  /**
 * Gets the value of the coverageDuration property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getCoverageDuration() {
 return coverageDuration;
 }
  /**
 * Sets the value of the coverageDuration property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setCoverageDuration(String value) {
 this.coverageDuration = value;
 }
  /**
 * Gets the value of the dosageOptionIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link Long }
 *     
 */
 public Long getDosageOptionIdentifier() {
 return dosageOptionIdentifier;
 }
  /**
 * Sets the value of the dosageOptionIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link Long }
 *     
 */
 public void setDosageOptionIdentifier(Long value) {
 this.dosageOptionIdentifier = value;
 }
  /**
 * Gets the value of the dosageText property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getDosageText() {
 return dosageText;
 }
  /**
 * Sets the value of the dosageText property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setDosageText(String value) {
 this.dosageText = value;
 }
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
 * Gets the value of the effectuatedPlannedItem property.
 * 
 * @return
 *     possible object is
 *     {@link EffectuatedPlannedItemType }
 *     
 */
 public EffectuatedPlannedItemType getEffectuatedPlannedItem() {
 return effectuatedPlannedItem;
 }
  /**
 * Sets the value of the effectuatedPlannedItem property.
 * 
 * @param value
 *     allowed object is
 *     {@link EffectuatedPlannedItemType }
 *     
 */
 public void setEffectuatedPlannedItem(EffectuatedPlannedItemType value) {
 this.effectuatedPlannedItem = value;
 }
  /**
 * Gets the value of the serviceCode property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getServiceCode() {
 return serviceCode;
 }
  /**
 * Sets the value of the serviceCode property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setServiceCode(String value) {
 this.serviceCode = value;
 }
  /**
 * Gets the value of the serviceCodeRemarkText property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getServiceCodeRemarkText() {
 return serviceCodeRemarkText;
 }
  /**
 * Sets the value of the serviceCodeRemarkText property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setServiceCodeRemarkText(String value) {
 this.serviceCodeRemarkText = value;
 }
  /**
 * Gets the value of the confirmedByPrescriptionServer property.
 * 
 */
 public boolean isConfirmedByPrescriptionServer() {
 return confirmedByPrescriptionServer;
 }
  /**
 * Sets the value of the confirmedByPrescriptionServer property.
 * 
 */
 public void setConfirmedByPrescriptionServer(boolean value) {
 this.confirmedByPrescriptionServer = value;
 }
  /**
 * Gets the value of the activeStatus property.
 * 
 */
 public boolean isActiveStatus() {
 return activeStatus;
 }
  /**
 * Sets the value of the activeStatus property.
 * 
 */
 public void setActiveStatus(boolean value) {
 this.activeStatus = value;
 }
  /**
 * Gets the value of the isPrevious property.
 * 
 */
 public boolean isIsPrevious() {
 return isPrevious;
 }
  /**
 * Sets the value of the isPrevious property.
 * 
 */
 public void setIsPrevious(boolean value) {
 this.isPrevious = value;
 }
  /**
 * Gets the value of the negativeConsentIndicator property.
 * 
 * @return
 *     possible object is
 *     {@link Boolean }
 *     
 */
 public Boolean isNegativeConsentIndicator() {
 return negativeConsentIndicator;
 }
  /**
 * Sets the value of the negativeConsentIndicator property.
 * 
 * @param value
 *     allowed object is
 *     {@link Boolean }
 *     
 */
 public void setNegativeConsentIndicator(Boolean value) {
 this.negativeConsentIndicator = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final VaccinationType.Builder<_B> _other) {
 _other.vaccinationIdentifier = this.vaccinationIdentifier;
 _other.vaccinationVersionIdentifier = this.vaccinationVersionIdentifier;
 _other.modified = ((this.modified == null)?null:this.modified.newCopyBuilder(_other));
 _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other));
 _other.reviewed = ((this.reviewed == null)?null:this.reviewed.newCopyBuilder(_other));
 _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other));
 _other.vaccine = ((this.vaccine == null)?null:this.vaccine.newCopyBuilder(_other));
 _other.vaccinationCredibility = this.vaccinationCredibility;
 _other.ssiDrug = ((this.ssiDrug == null)?null:this.ssiDrug.newCopyBuilder(_other));
 _other.batchNumber = this.batchNumber;
 _other.coverageDuration = this.coverageDuration;
 _other.dosageOptionIdentifier = this.dosageOptionIdentifier;
 _other.dosageText = this.dosageText;
 _other.effectuatedDateTime = ((this.effectuatedDateTime == null)?null:((XMLGregorianCalendar) this.effectuatedDateTime.clone()));
 _other.effectuatedPlannedItem = ((this.effectuatedPlannedItem == null)?null:this.effectuatedPlannedItem.newCopyBuilder(_other));
 _other.serviceCode = this.serviceCode;
 _other.serviceCodeRemarkText = this.serviceCodeRemarkText;
 _other.confirmedByPrescriptionServer = this.confirmedByPrescriptionServer;
 _other.activeStatus = this.activeStatus;
 _other.isPrevious = this.isPrevious;
 _other.negativeConsentIndicator = this.negativeConsentIndicator;
 }
  public<_B >VaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new VaccinationType.Builder<_B>(_parentBuilder, this, true);
 }
  public VaccinationType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static VaccinationType.Builder<Void> builder() {
 return new VaccinationType.Builder<>(null, null, false);
 }
  public static<_B >VaccinationType.Builder<_B> copyOf(final VaccinationType _other) {
 final VaccinationType.Builder<_B> _newBuilder = new VaccinationType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final VaccinationType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationIdentifierPropertyTree!= null):((vaccinationIdentifierPropertyTree == null)||(!vaccinationIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationIdentifier = this.vaccinationIdentifier;
 }
 final PropertyTree vaccinationVersionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationVersionIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationVersionIdentifierPropertyTree!= null):((vaccinationVersionIdentifierPropertyTree == null)||(!vaccinationVersionIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationVersionIdentifier = this.vaccinationVersionIdentifier;
 }
 final PropertyTree modifiedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modified"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedPropertyTree!= null):((modifiedPropertyTree == null)||(!modifiedPropertyTree.isLeaf())))) {
  _other.modified = ((this.modified == null)?null:this.modified.newCopyBuilder(_other, modifiedPropertyTree, _propertyTreeUse));
 }
 final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
  _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other, createdPropertyTree, _propertyTreeUse));
 }
 final PropertyTree reviewedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reviewed"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reviewedPropertyTree!= null):((reviewedPropertyTree == null)||(!reviewedPropertyTree.isLeaf())))) {
  _other.reviewed = ((this.reviewed == null)?null:this.reviewed.newCopyBuilder(_other, reviewedPropertyTree, _propertyTreeUse));
 }
 final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
  _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other, reportedPropertyTree, _propertyTreeUse));
 }
 final PropertyTree vaccinePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccine"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinePropertyTree!= null):((vaccinePropertyTree == null)||(!vaccinePropertyTree.isLeaf())))) {
  _other.vaccine = ((this.vaccine == null)?null:this.vaccine.newCopyBuilder(_other, vaccinePropertyTree, _propertyTreeUse));
 }
 final PropertyTree vaccinationCredibilityPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCredibility"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCredibilityPropertyTree!= null):((vaccinationCredibilityPropertyTree == null)||(!vaccinationCredibilityPropertyTree.isLeaf())))) {
  _other.vaccinationCredibility = this.vaccinationCredibility;
 }
 final PropertyTree ssiDrugPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("ssiDrug"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(ssiDrugPropertyTree!= null):((ssiDrugPropertyTree == null)||(!ssiDrugPropertyTree.isLeaf())))) {
  _other.ssiDrug = ((this.ssiDrug == null)?null:this.ssiDrug.newCopyBuilder(_other, ssiDrugPropertyTree, _propertyTreeUse));
 }
 final PropertyTree batchNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("batchNumber"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(batchNumberPropertyTree!= null):((batchNumberPropertyTree == null)||(!batchNumberPropertyTree.isLeaf())))) {
  _other.batchNumber = this.batchNumber;
 }
 final PropertyTree coverageDurationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("coverageDuration"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(coverageDurationPropertyTree!= null):((coverageDurationPropertyTree == null)||(!coverageDurationPropertyTree.isLeaf())))) {
  _other.coverageDuration = this.coverageDuration;
 }
 final PropertyTree dosageOptionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("dosageOptionIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(dosageOptionIdentifierPropertyTree!= null):((dosageOptionIdentifierPropertyTree == null)||(!dosageOptionIdentifierPropertyTree.isLeaf())))) {
  _other.dosageOptionIdentifier = this.dosageOptionIdentifier;
 }
 final PropertyTree dosageTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("dosageText"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(dosageTextPropertyTree!= null):((dosageTextPropertyTree == null)||(!dosageTextPropertyTree.isLeaf())))) {
  _other.dosageText = this.dosageText;
 }
 final PropertyTree effectuatedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedDateTime"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedDateTimePropertyTree!= null):((effectuatedDateTimePropertyTree == null)||(!effectuatedDateTimePropertyTree.isLeaf())))) {
  _other.effectuatedDateTime = ((this.effectuatedDateTime == null)?null:((XMLGregorianCalendar) this.effectuatedDateTime.clone()));
 }
 final PropertyTree effectuatedPlannedItemPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedPlannedItem"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedPlannedItemPropertyTree!= null):((effectuatedPlannedItemPropertyTree == null)||(!effectuatedPlannedItemPropertyTree.isLeaf())))) {
  _other.effectuatedPlannedItem = ((this.effectuatedPlannedItem == null)?null:this.effectuatedPlannedItem.newCopyBuilder(_other, effectuatedPlannedItemPropertyTree, _propertyTreeUse));
 }
 final PropertyTree serviceCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("serviceCode"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(serviceCodePropertyTree!= null):((serviceCodePropertyTree == null)||(!serviceCodePropertyTree.isLeaf())))) {
  _other.serviceCode = this.serviceCode;
 }
 final PropertyTree serviceCodeRemarkTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("serviceCodeRemarkText"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(serviceCodeRemarkTextPropertyTree!= null):((serviceCodeRemarkTextPropertyTree == null)||(!serviceCodeRemarkTextPropertyTree.isLeaf())))) {
  _other.serviceCodeRemarkText = this.serviceCodeRemarkText;
 }
 final PropertyTree confirmedByPrescriptionServerPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("confirmedByPrescriptionServer"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(confirmedByPrescriptionServerPropertyTree!= null):((confirmedByPrescriptionServerPropertyTree == null)||(!confirmedByPrescriptionServerPropertyTree.isLeaf())))) {
  _other.confirmedByPrescriptionServer = this.confirmedByPrescriptionServer;
 }
 final PropertyTree activeStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("activeStatus"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(activeStatusPropertyTree!= null):((activeStatusPropertyTree == null)||(!activeStatusPropertyTree.isLeaf())))) {
  _other.activeStatus = this.activeStatus;
 }
 final PropertyTree isPreviousPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("isPrevious"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(isPreviousPropertyTree!= null):((isPreviousPropertyTree == null)||(!isPreviousPropertyTree.isLeaf())))) {
  _other.isPrevious = this.isPrevious;
 }
 final PropertyTree negativeConsentIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentIndicator"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentIndicatorPropertyTree!= null):((negativeConsentIndicatorPropertyTree == null)||(!negativeConsentIndicatorPropertyTree.isLeaf())))) {
  _other.negativeConsentIndicator = this.negativeConsentIndicator;
 }
 }
  public<_B >VaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new VaccinationType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public VaccinationType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >VaccinationType.Builder<_B> copyOf(final VaccinationType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final VaccinationType.Builder<_B> _newBuilder = new VaccinationType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static VaccinationType.Builder<Void> copyExcept(final VaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static VaccinationType.Builder<Void> copyOnly(final VaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final VaccinationType _storedValue;
 private long vaccinationIdentifier;
 private long vaccinationVersionIdentifier;
 private ModifiedType.Builder<VaccinationType.Builder<_B>> modified;
 private CreatedType.Builder<VaccinationType.Builder<_B>> created;
 private ReviewedType.Builder<VaccinationType.Builder<_B>> reviewed;
 private ReportedType.Builder<VaccinationType.Builder<_B>> reported;
 private VaccineType.Builder<VaccinationType.Builder<_B>> vaccine;
 private VaccinationCredibilityEnumType vaccinationCredibility;
 private SSIDrugType.Builder<VaccinationType.Builder<_B>> ssiDrug;
 private String batchNumber;
 private String coverageDuration;
 private Long dosageOptionIdentifier;
 private String dosageText;
 private XMLGregorianCalendar effectuatedDateTime;
 private EffectuatedPlannedItemType.Builder<VaccinationType.Builder<_B>> effectuatedPlannedItem;
 private String serviceCode;
 private String serviceCodeRemarkText;
 private boolean confirmedByPrescriptionServer;
 private boolean activeStatus;
 private boolean isPrevious;
 private Boolean negativeConsentIndicator;
  public Builder(final _B _parentBuilder, final VaccinationType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccinationIdentifier = _other.vaccinationIdentifier;
     this.vaccinationVersionIdentifier = _other.vaccinationVersionIdentifier;
     this.modified = ((_other.modified == null)?null:_other.modified.newCopyBuilder(this));
     this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this));
     this.reviewed = ((_other.reviewed == null)?null:_other.reviewed.newCopyBuilder(this));
     this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this));
     this.vaccine = ((_other.vaccine == null)?null:_other.vaccine.newCopyBuilder(this));
     this.vaccinationCredibility = _other.vaccinationCredibility;
     this.ssiDrug = ((_other.ssiDrug == null)?null:_other.ssiDrug.newCopyBuilder(this));
     this.batchNumber = _other.batchNumber;
     this.coverageDuration = _other.coverageDuration;
     this.dosageOptionIdentifier = _other.dosageOptionIdentifier;
     this.dosageText = _other.dosageText;
     this.effectuatedDateTime = ((_other.effectuatedDateTime == null)?null:((XMLGregorianCalendar) _other.effectuatedDateTime.clone()));
     this.effectuatedPlannedItem = ((_other.effectuatedPlannedItem == null)?null:_other.effectuatedPlannedItem.newCopyBuilder(this));
     this.serviceCode = _other.serviceCode;
     this.serviceCodeRemarkText = _other.serviceCodeRemarkText;
     this.confirmedByPrescriptionServer = _other.confirmedByPrescriptionServer;
     this.activeStatus = _other.activeStatus;
     this.isPrevious = _other.isPrevious;
     this.negativeConsentIndicator = _other.negativeConsentIndicator;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final VaccinationType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationIdentifierPropertyTree!= null):((vaccinationIdentifierPropertyTree == null)||(!vaccinationIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationIdentifier = _other.vaccinationIdentifier;
     }
     final PropertyTree vaccinationVersionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationVersionIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationVersionIdentifierPropertyTree!= null):((vaccinationVersionIdentifierPropertyTree == null)||(!vaccinationVersionIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationVersionIdentifier = _other.vaccinationVersionIdentifier;
     }
     final PropertyTree modifiedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modified"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedPropertyTree!= null):((modifiedPropertyTree == null)||(!modifiedPropertyTree.isLeaf())))) {
        this.modified = ((_other.modified == null)?null:_other.modified.newCopyBuilder(this, modifiedPropertyTree, _propertyTreeUse));
     }
     final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
        this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this, createdPropertyTree, _propertyTreeUse));
     }
     final PropertyTree reviewedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reviewed"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reviewedPropertyTree!= null):((reviewedPropertyTree == null)||(!reviewedPropertyTree.isLeaf())))) {
        this.reviewed = ((_other.reviewed == null)?null:_other.reviewed.newCopyBuilder(this, reviewedPropertyTree, _propertyTreeUse));
     }
     final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
        this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this, reportedPropertyTree, _propertyTreeUse));
     }
     final PropertyTree vaccinePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccine"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinePropertyTree!= null):((vaccinePropertyTree == null)||(!vaccinePropertyTree.isLeaf())))) {
        this.vaccine = ((_other.vaccine == null)?null:_other.vaccine.newCopyBuilder(this, vaccinePropertyTree, _propertyTreeUse));
     }
     final PropertyTree vaccinationCredibilityPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCredibility"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCredibilityPropertyTree!= null):((vaccinationCredibilityPropertyTree == null)||(!vaccinationCredibilityPropertyTree.isLeaf())))) {
        this.vaccinationCredibility = _other.vaccinationCredibility;
     }
     final PropertyTree ssiDrugPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("ssiDrug"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(ssiDrugPropertyTree!= null):((ssiDrugPropertyTree == null)||(!ssiDrugPropertyTree.isLeaf())))) {
        this.ssiDrug = ((_other.ssiDrug == null)?null:_other.ssiDrug.newCopyBuilder(this, ssiDrugPropertyTree, _propertyTreeUse));
     }
     final PropertyTree batchNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("batchNumber"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(batchNumberPropertyTree!= null):((batchNumberPropertyTree == null)||(!batchNumberPropertyTree.isLeaf())))) {
        this.batchNumber = _other.batchNumber;
     }
     final PropertyTree coverageDurationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("coverageDuration"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(coverageDurationPropertyTree!= null):((coverageDurationPropertyTree == null)||(!coverageDurationPropertyTree.isLeaf())))) {
        this.coverageDuration = _other.coverageDuration;
     }
     final PropertyTree dosageOptionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("dosageOptionIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(dosageOptionIdentifierPropertyTree!= null):((dosageOptionIdentifierPropertyTree == null)||(!dosageOptionIdentifierPropertyTree.isLeaf())))) {
        this.dosageOptionIdentifier = _other.dosageOptionIdentifier;
     }
     final PropertyTree dosageTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("dosageText"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(dosageTextPropertyTree!= null):((dosageTextPropertyTree == null)||(!dosageTextPropertyTree.isLeaf())))) {
        this.dosageText = _other.dosageText;
     }
     final PropertyTree effectuatedDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedDateTime"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedDateTimePropertyTree!= null):((effectuatedDateTimePropertyTree == null)||(!effectuatedDateTimePropertyTree.isLeaf())))) {
        this.effectuatedDateTime = ((_other.effectuatedDateTime == null)?null:((XMLGregorianCalendar) _other.effectuatedDateTime.clone()));
     }
     final PropertyTree effectuatedPlannedItemPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatedPlannedItem"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatedPlannedItemPropertyTree!= null):((effectuatedPlannedItemPropertyTree == null)||(!effectuatedPlannedItemPropertyTree.isLeaf())))) {
        this.effectuatedPlannedItem = ((_other.effectuatedPlannedItem == null)?null:_other.effectuatedPlannedItem.newCopyBuilder(this, effectuatedPlannedItemPropertyTree, _propertyTreeUse));
     }
     final PropertyTree serviceCodePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("serviceCode"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(serviceCodePropertyTree!= null):((serviceCodePropertyTree == null)||(!serviceCodePropertyTree.isLeaf())))) {
        this.serviceCode = _other.serviceCode;
     }
     final PropertyTree serviceCodeRemarkTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("serviceCodeRemarkText"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(serviceCodeRemarkTextPropertyTree!= null):((serviceCodeRemarkTextPropertyTree == null)||(!serviceCodeRemarkTextPropertyTree.isLeaf())))) {
        this.serviceCodeRemarkText = _other.serviceCodeRemarkText;
     }
     final PropertyTree confirmedByPrescriptionServerPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("confirmedByPrescriptionServer"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(confirmedByPrescriptionServerPropertyTree!= null):((confirmedByPrescriptionServerPropertyTree == null)||(!confirmedByPrescriptionServerPropertyTree.isLeaf())))) {
        this.confirmedByPrescriptionServer = _other.confirmedByPrescriptionServer;
     }
     final PropertyTree activeStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("activeStatus"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(activeStatusPropertyTree!= null):((activeStatusPropertyTree == null)||(!activeStatusPropertyTree.isLeaf())))) {
        this.activeStatus = _other.activeStatus;
     }
     final PropertyTree isPreviousPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("isPrevious"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(isPreviousPropertyTree!= null):((isPreviousPropertyTree == null)||(!isPreviousPropertyTree.isLeaf())))) {
        this.isPrevious = _other.isPrevious;
     }
     final PropertyTree negativeConsentIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentIndicator"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentIndicatorPropertyTree!= null):((negativeConsentIndicatorPropertyTree == null)||(!negativeConsentIndicatorPropertyTree.isLeaf())))) {
        this.negativeConsentIndicator = _other.negativeConsentIndicator;
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
  protected<_P extends VaccinationType >_P init(final _P _product) {
  _product.vaccinationIdentifier = this.vaccinationIdentifier;
  _product.vaccinationVersionIdentifier = this.vaccinationVersionIdentifier;
  _product.modified = ((this.modified == null)?null:this.modified.build());
  _product.created = ((this.created == null)?null:this.created.build());
  _product.reviewed = ((this.reviewed == null)?null:this.reviewed.build());
  _product.reported = ((this.reported == null)?null:this.reported.build());
  _product.vaccine = ((this.vaccine == null)?null:this.vaccine.build());
  _product.vaccinationCredibility = this.vaccinationCredibility;
  _product.ssiDrug = ((this.ssiDrug == null)?null:this.ssiDrug.build());
  _product.batchNumber = this.batchNumber;
  _product.coverageDuration = this.coverageDuration;
  _product.dosageOptionIdentifier = this.dosageOptionIdentifier;
  _product.dosageText = this.dosageText;
  _product.effectuatedDateTime = this.effectuatedDateTime;
  _product.effectuatedPlannedItem = ((this.effectuatedPlannedItem == null)?null:this.effectuatedPlannedItem.build());
  _product.serviceCode = this.serviceCode;
  _product.serviceCodeRemarkText = this.serviceCodeRemarkText;
  _product.confirmedByPrescriptionServer = this.confirmedByPrescriptionServer;
  _product.activeStatus = this.activeStatus;
  _product.isPrevious = this.isPrevious;
  _product.negativeConsentIndicator = this.negativeConsentIndicator;
  return _product;
 }
  /**
 * Sets the new value of "vaccinationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationIdentifier
 *     New value of the "vaccinationIdentifier" property.
 */
 public VaccinationType.Builder<_B> withVaccinationIdentifier(final long vaccinationIdentifier) {
  this.vaccinationIdentifier = vaccinationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccinationVersionIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationVersionIdentifier
 *     New value of the "vaccinationVersionIdentifier" property.
 */
 public VaccinationType.Builder<_B> withVaccinationVersionIdentifier(final long vaccinationVersionIdentifier) {
  this.vaccinationVersionIdentifier = vaccinationVersionIdentifier;
  return this;
 }
  /**
 * Sets the new value of "modified" (any previous value will be replaced)
 * 
 * @param modified
 *     New value of the "modified" property.
 */
 public VaccinationType.Builder<_B> withModified(final ModifiedType modified) {
  this.modified = ((modified == null)?null:new ModifiedType.Builder<>(this, modified, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "modified" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "modified" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
 *     return to the current builder.
 */
 public ModifiedType.Builder<? extends VaccinationType.Builder<_B>> withModified() {
  if (this.modified!= null) {
   return this.modified;
  }
  return this.modified = new ModifiedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "created" (any previous value will be replaced)
 * 
 * @param created
 *     New value of the "created" property.
 */
 public VaccinationType.Builder<_B> withCreated(final CreatedType created) {
  this.created = ((created == null)?null:new CreatedType.Builder<>(this, created, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "created" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.CreatedType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "created" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.CreatedType.Builder#end()} to
 *     return to the current builder.
 */
 public CreatedType.Builder<? extends VaccinationType.Builder<_B>> withCreated() {
  if (this.created!= null) {
   return this.created;
  }
  return this.created = new CreatedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "reviewed" (any previous value will be replaced)
 * 
 * @param reviewed
 *     New value of the "reviewed" property.
 */
 public VaccinationType.Builder<_B> withReviewed(final ReviewedType reviewed) {
  this.reviewed = ((reviewed == null)?null:new ReviewedType.Builder<>(this, reviewed, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "reviewed" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.ReviewedType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "reviewed" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.ReviewedType.Builder#end()} to
 *     return to the current builder.
 */
 public ReviewedType.Builder<? extends VaccinationType.Builder<_B>> withReviewed() {
  if (this.reviewed!= null) {
   return this.reviewed;
  }
  return this.reviewed = new ReviewedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "reported" (any previous value will be replaced)
 * 
 * @param reported
 *     New value of the "reported" property.
 */
 public VaccinationType.Builder<_B> withReported(final ReportedType reported) {
  this.reported = ((reported == null)?null:new ReportedType.Builder<>(this, reported, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "reported" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.ReportedType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "reported" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.ReportedType.Builder#end()} to
 *     return to the current builder.
 */
 public ReportedType.Builder<? extends VaccinationType.Builder<_B>> withReported() {
  if (this.reported!= null) {
   return this.reported;
  }
  return this.reported = new ReportedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "vaccine" (any previous value will be replaced)
 * 
 * @param vaccine
 *     New value of the "vaccine" property.
 */
 public VaccinationType.Builder<_B> withVaccine(final VaccineType vaccine) {
  this.vaccine = ((vaccine == null)?null:new VaccineType.Builder<>(this, vaccine, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "vaccine" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.VaccineType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "vaccine" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.VaccineType.Builder#end()} to
 *     return to the current builder.
 */
 public VaccineType.Builder<? extends VaccinationType.Builder<_B>> withVaccine() {
  if (this.vaccine!= null) {
   return this.vaccine;
  }
  return this.vaccine = new VaccineType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "vaccinationCredibility" (any previous value will be
 * replaced)
 * 
 * @param vaccinationCredibility
 *     New value of the "vaccinationCredibility" property.
 */
 public VaccinationType.Builder<_B> withVaccinationCredibility(final VaccinationCredibilityEnumType vaccinationCredibility) {
  this.vaccinationCredibility = vaccinationCredibility;
  return this;
 }
  /**
 * Sets the new value of "ssiDrug" (any previous value will be replaced)
 * 
 * @param ssiDrug
 *     New value of the "ssiDrug" property.
 */
 public VaccinationType.Builder<_B> withSSIDrug(final SSIDrugType ssiDrug) {
  this.ssiDrug = ((ssiDrug == null)?null:new SSIDrugType.Builder<>(this, ssiDrug, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "ssiDrug" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.SSIDrugType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "ssiDrug" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.SSIDrugType.Builder#end()} to
 *     return to the current builder.
 */
 public SSIDrugType.Builder<? extends VaccinationType.Builder<_B>> withSSIDrug() {
  if (this.ssiDrug!= null) {
   return this.ssiDrug;
  }
  return this.ssiDrug = new SSIDrugType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "batchNumber" (any previous value will be replaced)
 * 
 * @param batchNumber
 *     New value of the "batchNumber" property.
 */
 public VaccinationType.Builder<_B> withBatchNumber(final String batchNumber) {
  this.batchNumber = batchNumber;
  return this;
 }
  /**
 * Sets the new value of "coverageDuration" (any previous value will be replaced)
 * 
 * @param coverageDuration
 *     New value of the "coverageDuration" property.
 */
 public VaccinationType.Builder<_B> withCoverageDuration(final String coverageDuration) {
  this.coverageDuration = coverageDuration;
  return this;
 }
  /**
 * Sets the new value of "dosageOptionIdentifier" (any previous value will be
 * replaced)
 * 
 * @param dosageOptionIdentifier
 *     New value of the "dosageOptionIdentifier" property.
 */
 public VaccinationType.Builder<_B> withDosageOptionIdentifier(final Long dosageOptionIdentifier) {
  this.dosageOptionIdentifier = dosageOptionIdentifier;
  return this;
 }
  /**
 * Sets the new value of "dosageText" (any previous value will be replaced)
 * 
 * @param dosageText
 *     New value of the "dosageText" property.
 */
 public VaccinationType.Builder<_B> withDosageText(final String dosageText) {
  this.dosageText = dosageText;
  return this;
 }
  /**
 * Sets the new value of "effectuatedDateTime" (any previous value will be
 * replaced)
 * 
 * @param effectuatedDateTime
 *     New value of the "effectuatedDateTime" property.
 */
 public VaccinationType.Builder<_B> withEffectuatedDateTime(final XMLGregorianCalendar effectuatedDateTime) {
  this.effectuatedDateTime = effectuatedDateTime;
  return this;
 }
  /**
 * Sets the new value of "effectuatedPlannedItem" (any previous value will be
 * replaced)
 * 
 * @param effectuatedPlannedItem
 *     New value of the "effectuatedPlannedItem" property.
 */
 public VaccinationType.Builder<_B> withEffectuatedPlannedItem(final EffectuatedPlannedItemType effectuatedPlannedItem) {
  this.effectuatedPlannedItem = ((effectuatedPlannedItem == null)?null:new EffectuatedPlannedItemType.Builder<>(this, effectuatedPlannedItem, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "effectuatedPlannedItem" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.EffectuatedPlannedItemType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "effectuatedPlannedItem" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.EffectuatedPlannedItemType.Builder#end()}
 *     to return to the current builder.
 */
 public EffectuatedPlannedItemType.Builder<? extends VaccinationType.Builder<_B>> withEffectuatedPlannedItem() {
  if (this.effectuatedPlannedItem!= null) {
   return this.effectuatedPlannedItem;
  }
  return this.effectuatedPlannedItem = new EffectuatedPlannedItemType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "serviceCode" (any previous value will be replaced)
 * 
 * @param serviceCode
 *     New value of the "serviceCode" property.
 */
 public VaccinationType.Builder<_B> withServiceCode(final String serviceCode) {
  this.serviceCode = serviceCode;
  return this;
 }
  /**
 * Sets the new value of "serviceCodeRemarkText" (any previous value will be
 * replaced)
 * 
 * @param serviceCodeRemarkText
 *     New value of the "serviceCodeRemarkText" property.
 */
 public VaccinationType.Builder<_B> withServiceCodeRemarkText(final String serviceCodeRemarkText) {
  this.serviceCodeRemarkText = serviceCodeRemarkText;
  return this;
 }
  /**
 * Sets the new value of "confirmedByPrescriptionServer" (any previous value will
 * be replaced)
 * 
 * @param confirmedByPrescriptionServer
 *     New value of the "confirmedByPrescriptionServer" property.
 */
 public VaccinationType.Builder<_B> withConfirmedByPrescriptionServer(final boolean confirmedByPrescriptionServer) {
  this.confirmedByPrescriptionServer = confirmedByPrescriptionServer;
  return this;
 }
  /**
 * Sets the new value of "activeStatus" (any previous value will be replaced)
 * 
 * @param activeStatus
 *     New value of the "activeStatus" property.
 */
 public VaccinationType.Builder<_B> withActiveStatus(final boolean activeStatus) {
  this.activeStatus = activeStatus;
  return this;
 }
  /**
 * Sets the new value of "isPrevious" (any previous value will be replaced)
 * 
 * @param isPrevious
 *     New value of the "isPrevious" property.
 */
 public VaccinationType.Builder<_B> withIsPrevious(final boolean isPrevious) {
  this.isPrevious = isPrevious;
  return this;
 }
  /**
 * Sets the new value of "negativeConsentIndicator" (any previous value will be
 * replaced)
 * 
 * @param negativeConsentIndicator
 *     New value of the "negativeConsentIndicator" property.
 */
 public VaccinationType.Builder<_B> withNegativeConsentIndicator(final Boolean negativeConsentIndicator) {
  this.negativeConsentIndicator = negativeConsentIndicator;
  return this;
 }
  @Override
 public VaccinationType build() {
  if (_storedValue == null) {
   return this.init(new VaccinationType());
  } else {
   return ((VaccinationType) _storedValue);
  }
 }
  public VaccinationType.Builder<_B> copyOf(final VaccinationType _other) {
  _other.copyTo(this);
  return this;
 }
  public VaccinationType.Builder<_B> copyOf(final VaccinationType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends VaccinationType.Selector<VaccinationType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static VaccinationType.Select _root() {
  return new VaccinationType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private ModifiedType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> modified = null;
 private CreatedType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> created = null;
 private ReviewedType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> reviewed = null;
 private ReportedType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> reported = null;
 private VaccineType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> vaccine = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> vaccinationCredibility = null;
 private SSIDrugType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> ssiDrug = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> batchNumber = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> coverageDuration = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> dosageOptionIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> dosageText = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> effectuatedDateTime = null;
 private EffectuatedPlannedItemType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> effectuatedPlannedItem = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> serviceCode = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> serviceCodeRemarkText = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> negativeConsentIndicator = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.modified!= null) {
   products.put("modified", this.modified.init());
  }
  if (this.created!= null) {
   products.put("created", this.created.init());
  }
  if (this.reviewed!= null) {
   products.put("reviewed", this.reviewed.init());
  }
  if (this.reported!= null) {
   products.put("reported", this.reported.init());
  }
  if (this.vaccine!= null) {
   products.put("vaccine", this.vaccine.init());
  }
  if (this.vaccinationCredibility!= null) {
   products.put("vaccinationCredibility", this.vaccinationCredibility.init());
  }
  if (this.ssiDrug!= null) {
   products.put("ssiDrug", this.ssiDrug.init());
  }
  if (this.batchNumber!= null) {
   products.put("batchNumber", this.batchNumber.init());
  }
  if (this.coverageDuration!= null) {
   products.put("coverageDuration", this.coverageDuration.init());
  }
  if (this.dosageOptionIdentifier!= null) {
   products.put("dosageOptionIdentifier", this.dosageOptionIdentifier.init());
  }
  if (this.dosageText!= null) {
   products.put("dosageText", this.dosageText.init());
  }
  if (this.effectuatedDateTime!= null) {
   products.put("effectuatedDateTime", this.effectuatedDateTime.init());
  }
  if (this.effectuatedPlannedItem!= null) {
   products.put("effectuatedPlannedItem", this.effectuatedPlannedItem.init());
  }
  if (this.serviceCode!= null) {
   products.put("serviceCode", this.serviceCode.init());
  }
  if (this.serviceCodeRemarkText!= null) {
   products.put("serviceCodeRemarkText", this.serviceCodeRemarkText.init());
  }
  if (this.negativeConsentIndicator!= null) {
   products.put("negativeConsentIndicator", this.negativeConsentIndicator.init());
  }
  return products;
 }
  public ModifiedType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> modified() {
  return ((this.modified == null)?this.modified = new ModifiedType.Selector<>(this._root, this, "modified"):this.modified);
 }
  public CreatedType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> created() {
  return ((this.created == null)?this.created = new CreatedType.Selector<>(this._root, this, "created"):this.created);
 }
  public ReviewedType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> reviewed() {
  return ((this.reviewed == null)?this.reviewed = new ReviewedType.Selector<>(this._root, this, "reviewed"):this.reviewed);
 }
  public ReportedType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> reported() {
  return ((this.reported == null)?this.reported = new ReportedType.Selector<>(this._root, this, "reported"):this.reported);
 }
  public VaccineType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> vaccine() {
  return ((this.vaccine == null)?this.vaccine = new VaccineType.Selector<>(this._root, this, "vaccine"):this.vaccine);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> vaccinationCredibility() {
  return ((this.vaccinationCredibility == null)?this.vaccinationCredibility = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationCredibility"):this.vaccinationCredibility);
 }
  public SSIDrugType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> ssiDrug() {
  return ((this.ssiDrug == null)?this.ssiDrug = new SSIDrugType.Selector<>(this._root, this, "ssiDrug"):this.ssiDrug);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> batchNumber() {
  return ((this.batchNumber == null)?this.batchNumber = new com.kscs.util.jaxb.Selector<>(this._root, this, "batchNumber"):this.batchNumber);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> coverageDuration() {
  return ((this.coverageDuration == null)?this.coverageDuration = new com.kscs.util.jaxb.Selector<>(this._root, this, "coverageDuration"):this.coverageDuration);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> dosageOptionIdentifier() {
  return ((this.dosageOptionIdentifier == null)?this.dosageOptionIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "dosageOptionIdentifier"):this.dosageOptionIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> dosageText() {
  return ((this.dosageText == null)?this.dosageText = new com.kscs.util.jaxb.Selector<>(this._root, this, "dosageText"):this.dosageText);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> effectuatedDateTime() {
  return ((this.effectuatedDateTime == null)?this.effectuatedDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "effectuatedDateTime"):this.effectuatedDateTime);
 }
  public EffectuatedPlannedItemType.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> effectuatedPlannedItem() {
  return ((this.effectuatedPlannedItem == null)?this.effectuatedPlannedItem = new EffectuatedPlannedItemType.Selector<>(this._root, this, "effectuatedPlannedItem"):this.effectuatedPlannedItem);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> serviceCode() {
  return ((this.serviceCode == null)?this.serviceCode = new com.kscs.util.jaxb.Selector<>(this._root, this, "serviceCode"):this.serviceCode);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> serviceCodeRemarkText() {
  return ((this.serviceCodeRemarkText == null)?this.serviceCodeRemarkText = new com.kscs.util.jaxb.Selector<>(this._root, this, "serviceCodeRemarkText"):this.serviceCodeRemarkText);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationType.Selector<TRoot, TParent>> negativeConsentIndicator() {
  return ((this.negativeConsentIndicator == null)?this.negativeConsentIndicator = new com.kscs.util.jaxb.Selector<>(this._root, this, "negativeConsentIndicator"):this.negativeConsentIndicator);
 }
  }
 }
