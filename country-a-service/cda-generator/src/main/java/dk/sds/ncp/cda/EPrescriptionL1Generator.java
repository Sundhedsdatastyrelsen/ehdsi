package dk.sds.ncp.cda;

import dk.sds.ncp.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
import lombok.Setter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.*;
import java.util.Base64;
import java.util.Objects;

public class EPrescriptionL1Generator {
    private final File baseFile;

    private String Author;
    private String PrescriptionId;
    private String Recipient;
    private String Ordination;

    private PDDocument pdfDocument;
    private PDPage pdfPage;

    public static String generate(EPrescriptionL3 dataModel) {
        EPrescriptionL1Generator generator = new EPrescriptionL1Generator("SDS_ProtoRecept.pdf");

        //TODO CFB more extensive data here
        generator.author(dataModel.getAuthor().getName().getFullName());
        generator.prescriptionId(dataModel.getPrescriptionId().getExtension());
        generator.recipient(dataModel.getPatient().getName().getFullName());
        generator.ordination(dataModel.getProduct().getName());

        return generator.WriteAllReturnBase64();
    }

    public EPrescriptionL1Generator(String resourceName) {
        //Load file from resources folder, based on the name input
        try {
            baseFile = new File(Objects.requireNonNull(EPrescriptionL1Generator.class.getResource("/" + resourceName)).getFile());
        } catch (NullPointerException e) {
            throw new RuntimeException("Could not find resource: "+resourceName);
        }
    }

    public EPrescriptionL1Generator author(String author) {
        Author = author;
        return this;
    }

    public EPrescriptionL1Generator prescriptionId(String prescriptionId) {
        PrescriptionId = prescriptionId;
        return this;
    }
    public EPrescriptionL1Generator recipient(String recipient) {
        Recipient = recipient;
        return this;
    }
    public EPrescriptionL1Generator ordination(String ordination) {
        Ordination = ordination;
        return this;
    }

    private void InitializeDocument(){
        if(pdfDocument == null){
            try {
                pdfDocument = Loader.loadPDF(baseFile);
            } catch (IOException e) {
                throw new RuntimeException(String.format("Malformed of missing PDF file in %s",baseFile.getAbsolutePath()),e);
            }
        }
        if(pdfPage == null){
            pdfPage = pdfDocument.getPage(0);
        }
    }

    private void CloseDocument(){
        if(pdfDocument != null){
            try {
                pdfDocument.close();
            } catch (IOException e) {
                //If we can't close the document, just don't do anything
            }
        }
    }

    public EPrescriptionL1Generator WriteOrdination() {
        InitializeDocument();
        try {
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage);
            contentStream.beginText();

            contentStream.newLineAtOffset(60, 390);
            contentStream.showText(Ordination);
            contentStream.endText();
            contentStream.close();
        } catch (IOException e) {
            CloseDocument();
            throw new RuntimeException(String.format("Error writing ordination to %s",baseFile.getAbsolutePath()),e);
        }
        return this;
    }

    public EPrescriptionL1Generator WriteRecipient() {
        InitializeDocument();
        try {
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage);
            contentStream.beginText();

            contentStream.newLineAtOffset(50, 570);
            contentStream.showText(Recipient);
            contentStream.endText();
            contentStream.close();
        } catch (IOException e) {
            CloseDocument();
            throw new RuntimeException(String.format("Error writing recipient to %s",baseFile.getAbsolutePath()),e);
        }
        return this;
    }

    public EPrescriptionL1Generator WritePrescriptionId() {
        InitializeDocument();
        try {
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage);
            contentStream.beginText();

            contentStream.newLineAtOffset(410, 660);
            contentStream.showText(PrescriptionId);
            contentStream.endText();
            contentStream.close();
        } catch (IOException e) {
            CloseDocument();
            throw new RuntimeException(String.format("Error writing prescription ID to %s",baseFile.getAbsolutePath()),e);
        }
        return this;
    }

    public EPrescriptionL1Generator WriteAuthor() {
        InitializeDocument();
        try {
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage);
            contentStream.beginText();

            contentStream.newLineAtOffset(60, 730);
            contentStream.showText(Author);
            contentStream.endText();
            contentStream.close();
        } catch (IOException e) {
            CloseDocument();
            throw new RuntimeException(String.format("Error writing author to %s",baseFile.getAbsolutePath()),e);
        }
        return this;
    }

    public String WriteAllReturnBase64(){
        InitializeDocument();
        WriteOrdination();
        WriteRecipient();
        WritePrescriptionId();
        WriteAuthor();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            pdfDocument.save(baos);
        } catch (IOException e) {
            CloseDocument();
            throw new RuntimeException("Could not generate PDF document",e);
        }
        CloseDocument();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }





}
