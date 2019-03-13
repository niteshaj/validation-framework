package org.nj.validator.exception;


import java.util.Map;
import lombok.Getter;
import org.nj.validator.enums.error.ValidationCode;

@Getter
public class ValidationException extends BaseException {

    private Map<String, ValidationCode> validationError;

    public ValidationException(ValidationCode errorCode) { super(errorCode, errorCode.getDescription()); }

    public ValidationException(Map<String, ValidationCode> validationError) { this.validationError = validationError; }
}