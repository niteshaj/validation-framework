package org.nj.validator.exception;

import org.nj.validator.enums.error.ErrorCode;

public class ApplicationException extends BaseException{
    public ApplicationException(ErrorCode errorCode) {
        super(errorCode, errorCode.getDescription());
    }
}
