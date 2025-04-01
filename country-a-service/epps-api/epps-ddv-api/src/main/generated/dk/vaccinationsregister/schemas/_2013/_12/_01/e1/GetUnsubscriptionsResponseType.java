package dk.vaccinationsregister.schemas._2013._12._01.e1;
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
* <p>Java class for GetUnsubscriptionsResponseType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="GetUnsubscriptionsResponseType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="Unsubscription" type="{http://vaccinationsregister.dk/schemas/2013/12/01/E1}UnsubscriptionType" maxOccurs="unbounded" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetUnsubscriptionsResponseType", propOrder = {
 "unsubscription"
})
public class GetUnsubscriptionsResponseType {
  @XmlElement(name = "Unsubscription")
 protected List<UnsubscriptionType> unsubscription;
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
 * {@link UnsubscriptionType }
 * 
 * 
 * @return
 *     The value of the unsubscription property.
 */
 public List<UnsubscriptionType> getUnsubscription() {
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
 public<_B >void copyTo(final GetUnsubscriptionsResponseType.Builder<_B> _other) {
 if (this.unsubscription == null) {
  _other.unsubscription = null;
 } else {
  _other.unsubscription = new ArrayList<>();
  for (UnsubscriptionType _item: this.unsubscription) {
   _other.unsubscription.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >GetUnsubscriptionsResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new GetUnsubscriptionsResponseType.Builder<_B>(_parentBuilder, this, true);
 }
  public GetUnsubscriptionsResponseType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static GetUnsubscriptionsResponseType.Builder<Void> builder() {
 return new GetUnsubscriptionsResponseType.Builder<>(null, null, false);
 }
  public static<_B >GetUnsubscriptionsResponseType.Builder<_B> copyOf(final GetUnsubscriptionsResponseType _other) {
 final GetUnsubscriptionsResponseType.Builder<_B> _newBuilder = new GetUnsubscriptionsResponseType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final GetUnsubscriptionsResponseType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree unsubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("unsubscription"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(unsubscriptionPropertyTree!= null):((unsubscriptionPropertyTree == null)||(!unsubscriptionPropertyTree.isLeaf())))) {
  if (this.unsubscription == null) {
   _other.unsubscription = null;
  } else {
   _other.unsubscription = new ArrayList<>();
   for (UnsubscriptionType _item: this.unsubscription) {
     _other.unsubscription.add(((_item == null)?null:_item.newCopyBuilder(_other, unsubscriptionPropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >GetUnsubscriptionsResponseType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new GetUnsubscriptionsResponseType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public GetUnsubscriptionsResponseType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >GetUnsubscriptionsResponseType.Builder<_B> copyOf(final GetUnsubscriptionsResponseType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final GetUnsubscriptionsResponseType.Builder<_B> _newBuilder = new GetUnsubscriptionsResponseType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static GetUnsubscriptionsResponseType.Builder<Void> copyExcept(final GetUnsubscriptionsResponseType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static GetUnsubscriptionsResponseType.Builder<Void> copyOnly(final GetUnsubscriptionsResponseType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final GetUnsubscriptionsResponseType _storedValue;
 private List<UnsubscriptionType.Builder<GetUnsubscriptionsResponseType.Builder<_B>>> unsubscription;
  public Builder(final _B _parentBuilder, final GetUnsubscriptionsResponseType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     if (_other.unsubscription == null) {
        this.unsubscription = null;
     } else {
        this.unsubscription = new ArrayList<>();
        for (UnsubscriptionType _item: _other.unsubscription) {
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
  public Builder(final _B _parentBuilder, final GetUnsubscriptionsResponseType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree unsubscriptionPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("unsubscription"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(unsubscriptionPropertyTree!= null):((unsubscriptionPropertyTree == null)||(!unsubscriptionPropertyTree.isLeaf())))) {
        if (_other.unsubscription == null) {
             this.unsubscription = null;
        } else {
             this.unsubscription = new ArrayList<>();
             for (UnsubscriptionType _item: _other.unsubscription) {
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
  protected<_P extends GetUnsubscriptionsResponseType >_P init(final _P _product) {
  if (this.unsubscription!= null) {
   final List<UnsubscriptionType> unsubscription = new ArrayList<>(this.unsubscription.size());
   for (UnsubscriptionType.Builder<GetUnsubscriptionsResponseType.Builder<_B>> _item: this.unsubscription) {
     unsubscription.add(_item.build());
   }
   _product.unsubscription = unsubscription;
  }
  return _product;
 }
  /**
 * Adds the given items to the value of "unsubscription"
 * 
 * @param unsubscription
 *     Items to add to the value of the "unsubscription" property
 */
 public GetUnsubscriptionsResponseType.Builder<_B> addUnsubscription(final Iterable<? extends UnsubscriptionType> unsubscription) {
  if (unsubscription!= null) {
   if (this.unsubscription == null) {
     this.unsubscription = new ArrayList<>();
   }
   for (UnsubscriptionType _item: unsubscription) {
     this.unsubscription.add(new UnsubscriptionType.Builder<>(this, _item, false));
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
 public GetUnsubscriptionsResponseType.Builder<_B> withUnsubscription(final Iterable<? extends UnsubscriptionType> unsubscription) {
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
 public GetUnsubscriptionsResponseType.Builder<_B> addUnsubscription(UnsubscriptionType... unsubscription) {
  addUnsubscription(Arrays.asList(unsubscription));
  return this;
 }
  /**
 * Sets the new value of "unsubscription" (any previous value will be replaced)
 * 
 * @param unsubscription
 *     New value of the "unsubscription" property.
 */
 public GetUnsubscriptionsResponseType.Builder<_B> withUnsubscription(UnsubscriptionType... unsubscription) {
  withUnsubscription(Arrays.asList(unsubscription));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "Unsubscription"
 * property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.e1.UnsubscriptionType.Builder#end()}
 * to return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "Unsubscription" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.e1.UnsubscriptionType.Builder#end()}
 *     to return to the current builder.
 */
 public UnsubscriptionType.Builder<? extends GetUnsubscriptionsResponseType.Builder<_B>> addUnsubscription() {
  if (this.unsubscription == null) {
   this.unsubscription = new ArrayList<>();
  }
  final UnsubscriptionType.Builder<GetUnsubscriptionsResponseType.Builder<_B>> unsubscription_Builder = new UnsubscriptionType.Builder<>(this, null, false);
  this.unsubscription.add(unsubscription_Builder);
  return unsubscription_Builder;
 }
  @Override
 public GetUnsubscriptionsResponseType build() {
  if (_storedValue == null) {
   return this.init(new GetUnsubscriptionsResponseType());
  } else {
   return ((GetUnsubscriptionsResponseType) _storedValue);
  }
 }
  public GetUnsubscriptionsResponseType.Builder<_B> copyOf(final GetUnsubscriptionsResponseType _other) {
  _other.copyTo(this);
  return this;
 }
  public GetUnsubscriptionsResponseType.Builder<_B> copyOf(final GetUnsubscriptionsResponseType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends GetUnsubscriptionsResponseType.Selector<GetUnsubscriptionsResponseType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static GetUnsubscriptionsResponseType.Select _root() {
  return new GetUnsubscriptionsResponseType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private UnsubscriptionType.Selector<TRoot, GetUnsubscriptionsResponseType.Selector<TRoot, TParent>> unsubscription = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.unsubscription!= null) {
   products.put("unsubscription", this.unsubscription.init());
  }
  return products;
 }
  public UnsubscriptionType.Selector<TRoot, GetUnsubscriptionsResponseType.Selector<TRoot, TParent>> unsubscription() {
  return ((this.unsubscription == null)?this.unsubscription = new UnsubscriptionType.Selector<>(this._root, this, "unsubscription"):this.unsubscription);
 }
  }
 }
