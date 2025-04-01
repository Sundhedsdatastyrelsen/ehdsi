package oasis.names.tc.saml._2_0.assertion;
import java.util.HashMap;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for anonymous complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType>
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}NameID"/>
*         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}SubjectConfirmation" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "nameID",
 "subjectConfirmation"
})
@XmlRootElement(name = "Subject")
public class Subject {
  @XmlElement(name = "NameID", required = true)
 protected NameID nameID;
 @XmlElement(name = "SubjectConfirmation")
 protected SubjectConfirmation subjectConfirmation;
  /**
 * Gets the value of the nameID property.
 * 
 * @return
 *     possible object is
 *     {@link NameID }
 *     
 */
 public NameID getNameID() {
 return nameID;
 }
  /**
 * Sets the value of the nameID property.
 * 
 * @param value
 *     allowed object is
 *     {@link NameID }
 *     
 */
 public void setNameID(NameID value) {
 this.nameID = value;
 }
  /**
 * Gets the value of the subjectConfirmation property.
 * 
 * @return
 *     possible object is
 *     {@link SubjectConfirmation }
 *     
 */
 public SubjectConfirmation getSubjectConfirmation() {
 return subjectConfirmation;
 }
  /**
 * Sets the value of the subjectConfirmation property.
 * 
 * @param value
 *     allowed object is
 *     {@link SubjectConfirmation }
 *     
 */
 public void setSubjectConfirmation(SubjectConfirmation value) {
 this.subjectConfirmation = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final Subject.Builder<_B> _other) {
 _other.nameID = ((this.nameID == null)?null:this.nameID.newCopyBuilder(_other));
 _other.subjectConfirmation = ((this.subjectConfirmation == null)?null:this.subjectConfirmation.newCopyBuilder(_other));
 }
  public<_B >Subject.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new Subject.Builder<_B>(_parentBuilder, this, true);
 }
  public Subject.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static Subject.Builder<Void> builder() {
 return new Subject.Builder<>(null, null, false);
 }
  public static<_B >Subject.Builder<_B> copyOf(final Subject _other) {
 final Subject.Builder<_B> _newBuilder = new Subject.Builder<>(null, null, false);
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
 public<_B >void copyTo(final Subject.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree nameIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("nameID"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(nameIDPropertyTree!= null):((nameIDPropertyTree == null)||(!nameIDPropertyTree.isLeaf())))) {
  _other.nameID = ((this.nameID == null)?null:this.nameID.newCopyBuilder(_other, nameIDPropertyTree, _propertyTreeUse));
 }
 final PropertyTree subjectConfirmationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("subjectConfirmation"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(subjectConfirmationPropertyTree!= null):((subjectConfirmationPropertyTree == null)||(!subjectConfirmationPropertyTree.isLeaf())))) {
  _other.subjectConfirmation = ((this.subjectConfirmation == null)?null:this.subjectConfirmation.newCopyBuilder(_other, subjectConfirmationPropertyTree, _propertyTreeUse));
 }
 }
  public<_B >Subject.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new Subject.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public Subject.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >Subject.Builder<_B> copyOf(final Subject _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final Subject.Builder<_B> _newBuilder = new Subject.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static Subject.Builder<Void> copyExcept(final Subject _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static Subject.Builder<Void> copyOnly(final Subject _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final Subject _storedValue;
 private NameID.Builder<Subject.Builder<_B>> nameID;
 private SubjectConfirmation.Builder<Subject.Builder<_B>> subjectConfirmation;
  public Builder(final _B _parentBuilder, final Subject _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.nameID = ((_other.nameID == null)?null:_other.nameID.newCopyBuilder(this));
     this.subjectConfirmation = ((_other.subjectConfirmation == null)?null:_other.subjectConfirmation.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final Subject _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree nameIDPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("nameID"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(nameIDPropertyTree!= null):((nameIDPropertyTree == null)||(!nameIDPropertyTree.isLeaf())))) {
        this.nameID = ((_other.nameID == null)?null:_other.nameID.newCopyBuilder(this, nameIDPropertyTree, _propertyTreeUse));
     }
     final PropertyTree subjectConfirmationPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("subjectConfirmation"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(subjectConfirmationPropertyTree!= null):((subjectConfirmationPropertyTree == null)||(!subjectConfirmationPropertyTree.isLeaf())))) {
        this.subjectConfirmation = ((_other.subjectConfirmation == null)?null:_other.subjectConfirmation.newCopyBuilder(this, subjectConfirmationPropertyTree, _propertyTreeUse));
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
  protected<_P extends Subject >_P init(final _P _product) {
  _product.nameID = ((this.nameID == null)?null:this.nameID.build());
  _product.subjectConfirmation = ((this.subjectConfirmation == null)?null:this.subjectConfirmation.build());
  return _product;
 }
  /**
 * Sets the new value of "nameID" (any previous value will be replaced)
 * 
 * @param nameID
 *     New value of the "nameID" property.
 */
 public Subject.Builder<_B> withNameID(final NameID nameID) {
  this.nameID = ((nameID == null)?null:new NameID.Builder<>(this, nameID, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the "nameID"
 * property.
 * Use {@link oasis.names.tc.saml._2_0.assertion.NameID.Builder#end()} to return to
 * the current builder.
 * 
 * @return
 *     A new builder to build the value of the "nameID" property.
 *     Use {@link oasis.names.tc.saml._2_0.assertion.NameID.Builder#end()} to return to
 *     the current builder.
 */
 public NameID.Builder<? extends Subject.Builder<_B>> withNameID() {
  if (this.nameID!= null) {
   return this.nameID;
  }
  return this.nameID = new NameID.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "subjectConfirmation" (any previous value will be
 * replaced)
 * 
 * @param subjectConfirmation
 *     New value of the "subjectConfirmation" property.
 */
 public Subject.Builder<_B> withSubjectConfirmation(final SubjectConfirmation subjectConfirmation) {
  this.subjectConfirmation = ((subjectConfirmation == null)?null:new SubjectConfirmation.Builder<>(this, subjectConfirmation, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "subjectConfirmation" property.
 * Use {@link oasis.names.tc.saml._2_0.assertion.SubjectConfirmation.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "subjectConfirmation" property.
 *     Use {@link oasis.names.tc.saml._2_0.assertion.SubjectConfirmation.Builder#end()}
 *     to return to the current builder.
 */
 public SubjectConfirmation.Builder<? extends Subject.Builder<_B>> withSubjectConfirmation() {
  if (this.subjectConfirmation!= null) {
   return this.subjectConfirmation;
  }
  return this.subjectConfirmation = new SubjectConfirmation.Builder<>(this, null, false);
 }
  @Override
 public Subject build() {
  if (_storedValue == null) {
   return this.init(new Subject());
  } else {
   return ((Subject) _storedValue);
  }
 }
  public Subject.Builder<_B> copyOf(final Subject _other) {
  _other.copyTo(this);
  return this;
 }
  public Subject.Builder<_B> copyOf(final Subject.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends Subject.Selector<Subject.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static Subject.Select _root() {
  return new Subject.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private NameID.Selector<TRoot, Subject.Selector<TRoot, TParent>> nameID = null;
 private SubjectConfirmation.Selector<TRoot, Subject.Selector<TRoot, TParent>> subjectConfirmation = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.nameID!= null) {
   products.put("nameID", this.nameID.init());
  }
  if (this.subjectConfirmation!= null) {
   products.put("subjectConfirmation", this.subjectConfirmation.init());
  }
  return products;
 }
  public NameID.Selector<TRoot, Subject.Selector<TRoot, TParent>> nameID() {
  return ((this.nameID == null)?this.nameID = new NameID.Selector<>(this._root, this, "nameID"):this.nameID);
 }
  public SubjectConfirmation.Selector<TRoot, Subject.Selector<TRoot, TParent>> subjectConfirmation() {
  return ((this.subjectConfirmation == null)?this.subjectConfirmation = new SubjectConfirmation.Selector<>(this._root, this, "subjectConfirmation"):this.subjectConfirmation);
 }
  }
 }
