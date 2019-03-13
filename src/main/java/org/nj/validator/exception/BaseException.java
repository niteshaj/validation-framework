package org.nj.validator.exception;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.nj.validator.enums.error.ErrorCode;

@Getter
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;

    private final List<String> properties = new LinkedList<>();

    BaseException() {
        super();
    }

    BaseException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    BaseException(ErrorCode errorCode, String message) {
        super(StringUtils.join(errorCode.getCode(), " - ", message));
        this.errorCode = errorCode;
    }

    public List<String> getProperties() {
        return properties;
    }

    public BaseException add(String value) {
        properties.add(value);
        return this;
    }
}
