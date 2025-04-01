package dk.vaccinationsregister.schemas._2013._12._01;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17.SimpleCPRPersonType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for GetVaccinationPassportAsPDFResponseType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="GetVaccinationPassportAsPDFResponseType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="VaccinationStatus" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationStatus" minOccurs="0"/>
*         <element name="DocumentId" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DocumentIdType"/>
*         <element name="PersonInfo" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2006/01/17/}SimpleCPRPersonType"/>
*         <element name="VaccinationAgainst" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationAgainstType"/>
*         <element name="Effectuation" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuationOverviewType" maxOccurs="unbounded" minOccurs="0"/>
*         <element name="AdditionalEffectuation" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuationOverviewType" maxOccurs="unbounded" minOccurs="0"/>
*         <element name="VaccinationPassportAsPDF" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPassportAsPDFType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetVaccinationPassportAsPDFResponseType", propOrder = {
 "vaccinationStatus",
 "documentId",
 "personInfo",
 "vaccinationAgainst",
 "effectuation",
 "additionalEffectuation",
 "vaccinationPassportAsPDF"
})
public class GetVaccinationPassportAsPDFResponseType {
  @XmlElement(name = "VaccinationStatus")
 @XmlSchemaType(name = "string")
 protected VaccinationStatus vaccinationStatus;
 @XmlElement(name = "DocumentId", required = true)
 protected String documentId;
 @XmlElement(name = "PersonInfo", required = true)
 protected SimpleCPRPersonType personInfo;
 @XmlElement(name = "VaccinationAgainst", required = true)
 protected String vaccinationAgainst;
 @XmlElement(name = "Effectuation")
 protected List<EffectuationOverviewType> effectuation;
 @XmlElement(name = "AdditionalEffectuation")
 protected List<EffectuationOverviewType> additionalEffectuation;
 @XmlElement(name = "VaccinationPassportAsPDF")
 protected byte[] vaccinationPassportAsPDF;
  /**
 * Gets the value of the vaccinationStatus property.
 * 
 * @return
 *     possible object is
 *     {@link VaccinationStatus }
 *     
 */
 public VaccinationStatus getVaccinationStatus() {
 return vaccinationStatus;
 }
  /**
 * Sets the value of the vaccinationStatus property.
 * 
 * @param value
 *     allowed object is
 *     {@link VaccinationStatus }
 *     
 */
 public void setVaccinationStatus(VaccinationStatus value) {
 this.vaccinationStatus = value;
 }
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
 * Gets the value of the personInfo property.
 * 
 * @return
 *     possible object is
 *     {@link SimpleCPRPersonType }
 *     
 */
 public SimpleCPRPersonType getPersonInfo() {
 return personInfo;
 }
  /**
 * Sets the value of the personInfo property.
 * 
 * @param value
 *     allowed object is
 *     {@link SimpleCPRPersonType }
 *     
 */
 public void setPersonInfo(SimpleCPRPersonType value) {
 this.personInfo = value;
 }
  /**
 * Gets the value of the vaccinationAgainst property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getVaccinationAgainst() {
 return vaccinationAgainst;
 }
  /**
 * Sets the value of the vaccinationAgainst property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setVaccinationAgainst(String value) {
 this.vaccinationAgainst = value;
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
 * Gets the value of the additionalEffectuation property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the additionalEffectuation property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getAdditionalEffectuation().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link EffectuationOverviewType }
 * 
 * 
 * @return
 *     The value of the additionalEffectuation property.
 */
 public List<EffectuationOverviewType> getAdditionalEffectuation() {
 if (additionalEffectuation == null) {
  additionalEffectuation = new ArrayList<>();
 }
 return this.additionalEffectuation;
 }
  /**
 * Gets the value of the vaccinationPassportAsPDF property.
 * 
 * @return
 *     possible object is
 *     byte[]
 */
 public byte[] getVaccinationPassportAsPDF() {
 return vaccinationPassportAsPDF;
 }
  /**
 * Sets the value of the vaccinationPassportAsPDF property.
 * 
 * @param value
 *     allowed object is
 *     byte[]
 */
 public void setVaccinationPassportAsPDF(byte[] value) {
 this.vaccinationPassportAsPDF = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final GetVaccinationPassportAsPDFResponseType.Builder<_B> _other) {
 _other.vaccinationStatus = this.vaccinationStatus;
 _other.documentId = this.documentId;
 _other.personInfo = ((this.personInfo == null)?null:this.personInfo.newCopyBuilder(_other));
 _other.vaccinationAgainst = this.vaccinationAgainst;
 if (this.effectuation == null) {
  _other.effectuation = null;
 } else {
  _other.effectuation = new ArrayList<>();
  for (EffectuationOverviewType _item: this.effectuation) {
   _other.effectuation.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 if (this.additionalEffectuation == null) {
  _other.additionalEffectuation = null;
 } else {
  _other.additionalEffectuation = new ArrayList<>();
  for (EffectuationOverviewType _item: this.additionalEffectuation) {
   _other.additionalEffectuation.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 _other.vaccinationPassportAsPDF = this.vaccinationPassportAsPDF;
 }
  public<_B >GetVaccinationPassportAsPDFResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new GetVaccinationPassportAsPDFResponseType.Builder<_B>(_parentBuilder, this, true);
 }
  public GetVaccinationPassportAsPDFResponseType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static GetVaccinationPassportAsPDFResponseType.Builder<Void> builder() {
 return new GetVaccinationPassportAsPDFResponseType.Builder<>(null, null, false);
 }
  public static<_B >GetVaccinationPassportAsPDFResponseType.Builder<_B> copyOf(final GetVaccinationPassportAsPDFResponseType _other) {
 final GetVaccinationPassportAsPDFResponseType.Builder<_B> _newBuilder = new GetVaccinationPassportAsPDFResponseType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final GetVaccinationPassportAsPDFResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree vaccinationStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationStatus"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationStatusPropertyTree!= null):((vaccinationStatusPropertyTree == null)||(!vaccinationStatusPropertyTree.isLeaf())))) {
  _other.vaccinationStatus = this.vaccinationStatus;
 }
 final PropertyTree documentIdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("documentId"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(documentIdPropertyTree!= null):((documentIdPropertyTree == null)||(!documentIdPropertyTree.isLeaf())))) {
  _other.documentId = this.documentId;
 }
 final PropertyTree personInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personInfo"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personInfoPropertyTree!= null):((personInfoPropertyTree == null)||(!personInfoPropertyTree.isLeaf())))) {
  _other.personInfo = ((this.personInfo == null)?null:this.personInfo.newCopyBuilder(_other, personInfoPropertyTree, _propertyTreeUse));
 }
 final PropertyTree vaccinationAgainstPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationAgainst"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationAgainstPropertyTree!= null):((vaccinationAgainstPropertyTree == null)||(!vaccinationAgainstPropertyTree.isLeaf())))) {
  _other.vaccinationAgainst = this.vaccinationAgainst;
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
 final PropertyTree additionalEffectuationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("additionalEffectuation"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(additionalEffectuationPropertyTree!= null):((additionalEffectuationPropertyTree == null)||(!additionalEffectuationPropertyTree.isLeaf())))) {
  if (this.additionalEffectuation == null) {
   _other.additionalEffectuation = null;
  } else {
   _other.additionalEffectuation = new ArrayList<>();
   for (EffectuationOverviewType _item: this.additionalEffectuation) {
     _other.additionalEffectuation.add(((_item == null)?null:_item.newCopyBuilder(_other, additionalEffectuationPropertyTree, _propertyTreeUse)));
   }
  }
 }
 final PropertyTree vaccinationPassportAsPDFPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPassportAsPDF"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPassportAsPDFPropertyTree!= null):((vaccinationPassportAsPDFPropertyTree == null)||(!vaccinationPassportAsPDFPropertyTree.isLeaf())))) {
  _other.vaccinationPassportAsPDF = this.vaccinationPassportAsPDF;
 }
 }
  public<_B >GetVaccinationPassportAsPDFResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new GetVaccinationPassportAsPDFResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public GetVaccinationPassportAsPDFResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >GetVaccinationPassportAsPDFResponseType.Builder<_B> copyOf(final GetVaccinationPassportAsPDFResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final GetVaccinationPassportAsPDFResponseType.Builder<_B> _newBuilder = new GetVaccinationPassportAsPDFResponseType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static GetVaccinationPassportAsPDFResponseType.Builder<Void> copyExcept(final GetVaccinationPassportAsPDFResponseType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static GetVaccinationPassportAsPDFResponseType.Builder<Void> copyOnly(final GetVaccinationPassportAsPDFResponseType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final GetVaccinationPassportAsPDFResponseType _storedValue;
 private VaccinationStatus vaccinationStatus;
 private String documentId;
 private SimpleCPRPersonType.Builder<GetVaccinationPassportAsPDFResponseType.Builder<_B>> personInfo;
 private String vaccinationAgainst;
 private List<EffectuationOverviewType.Builder<GetVaccinationPassportAsPDFResponseType.Builder<_B>>> effectuation;
 private List<EffectuationOverviewType.Builder<GetVaccinationPassportAsPDFResponseType.Builder<_B>>> additionalEffectuation;
 private byte[] vaccinationPassportAsPDF;
  public Builder(final _B _parentBuilder, final GetVaccinationPassportAsPDFResponseType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.vaccinationStatus = _other.vaccinationStatus;
     this.documentId = _other.documentId;
     this.personInfo = ((_other.personInfo == null)?null:_other.personInfo.newCopyBuilder(this));
     this.vaccinationAgainst = _other.vaccinationAgainst;
     if (_other.effectuation == null) {
        this.effectuation = null;
     } else {
        this.effectuation = new ArrayList<>();
        for (EffectuationOverviewType _item: _other.effectuation) {
             this.effectuation.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
     if (_other.additionalEffectuation == null) {
        this.additionalEffectuation = null;
     } else {
        this.additionalEffectuation = new ArrayList<>();
        for (EffectuationOverviewType _item: _other.additionalEffectuation) {
             this.additionalEffectuation.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
     this.vaccinationPassportAsPDF = _other.vaccinationPassportAsPDF;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final GetVaccinationPassportAsPDFResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree vaccinationStatusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationStatus"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationStatusPropertyTree!= null):((vaccinationStatusPropertyTree == null)||(!vaccinationStatusPropertyTree.isLeaf())))) {
        this.vaccinationStatus = _other.vaccinationStatus;
     }
     final PropertyTree documentIdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("documentId"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(documentIdPropertyTree!= null):((documentIdPropertyTree == null)||(!documentIdPropertyTree.isLeaf())))) {
        this.documentId = _other.documentId;
     }
     final PropertyTree personInfoPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personInfo"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personInfoPropertyTree!= null):((personInfoPropertyTree == null)||(!personInfoPropertyTree.isLeaf())))) {
        this.personInfo = ((_other.personInfo == null)?null:_other.personInfo.newCopyBuilder(this, personInfoPropertyTree, _propertyTreeUse));
     }
     final PropertyTree vaccinationAgainstPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationAgainst"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationAgainstPropertyTree!= null):((vaccinationAgainstPropertyTree == null)||(!vaccinationAgainstPropertyTree.isLeaf())))) {
        this.vaccinationAgainst = _other.vaccinationAgainst;
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
     final PropertyTree additionalEffectuationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("additionalEffectuation"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(additionalEffectuationPropertyTree!= null):((additionalEffectuationPropertyTree == null)||(!additionalEffectuationPropertyTree.isLeaf())))) {
        if (_other.additionalEffectuation == null) {
             this.additionalEffectuation = null;
        } else {
             this.additionalEffectuation = new ArrayList<>();
             for (EffectuationOverviewType _item: _other.additionalEffectuation) {
                     this.additionalEffectuation.add(((_item == null)?null:_item.newCopyBuilder(this, additionalEffectuationPropertyTree, _propertyTreeUse)));
             }
        }
     }
     final PropertyTree vaccinationPassportAsPDFPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPassportAsPDF"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPassportAsPDFPropertyTree!= null):((vaccinationPassportAsPDFPropertyTree == null)||(!vaccinationPassportAsPDFPropertyTree.isLeaf())))) {
        this.vaccinationPassportAsPDF = _other.vaccinationPassportAsPDF;
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
  protected<_P extends GetVaccinationPassportAsPDFResponseType >_P init(final _P _product) {
  _product.vaccinationStatus = this.vaccinationStatus;
  _product.documentId = this.documentId;
  _product.personInfo = ((this.personInfo == null)?null:this.personInfo.build());
  _product.vaccinationAgainst = this.vaccinationAgainst;
  if (this.effectuation!= null) {
   final List<EffectuationOverviewType> effectuation = new ArrayList<>(this.effectuation.size());
   for (EffectuationOverviewType.Builder<GetVaccinationPassportAsPDFResponseType.Builder<_B>> _item: this.effectuation) {
     effectuation.add(_item.build());
   }
   _product.effectuation = effectuation;
  }
  if (this.additionalEffectuation!= null) {
   final List<EffectuationOverviewType> additionalEffectuation = new ArrayList<>(this.additionalEffectuation.size());
   for (EffectuationOverviewType.Builder<GetVaccinationPassportAsPDFResponseType.Builder<_B>> _item: this.additionalEffectuation) {
     additionalEffectuation.add(_item.build());
   }
   _product.additionalEffectuation = additionalEffectuation;
  }
  _product.vaccinationPassportAsPDF = this.vaccinationPassportAsPDF;
  return _product;
 }
  /**
 * Sets the new value of "vaccinationStatus" (any previous value will be replaced)
 * 
 * @param vaccinationStatus
 *     New value of the "vaccinationStatus" property.
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> withVaccinationStatus(final VaccinationStatus vaccinationStatus) {
  this.vaccinationStatus = vaccinationStatus;
  return this;
 }
  /**
 * Sets the new value of "documentId" (any previous value will be replaced)
 * 
 * @param documentId
 *     New value of the "documentId" property.
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> withDocumentId(final String documentId) {
  this.documentId = documentId;
  return this;
 }
  /**
 * Sets the new value of "personInfo" (any previous value will be replaced)
 * 
 * @param personInfo
 *     New value of the "personInfo" property.
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> withPersonInfo(final SimpleCPRPersonType personInfo) {
  this.personInfo = ((personInfo == null)?null:new SimpleCPRPersonType.Builder<>(this, personInfo, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "personInfo" property.
 * Use {@link
 * dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17.SimpleCPRPersonType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "personInfo" property.
 *     Use {@link
 *     dk.oio.rep.cpr_dk.xml.schemas.core._2006._01._17.SimpleCPRPersonType.Builder#end()}
 *     to return to the current builder.
 */
 public SimpleCPRPersonType.Builder<? extends GetVaccinationPassportAsPDFResponseType.Builder<_B>> withPersonInfo() {
  if (this.personInfo!= null) {
   return this.personInfo;
  }
  return this.personInfo = new SimpleCPRPersonType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "vaccinationAgainst" (any previous value will be replaced)
 * 
 * @param vaccinationAgainst
 *     New value of the "vaccinationAgainst" property.
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> withVaccinationAgainst(final String vaccinationAgainst) {
  this.vaccinationAgainst = vaccinationAgainst;
  return this;
 }
  /**
 * Adds the given items to the value of "effectuation"
 * 
 * @param effectuation
 *     Items to add to the value of the "effectuation" property
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> addEffectuation(final Iterable<? extends EffectuationOverviewType> effectuation) {
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
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> withEffectuation(final Iterable<? extends EffectuationOverviewType> effectuation) {
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
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> addEffectuation(EffectuationOverviewType... effectuation) {
  addEffectuation(Arrays.asList(effectuation));
  return this;
 }
  /**
 * Sets the new value of "effectuation" (any previous value will be replaced)
 * 
 * @param effectuation
 *     New value of the "effectuation" property.
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> withEffectuation(EffectuationOverviewType... effectuation) {
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
 public EffectuationOverviewType.Builder<? extends GetVaccinationPassportAsPDFResponseType.Builder<_B>> addEffectuation() {
  if (this.effectuation == null) {
   this.effectuation = new ArrayList<>();
  }
  final EffectuationOverviewType.Builder<GetVaccinationPassportAsPDFResponseType.Builder<_B>> effectuation_Builder = new EffectuationOverviewType.Builder<>(this, null, false);
  this.effectuation.add(effectuation_Builder);
  return effectuation_Builder;
 }
  /**
 * Adds the given items to the value of "additionalEffectuation"
 * 
 * @param additionalEffectuation
 *     Items to add to the value of the "additionalEffectuation" property
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> addAdditionalEffectuation(final Iterable<? extends EffectuationOverviewType> additionalEffectuation) {
  if (additionalEffectuation!= null) {
   if (this.additionalEffectuation == null) {
     this.additionalEffectuation = new ArrayList<>();
   }
   for (EffectuationOverviewType _item: additionalEffectuation) {
     this.additionalEffectuation.add(new EffectuationOverviewType.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "additionalEffectuation" (any previous value will be
 * replaced)
 * 
 * @param additionalEffectuation
 *     New value of the "additionalEffectuation" property.
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> withAdditionalEffectuation(final Iterable<? extends EffectuationOverviewType> additionalEffectuation) {
  if (this.additionalEffectuation!= null) {
   this.additionalEffectuation.clear();
  }
  return addAdditionalEffectuation(additionalEffectuation);
 }
  /**
 * Adds the given items to the value of "additionalEffectuation"
 * 
 * @param additionalEffectuation
 *     Items to add to the value of the "additionalEffectuation" property
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> addAdditionalEffectuation(EffectuationOverviewType... additionalEffectuation) {
  addAdditionalEffectuation(Arrays.asList(additionalEffectuation));
  return this;
 }
  /**
 * Sets the new value of "additionalEffectuation" (any previous value will be
 * replaced)
 * 
 * @param additionalEffectuation
 *     New value of the "additionalEffectuation" property.
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> withAdditionalEffectuation(EffectuationOverviewType... additionalEffectuation) {
  withAdditionalEffectuation(Arrays.asList(additionalEffectuation));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the
 * "AdditionalEffectuation" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.EffectuationOverviewType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "AdditionalEffectuation"
 *     property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.EffectuationOverviewType.Builder#end()}
 *     to return to the current builder.
 */
 public EffectuationOverviewType.Builder<? extends GetVaccinationPassportAsPDFResponseType.Builder<_B>> addAdditionalEffectuation() {
  if (this.additionalEffectuation == null) {
   this.additionalEffectuation = new ArrayList<>();
  }
  final EffectuationOverviewType.Builder<GetVaccinationPassportAsPDFResponseType.Builder<_B>> additionalEffectuation_Builder = new EffectuationOverviewType.Builder<>(this, null, false);
  this.additionalEffectuation.add(additionalEffectuation_Builder);
  return additionalEffectuation_Builder;
 }
  /**
 * Sets the new value of "vaccinationPassportAsPDF" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPassportAsPDF
 *     New value of the "vaccinationPassportAsPDF" property.
 */
 public GetVaccinationPassportAsPDFResponseType.Builder<_B> withVaccinationPassportAsPDF(final byte[] vaccinationPassportAsPDF) {
  this.vaccinationPassportAsPDF = vaccinationPassportAsPDF;
  return this;
 }
  @Override
 public GetVaccinationPassportAsPDFResponseType build() {
  if (_storedValue == null) {
   return this.init(new GetVaccinationPassportAsPDFResponseType());
  } else {
   return ((GetVaccinationPassportAsPDFResponseType) _storedValue);
  }
 }
  public GetVaccinationPassportAsPDFResponseType.Builder<_B> copyOf(final GetVaccinationPassportAsPDFResponseType _other) {
  _other.copyTo(this);
  return this;
 }
  public GetVaccinationPassportAsPDFResponseType.Builder<_B> copyOf(final GetVaccinationPassportAsPDFResponseType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends GetVaccinationPassportAsPDFResponseType.Selector<GetVaccinationPassportAsPDFResponseType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static GetVaccinationPassportAsPDFResponseType.Select _root() {
  return new GetVaccinationPassportAsPDFResponseType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> vaccinationStatus = null;
 private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> documentId = null;
 private SimpleCPRPersonType.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> personInfo = null;
 private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> vaccinationAgainst = null;
 private EffectuationOverviewType.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> effectuation = null;
 private EffectuationOverviewType.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> additionalEffectuation = null;
 private com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> vaccinationPassportAsPDF = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.vaccinationStatus!= null) {
   products.put("vaccinationStatus", this.vaccinationStatus.init());
  }
  if (this.documentId!= null) {
   products.put("documentId", this.documentId.init());
  }
  if (this.personInfo!= null) {
   products.put("personInfo", this.personInfo.init());
  }
  if (this.vaccinationAgainst!= null) {
   products.put("vaccinationAgainst", this.vaccinationAgainst.init());
  }
  if (this.effectuation!= null) {
   products.put("effectuation", this.effectuation.init());
  }
  if (this.additionalEffectuation!= null) {
   products.put("additionalEffectuation", this.additionalEffectuation.init());
  }
  if (this.vaccinationPassportAsPDF!= null) {
   products.put("vaccinationPassportAsPDF", this.vaccinationPassportAsPDF.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> vaccinationStatus() {
  return ((this.vaccinationStatus == null)?this.vaccinationStatus = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationStatus"):this.vaccinationStatus);
 }
  public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> documentId() {
  return ((this.documentId == null)?this.documentId = new com.kscs.util.jaxb.Selector<>(this._root, this, "documentId"):this.documentId);
 }
  public SimpleCPRPersonType.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> personInfo() {
  return ((this.personInfo == null)?this.personInfo = new SimpleCPRPersonType.Selector<>(this._root, this, "personInfo"):this.personInfo);
 }
  public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> vaccinationAgainst() {
  return ((this.vaccinationAgainst == null)?this.vaccinationAgainst = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationAgainst"):this.vaccinationAgainst);
 }
  public EffectuationOverviewType.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> effectuation() {
  return ((this.effectuation == null)?this.effectuation = new EffectuationOverviewType.Selector<>(this._root, this, "effectuation"):this.effectuation);
 }
  public EffectuationOverviewType.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> additionalEffectuation() {
  return ((this.additionalEffectuation == null)?this.additionalEffectuation = new EffectuationOverviewType.Selector<>(this._root, this, "additionalEffectuation"):this.additionalEffectuation);
 }
  public com.kscs.util.jaxb.Selector<TRoot, GetVaccinationPassportAsPDFResponseType.Selector<TRoot, TParent>> vaccinationPassportAsPDF() {
  return ((this.vaccinationPassportAsPDF == null)?this.vaccinationPassportAsPDF = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPassportAsPDF"):this.vaccinationPassportAsPDF);
 }
  }
 }
