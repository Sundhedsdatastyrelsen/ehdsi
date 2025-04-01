package dk.vaccinationsregister.schemas._2013._12._01.e1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kscs.util.jaxb.Buildable;
import com.kscs.util.jaxb.PropertyTree;
import com.kscs.util.jaxb.PropertyTreeUse;
import dk.vaccinationsregister.schemas._2013._12._01.CreatedType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
/**
* <p>Java class for GetNotificationUnsubscriptionResponseType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="GetNotificationUnsubscriptionResponseType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="PersonCivilRegistrationIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType"/>
*         <element name="Unsubscription" maxOccurs="unbounded" minOccurs="0">
*           <complexType>
*             <complexContent>
*               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*                 <sequence>
*                   <element name="Created" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CreatedType"/>
*                   <element name="RecipientPersonIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType" minOccurs="0"/>
*                 </sequence>
*               </restriction>
*             </complexContent>
*           </complexType>
*         </element>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetNotificationUnsubscriptionResponseType", propOrder = {
 "personCivilRegistrationIdentifier",
 "unsubscription"
})
public class GetNotificationUnsubscriptionResponseType {
  @XmlElement(name = "PersonCivilRegistrationIdentifier", required = true)
 protected String personCivilRegistrationIdentifier;
 @XmlElement(name = "Unsubscription")
 protected List<GetNotificationUnsubscriptionResponseType.Unsubscription> unsubscription;
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
 * Gets the value of the unsubscription property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the unsubscription property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getUnsubscription().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link GetNotificationUnsubscriptionResponseType.Unsubscription }
 * 
 * 
 * @return
 *     The value of the unsubscription property.
 */
 public List<GetNotificationUnsubscriptionResponseType.Unsubscription> getUnsubscription() {
 if (unsubscription == null) {
  unsubscription = new ArrayList<>();
 }
 return this.unsubscription;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final GetNotificationUnsubscriptionResponseType.Builder<_B> _other) {
 _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 if (this.unsubscription == null) {
  _other.unsubscription = null;
 } else {
  _other.unsubscription = new ArrayList<>();
  for (GetNotificationUnsubscriptionResponseType.Unsubscription _item: this.unsubscription) {
   _other.unsubscription.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >GetNotificationUnsubscriptionResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new GetNotificationUnsubscriptionResponseType.Builder<_B>(_parentBuilder, this, true);
 }
  public GetNotificationUnsubscriptionResponseType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static GetNotificationUnsubscriptionResponseType.Builder<Void> builder() {
 return new GetNotificationUnsubscriptionResponseType.Builder<>(null, null, false);
 }
  public static<_B >GetNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final GetNotificationUnsubscriptionResponseType _other) {
 final GetNotificationUnsubscriptionResponseType.Builder<_B> _newBuilder = new GetNotificationUnsubscriptionResponseType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final GetNotificationUnsubscriptionResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
  _other.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
 }
 final PropertyTree unsubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("unsubscription"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(unsubscriptionPropertyTree!= null):((unsubscriptionPropertyTree == null)||(!unsubscriptionPropertyTree.isLeaf())))) {
  if (this.unsubscription == null) {
   _other.unsubscription = null;
  } else {
   _other.unsubscription = new ArrayList<>();
   for (GetNotificationUnsubscriptionResponseType.Unsubscription _item: this.unsubscription) {
     _other.unsubscription.add(((_item == null)?null:_item.newCopyBuilder(_other, unsubscriptionPropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >GetNotificationUnsubscriptionResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new GetNotificationUnsubscriptionResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public GetNotificationUnsubscriptionResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >GetNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final GetNotificationUnsubscriptionResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final GetNotificationUnsubscriptionResponseType.Builder<_B> _newBuilder = new GetNotificationUnsubscriptionResponseType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static GetNotificationUnsubscriptionResponseType.Builder<Void> copyExcept(final GetNotificationUnsubscriptionResponseType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static GetNotificationUnsubscriptionResponseType.Builder<Void> copyOnly(final GetNotificationUnsubscriptionResponseType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final GetNotificationUnsubscriptionResponseType _storedValue;
 private String personCivilRegistrationIdentifier;
 private List<GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<GetNotificationUnsubscriptionResponseType.Builder<_B>>> unsubscription;
  public Builder(final _B _parentBuilder, final GetNotificationUnsubscriptionResponseType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     if (_other.unsubscription == null) {
        this.unsubscription = null;
     } else {
        this.unsubscription = new ArrayList<>();
        for (GetNotificationUnsubscriptionResponseType.Unsubscription _item: _other.unsubscription) {
             this.unsubscription.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final GetNotificationUnsubscriptionResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree personCivilRegistrationIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("personCivilRegistrationIdentifier"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(personCivilRegistrationIdentifierPropertyTree!= null):((personCivilRegistrationIdentifierPropertyTree == null)||(!personCivilRegistrationIdentifierPropertyTree.isLeaf())))) {
        this.personCivilRegistrationIdentifier = _other.personCivilRegistrationIdentifier;
     }
     final PropertyTree unsubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("unsubscription"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(unsubscriptionPropertyTree!= null):((unsubscriptionPropertyTree == null)||(!unsubscriptionPropertyTree.isLeaf())))) {
        if (_other.unsubscription == null) {
             this.unsubscription = null;
        } else {
             this.unsubscription = new ArrayList<>();
             for (GetNotificationUnsubscriptionResponseType.Unsubscription _item: _other.unsubscription) {
                     this.unsubscription.add(((_item == null)?null:_item.newCopyBuilder(this, unsubscriptionPropertyTree, _propertyTreeUse)));
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
  protected<_P extends GetNotificationUnsubscriptionResponseType >_P init(final _P _product) {
  _product.personCivilRegistrationIdentifier = this.personCivilRegistrationIdentifier;
  if (this.unsubscription!= null) {
   final List<GetNotificationUnsubscriptionResponseType.Unsubscription> unsubscription = new ArrayList<>(this.unsubscription.size());
   for (GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<GetNotificationUnsubscriptionResponseType.Builder<_B>> _item: this.unsubscription) {
     unsubscription.add(_item.build());
   }
   _product.unsubscription = unsubscription;
  }
  return _product;
 }
  /**
 * Sets the new value of "personCivilRegistrationIdentifier" (any previous value
 * will be replaced)
 * 
 * @param personCivilRegistrationIdentifier
 *     New value of the "personCivilRegistrationIdentifier" property.
 */
 public GetNotificationUnsubscriptionResponseType.Builder<_B> withPersonCivilRegistrationIdentifier(final String personCivilRegistrationIdentifier) {
  this.personCivilRegistrationIdentifier = personCivilRegistrationIdentifier;
  return this;
 }
  /**
 * Adds the given items to the value of "unsubscription"
 * 
 * @param unsubscription
 *     Items to add to the value of the "unsubscription" property
 */
 public GetNotificationUnsubscriptionResponseType.Builder<_B> addUnsubscription(final Iterable<? extends GetNotificationUnsubscriptionResponseType.Unsubscription> unsubscription) {
  if (unsubscription!= null) {
   if (this.unsubscription == null) {
     this.unsubscription = new ArrayList<>();
   }
   for (GetNotificationUnsubscriptionResponseType.Unsubscription _item: unsubscription) {
     this.unsubscription.add(new GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "unsubscription" (any previous value will be replaced)
 * 
 * @param unsubscription
 *     New value of the "unsubscription" property.
 */
 public GetNotificationUnsubscriptionResponseType.Builder<_B> withUnsubscription(final Iterable<? extends GetNotificationUnsubscriptionResponseType.Unsubscription> unsubscription) {
  if (this.unsubscription!= null) {
   this.unsubscription.clear();
  }
  return addUnsubscription(unsubscription);
 }
  /**
 * Adds the given items to the value of "unsubscription"
 * 
 * @param unsubscription
 *     Items to add to the value of the "unsubscription" property
 */
 public GetNotificationUnsubscriptionResponseType.Builder<_B> addUnsubscription(GetNotificationUnsubscriptionResponseType.Unsubscription... unsubscription) {
  addUnsubscription(Arrays.asList(unsubscription));
  return this;
 }
  /**
 * Sets the new value of "unsubscription" (any previous value will be replaced)
 * 
 * @param unsubscription
 *     New value of the "unsubscription" property.
 */
 public GetNotificationUnsubscriptionResponseType.Builder<_B> withUnsubscription(GetNotificationUnsubscriptionResponseType.Unsubscription... unsubscription) {
  withUnsubscription(Arrays.asList(unsubscription));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "Unsubscription"
 * property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.e1.GetNotificationUnsubscriptionResponseType.Unsubscription.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "Unsubscription" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.e1.GetNotificationUnsubscriptionResponseType.Unsubscription.Builder#end()}
 *     to return to the current builder.
 */
 public GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<? extends GetNotificationUnsubscriptionResponseType.Builder<_B>> addUnsubscription() {
  if (this.unsubscription == null) {
   this.unsubscription = new ArrayList<>();
  }
  final GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<GetNotificationUnsubscriptionResponseType.Builder<_B>> unsubscription_Builder = new GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<>(this, null, false);
  this.unsubscription.add(unsubscription_Builder);
  return unsubscription_Builder;
 }
  @Override
 public GetNotificationUnsubscriptionResponseType build() {
  if (_storedValue == null) {
   return this.init(new GetNotificationUnsubscriptionResponseType());
  } else {
   return ((GetNotificationUnsubscriptionResponseType) _storedValue);
  }
 }
  public GetNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final GetNotificationUnsubscriptionResponseType _other) {
  _other.copyTo(this);
  return this;
 }
  public GetNotificationUnsubscriptionResponseType.Builder<_B> copyOf(final GetNotificationUnsubscriptionResponseType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends GetNotificationUnsubscriptionResponseType.Selector<GetNotificationUnsubscriptionResponseType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static GetNotificationUnsubscriptionResponseType.Select _root() {
  return new GetNotificationUnsubscriptionResponseType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, GetNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier = null;
 private GetNotificationUnsubscriptionResponseType.Unsubscription.Selector<TRoot, GetNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> unsubscription = null;
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
  if (this.unsubscription!= null) {
   products.put("unsubscription", this.unsubscription.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, GetNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> personCivilRegistrationIdentifier() {
  return ((this.personCivilRegistrationIdentifier == null)?this.personCivilRegistrationIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "personCivilRegistrationIdentifier"):this.personCivilRegistrationIdentifier);
 }
  public GetNotificationUnsubscriptionResponseType.Unsubscription.Selector<TRoot, GetNotificationUnsubscriptionResponseType.Selector<TRoot, TParent>> unsubscription() {
  return ((this.unsubscription == null)?this.unsubscription = new GetNotificationUnsubscriptionResponseType.Unsubscription.Selector<>(this._root, this, "unsubscription"):this.unsubscription);
 }
  }
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
 *         <element name="Created" type="{http://vaccinationsregister.dk/schemas/2013/12/01}CreatedType"/>
 *         <element name="RecipientPersonIdentifier" type="{http://rep.oio.dk/cpr.dk/xml/schemas/core/2005/03/18/}PersonCivilRegistrationIdentifierType" minOccurs="0"/>
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
 "created",
 "recipientPersonIdentifier"
 })
 public static class Unsubscription {
  @XmlElement(name = "Created", required = true)
 protected CreatedType created;
 @XmlElement(name = "RecipientPersonIdentifier")
 protected String recipientPersonIdentifier;
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
 * Gets the value of the recipientPersonIdentifier property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getRecipientPersonIdentifier() {
  return recipientPersonIdentifier;
 }
  /**
 * Sets the value of the recipientPersonIdentifier property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setRecipientPersonIdentifier(String value) {
  this.recipientPersonIdentifier = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> _other) {
  _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other));
  _other.recipientPersonIdentifier = this.recipientPersonIdentifier;
 }
  public<_B >GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
  return new GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B>(_parentBuilder, this, true);
 }
  public GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<Void> newCopyBuilder() {
  return newCopyBuilder(null);
 }
  public static GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<Void> builder() {
  return new GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<>(null, null, false);
 }
  public static<_B >GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> copyOf(final GetNotificationUnsubscriptionResponseType.Unsubscription _other) {
  final GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> _newBuilder = new GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<>(null, null, false);
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
 public<_B >void copyTo(final GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
  if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
   _other.created = ((this.created == null)?null:this.created.newCopyBuilder(_other, createdPropertyTree, _propertyTreeUse));
  }
  final PropertyTree recipientPersonIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("recipientPersonIdentifier"));
  if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(recipientPersonIdentifierPropertyTree!= null):((recipientPersonIdentifierPropertyTree == null)||(!recipientPersonIdentifierPropertyTree.isLeaf())))) {
   _other.recipientPersonIdentifier = this.recipientPersonIdentifier;
  }
 }
  public<_B >GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  return new GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> copyOf(final GetNotificationUnsubscriptionResponseType.Unsubscription _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  final GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> _newBuilder = new GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<>(null, null, false);
  _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
  return _newBuilder;
 }
  public static GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<Void> copyExcept(final GetNotificationUnsubscriptionResponseType.Unsubscription _other, final PropertyTree _propertyTree) {
  return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<Void> copyOnly(final GetNotificationUnsubscriptionResponseType.Unsubscription _other, final PropertyTree _propertyTree) {
  return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
    protected final _B _parentBuilder;
  protected final GetNotificationUnsubscriptionResponseType.Unsubscription _storedValue;
  private CreatedType.Builder<GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B>> created;
  private String recipientPersonIdentifier;
    public Builder(final _B _parentBuilder, final GetNotificationUnsubscriptionResponseType.Unsubscription _other, final boolean _copy) {
   this._parentBuilder = _parentBuilder;
   if (_other!= null) {
     if (_copy) {
        _storedValue = null;
        this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this));
        this.recipientPersonIdentifier = _other.recipientPersonIdentifier;
     } else {
        _storedValue = _other;
     }
   } else {
     _storedValue = null;
   }
  }
    public Builder(final _B _parentBuilder, final GetNotificationUnsubscriptionResponseType.Unsubscription _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
   this._parentBuilder = _parentBuilder;
   if (_other!= null) {
     if (_copy) {
        _storedValue = null;
        final PropertyTree createdPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("created"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(createdPropertyTree!= null):((createdPropertyTree == null)||(!createdPropertyTree.isLeaf())))) {
             this.created = ((_other.created == null)?null:_other.created.newCopyBuilder(this, createdPropertyTree, _propertyTreeUse));
        }
        final PropertyTree recipientPersonIdentifierPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("recipientPersonIdentifier"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(recipientPersonIdentifierPropertyTree!= null):((recipientPersonIdentifierPropertyTree == null)||(!recipientPersonIdentifierPropertyTree.isLeaf())))) {
             this.recipientPersonIdentifier = _other.recipientPersonIdentifier;
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
    protected<_P extends GetNotificationUnsubscriptionResponseType.Unsubscription >_P init(final _P _product) {
   _product.created = ((this.created == null)?null:this.created.build());
   _product.recipientPersonIdentifier = this.recipientPersonIdentifier;
   return _product;
  }
    /**
  * Sets the new value of "created" (any previous value will be replaced)
  * 
  * @param created
  *     New value of the "created" property.
  */
  public GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> withCreated(final CreatedType created) {
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
  public CreatedType.Builder<? extends GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B>> withCreated() {
   if (this.created!= null) {
     return this.created;
   }
   return this.created = new CreatedType.Builder<>(this, null, false);
  }
    /**
  * Sets the new value of "recipientPersonIdentifier" (any previous value will be
  * replaced)
  * 
  * @param recipientPersonIdentifier
  *     New value of the "recipientPersonIdentifier" property.
  */
  public GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> withRecipientPersonIdentifier(final String recipientPersonIdentifier) {
   this.recipientPersonIdentifier = recipientPersonIdentifier;
   return this;
  }
    @Override
  public GetNotificationUnsubscriptionResponseType.Unsubscription build() {
   if (_storedValue == null) {
     return this.init(new GetNotificationUnsubscriptionResponseType.Unsubscription());
   } else {
     return ((GetNotificationUnsubscriptionResponseType.Unsubscription) _storedValue);
   }
  }
    public GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> copyOf(final GetNotificationUnsubscriptionResponseType.Unsubscription _other) {
   _other.copyTo(this);
   return this;
  }
    public GetNotificationUnsubscriptionResponseType.Unsubscription.Builder<_B> copyOf(final GetNotificationUnsubscriptionResponseType.Unsubscription.Builder _other) {
   return copyOf(_other.build());
  }
   }
  public static class Select
 extends GetNotificationUnsubscriptionResponseType.Unsubscription.Selector<GetNotificationUnsubscriptionResponseType.Unsubscription.Select, Void>
 {
      Select() {
   super(null, null, null);
  }
    public static GetNotificationUnsubscriptionResponseType.Unsubscription.Select _root() {
   return new GetNotificationUnsubscriptionResponseType.Unsubscription.Select();
  }
   }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
    private CreatedType.Selector<TRoot, GetNotificationUnsubscriptionResponseType.Unsubscription.Selector<TRoot, TParent>> created = null;
  private com.kscs.util.jaxb.Selector<TRoot, GetNotificationUnsubscriptionResponseType.Unsubscription.Selector<TRoot, TParent>> recipientPersonIdentifier = null;
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
   if (this.recipientPersonIdentifier!= null) {
     products.put("recipientPersonIdentifier", this.recipientPersonIdentifier.init());
   }
   return products;
  }
    public CreatedType.Selector<TRoot, GetNotificationUnsubscriptionResponseType.Unsubscription.Selector<TRoot, TParent>> created() {
   return ((this.created == null)?this.created = new CreatedType.Selector<>(this._root, this, "created"):this.created);
  }
    public com.kscs.util.jaxb.Selector<TRoot, GetNotificationUnsubscriptionResponseType.Unsubscription.Selector<TRoot, TParent>> recipientPersonIdentifier() {
   return ((this.recipientPersonIdentifier == null)?this.recipientPersonIdentifier = new com.kscs.util.jaxb.Selector<>(this._root, this, "recipientPersonIdentifier"):this.recipientPersonIdentifier);
  }
   }
  }
 }
