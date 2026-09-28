package dk.sundhedsdatastyrelsen.epportal.utils

data class XmlNamespace(val prefix: String?, val uri: String?) {
    companion object {
        // This is a list of namespaces used in the project. Its purpose is convenience, to encourage consistency,
        // and to avoid scattering the information around the codebase.
        val HL7: XmlNamespace = XmlNamespace("hl7", "urn:hl7-org:v3")

        /** International Search Mask */
        val ISM = XmlNamespace("ism", "http://ec.europa.eu/sante/ehncp/ism")

        /** FSK */
        val SDTC: XmlNamespace = XmlNamespace("sdtc", "urn:hl7-org:sdtc")

        /** Dispensation */
        val PHARM: XmlNamespace = XmlNamespace("pharm", "urn:hl7-org:pharm")

        /** Authentication */
        val AUTH: XmlNamespace = XmlNamespace("auth", "http://docs.oasis-open.org/wsfed/authorization/200706")
        val DS: XmlNamespace = XmlNamespace("ds", "http://www.w3.org/2000/09/xmldsig#")

        // There are two valid DGWS/MEDCOM namespaces for historic reasons, and CPR at least requires the old one.
        // https://svn.medcom.dk/svn/releases/Standarder/DGWS/Dokumentation/Den%20Gode%20Webservice%201.0.1.pdf:
        // > [...] ældre webservices ikke altid har overholdt XML-skema, men anvendt det
        // > førstnævnte namespace (MEDCOM_LEGACY). Derfor anbefales det sidstnævnte namespace
        // > (MEDCOM) til fremtidige webservices [...]
        val MEDCOM: XmlNamespace =
            XmlNamespace("medcom", "http://svn.medcom.dk/svn/releases/Standarder/DGWS/Schemas/medcom-1.0.1.xsd")
        val MEDCOM_LEGACY: XmlNamespace =
            XmlNamespace("medcom-legacy", "http://www.medcom.dk/dgws/2006/04/dgws-1.0.xsd")
        val SAML: XmlNamespace = XmlNamespace("saml", "urn:oasis:names:tc:SAML:2.0:assertion")
        val SOAP: XmlNamespace = XmlNamespace("soap", "http://schemas.xmlsoap.org/soap/envelope/")
        val SOSI: XmlNamespace = XmlNamespace("sosi", "http://www.sosi.dk/sosi/2006/04/sosi-1.0.xsd")
        val WSA: XmlNamespace = XmlNamespace("wsa", "http://www.w3.org/2005/08/addressing")
        val WSP: XmlNamespace = XmlNamespace("wsp", "http://schemas.xmlsoap.org/ws/2004/09/policy")
        val WSSE: XmlNamespace =
            XmlNamespace("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
        val WSSE11: XmlNamespace =
            XmlNamespace("wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd")
        val WST: XmlNamespace = XmlNamespace("wst", "http://schemas.xmlsoap.org/ws/2005/02/trust")
        val WST13: XmlNamespace = XmlNamespace("wst13", "http://docs.oasis-open.org/ws-sx/ws-trust/200512")
        val WST14: XmlNamespace = XmlNamespace("wst14", "http://docs.oasis-open.org/ws-sx/ws-trust/200802")
        val WSU: XmlNamespace =
            XmlNamespace("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
        val XSD: XmlNamespace = XmlNamespace("xsd", "http://www.w3.org/2001/XMLSchema")
        val XSI: XmlNamespace = XmlNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")
    }
}
