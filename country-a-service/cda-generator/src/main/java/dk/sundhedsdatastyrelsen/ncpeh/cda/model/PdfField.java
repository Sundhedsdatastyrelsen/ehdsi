package dk.sundhedsdatastyrelsen.ncpeh.cda.model;
import java.util.List;
/**
* A text field to be printed on the ePrescription PDF.
*
* @param x          starting coordinate
* @param y          starting coordinate
* @param lines      list of text lines
* @param wrapLength maximum line length
*/
public record PdfField(
long x,
long y,
List<String> lines,
int wrapLength
) {
}
