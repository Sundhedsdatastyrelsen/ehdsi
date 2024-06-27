package dk.sds.ncp.cda.model;

import lombok.Value;

@Value
public class PdfField {
    Integer xCoordinate;
    Integer yCoordinate;
    String[] content;

    public PdfField(Integer xCoordinate, Integer yCoordinate, String[] content) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.content = content;
    }
}
