package org.nj.validator.exception;


import org.nj.validator.enums.error.ErrorCode;

class AuthenticationException extends BaseException {
    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
