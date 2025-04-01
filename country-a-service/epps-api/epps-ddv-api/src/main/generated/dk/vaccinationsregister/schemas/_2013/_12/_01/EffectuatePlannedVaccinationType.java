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
* <p>Java class for EffectuatePlannedVaccinationType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="EffectuatePlannedVaccinationType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccinationType"/>
*         <element name="VaccinationCreate" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationCreateType"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EffectuatePlannedVaccinationType", propOrder = {
 "plannedVaccination",
 "vaccinationCreate"
})
public class EffectuatePlannedVaccinationType {
  @XmlElement(name = "PlannedVaccination", required = true)
 protected PlannedVaccinationType plannedVaccination;
 @XmlElement(name = "VaccinationCreate", required = true)
 protected VaccinationCreateType vaccinationCreate;
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
 * Gets the value of the vaccinationCreate property.
 * 
 * @return
 *     possible object is
 *     {@link VaccinationCreateType }
 *     
 */
 public VaccinationCreateType getVaccinationCreate() {
 return vaccinationCreate;
 }
  /**
 * Sets the value of the vaccinationCreate property.
 * 
 * @param value
 *     allowed object is
 *     {@link VaccinationCreateType }
 *     
 */
 public void setVaccinationCreate(VaccinationCreateType value) {
 this.vaccinationCreate = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final EffectuatePlannedVaccinationType.Builder<_B> _other) {
 _other.plannedVaccination = ((this.plannedVaccination == null)?null:this.plannedVaccination.newCopyBuilder(_other));
 _other.vaccinationCreate = ((this.vaccinationCreate == null)?null:this.vaccinationCreate.newCopyBuilder(_other));
 }
  public<_B >EffectuatePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new EffectuatePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true);
 }
  public EffectuatePlannedVaccinationType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static EffectuatePlannedVaccinationType.Builder<Void> builder() {
 return new EffectuatePlannedVaccinationType.Builder<>(null, null, false);
 }
  public static<_B >EffectuatePlannedVaccinationType.Builder<_B> copyOf(final EffectuatePlannedVaccinationType _other) {
 final EffectuatePlannedVaccinationType.Builder<_B> _newBuilder = new EffectuatePlannedVaccinationType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final EffectuatePlannedVaccinationType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree plannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccination"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationPropertyTree!= null):((plannedVaccinationPropertyTree == null)||(!plannedVaccinationPropertyTree.isLeaf())))) {
  _other.plannedVaccination = ((this.plannedVaccination == null)?null:this.plannedVaccination.newCopyBuilder(_other, plannedVaccinationPropertyTree, _propertyTreeUse));
 }
 final PropertyTree vaccinationCreatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCreate"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCreatePropertyTree!= null):((vaccinationCreatePropertyTree == null)||(!vaccinationCreatePropertyTree.isLeaf())))) {
  _other.vaccinationCreate = ((this.vaccinationCreate == null)?null:this.vaccinationCreate.newCopyBuilder(_other, vaccinationCreatePropertyTree, _propertyTreeUse));
 }
 }
  public<_B >EffectuatePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new EffectuatePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public EffectuatePlannedVaccinationType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >EffectuatePlannedVaccinationType.Builder<_B> copyOf(final EffectuatePlannedVaccinationType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final EffectuatePlannedVaccinationType.Builder<_B> _newBuilder = new EffectuatePlannedVaccinationType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static EffectuatePlannedVaccinationType.Builder<Void> copyExcept(final EffectuatePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static EffectuatePlannedVaccinationType.Builder<Void> copyOnly(final EffectuatePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final EffectuatePlannedVaccinationType _storedValue;
 private PlannedVaccinationType.Builder<EffectuatePlannedVaccinationType.Builder<_B>> plannedVaccination;
 private VaccinationCreateType.Builder<EffectuatePlannedVaccinationType.Builder<_B>> vaccinationCreate;
  public Builder(final _B _parentBuilder, final EffectuatePlannedVaccinationType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.plannedVaccination = ((_other.plannedVaccination == null)?null:_other.plannedVaccination.newCopyBuilder(this));
     this.vaccinationCreate = ((_other.vaccinationCreate == null)?null:_other.vaccinationCreate.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final EffectuatePlannedVaccinationType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree plannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccination"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationPropertyTree!= null):((plannedVaccinationPropertyTree == null)||(!plannedVaccinationPropertyTree.isLeaf())))) {
        this.plannedVaccination = ((_other.plannedVaccination == null)?null:_other.plannedVaccination.newCopyBuilder(this, plannedVaccinationPropertyTree, _propertyTreeUse));
     }
     final PropertyTree vaccinationCreatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCreate"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCreatePropertyTree!= null):((vaccinationCreatePropertyTree == null)||(!vaccinationCreatePropertyTree.isLeaf())))) {
        this.vaccinationCreate = ((_other.vaccinationCreate == null)?null:_other.vaccinationCreate.newCopyBuilder(this, vaccinationCreatePropertyTree, _propertyTreeUse));
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
  protected<_P extends EffectuatePlannedVaccinationType >_P init(final _P _product) {
  _product.plannedVaccination = ((this.plannedVaccination == null)?null:this.plannedVaccination.build());
  _product.vaccinationCreate = ((this.vaccinationCreate == null)?null:this.vaccinationCreate.build());
  return _product;
 }
  /**
 * Sets the new value of "plannedVaccination" (any previous value will be replaced)
 * 
 * @param plannedVaccination
 *     New value of the "plannedVaccination" property.
 */
 public EffectuatePlannedVaccinationType.Builder<_B> withPlannedVaccination(final PlannedVaccinationType plannedVaccination) {
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
 public PlannedVaccinationType.Builder<? extends EffectuatePlannedVaccinationType.Builder<_B>> withPlannedVaccination() {
  if (this.plannedVaccination!= null) {
   return this.plannedVaccination;
  }
  return this.plannedVaccination = new PlannedVaccinationType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "vaccinationCreate" (any previous value will be replaced)
 * 
 * @param vaccinationCreate
 *     New value of the "vaccinationCreate" property.
 */
 public EffectuatePlannedVaccinationType.Builder<_B> withVaccinationCreate(final VaccinationCreateType vaccinationCreate) {
  this.vaccinationCreate = ((vaccinationCreate == null)?null:new VaccinationCreateType.Builder<>(this, vaccinationCreate, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "vaccinationCreate" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.VaccinationCreateType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "vaccinationCreate" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.VaccinationCreateType.Builder#end()}
 *     to return to the current builder.
 */
 public VaccinationCreateType.Builder<? extends EffectuatePlannedVaccinationType.Builder<_B>> withVaccinationCreate() {
  if (this.vaccinationCreate!= null) {
   return this.vaccinationCreate;
  }
  return this.vaccinationCreate = new VaccinationCreateType.Builder<>(this, null, false);
 }
  @Override
 public EffectuatePlannedVaccinationType build() {
  if (_storedValue == null) {
   return this.init(new EffectuatePlannedVaccinationType());
  } else {
   return ((EffectuatePlannedVaccinationType) _storedValue);
  }
 }
  public EffectuatePlannedVaccinationType.Builder<_B> copyOf(final EffectuatePlannedVaccinationType _other) {
  _other.copyTo(this);
  return this;
 }
  public EffectuatePlannedVaccinationType.Builder<_B> copyOf(final EffectuatePlannedVaccinationType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends EffectuatePlannedVaccinationType.Selector<EffectuatePlannedVaccinationType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static EffectuatePlannedVaccinationType.Select _root() {
  return new EffectuatePlannedVaccinationType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private PlannedVaccinationType.Selector<TRoot, EffectuatePlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccination = null;
 private VaccinationCreateType.Selector<TRoot, EffectuatePlannedVaccinationType.Selector<TRoot, TParent>> vaccinationCreate = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.plannedVaccination!= null) {
   products.put("plannedVaccination", this.plannedVaccination.init());
  }
  if (this.vaccinationCreate!= null) {
   products.put("vaccinationCreate", this.vaccinationCreate.init());
  }
  return products;
 }
  public PlannedVaccinationType.Selector<TRoot, EffectuatePlannedVaccinationType.Selector<TRoot, TParent>> plannedVaccination() {
  return ((this.plannedVaccination == null)?this.plannedVaccination = new PlannedVaccinationType.Selector<>(this._root, this, "plannedVaccination"):this.plannedVaccination);
 }
  public VaccinationCreateType.Selector<TRoot, EffectuatePlannedVaccinationType.Selector<TRoot, TParent>> vaccinationCreate() {
  return ((this.vaccinationCreate == null)?this.vaccinationCreate = new VaccinationCreateType.Selector<>(this._root, this, "vaccinationCreate"):this.vaccinationCreate);
 }
  }
 }
