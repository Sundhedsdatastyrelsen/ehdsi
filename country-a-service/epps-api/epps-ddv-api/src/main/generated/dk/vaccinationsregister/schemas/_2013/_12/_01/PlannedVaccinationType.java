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
* <p>Java class for PlannedVaccinationType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="PlannedVaccinationType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PlannedVaccinationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccinationIdentifierType"/>
*         <element name="VaccineIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccineIdentifierType"/>
*         <element name="VaccineName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccineNameType"/>
*         <element name="PlannedVaccinationDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
*         <element name="VaccinationPlanIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanIdentifierType" minOccurs="0"/>
*         <element name="VaccinationPlanVersionIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanVersionIdentifierType" minOccurs="0"/>
*         <element name="VaccinationPlanName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanNameType" minOccurs="0"/>
*         <element name="VaccinationPlanItemIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemIdentifierType" minOccurs="0"/>
*         <element name="VaccinationPlanItemDescription" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemDescriptionType" minOccurs="0"/>
*         <element name="VaccinationPlanItemIndex" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemIndexType" minOccurs="0"/>
*         <element name="VaccinationPlanItemMinimumInterval" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemMinimumIntervalType" minOccurs="0"/>
*         <element name="VaccinationPlanItemSeries" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemSeriesType" minOccurs="0"/>
*         <element name="CoverageDuration" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CoverageDurationType" minOccurs="0"/>
*         <element name="NegativeConsentIndicator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}NegativeConsentIndicatorType" minOccurs="0"/>
*         <element name="Modified" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType" minOccurs="0"/>
*         <element name="Created" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CreatedType"/>
*         <element name="Reported" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ReportedType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlannedVaccinationType", propOrder = {
 "plannedVaccinationIdentifier",
 "vaccineIdentifier",
 "vaccineName",
 "plannedVaccinationDateTime",
 "vaccinationPlanIdentifier",
 "vaccinationPlanVersionIdentifier",
 "vaccinationPlanName",
 "vaccinationPlanItemIdentifier",
 "vaccinationPlanItemDescription",
 "vaccinationPlanItemIndex",
 "vaccinationPlanItemMinimumInterval",
 "vaccinationPlanItemSeries",
 "coverageDuration",
 "negativeConsentIndicator",
 "modified",
 "created",
 "reported"
})
public class PlannedVaccinationType {
  @XmlElement(name = "PlannedVaccinationIdentifier")
 protected long plannedVaccinationIdentifier;
 @XmlElement(name = "VaccineIdentifier")
 protected long vaccineIdentifier;
 @XmlElement(name = "VaccineName", required = true)
 protected String vaccineName;
 @XmlElement(name = "PlannedVaccinationDateTime", required = true)
 @XmlSchemaType(name = "dateTime")
 protected XMLGregorianCalendar plannedVaccinationDateTime;
 @XmlElement(name = "VaccinationPlanIdentifier")
 protected Long vaccinationPlanIdentifier;
 @XmlElement(name = "VaccinationPlanVersionIdentifier")
 protected Long vaccinationPlanVersionIdentifier;
 @XmlElement(name = "VaccinationPlanName")
 protected String vaccinationPlanName;
 @XmlElement(name = "VaccinationPlanItemIdentifier")
 protected Long vaccinationPlanItemIdentifier;
 @XmlElement(name = "VaccinationPlanItemDescription")
 protected String vaccinationPlanItemDescription;
 @XmlElement(name = "VaccinationPlanItemIndex")
 protected Long vaccinationPlanItemIndex;
 @XmlElement(name = "VaccinationPlanItemMinimumInterval")
 protected Long vaccinationPlanItemMinimumInterval;
 @XmlElement(name = "VaccinationPlanItemSeries")
 protected String vaccinationPlanItemSeries;
 @XmlElement(name = "CoverageDuration")
 protected String coverageDuration;
 @XmlElement(name = "NegativeConsentIndicator")
 protected Boolean negativeConsentIndicator;
 @XmlElement(name = "Modified")
 protected ModifiedType modified;
 @XmlElement(name = "Created", required = true)
 protected CreatedType created;
 @XmlElement(name = "Reported")
 protected ReportedType reported;
  /**
 * Gets the value of the plannedVaccinationIdentifier property.
 * 
 */
 public long getPlannedVaccinationIdentifier() {
 return plannedVaccinationIdentifier;
 }
  /**
 * Sets the value of the plannedVaccinationIdentifier property.
 * 
 */
 public void setPlannedVaccinationIdentifier(long value) {
 this.plannedVaccinationIdentifier = value;
 }
  /**
 * Gets the value of the vaccineIdentifier property.
 * 
 */
 public long getVaccineIdentifier() {
 return vaccineIdentifier;
 }
  /**
 * Sets the value of the vaccineIdentifier property.
 * 
 */
 public void setVaccineIdentifier(long value) {
 this.vaccineIdentifier = value;
 }
  /**
 * Gets the value of the vaccineName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccineName() {
 return vaccineName;
 }
  /**
 * Sets the value of the vaccineName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccineName(String value) {
 this.vaccineName = value;
 }
  /**
 * Gets the value of the plannedVaccinationDateTime property.
 * 
 * @return
 *     possible object is
 *     {@link XMLGregorianCalendar }
 *     
 */
 public XMLGregorianCalendar getPlannedVaccinationDateTime() {
 return plannedVaccinationDateTime;
 }
  /**
 * Sets the value of the plannedVaccinationDateTime property.
 * 
 * @param value
 *     allowed object is
 *     {@link XMLGregorianCalendar }
 *     
 */
 public void setPlannedVaccinationDateTime(XMLGregorianCalendar value) {
 this.plannedVaccinationDateTime = value;
 }
  /**
 * Gets the value of the vaccinationPlanIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link Long }
 *     
 */
 public Long getVaccinationPlanIdentifier() {
 return vaccinationPlanIdentifier;
 }
  /**
 * Sets the value of the vaccinationPlanIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link Long }
 *     
 */
 public void setVaccinationPlanIdentifier(Long value) {
 this.vaccinationPlanIdentifier = value;
 }
  /**
 * Gets the value of the vaccinationPlanVersionIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link Long }
 *     
 */
 public Long getVaccinationPlanVersionIdentifier() {
 return vaccinationPlanVersionIdentifier;
 }
  /**
 * Sets the value of the vaccinationPlanVersionIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link Long }
 *     
 */
 public void setVaccinationPlanVersionIdentifier(Long value) {
 this.vaccinationPlanVersionIdentifier = value;
 }
  /**
 * Gets the value of the vaccinationPlanName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccinationPlanName() {
 return vaccinationPlanName;
 }
  /**
 * Sets the value of the vaccinationPlanName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccinationPlanName(String value) {
 this.vaccinationPlanName = value;
 }
  /**
 * Gets the value of the vaccinationPlanItemIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link Long }
 *     
 */
 public Long getVaccinationPlanItemIdentifier() {
 return vaccinationPlanItemIdentifier;
 }
  /**
 * Sets the value of the vaccinationPlanItemIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link Long }
 *     
 */
 public void setVaccinationPlanItemIdentifier(Long value) {
 this.vaccinationPlanItemIdentifier = value;
 }
  /**
 * Gets the value of the vaccinationPlanItemDescription property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccinationPlanItemDescription() {
 return vaccinationPlanItemDescription;
 }
  /**
 * Sets the value of the vaccinationPlanItemDescription property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccinationPlanItemDescription(String value) {
 this.vaccinationPlanItemDescription = value;
 }
  /**
 * Gets the value of the vaccinationPlanItemIndex property.
 * 
 * @return
 *     possible object is
 *     {@link Long }
 *     
 */
 public Long getVaccinationPlanItemIndex() {
 return vaccinationPlanItemIndex;
 }
  /**
 * Sets the value of the vaccinationPlanItemIndex property.
 * 
 * @param value
 *     allowed object is
 *     {@link Long }
 *     
 */
 public void setVaccinationPlanItemIndex(Long value) {
 this.vaccinationPlanItemIndex = value;
 }
  /**
 * Gets the value of the vaccinationPlanItemMinimumInterval property.
 * 
 * @return
 *     possible object is
 *     {@link Long }
 *     
 */
 public Long getVaccinationPlanItemMinimumInterval() {
 return vaccinationPlanItemMinimumInterval;
 }
  /**
 * Sets the value of the vaccinationPlanItemMinimumInterval property.
 * 
 * @param value
 *     allowed object is
 *     {@link Long }
 *     
 */
 public void setVaccinationPlanItemMinimumInterval(Long value) {
 this.vaccinationPlanItemMinimumInterval = value;
 }
  /**
 * Gets the value of the vaccinationPlanItemSeries property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccinationPlanItemSeries() {
 return vaccinationPlanItemSeries;
 }
  /**
 * Sets the value of the vaccinationPlanItemSeries property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccinationPlanItemSeries(String value) {
 this.vaccinationPlanItemSeries = value;
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
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final PlannedVaccinationType.Builder<_B> _other) {
 _other.plannedVaccinationIdentifier = this.plannedVaccinationIdentifier;
 _other.vaccineIdentifier = this.vaccineIdentifier;
 _other.vaccineName = this.vaccineName;
 _other.plannedVaccinationDateTime = ((this.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) this.plannedVaccinationDateTime.clone()));
 _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 _other.vaccinationPlanVersionIdentifier = this.vaccinationPlanVersionIdentifier;
 _other.vaccinationPlanName = this.vaccinationPlanName;
 _other.vaccinationPlanItemIdentifier = this.vaccinationPlanItemIdentifier;
 _other.vaccinationPlanItemDescription = this.vaccinationPlanItemDescription;
 _other.vaccinationPlanItemIndex = this.vaccinationPlanItemIndex;
 _other.vaccinationPlanItemMinimumInterval = this.vaccinationPlanItemMinimumInterval;
 _other.vaccinationPlanItemSeries = this.vaccinationPlanItemSeries;
 _other.coverageDuration = this.coverageDuration;
 _other.negativeConsentIndicator = this.negativeConsentIndicator;
 _other.modified = ((this.modified == null)?null:this.modified.newCopyBuilder(_other));
 _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other));
 _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other));
 }
  public<_B >PlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new PlannedVaccinationType.Builder<_B>(_parentBuilder, this, true);
 }
  public PlannedVaccinationType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static PlannedVaccinationType.Builder<Void> builder() {
 return new PlannedVaccinationType.Builder<>(null, null, false);
 }
  public static<_B >PlannedVaccinationType.Builder<_B> copyOf(final PlannedVaccinationType _other) {
 final PlannedVaccinationType.Builder<_B> _newBuilder = new PlannedVaccinationType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final PlannedVaccinationType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree plannedVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationIdentifierPropertyTree!= null):((plannedVaccinationIdentifierPropertyTree == null)||(!plannedVaccinationIdentifierPropertyTree.isLeaf())))) {
  _other.plannedVaccinationIdentifier = this.plannedVaccinationIdentifier;
 }
 final PropertyTree vaccineIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineIdentifierPropertyTree!= null):((vaccineIdentifierPropertyTree == null)||(!vaccineIdentifierPropertyTree.isLeaf())))) {
  _other.vaccineIdentifier = this.vaccineIdentifier;
 }
 final PropertyTree vaccineNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineNamePropertyTree!= null):((vaccineNamePropertyTree == null)||(!vaccineNamePropertyTree.isLeaf())))) {
  _other.vaccineName = this.vaccineName;
 }
 final PropertyTree plannedVaccinationDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationDateTime"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationDateTimePropertyTree!= null):((plannedVaccinationDateTimePropertyTree == null)||(!plannedVaccinationDateTimePropertyTree.isLeaf())))) {
  _other.plannedVaccinationDateTime = ((this.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) this.plannedVaccinationDateTime.clone()));
 }
 final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 }
 final PropertyTree vaccinationPlanVersionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanVersionIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanVersionIdentifierPropertyTree!= null):((vaccinationPlanVersionIdentifierPropertyTree == null)||(!vaccinationPlanVersionIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanVersionIdentifier = this.vaccinationPlanVersionIdentifier;
 }
 final PropertyTree vaccinationPlanNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanNamePropertyTree!= null):((vaccinationPlanNamePropertyTree == null)||(!vaccinationPlanNamePropertyTree.isLeaf())))) {
  _other.vaccinationPlanName = this.vaccinationPlanName;
 }
 final PropertyTree vaccinationPlanItemIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIdentifierPropertyTree!= null):((vaccinationPlanItemIdentifierPropertyTree == null)||(!vaccinationPlanItemIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemIdentifier = this.vaccinationPlanItemIdentifier;
 }
 final PropertyTree vaccinationPlanItemDescriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemDescription"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemDescriptionPropertyTree!= null):((vaccinationPlanItemDescriptionPropertyTree == null)||(!vaccinationPlanItemDescriptionPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemDescription = this.vaccinationPlanItemDescription;
 }
 final PropertyTree vaccinationPlanItemIndexPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIndex"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIndexPropertyTree!= null):((vaccinationPlanItemIndexPropertyTree == null)||(!vaccinationPlanItemIndexPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemIndex = this.vaccinationPlanItemIndex;
 }
 final PropertyTree vaccinationPlanItemMinimumIntervalPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemMinimumInterval"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemMinimumIntervalPropertyTree!= null):((vaccinationPlanItemMinimumIntervalPropertyTree == null)||(!vaccinationPlanItemMinimumIntervalPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemMinimumInterval = this.vaccinationPlanItemMinimumInterval;
 }
 final PropertyTree vaccinationPlanItemSeriesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemSeries"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemSeriesPropertyTree!= null):((vaccinationPlanItemSeriesPropertyTree == null)||(!vaccinationPlanItemSeriesPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemSeries = this.vaccinationPlanItemSeries;
 }
 final PropertyTree coverageDurationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("coverageDuration"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(coverageDurationPropertyTree!= null):((coverageDurationPropertyTree == null)||(!coverageDurationPropertyTree.isLeaf())))) {
  _other.coverageDuration = this.coverageDuration;
 }
 final PropertyTree negativeConsentIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentIndicator"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentIndicatorPropertyTree!= null):((negativeConsentIndicatorPropertyTree == null)||(!negativeConsentIndicatorPropertyTree.isLeaf())))) {
  _other.negativeConsentIndicator = this.negativeConsentIndicator;
 }
 final PropertyTree modifiedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modified"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedPropertyTree!= null):((modifiedPropertyTree == null)||(!modifiedPropertyTree.isLeaf())))) {
  _other.modified = ((this.modified == null)?null:this.modified.newCopyBuilder(_other, modifiedPropertyTree, _propertyTreeUse));
 }
 final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
  _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other, createdPropertyTree, _propertyTreeUse));
 }
 final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
  _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other, reportedPropertyTree, _propertyTreeUse));
 }
 }
  public<_B >PlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new PlannedVaccinationType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public PlannedVaccinationType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >PlannedVaccinationType.Builder<_B> copyOf(final PlannedVaccinationType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PlannedVaccinationType.Builder<_B> _newBuilder = new PlannedVaccinationType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static PlannedVaccinationType.Builder<Void> copyExcept(final PlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static PlannedVaccinationType.Builder<Void> copyOnly(final PlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final PlannedVaccinationType _storedValue;
 private long plannedVaccinationIdentifier;
 private long vaccineIdentifier;
 private String vaccineName;
 private XMLGregorianCalendar plannedVaccinationDateTime;
 private Long vaccinationPlanIdentifier;
 private Long vaccinationPlanVersionIdentifier;
 private String vaccinationPlanName;
 private Long vaccinationPlanItemIdentifier;
 private String vaccinationPlanItemDescription;
 private Long vaccinationPlanItemIndex;
 private Long vaccinationPlanItemMinimumInterval;
 private String vaccinationPlanItemSeries;
 private String coverageDuration;
 private Boolean negativeConsentIndicator;
 private ModifiedType.Builder<PlannedVaccinationType.Builder<_B>> modified;
 private CreatedType.Builder<PlannedVaccinationType.Builder<_B>> created;
 private ReportedType.Builder<PlannedVaccinationType.Builder<_B>> reported;
  public Builder(final _B _parentBuilder, final PlannedVaccinationType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.plannedVaccinationIdentifier = _other.plannedVaccinationIdentifier;
     this.vaccineIdentifier = _other.vaccineIdentifier;
     this.vaccineName = _other.vaccineName;
     this.plannedVaccinationDateTime = ((_other.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) _other.plannedVaccinationDateTime.clone()));
     this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     this.vaccinationPlanVersionIdentifier = _other.vaccinationPlanVersionIdentifier;
     this.vaccinationPlanName = _other.vaccinationPlanName;
     this.vaccinationPlanItemIdentifier = _other.vaccinationPlanItemIdentifier;
     this.vaccinationPlanItemDescription = _other.vaccinationPlanItemDescription;
     this.vaccinationPlanItemIndex = _other.vaccinationPlanItemIndex;
     this.vaccinationPlanItemMinimumInterval = _other.vaccinationPlanItemMinimumInterval;
     this.vaccinationPlanItemSeries = _other.vaccinationPlanItemSeries;
     this.coverageDuration = _other.coverageDuration;
     this.negativeConsentIndicator = _other.negativeConsentIndicator;
     this.modified = ((_other.modified == null)?null:_other.modified.newCopyBuilder(this));
     this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this));
     this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final PlannedVaccinationType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree plannedVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationIdentifierPropertyTree!= null):((plannedVaccinationIdentifierPropertyTree == null)||(!plannedVaccinationIdentifierPropertyTree.isLeaf())))) {
        this.plannedVaccinationIdentifier = _other.plannedVaccinationIdentifier;
     }
     final PropertyTree vaccineIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineIdentifierPropertyTree!= null):((vaccineIdentifierPropertyTree == null)||(!vaccineIdentifierPropertyTree.isLeaf())))) {
        this.vaccineIdentifier = _other.vaccineIdentifier;
     }
     final PropertyTree vaccineNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineNamePropertyTree!= null):((vaccineNamePropertyTree == null)||(!vaccineNamePropertyTree.isLeaf())))) {
        this.vaccineName = _other.vaccineName;
     }
     final PropertyTree plannedVaccinationDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationDateTime"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationDateTimePropertyTree!= null):((plannedVaccinationDateTimePropertyTree == null)||(!plannedVaccinationDateTimePropertyTree.isLeaf())))) {
        this.plannedVaccinationDateTime = ((_other.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) _other.plannedVaccinationDateTime.clone()));
     }
     final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     }
     final PropertyTree vaccinationPlanVersionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanVersionIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanVersionIdentifierPropertyTree!= null):((vaccinationPlanVersionIdentifierPropertyTree == null)||(!vaccinationPlanVersionIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanVersionIdentifier = _other.vaccinationPlanVersionIdentifier;
     }
     final PropertyTree vaccinationPlanNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanNamePropertyTree!= null):((vaccinationPlanNamePropertyTree == null)||(!vaccinationPlanNamePropertyTree.isLeaf())))) {
        this.vaccinationPlanName = _other.vaccinationPlanName;
     }
     final PropertyTree vaccinationPlanItemIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIdentifierPropertyTree!= null):((vaccinationPlanItemIdentifierPropertyTree == null)||(!vaccinationPlanItemIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemIdentifier = _other.vaccinationPlanItemIdentifier;
     }
     final PropertyTree vaccinationPlanItemDescriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemDescription"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemDescriptionPropertyTree!= null):((vaccinationPlanItemDescriptionPropertyTree == null)||(!vaccinationPlanItemDescriptionPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemDescription = _other.vaccinationPlanItemDescription;
     }
     final PropertyTree vaccinationPlanItemIndexPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIndex"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIndexPropertyTree!= null):((vaccinationPlanItemIndexPropertyTree == null)||(!vaccinationPlanItemIndexPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemIndex = _other.vaccinationPlanItemIndex;
     }
     final PropertyTree vaccinationPlanItemMinimumIntervalPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemMinimumInterval"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemMinimumIntervalPropertyTree!= null):((vaccinationPlanItemMinimumIntervalPropertyTree == null)||(!vaccinationPlanItemMinimumIntervalPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemMinimumInterval = _other.vaccinationPlanItemMinimumInterval;
     }
     final PropertyTree vaccinationPlanItemSeriesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemSeries"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemSeriesPropertyTree!= null):((vaccinationPlanItemSeriesPropertyTree == null)||(!vaccinationPlanItemSeriesPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemSeries = _other.vaccinationPlanItemSeries;
     }
     final PropertyTree coverageDurationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("coverageDuration"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(coverageDurationPropertyTree!= null):((coverageDurationPropertyTree == null)||(!coverageDurationPropertyTree.isLeaf())))) {
        this.coverageDuration = _other.coverageDuration;
     }
     final PropertyTree negativeConsentIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentIndicator"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentIndicatorPropertyTree!= null):((negativeConsentIndicatorPropertyTree == null)||(!negativeConsentIndicatorPropertyTree.isLeaf())))) {
        this.negativeConsentIndicator = _other.negativeConsentIndicator;
     }
     final PropertyTree modifiedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modified"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedPropertyTree!= null):((modifiedPropertyTree == null)||(!modifiedPropertyTree.isLeaf())))) {
        this.modified = ((_other.modified == null)?null:_other.modified.newCopyBuilder(this, modifiedPropertyTree, _propertyTreeUse));
     }
     final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
        this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this, createdPropertyTree, _propertyTreeUse));
     }
     final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
        this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this, reportedPropertyTree, _propertyTreeUse));
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
  protected<_P extends PlannedVaccinationType >_P init(final _P _product) {
  _product.plannedVaccinationIdentifier = this.plannedVaccinationIdentifier;
  _product.vaccineIdentifier = this.vaccineIdentifier;
  _product.vaccineName = this.vaccineName;
  _product.plannedVaccinationDateTime = this.plannedVaccinationDateTime;
  _product.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
  _product.vaccinationPlanVersionIdentifier = this.vaccinationPlanVersionIdentifier;
  _product.vaccinationPlanName = this.vaccinationPlanName;
  _product.vaccinationPlanItemIdentifier = this.vaccinationPlanItemIdentifier;
  _product.vaccinationPlanItemDescription = this.vaccinationPlanItemDescription;
  _product.vaccinationPlanItemIndex = this.vaccinationPlanItemIndex;
  _product.vaccinationPlanItemMinimumInterval = this.vaccinationPlanItemMinimumInterval;
  _product.vaccinationPlanItemSeries = this.vaccinationPlanItemSeries;
  _product.coverageDuration = this.coverageDuration;
  _product.negativeConsentIndicator = this.negativeConsentIndicator;
  _product.modified = ((this.modified == null)?null:this.modified.build());
  _product.created = ((this.created == null)?null:this.created.build());
  _product.reported = ((this.reported == null)?null:this.reported.build());
  return _product;
 }
  /**
 * Sets the new value of "plannedVaccinationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param plannedVaccinationIdentifier
 *     New value of the "plannedVaccinationIdentifier" property.
 */
 public PlannedVaccinationType.Builder<_B> withPlannedVaccinationIdentifier(final long plannedVaccinationIdentifier) {
  this.plannedVaccinationIdentifier = plannedVaccinationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccineIdentifier" (any previous value will be replaced)
 * 
 * @param vaccineIdentifier
 *     New value of the "vaccineIdentifier" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccineIdentifier(final long vaccineIdentifier) {
  this.vaccineIdentifier = vaccineIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccineName" (any previous value will be replaced)
 * 
 * @param vaccineName
 *     New value of the "vaccineName" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccineName(final String vaccineName) {
  this.vaccineName = vaccineName;
  return this;
 }
  /**
 * Sets the new value of "plannedVaccinationDateTime" (any previous value will be
 * replaced)
 * 
 * @param plannedVaccinationDateTime
 *     New value of the "plannedVaccinationDateTime" property.
 */
 public PlannedVaccinationType.Builder<_B> withPlannedVaccinationDateTime(final XMLGregorianCalendar plannedVaccinationDateTime) {
  this.plannedVaccinationDateTime = plannedVaccinationDateTime;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanIdentifier
 *     New value of the "vaccinationPlanIdentifier" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccinationPlanIdentifier(final Long vaccinationPlanIdentifier) {
  this.vaccinationPlanIdentifier = vaccinationPlanIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanVersionIdentifier" (any previous value
 * will be replaced)
 * 
 * @param vaccinationPlanVersionIdentifier
 *     New value of the "vaccinationPlanVersionIdentifier" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccinationPlanVersionIdentifier(final Long vaccinationPlanVersionIdentifier) {
  this.vaccinationPlanVersionIdentifier = vaccinationPlanVersionIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanName" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanName
 *     New value of the "vaccinationPlanName" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccinationPlanName(final String vaccinationPlanName) {
  this.vaccinationPlanName = vaccinationPlanName;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanItemIdentifier" (any previous value will
 * be replaced)
 * 
 * @param vaccinationPlanItemIdentifier
 *     New value of the "vaccinationPlanItemIdentifier" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccinationPlanItemIdentifier(final Long vaccinationPlanItemIdentifier) {
  this.vaccinationPlanItemIdentifier = vaccinationPlanItemIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanItemDescription" (any previous value will
 * be replaced)
 * 
 * @param vaccinationPlanItemDescription
 *     New value of the "vaccinationPlanItemDescription" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccinationPlanItemDescription(final String vaccinationPlanItemDescription) {
  this.vaccinationPlanItemDescription = vaccinationPlanItemDescription;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanItemIndex" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanItemIndex
 *     New value of the "vaccinationPlanItemIndex" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccinationPlanItemIndex(final Long vaccinationPlanItemIndex) {
  this.vaccinationPlanItemIndex = vaccinationPlanItemIndex;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanItemMinimumInterval" (any previous value
 * will be replaced)
 * 
 * @param vaccinationPlanItemMinimumInterval
 *     New value of the "vaccinationPlanItemMinimumInterval" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccinationPlanItemMinimumInterval(final Long vaccinationPlanItemMinimumInterval) {
  this.vaccinationPlanItemMinimumInterval = vaccinationPlanItemMinimumInterval;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanItemSeries" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanItemSeries
 *     New value of the "vaccinationPlanItemSeries" property.
 */
 public PlannedVaccinationType.Builder<_B> withVaccinationPlanItemSeries(final String vaccinationPlanItemSeries) {
  this.vaccinationPlanItemSeries = vaccinationPlanItemSeries;
  return this;
 }
  /**
 * Sets the new value of "coverageDuration" (any previous value will be replaced)
 * 
 * @param coverageDuration
 *     New value of the "coverageDuration" property.
 */
 public PlannedVaccinationType.Builder<_B> withCoverageDuration(final String coverageDuration) {
  this.coverageDuration = coverageDuration;
  return this;
 }
  /**
 * Sets the new value of "negativeConsentIndicator" (any previous value will be
 * replaced)
 * 
 * @param negativeConsentIndicator
 *     New value of the "negativeConsentIndicator" property.
 */
 public PlannedVaccinationType.Builder<_B> withNegativeConsentIndicator(final Boolean negativeConsentIndicator) {
  this.negativeConsentIndicator = negativeConsentIndicator;
  return this;
 }
  /**
 * Sets the new value of "modified" (any previous value will be replaced)
 * 
 * @param modified
 *     New value of the "modified" property.
 */
 public PlannedVaccinationType.Builder<_B> withModified(final ModifiedType modified) {
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
 public ModifiedType.Builder<? extends PlannedVaccinationType.Builder<_B>> withModified() {
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
 public PlannedVaccinationType.Builder<_B> withCreated(final CreatedType created) {
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
 public CreatedType.Builder<? extends PlannedVaccinationType.Builder<_B>> withCreated() {
  if (this.created!= null) {
   return this.created;
  }
  return this.created = new CreatedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "reported" (any previous value will be replaced)
 * 
 * @param reported
 *     New value of the "reported" property.
 */
 public PlannedVaccinationType.Builder<_B> withReported(final ReportedType reported) {
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
 public ReportedType.Builder<? extends PlannedVaccinationType.Builder<_B>> withReported() {
  if (this.reported!= null) {
   return this.reported;
  }
  return this.reported = new ReportedType.Builder<>(this, null, false);
 }
  @Override
 public PlannedVaccinationType build() {
  if (_storedValue == null) {
   return this.init(new PlannedVaccinationType());
  } else {
   return ((PlannedVaccinationType) _storedValue);
  }
 }
  public PlannedVaccinationType.Builder<_B> copyOf(final PlannedVaccinationType _other) {
  _other.copyTo(this);
  return this;
 }
  public PlannedVaccinationType.Builder<_B> copyOf(final PlannedVaccinationType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends PlannedVaccinationType.Selector<PlannedVaccinationType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static PlannedVaccinationType.Select _root() {
  return new PlannedVaccinationType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccineName = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccinationDateTime = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanVersionIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanName = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemDescription = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemIndex = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemMinimumInterval = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemSeries = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> coverageDuration = null;
 private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> negativeConsentIndicator = null;
 private ModifiedType.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> modified = null;
 private CreatedType.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> created = null;
 private ReportedType.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> reported = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.vaccineName!= null) {
   products.put("vaccineName", this.vaccineName.init());
  }
  if (this.plannedVaccinationDateTime!= null) {
   products.put("plannedVaccinationDateTime", this.plannedVaccinationDateTime.init());
  }
  if (this.vaccinationPlanIdentifier!= null) {
   products.put("vaccinationPlanIdentifier", this.vaccinationPlanIdentifier.init());
  }
  if (this.vaccinationPlanVersionIdentifier!= null) {
   products.put("vaccinationPlanVersionIdentifier", this.vaccinationPlanVersionIdentifier.init());
  }
  if (this.vaccinationPlanName!= null) {
   products.put("vaccinationPlanName", this.vaccinationPlanName.init());
  }
  if (this.vaccinationPlanItemIdentifier!= null) {
   products.put("vaccinationPlanItemIdentifier", this.vaccinationPlanItemIdentifier.init());
  }
  if (this.vaccinationPlanItemDescription!= null) {
   products.put("vaccinationPlanItemDescription", this.vaccinationPlanItemDescription.init());
  }
  if (this.vaccinationPlanItemIndex!= null) {
   products.put("vaccinationPlanItemIndex", this.vaccinationPlanItemIndex.init());
  }
  if (this.vaccinationPlanItemMinimumInterval!= null) {
   products.put("vaccinationPlanItemMinimumInterval", this.vaccinationPlanItemMinimumInterval.init());
  }
  if (this.vaccinationPlanItemSeries!= null) {
   products.put("vaccinationPlanItemSeries", this.vaccinationPlanItemSeries.init());
  }
  if (this.coverageDuration!= null) {
   products.put("coverageDuration", this.coverageDuration.init());
  }
  if (this.negativeConsentIndicator!= null) {
   products.put("negativeConsentIndicator", this.negativeConsentIndicator.init());
  }
  if (this.modified!= null) {
   products.put("modified", this.modified.init());
  }
  if (this.created!= null) {
   products.put("created", this.created.init());
  }
  if (this.reported!= null) {
   products.put("reported", this.reported.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccineName() {
  return ((this.vaccineName == null)?this.vaccineName = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccineName"):this.vaccineName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccinationDateTime() {
  return ((this.plannedVaccinationDateTime == null)?this.plannedVaccinationDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "plannedVaccinationDateTime"):this.plannedVaccinationDateTime);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanIdentifier() {
  return ((this.vaccinationPlanIdentifier == null)?this.vaccinationPlanIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanIdentifier"):this.vaccinationPlanIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanVersionIdentifier() {
  return ((this.vaccinationPlanVersionIdentifier == null)?this.vaccinationPlanVersionIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanVersionIdentifier"):this.vaccinationPlanVersionIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanName() {
  return ((this.vaccinationPlanName == null)?this.vaccinationPlanName = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanName"):this.vaccinationPlanName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemIdentifier() {
  return ((this.vaccinationPlanItemIdentifier == null)?this.vaccinationPlanItemIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanItemIdentifier"):this.vaccinationPlanItemIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemDescription() {
  return ((this.vaccinationPlanItemDescription == null)?this.vaccinationPlanItemDescription = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanItemDescription"):this.vaccinationPlanItemDescription);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemIndex() {
  return ((this.vaccinationPlanItemIndex == null)?this.vaccinationPlanItemIndex = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanItemIndex"):this.vaccinationPlanItemIndex);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemMinimumInterval() {
  return ((this.vaccinationPlanItemMinimumInterval == null)?this.vaccinationPlanItemMinimumInterval = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanItemMinimumInterval"):this.vaccinationPlanItemMinimumInterval);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> vaccinationPlanItemSeries() {
  return ((this.vaccinationPlanItemSeries == null)?this.vaccinationPlanItemSeries = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanItemSeries"):this.vaccinationPlanItemSeries);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> coverageDuration() {
  return ((this.coverageDuration == null)?this.coverageDuration = new com.kscs.util.jaxb.Selector<>(this._root, this, "coverageDuration"):this.coverageDuration);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> negativeConsentIndicator() {
  return ((this.negativeConsentIndicator == null)?this.negativeConsentIndicator = new com.kscs.util.jaxb.Selector<>(this._root, this, "negativeConsentIndicator"):this.negativeConsentIndicator);
 }
  public ModifiedType.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> modified() {
  return ((this.modified == null)?this.modified = new ModifiedType.Selector<>(this._root, this, "modified"):this.modified);
 }
  public CreatedType.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> created() {
  return ((this.created == null)?this.created = new CreatedType.Selector<>(this._root, this, "created"):this.created);
 }
  public ReportedType.Selector<TRoot, PlannedVaccinationType.Selector<TRoot, TParent>> reported() {
  return ((this.reported == null)?this.reported = new ReportedType.Selector<>(this._root, this, "reported"):this.reported);
 }
  }
 }
