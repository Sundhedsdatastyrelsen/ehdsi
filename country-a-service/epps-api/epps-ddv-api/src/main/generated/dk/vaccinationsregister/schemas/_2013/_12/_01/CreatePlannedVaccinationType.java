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
* <p>Java class for CreatePlannedVaccinationType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="CreatePlannedVaccinationType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccinationPlanItemIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemIdentifierType"/>
*         <element name="PlannedVaccinationDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreatePlannedVaccinationType", propOrder = {
 "vaccinationPlanItemIdentifier",
 "plannedVaccinationDateTime"
})
public class CreatePlannedVaccinationType {
  @XmlElement(name = "VaccinationPlanItemIdentifier")
 protected long vaccinationPlanItemIdentifier;
 @XmlElement(name = "PlannedVaccinationDateTime", required = true)
 @XmlSchemaType(name = "dateTime")
 protected XMLGregorianCalendar plannedVaccinationDateTime;
  /**
 * Gets the value of the vaccinationPlanItemIdentifier property.
 * 
 */
 public long getVaccinationPlanItemIdentifier() {
 return vaccinationPlanItemIdentifier;
 }
  /**
 * Sets the value of the vaccinationPlanItemIdentifier property.
 * 
 */
 public void setVaccinationPlanItemIdentifier(long value) {
 this.vaccinationPlanItemIdentifier = value;
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
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final CreatePlannedVaccinationType.Builder<_B> _other) {
 _other.vaccinationPlanItemIdentifier = this.vaccinationPlanItemIdentifier;
 _other.plannedVaccinationDateTime = ((this.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) this.plannedVaccinationDateTime.clone()));
 }
  public<_B >CreatePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new CreatePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true);
 }
  public CreatePlannedVaccinationType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static CreatePlannedVaccinationType.Builder<Void> builder() {
 return new CreatePlannedVaccinationType.Builder<>(null, null, false);
 }
  public static<_B >CreatePlannedVaccinationType.Builder<_B> copyOf(final CreatePlannedVaccinationType _other) {
 final CreatePlannedVaccinationType.Builder<_B> _newBuilder = new CreatePlannedVaccinationType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final CreatePlannedVaccinationType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationPlanItemIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIdentifierPropertyTree!= null):((vaccinationPlanItemIdentifierPropertyTree == null)||(!vaccinationPlanItemIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemIdentifier = this.vaccinationPlanItemIdentifier;
 }
 final PropertyTree plannedVaccinationDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationDateTime"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationDateTimePropertyTree!= null):((plannedVaccinationDateTimePropertyTree == null)||(!plannedVaccinationDateTimePropertyTree.isLeaf())))) {
  _other.plannedVaccinationDateTime = ((this.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) this.plannedVaccinationDateTime.clone()));
 }
 }
  public<_B >CreatePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new CreatePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public CreatePlannedVaccinationType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >CreatePlannedVaccinationType.Builder<_B> copyOf(final CreatePlannedVaccinationType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final CreatePlannedVaccinationType.Builder<_B> _newBuilder = new CreatePlannedVaccinationType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static CreatePlannedVaccinationType.Builder<Void> copyExcept(final CreatePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static CreatePlannedVaccinationType.Builder<Void> copyOnly(final CreatePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final CreatePlannedVaccinationType _storedValue;
 private long vaccinationPlanItemIdentifier;
 private XMLGregorianCalendar plannedVaccinationDateTime;
  public Builder(final _B _parentBuilder, final CreatePlannedVaccinationType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccinationPlanItemIdentifier = _other.vaccinationPlanItemIdentifier;
     this.plannedVaccinationDateTime = ((_other.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) _other.plannedVaccinationDateTime.clone()));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final CreatePlannedVaccinationType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccinationPlanItemIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIdentifierPropertyTree!= null):((vaccinationPlanItemIdentifierPropertyTree == null)||(!vaccinationPlanItemIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemIdentifier = _other.vaccinationPlanItemIdentifier;
     }
     final PropertyTree plannedVaccinationDateTimePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationDateTime"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationDateTimePropertyTree!= null):((plannedVaccinationDateTimePropertyTree == null)||(!plannedVaccinationDateTimePropertyTree.isLeaf())))) {
        this.plannedVaccinationDateTime = ((_other.plannedVaccinationDateTime == null)?null:((XMLGregorianCalendar) _other.plannedVaccinationDateTime.clone()));
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
  protected<_P extends CreatePlannedVaccinationType >_P init(final _P _product) {
  _product.vaccinationPlanItemIdentifier = this.vaccinationPlanItemIdentifier;
  _product.plannedVaccinationDateTime = this.plannedVaccinationDateTime;
  return _product;
 }
  /**
 * Sets the new value of "vaccinationPlanItemIdentifier" (any previous value will
 * be replaced)
 * 
 * @param vaccinationPlanItemIdentifier
 *     New value of the "vaccinationPlanItemIdentifier" property.
 */
 public CreatePlannedVaccinationType.Builder<_B> withVaccinationPlanItemIdentifier(final long vaccinationPlanItemIdentifier) {
  this.vaccinationPlanItemIdentifier = vaccinationPlanItemIdentifier;
  return this;
 }
  /**
 * Sets the new value of "plannedVaccinationDateTime" (any previous value will be
 * replaced)
 * 
 * @param plannedVaccinationDateTime
 *     New value of the "plannedVaccinationDateTime" property.
 */
 public CreatePlannedVaccinationType.Builder<_B> withPlannedVaccinationDateTime(final XMLGregorianCalendar plannedVaccinationDateTime) {
  this.plannedVaccinationDateTime = plannedVaccinationDateTime;
  return this;
 }
  @Override
 public CreatePlannedVaccinationType build() {
  if (_storedValue == null) {
   return this.init(new CreatePlannedVaccinationType());
  } else {
   return ((CreatePlannedVaccinationType) _storedValue);
  }
 }
  public CreatePlannedVaccinationType.Builder<_B> copyOf(final CreatePlannedVaccinationType _other) {
  _other.copyTo(this);
  return this;
 }
  public CreatePlannedVaccinationType.Builder<_B> copyOf(final CreatePlannedVaccinationType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends CreatePlannedVaccinationType.Selector<CreatePlannedVaccinationType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static CreatePlannedVaccinationType.Select _root() {
  return new CreatePlannedVaccinationType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, CreatePlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccinationDateTime = null;
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
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, CreatePlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccinationDateTime() {
  return ((this.plannedVaccinationDateTime == null)?this.plannedVaccinationDateTime = new com.kscs.util.jaxb.Selector<>(this._root, this, "plannedVaccinationDateTime"):this.plannedVaccinationDateTime);
 }
  }
 }
