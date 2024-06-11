package dk.sds.ncp.cda.model;

import lombok.Data;

import java.util.function.Supplier;

@Data
public class PdfField {
    private Integer xCoordinate;
    private Integer yCoordinate;
    private Supplier<String> content;

    public PdfField(Integer xCoordinate, Integer yCoordinate, Supplier<String> content) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.content = content;
    }
}
