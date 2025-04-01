package oasis.names.tc.saml._2_0.assertion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.XMLGregorianCalendar;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3._2000._09.xmldsig_.Signature;
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
*         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Issuer"/>
*         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Subject"/>
*         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Conditions"/>
*         <element ref="{urn:oasis:names:tc:SAML:2.0:assertion}AttributeStatement" maxOccurs="3"/>
*         <element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
*       </sequence>
*       <attribute name="IssueInstant" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
*       <attribute name="Version" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
*       <attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "issuer",
 "subject",
 "conditions",
 "attributeStatement",
 "signature"
})
@XmlRootElement(name = "Assertion")
public class Assertion {
  @XmlElement(name = "Issuer", required = true)
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 @XmlSchemaType(name = "NCName")
 protected String issuer;
 @XmlElement(name = "Subject", required = true)
 protected Subject subject;
 @XmlElement(name = "Conditions", required = true)
 protected Conditions conditions;
 @XmlElement(name = "AttributeStatement", required = true)
 protected List<AttributeStatement> attributeStatement;
 @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
 protected Signature signature;
 @XmlAttribute(name = "IssueInstant", required = true)
 @XmlSchemaType(name = "dateTime")
 protected XMLGregorianCalendar issueInstant;
 @XmlAttribute(name = "Version", required = true)
 protected BigDecimal version;
 @XmlAttribute(name = "id", required = true)
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 @XmlSchemaType(name = "NCName")
 protected String id;
  /**
 * Gets the value of the issuer property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getIssuer() {
 return issuer;
 }
  /**
 * Sets the value of the issuer property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setIssuer(String value) {
 this.issuer = value;
 }
  /**
 * Gets the value of the subject property.
 * 
 * @return
 *     possible object is
 *     {@link Subject }
 *     
 */
 public Subject getSubject() {
 return subject;
 }
  /**
 * Sets the value of the subject property.
 * 
 * @param value
 *     allowed object is
 *     {@link Subject }
 *     
 */
 public void setSubject(Subject value) {
 this.subject = value;
 }
  /**
 * Gets the value of the conditions property.
 * 
 * @return
 *     possible object is
 *     {@link Conditions }
 *     
 */
 public Conditions getConditions() {
 return conditions;
 }
  /**
 * Sets the value of the conditions property.
 * 
 * @param value
 *     allowed object is
 *     {@link Conditions }
 *     
 */
 public void setConditions(Conditions value) {
 this.conditions = value;
 }
  /**
 * Gets the value of the attributeStatement property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the attributeStatement property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getAttributeStatement().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link AttributeStatement }
 * 
 * 
 * @return
 *     The value of the attributeStatement property.
 */
 public List<AttributeStatement> getAttributeStatement() {
 if (attributeStatement == null) {
  attributeStatement = new ArrayList<>();
 }
 return this.attributeStatement;
 }
  /**
 * Gets the value of the signature property.
 * 
 * @return
 *     possible object is
 *     {@link Signature }
 *     
 */
 public Signature getSignature() {
 return signature;
 }
  /**
 * Sets the value of the signature property.
 * 
 * @param value
 *     allowed object is
 *     {@link Signature }
 *     
 */
 public void setSignature(Signature value) {
 this.signature = value;
 }
  /**
 * Gets the value of the issueInstant property.
 * 
 * @return
 *     possible object is
 *     {@link XMLGregorianCalendar }
 *     
 */
 public XMLGregorianCalendar getIssueInstant() {
 return issueInstant;
 }
  /**
 * Sets the value of the issueInstant property.
 * 
 * @param value
 *     allowed object is
 *     {@link XMLGregorianCalendar }
 *     
 */
 public void setIssueInstant(XMLGregorianCalendar value) {
 this.issueInstant = value;
 }
  /**
 * Gets the value of the version property.
 * 
 * @return
 *     possible object is
 *     {@link BigDecimal }
 *     
 */
 public BigDecimal getVersion() {
 return version;
 }
  /**
 * Sets the value of the version property.
 * 
 * @param value
 *     allowed object is
 *     {@link BigDecimal }
 *     
 */
 public void setVersion(BigDecimal value) {
 this.version = value;
 }
  /**
 * Gets the value of the id property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getId() {
 return id;
 }
  /**
 * Sets the value of the id property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setId(String value) {
 this.id = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final Assertion.Builder<_B> _other) {
 _other.issuer = this.issuer;
 _other.subject = ((this.subject == null)?null:this.subject.newCopyBuilder(_other));
 _other.conditions = ((this.conditions == null)?null:this.conditions.newCopyBuilder(_other));
 if (this.attributeStatement == null) {
  _other.attributeStatement = null;
 } else {
  _other.attributeStatement = new ArrayList<>();
  for (AttributeStatement _item: this.attributeStatement) {
   _other.attributeStatement.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 _other.signature = ((this.signature == null)?null:this.signature.newCopyBuilder(_other));
 _other.issueInstant = ((this.issueInstant == null)?null:((XMLGregorianCalendar) this.issueInstant.clone()));
 _other.version = this.version;
 _other.id = this.id;
 }
  public<_B >Assertion.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new Assertion.Builder<_B>(_parentBuilder, this, true);
 }
  public Assertion.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static Assertion.Builder<Void> builder() {
 return new Assertion.Builder<>(null, null, false);
 }
  public static<_B >Assertion.Builder<_B> copyOf(final Assertion _other) {
 final Assertion.Builder<_B> _newBuilder = new Assertion.Builder<>(null, null, false);
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
 public<_B >void copyTo(final Assertion.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree issuerPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("issuer"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(issuerPropertyTree!= null):((issuerPropertyTree == null)||(!issuerPropertyTree.isLeaf())))) {
  _other.issuer = this.issuer;
 }
 final PropertyTree subjectPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("subject"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(subjectPropertyTree!= null):((subjectPropertyTree == null)||(!subjectPropertyTree.isLeaf())))) {
  _other.subject = ((this.subject == null)?null:this.subject.newCopyBuilder(_other, subjectPropertyTree, _propertyTreeUse));
 }
 final PropertyTree conditionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("conditions"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(conditionsPropertyTree!= null):((conditionsPropertyTree == null)||(!conditionsPropertyTree.isLeaf())))) {
  _other.conditions = ((this.conditions == null)?null:this.conditions.newCopyBuilder(_other, conditionsPropertyTree, _propertyTreeUse));
 }
 final PropertyTree attributeStatementPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("attributeStatement"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(attributeStatementPropertyTree!= null):((attributeStatementPropertyTree == null)||(!attributeStatementPropertyTree.isLeaf())))) {
  if (this.attributeStatement == null) {
   _other.attributeStatement = null;
  } else {
   _other.attributeStatement = new ArrayList<>();
   for (AttributeStatement _item: this.attributeStatement) {
     _other.attributeStatement.add(((_item == null)?null:_item.newCopyBuilder(_other, attributeStatementPropertyTree, _propertyTreeUse)));
   }
  }
 }
 final PropertyTree signaturePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signature"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signaturePropertyTree!= null):((signaturePropertyTree == null)||(!signaturePropertyTree.isLeaf())))) {
  _other.signature = ((this.signature == null)?null:this.signature.newCopyBuilder(_other, signaturePropertyTree, _propertyTreeUse));
 }
 final PropertyTree issueInstantPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("issueInstant"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(issueInstantPropertyTree!= null):((issueInstantPropertyTree == null)||(!issueInstantPropertyTree.isLeaf())))) {
  _other.issueInstant = ((this.issueInstant == null)?null:((XMLGregorianCalendar) this.issueInstant.clone()));
 }
 final PropertyTree versionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("version"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(versionPropertyTree!= null):((versionPropertyTree == null)||(!versionPropertyTree.isLeaf())))) {
  _other.version = this.version;
 }
 final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
  _other.id = this.id;
 }
 }
  public<_B >Assertion.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new Assertion.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public Assertion.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >Assertion.Builder<_B> copyOf(final Assertion _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final Assertion.Builder<_B> _newBuilder = new Assertion.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static Assertion.Builder<Void> copyExcept(final Assertion _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static Assertion.Builder<Void> copyOnly(final Assertion _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final Assertion _storedValue;
 private String issuer;
 private Subject.Builder<Assertion.Builder<_B>> subject;
 private Conditions.Builder<Assertion.Builder<_B>> conditions;
 private List<AttributeStatement.Builder<Assertion.Builder<_B>>> attributeStatement;
 private Signature.Builder<Assertion.Builder<_B>> signature;
 private XMLGregorianCalendar issueInstant;
 private BigDecimal version;
 private String id;
  public Builder(final _B _parentBuilder, final Assertion _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.issuer = _other.issuer;
     this.subject = ((_other.subject == null)?null:_other.subject.newCopyBuilder(this));
     this.conditions = ((_other.conditions == null)?null:_other.conditions.newCopyBuilder(this));
     if (_other.attributeStatement == null) {
        this.attributeStatement = null;
     } else {
        this.attributeStatement = new ArrayList<>();
        for (AttributeStatement _item: _other.attributeStatement) {
             this.attributeStatement.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
     this.signature = ((_other.signature == null)?null:_other.signature.newCopyBuilder(this));
     this.issueInstant = ((_other.issueInstant == null)?null:((XMLGregorianCalendar) _other.issueInstant.clone()));
     this.version = _other.version;
     this.id = _other.id;
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final Assertion _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree issuerPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("issuer"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(issuerPropertyTree!= null):((issuerPropertyTree == null)||(!issuerPropertyTree.isLeaf())))) {
        this.issuer = _other.issuer;
     }
     final PropertyTree subjectPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("subject"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(subjectPropertyTree!= null):((subjectPropertyTree == null)||(!subjectPropertyTree.isLeaf())))) {
        this.subject = ((_other.subject == null)?null:_other.subject.newCopyBuilder(this, subjectPropertyTree, _propertyTreeUse));
     }
     final PropertyTree conditionsPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("conditions"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(conditionsPropertyTree!= null):((conditionsPropertyTree == null)||(!conditionsPropertyTree.isLeaf())))) {
        this.conditions = ((_other.conditions == null)?null:_other.conditions.newCopyBuilder(this, conditionsPropertyTree, _propertyTreeUse));
     }
     final PropertyTree attributeStatementPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("attributeStatement"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(attributeStatementPropertyTree!= null):((attributeStatementPropertyTree == null)||(!attributeStatementPropertyTree.isLeaf())))) {
        if (_other.attributeStatement == null) {
             this.attributeStatement = null;
        } else {
             this.attributeStatement = new ArrayList<>();
             for (AttributeStatement _item: _other.attributeStatement) {
                     this.attributeStatement.add(((_item == null)?null:_item.newCopyBuilder(this, attributeStatementPropertyTree, _propertyTreeUse)));
             }
        }
     }
     final PropertyTree signaturePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("signature"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(signaturePropertyTree!= null):((signaturePropertyTree == null)||(!signaturePropertyTree.isLeaf())))) {
        this.signature = ((_other.signature == null)?null:_other.signature.newCopyBuilder(this, signaturePropertyTree, _propertyTreeUse));
     }
     final PropertyTree issueInstantPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("issueInstant"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(issueInstantPropertyTree!= null):((issueInstantPropertyTree == null)||(!issueInstantPropertyTree.isLeaf())))) {
        this.issueInstant = ((_other.issueInstant == null)?null:((XMLGregorianCalendar) _other.issueInstant.clone()));
     }
     final PropertyTree versionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("version"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(versionPropertyTree!= null):((versionPropertyTree == null)||(!versionPropertyTree.isLeaf())))) {
        this.version = _other.version;
     }
     final PropertyTree idPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("id"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(idPropertyTree!= null):((idPropertyTree == null)||(!idPropertyTree.isLeaf())))) {
        this.id = _other.id;
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
  protected<_P extends Assertion >_P init(final _P _product) {
  _product.issuer = this.issuer;
  _product.subject = ((this.subject == null)?null:this.subject.build());
  _product.conditions = ((this.conditions == null)?null:this.conditions.build());
  if (this.attributeStatement!= null) {
   final List<AttributeStatement> attributeStatement = new ArrayList<>(this.attributeStatement.size());
   for (AttributeStatement.Builder<Assertion.Builder<_B>> _item: this.attributeStatement) {
     attributeStatement.add(_item.build());
   }
   _product.attributeStatement = attributeStatement;
  }
  _product.signature = ((this.signature == null)?null:this.signature.build());
  _product.issueInstant = this.issueInstant;
  _product.version = this.version;
  _product.id = this.id;
  return _product;
 }
  /**
 * Sets the new value of "issuer" (any previous value will be replaced)
 * 
 * @param issuer
 *     New value of the "issuer" property.
 */
 public Assertion.Builder<_B> withIssuer(final String issuer) {
  this.issuer = issuer;
  return this;
 }
  /**
 * Sets the new value of "subject" (any previous value will be replaced)
 * 
 * @param subject
 *     New value of the "subject" property.
 */
 public Assertion.Builder<_B> withSubject(final Subject subject) {
  this.subject = ((subject == null)?null:new Subject.Builder<>(this, subject, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "subject" property.
 * Use {@link oasis.names.tc.saml._2_0.assertion.Subject.Builder#end()} to return
 * to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "subject" property.
 *     Use {@link oasis.names.tc.saml._2_0.assertion.Subject.Builder#end()} to return
 *     to the current builder.
 */
 public Subject.Builder<? extends Assertion.Builder<_B>> withSubject() {
  if (this.subject!= null) {
   return this.subject;
  }
  return this.subject = new Subject.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "conditions" (any previous value will be replaced)
 * 
 * @param conditions
 *     New value of the "conditions" property.
 */
 public Assertion.Builder<_B> withConditions(final Conditions conditions) {
  this.conditions = ((conditions == null)?null:new Conditions.Builder<>(this, conditions, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "conditions" property.
 * Use {@link oasis.names.tc.saml._2_0.assertion.Conditions.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "conditions" property.
 *     Use {@link oasis.names.tc.saml._2_0.assertion.Conditions.Builder#end()} to
 *     return to the current builder.
 */
 public Conditions.Builder<? extends Assertion.Builder<_B>> withConditions() {
  if (this.conditions!= null) {
   return this.conditions;
  }
  return this.conditions = new Conditions.Builder<>(this, null, false);
 }
  /**
 * Adds the given items to the value of "attributeStatement"
 * 
 * @param attributeStatement
 *     Items to add to the value of the "attributeStatement" property
 */
 public Assertion.Builder<_B> addAttributeStatement(final Iterable<? extends AttributeStatement> attributeStatement) {
  if (attributeStatement!= null) {
   if (this.attributeStatement == null) {
     this.attributeStatement = new ArrayList<>();
   }
   for (AttributeStatement _item: attributeStatement) {
     this.attributeStatement.add(new AttributeStatement.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "attributeStatement" (any previous value will be replaced)
 * 
 * @param attributeStatement
 *     New value of the "attributeStatement" property.
 */
 public Assertion.Builder<_B> withAttributeStatement(final Iterable<? extends AttributeStatement> attributeStatement) {
  if (this.attributeStatement!= null) {
   this.attributeStatement.clear();
  }
  return addAttributeStatement(attributeStatement);
 }
  /**
 * Adds the given items to the value of "attributeStatement"
 * 
 * @param attributeStatement
 *     Items to add to the value of the "attributeStatement" property
 */
 public Assertion.Builder<_B> addAttributeStatement(AttributeStatement... attributeStatement) {
  addAttributeStatement(Arrays.asList(attributeStatement));
  return this;
 }
  /**
 * Sets the new value of "attributeStatement" (any previous value will be replaced)
 * 
 * @param attributeStatement
 *     New value of the "attributeStatement" property.
 */
 public Assertion.Builder<_B> withAttributeStatement(AttributeStatement... attributeStatement) {
  withAttributeStatement(Arrays.asList(attributeStatement));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "AttributeStatement"
 * property.
 * Use {@link oasis.names.tc.saml._2_0.assertion.AttributeStatement.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "AttributeStatement" property.
 *     Use {@link oasis.names.tc.saml._2_0.assertion.AttributeStatement.Builder#end()}
 *     to return to the current builder.
 */
 public AttributeStatement.Builder<? extends Assertion.Builder<_B>> addAttributeStatement() {
  if (this.attributeStatement == null) {
   this.attributeStatement = new ArrayList<>();
  }
  final AttributeStatement.Builder<Assertion.Builder<_B>> attributeStatement_Builder = new AttributeStatement.Builder<>(this, null, false);
  this.attributeStatement.add(attributeStatement_Builder);
  return attributeStatement_Builder;
 }
  /**
 * Sets the new value of "signature" (any previous value will be replaced)
 * 
 * @param signature
 *     New value of the "signature" property.
 */
 public Assertion.Builder<_B> withSignature(final Signature signature) {
  this.signature = ((signature == null)?null:new Signature.Builder<>(this, signature, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "signature" property.
 * Use {@link org.w3._2000._09.xmldsig_.Signature.Builder#end()} to return to the
 * current builder.
 * 
 * @return
 *     A new builder to build the value of the "signature" property.
 *     Use {@link org.w3._2000._09.xmldsig_.Signature.Builder#end()} to return to the
 *     current builder.
 */
 public Signature.Builder<? extends Assertion.Builder<_B>> withSignature() {
  if (this.signature!= null) {
   return this.signature;
  }
  return this.signature = new Signature.Builder<>(this, null, false);
 }
  /**
 * Sets the new value of "issueInstant" (any previous value will be replaced)
 * 
 * @param issueInstant
 *     New value of the "issueInstant" property.
 */
 public Assertion.Builder<_B> withIssueInstant(final XMLGregorianCalendar issueInstant) {
  this.issueInstant = issueInstant;
  return this;
 }
  /**
 * Sets the new value of "version" (any previous value will be replaced)
 * 
 * @param version
 *     New value of the "version" property.
 */
 public Assertion.Builder<_B> withVersion(final BigDecimal version) {
  this.version = version;
  return this;
 }
  /**
 * Sets the new value of "id" (any previous value will be replaced)
 * 
 * @param id
 *     New value of the "id" property.
 */
 public Assertion.Builder<_B> withId(final String id) {
  this.id = id;
  return this;
 }
  @Override
 public Assertion build() {
  if (_storedValue == null) {
   return this.init(new Assertion());
  } else {
   return ((Assertion) _storedValue);
  }
 }
  public Assertion.Builder<_B> copyOf(final Assertion _other) {
  _other.copyTo(this);
  return this;
 }
  public Assertion.Builder<_B> copyOf(final Assertion.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends Assertion.Selector<Assertion.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static Assertion.Select _root() {
  return new Assertion.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, Assertion.Selector<TRoot, TParent>> issuer = null;
 private Subject.Selector<TRoot, Assertion.Selector<TRoot, TParent>> subject = null;
 private Conditions.Selector<TRoot, Assertion.Selector<TRoot, TParent>> conditions = null;
 private AttributeStatement.Selector<TRoot, Assertion.Selector<TRoot, TParent>> attributeStatement = null;
 private Signature.Selector<TRoot, Assertion.Selector<TRoot, TParent>> signature = null;
 private com.kscs.util.jaxb.Selector<TRoot, Assertion.Selector<TRoot, TParent>> issueInstant = null;
 private com.kscs.util.jaxb.Selector<TRoot, Assertion.Selector<TRoot, TParent>> version = null;
 private com.kscs.util.jaxb.Selector<TRoot, Assertion.Selector<TRoot, TParent>> id = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.issuer!= null) {
   products.put("issuer", this.issuer.init());
  }
  if (this.subject!= null) {
   products.put("subject", this.subject.init());
  }
  if (this.conditions!= null) {
   products.put("conditions", this.conditions.init());
  }
  if (this.attributeStatement!= null) {
   products.put("attributeStatement", this.attributeStatement.init());
  }
  if (this.signature!= null) {
   products.put("signature", this.signature.init());
  }
  if (this.issueInstant!= null) {
   products.put("issueInstant", this.issueInstant.init());
  }
  if (this.version!= null) {
   products.put("version", this.version.init());
  }
  if (this.id!= null) {
   products.put("id", this.id.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, Assertion.Selector<TRoot, TParent>> issuer() {
  return ((this.issuer == null)?this.issuer = new com.kscs.util.jaxb.Selector<>(this._root, this, "issuer"):this.issuer);
 }
  public Subject.Selector<TRoot, Assertion.Selector<TRoot, TParent>> subject() {
  return ((this.subject == null)?this.subject = new Subject.Selector<>(this._root, this, "subject"):this.subject);
 }
  public Conditions.Selector<TRoot, Assertion.Selector<TRoot, TParent>> conditions() {
  return ((this.conditions == null)?this.conditions = new Conditions.Selector<>(this._root, this, "conditions"):this.conditions);
 }
  public AttributeStatement.Selector<TRoot, Assertion.Selector<TRoot, TParent>> attributeStatement() {
  return ((this.attributeStatement == null)?this.attributeStatement = new AttributeStatement.Selector<>(this._root, this, "attributeStatement"):this.attributeStatement);
 }
  public Signature.Selector<TRoot, Assertion.Selector<TRoot, TParent>> signature() {
  return ((this.signature == null)?this.signature = new Signature.Selector<>(this._root, this, "signature"):this.signature);
 }
  public com.kscs.util.jaxb.Selector<TRoot, Assertion.Selector<TRoot, TParent>> issueInstant() {
  return ((this.issueInstant == null)?this.issueInstant = new com.kscs.util.jaxb.Selector<>(this._root, this, "issueInstant"):this.issueInstant);
 }
  public com.kscs.util.jaxb.Selector<TRoot, Assertion.Selector<TRoot, TParent>> version() {
  return ((this.version == null)?this.version = new com.kscs.util.jaxb.Selector<>(this._root, this, "version"):this.version);
 }
  public com.kscs.util.jaxb.Selector<TRoot, Assertion.Selector<TRoot, TParent>> id() {
  return ((this.id == null)?this.id = new com.kscs.util.jaxb.Selector<>(this._root, this, "id"):this.id);
 }
  }
 }
