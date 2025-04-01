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
* <p>Java class for CreateSinglePlannedVaccinationType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="CreateSinglePlannedVaccinationType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccineIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccineIdentifierType"/>
*         <element name="PlannedVaccinationDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
@XmlType(name = "CreateSinglePlannedVaccinationType", propOrder = {
 "vaccineIdentifier",
 "plannedVaccinationDateTime",
 "negativeConsentIndicator"
})
public class CreateSinglePlannedVaccinationType {
  @XmlElement(name = "VaccineIdentifier")
 protected long vaccineIdentifier;
 @XmlElement(name = "PlannedVaccinationDateTime", required = true)
 @XmlSchemaType(name = "dateTime")
 protected XMLGregorianCalendar plannedVaccinationDateTime;
 @XmlElement(name = "NegativeConsentIndicator")
 protected Boolean negativeConsentIndicator;
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
 public<_B >void copyTo(final CreateSinglePlannedVaccinationType.Builder<_B> _other) {
 _other.vaccineIdentifier = this.vaccineIdentifier;
 _other.plannedVaccinationDateTime = ((this.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) this.plannedVaccinationDateTime.clone()));
 _other.negativeConsentIndicator = this.negativeConsentIndicator;
 }
  public<_B >CreateSinglePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new CreateSinglePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true);
 }
  public CreateSinglePlannedVaccinationType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static CreateSinglePlannedVaccinationType.Builder<Void> builder() {
 return new CreateSinglePlannedVaccinationType.Builder<>(null, null, false);
 }
  public static<_B >CreateSinglePlannedVaccinationType.Builder<_B> copyOf(final CreateSinglePlannedVaccinationType _other) {
 final CreateSinglePlannedVaccinationType.Builder<_B> _newBuilder = new CreateSinglePlannedVaccinationType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final CreateSinglePlannedVaccinationType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccineIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineIdentifierPropertyTree!= null):((vaccineIdentifierPropertyTree == null)||(!vaccineIdentifierPropertyTree.isLeaf())))) {
  _other.vaccineIdentifier = this.vaccineIdentifier;
 }
 final PropertyTree plannedVaccinationDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationDateTime"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationDateTimePropertyTree!= null):((plannedVaccinationDateTimePropertyTree == null)||(!plannedVaccinationDateTimePropertyTree.isLeaf())))) {
  _other.plannedVaccinationDateTime = ((this.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) this.plannedVaccinationDateTime.clone()));
 }
 final PropertyTree negativeConsentIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("negativeConsentIndicator"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(negativeConsentIndicatorPropertyTree!= null):((negativeConsentIndicatorPropertyTree == null)||(!negativeConsentIndicatorPropertyTree.isLeaf())))) {
  _other.negativeConsentIndicator = this.negativeConsentIndicator;
 }
 }
  public<_B >CreateSinglePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new CreateSinglePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public CreateSinglePlannedVaccinationType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >CreateSinglePlannedVaccinationType.Builder<_B> copyOf(final CreateSinglePlannedVaccinationType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final CreateSinglePlannedVaccinationType.Builder<_B> _newBuilder = new CreateSinglePlannedVaccinationType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static CreateSinglePlannedVaccinationType.Builder<Void> copyExcept(final CreateSinglePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static CreateSinglePlannedVaccinationType.Builder<Void> copyOnly(final CreateSinglePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final CreateSinglePlannedVaccinationType _storedValue;
 private long vaccineIdentifier;
 private XMLGregorianCalendar plannedVaccinationDateTime;
 private Boolean negativeConsentIndicator;
  public Builder(final _B _parentBuilder, final CreateSinglePlannedVaccinationType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccineIdentifier = _other.vaccineIdentifier;
     this.plannedVaccinationDateTime = ((_other.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) _other.plannedVaccinationDateTime.clone()));
     this.negativeConsentIndicator = _other.negativeConsentIndicator;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final CreateSinglePlannedVaccinationType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccineIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineIdentifierPropertyTree!= null):((vaccineIdentifierPropertyTree == null)||(!vaccineIdentifierPropertyTree.isLeaf())))) {
        this.vaccineIdentifier = _other.vaccineIdentifier;
     }
     final PropertyTree plannedVaccinationDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationDateTime"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationDateTimePropertyTree!= null):((plannedVaccinationDateTimePropertyTree == null)||(!plannedVaccinationDateTimePropertyTree.isLeaf())))) {
        this.plannedVaccinationDateTime = ((_other.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) _other.plannedVaccinationDateTime.clone()));
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
  protected<_P extends CreateSinglePlannedVaccinationType >_P init(final _P _product) {
  _product.vaccineIdentifier = this.vaccineIdentifier;
  _product.plannedVaccinationDateTime = this.plannedVaccinationDateTime;
  _product.negativeConsentIndicator = this.negativeConsentIndicator;
  return _product;
 }
  /**
 * Sets the new value of "vaccineIdentifier" (any previous value will be replaced)
 * 
 * @param vaccineIdentifier
 *     New value of the "vaccineIdentifier" property.
 */
 public CreateSinglePlannedVaccinationType.Builder<_B> withVaccineIdentifier(final long vaccineIdentifier) {
  this.vaccineIdentifier = vaccineIdentifier;
  return this;
 }
  /**
 * Sets the new value of "plannedVaccinationDateTime" (any previous value will be
 * replaced)
 * 
 * @param plannedVaccinationDateTime
 *     New value of the "plannedVaccinationDateTime" property.
 */
 public CreateSinglePlannedVaccinationType.Builder<_B> withPlannedVaccinationDateTime(final XMLGregorianCalendar plannedVaccinationDateTime) {
  this.plannedVaccinationDateTime = plannedVaccinationDateTime;
  return this;
 }
  /**
 * Sets the new value of "negativeConsentIndicator" (any previous value will be
 * replaced)
 * 
 * @param negativeConsentIndicator
 *     New value of the "negativeConsentIndicator" property.
 */
 public CreateSinglePlannedVaccinationType.Builder<_B> withNegativeConsentIndicator(final Boolean negativeConsentIndicator) {
  this.negativeConsentIndicator = negativeConsentIndicator;
  return this;
 }
  @Override
 public CreateSinglePlannedVaccinationType build() {
  if (_storedValue == null) {
   return this.init(new CreateSinglePlannedVaccinationType());
  } else {
   return ((CreateSinglePlannedVaccinationType) _storedValue);
  }
 }
  public CreateSinglePlannedVaccinationType.Builder<_B> copyOf(final CreateSinglePlannedVaccinationType _other) {
  _other.copyTo(this);
  return this;
 }
  public CreateSinglePlannedVaccinationType.Builder<_B> copyOf(final CreateSinglePlannedVaccinationType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends CreateSinglePlannedVaccinationType.Selector<CreateSinglePlannedVaccinationType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static CreateSinglePlannedVaccinationType.Select _root() {
  return new CreateSinglePlannedVaccinationType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, CreateSinglePlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccinationDateTime = null;
 private com.kscs.util.jaxb.Selector<TRoot, CreateSinglePlannedVaccinationType.Selector<TRoot, TParent>> negativeConsentIndicator = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.plannedVaccinationDateTime!= null) {
   products.put("plannedVaccinationDateTime", this.plannedVaccinationDateTime.init());
  }
  if (this.negativeConsentIndicator!= null) {
   products.put("negativeConsentIndicator", this.negativeConsentIndicator.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, CreateSinglePlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccinationDateTime() {
  return ((this.plannedVaccinationDateTime == null)?this.plannedVaccinationDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "plannedVaccinationDateTime"):this.plannedVaccinationDateTime);
 }
  public com.kscs.util.jaxb.Selector<TRoot, CreateSinglePlannedVaccinationType.Selector<TRoot, TParent>> negativeConsentIndicator() {
  return ((this.negativeConsentIndicator == null)?this.negativeConsentIndicator = new com.kscs.util.jaxb.Selector<>(this._root, this, "negativeConsentIndicator"):this.negativeConsentIndicator);
 }
  }
 }
