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
* <p>Java class for EffectuatePlannedVaccinationRequestType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="EffectuatePlannedVaccinationRequestType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
*         <element name="Created" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType"/>
*         <element name="Reported" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType" minOccurs="0"/>
*         <element name="EffectuatePlannedVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EffectuatePlannedVaccinationType"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EffectuatePlannedVaccinationRequestType", propOrder = {
 "personCivilRegistrationIdentifier",
 "created",
 "reported",
 "effectuatePlannedVaccination"
})
public class EffectuatePlannedVaccinationRequestType {
  @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
 protected String personCivilRegistrationIdentifier;
 @XmlElement(name = "Created", required = true)
 protected ModifiedType created;
 @XmlElement(name = "Reported")
 protected ModifiedType reported;
 @XmlElement(name = "EffectuatePlannedVaccination", required = true)
 protected EffectuatePlannedVaccinationType effectuatePlannedVaccination;
  /**
 * Gets the value of the personCivilRegistrationIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getPersonCivilRegistrationIdentifier() {
 return personCivilRegistrationIdentifier;
 }
  /**
 * Sets the value of the personCivilRegistrationIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setPersonCivilRegistrationIdentifier(String value) {
 this.personCivilRegistrationIdentifier = value;
 }
  /**
 * Gets the value of the created property.
 * 
 * @return
 *     possible object is
 *     {@link ModifiedType }
 *     
 */
 public ModifiedType getCreated() {
 return created;
 }
  /**
 * Sets the value of the created property.
 * 
 * @param value
 *     allowed object is
 *     {@link ModifiedType }
 *     
 */
 public void setCreated(ModifiedType value) {
 this.created = value;
 }
  /**
 * Gets the value of the reported property.
 * 
 * @return
 *     possible object is
 *     {@link ModifiedType }
 *     
 */
 public ModifiedType getReported() {
 return reported;
 }
  /**
 * Sets the value of the reported property.
 * 
 * @param value
 *     allowed object is
 *     {@link ModifiedType }
 *     
 */
 public void setReported(ModifiedType value) {
 this.reported = value;
 }
  /**
 * Gets the value of the effectuatePlannedVaccination property.
 * 
 * @return
 *     possible object is
 *     {@link EffectuatePlannedVaccinationType }
 *     
 */
 public EffectuatePlannedVaccinationType getEffectuatePlannedVaccination() {
 return effectuatePlannedVaccination;
 }
  /**
 * Sets the value of the effectuatePlannedVaccination property.
 * 
 * @param value
 *     allowed object is
 *     {@link EffectuatePlannedVaccinationType }
 *     
 */
 public void setEffectuatePlannedVaccination(EffectuatePlannedVaccinationType value) {
 this.effectuatePlannedVaccination = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final EffectuatePlannedVaccinationRequestType.Builder<_B> _other) {
 _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other));
 _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other));
 _other.effectuatePlannedVaccination = ((this.effectuatePlannedVaccination == null)?null:this.effectuatePlannedVaccination.newCopyBuilder(_other));
 }
  public<_B >EffectuatePlannedVaccinationRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new EffectuatePlannedVaccinationRequestType.Builder<_B>(_parentBuilder, this, true);
 }
  public EffectuatePlannedVaccinationRequestType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static EffectuatePlannedVaccinationRequestType.Builder<Void> builder() {
 return new EffectuatePlannedVaccinationRequestType.Builder<>(null, null, false);
 }
  public static<_B >EffectuatePlannedVaccinationRequestType.Builder<_B> copyOf(final EffectuatePlannedVaccinationRequestType _other) {
 final EffectuatePlannedVaccinationRequestType.Builder<_B> _newBuilder = new EffectuatePlannedVaccinationRequestType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final EffectuatePlannedVaccinationRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
  _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 }
 final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
  _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other, createdPropertyTree, _propertyTreeUse));
 }
 final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
  _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other, reportedPropertyTree, _propertyTreeUse));
 }
 final PropertyTree effectuatePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatePlannedVaccination"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatePlannedVaccinationPropertyTree!= null):((effectuatePlannedVaccinationPropertyTree == null)||(!effectuatePlannedVaccinationPropertyTree.isLeaf())))) {
  _other.effectuatePlannedVaccination = ((this.effectuatePlannedVaccination == null)?null:this.effectuatePlannedVaccination.newCopyBuilder(_other, effectuatePlannedVaccinationPropertyTree, _propertyTreeUse));
 }
 }
  public<_B >EffectuatePlannedVaccinationRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new EffectuatePlannedVaccinationRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public EffectuatePlannedVaccinationRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >EffectuatePlannedVaccinationRequestType.Builder<_B> copyOf(final EffectuatePlannedVaccinationRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final EffectuatePlannedVaccinationRequestType.Builder<_B> _newBuilder = new EffectuatePlannedVaccinationRequestType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static EffectuatePlannedVaccinationRequestType.Builder<Void> copyExcept(final EffectuatePlannedVaccinationRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static EffectuatePlannedVaccinationRequestType.Builder<Void> copyOnly(final EffectuatePlannedVaccinationRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final EffectuatePlannedVaccinationRequestType _storedValue;
 private String personCivilRegistrationIdentifier;
 private ModifiedType.Builder<EffectuatePlannedVaccinationRequestType.Builder<_B>> created;
 private ModifiedType.Builder<EffectuatePlannedVaccinationRequestType.Builder<_B>> reported;
 private EffectuatePlannedVaccinationType.Builder<EffectuatePlannedVaccinationRequestType.Builder<_B>> effectuatePlannedVaccination;
  public Builder(final _B _parentBuilder, final EffectuatePlannedVaccinationRequestType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this));
     this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this));
     this.effectuatePlannedVaccination = ((_other.effectuatePlannedVaccination == null)?null:_other.effectuatePlannedVaccination.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final EffectuatePlannedVaccinationRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     }
     final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
        this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this, createdPropertyTree, _propertyTreeUse));
     }
     final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
        this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this, reportedPropertyTree, _propertyTreeUse));
     }
     final PropertyTree effectuatePlannedVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("effectuatePlannedVaccination"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(effectuatePlannedVaccinationPropertyTree!= null):((effectuatePlannedVaccinationPropertyTree == null)||(!effectuatePlannedVaccinationPropertyTree.isLeaf())))) {
        this.effectuatePlannedVaccination = ((_other.effectuatePlannedVaccination == null)?null:_other.effectuatePlannedVaccination.newCopyBuilder(this, effectuatePlannedVaccinationPropertyTree, _propertyTreeUse));
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
  protected<_P extends EffectuatePlannedVaccinationRequestType >_P init(final _P _product) {
  _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
  _product.created = ((this.created == null)?null:this.created.build());
  _product.reported = ((this.reported == null)?null:this.reported.build());
  _product.effectuatePlannedVaccination = ((this.effectuatePlannedVaccination == null)?null:this.effectuatePlannedVaccination.build());
  return _product;
 }
  /**
 * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
 * will be replaced)
 * 
 * @param personCivilRegistrationIdentifier
 *     New value of the "personCivilRegistrationIdentifier" property.
 */
 public EffectuatePlannedVaccinationRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
  this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "created" (any previous value will be replaced)
 * 
 * @param created
 *     New value of the "created" property.
 */
 public EffectuatePlannedVaccinationRequestType.Builder<_B> withCreated(final ModifiedType created) {
  this.created = ((created == null)?null:new ModifiedType.Builder<>(this, created, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "created" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "created" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
 *     return to the current builder.
 */
 public ModifiedType.Builder<? extends EffectuatePlannedVaccinationRequestType.Builder<_B>> withCreated() {
  if (this.created!= null) {
   return this.created;
  }
  return this.created = new ModifiedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "reported" (any previous value will be replaced)
 * 
 * @param reported
 *     New value of the "reported" property.
 */
 public EffectuatePlannedVaccinationRequestType.Builder<_B> withReported(final ModifiedType reported) {
  this.reported = ((reported == null)?null:new ModifiedType.Builder<>(this, reported, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "reported" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "reported" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
 *     return to the current builder.
 */
 public ModifiedType.Builder<? extends EffectuatePlannedVaccinationRequestType.Builder<_B>> withReported() {
  if (this.reported!= null) {
   return this.reported;
  }
  return this.reported = new ModifiedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "effectuatePlannedVaccination" (any previous value will be
 * replaced)
 * 
 * @param effectuatePlannedVaccination
 *     New value of the "effectuatePlannedVaccination" property.
 */
 public EffectuatePlannedVaccinationRequestType.Builder<_B> withEffectuatePlannedVaccination(final EffectuatePlannedVaccinationType effectuatePlannedVaccination) {
  this.effectuatePlannedVaccination = ((effectuatePlannedVaccination == null)?null:new EffectuatePlannedVaccinationType.Builder<>(this, effectuatePlannedVaccination, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "effectuatePlannedVaccination" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.EffectuatePlannedVaccinationType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "effectuatePlannedVaccination" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.EffectuatePlannedVaccinationType.Builder#end()}
 *     to return to the current builder.
 */
 public EffectuatePlannedVaccinationType.Builder<? extends EffectuatePlannedVaccinationRequestType.Builder<_B>> withEffectuatePlannedVaccination() {
  if (this.effectuatePlannedVaccination!= null) {
   return this.effectuatePlannedVaccination;
  }
  return this.effectuatePlannedVaccination = new EffectuatePlannedVaccinationType.Builder<>(this, null, false);
 }
  @Override
 public EffectuatePlannedVaccinationRequestType build() {
  if (_storedValue == null) {
   return this.init(new EffectuatePlannedVaccinationRequestType());
  } else {
   return ((EffectuatePlannedVaccinationRequestType) _storedValue);
  }
 }
  public EffectuatePlannedVaccinationRequestType.Builder<_B> copyOf(final EffectuatePlannedVaccinationRequestType _other) {
  _other.copyTo(this);
  return this;
 }
  public EffectuatePlannedVaccinationRequestType.Builder<_B> copyOf(final EffectuatePlannedVaccinationRequestType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends EffectuatePlannedVaccinationRequestType.Selector<EffectuatePlannedVaccinationRequestType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static EffectuatePlannedVaccinationRequestType.Select _root() {
  return new EffectuatePlannedVaccinationRequestType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, EffectuatePlannedVaccinationRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
 private ModifiedType.Selector<TRoot, EffectuatePlannedVaccinationRequestType.Selector<TRoot, TParent>> created = null;
 private ModifiedType.Selector<TRoot, EffectuatePlannedVaccinationRequestType.Selector<TRoot, TParent>> reported = null;
 private EffectuatePlannedVaccinationType.Selector<TRoot, EffectuatePlannedVaccinationRequestType.Selector<TRoot, TParent>> effectuatePlannedVaccination = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.personCivilRegistrationIdentifier!= null) {
   products.put("personCivilRegistrationIdentifier", this.personCivilRegistrationIdentifier.init());
  }
  if (this.created!= null) {
   products.put("created", this.created.init());
  }
  if (this.reported!= null) {
   products.put("reported", this.reported.init());
  }
  if (this.effectuatePlannedVaccination!= null) {
   products.put("effectuatePlannedVaccination", this.effectuatePlannedVaccination.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, EffectuatePlannedVaccinationRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
  return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
 }
  public ModifiedType.Selector<TRoot, EffectuatePlannedVaccinationRequestType.Selector<TRoot, TParent>> created() {
  return ((this.created == null)?this.created = new ModifiedType.Selector<>(this._root, this, "created"):this.created);
 }
  public ModifiedType.Selector<TRoot, EffectuatePlannedVaccinationRequestType.Selector<TRoot, TParent>> reported() {
  return ((this.reported == null)?this.reported = new ModifiedType.Selector<>(this._root, this, "reported"):this.reported);
 }
  public EffectuatePlannedVaccinationType.Selector<TRoot, EffectuatePlannedVaccinationRequestType.Selector<TRoot, TParent>> effectuatePlannedVaccination() {
  return ((this.effectuatePlannedVaccination == null)?this.effectuatePlannedVaccination = new EffectuatePlannedVaccinationType.Selector<>(this._root, this, "effectuatePlannedVaccination"):this.effectuatePlannedVaccination);
 }
  }
 }
