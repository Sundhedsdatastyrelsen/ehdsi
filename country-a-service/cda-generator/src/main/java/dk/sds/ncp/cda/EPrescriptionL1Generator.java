package dk.sds.ncp.cda;

import dk.sds.ncp.cda.model.EPrescriptionL3;
import dk.sds.ncp.cda.model.PdfField;
import lombok.Getter;
import lombok.With;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class EPrescriptionL1Generator {
    //Configuration
    private static final Integer FONT_SIZE = 14;
    private static final String TEMPLATE = "pdfTemplates/Receptdesign.pdf"; //Load file from resources folder, based on the name input

    //Internal tracking fields for the generator
    private final List<PdfField> Fields;
    private final File baseFile;
    private PDDocument pdfDocument;
    private PDPage pdfPage;

    public EPrescriptionL1Generator(List<PdfField> fields) {
        try {
            baseFile = new File(Objects.requireNonNull(EPrescriptionL1Generator.class.getResource("/" + TEMPLATE)).getFile());
        } catch (NullPointerException e) {
            throw new RuntimeException("Could not find resource: "+ TEMPLATE);
        }

        Fields = fields;
    }

    public String generate() {
        return WriteAllReturnBase64();
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


    private EPrescriptionL1Generator WriteField(PdfField field) {
        InitializeDocument();
        try {
            WriteTextAtCoordinates(field.getContent(),field.getXCoordinate(),field.getYCoordinate(),pdfDocument,pdfPage);
        } catch (IOException e) {
            CloseDocument();
            throw new RuntimeException(String.format("Error writing author to %s",baseFile.getAbsolutePath()),e);
        }
        return this;
    }

    public String WriteAllReturnBase64(){
        InitializeDocument();
        for(PdfField field : Fields){
            WriteField(field);
        }
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

    private void WriteTextAtCoordinates(String[] Text, Integer xCoordinate, Integer yCoordinate, PDDocument pdfDocument, PDPage pdfPage) throws IOException {
        // For each line in the array, we print a new line, shifted one fontsize down
        for ( int lineNo = 0; lineNo < Text.length ; lineNo++) {
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.beginText();

            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), FONT_SIZE);
            contentStream.newLineAtOffset(xCoordinate, yCoordinate - FONT_SIZE * lineNo);
            contentStream.showText(Text[lineNo]);

            contentStream.endText();
            contentStream.close();
        }
    }





}
