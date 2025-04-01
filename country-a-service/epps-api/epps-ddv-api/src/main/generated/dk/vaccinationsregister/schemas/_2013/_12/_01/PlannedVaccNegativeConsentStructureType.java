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
* <p>Java class for PlannedVaccNegativeConsentStructureType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="PlannedVaccNegativeConsentStructureType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PlannedVaccinationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccinationIdentifierType" maxOccurs="unbounded"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlannedVaccNegativeConsentStructureType", propOrder = {
 "plannedVaccinationIdentifier"
})
public class PlannedVaccNegativeConsentStructureType {
  @XmlElement(name = "PlannedVaccinationIdentifier", type = Long.class)
 protected List<Long> plannedVaccinationIdentifier;
  /**
 * Gets the value of the plannedVaccinationIdentifier property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the plannedVaccinationIdentifier property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getPlannedVaccinationIdentifier().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link Long }
 * 
 * 
 * @return
 *     The value of the plannedVaccinationIdentifier property.
 */
 public List<Long> getPlannedVaccinationIdentifier() {
 if (plannedVaccinationIdentifier == null) {
  plannedVaccinationIdentifier = new ArrayList<>();
 }
 return this.plannedVaccinationIdentifier;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final PlannedVaccNegativeConsentStructureType.Builder<_B> _other) {
 if (this.plannedVaccinationIdentifier == null) {
  _other.plannedVaccinationIdentifier = null;
 } else {
  _other.plannedVaccinationIdentifier = new ArrayList<>();
  for (Long _item: this.plannedVaccinationIdentifier) {
   _other.plannedVaccinationIdentifier.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
  }
 }
 }
  public<_B >PlannedVaccNegativeConsentStructureType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new PlannedVaccNegativeConsentStructureType.Builder<_B>(_parentBuilder, this, true);
 }
  public PlannedVaccNegativeConsentStructureType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static PlannedVaccNegativeConsentStructureType.Builder<Void> builder() {
 return new PlannedVaccNegativeConsentStructureType.Builder<>(null, null, false);
 }
  public static<_B >PlannedVaccNegativeConsentStructureType.Builder<_B> copyOf(final PlannedVaccNegativeConsentStructureType _other) {
 final PlannedVaccNegativeConsentStructureType.Builder<_B> _newBuilder = new PlannedVaccNegativeConsentStructureType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final PlannedVaccNegativeConsentStructureType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree plannedVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationIdentifierPropertyTree!= null):((plannedVaccinationIdentifierPropertyTree == null)||(!plannedVaccinationIdentifierPropertyTree.isLeaf())))) {
  if (this.plannedVaccinationIdentifier == null) {
   _other.plannedVaccinationIdentifier = null;
  } else {
   _other.plannedVaccinationIdentifier = new ArrayList<>();
   for (Long _item: this.plannedVaccinationIdentifier) {
     _other.plannedVaccinationIdentifier.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
   }
  }
 }
 }
  public<_B >PlannedVaccNegativeConsentStructureType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new PlannedVaccNegativeConsentStructureType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public PlannedVaccNegativeConsentStructureType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >PlannedVaccNegativeConsentStructureType.Builder<_B> copyOf(final PlannedVaccNegativeConsentStructureType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PlannedVaccNegativeConsentStructureType.Builder<_B> _newBuilder = new PlannedVaccNegativeConsentStructureType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static PlannedVaccNegativeConsentStructureType.Builder<Void> copyExcept(final PlannedVaccNegativeConsentStructureType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static PlannedVaccNegativeConsentStructureType.Builder<Void> copyOnly(final PlannedVaccNegativeConsentStructureType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final PlannedVaccNegativeConsentStructureType _storedValue;
 private List<Buildable> plannedVaccinationIdentifier;
  public Builder(final _B _parentBuilder, final PlannedVaccNegativeConsentStructureType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     if (_other.plannedVaccinationIdentifier == null) {
        this.plannedVaccinationIdentifier = null;
     } else {
        this.plannedVaccinationIdentifier = new ArrayList<>();
        for (Long _item: _other.plannedVaccinationIdentifier) {
             this.plannedVaccinationIdentifier.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final PlannedVaccNegativeConsentStructureType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree plannedVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationIdentifierPropertyTree!= null):((plannedVaccinationIdentifierPropertyTree == null)||(!plannedVaccinationIdentifierPropertyTree.isLeaf())))) {
        if (_other.plannedVaccinationIdentifier == null) {
             this.plannedVaccinationIdentifier = null;
        } else {
             this.plannedVaccinationIdentifier = new ArrayList<>();
             for (Long _item: _other.plannedVaccinationIdentifier) {
                     this.plannedVaccinationIdentifier.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
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
  protected<_P extends PlannedVaccNegativeConsentStructureType >_P init(final _P _product) {
  if (this.plannedVaccinationIdentifier!= null) {
   final List<Long> plannedVaccinationIdentifier = new ArrayList<>(this.plannedVaccinationIdentifier.size());
   for (Buildable _item: this.plannedVaccinationIdentifier) {
     plannedVaccinationIdentifier.add(((Long) _item.build()));
   }
   _product.plannedVaccinationIdentifier = plannedVaccinationIdentifier;
  }
  return _product;
 }
  /**
 * Adds the given items to the value of "plannedVaccinationIdentifier"
 * 
 * @param plannedVaccinationIdentifier
 *     Items to add to the value of the "plannedVaccinationIdentifier" property
 */
 public PlannedVaccNegativeConsentStructureType.Builder<_B> addPlannedVaccinationIdentifier(final Iterable<? extends Long> plannedVaccinationIdentifier) {
  if (plannedVaccinationIdentifier!= null) {
   if (this.plannedVaccinationIdentifier == null) {
     this.plannedVaccinationIdentifier = new ArrayList<>();
   }
   for (Long _item: plannedVaccinationIdentifier) {
     this.plannedVaccinationIdentifier.add(new Buildable.PrimitiveBuildable(_item));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "plannedVaccinationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param plannedVaccinationIdentifier
 *     New value of the "plannedVaccinationIdentifier" property.
 */
 public PlannedVaccNegativeConsentStructureType.Builder<_B> withPlannedVaccinationIdentifier(final Iterable<? extends Long> plannedVaccinationIdentifier) {
  if (this.plannedVaccinationIdentifier!= null) {
   this.plannedVaccinationIdentifier.clear();
  }
  return addPlannedVaccinationIdentifier(plannedVaccinationIdentifier);
 }
  /**
 * Adds the given items to the value of "plannedVaccinationIdentifier"
 * 
 * @param plannedVaccinationIdentifier
 *     Items to add to the value of the "plannedVaccinationIdentifier" property
 */
 public PlannedVaccNegativeConsentStructureType.Builder<_B> addPlannedVaccinationIdentifier(Long... plannedVaccinationIdentifier) {
  addPlannedVaccinationIdentifier(Arrays.asList(plannedVaccinationIdentifier));
  return this;
 }
  /**
 * Sets the new value of "plannedVaccinationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param plannedVaccinationIdentifier
 *     New value of the "plannedVaccinationIdentifier" property.
 */
 public PlannedVaccNegativeConsentStructureType.Builder<_B> withPlannedVaccinationIdentifier(Long... plannedVaccinationIdentifier) {
  withPlannedVaccinationIdentifier(Arrays.asList(plannedVaccinationIdentifier));
  return this;
 }
  @Override
 public PlannedVaccNegativeConsentStructureType build() {
  if (_storedValue == null) {
   return this.init(new PlannedVaccNegativeConsentStructureType());
  } else {
   return ((PlannedVaccNegativeConsentStructureType) _storedValue);
  }
 }
  public PlannedVaccNegativeConsentStructureType.Builder<_B> copyOf(final PlannedVaccNegativeConsentStructureType _other) {
  _other.copyTo(this);
  return this;
 }
  public PlannedVaccNegativeConsentStructureType.Builder<_B> copyOf(final PlannedVaccNegativeConsentStructureType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends PlannedVaccNegativeConsentStructureType.Selector<PlannedVaccNegativeConsentStructureType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static PlannedVaccNegativeConsentStructureType.Select _root() {
  return new PlannedVaccNegativeConsentStructureType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, PlannedVaccNegativeConsentStructureType.Selector<TRoot, TParent>> plannedVaccinationIdentifier = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.plannedVaccinationIdentifier!= null) {
   products.put("plannedVaccinationIdentifier", this.plannedVaccinationIdentifier.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, PlannedVaccNegativeConsentStructureType.Selector<TRoot, TParent>> plannedVaccinationIdentifier() {
  return ((this.plannedVaccinationIdentifier == null)?this.plannedVaccinationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "plannedVaccinationIdentifier"):this.plannedVaccinationIdentifier);
 }
  }
 }
