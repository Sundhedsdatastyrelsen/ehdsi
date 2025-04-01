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
* <p>Java class for EffectuatedPlannedItemType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="EffectuatedPlannedItemType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccinationPlanIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanIdentifierType" minOccurs="0"/>
*         <element name="VaccinationPlanVersionIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanVersionIdentifierType" minOccurs="0"/>
*         <element name="VaccinationPlanName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanNameType" minOccurs="0"/>
*         <element name="VaccinationPlanItemIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemIdentifierType" minOccurs="0"/>
*         <element name="VaccinationPlanItemDescription" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemDescriptionType" minOccurs="0"/>
*         <element name="VaccinationPlanItemIndex" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemIndexType" minOccurs="0"/>
*         <element name="VaccinationPlanItemSeries" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanItemSeriesType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EffectuatedPlannedItemType", propOrder = {
 "vaccinationPlanIdentifier",
 "vaccinationPlanVersionIdentifier",
 "vaccinationPlanName",
 "vaccinationPlanItemIdentifier",
 "vaccinationPlanItemDescription",
 "vaccinationPlanItemIndex",
 "vaccinationPlanItemSeries"
})
public class EffectuatedPlannedItemType {
  @XmlElement(name = "VaccinationPlanIdentifier")
 protected Long vaccinationPlanIdentifier;
 @XmlElement(name = "VaccinationPlanVersionIdentifier")
 protected Long vaccinationPlanVersionIdentifier;
 @XmlElement(name = "VaccinationPlanName")
 protected String vaccinationPlanName;
 @XmlElement(name = "VaccinationPlanItemIdentifier")
 protected Long vaccinationPlanItemIdentifier;
 @XmlElement(name = "VaccinationPlanItemDescription")
 protected String vaccinationPlanItemDescription;
 @XmlElement(name = "VaccinationPlanItemIndex")
 protected Long vaccinationPlanItemIndex;
 @XmlElement(name = "VaccinationPlanItemSeries")
 protected String vaccinationPlanItemSeries;
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
 * Gets the value of the vaccinationPlanVersionIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link Long }
 *     
 */
 public Long getVaccinationPlanVersionIdentifier() {
 return vaccinationPlanVersionIdentifier;
 }
  /**
 * Sets the value of the vaccinationPlanVersionIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link Long }
 *     
 */
 public void setVaccinationPlanVersionIdentifier(Long value) {
 this.vaccinationPlanVersionIdentifier = value;
 }
  /**
 * Gets the value of the vaccinationPlanName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccinationPlanName() {
 return vaccinationPlanName;
 }
  /**
 * Sets the value of the vaccinationPlanName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccinationPlanName(String value) {
 this.vaccinationPlanName = value;
 }
  /**
 * Gets the value of the vaccinationPlanItemIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link Long }
 *     
 */
 public Long getVaccinationPlanItemIdentifier() {
 return vaccinationPlanItemIdentifier;
 }
  /**
 * Sets the value of the vaccinationPlanItemIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link Long }
 *     
 */
 public void setVaccinationPlanItemIdentifier(Long value) {
 this.vaccinationPlanItemIdentifier = value;
 }
  /**
 * Gets the value of the vaccinationPlanItemDescription property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccinationPlanItemDescription() {
 return vaccinationPlanItemDescription;
 }
  /**
 * Sets the value of the vaccinationPlanItemDescription property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccinationPlanItemDescription(String value) {
 this.vaccinationPlanItemDescription = value;
 }
  /**
 * Gets the value of the vaccinationPlanItemIndex property.
 * 
 * @return
 *     possible object is
 *     {@link Long }
 *     
 */
 public Long getVaccinationPlanItemIndex() {
 return vaccinationPlanItemIndex;
 }
  /**
 * Sets the value of the vaccinationPlanItemIndex property.
 * 
 * @param value
 *     allowed object is
 *     {@link Long }
 *     
 */
 public void setVaccinationPlanItemIndex(Long value) {
 this.vaccinationPlanItemIndex = value;
 }
  /**
 * Gets the value of the vaccinationPlanItemSeries property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccinationPlanItemSeries() {
 return vaccinationPlanItemSeries;
 }
  /**
 * Sets the value of the vaccinationPlanItemSeries property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccinationPlanItemSeries(String value) {
 this.vaccinationPlanItemSeries = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final EffectuatedPlannedItemType.Builder<_B> _other) {
 _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 _other.vaccinationPlanVersionIdentifier = this.vaccinationPlanVersionIdentifier;
 _other.vaccinationPlanName = this.vaccinationPlanName;
 _other.vaccinationPlanItemIdentifier = this.vaccinationPlanItemIdentifier;
 _other.vaccinationPlanItemDescription = this.vaccinationPlanItemDescription;
 _other.vaccinationPlanItemIndex = this.vaccinationPlanItemIndex;
 _other.vaccinationPlanItemSeries = this.vaccinationPlanItemSeries;
 }
  public<_B >EffectuatedPlannedItemType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new EffectuatedPlannedItemType.Builder<_B>(_parentBuilder, this, true);
 }
  public EffectuatedPlannedItemType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static EffectuatedPlannedItemType.Builder<Void> builder() {
 return new EffectuatedPlannedItemType.Builder<>(null, null, false);
 }
  public static<_B >EffectuatedPlannedItemType.Builder<_B> copyOf(final EffectuatedPlannedItemType _other) {
 final EffectuatedPlannedItemType.Builder<_B> _newBuilder = new EffectuatedPlannedItemType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final EffectuatedPlannedItemType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 }
 final PropertyTree vaccinationPlanVersionIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanVersionIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanVersionIdentifierPropertyTree!= null):((vaccinationPlanVersionIdentifierPropertyTree == null)||(!vaccinationPlanVersionIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanVersionIdentifier = this.vaccinationPlanVersionIdentifier;
 }
 final PropertyTree vaccinationPlanNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanNamePropertyTree!= null):((vaccinationPlanNamePropertyTree == null)||(!vaccinationPlanNamePropertyTree.isLeaf())))) {
  _other.vaccinationPlanName = this.vaccinationPlanName;
 }
 final PropertyTree vaccinationPlanItemIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIdentifierPropertyTree!= null):((vaccinationPlanItemIdentifierPropertyTree == null)||(!vaccinationPlanItemIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemIdentifier = this.vaccinationPlanItemIdentifier;
 }
 final PropertyTree vaccinationPlanItemDescriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemDescription"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemDescriptionPropertyTree!= null):((vaccinationPlanItemDescriptionPropertyTree == null)||(!vaccinationPlanItemDescriptionPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemDescription = this.vaccinationPlanItemDescription;
 }
 final PropertyTree vaccinationPlanItemIndexPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIndex"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIndexPropertyTree!= null):((vaccinationPlanItemIndexPropertyTree == null)||(!vaccinationPlanItemIndexPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemIndex = this.vaccinationPlanItemIndex;
 }
 final PropertyTree vaccinationPlanItemSeriesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemSeries"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemSeriesPropertyTree!= null):((vaccinationPlanItemSeriesPropertyTree == null)||(!vaccinationPlanItemSeriesPropertyTree.isLeaf())))) {
  _other.vaccinationPlanItemSeries = this.vaccinationPlanItemSeries;
 }
 }
  public<_B >EffectuatedPlannedItemType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new EffectuatedPlannedItemType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public EffectuatedPlannedItemType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >EffectuatedPlannedItemType.Builder<_B> copyOf(final EffectuatedPlannedItemType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final EffectuatedPlannedItemType.Builder<_B> _newBuilder = new EffectuatedPlannedItemType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static EffectuatedPlannedItemType.Builder<Void> copyExcept(final EffectuatedPlannedItemType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static EffectuatedPlannedItemType.Builder<Void> copyOnly(final EffectuatedPlannedItemType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final EffectuatedPlannedItemType _storedValue;
 private Long vaccinationPlanIdentifier;
 private Long vaccinationPlanVersionIdentifier;
 private String vaccinationPlanName;
 private Long vaccinationPlanItemIdentifier;
 private String vaccinationPlanItemDescription;
 private Long vaccinationPlanItemIndex;
 private String vaccinationPlanItemSeries;
  public Builder(final _B _parentBuilder, final EffectuatedPlannedItemType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     this.vaccinationPlanVersionIdentifier = _other.vaccinationPlanVersionIdentifier;
     this.vaccinationPlanName = _other.vaccinationPlanName;
     this.vaccinationPlanItemIdentifier = _other.vaccinationPlanItemIdentifier;
     this.vaccinationPlanItemDescription = _other.vaccinationPlanItemDescription;
     this.vaccinationPlanItemIndex = _other.vaccinationPlanItemIndex;
     this.vaccinationPlanItemSeries = _other.vaccinationPlanItemSeries;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final EffectuatedPlannedItemType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
     final PropertyTree vaccinationPlanNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanNamePropertyTree!= null):((vaccinationPlanNamePropertyTree == null)||(!vaccinationPlanNamePropertyTree.isLeaf())))) {
        this.vaccinationPlanName = _other.vaccinationPlanName;
     }
     final PropertyTree vaccinationPlanItemIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIdentifierPropertyTree!= null):((vaccinationPlanItemIdentifierPropertyTree == null)||(!vaccinationPlanItemIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemIdentifier = _other.vaccinationPlanItemIdentifier;
     }
     final PropertyTree vaccinationPlanItemDescriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemDescription"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemDescriptionPropertyTree!= null):((vaccinationPlanItemDescriptionPropertyTree == null)||(!vaccinationPlanItemDescriptionPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemDescription = _other.vaccinationPlanItemDescription;
     }
     final PropertyTree vaccinationPlanItemIndexPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemIndex"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemIndexPropertyTree!= null):((vaccinationPlanItemIndexPropertyTree == null)||(!vaccinationPlanItemIndexPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemIndex = _other.vaccinationPlanItemIndex;
     }
     final PropertyTree vaccinationPlanItemSeriesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanItemSeries"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanItemSeriesPropertyTree!= null):((vaccinationPlanItemSeriesPropertyTree == null)||(!vaccinationPlanItemSeriesPropertyTree.isLeaf())))) {
        this.vaccinationPlanItemSeries = _other.vaccinationPlanItemSeries;
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
  protected<_P extends EffectuatedPlannedItemType >_P init(final _P _product) {
  _product.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
  _product.vaccinationPlanVersionIdentifier = this.vaccinationPlanVersionIdentifier;
  _product.vaccinationPlanName = this.vaccinationPlanName;
  _product.vaccinationPlanItemIdentifier = this.vaccinationPlanItemIdentifier;
  _product.vaccinationPlanItemDescription = this.vaccinationPlanItemDescription;
  _product.vaccinationPlanItemIndex = this.vaccinationPlanItemIndex;
  _product.vaccinationPlanItemSeries = this.vaccinationPlanItemSeries;
  return _product;
 }
  /**
 * Sets the new value of "vaccinationPlanIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanIdentifier
 *     New value of the "vaccinationPlanIdentifier" property.
 */
 public EffectuatedPlannedItemType.Builder<_B> withVaccinationPlanIdentifier(final Long vaccinationPlanIdentifier) {
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
 public EffectuatedPlannedItemType.Builder<_B> withVaccinationPlanVersionIdentifier(final Long vaccinationPlanVersionIdentifier) {
  this.vaccinationPlanVersionIdentifier = vaccinationPlanVersionIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanName" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanName
 *     New value of the "vaccinationPlanName" property.
 */
 public EffectuatedPlannedItemType.Builder<_B> withVaccinationPlanName(final String vaccinationPlanName) {
  this.vaccinationPlanName = vaccinationPlanName;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanItemIdentifier" (any previous value will
 * be replaced)
 * 
 * @param vaccinationPlanItemIdentifier
 *     New value of the "vaccinationPlanItemIdentifier" property.
 */
 public EffectuatedPlannedItemType.Builder<_B> withVaccinationPlanItemIdentifier(final Long vaccinationPlanItemIdentifier) {
  this.vaccinationPlanItemIdentifier = vaccinationPlanItemIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanItemDescription" (any previous value will
 * be replaced)
 * 
 * @param vaccinationPlanItemDescription
 *     New value of the "vaccinationPlanItemDescription" property.
 */
 public EffectuatedPlannedItemType.Builder<_B> withVaccinationPlanItemDescription(final String vaccinationPlanItemDescription) {
  this.vaccinationPlanItemDescription = vaccinationPlanItemDescription;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanItemIndex" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanItemIndex
 *     New value of the "vaccinationPlanItemIndex" property.
 */
 public EffectuatedPlannedItemType.Builder<_B> withVaccinationPlanItemIndex(final Long vaccinationPlanItemIndex) {
  this.vaccinationPlanItemIndex = vaccinationPlanItemIndex;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanItemSeries" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanItemSeries
 *     New value of the "vaccinationPlanItemSeries" property.
 */
 public EffectuatedPlannedItemType.Builder<_B> withVaccinationPlanItemSeries(final String vaccinationPlanItemSeries) {
  this.vaccinationPlanItemSeries = vaccinationPlanItemSeries;
  return this;
 }
  @Override
 public EffectuatedPlannedItemType build() {
  if (_storedValue == null) {
   return this.init(new EffectuatedPlannedItemType());
  } else {
   return ((EffectuatedPlannedItemType) _storedValue);
  }
 }
  public EffectuatedPlannedItemType.Builder<_B> copyOf(final EffectuatedPlannedItemType _other) {
  _other.copyTo(this);
  return this;
 }
  public EffectuatedPlannedItemType.Builder<_B> copyOf(final EffectuatedPlannedItemType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends EffectuatedPlannedItemType.Selector<EffectuatedPlannedItemType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static EffectuatedPlannedItemType.Select _root() {
  return new EffectuatedPlannedItemType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanVersionIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanName = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanItemIdentifier = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanItemDescription = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanItemIndex = null;
 private com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanItemSeries = null;
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
  if (this.vaccinationPlanVersionIdentifier!= null) {
   products.put("vaccinationPlanVersionIdentifier", this.vaccinationPlanVersionIdentifier.init());
  }
  if (this.vaccinationPlanName!= null) {
   products.put("vaccinationPlanName", this.vaccinationPlanName.init());
  }
  if (this.vaccinationPlanItemIdentifier!= null) {
   products.put("vaccinationPlanItemIdentifier", this.vaccinationPlanItemIdentifier.init());
  }
  if (this.vaccinationPlanItemDescription!= null) {
   products.put("vaccinationPlanItemDescription", this.vaccinationPlanItemDescription.init());
  }
  if (this.vaccinationPlanItemIndex!= null) {
   products.put("vaccinationPlanItemIndex", this.vaccinationPlanItemIndex.init());
  }
  if (this.vaccinationPlanItemSeries!= null) {
   products.put("vaccinationPlanItemSeries", this.vaccinationPlanItemSeries.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanIdentifier() {
  return ((this.vaccinationPlanIdentifier == null)?this.vaccinationPlanIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanIdentifier"):this.vaccinationPlanIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanVersionIdentifier() {
  return ((this.vaccinationPlanVersionIdentifier == null)?this.vaccinationPlanVersionIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanVersionIdentifier"):this.vaccinationPlanVersionIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanName() {
  return ((this.vaccinationPlanName == null)?this.vaccinationPlanName = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanName"):this.vaccinationPlanName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanItemIdentifier() {
  return ((this.vaccinationPlanItemIdentifier == null)?this.vaccinationPlanItemIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanItemIdentifier"):this.vaccinationPlanItemIdentifier);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanItemDescription() {
  return ((this.vaccinationPlanItemDescription == null)?this.vaccinationPlanItemDescription = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanItemDescription"):this.vaccinationPlanItemDescription);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanItemIndex() {
  return ((this.vaccinationPlanItemIndex == null)?this.vaccinationPlanItemIndex = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanItemIndex"):this.vaccinationPlanItemIndex);
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatedPlannedItemType.Selector<TRoot, TParent>> vaccinationPlanItemSeries() {
  return ((this.vaccinationPlanItemSeries == null)?this.vaccinationPlanItemSeries = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanItemSeries"):this.vaccinationPlanItemSeries);
 }
  }
 }
