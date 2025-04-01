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
* <p>Java class for VaccineType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="VaccineType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccineIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccineIdentifierType"/>
*         <element name="VaccineName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccineNameType"/>
*         <element name="Disease" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DiseaseType" maxOccurs="unbounded" minOccurs="0"/>
*         <element name="ATC" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ATCType"/>
*         <element name="SSIDrug" type="{http://vaccinationsregister.dk/schemas/2013/12/01}SSIDrugType" maxOccurs="unbounded" minOccurs="0"/>
*         <element name="VaccineKeywordsText" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccineKeywordsTextType" minOccurs="0"/>
*         <element name="DisplayMinimumVaccinationPlan" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DisplayMinimumVaccinationPlanType" maxOccurs="unbounded" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VaccineType", propOrder = {
 "vaccineIdentifier",
 "vaccineName",
 "disease",
 "atc",
 "ssiDrug",
 "vaccineKeywordsText",
 "displayMinimumVaccinationPlan"
})
public class VaccineType {
  @XmlElement(name = "VaccineIdentifier")
 protected long vaccineIdentifier;
 @XmlElement(name = "VaccineName", required = true)
 protected String vaccineName;
 @XmlElement(name = "Disease")
 protected List<DiseaseType> disease;
 @XmlElement(name = "ATC", required = true)
 protected ATCType atc;
 @XmlElement(name = "SSIDrug")
 protected List<SSIDrugType> ssiDrug;
 @XmlElement(name = "VaccineKeywordsText")
 protected String vaccineKeywordsText;
 @XmlElement(name = "DisplayMinimumVaccinationPlan")
 protected List<DisplayMinimumVaccinationPlanType> displayMinimumVaccinationPlan;
  /**
 * Gets the value of the vaccineIdentifier property.
 * 
 */
 public long getVaccineIdentifier() {
 return vaccineIdentifier;
 }
  /**
 * Sets the value of the vaccineIdentifier property.
 * 
 */
 public void setVaccineIdentifier(long value) {
 this.vaccineIdentifier = value;
 }
  /**
 * Gets the value of the vaccineName property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccineName() {
 return vaccineName;
 }
  /**
 * Sets the value of the vaccineName property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccineName(String value) {
 this.vaccineName = value;
 }
  /**
 * Gets the value of the disease property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the disease property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getDisease().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link DiseaseType }
 * 
 * 
 * @return
 *     The value of the disease property.
 */
 public List<DiseaseType> getDisease() {
 if (disease == null) {
  disease = new ArrayList<>();
 }
 return this.disease;
 }
  /**
 * Gets the value of the atc property.
 * 
 * @return
 *     possible object is
 *     {@link ATCType }
 *     
 */
 public ATCType getATC() {
 return atc;
 }
  /**
 * Sets the value of the atc property.
 * 
 * @param value
 *     allowed object is
 *     {@link ATCType }
 *     
 */
 public void setATC(ATCType value) {
 this.atc = value;
 }
  /**
 * Gets the value of the ssiDrug property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the ssiDrug property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getSSIDrug().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link SSIDrugType }
 * 
 * 
 * @return
 *     The value of the ssiDrug property.
 */
 public List<SSIDrugType> getSSIDrug() {
 if (ssiDrug == null) {
  ssiDrug = new ArrayList<>();
 }
 return this.ssiDrug;
 }
  /**
 * Gets the value of the vaccineKeywordsText property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccineKeywordsText() {
 return vaccineKeywordsText;
 }
  /**
 * Sets the value of the vaccineKeywordsText property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccineKeywordsText(String value) {
 this.vaccineKeywordsText = value;
 }
  /**
 * Gets the value of the displayMinimumVaccinationPlan property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the displayMinimumVaccinationPlan property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getDisplayMinimumVaccinationPlan().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link DisplayMinimumVaccinationPlanType }
 * 
 * 
 * @return
 *     The value of the displayMinimumVaccinationPlan property.
 */
 public List<DisplayMinimumVaccinationPlanType> getDisplayMinimumVaccinationPlan() {
 if (displayMinimumVaccinationPlan == null) {
  displayMinimumVaccinationPlan = new ArrayList<>();
 }
 return this.displayMinimumVaccinationPlan;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final VaccineType.Builder<_B> _other) {
 _other.vaccineIdentifier = this.vaccineIdentifier;
 _other.vaccineName = this.vaccineName;
 if (this.disease == null) {
  _other.disease = null;
 } else {
  _other.disease = new ArrayList<>();
  for (DiseaseType _item: this.disease) {
   _other.disease.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 _other.atc = ((this.atc == null)?null:this.atc.newCopyBuilder(_other));
 if (this.ssiDrug == null) {
  _other.ssiDrug = null;
 } else {
  _other.ssiDrug = new ArrayList<>();
  for (SSIDrugType _item: this.ssiDrug) {
   _other.ssiDrug.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 _other.vaccineKeywordsText = this.vaccineKeywordsText;
 if (this.displayMinimumVaccinationPlan == null) {
  _other.displayMinimumVaccinationPlan = null;
 } else {
  _other.displayMinimumVaccinationPlan = new ArrayList<>();
  for (DisplayMinimumVaccinationPlanType _item: this.displayMinimumVaccinationPlan) {
   _other.displayMinimumVaccinationPlan.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >VaccineType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new VaccineType.Builder<_B>(_parentBuilder, this, true);
 }
  public VaccineType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static VaccineType.Builder<Void> builder() {
 return new VaccineType.Builder<>(null, null, false);
 }
  public static<_B >VaccineType.Builder<_B> copyOf(final VaccineType _other) {
 final VaccineType.Builder<_B> _newBuilder = new VaccineType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final VaccineType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccineIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineIdentifierPropertyTree!= null):((vaccineIdentifierPropertyTree == null)||(!vaccineIdentifierPropertyTree.isLeaf())))) {
  _other.vaccineIdentifier = this.vaccineIdentifier;
 }
 final PropertyTree vaccineNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineNamePropertyTree!= null):((vaccineNamePropertyTree == null)||(!vaccineNamePropertyTree.isLeaf())))) {
  _other.vaccineName = this.vaccineName;
 }
 final PropertyTree diseasePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("disease"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(diseasePropertyTree!= null):((diseasePropertyTree == null)||(!diseasePropertyTree.isLeaf())))) {
  if (this.disease == null) {
   _other.disease = null;
  } else {
   _other.disease = new ArrayList<>();
   for (DiseaseType _item: this.disease) {
     _other.disease.add(((_item == null)?null:_item.newCopyBuilder(_other, diseasePropertyTree, _propertyTreeUse)));
   }
  }
 }
 final PropertyTree atcPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("atc"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(atcPropertyTree!= null):((atcPropertyTree == null)||(!atcPropertyTree.isLeaf())))) {
  _other.atc = ((this.atc == null)?null:this.atc.newCopyBuilder(_other, atcPropertyTree, _propertyTreeUse));
 }
 final PropertyTree ssiDrugPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("ssiDrug"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(ssiDrugPropertyTree!= null):((ssiDrugPropertyTree == null)||(!ssiDrugPropertyTree.isLeaf())))) {
  if (this.ssiDrug == null) {
   _other.ssiDrug = null;
  } else {
   _other.ssiDrug = new ArrayList<>();
   for (SSIDrugType _item: this.ssiDrug) {
     _other.ssiDrug.add(((_item == null)?null:_item.newCopyBuilder(_other, ssiDrugPropertyTree, _propertyTreeUse)));
   }
  }
 }
 final PropertyTree vaccineKeywordsTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineKeywordsText"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineKeywordsTextPropertyTree!= null):((vaccineKeywordsTextPropertyTree == null)||(!vaccineKeywordsTextPropertyTree.isLeaf())))) {
  _other.vaccineKeywordsText = this.vaccineKeywordsText;
 }
 final PropertyTree displayMinimumVaccinationPlanPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("displayMinimumVaccinationPlan"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(displayMinimumVaccinationPlanPropertyTree!= null):((displayMinimumVaccinationPlanPropertyTree == null)||(!displayMinimumVaccinationPlanPropertyTree.isLeaf())))) {
  if (this.displayMinimumVaccinationPlan == null) {
   _other.displayMinimumVaccinationPlan = null;
  } else {
   _other.displayMinimumVaccinationPlan = new ArrayList<>();
   for (DisplayMinimumVaccinationPlanType _item: this.displayMinimumVaccinationPlan) {
     _other.displayMinimumVaccinationPlan.add(((_item == null)?null:_item.newCopyBuilder(_other, displayMinimumVaccinationPlanPropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >VaccineType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new VaccineType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public VaccineType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >VaccineType.Builder<_B> copyOf(final VaccineType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final VaccineType.Builder<_B> _newBuilder = new VaccineType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static VaccineType.Builder<Void> copyExcept(final VaccineType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static VaccineType.Builder<Void> copyOnly(final VaccineType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final VaccineType _storedValue;
 private long vaccineIdentifier;
 private String vaccineName;
 private List<DiseaseType.Builder<VaccineType.Builder<_B>>> disease;
 private ATCType.Builder<VaccineType.Builder<_B>> atc;
 private List<SSIDrugType.Builder<VaccineType.Builder<_B>>> ssiDrug;
 private String vaccineKeywordsText;
 private List<DisplayMinimumVaccinationPlanType.Builder<VaccineType.Builder<_B>>> displayMinimumVaccinationPlan;
  public Builder(final _B _parentBuilder, final VaccineType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccineIdentifier = _other.vaccineIdentifier;
     this.vaccineName = _other.vaccineName;
     if (_other.disease == null) {
        this.disease = null;
     } else {
        this.disease = new ArrayList<>();
        for (DiseaseType _item: _other.disease) {
             this.disease.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
     this.atc = ((_other.atc == null)?null:_other.atc.newCopyBuilder(this));
     if (_other.ssiDrug == null) {
        this.ssiDrug = null;
     } else {
        this.ssiDrug = new ArrayList<>();
        for (SSIDrugType _item: _other.ssiDrug) {
             this.ssiDrug.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
     this.vaccineKeywordsText = _other.vaccineKeywordsText;
     if (_other.displayMinimumVaccinationPlan == null) {
        this.displayMinimumVaccinationPlan = null;
     } else {
        this.displayMinimumVaccinationPlan = new ArrayList<>();
        for (DisplayMinimumVaccinationPlanType _item: _other.displayMinimumVaccinationPlan) {
             this.displayMinimumVaccinationPlan.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final VaccineType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccineIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineIdentifierPropertyTree!= null):((vaccineIdentifierPropertyTree == null)||(!vaccineIdentifierPropertyTree.isLeaf())))) {
        this.vaccineIdentifier = _other.vaccineIdentifier;
     }
     final PropertyTree vaccineNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineNamePropertyTree!= null):((vaccineNamePropertyTree == null)||(!vaccineNamePropertyTree.isLeaf())))) {
        this.vaccineName = _other.vaccineName;
     }
     final PropertyTree diseasePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("disease"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(diseasePropertyTree!= null):((diseasePropertyTree == null)||(!diseasePropertyTree.isLeaf())))) {
        if (_other.disease == null) {
             this.disease = null;
        } else {
             this.disease = new ArrayList<>();
             for (DiseaseType _item: _other.disease) {
                     this.disease.add(((_item == null)?null:_item.newCopyBuilder(this, diseasePropertyTree, _propertyTreeUse)));
             }
        }
     }
     final PropertyTree atcPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("atc"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(atcPropertyTree!= null):((atcPropertyTree == null)||(!atcPropertyTree.isLeaf())))) {
        this.atc = ((_other.atc == null)?null:_other.atc.newCopyBuilder(this, atcPropertyTree, _propertyTreeUse));
     }
     final PropertyTree ssiDrugPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("ssiDrug"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(ssiDrugPropertyTree!= null):((ssiDrugPropertyTree == null)||(!ssiDrugPropertyTree.isLeaf())))) {
        if (_other.ssiDrug == null) {
             this.ssiDrug = null;
        } else {
             this.ssiDrug = new ArrayList<>();
             for (SSIDrugType _item: _other.ssiDrug) {
                     this.ssiDrug.add(((_item == null)?null:_item.newCopyBuilder(this, ssiDrugPropertyTree, _propertyTreeUse)));
             }
        }
     }
     final PropertyTree vaccineKeywordsTextPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccineKeywordsText"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccineKeywordsTextPropertyTree!= null):((vaccineKeywordsTextPropertyTree == null)||(!vaccineKeywordsTextPropertyTree.isLeaf())))) {
        this.vaccineKeywordsText = _other.vaccineKeywordsText;
     }
     final PropertyTree displayMinimumVaccinationPlanPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("displayMinimumVaccinationPlan"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(displayMinimumVaccinationPlanPropertyTree!= null):((displayMinimumVaccinationPlanPropertyTree == null)||(!displayMinimumVaccinationPlanPropertyTree.isLeaf())))) {
        if (_other.displayMinimumVaccinationPlan == null) {
             this.displayMinimumVaccinationPlan = null;
        } else {
             this.displayMinimumVaccinationPlan = new ArrayList<>();
             for (DisplayMinimumVaccinationPlanType _item: _other.displayMinimumVaccinationPlan) {
                     this.displayMinimumVaccinationPlan.add(((_item == null)?null:_item.newCopyBuilder(this, displayMinimumVaccinationPlanPropertyTree, _propertyTreeUse)));
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
  protected<_P extends VaccineType >_P init(final _P _product) {
  _product.vaccineIdentifier = this.vaccineIdentifier;
  _product.vaccineName = this.vaccineName;
  if (this.disease!= null) {
   final List<DiseaseType> disease = new ArrayList<>(this.disease.size());
   for (DiseaseType.Builder<VaccineType.Builder<_B>> _item: this.disease) {
     disease.add(_item.build());
   }
   _product.disease = disease;
  }
  _product.atc = ((this.atc == null)?null:this.atc.build());
  if (this.ssiDrug!= null) {
   final List<SSIDrugType> ssiDrug = new ArrayList<>(this.ssiDrug.size());
   for (SSIDrugType.Builder<VaccineType.Builder<_B>> _item: this.ssiDrug) {
     ssiDrug.add(_item.build());
   }
   _product.ssiDrug = ssiDrug;
  }
  _product.vaccineKeywordsText = this.vaccineKeywordsText;
  if (this.displayMinimumVaccinationPlan!= null) {
   final List<DisplayMinimumVaccinationPlanType> displayMinimumVaccinationPlan = new ArrayList<>(this.displayMinimumVaccinationPlan.size());
   for (DisplayMinimumVaccinationPlanType.Builder<VaccineType.Builder<_B>> _item: this.displayMinimumVaccinationPlan) {
     displayMinimumVaccinationPlan.add(_item.build());
   }
   _product.displayMinimumVaccinationPlan = displayMinimumVaccinationPlan;
  }
  return _product;
 }
  /**
 * Sets the new value of "vaccineIdentifier" (any previous value will be replaced)
 * 
 * @param vaccineIdentifier
 *     New value of the "vaccineIdentifier" property.
 */
 public VaccineType.Builder<_B> withVaccineIdentifier(final long vaccineIdentifier) {
  this.vaccineIdentifier = vaccineIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccineName" (any previous value will be replaced)
 * 
 * @param vaccineName
 *     New value of the "vaccineName" property.
 */
 public VaccineType.Builder<_B> withVaccineName(final String vaccineName) {
  this.vaccineName = vaccineName;
  return this;
 }
  /**
 * Adds the given items to the value of "disease"
 * 
 * @param disease
 *     Items to add to the value of the "disease" property
 */
 public VaccineType.Builder<_B> addDisease(final Iterable<? extends DiseaseType> disease) {
  if (disease!= null) {
   if (this.disease == null) {
     this.disease = new ArrayList<>();
   }
   for (DiseaseType _item: disease) {
     this.disease.add(new DiseaseType.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "disease" (any previous value will be replaced)
 * 
 * @param disease
 *     New value of the "disease" property.
 */
 public VaccineType.Builder<_B> withDisease(final Iterable<? extends DiseaseType> disease) {
  if (this.disease!= null) {
   this.disease.clear();
  }
  return addDisease(disease);
 }
  /**
 * Adds the given items to the value of "disease"
 * 
 * @param disease
 *     Items to add to the value of the "disease" property
 */
 public VaccineType.Builder<_B> addDisease(DiseaseType... disease) {
  addDisease(Arrays.asList(disease));
  return this;
 }
  /**
 * Sets the new value of "disease" (any previous value will be replaced)
 * 
 * @param disease
 *     New value of the "disease" property.
 */
 public VaccineType.Builder<_B> withDisease(DiseaseType... disease) {
  withDisease(Arrays.asList(disease));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "Disease" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.DiseaseType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "Disease" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.DiseaseType.Builder#end()} to
 *     return to the current builder.
 */
 public DiseaseType.Builder<? extends VaccineType.Builder<_B>> addDisease() {
  if (this.disease == null) {
   this.disease = new ArrayList<>();
  }
  final DiseaseType.Builder<VaccineType.Builder<_B>> disease_Builder = new DiseaseType.Builder<>(this, null, false);
  this.disease.add(disease_Builder);
  return disease_Builder;
 }
  /**
 * Sets the new value of "atc" (any previous value will be replaced)
 * 
 * @param atc
 *     New value of the "atc" property.
 */
 public VaccineType.Builder<_B> withATC(final ATCType atc) {
  this.atc = ((atc == null)?null:new ATCType.Builder<>(this, atc, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the "atc"
 * property.
 * Use {@link dk.vaccinationsregister.schemas._2013._12._01.ATCType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "atc" property.
 *     Use {@link dk.vaccinationsregister.schemas._2013._12._01.ATCType.Builder#end()}
 *     to return to the current builder.
 */
 public ATCType.Builder<? extends VaccineType.Builder<_B>> withATC() {
  if (this.atc!= null) {
   return this.atc;
  }
  return this.atc = new ATCType.Builder<>(this, null, false);
 }
  /**
 * Adds the given items to the value of "ssiDrug"
 * 
 * @param ssiDrug
 *     Items to add to the value of the "ssiDrug" property
 */
 public VaccineType.Builder<_B> addSSIDrug(final Iterable<? extends SSIDrugType> ssiDrug) {
  if (ssiDrug!= null) {
   if (this.ssiDrug == null) {
     this.ssiDrug = new ArrayList<>();
   }
   for (SSIDrugType _item: ssiDrug) {
     this.ssiDrug.add(new SSIDrugType.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "ssiDrug" (any previous value will be replaced)
 * 
 * @param ssiDrug
 *     New value of the "ssiDrug" property.
 */
 public VaccineType.Builder<_B> withSSIDrug(final Iterable<? extends SSIDrugType> ssiDrug) {
  if (this.ssiDrug!= null) {
   this.ssiDrug.clear();
  }
  return addSSIDrug(ssiDrug);
 }
  /**
 * Adds the given items to the value of "ssiDrug"
 * 
 * @param ssiDrug
 *     Items to add to the value of the "ssiDrug" property
 */
 public VaccineType.Builder<_B> addSSIDrug(SSIDrugType... ssiDrug) {
  addSSIDrug(Arrays.asList(ssiDrug));
  return this;
 }
  /**
 * Sets the new value of "ssiDrug" (any previous value will be replaced)
 * 
 * @param ssiDrug
 *     New value of the "ssiDrug" property.
 */
 public VaccineType.Builder<_B> withSSIDrug(SSIDrugType... ssiDrug) {
  withSSIDrug(Arrays.asList(ssiDrug));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "SSIDrug" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.SSIDrugType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "SSIDrug" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.SSIDrugType.Builder#end()} to
 *     return to the current builder.
 */
 public SSIDrugType.Builder<? extends VaccineType.Builder<_B>> addSSIDrug() {
  if (this.ssiDrug == null) {
   this.ssiDrug = new ArrayList<>();
  }
  final SSIDrugType.Builder<VaccineType.Builder<_B>> ssiDrug_Builder = new SSIDrugType.Builder<>(this, null, false);
  this.ssiDrug.add(ssiDrug_Builder);
  return ssiDrug_Builder;
 }
  /**
 * Sets the new value of "vaccineKeywordsText" (any previous value will be
 * replaced)
 * 
 * @param vaccineKeywordsText
 *     New value of the "vaccineKeywordsText" property.
 */
 public VaccineType.Builder<_B> withVaccineKeywordsText(final String vaccineKeywordsText) {
  this.vaccineKeywordsText = vaccineKeywordsText;
  return this;
 }
  /**
 * Adds the given items to the value of "displayMinimumVaccinationPlan"
 * 
 * @param displayMinimumVaccinationPlan
 *     Items to add to the value of the "displayMinimumVaccinationPlan" property
 */
 public VaccineType.Builder<_B> addDisplayMinimumVaccinationPlan(final Iterable<? extends DisplayMinimumVaccinationPlanType> displayMinimumVaccinationPlan) {
  if (displayMinimumVaccinationPlan!= null) {
   if (this.displayMinimumVaccinationPlan == null) {
     this.displayMinimumVaccinationPlan = new ArrayList<>();
   }
   for (DisplayMinimumVaccinationPlanType _item: displayMinimumVaccinationPlan) {
     this.displayMinimumVaccinationPlan.add(new DisplayMinimumVaccinationPlanType.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "displayMinimumVaccinationPlan" (any previous value will
 * be replaced)
 * 
 * @param displayMinimumVaccinationPlan
 *     New value of the "displayMinimumVaccinationPlan" property.
 */
 public VaccineType.Builder<_B> withDisplayMinimumVaccinationPlan(final Iterable<? extends DisplayMinimumVaccinationPlanType> displayMinimumVaccinationPlan) {
  if (this.displayMinimumVaccinationPlan!= null) {
   this.displayMinimumVaccinationPlan.clear();
  }
  return addDisplayMinimumVaccinationPlan(displayMinimumVaccinationPlan);
 }
  /**
 * Adds the given items to the value of "displayMinimumVaccinationPlan"
 * 
 * @param displayMinimumVaccinationPlan
 *     Items to add to the value of the "displayMinimumVaccinationPlan" property
 */
 public VaccineType.Builder<_B> addDisplayMinimumVaccinationPlan(DisplayMinimumVaccinationPlanType... displayMinimumVaccinationPlan) {
  addDisplayMinimumVaccinationPlan(Arrays.asList(displayMinimumVaccinationPlan));
  return this;
 }
  /**
 * Sets the new value of "displayMinimumVaccinationPlan" (any previous value will
 * be replaced)
 * 
 * @param displayMinimumVaccinationPlan
 *     New value of the "displayMinimumVaccinationPlan" property.
 */
 public VaccineType.Builder<_B> withDisplayMinimumVaccinationPlan(DisplayMinimumVaccinationPlanType... displayMinimumVaccinationPlan) {
  withDisplayMinimumVaccinationPlan(Arrays.asList(displayMinimumVaccinationPlan));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the
 * "DisplayMinimumVaccinationPlan" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.DisplayMinimumVaccinationPlanType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the
 *     "DisplayMinimumVaccinationPlan" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.DisplayMinimumVaccinationPlanType.Builder#end()}
 *     to return to the current builder.
 */
 public DisplayMinimumVaccinationPlanType.Builder<? extends VaccineType.Builder<_B>> addDisplayMinimumVaccinationPlan() {
  if (this.displayMinimumVaccinationPlan == null) {
   this.displayMinimumVaccinationPlan = new ArrayList<>();
  }
  final DisplayMinimumVaccinationPlanType.Builder<VaccineType.Builder<_B>> displayMinimumVaccinationPlan_Builder = new DisplayMinimumVaccinationPlanType.Builder<>(this, null, false);
  this.displayMinimumVaccinationPlan.add(displayMinimumVaccinationPlan_Builder);
  return displayMinimumVaccinationPlan_Builder;
 }
  @Override
 public VaccineType build() {
  if (_storedValue == null) {
   return this.init(new VaccineType());
  } else {
   return ((VaccineType) _storedValue);
  }
 }
  public VaccineType.Builder<_B> copyOf(final VaccineType _other) {
  _other.copyTo(this);
  return this;
 }
  public VaccineType.Builder<_B> copyOf(final VaccineType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends VaccineType.Selector<VaccineType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static VaccineType.Select _root() {
  return new VaccineType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> vaccineName = null;
 private DiseaseType.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> disease = null;
 private ATCType.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> atc = null;
 private SSIDrugType.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> ssiDrug = null;
 private com.kscs.util.jaxb.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> vaccineKeywordsText = null;
 private DisplayMinimumVaccinationPlanType.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> displayMinimumVaccinationPlan = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.vaccineName!= null) {
   products.put("vaccineName", this.vaccineName.init());
  }
  if (this.disease!= null) {
   products.put("disease", this.disease.init());
  }
  if (this.atc!= null) {
   products.put("atc", this.atc.init());
  }
  if (this.ssiDrug!= null) {
   products.put("ssiDrug", this.ssiDrug.init());
  }
  if (this.vaccineKeywordsText!= null) {
   products.put("vaccineKeywordsText", this.vaccineKeywordsText.init());
  }
  if (this.displayMinimumVaccinationPlan!= null) {
   products.put("displayMinimumVaccinationPlan", this.displayMinimumVaccinationPlan.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> vaccineName() {
  return ((this.vaccineName == null)?this.vaccineName = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccineName"):this.vaccineName);
 }
  public DiseaseType.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> disease() {
  return ((this.disease == null)?this.disease = new DiseaseType.Selector<>(this._root, this, "disease"):this.disease);
 }
  public ATCType.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> atc() {
  return ((this.atc == null)?this.atc = new ATCType.Selector<>(this._root, this, "atc"):this.atc);
 }
  public SSIDrugType.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> ssiDrug() {
  return ((this.ssiDrug == null)?this.ssiDrug = new SSIDrugType.Selector<>(this._root, this, "ssiDrug"):this.ssiDrug);
 }
  public com.kscs.util.jaxb.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> vaccineKeywordsText() {
  return ((this.vaccineKeywordsText == null)?this.vaccineKeywordsText = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccineKeywordsText"):this.vaccineKeywordsText);
 }
  public DisplayMinimumVaccinationPlanType.Selector<TRoot, VaccineType.Selector<TRoot, TParent>> displayMinimumVaccinationPlan() {
  return ((this.displayMinimumVaccinationPlan == null)?this.displayMinimumVaccinationPlan = new DisplayMinimumVaccinationPlanType.Selector<>(this._root, this, "displayMinimumVaccinationPlan"):this.displayMinimumVaccinationPlan);
 }
  }
 }
