package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import java.io.File;

public class AuthenticationMain {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java AuthenticationMain <soapHeaderFile>");
            System.exit(1);
        }

        try {
            SoapHeaderParser parser = new SoapHeaderParser();
            ParsedData parsedData = parser.parse(new File(args[0]));

            // You can log or process the parsed data here
            System.out.println("Parsed ID: " + parsedData.getId());
            System.out.println("Issuer: " + parsedData.getIssuer());
            System.out.println("Subject: " + parsedData.getSubject().getNameIdValue());

            AssertionBuilder builder = new AssertionBuilder();
            var outputXML = builder.buildAssertionXml(parsedData, "0123456789");
            System.out.println("Assertion XML:\n\n" + outputXML);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
