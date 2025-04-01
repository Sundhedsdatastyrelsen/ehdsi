package dk.sundhedsdatastyrelsen.ncpeh.errorhandling;
import dk.sundhedsdatastyrelsen.ncpeh.ncp.api.ErrorDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
@Slf4j
@Order(1)
@RestControllerAdvice
public class SpringExceptionHandler {
 @ExceptionHandler(AccessDeniedException.class)
 public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException e) {
 return internalError(HttpStatus.FORBIDDEN, e);
 }
  @ExceptionHandler(MissingServletRequestPartException.class)
 public ResponseEntity<ErrorDto> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
 return internalError(HttpStatus.BAD_REQUEST, e);
 }
  @ExceptionHandler(MissingServletRequestParameterException.class)
 public ResponseEntity<ErrorDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
 return internalError(HttpStatus.BAD_REQUEST, e);
 }
  @ExceptionHandler(ConstraintViolationException.class)
 public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException e) {
 return internalError(HttpStatus.BAD_REQUEST, e);
 }
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
 public ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
 return internalError(HttpStatus.BAD_REQUEST, e);
 }
  private ResponseEntity<ErrorDto> internalError(HttpStatus httpStatus, Exception e) {
 var details = new ErrorDto(
 httpStatus.name(), e.getMessage() != null ? e.getMessage() : e.getClass()
 .getSimpleName());
  if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
  log.error("{} - Returning {}", e.getClass().getSimpleName(), httpStatus, e);
 } else if (httpStatus.is5xxServerError()) {
  log.warn("{} - Returning {}", e.getClass().getSimpleName(), httpStatus);
 } else {
  log.info("{} - Returning {}", e.getClass().getSimpleName(), httpStatus);
 }
  return ResponseEntity.status(httpStatus).body(details);
 }
}
