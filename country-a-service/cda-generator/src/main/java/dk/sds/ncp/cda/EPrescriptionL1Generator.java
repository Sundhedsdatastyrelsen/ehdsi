package dk.sds.ncp.cda;

import dk.sds.ncp.cda.model.EPrescriptionL3;
import freemarker.template.TemplateException;
import lombok.Setter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
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
        EPrescriptionL1Generator generator = new EPrescriptionL1Generator("pdfTemplates/SDS_ProtoRecept.pdf");

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
            WriteTextAtCoordinates(Ordination,60,390,pdfDocument,pdfPage);
        } catch (IOException e) {
            CloseDocument();
            throw new RuntimeException(String.format("Error writing ordination to %s",baseFile.getAbsolutePath()),e);
        }
        return this;
    }

    public EPrescriptionL1Generator WriteRecipient() {
        InitializeDocument();
        try {
            WriteTextAtCoordinates(Recipient,50,570,pdfDocument,pdfPage);
        } catch (IOException e) {
            CloseDocument();
            throw new RuntimeException(String.format("Error writing recipient to %s",baseFile.getAbsolutePath()),e);
        }
        return this;
    }

    public EPrescriptionL1Generator WritePrescriptionId() {
        InitializeDocument();
        try {
            WriteTextAtCoordinates(PrescriptionId, 410,660,pdfDocument,pdfPage);
        } catch (IOException e) {
            CloseDocument();
            throw new RuntimeException(String.format("Error writing prescription ID to %s",baseFile.getAbsolutePath()),e);
        }
        return this;
    }

    public EPrescriptionL1Generator WriteAuthor() {
        InitializeDocument();
        try {
            WriteTextAtCoordinates(Author,60,730,pdfDocument,pdfPage);
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

    private void WriteTextAtCoordinates(String Text, Integer xCoordinate, Integer yCoordinate, PDDocument pdfDocument, PDPage pdfPage) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage, PDPageContentStream.AppendMode.APPEND,true);
        contentStream.beginText();
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 14);
        contentStream.newLineAtOffset(xCoordinate, yCoordinate);
        contentStream.showText(Text);
        contentStream.endText();
        contentStream.close();
    }





}
