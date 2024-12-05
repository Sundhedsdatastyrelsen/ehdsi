package dk.sds.ncp.cda.model;

import java.util.List;

public record PdfField(
    long x,
    long y,
    String text,
    int wrapLength
) {
    public static PdfField ofLines(long x, long y, List<String> lines, int wrapLength) {
        return new PdfField(x, y, String.join("\n", lines), wrapLength);
    }
}
