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
* <p>Java class for PassListType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="PassListType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="Pass" type="{http://vaccinationsregister.dk/schemas/2013/12/01}PassJWTType" maxOccurs="unbounded" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PassListType", propOrder = {
 "pass"
})
public class PassListType {
  @XmlElement(name = "Pass")
 protected List<PassJWTType> pass;
  /**
 * Gets the value of the pass property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the pass property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getPass().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link PassJWTType }
 * 
 * 
 * @return
 *     The value of the pass property.
 */
 public List<PassJWTType> getPass() {
 if (pass == null) {
  pass = new ArrayList<>();
 }
 return this.pass;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final PassListType.Builder<_B> _other) {
 if (this.pass == null) {
  _other.pass = null;
 } else {
  _other.pass = new ArrayList<>();
  for (PassJWTType _item: this.pass) {
   _other.pass.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >PassListType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new PassListType.Builder<_B>(_parentBuilder, this, true);
 }
  public PassListType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static PassListType.Builder<Void> builder() {
 return new PassListType.Builder<>(null, null, false);
 }
  public static<_B >PassListType.Builder<_B> copyOf(final PassListType _other) {
 final PassListType.Builder<_B> _newBuilder = new PassListType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final PassListType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree passPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("pass"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(passPropertyTree!= null):((passPropertyTree == null)||(!passPropertyTree.isLeaf())))) {
  if (this.pass == null) {
   _other.pass = null;
  } else {
   _other.pass = new ArrayList<>();
   for (PassJWTType _item: this.pass) {
     _other.pass.add(((_item == null)?null:_item.newCopyBuilder(_other, passPropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >PassListType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new PassListType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public PassListType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >PassListType.Builder<_B> copyOf(final PassListType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PassListType.Builder<_B> _newBuilder = new PassListType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static PassListType.Builder<Void> copyExcept(final PassListType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static PassListType.Builder<Void> copyOnly(final PassListType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final PassListType _storedValue;
 private List<PassJWTType.Builder<PassListType.Builder<_B>>> pass;
  public Builder(final _B _parentBuilder, final PassListType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     if (_other.pass == null) {
        this.pass = null;
     } else {
        this.pass = new ArrayList<>();
        for (PassJWTType _item: _other.pass) {
             this.pass.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final PassListType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree passPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("pass"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(passPropertyTree!= null):((passPropertyTree == null)||(!passPropertyTree.isLeaf())))) {
        if (_other.pass == null) {
             this.pass = null;
        } else {
             this.pass = new ArrayList<>();
             for (PassJWTType _item: _other.pass) {
                     this.pass.add(((_item == null)?null:_item.newCopyBuilder(this, passPropertyTree, _propertyTreeUse)));
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
  protected<_P extends PassListType >_P init(final _P _product) {
  if (this.pass!= null) {
   final List<PassJWTType> pass = new ArrayList<>(this.pass.size());
   for (PassJWTType.Builder<PassListType.Builder<_B>> _item: this.pass) {
     pass.add(_item.build());
   }
   _product.pass = pass;
  }
  return _product;
 }
  /**
 * Adds the given items to the value of "pass"
 * 
 * @param pass
 *     Items to add to the value of the "pass" property
 */
 public PassListType.Builder<_B> addPass(final Iterable<? extends PassJWTType> pass) {
  if (pass!= null) {
   if (this.pass == null) {
     this.pass = new ArrayList<>();
   }
   for (PassJWTType _item: pass) {
     this.pass.add(new PassJWTType.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "pass" (any previous value will be replaced)
 * 
 * @param pass
 *     New value of the "pass" property.
 */
 public PassListType.Builder<_B> withPass(final Iterable<? extends PassJWTType> pass) {
  if (this.pass!= null) {
   this.pass.clear();
  }
  return addPass(pass);
 }
  /**
 * Adds the given items to the value of "pass"
 * 
 * @param pass
 *     Items to add to the value of the "pass" property
 */
 public PassListType.Builder<_B> addPass(PassJWTType... pass) {
  addPass(Arrays.asList(pass));
  return this;
 }
  /**
 * Sets the new value of "pass" (any previous value will be replaced)
 * 
 * @param pass
 *     New value of the "pass" property.
 */
 public PassListType.Builder<_B> withPass(PassJWTType... pass) {
  withPass(Arrays.asList(pass));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "Pass" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.PassJWTType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "Pass" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.PassJWTType.Builder#end()} to
 *     return to the current builder.
 */
 public PassJWTType.Builder<? extends PassListType.Builder<_B>> addPass() {
  if (this.pass == null) {
   this.pass = new ArrayList<>();
  }
  final PassJWTType.Builder<PassListType.Builder<_B>> pass_Builder = new PassJWTType.Builder<>(this, null, false);
  this.pass.add(pass_Builder);
  return pass_Builder;
 }
  @Override
 public PassListType build() {
  if (_storedValue == null) {
   return this.init(new PassListType());
  } else {
   return ((PassListType) _storedValue);
  }
 }
  public PassListType.Builder<_B> copyOf(final PassListType _other) {
  _other.copyTo(this);
  return this;
 }
  public PassListType.Builder<_B> copyOf(final PassListType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends PassListType.Selector<PassListType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static PassListType.Select _root() {
  return new PassListType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private PassJWTType.Selector<TRoot, PassListType.Selector<TRoot, TParent>> pass = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.pass!= null) {
   products.put("pass", this.pass.init());
  }
  return products;
 }
  public PassJWTType.Selector<TRoot, PassListType.Selector<TRoot, TParent>> pass() {
  return ((this.pass == null)?this.pass = new PassJWTType.Selector<>(this._root, this, "pass"):this.pass);
 }
  }
 }
