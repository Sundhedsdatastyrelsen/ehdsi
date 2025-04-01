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
* <p>Java class for SubscribeAndCreatePlannedVaccinationsType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="SubscribeAndCreatePlannedVaccinationsType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccinationPlanIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanIdentifierType"/>
*         <element name="VaccinationPlanVersionIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanVersionIdentifierType"/>
*         <element name="CreatePlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CreatePlannedVaccinationType" maxOccurs="500"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscribeAndCreatePlannedVaccinationsType", propOrder = {
 "vaccinationPlanIdentifier",
 "vaccinationPlanVersionIdentifier",
 "createPlannedVaccination"
})
public class SubscribeAndCreatePlannedVaccinationsType {
  @XmlElement(name = "VaccinationPlanIdentifier")
 protected long vaccinationPlanIdentifier;
 @XmlElement(name = "VaccinationPlanVersionIdentifier")
 protected long vaccinationPlanVersionIdentifier;
 @XmlElement(name = "CreatePlannedVaccination", required = true)
 protected List<CreatePlannedVaccinationType> createPlannedVaccination;
  /**
 * Gets the value of the vaccinationPlanIdentifier property.
 * 
 */
 public long getVaccinationPlanIdentifier() {
 return vaccinationPlanIdentifier;
 }
  /**
 * Sets the value of the vaccinationPlanIdentifier property.
 * 
 */
 public void setVaccinationPlanIdentifier(long value) {
 this.vaccinationPlanIdentifier = value;
 }
  /**
 * Gets the value of the vaccinationPlanVersionIdentifier property.
 * 
 */
 public long getVaccinationPlanVersionIdentifier() {
 return vaccinationPlanVersionIdentifier;
 }
  /**
 * Sets the value of the vaccinationPlanVersionIdentifier property.
 * 
 */
 public void setVaccinationPlanVersionIdentifier(long value) {
 this.vaccinationPlanVersionIdentifier = value;
 }
  /**
 * Gets the value of the createPlannedVaccination property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the createPlannedVaccination property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getCreatePlannedVaccination().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link CreatePlannedVaccinationType }
 * 
 * 
 * @return
 *     The value of the createPlannedVaccination property.
 */
 public List<CreatePlannedVaccinationType> getCreatePlannedVaccination() {
 if (createPlannedVaccination == null) {
  createPlannedVaccination = new ArrayList<>();
 }
 return this.createPlannedVaccination;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final SubscribeAndCreatePlannedVaccinationsType.Builder<_B> _other) {
 _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 _other.vaccinationPlanVersionIdentifier = this.vaccinationPlanVersionIdentifier;
 if (this.createPlannedVaccination == null) {
  _other.createPlannedVaccination = null;
 } else {
  _other.createPlannedVaccination = new ArrayList<>();
  for (CreatePlannedVaccinationType _item: this.createPlannedVaccination) {
   _other.createPlannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >SubscribeAndCreatePlannedVaccinationsType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new SubscribeAndCreatePlannedVaccinationsType.Builder<_B>(_parentBuilder, this, true);
 }
  public SubscribeAndCreatePlannedVaccinationsType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static SubscribeAndCreatePlannedVaccinationsType.Builder<Void> builder() {
 return new SubscribeAndCreatePlannedVaccinationsType.Builder<>(null, null, false);
 }
  public static<_B >SubscribeAndCreatePlannedVaccinationsType.Builder<_B> copyOf(final SubscribeAndCreatePlannedVaccinationsType _other) {
 final SubscribeAndCreatePlannedVaccinationsType.Builder<_B> _newBuilder = new SubscribeAndCreatePlannedVaccinationsType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final SubscribeAndCreatePlannedVaccinationsType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 }
 final PropertyTree vaccinationPlanVersionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanVersionIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanVersionIdentifierPropertyTree!= null):((vaccinationPlanVersionIdentifierPropertyTree == null)||(!vaccinationPlanVersionIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanVersionIdentifier = this.vaccinationPlanVersionIdentifier;
 }
 final PropertyTree createPlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("createPlannedVaccination"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createPlannedVaccinationPropertyTree!= null):((createPlannedVaccinationPropertyTree == null)||(!createPlannedVaccinationPropertyTree.isLeaf())))) {
  if (this.createPlannedVaccination == null) {
   _other.createPlannedVaccination = null;
  } else {
   _other.createPlannedVaccination = new ArrayList<>();
   for (CreatePlannedVaccinationType _item: this.createPlannedVaccination) {
     _other.createPlannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(_other, createPlannedVaccinationPropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >SubscribeAndCreatePlannedVaccinationsType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new SubscribeAndCreatePlannedVaccinationsType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public SubscribeAndCreatePlannedVaccinationsType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >SubscribeAndCreatePlannedVaccinationsType.Builder<_B> copyOf(final SubscribeAndCreatePlannedVaccinationsType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final SubscribeAndCreatePlannedVaccinationsType.Builder<_B> _newBuilder = new SubscribeAndCreatePlannedVaccinationsType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static SubscribeAndCreatePlannedVaccinationsType.Builder<Void> copyExcept(final SubscribeAndCreatePlannedVaccinationsType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static SubscribeAndCreatePlannedVaccinationsType.Builder<Void> copyOnly(final SubscribeAndCreatePlannedVaccinationsType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final SubscribeAndCreatePlannedVaccinationsType _storedValue;
 private long vaccinationPlanIdentifier;
 private long vaccinationPlanVersionIdentifier;
 private List<CreatePlannedVaccinationType.Builder<SubscribeAndCreatePlannedVaccinationsType.Builder<_B>>> createPlannedVaccination;
  public Builder(final _B _parentBuilder, final SubscribeAndCreatePlannedVaccinationsType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     this.vaccinationPlanVersionIdentifier = _other.vaccinationPlanVersionIdentifier;
     if (_other.createPlannedVaccination == null) {
        this.createPlannedVaccination = null;
     } else {
        this.createPlannedVaccination = new ArrayList<>();
        for (CreatePlannedVaccinationType _item: _other.createPlannedVaccination) {
             this.createPlannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final SubscribeAndCreatePlannedVaccinationsType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     }
     final PropertyTree vaccinationPlanVersionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanVersionIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanVersionIdentifierPropertyTree!= null):((vaccinationPlanVersionIdentifierPropertyTree == null)||(!vaccinationPlanVersionIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanVersionIdentifier = _other.vaccinationPlanVersionIdentifier;
     }
     final PropertyTree createPlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("createPlannedVaccination"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createPlannedVaccinationPropertyTree!= null):((createPlannedVaccinationPropertyTree == null)||(!createPlannedVaccinationPropertyTree.isLeaf())))) {
        if (_other.createPlannedVaccination == null) {
             this.createPlannedVaccination = null;
        } else {
             this.createPlannedVaccination = new ArrayList<>();
             for (CreatePlannedVaccinationType _item: _other.createPlannedVaccination) {
                     this.createPlannedVaccination.add(((_item == null)?null:_item.newCopyBuilder(this, createPlannedVaccinationPropertyTree, _propertyTreeUse)));
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
  protected<_P extends SubscribeAndCreatePlannedVaccinationsType >_P init(final _P _product) {
  _product.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
  _product.vaccinationPlanVersionIdentifier = this.vaccinationPlanVersionIdentifier;
  if (this.createPlannedVaccination!= null) {
   final List<CreatePlannedVaccinationType> createPlannedVaccination = new ArrayList<>(this.createPlannedVaccination.size());
   for (CreatePlannedVaccinationType.Builder<SubscribeAndCreatePlannedVaccinationsType.Builder<_B>> _item: this.createPlannedVaccination) {
     createPlannedVaccination.add(_item.build());
   }
   _product.createPlannedVaccination = createPlannedVaccination;
  }
  return _product;
 }
  /**
 * Sets the new value of "vaccinationPlanIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanIdentifier
 *     New value of the "vaccinationPlanIdentifier" property.
 */
 public SubscribeAndCreatePlannedVaccinationsType.Builder<_B> withVaccinationPlanIdentifier(final long vaccinationPlanIdentifier) {
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
 public SubscribeAndCreatePlannedVaccinationsType.Builder<_B> withVaccinationPlanVersionIdentifier(final long vaccinationPlanVersionIdentifier) {
  this.vaccinationPlanVersionIdentifier = vaccinationPlanVersionIdentifier;
  return this;
 }
  /**
 * Adds the given items to the value of "createPlannedVaccination"
 * 
 * @param createPlannedVaccination
 *     Items to add to the value of the "createPlannedVaccination" property
 */
 public SubscribeAndCreatePlannedVaccinationsType.Builder<_B> addCreatePlannedVaccination(final Iterable<? extends CreatePlannedVaccinationType> createPlannedVaccination) {
  if (createPlannedVaccination!= null) {
   if (this.createPlannedVaccination == null) {
     this.createPlannedVaccination = new ArrayList<>();
   }
   for (CreatePlannedVaccinationType _item: createPlannedVaccination) {
     this.createPlannedVaccination.add(new CreatePlannedVaccinationType.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "createPlannedVaccination" (any previous value will be
 * replaced)
 * 
 * @param createPlannedVaccination
 *     New value of the "createPlannedVaccination" property.
 */
 public SubscribeAndCreatePlannedVaccinationsType.Builder<_B> withCreatePlannedVaccination(final Iterable<? extends CreatePlannedVaccinationType> createPlannedVaccination) {
  if (this.createPlannedVaccination!= null) {
   this.createPlannedVaccination.clear();
  }
  return addCreatePlannedVaccination(createPlannedVaccination);
 }
  /**
 * Adds the given items to the value of "createPlannedVaccination"
 * 
 * @param createPlannedVaccination
 *     Items to add to the value of the "createPlannedVaccination" property
 */
 public SubscribeAndCreatePlannedVaccinationsType.Builder<_B> addCreatePlannedVaccination(CreatePlannedVaccinationType... createPlannedVaccination) {
  addCreatePlannedVaccination(Arrays.asList(createPlannedVaccination));
  return this;
 }
  /**
 * Sets the new value of "createPlannedVaccination" (any previous value will be
 * replaced)
 * 
 * @param createPlannedVaccination
 *     New value of the "createPlannedVaccination" property.
 */
 public SubscribeAndCreatePlannedVaccinationsType.Builder<_B> withCreatePlannedVaccination(CreatePlannedVaccinationType... createPlannedVaccination) {
  withCreatePlannedVaccination(Arrays.asList(createPlannedVaccination));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the
 * "CreatePlannedVaccination" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.CreatePlannedVaccinationType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "CreatePlannedVaccination"
 *     property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.CreatePlannedVaccinationType.Builder#end()}
 *     to return to the current builder.
 */
 public CreatePlannedVaccinationType.Builder<? extends SubscribeAndCreatePlannedVaccinationsType.Builder<_B>> addCreatePlannedVaccination() {
  if (this.createPlannedVaccination == null) {
   this.createPlannedVaccination = new ArrayList<>();
  }
  final CreatePlannedVaccinationType.Builder<SubscribeAndCreatePlannedVaccinationsType.Builder<_B>> createPlannedVaccination_Builder = new CreatePlannedVaccinationType.Builder<>(this, null, false);
  this.createPlannedVaccination.add(createPlannedVaccination_Builder);
  return createPlannedVaccination_Builder;
 }
  @Override
 public SubscribeAndCreatePlannedVaccinationsType build() {
  if (_storedValue == null) {
   return this.init(new SubscribeAndCreatePlannedVaccinationsType());
  } else {
   return ((SubscribeAndCreatePlannedVaccinationsType) _storedValue);
  }
 }
  public SubscribeAndCreatePlannedVaccinationsType.Builder<_B> copyOf(final SubscribeAndCreatePlannedVaccinationsType _other) {
  _other.copyTo(this);
  return this;
 }
  public SubscribeAndCreatePlannedVaccinationsType.Builder<_B> copyOf(final SubscribeAndCreatePlannedVaccinationsType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends SubscribeAndCreatePlannedVaccinationsType.Selector<SubscribeAndCreatePlannedVaccinationsType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static SubscribeAndCreatePlannedVaccinationsType.Select _root() {
  return new SubscribeAndCreatePlannedVaccinationsType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private CreatePlannedVaccinationType.Selector<TRoot, SubscribeAndCreatePlannedVaccinationsType.Selector<TRoot, TParent>> createPlannedVaccination = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.createPlannedVaccination!= null) {
   products.put("createPlannedVaccination", this.createPlannedVaccination.init());
  }
  return products;
 }
  public CreatePlannedVaccinationType.Selector<TRoot, SubscribeAndCreatePlannedVaccinationsType.Selector<TRoot, TParent>> createPlannedVaccination() {
  return ((this.createPlannedVaccination == null)?this.createPlannedVaccination = new CreatePlannedVaccinationType.Selector<>(this._root, this, "createPlannedVaccination"):this.createPlannedVaccination);
 }
  }
 }
