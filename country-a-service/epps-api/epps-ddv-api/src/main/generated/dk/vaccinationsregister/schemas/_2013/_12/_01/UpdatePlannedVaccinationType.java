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
* <p>Java class for UpdatePlannedVaccinationType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="UpdatePlannedVaccinationType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PlannedVaccinationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccinationIdentifierType"/>
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
@XmlType(name = "UpdatePlannedVaccinationType", propOrder = {
 "plannedVaccinationIdentifier",
 "plannedVaccinationDateTime",
 "negativeConsentIndicator"
})
public class UpdatePlannedVaccinationType {
  @XmlElement(name = "PlannedVaccinationIdentifier")
 protected long plannedVaccinationIdentifier;
 @XmlElement(name = "PlannedVaccinationDateTime", required = true)
 @XmlSchemaType(name = "dateTime")
 protected XMLGregorianCalendar plannedVaccinationDateTime;
 @XmlElement(name = "NegativeConsentIndicator")
 protected Boolean negativeConsentIndicator;
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
 public<_B >void copyTo(final UpdatePlannedVaccinationType.Builder<_B> _other) {
 _other.plannedVaccinationIdentifier = this.plannedVaccinationIdentifier;
 _other.plannedVaccinationDateTime = ((this.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) this.plannedVaccinationDateTime.clone()));
 _other.negativeConsentIndicator = this.negativeConsentIndicator;
 }
  public<_B >UpdatePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new UpdatePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true);
 }
  public UpdatePlannedVaccinationType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static UpdatePlannedVaccinationType.Builder<Void> builder() {
 return new UpdatePlannedVaccinationType.Builder<>(null, null, false);
 }
  public static<_B >UpdatePlannedVaccinationType.Builder<_B> copyOf(final UpdatePlannedVaccinationType _other) {
 final UpdatePlannedVaccinationType.Builder<_B> _newBuilder = new UpdatePlannedVaccinationType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final UpdatePlannedVaccinationType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree plannedVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationIdentifierPropertyTree!= null):((plannedVaccinationIdentifierPropertyTree == null)||(!plannedVaccinationIdentifierPropertyTree.isLeaf())))) {
  _other.plannedVaccinationIdentifier = this.plannedVaccinationIdentifier;
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
  public<_B >UpdatePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new UpdatePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public UpdatePlannedVaccinationType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >UpdatePlannedVaccinationType.Builder<_B> copyOf(final UpdatePlannedVaccinationType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final UpdatePlannedVaccinationType.Builder<_B> _newBuilder = new UpdatePlannedVaccinationType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static UpdatePlannedVaccinationType.Builder<Void> copyExcept(final UpdatePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static UpdatePlannedVaccinationType.Builder<Void> copyOnly(final UpdatePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final UpdatePlannedVaccinationType _storedValue;
 private long plannedVaccinationIdentifier;
 private XMLGregorianCalendar plannedVaccinationDateTime;
 private Boolean negativeConsentIndicator;
  public Builder(final _B _parentBuilder, final UpdatePlannedVaccinationType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.plannedVaccinationIdentifier = _other.plannedVaccinationIdentifier;
     this.plannedVaccinationDateTime = ((_other.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) _other.plannedVaccinationDateTime.clone()));
     this.negativeConsentIndicator = _other.negativeConsentIndicator;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final UpdatePlannedVaccinationType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree plannedVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationIdentifierPropertyTree!= null):((plannedVaccinationIdentifierPropertyTree == null)||(!plannedVaccinationIdentifierPropertyTree.isLeaf())))) {
        this.plannedVaccinationIdentifier = _other.plannedVaccinationIdentifier;
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
  protected<_P extends UpdatePlannedVaccinationType >_P init(final _P _product) {
  _product.plannedVaccinationIdentifier = this.plannedVaccinationIdentifier;
  _product.plannedVaccinationDateTime = this.plannedVaccinationDateTime;
  _product.negativeConsentIndicator = this.negativeConsentIndicator;
  return _product;
 }
  /**
 * Sets the new value of "plannedVaccinationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param plannedVaccinationIdentifier
 *     New value of the "plannedVaccinationIdentifier" property.
 */
 public UpdatePlannedVaccinationType.Builder<_B> withPlannedVaccinationIdentifier(final long plannedVaccinationIdentifier) {
  this.plannedVaccinationIdentifier = plannedVaccinationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "plannedVaccinationDateTime" (any previous value will be
 * replaced)
 * 
 * @param plannedVaccinationDateTime
 *     New value of the "plannedVaccinationDateTime" property.
 */
 public UpdatePlannedVaccinationType.Builder<_B> withPlannedVaccinationDateTime(final XMLGregorianCalendar plannedVaccinationDateTime) {
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
 public UpdatePlannedVaccinationType.Builder<_B> withNegativeConsentIndicator(final Boolean negativeConsentIndicator) {
  this.negativeConsentIndicator = negativeConsentIndicator;
  return this;
 }
  @Override
 public UpdatePlannedVaccinationType build() {
  if (_storedValue == null) {
   return this.init(new UpdatePlannedVaccinationType());
  } else {
   return ((UpdatePlannedVaccinationType) _storedValue);
  }
 }
  public UpdatePlannedVaccinationType.Builder<_B> copyOf(final UpdatePlannedVaccinationType _other) {
  _other.copyTo(this);
  return this;
 }
  public UpdatePlannedVaccinationType.Builder<_B> copyOf(final UpdatePlannedVaccinationType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends UpdatePlannedVaccinationType.Selector<UpdatePlannedVaccinationType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static UpdatePlannedVaccinationType.Select _root() {
  return new UpdatePlannedVaccinationType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, UpdatePlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccinationDateTime = null;
 private com.kscs.util.jaxb.Selector<TRoot, UpdatePlannedVaccinationType.Selector<TRoot, TParent>> negativeConsentIndicator = null;
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
  public com.kscs.util.jaxb.Selector<TRoot, UpdatePlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccinationDateTime() {
  return ((this.plannedVaccinationDateTime == null)?this.plannedVaccinationDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "plannedVaccinationDateTime"):this.plannedVaccinationDateTime);
 }
  public com.kscs.util.jaxb.Selector<TRoot, UpdatePlannedVaccinationType.Selector<TRoot, TParent>> negativeConsentIndicator() {
  return ((this.negativeConsentIndicator == null)?this.negativeConsentIndicator = new com.kscs.util.jaxb.Selector<>(this._root, this, "negativeConsentIndicator"):this.negativeConsentIndicator);
 }
  }
 }
