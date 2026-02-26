package dk.sundhedsdatastyrelsen.ncpeh.client.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.dom.DOMResult;

public class ClientUtils {
    public static <T> Element toElement(JAXBContext jaxbContext, JAXBElement<T> jaxbElement) throws JAXBException {
        DOMResult res = new DOMResult();
        jaxbContext.createMarshaller().marshal(jaxbElement, res);
        return ((Document) res.getNode()).getDocumentElement();
    }
}
