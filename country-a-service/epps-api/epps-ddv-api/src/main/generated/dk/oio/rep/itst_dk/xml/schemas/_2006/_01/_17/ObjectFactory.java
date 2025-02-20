
package dk.oio.rep.itst_dk.xml.schemas._2006._01._17;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dk.oio.rep.itst_dk.xml.schemas._2006._01._17 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _PersonNameStructure_QNAME = new QName("http://rep.oio.dk/itst.dk/xml/schemas/2006/01/17/", "PersonNameStructure");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dk.oio.rep.itst_dk.xml.schemas._2006._01._17
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PersonNameStructureType }
     * 
     * @return
     *     the new instance of {@link PersonNameStructureType }
     */
    public PersonNameStructureType createPersonNameStructureType() {
        return new PersonNameStructureType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonNameStructureType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PersonNameStructureType }{@code >}
     */
    @XmlElementDecl(namespace = "http://rep.oio.dk/itst.dk/xml/schemas/2006/01/17/", name = "PersonNameStructure")
    public JAXBElement<PersonNameStructureType> createPersonNameStructure(PersonNameStructureType value) {
        return new JAXBElement<>(_PersonNameStructure_QNAME, PersonNameStructureType.class, null, value);
    }

}
