package org.nj.validator.exception;


import org.nj.validator.enums.error.ErrorCode;

class UnauthorizedException extends BaseException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
