package dk.sds.ncp.cda.model;

import lombok.Data;

@Data
public class PdfField {
    private Integer xCoordinate;
    private Integer yCoordinate;
    private String[] content;

    public PdfField(Integer xCoordinate, Integer yCoordinate, String[] content) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.content = content;
    }
}
