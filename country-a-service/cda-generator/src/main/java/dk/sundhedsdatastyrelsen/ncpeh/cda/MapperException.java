package dk.sundhedsdatastyrelsen.ncpeh.cda;
public class MapperException extends Exception {
 public MapperException(String message) {
 super(message);
 }
  public MapperException(String message, Exception cause) {
 super(message, cause);
 }
}
