package dk.vaccinationsregister.schemas._2013._12._01;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for SubscribeAndCreatePlannedVaccinationsResponseType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="SubscribeAndCreatePlannedVaccinationsResponseType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VersionMismatchWarningIndicator" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VersionMismatchWarningIndicatorType" minOccurs="0"/>
*         <element name="PlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccinationType" maxOccurs="unbounded" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscribeAndCreatePlannedVaccinationsResponseType", propOrder = {
 "versionMismatchWarningIndicator",
 "plannedVaccination"
})
public class SubscribeAndCreatePlannedVaccinationsResponseType {
  @XmlElement(name = "VersionMismatchWarningIndicator")
 protected VersionMismatchWarningIndicatorType versionMismatchWarningIndicator;
 @XmlElement(name = "PlannedVaccination")
 protected List<PlannedVaccinationType> plannedVaccination;
  /**
 * Gets the value of the versionMismatchWarningIndicator property.
 * 
 * @return
 *     possible object is
 *     {@link VersionMismatchWarningIndicatorType }
 *     
 */
 public VersionMismatchWarningIndicatorType getVersionMismatchWarningIndicator() {
 return versionMismatchWarningIndicator;
 }
  /**
 * Sets the value of the versionMismatchWarningIndicator property.
 * 
 * @param value
 *     allowed object is
 *     {@link VersionMismatchWarningIndicatorType }
 *     
 */
 public void setVersionMismatchWarningIndicator(VersionMismatchWarningIndicatorType value) {
 this.versionMismatchWarningIndicator = value;
 }
  /**
 * Gets the value of the plannedVaccination property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the plannedVaccination property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getPlannedVaccination().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link PlannedVaccinationType }
 * 
 * 
 * @return
 *     The value of the plannedVaccination property.
 */
 public List<PlannedVaccinationType> getPlannedVaccination() {
 if (plannedVaccination == null) {
  plannedVaccination = new ArrayList<>();
 }
 return this.plannedVaccination;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> _other) {
 _other.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.newCopyBuilder(_other));
 if (this.plannedVaccination == null) {
  _other.plannedVaccination = null;
 } else {
  _other.plannedVaccination = new ArrayList<>();
  for (PlannedVaccinationType _item: this.plannedVaccination) {
   _other.plannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B>(_parentBuilder, this, true);
 }
  public SubscribeAndCreatePlannedVaccinationsResponseType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static SubscribeAndCreatePlannedVaccinationsResponseType.Builder<Void> builder() {
 return new SubscribeAndCreatePlannedVaccinationsResponseType.Builder<>(null, null, false);
 }
  public static<_B >SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> copyOf(final SubscribeAndCreatePlannedVaccinationsResponseType _other) {
 final SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> _newBuilder = new SubscribeAndCreatePlannedVaccinationsResponseType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree versionMismatchWarningIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("versionMismatchWarningIndicator"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(versionMismatchWarningIndicatorPropertyTree!= null):((versionMismatchWarningIndicatorPropertyTree == null)||(!versionMismatchWarningIndicatorPropertyTree.isLeaf())))) {
  _other.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.newCopyBuilder(_other, versionMismatchWarningIndicatorPropertyTree, _propertyTreeUse));
 }
 final PropertyTree plannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccination"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationPropertyTree!= null):((plannedVaccinationPropertyTree == null)||(!plannedVaccinationPropertyTree.isLeaf())))) {
  if (this.plannedVaccination == null) {
   _other.plannedVaccination = null;
  } else {
   _other.plannedVaccination = new ArrayList<>();
   for (PlannedVaccinationType _item: this.plannedVaccination) {
     _other.plannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(_other, plannedVaccinationPropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public SubscribeAndCreatePlannedVaccinationsResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> copyOf(final SubscribeAndCreatePlannedVaccinationsResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> _newBuilder = new SubscribeAndCreatePlannedVaccinationsResponseType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static SubscribeAndCreatePlannedVaccinationsResponseType.Builder<Void> copyExcept(final SubscribeAndCreatePlannedVaccinationsResponseType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static SubscribeAndCreatePlannedVaccinationsResponseType.Builder<Void> copyOnly(final SubscribeAndCreatePlannedVaccinationsResponseType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final SubscribeAndCreatePlannedVaccinationsResponseType _storedValue;
 private VersionMismatchWarningIndicatorType.Builder<SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B>> versionMismatchWarningIndicator;
 private List<PlannedVaccinationType.Builder<SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B>>> plannedVaccination;
  public Builder(final _B _parentBuilder, final SubscribeAndCreatePlannedVaccinationsResponseType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.versionMismatchWarningIndicator = ((_other.versionMismatchWarningIndicator == null)?null:_other.versionMismatchWarningIndicator.newCopyBuilder(this));
     if (_other.plannedVaccination == null) {
        this.plannedVaccination = null;
     } else {
        this.plannedVaccination = new ArrayList<>();
        for (PlannedVaccinationType _item: _other.plannedVaccination) {
             this.plannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final SubscribeAndCreatePlannedVaccinationsResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree versionMismatchWarningIndicatorPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("versionMismatchWarningIndicator"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(versionMismatchWarningIndicatorPropertyTree!= null):((versionMismatchWarningIndicatorPropertyTree == null)||(!versionMismatchWarningIndicatorPropertyTree.isLeaf())))) {
        this.versionMismatchWarningIndicator = ((_other.versionMismatchWarningIndicator == null)?null:_other.versionMismatchWarningIndicator.newCopyBuilder(this, versionMismatchWarningIndicatorPropertyTree, _propertyTreeUse));
     }
     final PropertyTree plannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccination"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationPropertyTree!= null):((plannedVaccinationPropertyTree == null)||(!plannedVaccinationPropertyTree.isLeaf())))) {
        if (_other.plannedVaccination == null) {
             this.plannedVaccination = null;
        } else {
             this.plannedVaccination = new ArrayList<>();
             for (PlannedVaccinationType _item: _other.plannedVaccination) {
                     this.plannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(this, plannedVaccinationPropertyTree, _propertyTreeUse)));
             }
        }
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
  protected<_P extends SubscribeAndCreatePlannedVaccinationsResponseType >_P init(final _P _product) {
  _product.versionMismatchWarningIndicator = ((this.versionMismatchWarningIndicator == null)?null:this.versionMismatchWarningIndicator.build());
  if (this.plannedVaccination!= null) {
   final List<PlannedVaccinationType> plannedVaccination = new ArrayList<>(this.plannedVaccination.size());
   for (PlannedVaccinationType.Builder<SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B>> _item: this.plannedVaccination) {
     plannedVaccination.add(_item.build());
   }
   _product.plannedVaccination = plannedVaccination;
  }
  return _product;
 }
  /**
 * Sets the new value of "versionMismatchWarningIndicator" (any previous value will
 * be replaced)
 * 
 * @param versionMismatchWarningIndicator
 *     New value of the "versionMismatchWarningIndicator" property.
 */
 public SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> withVersionMismatchWarningIndicator(final VersionMismatchWarningIndicatorType versionMismatchWarningIndicator) {
  this.versionMismatchWarningIndicator = ((versionMismatchWarningIndicator == null)?null:new VersionMismatchWarningIndicatorType.Builder<>(this, versionMismatchWarningIndicator, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "versionMismatchWarningIndicator" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.VersionMismatchWarningIndicatorType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "versionMismatchWarningIndicator"
 *     property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.VersionMismatchWarningIndicatorType.Builder#end()}
 *     to return to the current builder.
 */
 public VersionMismatchWarningIndicatorType.Builder<? extends SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B>> withVersionMismatchWarningIndicator() {
  if (this.versionMismatchWarningIndicator!= null) {
   return this.versionMismatchWarningIndicator;
  }
  return this.versionMismatchWarningIndicator = new VersionMismatchWarningIndicatorType.Builder<>(this, null, false);
 }
  /**
 * Adds the given items to the value of "plannedVaccination"
 * 
 * @param plannedVaccination
 *     Items to add to the value of the "plannedVaccination" property
 */
 public SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> addPlannedVaccination(final Iterable<? extends PlannedVaccinationType> plannedVaccination) {
  if (plannedVaccination!= null) {
   if (this.plannedVaccination == null) {
     this.plannedVaccination = new ArrayList<>();
   }
   for (PlannedVaccinationType _item: plannedVaccination) {
     this.plannedVaccination.add(new PlannedVaccinationType.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "plannedVaccination" (any previous value will be replaced)
 * 
 * @param plannedVaccination
 *     New value of the "plannedVaccination" property.
 */
 public SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> withPlannedVaccination(final Iterable<? extends PlannedVaccinationType> plannedVaccination) {
  if (this.plannedVaccination!= null) {
   this.plannedVaccination.clear();
  }
  return addPlannedVaccination(plannedVaccination);
 }
  /**
 * Adds the given items to the value of "plannedVaccination"
 * 
 * @param plannedVaccination
 *     Items to add to the value of the "plannedVaccination" property
 */
 public SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> addPlannedVaccination(PlannedVaccinationType... plannedVaccination) {
  addPlannedVaccination(Arrays.asList(plannedVaccination));
  return this;
 }
  /**
 * Sets the new value of "plannedVaccination" (any previous value will be replaced)
 * 
 * @param plannedVaccination
 *     New value of the "plannedVaccination" property.
 */
 public SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> withPlannedVaccination(PlannedVaccinationType... plannedVaccination) {
  withPlannedVaccination(Arrays.asList(plannedVaccination));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "PlannedVaccination"
 * property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.PlannedVaccinationType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "PlannedVaccination" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.PlannedVaccinationType.Builder#end()}
 *     to return to the current builder.
 */
 public PlannedVaccinationType.Builder<? extends SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B>> addPlannedVaccination() {
  if (this.plannedVaccination == null) {
   this.plannedVaccination = new ArrayList<>();
  }
  final PlannedVaccinationType.Builder<SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B>> plannedVaccination_Builder = new PlannedVaccinationType.Builder<>(this, null, false);
  this.plannedVaccination.add(plannedVaccination_Builder);
  return plannedVaccination_Builder;
 }
  @Override
 public SubscribeAndCreatePlannedVaccinationsResponseType build() {
  if (_storedValue == null) {
   return this.init(new SubscribeAndCreatePlannedVaccinationsResponseType());
  } else {
   return ((SubscribeAndCreatePlannedVaccinationsResponseType) _storedValue);
  }
 }
  public SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> copyOf(final SubscribeAndCreatePlannedVaccinationsResponseType _other) {
  _other.copyTo(this);
  return this;
 }
  public SubscribeAndCreatePlannedVaccinationsResponseType.Builder<_B> copyOf(final SubscribeAndCreatePlannedVaccinationsResponseType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends SubscribeAndCreatePlannedVaccinationsResponseType.Selector<SubscribeAndCreatePlannedVaccinationsResponseType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static SubscribeAndCreatePlannedVaccinationsResponseType.Select _root() {
  return new SubscribeAndCreatePlannedVaccinationsResponseType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private VersionMismatchWarningIndicatorType.Selector<TRoot, SubscribeAndCreatePlannedVaccinationsResponseType.Selector<TRoot, TParent>> versionMismatchWarningIndicator = null;
 private PlannedVaccinationType.Selector<TRoot, SubscribeAndCreatePlannedVaccinationsResponseType.Selector<TRoot, TParent>> plannedVaccination = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.versionMismatchWarningIndicator!= null) {
   products.put("versionMismatchWarningIndicator", this.versionMismatchWarningIndicator.init());
  }
  if (this.plannedVaccination!= null) {
   products.put("plannedVaccination", this.plannedVaccination.init());
  }
  return products;
 }
  public VersionMismatchWarningIndicatorType.Selector<TRoot, SubscribeAndCreatePlannedVaccinationsResponseType.Selector<TRoot, TParent>> versionMismatchWarningIndicator() {
  return ((this.versionMismatchWarningIndicator == null)?this.versionMismatchWarningIndicator = new VersionMismatchWarningIndicatorType.Selector<>(this._root, this, "versionMismatchWarningIndicator"):this.versionMismatchWarningIndicator);
 }
  public PlannedVaccinationType.Selector<TRoot, SubscribeAndCreatePlannedVaccinationsResponseType.Selector<TRoot, TParent>> plannedVaccination() {
  return ((this.plannedVaccination == null)?this.plannedVaccination = new PlannedVaccinationType.Selector<>(this._root, this, "plannedVaccination"):this.plannedVaccination);
 }
  }
 }
