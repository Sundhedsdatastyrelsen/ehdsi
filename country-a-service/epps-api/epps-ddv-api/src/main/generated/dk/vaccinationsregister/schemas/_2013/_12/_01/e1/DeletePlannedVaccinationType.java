package dk.vaccinationsregister.schemas._2013._12._01.e1;
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
* <p>Java class for DeletePlannedVaccinationType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="DeletePlannedVaccinationType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PlannedVaccinationIdentifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PlannedVaccinationIdentifierType"/>
*         <element name="Status" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}PlannedVaccinationStatusType"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeletePlannedVaccinationType", propOrder = {
 "plannedVaccinationIdentifier",
 "status"
})
public class DeletePlannedVaccinationType {
  @XmlElement(name = "PlannedVaccinationIdentifier")
 protected long plannedVaccinationIdentifier;
 @XmlElement(name = "Status", required = true)
 protected String status;
  /**
 * Gets the value of the plannedVaccinationIdentifier property.
 * 
 */
 public long getPlannedVaccinationIdentifier() {
 return plannedVaccinationIdentifier;
 }
  /**
 * Sets the value of the plannedVaccinationIdentifier property.
 * 
 */
 public void setPlannedVaccinationIdentifier(long value) {
 this.plannedVaccinationIdentifier = value;
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
 public<_B >void copyTo(final DeletePlannedVaccinationType.Builder<_B> _other) {
 _other.plannedVaccinationIdentifier = this.plannedVaccinationIdentifier;
 _other.status = this.status;
 }
  public<_B >DeletePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new DeletePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true);
 }
  public DeletePlannedVaccinationType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static DeletePlannedVaccinationType.Builder<Void> builder() {
 return new DeletePlannedVaccinationType.Builder<>(null, null, false);
 }
  public static<_B >DeletePlannedVaccinationType.Builder<_B> copyOf(final DeletePlannedVaccinationType _other) {
 final DeletePlannedVaccinationType.Builder<_B> _newBuilder = new DeletePlannedVaccinationType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final DeletePlannedVaccinationType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree plannedVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationIdentifierPropertyTree!= null):((plannedVaccinationIdentifierPropertyTree == null)||(!plannedVaccinationIdentifierPropertyTree.isLeaf())))) {
  _other.plannedVaccinationIdentifier = this.plannedVaccinationIdentifier;
 }
 final PropertyTree statusPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("status"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(statusPropertyTree!= null):((statusPropertyTree == null)||(!statusPropertyTree.isLeaf())))) {
  _other.status = this.status;
 }
 }
  public<_B >DeletePlannedVaccinationType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new DeletePlannedVaccinationType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public DeletePlannedVaccinationType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >DeletePlannedVaccinationType.Builder<_B> copyOf(final DeletePlannedVaccinationType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final DeletePlannedVaccinationType.Builder<_B> _newBuilder = new DeletePlannedVaccinationType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static DeletePlannedVaccinationType.Builder<Void> copyExcept(final DeletePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static DeletePlannedVaccinationType.Builder<Void> copyOnly(final DeletePlannedVaccinationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final DeletePlannedVaccinationType _storedValue;
 private long plannedVaccinationIdentifier;
 private String status;
  public Builder(final _B _parentBuilder, final DeletePlannedVaccinationType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.plannedVaccinationIdentifier = _other.plannedVaccinationIdentifier;
     this.status = _other.status;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final DeletePlannedVaccinationType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree plannedVaccinationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("plannedVaccinationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(plannedVaccinationIdentifierPropertyTree!= null):((plannedVaccinationIdentifierPropertyTree == null)||(!plannedVaccinationIdentifierPropertyTree.isLeaf())))) {
        this.plannedVaccinationIdentifier = _other.plannedVaccinationIdentifier;
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
  protected<_P extends DeletePlannedVaccinationType >_P init(final _P _product) {
  _product.plannedVaccinationIdentifier = this.plannedVaccinationIdentifier;
  _product.status = this.status;
  return _product;
 }
  /**
 * Sets the new value of "plannedVaccinationIdentifier" (any previous value will be
 * replaced)
 * 
 * @param plannedVaccinationIdentifier
 *     New value of the "plannedVaccinationIdentifier" property.
 */
 public DeletePlannedVaccinationType.Builder<_B> withPlannedVaccinationIdentifier(final long plannedVaccinationIdentifier) {
  this.plannedVaccinationIdentifier = plannedVaccinationIdentifier;
  return this;
 }
  /**
 * Sets the new value of "status" (any previous value will be replaced)
 * 
 * @param status
 *     New value of the "status" property.
 */
 public DeletePlannedVaccinationType.Builder<_B> withStatus(final String status) {
  this.status = status;
  return this;
 }
  @Override
 public DeletePlannedVaccinationType build() {
  if (_storedValue == null) {
   return this.init(new DeletePlannedVaccinationType());
  } else {
   return ((DeletePlannedVaccinationType) _storedValue);
  }
 }
  public DeletePlannedVaccinationType.Builder<_B> copyOf(final DeletePlannedVaccinationType _other) {
  _other.copyTo(this);
  return this;
 }
  public DeletePlannedVaccinationType.Builder<_B> copyOf(final DeletePlannedVaccinationType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends DeletePlannedVaccinationType.Selector<DeletePlannedVaccinationType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static DeletePlannedVaccinationType.Select _root() {
  return new DeletePlannedVaccinationType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, DeletePlannedVaccinationType.Selector<TRoot, TParent>> status = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.status!= null) {
   products.put("status", this.status.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, DeletePlannedVaccinationType.Selector<TRoot, TParent>> status() {
  return ((this.status == null)?this.status = new com.kscs.util.jaxb.Selector<>(this._root, this, "status"):this.status);
 }
  }
 }
