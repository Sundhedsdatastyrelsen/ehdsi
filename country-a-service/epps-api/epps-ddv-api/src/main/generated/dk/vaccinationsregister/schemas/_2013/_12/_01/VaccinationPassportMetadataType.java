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
* <p>Java class for VaccinationPassportMetadataType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="VaccinationPassportMetadataType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="DocumentId" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DocumentIdType"/>
*         <element name="Effectuation" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuationOverviewType" maxOccurs="unbounded" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccinationPassportMetadataType", propOrder = {
 "documentId",
 "effectuation"
})
public class VaccinationPassportMetadataType {
  @XmlElement(name = "DocumentId", required = true)
 protected String documentId;
 @XmlElement(name = "Effectuation")
 protected List<EffectuationOverviewType> effectuation;
  /**
 * Gets the value of the documentId property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getDocumentId() {
 return documentId;
 }
  /**
 * Sets the value of the documentId property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setDocumentId(String value) {
 this.documentId = value;
 }
  /**
 * Gets the value of the effectuation property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the effectuation property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getEffectuation().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link EffectuationOverviewType }
 * 
 * 
 * @return
 *     The value of the effectuation property.
 */
 public List<EffectuationOverviewType> getEffectuation() {
 if (effectuation == null) {
  effectuation = new ArrayList<>();
 }
 return this.effectuation;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final VaccinationPassportMetadataType.Builder<_B> _other) {
 _other.documentId = this.documentId;
 if (this.effectuation == null) {
  _other.effectuation = null;
 } else {
  _other.effectuation = new ArrayList<>();
  for (EffectuationOverviewType _item: this.effectuation) {
   _other.effectuation.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >VaccinationPassportMetadataType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new VaccinationPassportMetadataType.Builder<_B>(_parentBuilder, this, true);
 }
  public VaccinationPassportMetadataType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static VaccinationPassportMetadataType.Builder<Void> builder() {
 return new VaccinationPassportMetadataType.Builder<>(null, null, false);
 }
  public static<_B >VaccinationPassportMetadataType.Builder<_B> copyOf(final VaccinationPassportMetadataType _other) {
 final VaccinationPassportMetadataType.Builder<_B> _newBuilder = new VaccinationPassportMetadataType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final VaccinationPassportMetadataType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree documentIdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("documentId"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(documentIdPropertyTree!= null):((documentIdPropertyTree == null)||(!documentIdPropertyTree.isLeaf())))) {
  _other.documentId = this.documentId;
 }
 final PropertyTree effectuationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuation"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuationPropertyTree!= null):((effectuationPropertyTree == null)||(!effectuationPropertyTree.isLeaf())))) {
  if (this.effectuation == null) {
   _other.effectuation = null;
  } else {
   _other.effectuation = new ArrayList<>();
   for (EffectuationOverviewType _item: this.effectuation) {
     _other.effectuation.add(((_item == null)?null:_item.newCopyBuilder(_other, effectuationPropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >VaccinationPassportMetadataType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new VaccinationPassportMetadataType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public VaccinationPassportMetadataType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >VaccinationPassportMetadataType.Builder<_B> copyOf(final VaccinationPassportMetadataType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final VaccinationPassportMetadataType.Builder<_B> _newBuilder = new VaccinationPassportMetadataType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static VaccinationPassportMetadataType.Builder<Void> copyExcept(final VaccinationPassportMetadataType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static VaccinationPassportMetadataType.Builder<Void> copyOnly(final VaccinationPassportMetadataType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final VaccinationPassportMetadataType _storedValue;
 private String documentId;
 private List<EffectuationOverviewType.Builder<VaccinationPassportMetadataType.Builder<_B>>> effectuation;
  public Builder(final _B _parentBuilder, final VaccinationPassportMetadataType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.documentId = _other.documentId;
     if (_other.effectuation == null) {
        this.effectuation = null;
     } else {
        this.effectuation = new ArrayList<>();
        for (EffectuationOverviewType _item: _other.effectuation) {
             this.effectuation.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final VaccinationPassportMetadataType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree documentIdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("documentId"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(documentIdPropertyTree!= null):((documentIdPropertyTree == null)||(!documentIdPropertyTree.isLeaf())))) {
        this.documentId = _other.documentId;
     }
     final PropertyTree effectuationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuation"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuationPropertyTree!= null):((effectuationPropertyTree == null)||(!effectuationPropertyTree.isLeaf())))) {
        if (_other.effectuation == null) {
             this.effectuation = null;
        } else {
             this.effectuation = new ArrayList<>();
             for (EffectuationOverviewType _item: _other.effectuation) {
                     this.effectuation.add(((_item == null)?null:_item.newCopyBuilder(this, effectuationPropertyTree, _propertyTreeUse)));
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
  protected<_P extends VaccinationPassportMetadataType >_P init(final _P _product) {
  _product.documentId = this.documentId;
  if (this.effectuation!= null) {
   final List<EffectuationOverviewType> effectuation = new ArrayList<>(this.effectuation.size());
   for (EffectuationOverviewType.Builder<VaccinationPassportMetadataType.Builder<_B>> _item: this.effectuation) {
     effectuation.add(_item.build());
   }
   _product.effectuation = effectuation;
  }
  return _product;
 }
  /**
 * Sets the new value of "documentId" (any previous value will be replaced)
 * 
 * @param documentId
 *     New value of the "documentId" property.
 */
 public VaccinationPassportMetadataType.Builder<_B> withDocumentId(final String documentId) {
  this.documentId = documentId;
  return this;
 }
  /**
 * Adds the given items to the value of "effectuation"
 * 
 * @param effectuation
 *     Items to add to the value of the "effectuation" property
 */
 public VaccinationPassportMetadataType.Builder<_B> addEffectuation(final Iterable<? extends EffectuationOverviewType> effectuation) {
  if (effectuation!= null) {
   if (this.effectuation == null) {
     this.effectuation = new ArrayList<>();
   }
   for (EffectuationOverviewType _item: effectuation) {
     this.effectuation.add(new EffectuationOverviewType.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "effectuation" (any previous value will be replaced)
 * 
 * @param effectuation
 *     New value of the "effectuation" property.
 */
 public VaccinationPassportMetadataType.Builder<_B> withEffectuation(final Iterable<? extends EffectuationOverviewType> effectuation) {
  if (this.effectuation!= null) {
   this.effectuation.clear();
  }
  return addEffectuation(effectuation);
 }
  /**
 * Adds the given items to the value of "effectuation"
 * 
 * @param effectuation
 *     Items to add to the value of the "effectuation" property
 */
 public VaccinationPassportMetadataType.Builder<_B> addEffectuation(EffectuationOverviewType... effectuation) {
  addEffectuation(Arrays.asList(effectuation));
  return this;
 }
  /**
 * Sets the new value of "effectuation" (any previous value will be replaced)
 * 
 * @param effectuation
 *     New value of the "effectuation" property.
 */
 public VaccinationPassportMetadataType.Builder<_B> withEffectuation(EffectuationOverviewType... effectuation) {
  withEffectuation(Arrays.asList(effectuation));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "Effectuation"
 * property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.EffectuationOverviewType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "Effectuation" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.EffectuationOverviewType.Builder#end()}
 *     to return to the current builder.
 */
 public EffectuationOverviewType.Builder<? extends VaccinationPassportMetadataType.Builder<_B>> addEffectuation() {
  if (this.effectuation == null) {
   this.effectuation = new ArrayList<>();
  }
  final EffectuationOverviewType.Builder<VaccinationPassportMetadataType.Builder<_B>> effectuation_Builder = new EffectuationOverviewType.Builder<>(this, null, false);
  this.effectuation.add(effectuation_Builder);
  return effectuation_Builder;
 }
  @Override
 public VaccinationPassportMetadataType build() {
  if (_storedValue == null) {
   return this.init(new VaccinationPassportMetadataType());
  } else {
   return ((VaccinationPassportMetadataType) _storedValue);
  }
 }
  public VaccinationPassportMetadataType.Builder<_B> copyOf(final VaccinationPassportMetadataType _other) {
  _other.copyTo(this);
  return this;
 }
  public VaccinationPassportMetadataType.Builder<_B> copyOf(final VaccinationPassportMetadataType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends VaccinationPassportMetadataType.Selector<VaccinationPassportMetadataType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static VaccinationPassportMetadataType.Select _root() {
  return new VaccinationPassportMetadataType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, VaccinationPassportMetadataType.Selector<TRoot, TParent>> documentId = null;
 private EffectuationOverviewType.Selector<TRoot, VaccinationPassportMetadataType.Selector<TRoot, TParent>> effectuation = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.documentId!= null) {
   products.put("documentId", this.documentId.init());
  }
  if (this.effectuation!= null) {
   products.put("effectuation", this.effectuation.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccinationPassportMetadataType.Selector<TRoot, TParent>> documentId() {
  return ((this.documentId == null)?this.documentId = new com.kscs.util.jaxb.Selector<>(this._root, this, "documentId"):this.documentId);
 }
  public EffectuationOverviewType.Selector<TRoot, VaccinationPassportMetadataType.Selector<TRoot, TParent>> effectuation() {
  return ((this.effectuation == null)?this.effectuation = new EffectuationOverviewType.Selector<>(this._root, this, "effectuation"):this.effectuation);
 }
  }
 }
