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
* <p>Java class for DeleteVaccinationRequestType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="DeleteVaccinationRequestType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
*         <element name="Modified" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType"/>
*         <element name="Reported" type="{http://vaccinationsregister.dk/schemas/2013/12/01}ModifiedType" minOccurs="0"/>
*         <element name="DeleteVaccination" type="{http://vaccinationsregister.dk/schemas/2013/12/01}DeleteVaccinationType"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteVaccinationRequestType", propOrder = {
 "personCivilRegistrationIdentifier",
 "modified",
 "reported",
 "deleteVaccination"
})
public class DeleteVaccinationRequestType {
  @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
 protected String personCivilRegistrationIdentifier;
 @XmlElement(name = "Modified", required = true)
 protected ModifiedType modified;
 @XmlElement(name = "Reported")
 protected ModifiedType reported;
 @XmlElement(name = "DeleteVaccination", required = true)
 protected DeleteVaccinationType deleteVaccination;
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
 * Gets the value of the modified property.
 * 
 * @return
 *     possible object is
 *     {@link ModifiedType }
 *     
 */
 public ModifiedType getModified() {
 return modified;
 }
  /**
 * Sets the value of the modified property.
 * 
 * @param value
 *     allowed object is
 *     {@link ModifiedType }
 *     
 */
 public void setModified(ModifiedType value) {
 this.modified = value;
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
 * Gets the value of the deleteVaccination property.
 * 
 * @return
 *     possible object is
 *     {@link DeleteVaccinationType }
 *     
 */
 public DeleteVaccinationType getDeleteVaccination() {
 return deleteVaccination;
 }
  /**
 * Sets the value of the deleteVaccination property.
 * 
 * @param value
 *     allowed object is
 *     {@link DeleteVaccinationType }
 *     
 */
 public void setDeleteVaccination(DeleteVaccinationType value) {
 this.deleteVaccination = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final DeleteVaccinationRequestType.Builder<_B> _other) {
 _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 _other.modified = ((this.modified == null)?null:this.modified.newCopyBuilder(_other));
 _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other));
 _other.deleteVaccination = ((this.deleteVaccination == null)?null:this.deleteVaccination.newCopyBuilder(_other));
 }
  public<_B >DeleteVaccinationRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new DeleteVaccinationRequestType.Builder<_B>(_parentBuilder, this, true);
 }
  public DeleteVaccinationRequestType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static DeleteVaccinationRequestType.Builder<Void> builder() {
 return new DeleteVaccinationRequestType.Builder<>(null, null, false);
 }
  public static<_B >DeleteVaccinationRequestType.Builder<_B> copyOf(final DeleteVaccinationRequestType _other) {
 final DeleteVaccinationRequestType.Builder<_B> _newBuilder = new DeleteVaccinationRequestType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final DeleteVaccinationRequestType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
  _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 }
 final PropertyTree modifiedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modified"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedPropertyTree!= null):((modifiedPropertyTree == null)||(!modifiedPropertyTree.isLeaf())))) {
  _other.modified = ((this.modified == null)?null:this.modified.newCopyBuilder(_other, modifiedPropertyTree, _propertyTreeUse));
 }
 final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
  _other.reported = ((this.reported == null)?null:this.reported.newCopyBuilder(_other, reportedPropertyTree, _propertyTreeUse));
 }
 final PropertyTree deleteVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deleteVaccination"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deleteVaccinationPropertyTree!= null):((deleteVaccinationPropertyTree == null)||(!deleteVaccinationPropertyTree.isLeaf())))) {
  _other.deleteVaccination = ((this.deleteVaccination == null)?null:this.deleteVaccination.newCopyBuilder(_other, deleteVaccinationPropertyTree, _propertyTreeUse));
 }
 }
  public<_B >DeleteVaccinationRequestType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new DeleteVaccinationRequestType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public DeleteVaccinationRequestType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >DeleteVaccinationRequestType.Builder<_B> copyOf(final DeleteVaccinationRequestType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final DeleteVaccinationRequestType.Builder<_B> _newBuilder = new DeleteVaccinationRequestType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static DeleteVaccinationRequestType.Builder<Void> copyExcept(final DeleteVaccinationRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static DeleteVaccinationRequestType.Builder<Void> copyOnly(final DeleteVaccinationRequestType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final DeleteVaccinationRequestType _storedValue;
 private String personCivilRegistrationIdentifier;
 private ModifiedType.Builder<DeleteVaccinationRequestType.Builder<_B>> modified;
 private ModifiedType.Builder<DeleteVaccinationRequestType.Builder<_B>> reported;
 private DeleteVaccinationType.Builder<DeleteVaccinationRequestType.Builder<_B>> deleteVaccination;
  public Builder(final _B _parentBuilder, final DeleteVaccinationRequestType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     this.modified = ((_other.modified == null)?null:_other.modified.newCopyBuilder(this));
     this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this));
     this.deleteVaccination = ((_other.deleteVaccination == null)?null:_other.deleteVaccination.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final DeleteVaccinationRequestType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     }
     final PropertyTree modifiedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("modified"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(modifiedPropertyTree!= null):((modifiedPropertyTree == null)||(!modifiedPropertyTree.isLeaf())))) {
        this.modified = ((_other.modified == null)?null:_other.modified.newCopyBuilder(this, modifiedPropertyTree, _propertyTreeUse));
     }
     final PropertyTree reportedPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("reported"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(reportedPropertyTree!= null):((reportedPropertyTree == null)||(!reportedPropertyTree.isLeaf())))) {
        this.reported = ((_other.reported == null)?null:_other.reported.newCopyBuilder(this, reportedPropertyTree, _propertyTreeUse));
     }
     final PropertyTree deleteVaccinationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("deleteVaccination"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(deleteVaccinationPropertyTree!= null):((deleteVaccinationPropertyTree == null)||(!deleteVaccinationPropertyTree.isLeaf())))) {
        this.deleteVaccination = ((_other.deleteVaccination == null)?null:_other.deleteVaccination.newCopyBuilder(this, deleteVaccinationPropertyTree, _propertyTreeUse));
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
  protected<_P extends DeleteVaccinationRequestType >_P init(final _P _product) {
  _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
  _product.modified = ((this.modified == null)?null:this.modified.build());
  _product.reported = ((this.reported == null)?null:this.reported.build());
  _product.deleteVaccination = ((this.deleteVaccination == null)?null:this.deleteVaccination.build());
  return _product;
 }
  /**
 * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
 * will be replaced)
 * 
 * @param personCivilRegistrationIdentifier
 *     New value of the "personCivilRegistrationIdentifier" property.
 */
 public DeleteVaccinationRequestType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
  this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "modified" (any previous value will be replaced)
 * 
 * @param modified
 *     New value of the "modified" property.
 */
 public DeleteVaccinationRequestType.Builder<_B> withModified(final ModifiedType modified) {
  this.modified = ((modified == null)?null:new ModifiedType.Builder<>(this, modified, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "modified" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "modified" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.ModifiedType.Builder#end()} to
 *     return to the current builder.
 */
 public ModifiedType.Builder<? extends DeleteVaccinationRequestType.Builder<_B>> withModified() {
  if (this.modified!= null) {
   return this.modified;
  }
  return this.modified = new ModifiedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "reported" (any previous value will be replaced)
 * 
 * @param reported
 *     New value of the "reported" property.
 */
 public DeleteVaccinationRequestType.Builder<_B> withReported(final ModifiedType reported) {
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
 public ModifiedType.Builder<? extends DeleteVaccinationRequestType.Builder<_B>> withReported() {
  if (this.reported!= null) {
   return this.reported;
  }
  return this.reported = new ModifiedType.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "deleteVaccination" (any previous value will be replaced)
 * 
 * @param deleteVaccination
 *     New value of the "deleteVaccination" property.
 */
 public DeleteVaccinationRequestType.Builder<_B> withDeleteVaccination(final DeleteVaccinationType deleteVaccination) {
  this.deleteVaccination = ((deleteVaccination == null)?null:new DeleteVaccinationType.Builder<>(this, deleteVaccination, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "deleteVaccination" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.DeleteVaccinationType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "deleteVaccination" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.DeleteVaccinationType.Builder#end()}
 *     to return to the current builder.
 */
 public DeleteVaccinationType.Builder<? extends DeleteVaccinationRequestType.Builder<_B>> withDeleteVaccination() {
  if (this.deleteVaccination!= null) {
   return this.deleteVaccination;
  }
  return this.deleteVaccination = new DeleteVaccinationType.Builder<>(this, null, false);
 }
  @Override
 public DeleteVaccinationRequestType build() {
  if (_storedValue == null) {
   return this.init(new DeleteVaccinationRequestType());
  } else {
   return ((DeleteVaccinationRequestType) _storedValue);
  }
 }
  public DeleteVaccinationRequestType.Builder<_B> copyOf(final DeleteVaccinationRequestType _other) {
  _other.copyTo(this);
  return this;
 }
  public DeleteVaccinationRequestType.Builder<_B> copyOf(final DeleteVaccinationRequestType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends DeleteVaccinationRequestType.Selector<DeleteVaccinationRequestType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static DeleteVaccinationRequestType.Select _root() {
  return new DeleteVaccinationRequestType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, DeleteVaccinationRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
 private ModifiedType.Selector<TRoot, DeleteVaccinationRequestType.Selector<TRoot, TParent>> modified = null;
 private ModifiedType.Selector<TRoot, DeleteVaccinationRequestType.Selector<TRoot, TParent>> reported = null;
 private DeleteVaccinationType.Selector<TRoot, DeleteVaccinationRequestType.Selector<TRoot, TParent>> deleteVaccination = null;
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
  if (this.modified!= null) {
   products.put("modified", this.modified.init());
  }
  if (this.reported!= null) {
   products.put("reported", this.reported.init());
  }
  if (this.deleteVaccination!= null) {
   products.put("deleteVaccination", this.deleteVaccination.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, DeleteVaccinationRequestType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
  return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
 }
  public ModifiedType.Selector<TRoot, DeleteVaccinationRequestType.Selector<TRoot, TParent>> modified() {
  return ((this.modified == null)?this.modified = new ModifiedType.Selector<>(this._root, this, "modified"):this.modified);
 }
  public ModifiedType.Selector<TRoot, DeleteVaccinationRequestType.Selector<TRoot, TParent>> reported() {
  return ((this.reported == null)?this.reported = new ModifiedType.Selector<>(this._root, this, "reported"):this.reported);
 }
  public DeleteVaccinationType.Selector<TRoot, DeleteVaccinationRequestType.Selector<TRoot, TParent>> deleteVaccination() {
  return ((this.deleteVaccination == null)?this.deleteVaccination = new DeleteVaccinationType.Selector<>(this._root, this, "deleteVaccination"):this.deleteVaccination);
 }
  }
 }
