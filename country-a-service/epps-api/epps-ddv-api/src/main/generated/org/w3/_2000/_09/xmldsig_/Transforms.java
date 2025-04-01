package org.w3._2000._09.xmldsig_;
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
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
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
*       <sequence maxOccurs="unbounded">
*         <element name="Transform">
*           <complexType>
*             <complexContent>
*               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*                 <attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
@XmlType(name = "", propOrder = {
 "transform"
})
@XmlRootElement(name = "Transforms")
public class Transforms {
  @XmlElement(name = "Transform", required = true)
 protected List<Transforms.Transform> transform;
  /**
 * Gets the value of the transform property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the transform property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getTransform().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link Transforms.Transform }
 * 
 * 
 * @return
 *     The value of the transform property.
 */
 public List<Transforms.Transform> getTransform() {
 if (transform == null) {
  transform = new ArrayList<>();
 }
 return this.transform;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final Transforms.Builder<_B> _other) {
 if (this.transform == null) {
  _other.transform = null;
 } else {
  _other.transform = new ArrayList<>();
  for (Transforms.Transform _item: this.transform) {
   _other.transform.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >Transforms.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new Transforms.Builder<_B>(_parentBuilder, this, true);
 }
  public Transforms.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static Transforms.Builder<Void> builder() {
 return new Transforms.Builder<>(null, null, false);
 }
  public static<_B >Transforms.Builder<_B> copyOf(final Transforms _other) {
 final Transforms.Builder<_B> _newBuilder = new Transforms.Builder<>(null, null, false);
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
 public<_B >void copyTo(final Transforms.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree transformPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("transform"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(transformPropertyTree!= null):((transformPropertyTree == null)||(!transformPropertyTree.isLeaf())))) {
  if (this.transform == null) {
   _other.transform = null;
  } else {
   _other.transform = new ArrayList<>();
   for (Transforms.Transform _item: this.transform) {
     _other.transform.add(((_item == null)?null:_item.newCopyBuilder(_other, transformPropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >Transforms.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new Transforms.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public Transforms.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >Transforms.Builder<_B> copyOf(final Transforms _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final Transforms.Builder<_B> _newBuilder = new Transforms.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static Transforms.Builder<Void> copyExcept(final Transforms _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static Transforms.Builder<Void> copyOnly(final Transforms _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final Transforms _storedValue;
 private List<Transforms.Transform.Builder<Transforms.Builder<_B>>> transform;
  public Builder(final _B _parentBuilder, final Transforms _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     if (_other.transform == null) {
        this.transform = null;
     } else {
        this.transform = new ArrayList<>();
        for (Transforms.Transform _item: _other.transform) {
             this.transform.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final Transforms _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree transformPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("transform"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(transformPropertyTree!= null):((transformPropertyTree == null)||(!transformPropertyTree.isLeaf())))) {
        if (_other.transform == null) {
             this.transform = null;
        } else {
             this.transform = new ArrayList<>();
             for (Transforms.Transform _item: _other.transform) {
                     this.transform.add(((_item == null)?null:_item.newCopyBuilder(this, transformPropertyTree, _propertyTreeUse)));
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
  protected<_P extends Transforms >_P init(final _P _product) {
  if (this.transform!= null) {
   final List<Transforms.Transform> transform = new ArrayList<>(this.transform.size());
   for (Transforms.Transform.Builder<Transforms.Builder<_B>> _item: this.transform) {
     transform.add(_item.build());
   }
   _product.transform = transform;
  }
  return _product;
 }
  /**
 * Adds the given items to the value of "transform"
 * 
 * @param transform
 *     Items to add to the value of the "transform" property
 */
 public Transforms.Builder<_B> addTransform(final Iterable<? extends Transforms.Transform> transform) {
  if (transform!= null) {
   if (this.transform == null) {
     this.transform = new ArrayList<>();
   }
   for (Transforms.Transform _item: transform) {
     this.transform.add(new Transforms.Transform.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "transform" (any previous value will be replaced)
 * 
 * @param transform
 *     New value of the "transform" property.
 */
 public Transforms.Builder<_B> withTransform(final Iterable<? extends Transforms.Transform> transform) {
  if (this.transform!= null) {
   this.transform.clear();
  }
  return addTransform(transform);
 }
  /**
 * Adds the given items to the value of "transform"
 * 
 * @param transform
 *     Items to add to the value of the "transform" property
 */
 public Transforms.Builder<_B> addTransform(Transforms.Transform... transform) {
  addTransform(Arrays.asList(transform));
  return this;
 }
  /**
 * Sets the new value of "transform" (any previous value will be replaced)
 * 
 * @param transform
 *     New value of the "transform" property.
 */
 public Transforms.Builder<_B> withTransform(Transforms.Transform... transform) {
  withTransform(Arrays.asList(transform));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "Transform" property.
 * Use {@link org.w3._2000._09.xmldsig_.Transforms.Transform.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "Transform" property.
 *     Use {@link org.w3._2000._09.xmldsig_.Transforms.Transform.Builder#end()} to
 *     return to the current builder.
 */
 public Transforms.Transform.Builder<? extends Transforms.Builder<_B>> addTransform() {
  if (this.transform == null) {
   this.transform = new ArrayList<>();
  }
  final Transforms.Transform.Builder<Transforms.Builder<_B>> transform_Builder = new Transforms.Transform.Builder<>(this, null, false);
  this.transform.add(transform_Builder);
  return transform_Builder;
 }
  @Override
 public Transforms build() {
  if (_storedValue == null) {
   return this.init(new Transforms());
  } else {
   return ((Transforms) _storedValue);
  }
 }
  public Transforms.Builder<_B> copyOf(final Transforms _other) {
  _other.copyTo(this);
  return this;
 }
  public Transforms.Builder<_B> copyOf(final Transforms.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends Transforms.Selector<Transforms.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static Transforms.Select _root() {
  return new Transforms.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private Transforms.Transform.Selector<TRoot, Transforms.Selector<TRoot, TParent>> transform = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.transform!= null) {
   products.put("transform", this.transform.init());
  }
  return products;
 }
  public Transforms.Transform.Selector<TRoot, Transforms.Selector<TRoot, TParent>> transform() {
  return ((this.transform == null)?this.transform = new Transforms.Transform.Selector<>(this._root, this, "transform"):this.transform);
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
 *       <attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "")
 public static class Transform {
  @XmlAttribute(name = "Algorithm", required = true)
 @XmlSchemaType(name = "anySimpleType")
 protected String algorithm;
  /**
 * Gets the value of the algorithm property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getAlgorithm() {
  return algorithm;
 }
  /**
 * Sets the value of the algorithm property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setAlgorithm(String value) {
  this.algorithm = value;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final Transforms.Transform.Builder<_B> _other) {
  _other.algorithm = this.algorithm;
 }
  public<_B >Transforms.Transform.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
  return new Transforms.Transform.Builder<_B>(_parentBuilder, this, true);
 }
  public Transforms.Transform.Builder<Void> newCopyBuilder() {
  return newCopyBuilder(null);
 }
  public static Transforms.Transform.Builder<Void> builder() {
  return new Transforms.Transform.Builder<>(null, null, false);
 }
  public static<_B >Transforms.Transform.Builder<_B> copyOf(final Transforms.Transform _other) {
  final Transforms.Transform.Builder<_B> _newBuilder = new Transforms.Transform.Builder<>(null, null, false);
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
 public<_B >void copyTo(final Transforms.Transform.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  final PropertyTree algorithmPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("algorithm"));
  if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(algorithmPropertyTree!= null):((algorithmPropertyTree == null)||(!algorithmPropertyTree.isLeaf())))) {
   _other.algorithm = this.algorithm;
  }
 }
  public<_B >Transforms.Transform.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  return new Transforms.Transform.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public Transforms.Transform.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >Transforms.Transform.Builder<_B> copyOf(final Transforms.Transform _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  final Transforms.Transform.Builder<_B> _newBuilder = new Transforms.Transform.Builder<>(null, null, false);
  _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
  return _newBuilder;
 }
  public static Transforms.Transform.Builder<Void> copyExcept(final Transforms.Transform _other, final PropertyTree _propertyTree) {
  return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static Transforms.Transform.Builder<Void> copyOnly(final Transforms.Transform _other, final PropertyTree _propertyTree) {
  return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
    protected final _B _parentBuilder;
  protected final Transforms.Transform _storedValue;
  private String algorithm;
    public Builder(final _B _parentBuilder, final Transforms.Transform _other, final boolean _copy) {
   this._parentBuilder = _parentBuilder;
   if (_other!= null) {
     if (_copy) {
        _storedValue = null;
        this.algorithm = _other.algorithm;
     } else {
        _storedValue = _other;
     }
   } else {
     _storedValue = null;
   }
  }
    public Builder(final _B _parentBuilder, final Transforms.Transform _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
   this._parentBuilder = _parentBuilder;
   if (_other!= null) {
     if (_copy) {
        _storedValue = null;
        final PropertyTree algorithmPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("algorithm"));
        if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(algorithmPropertyTree!= null):((algorithmPropertyTree == null)||(!algorithmPropertyTree.isLeaf())))) {
             this.algorithm = _other.algorithm;
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
    protected<_P extends Transforms.Transform >_P init(final _P _product) {
   _product.algorithm = this.algorithm;
   return _product;
  }
    /**
  * Sets the new value of "algorithm" (any previous value will be replaced)
  * 
  * @param algorithm
  *     New value of the "algorithm" property.
  */
  public Transforms.Transform.Builder<_B> withAlgorithm(final String algorithm) {
   this.algorithm = algorithm;
   return this;
  }
    @Override
  public Transforms.Transform build() {
   if (_storedValue == null) {
     return this.init(new Transforms.Transform());
   } else {
     return ((Transforms.Transform) _storedValue);
   }
  }
    public Transforms.Transform.Builder<_B> copyOf(final Transforms.Transform _other) {
   _other.copyTo(this);
   return this;
  }
    public Transforms.Transform.Builder<_B> copyOf(final Transforms.Transform.Builder _other) {
   return copyOf(_other.build());
  }
   }
  public static class Select
 extends Transforms.Transform.Selector<Transforms.Transform.Select, Void>
 {
      Select() {
   super(null, null, null);
  }
    public static Transforms.Transform.Select _root() {
   return new Transforms.Transform.Select();
  }
   }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
    private com.kscs.util.jaxb.Selector<TRoot, Transforms.Transform.Selector<TRoot, TParent>> algorithm = null;
    public Selector(final TRoot root, final TParent parent, final String propertyName) {
   super(root, parent, propertyName);
  }
    @Override
  public Map<String, PropertyTree> buildChildren() {
   final Map<String, PropertyTree> products = new HashMap<>();
   products.putAll(super.buildChildren());
   if (this.algorithm!= null) {
     products.put("algorithm", this.algorithm.init());
   }
   return products;
  }
    public com.kscs.util.jaxb.Selector<TRoot, Transforms.Transform.Selector<TRoot, TParent>> algorithm() {
   return ((this.algorithm == null)?this.algorithm = new com.kscs.util.jaxb.Selector<>(this._root, this, "algorithm"):this.algorithm);
  }
   }
  }
 }
