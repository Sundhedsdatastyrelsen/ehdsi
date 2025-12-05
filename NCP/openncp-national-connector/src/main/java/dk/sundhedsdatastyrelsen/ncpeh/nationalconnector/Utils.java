package dk.sundhedsdatastyrelsen.ncpeh.nationalconnector;

import dk.sundhedsdatastyrelsen.ncpeh.ApiException;
import dk.sundhedsdatastyrelsen.ncpeh.api.model.ClassCode;
import eu.europa.ec.sante.openncp.common.error.OpenNCPErrorCode;
import eu.europa.ec.sante.openncp.core.common.ihe.exception.NIException;
import org.w3c.dom.Element;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Utils {
    public static String elementToString(Element xml) {
        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            StringWriter buffer = new StringWriter();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(xml), new StreamResult(buffer));
            return buffer.toString();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static ClassCode classCode(eu.europa.ec.sante.openncp.common.ClassCode classCode) {
        switch (classCode) {
            case EP_CLASSCODE:
                return ClassCode._57833_6;
            case ED_CLASSCODE:
                return ClassCode._60593_1;
            default:
                throw new IllegalArgumentException("Classcode not supported: " + classCode.getCode());
        }
    }

    public static Date offsetDateTimeToDate(OffsetDateTime d) {
        return d == null ? null : Date.from(d.toInstant());
    }

    public static OffsetDateTime dateToUtcOffsetDateTime(Date d) {
        return d == null ? null : d.toInstant().atOffset(ZoneOffset.UTC);
    }

    public static Long parseLong(String s) {
        return s == null ? null : Long.parseLong(s);
    }

    public static OffsetDateTime parseOffsetDateTime(String s) {
        return s == null ? null : OffsetDateTime.parse(s);
    }

    public static NIException restErrorToNcpException(ApiException apiError, OpenNCPErrorCode errorCode) {
        return new NIException(errorCode, apiError.getCode() > 0
                ? String.format("Error, status: %s body: %s", apiError.getCode(), apiError.getResponseBody())
                : "Missing response from service"
            );
    }
}
