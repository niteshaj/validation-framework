package org.nj.validator.config;


import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.nj.validator.enums.error.SystemCode;
import org.nj.validator.enums.error.ValidationCode;
import org.nj.validator.exception.ApplicationException;
import org.nj.validator.exception.ValidationException;
import org.nj.validator.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException e, WebRequest request) {
    Response response = Response.createErrorResponse(SystemCode.ILLEGAL_STATE, e.getMessage(),
        null, request.getDescription(false));
    log.error("Illegal Argument Exception :: ", e);
    return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(value = ApplicationException.class)
  protected ResponseEntity<Object> handleApplicationException(ApplicationException e,
      WebRequest request) {
    Response response = Response.createErrorResponse(e.getErrorCode(), null,
        e.getProperties().toArray(), request.getDescription(false));
    log.error("Application exception :: ", e);
    return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = ValidationException.class)
  protected ResponseEntity<Object> handleValidationException(ValidationException e,
      WebRequest request) {
    Response response = Response.createErrorResponse(e.getValidationError(), null);
    return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    Response response = null;
    if (ex.getCause() != null && ex.getCause().getCause() instanceof DateTimeParseException) {
      String dateValue = ((DateTimeParseException) ex.getCause().getCause()).getParsedString();
      response = Response
          .createErrorResponse(ValidationCode.INVALID_DATE, null, new String[]{dateValue}
              , request.getDescription(false));
    }
    log.error("Error while parsing request :: ", ex);
    return handleExceptionInternal(ex, response, headers, status, request);
  }

  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    BindingResult result = e.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    Response response = Response.createErrorResponse(fieldErrors, null);
    log.error("Error while validating request :: ", e);
    return handleExceptionInternal(e, response, headers, status, request);
  }
}
