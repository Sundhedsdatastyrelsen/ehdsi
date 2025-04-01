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
* <p>Java class for NegativeConsentStructureType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="NegativeConsentStructureType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccinationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationIdentifierType" maxOccurs="unbounded"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NegativeConsentStructureType", propOrder = {
 "vaccinationIdentifier"
})
public class NegativeConsentStructureType {
  @XmlElement(name = "VaccinationIdentifier", type = Long.class)
 protected List<Long> vaccinationIdentifier;
  /**
 * Gets the value of the vaccinationIdentifier property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the vaccinationIdentifier property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getVaccinationIdentifier().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link Long }
 * 
 * 
 * @return
 *     The value of the vaccinationIdentifier property.
 */
 public List<Long> getVaccinationIdentifier() {
 if (vaccinationIdentifier == null) {
  vaccinationIdentifier = new ArrayList<>();
 }
 return this.vaccinationIdentifier;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final NegativeConsentStructureType.Builder<_B> _other) {
 if (this.vaccinationIdentifier == null) {
  _other.vaccinationIdentifier = null;
 } else {
  _other.vaccinationIdentifier = new ArrayList<>();
  for (Long _item: this.vaccinationIdentifier) {
   _other.vaccinationIdentifier.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
  }
 }
 }
  public<_B >NegativeConsentStructureType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new NegativeConsentStructureType.Builder<_B>(_parentBuilder, this, true);
 }
  public NegativeConsentStructureType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static NegativeConsentStructureType.Builder<Void> builder() {
 return new NegativeConsentStructureType.Builder<>(null, null, false);
 }
  public static<_B >NegativeConsentStructureType.Builder<_B> copyOf(final NegativeConsentStructureType _other) {
 final NegativeConsentStructureType.Builder<_B> _newBuilder = new NegativeConsentStructureType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final NegativeConsentStructureType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationIdentifierPropertyTree!= null):((vaccinationIdentifierPropertyTree == null)||(!vaccinationIdentifierPropertyTree.isLeaf())))) {
  if (this.vaccinationIdentifier == null) {
   _other.vaccinationIdentifier = null;
  } else {
   _other.vaccinationIdentifier = new ArrayList<>();
   for (Long _item: this.vaccinationIdentifier) {
     _other.vaccinationIdentifier.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
   }
  }
 }
 }
  public<_B >NegativeConsentStructureType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new NegativeConsentStructureType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public NegativeConsentStructureType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >NegativeConsentStructureType.Builder<_B> copyOf(final NegativeConsentStructureType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final NegativeConsentStructureType.Builder<_B> _newBuilder = new NegativeConsentStructureType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static NegativeConsentStructureType.Builder<Void> copyExcept(final NegativeConsentStructureType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static NegativeConsentStructureType.Builder<Void> copyOnly(final NegativeConsentStructureType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final NegativeConsentStructureType _storedValue;
 private List<Buildable> vaccinationIdentifier;
  public Builder(final _B _parentBuilder, final NegativeConsentStructureType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     if (_other.vaccinationIdentifier == null) {
        this.vaccinationIdentifier = null;
     } else {
        this.vaccinationIdentifier = new ArrayList<>();
        for (Long _item: _other.vaccinationIdentifier) {
             this.vaccinationIdentifier.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final NegativeConsentStructureType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationIdentifierPropertyTree!= null):((vaccinationIdentifierPropertyTree == null)||(!vaccinationIdentifierPropertyTree.isLeaf())))) {
        if (_other.vaccinationIdentifier == null) {
             this.vaccinationIdentifier = null;
        } else {
             this.vaccinationIdentifier = new ArrayList<>();
             for (Long _item: _other.vaccinationIdentifier) {
                     this.vaccinationIdentifier.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
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
  protected<_P extends NegativeConsentStructureType >_P init(final _P _product) {
  if (this.vaccinationIdentifier!= null) {
   final List<Long> vaccinationIdentifier = new ArrayList<>(this.vaccinationIdentifier.size());
   for (Buildable _item: this.vaccinationIdentifier) {
     vaccinationIdentifier.add(((Long) _item.build()));
   }
   _product.vaccinationIdentifier = vaccinationIdentifier;
  }
  return _product;
 }
  /**
 * Adds the given items to the value of "vaccinationIdentifier"
 * 
 * @param vaccinationIdentifier
 *     Items to add to the value of the "vaccinationIdentifier" property
 */
 public NegativeConsentStructureType.Builder<_B> addVaccinationIdentifier(final Iterable<? extends Long> vaccinationIdentifier) {
  if (vaccinationIdentifier!= null) {
   if (this.vaccinationIdentifier == null) {
     this.vaccinationIdentifier = new ArrayList<>();
   }
   for (Long _item: vaccinationIdentifier) {
     this.vaccinationIdentifier.add(new Buildable.PrimitiveBuildable(_item));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "vaccinationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationIdentifier
 *     New value of the "vaccinationIdentifier" property.
 */
 public NegativeConsentStructureType.Builder<_B> withVaccinationIdentifier(final Iterable<? extends Long> vaccinationIdentifier) {
  if (this.vaccinationIdentifier!= null) {
   this.vaccinationIdentifier.clear();
  }
  return addVaccinationIdentifier(vaccinationIdentifier);
 }
  /**
 * Adds the given items to the value of "vaccinationIdentifier"
 * 
 * @param vaccinationIdentifier
 *     Items to add to the value of the "vaccinationIdentifier" property
 */
 public NegativeConsentStructureType.Builder<_B> addVaccinationIdentifier(Long... vaccinationIdentifier) {
  addVaccinationIdentifier(Arrays.asList(vaccinationIdentifier));
  return this;
 }
  /**
 * Sets the new value of "vaccinationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationIdentifier
 *     New value of the "vaccinationIdentifier" property.
 */
 public NegativeConsentStructureType.Builder<_B> withVaccinationIdentifier(Long... vaccinationIdentifier) {
  withVaccinationIdentifier(Arrays.asList(vaccinationIdentifier));
  return this;
 }
  @Override
 public NegativeConsentStructureType build() {
  if (_storedValue == null) {
   return this.init(new NegativeConsentStructureType());
  } else {
   return ((NegativeConsentStructureType) _storedValue);
  }
 }
  public NegativeConsentStructureType.Builder<_B> copyOf(final NegativeConsentStructureType _other) {
  _other.copyTo(this);
  return this;
 }
  public NegativeConsentStructureType.Builder<_B> copyOf(final NegativeConsentStructureType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends NegativeConsentStructureType.Selector<NegativeConsentStructureType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static NegativeConsentStructureType.Select _root() {
  return new NegativeConsentStructureType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, NegativeConsentStructureType.Selector<TRoot, TParent>> vaccinationIdentifier = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.vaccinationIdentifier!= null) {
   products.put("vaccinationIdentifier", this.vaccinationIdentifier.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, NegativeConsentStructureType.Selector<TRoot, TParent>> vaccinationIdentifier() {
  return ((this.vaccinationIdentifier == null)?this.vaccinationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationIdentifier"):this.vaccinationIdentifier);
 }
  }
 }
