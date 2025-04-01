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
* <p>Java class for CreateVaccinationPlanSubscriptionType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="CreateVaccinationPlanSubscriptionType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccinationPlanIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanIdentifierType" minOccurs="0"/>
*         <element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateVaccinationPlanSubscriptionType", propOrder = {
 "vaccinationPlanIdentifier",
 "startDate"
})
public class CreateVaccinationPlanSubscriptionType {
  @XmlElement(name = "VaccinationPlanIdentifier")
 protected Long vaccinationPlanIdentifier;
 @XmlElement(required = true)
 @XmlSchemaType(name = "dateTime")
 protected XMLGregorianCalendar startDate;
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
 * Gets the value of the startDate property.
 * 
 * @return
 *     possible object is
 *     {@link XMLGregorianCalendar }
 *     
 */
 public XMLGregorianCalendar getStartDate() {
 return startDate;
 }
  /**
 * Sets the value of the startDate property.
 * 
 * @param value
 *     allowed object is
 *     {@link XMLGregorianCalendar }
 *     
 */
 public void setStartDate(XMLGregorianCalendar value) {
 this.startDate = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final CreateVaccinationPlanSubscriptionType.Builder<_B> _other) {
 _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 _other.startDate = ((this.startDate == null)?null:((XMLGregorianCalendar) this.startDate.clone()));
 }
  public<_B >CreateVaccinationPlanSubscriptionType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new CreateVaccinationPlanSubscriptionType.Builder<_B>(_parentBuilder, this, true);
 }
  public CreateVaccinationPlanSubscriptionType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static CreateVaccinationPlanSubscriptionType.Builder<Void> builder() {
 return new CreateVaccinationPlanSubscriptionType.Builder<>(null, null, false);
 }
  public static<_B >CreateVaccinationPlanSubscriptionType.Builder<_B> copyOf(final CreateVaccinationPlanSubscriptionType _other) {
 final CreateVaccinationPlanSubscriptionType.Builder<_B> _newBuilder = new CreateVaccinationPlanSubscriptionType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final CreateVaccinationPlanSubscriptionType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 }
 final PropertyTree startDatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("startDate"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(startDatePropertyTree!= null):((startDatePropertyTree == null)||(!startDatePropertyTree.isLeaf())))) {
  _other.startDate = ((this.startDate == null)?null:((XMLGregorianCalendar) this.startDate.clone()));
 }
 }
  public<_B >CreateVaccinationPlanSubscriptionType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new CreateVaccinationPlanSubscriptionType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public CreateVaccinationPlanSubscriptionType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >CreateVaccinationPlanSubscriptionType.Builder<_B> copyOf(final CreateVaccinationPlanSubscriptionType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final CreateVaccinationPlanSubscriptionType.Builder<_B> _newBuilder = new CreateVaccinationPlanSubscriptionType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static CreateVaccinationPlanSubscriptionType.Builder<Void> copyExcept(final CreateVaccinationPlanSubscriptionType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static CreateVaccinationPlanSubscriptionType.Builder<Void> copyOnly(final CreateVaccinationPlanSubscriptionType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final CreateVaccinationPlanSubscriptionType _storedValue;
 private Long vaccinationPlanIdentifier;
 private XMLGregorianCalendar startDate;
  public Builder(final _B _parentBuilder, final CreateVaccinationPlanSubscriptionType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     this.startDate = ((_other.startDate == null)?null:((XMLGregorianCalendar) _other.startDate.clone()));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final CreateVaccinationPlanSubscriptionType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     }
     final PropertyTree startDatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("startDate"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(startDatePropertyTree!= null):((startDatePropertyTree == null)||(!startDatePropertyTree.isLeaf())))) {
        this.startDate = ((_other.startDate == null)?null:((XMLGregorianCalendar) _other.startDate.clone()));
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
  protected<_P extends CreateVaccinationPlanSubscriptionType >_P init(final _P _product) {
  _product.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
  _product.startDate = this.startDate;
  return _product;
 }
  /**
 * Sets the new value of "vaccinationPlanIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanIdentifier
 *     New value of the "vaccinationPlanIdentifier" property.
 */
 public CreateVaccinationPlanSubscriptionType.Builder<_B> withVaccinationPlanIdentifier(final Long vaccinationPlanIdentifier) {
  this.vaccinationPlanIdentifier = vaccinationPlanIdentifier;
  return this;
 }
  /**
 * Sets the new value of "startDate" (any previous value will be replaced)
 * 
 * @param startDate
 *     New value of the "startDate" property.
 */
 public CreateVaccinationPlanSubscriptionType.Builder<_B> withStartDate(final XMLGregorianCalendar startDate) {
  this.startDate = startDate;
  return this;
 }
  @Override
 public CreateVaccinationPlanSubscriptionType build() {
  if (_storedValue == null) {
   return this.init(new CreateVaccinationPlanSubscriptionType());
  } else {
   return ((CreateVaccinationPlanSubscriptionType) _storedValue);
  }
 }
  public CreateVaccinationPlanSubscriptionType.Builder<_B> copyOf(final CreateVaccinationPlanSubscriptionType _other) {
  _other.copyTo(this);
  return this;
 }
  public CreateVaccinationPlanSubscriptionType.Builder<_B> copyOf(final CreateVaccinationPlanSubscriptionType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends CreateVaccinationPlanSubscriptionType.Selector<CreateVaccinationPlanSubscriptionType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static CreateVaccinationPlanSubscriptionType.Select _root() {
  return new CreateVaccinationPlanSubscriptionType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, CreateVaccinationPlanSubscriptionType.Selector<TRoot, TParent>> vaccinationPlanIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, CreateVaccinationPlanSubscriptionType.Selector<TRoot, TParent>> startDate = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.vaccinationPlanIdentifier!= null) {
   products.put("vaccinationPlanIdentifier", this.vaccinationPlanIdentifier.init());
  }
  if (this.startDate!= null) {
   products.put("startDate", this.startDate.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, CreateVaccinationPlanSubscriptionType.Selector<TRoot, TParent>> vaccinationPlanIdentifier() {
  return ((this.vaccinationPlanIdentifier == null)?this.vaccinationPlanIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanIdentifier"):this.vaccinationPlanIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, CreateVaccinationPlanSubscriptionType.Selector<TRoot, TParent>> startDate() {
  return ((this.startDate == null)?this.startDate = new com.kscs.util.jaxb.Selector<>(this._root, this, "startDate"):this.startDate);
 }
  }
 }
