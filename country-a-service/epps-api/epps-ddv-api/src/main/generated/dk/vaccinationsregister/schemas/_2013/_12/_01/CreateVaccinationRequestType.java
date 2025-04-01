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
* <p>Java class for CreateVaccinationRequestType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="CreateVaccinationRequestType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
*         <element name="Created" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType"/>
*         <element name="Reported" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType" minOccurs="0"/>
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
@XmlType(name = "CreateVaccinationRequestType", propOrder = {
 "personCivilRegistrationIdentifier",
 "created",
 "reported",
 "vaccinationCreate"
})
public class CreateVaccinationRequestType {
  @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
 protected String personCivilRegistrationIdentifier;
 @XmlElement(name = "Created", required = true)
 protected ModifiedType created;
 @XmlElement(name = "Reported")
 protected ModifiedType reported;
 @XmlElement(name = "VaccinationCreate", required = true)
 protected VaccinationCreateType vaccinationCreate;
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
 public<_B >void copyTo(final CreateVaccinationRequestType.Builder<_B> _other) {
 _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other));
 _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other));
 _other.vaccinationCreate = ((this.vaccinationCreate == null)?null:this.vaccinationCreate.newCopyBuilder(_other));
 }
  public<_B >CreateVaccinationRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new CreateVaccinationRequestType.Builder<_B>(_parentBuilder, this, true);
 }
  public CreateVaccinationRequestType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static CreateVaccinationRequestType.Builder<Void> builder() {
 return new CreateVaccinationRequestType.Builder<>(null, null, false);
 }
  public static<_B >CreateVaccinationRequestType.Builder<_B> copyOf(final CreateVaccinationRequestType _other) {
 final CreateVaccinationRequestType.Builder<_B> _newBuilder = new CreateVaccinationRequestType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final CreateVaccinationRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
 final PropertyTree vaccinationCreatePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationCreate"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationCreatePropertyTree!= null):((vaccinationCreatePropertyTree == null)||(!vaccinationCreatePropertyTree.isLeaf())))) {
  _other.vaccinationCreate = ((this.vaccinationCreate == null)?null:this.vaccinationCreate.newCopyBuilder(_other, vaccinationCreatePropertyTree, _propertyTreeUse));
 }
 }
  public<_B >CreateVaccinationRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new CreateVaccinationRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public CreateVaccinationRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >CreateVaccinationRequestType.Builder<_B> copyOf(final CreateVaccinationRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final CreateVaccinationRequestType.Builder<_B> _newBuilder = new CreateVaccinationRequestType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static CreateVaccinationRequestType.Builder<Void> copyExcept(final CreateVaccinationRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static CreateVaccinationRequestType.Builder<Void> copyOnly(final CreateVaccinationRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final CreateVaccinationRequestType _storedValue;
 private String personCivilRegistrationIdentifier;
 private ModifiedType.Builder<CreateVaccinationRequestType.Builder<_B>> created;
 private ModifiedType.Builder<CreateVaccinationRequestType.Builder<_B>> reported;
 private VaccinationCreateType.Builder<CreateVaccinationRequestType.Builder<_B>> vaccinationCreate;
  public Builder(final _B _parentBuilder, final CreateVaccinationRequestType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this));
     this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this));
     this.vaccinationCreate = ((_other.vaccinationCreate == null)?null:_other.vaccinationCreate.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final CreateVaccinationRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
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
  protected<_P extends CreateVaccinationRequestType >_P init(final _P _product) {
  _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
  _product.created = ((this.created == null)?null:this.created.build());
  _product.reported = ((this.reported == null)?null:this.reported.build());
  _product.vaccinationCreate = ((this.vaccinationCreate == null)?null:this.vaccinationCreate.build());
  return _product;
 }
  /**
 * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
 * will be replaced)
 * 
 * @param personCivilRegistrationIdentifier
 *     New value of the "personCivilRegistrationIdentifier" property.
 */
 public CreateVaccinationRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
  this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "created" (any previous value will be replaced)
 * 
 * @param created
 *     New value of the "created" property.
 */
 public CreateVaccinationRequestType.Builder<_B> withCreated(final ModifiedType created) {
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
 public ModifiedType.Builder<? extends CreateVaccinationRequestType.Builder<_B>> withCreated() {
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
 public CreateVaccinationRequestType.Builder<_B> withReported(final ModifiedType reported) {
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
 public ModifiedType.Builder<? extends CreateVaccinationRequestType.Builder<_B>> withReported() {
  if (this.reported!= null) {
   return this.reported;
  }
  return this.reported = new ModifiedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "vaccinationCreate" (any previous value will be replaced)
 * 
 * @param vaccinationCreate
 *     New value of the "vaccinationCreate" property.
 */
 public CreateVaccinationRequestType.Builder<_B> withVaccinationCreate(final VaccinationCreateType vaccinationCreate) {
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
 public VaccinationCreateType.Builder<? extends CreateVaccinationRequestType.Builder<_B>> withVaccinationCreate() {
  if (this.vaccinationCreate!= null) {
   return this.vaccinationCreate;
  }
  return this.vaccinationCreate = new VaccinationCreateType.Builder<>(this, null, false);
 }
  @Override
 public CreateVaccinationRequestType build() {
  if (_storedValue == null) {
   return this.init(new CreateVaccinationRequestType());
  } else {
   return ((CreateVaccinationRequestType) _storedValue);
  }
 }
  public CreateVaccinationRequestType.Builder<_B> copyOf(final CreateVaccinationRequestType _other) {
  _other.copyTo(this);
  return this;
 }
  public CreateVaccinationRequestType.Builder<_B> copyOf(final CreateVaccinationRequestType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends CreateVaccinationRequestType.Selector<CreateVaccinationRequestType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static CreateVaccinationRequestType.Select _root() {
  return new CreateVaccinationRequestType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, CreateVaccinationRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
 private ModifiedType.Selector<TRoot, CreateVaccinationRequestType.Selector<TRoot, TParent>> created = null;
 private ModifiedType.Selector<TRoot, CreateVaccinationRequestType.Selector<TRoot, TParent>> reported = null;
 private VaccinationCreateType.Selector<TRoot, CreateVaccinationRequestType.Selector<TRoot, TParent>> vaccinationCreate = null;
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
  if (this.vaccinationCreate!= null) {
   products.put("vaccinationCreate", this.vaccinationCreate.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, CreateVaccinationRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
  return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
 }
  public ModifiedType.Selector<TRoot, CreateVaccinationRequestType.Selector<TRoot, TParent>> created() {
  return ((this.created == null)?this.created = new ModifiedType.Selector<>(this._root, this, "created"):this.created);
 }
  public ModifiedType.Selector<TRoot, CreateVaccinationRequestType.Selector<TRoot, TParent>> reported() {
  return ((this.reported == null)?this.reported = new ModifiedType.Selector<>(this._root, this, "reported"):this.reported);
 }
  public VaccinationCreateType.Selector<TRoot, CreateVaccinationRequestType.Selector<TRoot, TParent>> vaccinationCreate() {
  return ((this.vaccinationCreate == null)?this.vaccinationCreate = new VaccinationCreateType.Selector<>(this._root, this, "vaccinationCreate"):this.vaccinationCreate);
 }
  }
 }
