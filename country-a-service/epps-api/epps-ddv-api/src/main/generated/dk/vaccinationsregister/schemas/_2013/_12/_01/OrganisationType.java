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
* <p>Java class for OrganisationType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="OrganisationType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="Name" type="{http://vaccinationsregister.dk/schemas/2013/12/01}OrganisationNameType"/>
*         <element name="AddressLine" type="{http://vaccinationsregister.dk/schemas/2013/12/01}AddressLineType" maxOccurs="5" minOccurs="0"/>
*         <element name="TelephoneNumber" type="{http://vaccinationsregister.dk/schemas/2013/12/01}TelephoneNumberType" minOccurs="0"/>
*         <element name="EmailAddress" type="{http://vaccinationsregister.dk/schemas/2013/12/01}EmailAddressType" minOccurs="0"/>
*         <element name="Type" type="{http://vaccinationsregister.dk/schemas/2013/12/01}OrganisationTypeType"/>
*         <element name="Identifier" type="{http://vaccinationsregister.dk/schemas/2013/12/01}OrganisationIdentifierType"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganisationType", propOrder = {
 "name",
 "addressLine",
 "telephoneNumber",
 "emailAddress",
 "type",
 "identifier"
})
public class OrganisationType {
  @XmlElement(name = "Name", required = true)
 protected String name;
 @XmlElement(name = "AddressLine")
 protected List<String> addressLine;
 @XmlElement(name = "TelephoneNumber")
 protected String telephoneNumber;
 @XmlElement(name = "EmailAddress")
 protected String emailAddress;
 @XmlElement(name = "Type", required = true)
 protected String type;
 @XmlElement(name = "Identifier", required = true)
 protected OrganisationIdentifierType identifier;
  /**
 * Gets the value of the name property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getName() {
 return name;
 }
  /**
 * Sets the value of the name property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setName(String value) {
 this.name = value;
 }
  /**
 * Gets the value of the addressLine property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the addressLine property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getAddressLine().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link String }
 * 
 * 
 * @return
 *     The value of the addressLine property.
 */
 public List<String> getAddressLine() {
 if (addressLine == null) {
  addressLine = new ArrayList<>();
 }
 return this.addressLine;
 }
  /**
 * Gets the value of the telephoneNumber property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getTelephoneNumber() {
 return telephoneNumber;
 }
  /**
 * Sets the value of the telephoneNumber property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setTelephoneNumber(String value) {
 this.telephoneNumber = value;
 }
  /**
 * Gets the value of the emailAddress property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getEmailAddress() {
 return emailAddress;
 }
  /**
 * Sets the value of the emailAddress property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setEmailAddress(String value) {
 this.emailAddress = value;
 }
  /**
 * Gets the value of the type property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getType() {
 return type;
 }
  /**
 * Sets the value of the type property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setType(String value) {
 this.type = value;
 }
  /**
 * Gets the value of the identifier property.
 * 
 * @return
 *     possible object is
 *     {@link OrganisationIdentifierType }
 *     
 */
 public OrganisationIdentifierType getIdentifier() {
 return identifier;
 }
  /**
 * Sets the value of the identifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link OrganisationIdentifierType }
 *     
 */
 public void setIdentifier(OrganisationIdentifierType value) {
 this.identifier = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final OrganisationType.Builder<_B> _other) {
 _other.name = this.name;
 if (this.addressLine == null) {
  _other.addressLine = null;
 } else {
  _other.addressLine = new ArrayList<>();
  for (String _item: this.addressLine) {
   _other.addressLine.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
  }
 }
 _other.telephoneNumber = this.telephoneNumber;
 _other.emailAddress = this.emailAddress;
 _other.type = this.type;
 _other.identifier = ((this.identifier == null)?null:this.identifier.newCopyBuilder(_other));
 }
  public<_B >OrganisationType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new OrganisationType.Builder<_B>(_parentBuilder, this, true);
 }
  public OrganisationType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static OrganisationType.Builder<Void> builder() {
 return new OrganisationType.Builder<>(null, null, false);
 }
  public static<_B >OrganisationType.Builder<_B> copyOf(final OrganisationType _other) {
 final OrganisationType.Builder<_B> _newBuilder = new OrganisationType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final OrganisationType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
  _other.name = this.name;
 }
 final PropertyTree addressLinePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("addressLine"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(addressLinePropertyTree!= null):((addressLinePropertyTree == null)||(!addressLinePropertyTree.isLeaf())))) {
  if (this.addressLine == null) {
   _other.addressLine = null;
  } else {
   _other.addressLine = new ArrayList<>();
   for (String _item: this.addressLine) {
     _other.addressLine.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
   }
  }
 }
 final PropertyTree telephoneNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("telephoneNumber"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(telephoneNumberPropertyTree!= null):((telephoneNumberPropertyTree == null)||(!telephoneNumberPropertyTree.isLeaf())))) {
  _other.telephoneNumber = this.telephoneNumber;
 }
 final PropertyTree emailAddressPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("emailAddress"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(emailAddressPropertyTree!= null):((emailAddressPropertyTree == null)||(!emailAddressPropertyTree.isLeaf())))) {
  _other.emailAddress = this.emailAddress;
 }
 final PropertyTree typePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("type"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(typePropertyTree!= null):((typePropertyTree == null)||(!typePropertyTree.isLeaf())))) {
  _other.type = this.type;
 }
 final PropertyTree identifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("identifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(identifierPropertyTree!= null):((identifierPropertyTree == null)||(!identifierPropertyTree.isLeaf())))) {
  _other.identifier = ((this.identifier == null)?null:this.identifier.newCopyBuilder(_other, identifierPropertyTree, _propertyTreeUse));
 }
 }
  public<_B >OrganisationType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new OrganisationType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public OrganisationType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >OrganisationType.Builder<_B> copyOf(final OrganisationType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final OrganisationType.Builder<_B> _newBuilder = new OrganisationType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static OrganisationType.Builder<Void> copyExcept(final OrganisationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static OrganisationType.Builder<Void> copyOnly(final OrganisationType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final OrganisationType _storedValue;
 private String name;
 private List<Buildable> addressLine;
 private String telephoneNumber;
 private String emailAddress;
 private String type;
 private OrganisationIdentifierType.Builder<OrganisationType.Builder<_B>> identifier;
  public Builder(final _B _parentBuilder, final OrganisationType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.name = _other.name;
     if (_other.addressLine == null) {
        this.addressLine = null;
     } else {
        this.addressLine = new ArrayList<>();
        for (String _item: _other.addressLine) {
             this.addressLine.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
        }
     }
     this.telephoneNumber = _other.telephoneNumber;
     this.emailAddress = _other.emailAddress;
     this.type = _other.type;
     this.identifier = ((_other.identifier == null)?null:_other.identifier.newCopyBuilder(this));
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final OrganisationType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree namePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("name"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(namePropertyTree!= null):((namePropertyTree == null)||(!namePropertyTree.isLeaf())))) {
        this.name = _other.name;
     }
     final PropertyTree addressLinePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("addressLine"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(addressLinePropertyTree!= null):((addressLinePropertyTree == null)||(!addressLinePropertyTree.isLeaf())))) {
        if (_other.addressLine == null) {
             this.addressLine = null;
        } else {
             this.addressLine = new ArrayList<>();
             for (String _item: _other.addressLine) {
                     this.addressLine.add(((_item == null)?null:new Buildable.PrimitiveBuildable(_item)));
             }
        }
     }
     final PropertyTree telephoneNumberPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("telephoneNumber"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(telephoneNumberPropertyTree!= null):((telephoneNumberPropertyTree == null)||(!telephoneNumberPropertyTree.isLeaf())))) {
        this.telephoneNumber = _other.telephoneNumber;
     }
     final PropertyTree emailAddressPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("emailAddress"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(emailAddressPropertyTree!= null):((emailAddressPropertyTree == null)||(!emailAddressPropertyTree.isLeaf())))) {
        this.emailAddress = _other.emailAddress;
     }
     final PropertyTree typePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("type"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(typePropertyTree!= null):((typePropertyTree == null)||(!typePropertyTree.isLeaf())))) {
        this.type = _other.type;
     }
     final PropertyTree identifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("identifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(identifierPropertyTree!= null):((identifierPropertyTree == null)||(!identifierPropertyTree.isLeaf())))) {
        this.identifier = ((_other.identifier == null)?null:_other.identifier.newCopyBuilder(this, identifierPropertyTree, _propertyTreeUse));
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
  protected<_P extends OrganisationType >_P init(final _P _product) {
  _product.name = this.name;
  if (this.addressLine!= null) {
   final List<String> addressLine = new ArrayList<>(this.addressLine.size());
   for (Buildable _item: this.addressLine) {
     addressLine.add(((String) _item.build()));
   }
   _product.addressLine = addressLine;
  }
  _product.telephoneNumber = this.telephoneNumber;
  _product.emailAddress = this.emailAddress;
  _product.type = this.type;
  _product.identifier = ((this.identifier == null)?null:this.identifier.build());
  return _product;
 }
  /**
 * Sets the new value of "name" (any previous value will be replaced)
 * 
 * @param name
 *     New value of the "name" property.
 */
 public OrganisationType.Builder<_B> withName(final String name) {
  this.name = name;
  return this;
 }
  /**
 * Adds the given items to the value of "addressLine"
 * 
 * @param addressLine
 *     Items to add to the value of the "addressLine" property
 */
 public OrganisationType.Builder<_B> addAddressLine(final Iterable<? extends String> addressLine) {
  if (addressLine!= null) {
   if (this.addressLine == null) {
     this.addressLine = new ArrayList<>();
   }
   for (String _item: addressLine) {
     this.addressLine.add(new Buildable.PrimitiveBuildable(_item));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "addressLine" (any previous value will be replaced)
 * 
 * @param addressLine
 *     New value of the "addressLine" property.
 */
 public OrganisationType.Builder<_B> withAddressLine(final Iterable<? extends String> addressLine) {
  if (this.addressLine!= null) {
   this.addressLine.clear();
  }
  return addAddressLine(addressLine);
 }
  /**
 * Adds the given items to the value of "addressLine"
 * 
 * @param addressLine
 *     Items to add to the value of the "addressLine" property
 */
 public OrganisationType.Builder<_B> addAddressLine(String... addressLine) {
  addAddressLine(Arrays.asList(addressLine));
  return this;
 }
  /**
 * Sets the new value of "addressLine" (any previous value will be replaced)
 * 
 * @param addressLine
 *     New value of the "addressLine" property.
 */
 public OrganisationType.Builder<_B> withAddressLine(String... addressLine) {
  withAddressLine(Arrays.asList(addressLine));
  return this;
 }
  /**
 * Sets the new value of "telephoneNumber" (any previous value will be replaced)
 * 
 * @param telephoneNumber
 *     New value of the "telephoneNumber" property.
 */
 public OrganisationType.Builder<_B> withTelephoneNumber(final String telephoneNumber) {
  this.telephoneNumber = telephoneNumber;
  return this;
 }
  /**
 * Sets the new value of "emailAddress" (any previous value will be replaced)
 * 
 * @param emailAddress
 *     New value of the "emailAddress" property.
 */
 public OrganisationType.Builder<_B> withEmailAddress(final String emailAddress) {
  this.emailAddress = emailAddress;
  return this;
 }
  /**
 * Sets the new value of "type" (any previous value will be replaced)
 * 
 * @param type
 *     New value of the "type" property.
 */
 public OrganisationType.Builder<_B> withType(final String type) {
  this.type = type;
  return this;
 }
  /**
 * Sets the new value of "identifier" (any previous value will be replaced)
 * 
 * @param identifier
 *     New value of the "identifier" property.
 */
 public OrganisationType.Builder<_B> withIdentifier(final OrganisationIdentifierType identifier) {
  this.identifier = ((identifier == null)?null:new OrganisationIdentifierType.Builder<>(this, identifier, false));
  return this;
 }
  /**
 * Returns the existing builder or a new builder to build the value of the
 * "identifier" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.OrganisationIdentifierType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     A new builder to build the value of the "identifier" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.OrganisationIdentifierType.Builder#end()}
 *     to return to the current builder.
 */
 public OrganisationIdentifierType.Builder<? extends OrganisationType.Builder<_B>> withIdentifier() {
  if (this.identifier!= null) {
   return this.identifier;
  }
  return this.identifier = new OrganisationIdentifierType.Builder<>(this, null, false);
 }
  @Override
 public OrganisationType build() {
  if (_storedValue == null) {
   return this.init(new OrganisationType());
  } else {
   return ((OrganisationType) _storedValue);
  }
 }
  public OrganisationType.Builder<_B> copyOf(final OrganisationType _other) {
  _other.copyTo(this);
  return this;
 }
  public OrganisationType.Builder<_B> copyOf(final OrganisationType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends OrganisationType.Selector<OrganisationType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static OrganisationType.Select _root() {
  return new OrganisationType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> name = null;
 private com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> addressLine = null;
 private com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> telephoneNumber = null;
 private com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> emailAddress = null;
 private com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> type = null;
 private OrganisationIdentifierType.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> identifier = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.name!= null) {
   products.put("name", this.name.init());
  }
  if (this.addressLine!= null) {
   products.put("addressLine", this.addressLine.init());
  }
  if (this.telephoneNumber!= null) {
   products.put("telephoneNumber", this.telephoneNumber.init());
  }
  if (this.emailAddress!= null) {
   products.put("emailAddress", this.emailAddress.init());
  }
  if (this.type!= null) {
   products.put("type", this.type.init());
  }
  if (this.identifier!= null) {
   products.put("identifier", this.identifier.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> name() {
  return ((this.name == null)?this.name = new com.kscs.util.jaxb.Selector<>(this._root, this, "name"):this.name);
 }
  public com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> addressLine() {
  return ((this.addressLine == null)?this.addressLine = new com.kscs.util.jaxb.Selector<>(this._root, this, "addressLine"):this.addressLine);
 }
  public com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> telephoneNumber() {
  return ((this.telephoneNumber == null)?this.telephoneNumber = new com.kscs.util.jaxb.Selector<>(this._root, this, "telephoneNumber"):this.telephoneNumber);
 }
  public com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> emailAddress() {
  return ((this.emailAddress == null)?this.emailAddress = new com.kscs.util.jaxb.Selector<>(this._root, this, "emailAddress"):this.emailAddress);
 }
  public com.kscs.util.jaxb.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> type() {
  return ((this.type == null)?this.type = new com.kscs.util.jaxb.Selector<>(this._root, this, "type"):this.type);
 }
  public OrganisationIdentifierType.Selector<TRoot, OrganisationType.Selector<TRoot, TParent>> identifier() {
  return ((this.identifier == null)?this.identifier = new OrganisationIdentifierType.Selector<>(this._root, this, "identifier"):this.identifier);
 }
  }
 }
