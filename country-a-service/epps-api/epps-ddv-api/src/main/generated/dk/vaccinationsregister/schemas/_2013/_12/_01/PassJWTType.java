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
* <p>Java class for PassJWTType complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>{@code
* <complexType name="PassJWTType">
*   <complexContent>
*     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       <sequence>
*         <element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         <element name="JWT" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         <element name="Properties" type="{http://vaccinationsregister.dk/schemas/2013/12/01}KeyValuePairType" maxOccurs="unbounded" minOccurs="0"/>
*       </sequence>
*     </restriction>
*   </complexContent>
* </complexType>
* }</pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PassJWTType", propOrder = {
 "type",
 "jwt",
 "properties"
})
public class PassJWTType {
  @XmlElement(name = "Type", required = true)
 protected String type;
 @XmlElement(name = "JWT", required = true)
 protected String jwt;
 @XmlElement(name = "Properties")
 protected List<KeyValuePairType> properties;
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
 * Gets the value of the jwt property.
 * 
 * @return
 *     possible object is
 *     {@link String }
 *     
 */
 public String getJWT() {
 return jwt;
 }
  /**
 * Sets the value of the jwt property.
 * 
 * @param value
 *     allowed object is
 *     {@link String }
 *     
 */
 public void setJWT(String value) {
 this.jwt = value;
 }
  /**
 * Gets the value of the properties property.
 * 
 * <p>
 * This accessor method returns a reference to the live list,
 * not a snapshot. Therefore any modification you make to the
 * returned list will be present inside the Jakarta XML Binding object.
 * This is why there is not a {@code set} method for the properties property.
 * 
 * <p>
 * For example, to add a new item, do as follows:
 * <pre>
 *    getProperties().add(newItem);
 * </pre>
 * 
 * 
 * <p>
 * Objects of the following type(s) are allowed in the list
 * {@link KeyValuePairType }
 * 
 * 
 * @return
 *     The value of the properties property.
 */
 public List<KeyValuePairType> getProperties() {
 if (properties == null) {
  properties = new ArrayList<>();
 }
 return this.properties;
 }
  /**
 * Copies all state of this object to a builder. This method is used by the {@link
 * #copyOf} method and should not be called directly by client code.
 * 
 * @param _other
 *     A builder instance to which the state of this object will be copied.
 */
 public<_B >void copyTo(final PassJWTType.Builder<_B> _other) {
 _other.type = this.type;
 _other.jwt = this.jwt;
 if (this.properties == null) {
  _other.properties = null;
 } else {
  _other.properties = new ArrayList<>();
  for (KeyValuePairType _item: this.properties) {
   _other.properties.add(((_item == null)?null:_item.newCopyBuilder(_other)));
  }
 }
 }
  public<_B >PassJWTType.Builder<_B> newCopyBuilder(final _B _parentBuilder) {
 return new PassJWTType.Builder<_B>(_parentBuilder, this, true);
 }
  public PassJWTType.Builder<Void> newCopyBuilder() {
 return newCopyBuilder(null);
 }
  public static PassJWTType.Builder<Void> builder() {
 return new PassJWTType.Builder<>(null, null, false);
 }
  public static<_B >PassJWTType.Builder<_B> copyOf(final PassJWTType _other) {
 final PassJWTType.Builder<_B> _newBuilder = new PassJWTType.Builder<>(null, null, false);
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
 public<_B >void copyTo(final PassJWTType.Builder<_B> _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PropertyTree typePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("type"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(typePropertyTree!= null):((typePropertyTree == null)||(!typePropertyTree.isLeaf())))) {
  _other.type = this.type;
 }
 final PropertyTree jwtPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("jwt"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(jwtPropertyTree!= null):((jwtPropertyTree == null)||(!jwtPropertyTree.isLeaf())))) {
  _other.jwt = this.jwt;
 }
 final PropertyTree propertiesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("properties"));
 if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(propertiesPropertyTree!= null):((propertiesPropertyTree == null)||(!propertiesPropertyTree.isLeaf())))) {
  if (this.properties == null) {
   _other.properties = null;
  } else {
   _other.properties = new ArrayList<>();
   for (KeyValuePairType _item: this.properties) {
     _other.properties.add(((_item == null)?null:_item.newCopyBuilder(_other, propertiesPropertyTree, _propertyTreeUse)));
   }
  }
 }
 }
  public<_B >PassJWTType.Builder<_B> newCopyBuilder(final _B _parentBuilder, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return new PassJWTType.Builder<_B>(_parentBuilder, this, true, _propertyTree, _propertyTreeUse);
 }
  public PassJWTType.Builder<Void> newCopyBuilder(final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 return newCopyBuilder(null, _propertyTree, _propertyTreeUse);
 }
  public static<_B >PassJWTType.Builder<_B> copyOf(final PassJWTType _other, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
 final PassJWTType.Builder<_B> _newBuilder = new PassJWTType.Builder<>(null, null, false);
 _other.copyTo(_newBuilder, _propertyTree, _propertyTreeUse);
 return _newBuilder;
 }
  public static PassJWTType.Builder<Void> copyExcept(final PassJWTType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.EXCLUDE);
 }
  public static PassJWTType.Builder<Void> copyOnly(final PassJWTType _other, final PropertyTree _propertyTree) {
 return copyOf(_other, _propertyTree, PropertyTreeUse.INCLUDE);
 }
  public static class Builder<_B >implements Buildable
 {
  protected final _B _parentBuilder;
 protected final PassJWTType _storedValue;
 private String type;
 private String jwt;
 private List<KeyValuePairType.Builder<PassJWTType.Builder<_B>>> properties;
  public Builder(final _B _parentBuilder, final PassJWTType _other, final boolean _copy) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     this.type = _other.type;
     this.jwt = _other.jwt;
     if (_other.properties == null) {
        this.properties = null;
     } else {
        this.properties = new ArrayList<>();
        for (KeyValuePairType _item: _other.properties) {
             this.properties.add(((_item == null)?null:_item.newCopyBuilder(this)));
        }
     }
   } else {
     _storedValue = _other;
   }
  } else {
   _storedValue = null;
  }
 }
  public Builder(final _B _parentBuilder, final PassJWTType _other, final boolean _copy, final PropertyTree _propertyTree, final PropertyTreeUse _propertyTreeUse) {
  this._parentBuilder = _parentBuilder;
  if (_other!= null) {
   if (_copy) {
     _storedValue = null;
     final PropertyTree typePropertyTree = ((_propertyTree == null)?null:_propertyTree.get("type"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(typePropertyTree!= null):((typePropertyTree == null)||(!typePropertyTree.isLeaf())))) {
        this.type = _other.type;
     }
     final PropertyTree jwtPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("jwt"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(jwtPropertyTree!= null):((jwtPropertyTree == null)||(!jwtPropertyTree.isLeaf())))) {
        this.jwt = _other.jwt;
     }
     final PropertyTree propertiesPropertyTree = ((_propertyTree == null)?null:_propertyTree.get("properties"));
     if (((_propertyTreeUse == PropertyTreeUse.INCLUDE)?(propertiesPropertyTree!= null):((propertiesPropertyTree == null)||(!propertiesPropertyTree.isLeaf())))) {
        if (_other.properties == null) {
             this.properties = null;
        } else {
             this.properties = new ArrayList<>();
             for (KeyValuePairType _item: _other.properties) {
                     this.properties.add(((_item == null)?null:_item.newCopyBuilder(this, propertiesPropertyTree, _propertyTreeUse)));
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
  protected<_P extends PassJWTType >_P init(final _P _product) {
  _product.type = this.type;
  _product.jwt = this.jwt;
  if (this.properties!= null) {
   final List<KeyValuePairType> properties = new ArrayList<>(this.properties.size());
   for (KeyValuePairType.Builder<PassJWTType.Builder<_B>> _item: this.properties) {
     properties.add(_item.build());
   }
   _product.properties = properties;
  }
  return _product;
 }
  /**
 * Sets the new value of "type" (any previous value will be replaced)
 * 
 * @param type
 *     New value of the "type" property.
 */
 public PassJWTType.Builder<_B> withType(final String type) {
  this.type = type;
  return this;
 }
  /**
 * Sets the new value of "jwt" (any previous value will be replaced)
 * 
 * @param jwt
 *     New value of the "jwt" property.
 */
 public PassJWTType.Builder<_B> withJWT(final String jwt) {
  this.jwt = jwt;
  return this;
 }
  /**
 * Adds the given items to the value of "properties"
 * 
 * @param properties
 *     Items to add to the value of the "properties" property
 */
 public PassJWTType.Builder<_B> addProperties(final Iterable<? extends KeyValuePairType> properties) {
  if (properties!= null) {
   if (this.properties == null) {
     this.properties = new ArrayList<>();
   }
   for (KeyValuePairType _item: properties) {
     this.properties.add(new KeyValuePairType.Builder<>(this, _item, false));
   }
  }
  return this;
 }
  /**
 * Sets the new value of "properties" (any previous value will be replaced)
 * 
 * @param properties
 *     New value of the "properties" property.
 */
 public PassJWTType.Builder<_B> withProperties(final Iterable<? extends KeyValuePairType> properties) {
  if (this.properties!= null) {
   this.properties.clear();
  }
  return addProperties(properties);
 }
  /**
 * Adds the given items to the value of "properties"
 * 
 * @param properties
 *     Items to add to the value of the "properties" property
 */
 public PassJWTType.Builder<_B> addProperties(KeyValuePairType... properties) {
  addProperties(Arrays.asList(properties));
  return this;
 }
  /**
 * Sets the new value of "properties" (any previous value will be replaced)
 * 
 * @param properties
 *     New value of the "properties" property.
 */
 public PassJWTType.Builder<_B> withProperties(KeyValuePairType... properties) {
  withProperties(Arrays.asList(properties));
  return this;
 }
  /**
 * Returns a new builder to build an additional value of the "Properties" property.
 * Use {@link
 * dk.vaccinationsregister.schemas._2013._12._01.KeyValuePairType.Builder#end()} to
 * return to the current builder.
 * 
 * @return
 *     a new builder to build an additional value of the "Properties" property.
 *     Use {@link
 *     dk.vaccinationsregister.schemas._2013._12._01.KeyValuePairType.Builder#end()} to
 *     return to the current builder.
 */
 public KeyValuePairType.Builder<? extends PassJWTType.Builder<_B>> addProperties() {
  if (this.properties == null) {
   this.properties = new ArrayList<>();
  }
  final KeyValuePairType.Builder<PassJWTType.Builder<_B>> properties_Builder = new KeyValuePairType.Builder<>(this, null, false);
  this.properties.add(properties_Builder);
  return properties_Builder;
 }
  @Override
 public PassJWTType build() {
  if (_storedValue == null) {
   return this.init(new PassJWTType());
  } else {
   return ((PassJWTType) _storedValue);
  }
 }
  public PassJWTType.Builder<_B> copyOf(final PassJWTType _other) {
  _other.copyTo(this);
  return this;
 }
  public PassJWTType.Builder<_B> copyOf(final PassJWTType.Builder _other) {
  return copyOf(_other.build());
 }
  }
  public static class Select
 extends PassJWTType.Selector<PassJWTType.Select, Void>
 {
   Select() {
  super(null, null, null);
 }
  public static PassJWTType.Select _root() {
  return new PassJWTType.Select();
 }
  }
  public static class Selector<TRoot extends com.kscs.util.jaxb.Selector<TRoot, ?> , TParent >
 extends com.kscs.util.jaxb.Selector<TRoot, TParent>
 {
  private com.kscs.util.jaxb.Selector<TRoot, PassJWTType.Selector<TRoot, TParent>> type = null;
 private com.kscs.util.jaxb.Selector<TRoot, PassJWTType.Selector<TRoot, TParent>> jwt = null;
 private KeyValuePairType.Selector<TRoot, PassJWTType.Selector<TRoot, TParent>> properties = null;
  public Selector(final TRoot root, final TParent parent, final String propertyName) {
  super(root, parent, propertyName);
 }
  @Override
 public Map<String, PropertyTree> buildChildren() {
  final Map<String, PropertyTree> products = new HashMap<>();
  products.putAll(super.buildChildren());
  if (this.type!= null) {
   products.put("type", this.type.init());
  }
  if (this.jwt!= null) {
   products.put("jwt", this.jwt.init());
  }
  if (this.properties!= null) {
   products.put("properties", this.properties.init());
  }
  return products;
 }
  public com.kscs.util.jaxb.Selector<TRoot, PassJWTType.Selector<TRoot, TParent>> type() {
  return ((this.type == null)?this.type = new com.kscs.util.jaxb.Selector<>(this._root, this, "type"):this.type);
 }
  public com.kscs.util.jaxb.Selector<TRoot, PassJWTType.Selector<TRoot, TParent>> jwt() {
  return ((this.jwt == null)?this.jwt = new com.kscs.util.jaxb.Selector<>(this._root, this, "jwt"):this.jwt);
 }
  public KeyValuePairType.Selector<TRoot, PassJWTType.Selector<TRoot, TParent>> properties() {
  return ((this.properties == null)?this.properties = new KeyValuePairType.Selector<>(this._root, this, "properties"):this.properties);
 }
  }
 }
