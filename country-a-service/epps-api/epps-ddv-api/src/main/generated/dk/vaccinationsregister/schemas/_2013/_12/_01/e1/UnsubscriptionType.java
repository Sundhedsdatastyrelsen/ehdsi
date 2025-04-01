package dk.vaccinationsregister.schemas._2013._12._01.e1;
import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import dk.vaccinationsregister.schemas._2013._12._01.CreatedType;
import dk.vaccinationsregister.schemas._2013._12._01.ReportedType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for UnsubscriptionType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="UnsubscriptionType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="Created" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CreatedType"/>
*         <element name="Reported" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ReportedType" minOccurs="0"/>
*         <element name="VaccinationPlanIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanIdentifierType"/>
*         <element name="VaccinationPlanName" type="{http://vaccinationsregister.dk/schemas/2013/12/01}VaccinationPlanNameType"/>
*         <element name="Status" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}PlannedVaccinationStatusType" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnsubscriptionType", propOrder = {
 "created",
 "reported",
 "vaccinationPlanIdentifier",
 "vaccinationPlanName",
 "status"
})
public class UnsubscriptionType {
  @XmlElement(name = "Created", required = true)
 protected CreatedType created;
 @XmlElement(name = "Reported")
 protected ReportedType reported;
 @XmlElement(name = "VaccinationPlanIdentifier")
 protected long vaccinationPlanIdentifier;
 @XmlElement(name = "VaccinationPlanName", required = true)
 protected String vaccinationPlanName;
 @XmlElement(name = "Status")
 protected String status;
  /**
 * Gets the value of the created property.
 * 
 * @return
 *     possible object is
 *     {@link CreatedType }
 *     
 */
 public CreatedType getCreated() {
 return created;
 }
  /**
 * Sets the value of the created property.
 * 
 * @param value
 *     allowed object is
 *     {@link CreatedType }
 *     
 */
 public void setCreated(CreatedType value) {
 this.created = value;
 }
  /**
 * Gets the value of the reported property.
 * 
 * @return
 *     possible object is
 *     {@link ReportedType }
 *     
 */
 public ReportedType getReported() {
 return reported;
 }
  /**
 * Sets the value of the reported property.
 * 
 * @param value
 *     allowed object is
 *     {@link ReportedType }
 *     
 */
 public void setReported(ReportedType value) {
 this.reported = value;
 }
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
 * Gets the value of the status property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getStatus() {
 return status;
 }
  /**
 * Sets the value of the status property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setStatus(String value) {
 this.status = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final UnsubscriptionType.Builder<_B> _other) {
 _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other));
 _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other));
 _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 _other.vaccinationPlanName = this.vaccinationPlanName;
 _other.status = this.status;
 }
  public<_B >UnsubscriptionType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new UnsubscriptionType.Builder<_B>(_parentBuilder, this, true);
 }
  public UnsubscriptionType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static UnsubscriptionType.Builder<Void> builder() {
 return new UnsubscriptionType.Builder<>(null, null, false);
 }
  public static<_B >UnsubscriptionType.Builder<_B> copyOf(final UnsubscriptionType _other) {
 final UnsubscriptionType.Builder<_B> _newBuilder = new UnsubscriptionType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final UnsubscriptionType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
  _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other, createdPropertyTree, _propertyTreeUse));
 }
 final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
  _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other, reportedPropertyTree, _propertyTreeUse));
 }
 final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
  _other.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
 }
 final PropertyTree vaccinationPlanNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanName"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanNamePropertyTree!= null):((vaccinationPlanNamePropertyTree == null)||(!vaccinationPlanNamePropertyTree.isLeaf())))) {
  _other.vaccinationPlanName = this.vaccinationPlanName;
 }
 final PropertyTree statusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("status"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(statusPropertyTree!= null):((statusPropertyTree == null)||(!statusPropertyTree.isLeaf())))) {
  _other.status = this.status;
 }
 }
  public<_B >UnsubscriptionType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new UnsubscriptionType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public UnsubscriptionType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >UnsubscriptionType.Builder<_B> copyOf(final UnsubscriptionType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final UnsubscriptionType.Builder<_B> _newBuilder = new UnsubscriptionType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static UnsubscriptionType.Builder<Void> copyExcept(final UnsubscriptionType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static UnsubscriptionType.Builder<Void> copyOnly(final UnsubscriptionType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final UnsubscriptionType _storedValue;
 private CreatedType.Builder<UnsubscriptionType.Builder<_B>> created;
 private ReportedType.Builder<UnsubscriptionType.Builder<_B>> reported;
 private long vaccinationPlanIdentifier;
 private String vaccinationPlanName;
 private String status;
  public Builder(final _B _parentBuilder, final UnsubscriptionType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this));
     this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this));
     this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     this.vaccinationPlanName = _other.vaccinationPlanName;
     this.status = _other.status;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final UnsubscriptionType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
        this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this, createdPropertyTree, _propertyTreeUse));
     }
     final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
        this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this, reportedPropertyTree, _propertyTreeUse));
     }
     final PropertyTree vaccinationPlanIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanIdentifierPropertyTree!= null):((vaccinationPlanIdentifierPropertyTree == null)||(!vaccinationPlanIdentifierPropertyTree.isLeaf())))) {
        this.vaccinationPlanIdentifier = _other.vaccinationPlanIdentifier;
     }
     final PropertyTree vaccinationPlanNamePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("vaccinationPlanName"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(vaccinationPlanNamePropertyTree!= null):((vaccinationPlanNamePropertyTree == null)||(!vaccinationPlanNamePropertyTree.isLeaf())))) {
        this.vaccinationPlanName = _other.vaccinationPlanName;
     }
     final PropertyTree statusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("status"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(statusPropertyTree!= null):((statusPropertyTree == null)||(!statusPropertyTree.isLeaf())))) {
        this.status = _other.status;
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
  protected<_P extends UnsubscriptionType >_P init(final _P _product) {
  _product.created = ((this.created == null)?null:this.created.build());
  _product.reported = ((this.reported == null)?null:this.reported.build());
  _product.vaccinationPlanIdentifier = this.vaccinationPlanIdentifier;
  _product.vaccinationPlanName = this.vaccinationPlanName;
  _product.status = this.status;
  return _product;
 }
  /**
 * Sets the new value of "created" (any previous value will be replaced)
 * 
 * @param created
 *     New value of the "created" property.
 */
 public UnsubscriptionType.Builder<_B> withCreated(final CreatedType created) {
  this.created = ((created == null)?null:new CreatedType.Builder<>(this, created, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "created" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.CreatedType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "created" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.CreatedType.Builder#end()} to
 *     return to the current builder.
 */
 public CreatedType.Builder<? extends UnsubscriptionType.Builder<_B>> withCreated() {
  if (this.created!= null) {
   return this.created;
  }
  return this.created = new CreatedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "reported" (any previous value will be replaced)
 * 
 * @param reported
 *     New value of the "reported" property.
 */
 public UnsubscriptionType.Builder<_B> withReported(final ReportedType reported) {
  this.reported = ((reported == null)?null:new ReportedType.Builder<>(this, reported, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "reported" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.ReportedType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "reported" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.ReportedType.Builder#end()} to
 *     return to the current builder.
 */
 public ReportedType.Builder<? extends UnsubscriptionType.Builder<_B>> withReported() {
  if (this.reported!= null) {
   return this.reported;
  }
  return this.reported = new ReportedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "vaccinationPlanIdentifier" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanIdentifier
 *     New value of the "vaccinationPlanIdentifier" property.
 */
 public UnsubscriptionType.Builder<_B> withVaccinationPlanIdentifier(final long vaccinationPlanIdentifier) {
  this.vaccinationPlanIdentifier = vaccinationPlanIdentifier;
  return this;
 }
  /**
 * Sets the new value of "vaccinationPlanName" (any previous value will be
 * replaced)
 * 
 * @param vaccinationPlanName
 *     New value of the "vaccinationPlanName" property.
 */
 public UnsubscriptionType.Builder<_B> withVaccinationPlanName(final String vaccinationPlanName) {
  this.vaccinationPlanName = vaccinationPlanName;
  return this;
 }
  /**
 * Sets the new value of "status" (any previous value will be replaced)
 * 
 * @param status
 *     New value of the "status" property.
 */
 public UnsubscriptionType.Builder<_B> withStatus(final String status) {
  this.status = status;
  return this;
 }
  @Override
 public UnsubscriptionType build() {
  if (_storedValue == null) {
   return this.init(new UnsubscriptionType());
  } else {
   return ((UnsubscriptionType) _storedValue);
  }
 }
  public UnsubscriptionType.Builder<_B> copyOf(final UnsubscriptionType _other) {
  _other.copyTo(this);
  return this;
 }
  public UnsubscriptionType.Builder<_B> copyOf(final UnsubscriptionType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends UnsubscriptionType.Selector<UnsubscriptionType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static UnsubscriptionType.Select _root() {
  return new UnsubscriptionType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private CreatedType.Selector<TRoot, UnsubscriptionType.Selector<TRoot, TParent>> created = null;
 private ReportedType.Selector<TRoot, UnsubscriptionType.Selector<TRoot, TParent>> reported = null;
 private com.kscs.util.jaxb.Selector<TRoot, UnsubscriptionType.Selector<TRoot, TParent>> vaccinationPlanName = null;
 private com.kscs.util.jaxb.Selector<TRoot, UnsubscriptionType.Selector<TRoot, TParent>> status = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.created!= null) {
   products.put("created", this.created.init());
  }
  if (this.reported!= null) {
   products.put("reported", this.reported.init());
  }
  if (this.vaccinationPlanName!= null) {
   products.put("vaccinationPlanName", this.vaccinationPlanName.init());
  }
  if (this.status!= null) {
   products.put("status", this.status.init());
  }
  return products;
 }
  public CreatedType.Selector<TRoot, UnsubscriptionType.Selector<TRoot, TParent>> created() {
  return ((this.created == null)?this.created = new CreatedType.Selector<>(this._root, this, "created"):this.created);
 }
  public ReportedType.Selector<TRoot, UnsubscriptionType.Selector<TRoot, TParent>> reported() {
  return ((this.reported == null)?this.reported = new ReportedType.Selector<>(this._root, this, "reported"):this.reported);
 }
  public com.kscs.util.jaxb.Selector<TRoot, UnsubscriptionType.Selector<TRoot, TParent>> vaccinationPlanName() {
  return ((this.vaccinationPlanName == null)?this.vaccinationPlanName = new com.kscs.util.jaxb.Selector<>(this._root, this, "vaccinationPlanName"):this.vaccinationPlanName);
 }
  public com.kscs.util.jaxb.Selector<TRoot, UnsubscriptionType.Selector<TRoot, TParent>> status() {
  return ((this.status == null)?this.status = new com.kscs.util.jaxb.Selector<>(this._root, this, "status"):this.status);
 }
  }
 }
