package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import dk.sundhedsdatastyrelsen.ncpeh.authentication.mapper.SoapHeaderToNcpBstMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class AuthenticationMain {

    private static final String INPUT_DIR = "src/main/resources/soap-headers";
    private static final String OUTPUT_DIR = "src/main/resources/output";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java AuthenticationMain <input-filename>");
            System.out.println("Example: java AuthenticationMain soapHeader.xml");
            return;
        }

        String inputFilename = args[0];
        Path inputPath = Paths.get(INPUT_DIR, inputFilename);
        Path outputPath = Paths.get(OUTPUT_DIR, getOutputFilename(inputFilename));

        try {
            // Create output directory if it doesn't exist
            Files.createDirectories(Paths.get(OUTPUT_DIR));

            // Read input file
            String soapHeader = Files.readString(inputPath);
            log.info("Read SOAP header from: {}", inputPath);

            // Transform to NCP-BST
            SoapHeaderToNcpBstMapper mapper = new SoapHeaderToNcpBstMapper();
            String ncpBstXml = mapper.mapToNcpBst(soapHeader);
            log.info("Transformed SOAP header to NCP-BST format");

            // Write output file
            Files.writeString(outputPath, ncpBstXml);
            log.info("Wrote NCP-BST XML to: {}", outputPath);

        } catch (IOException e) {
            log.error("Failed to process file: {}", inputFilename, e);
            System.exit(1);
        }
    }

    private static String getOutputFilename(String inputFilename) {
        String baseName = inputFilename.substring(0, inputFilename.lastIndexOf('.'));
        return baseName + "-ncp-bst.xml";
    }
} 