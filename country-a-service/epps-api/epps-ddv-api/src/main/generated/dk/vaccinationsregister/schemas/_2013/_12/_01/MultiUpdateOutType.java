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
* <p>Java class for MultiUpdateOutType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="MultiUpdateOutType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <choice>
*         <element name="Vaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationType"/>
*         <element name="PlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccinationType"/>
*         <element name="VaccinationPlanIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanIdentifierType"/>
*       </choice>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultiUpdateOutType", propOrder = {
 "vaccination",
 "plannedVaccination",
 "vaccinationPlanIdentifier"
})
public class MultiUpdateOutType {
  @XmlElement(name = "Vaccination")
 protected VaccinationType vaccination;
 @XmlElement(name = "PlannedVaccination")
 protected PlannedVaccinationType plannedVaccination;
 @XmlElement(name = "VaccinationPlanIdentifier")
 protected Long vaccinationPlanIdentifier;
  /**
 * Gets the value of the vaccination property.
 * 
 * @return
 *     possible object is
 *     {@link VaccinationType }
 *     
 */
 public VaccinationType getVaccination() {
 return vaccination;
 }
  /**
 * Sets the value of the vaccination property.
 * 
 * @param value
 *     allowed object is
 *     {@link VaccinationType }
 *     
 */
 public void setVaccination(VaccinationType value) {
 this.vaccination = value;
 }
  /**
 * Gets the value of the plannedVaccination property.
 * 
 * @return
 *     possible object is
 *     {@link PlannedVaccinationType }
 *     
 */
 public PlannedVaccinationType getPlannedVaccination() {
 return plannedVaccination;
 }
  /**
 * Sets the value of the plannedVaccination property.
 * 
 * @param value
 *     allowed object is
 *     {@link PlannedVaccinationType }
 *     
 */
 public void setPlannedVaccination(PlannedVaccinationType value) {
 this.plannedVaccination = value;
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
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final MultiUpdateOutType.Builder<_B> _other) {
 _other.vaccination = ((this.vaccination == null)?null:this.vaccination.newCopyBuilder(_other));
 _other.plannedVaccination = ((this.plannedVaccination == null)?null:this.plannedVaccination.newCopyBuilder(_other));
 _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 }
  public<_B >MultiUpdateOutType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new MultiUpdateOutType.Builder<_B>(_parentBuilder, this, true);
 }
  public MultiUpdateOutType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static MultiUpdateOutType.Builder<Void> builder() {
 return new MultiUpdateOutType.Builder<>(null, null, false);
 }
  public static<_B >MultiUpdateOutType.Builder<_B> copyOf(final MultiUpdateOutType _other) {
 final MultiUpdateOutType.Builder<_B> _newBuilder = new MultiUpdateOutType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final MultiUpdateOutType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccination"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPropertyTree!= null):((vaccinationPropertyTree == null)||(!vaccinationPropertyTree.isLeaf())))) {
  _other.vaccination = ((this.vaccination == null)?null:this.vaccination.newCopyBuilder(_other, vaccinationPropertyTree, _propertyTreeUse));
 }
 final PropertyTree plannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccination"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationPropertyTree!= null):((plannedVaccinationPropertyTree == null)||(!plannedVaccinationPropertyTree.isLeaf())))) {
  _other.plannedVaccination = ((this.plannedVaccination == null)?null:this.plannedVaccination.newCopyBuilder(_other, plannedVaccinationPropertyTree, _propertyTreeUse));
 }
 final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 }
 }
  public<_B >MultiUpdateOutType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new MultiUpdateOutType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public MultiUpdateOutType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >MultiUpdateOutType.Builder<_B> copyOf(final MultiUpdateOutType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final MultiUpdateOutType.Builder<_B> _newBuilder = new MultiUpdateOutType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static MultiUpdateOutType.Builder<Void> copyExcept(final MultiUpdateOutType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static MultiUpdateOutType.Builder<Void> copyOnly(final MultiUpdateOutType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final MultiUpdateOutType _storedValue;
 private VaccinationType.Builder<MultiUpdateOutType.Builder<_B>> vaccination;
 private PlannedVaccinationType.Builder<MultiUpdateOutType.Builder<_B>> plannedVaccination;
 private Long vaccinationPlanIdentifier;
  public Builder(final _B _parentBuilder, final MultiUpdateOutType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccination = ((_other.vaccination == null)?null:_other.vaccination.newCopyBuilder(this));
     this.plannedVaccination = ((_other.plannedVaccination == null)?null:_other.plannedVaccination.newCopyBuilder(this));
     this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final MultiUpdateOutType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccination"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPropertyTree!= null):((vaccinationPropertyTree == null)||(!vaccinationPropertyTree.isLeaf())))) {
        this.vaccination = ((_other.vaccination == null)?null:_other.vaccination.newCopyBuilder(this, vaccinationPropertyTree, _propertyTreeUse));
     }
     final PropertyTree plannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccination"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationPropertyTree!= null):((plannedVaccinationPropertyTree == null)||(!plannedVaccinationPropertyTree.isLeaf())))) {
        this.plannedVaccination = ((_other.plannedVaccination == null)?null:_other.plannedVaccination.newCopyBuilder(this, plannedVaccinationPropertyTree, _propertyTreeUse));
     }
     final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
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
  protected<_P extends MultiUpdateOutType >_P init(final _P _product) {
  _product.vaccination = ((this.vaccination == null)?null:this.vaccination.build());
  _product.plannedVaccination = ((this.plannedVaccination == null)?null:this.plannedVaccination.build());
  _product.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
  return _product;
 }
  /**
 * Sets the new value of "vaccination" (any previous value will be replaced)
 * 
 * @param vaccination
 *     New value of the "vaccination" property.
 */
 public MultiUpdateOutType.Builder<_B> withVaccination(final VaccinationType vaccination) {
  this.vaccination = ((vaccination == null)?null:new VaccinationType.Builder<>(this, vaccination, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "vaccination" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.VaccinationType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "vaccination" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.VaccinationType.Builder#end()} to
 *     return to the current builder.
 */
 public VaccinationType.Builder<? extends MultiUpdateOutType.Builder<_B>> withVaccination() {
  if (this.vaccination!= null) {
   return this.vaccination;
  }
  return this.vaccination = new VaccinationType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "plannedVaccination" (any previous value will be replaced)
 * 
 * @param plannedVaccination
 *     New value of the "plannedVaccination" property.
 */
 public MultiUpdateOutType.Builder<_B> withPlannedVaccination(final PlannedVaccinationType plannedVaccination) {
  this.plannedVaccination = ((plannedVaccination == null)?null:new PlannedVaccinationType.Builder<>(this, plannedVaccination, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "plannedVaccination" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.PlannedVaccinationType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "plannedVaccination" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.PlannedVaccinationType.Builder#end()}
 *     to return to the current builder.
 */
 public PlannedVaccinationType.Builder<? extends MultiUpdateOutType.Builder<_B>> withPlannedVaccination() {
  if (this.plannedVaccination!= null) {
   return this.plannedVaccination;
  }
  return this.plannedVaccination = new PlannedVaccinationType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "vaccinationPlanIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanIdentifier
 *     New value of the "vaccinationPlanIdentifier" property.
 */
 public MultiUpdateOutType.Builder<_B> withVaccinationPlanIdentifier(final Long vaccinationPlanIdentifier) {
  this.vaccinationPlanIdentifier = vaccinationPlanIdentifier;
  return this;
 }
  @Override
 public MultiUpdateOutType build() {
  if (_storedValue == null) {
   return this.init(new MultiUpdateOutType());
  } else {
   return ((MultiUpdateOutType) _storedValue);
  }
 }
  public MultiUpdateOutType.Builder<_B> copyOf(final MultiUpdateOutType _other) {
  _other.copyTo(this);
  return this;
 }
  public MultiUpdateOutType.Builder<_B> copyOf(final MultiUpdateOutType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends MultiUpdateOutType.Selector<MultiUpdateOutType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static MultiUpdateOutType.Select _root() {
  return new MultiUpdateOutType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private VaccinationType.Selector<TRoot, MultiUpdateOutType.Selector<TRoot, TParent>> vaccination = null;
 private PlannedVaccinationType.Selector<TRoot, MultiUpdateOutType.Selector<TRoot, TParent>> plannedVaccination = null;
 private com.kscs.util.jaxb.Selector<TRoot, MultiUpdateOutType.Selector<TRoot, TParent>> vaccinationPlanIdentifier = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.vaccination!= null) {
   products.put("vaccination", this.vaccination.init());
  }
  if (this.plannedVaccination!= null) {
   products.put("plannedVaccination", this.plannedVaccination.init());
  }
  if (this.vaccinationPlanIdentifier!= null) {
   products.put("vaccinationPlanIdentifier", this.vaccinationPlanIdentifier.init());
  }
  return products;
 }
  public VaccinationType.Selector<TRoot, MultiUpdateOutType.Selector<TRoot, TParent>> vaccination() {
  return ((this.vaccination == null)?this.vaccination = new VaccinationType.Selector<>(this._root, this, "vaccination"):this.vaccination);
 }
  public PlannedVaccinationType.Selector<TRoot, MultiUpdateOutType.Selector<TRoot, TParent>> plannedVaccination() {
  return ((this.plannedVaccination == null)?this.plannedVaccination = new PlannedVaccinationType.Selector<>(this._root, this, "plannedVaccination"):this.plannedVaccination);
 }
  public com.kscs.util.jaxb.Selector<TRoot, MultiUpdateOutType.Selector<TRoot, TParent>> vaccinationPlanIdentifier() {
  return ((this.vaccinationPlanIdentifier == null)?this.vaccinationPlanIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanIdentifier"):this.vaccinationPlanIdentifier);
 }
  }
 }
