package org.nj.validator.response;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.nj.validator.enums.error.ApplicationCode;
import org.nj.validator.enums.error.ErrorCode;
import org.nj.validator.enums.error.ValidationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;

/**
 * Created by njawanj on 27/06/17.
 */
@Data
public final class Response<T> {
    private final static Logger LOGGER = LoggerFactory.getLogger(Response.class);

    private int count;

    //private String type;

    private T result;

    private List<ErrorResponse> error;

    private String path;

    private Response() {
    }

    private Response(ErrorResponse error, String path) {
        this.error = Collections.singletonList(error);
        this.path = path;
    }

    private Response(List<ErrorResponse> error, String path) {
        this.error = error;
        this.path = path;
    }

    public static <T> Response createResponse(final T model) {
        Response<T> response = new Response<>();
        if (model instanceof Collection) {
            response.count = ((Collection) model).size();
        } else if (model instanceof Map) {
            response.count = ((Map) model).size();
        } else {
            response.count = 1;
        }
        response.result = model;
        return response;
    }

    public static Response createCountResponse(final int count) {
        Response response = new Response<>();
        response.count = count;
        return response;
    }

    public static Response createErrorResponse(String message, String detailMessage, String path) {
        ErrorCode code = ApplicationCode.UNHANDLED_EXCEPTION;
        String msg = LocalizationHelper.getLocalizedMessage(code, new Object());
        Response response = new Response<>(new ErrorResponse(code.getCode(),
                msg, null, StringUtils.join(message, " - ",detailMessage)), path);
        LOGGER.error("Error Response :: {}", response);
        return response;
    }

    public static Response createErrorResponse(final ErrorCode errorCode, String detailMessage, Object[] params, String path) {
        String message = LocalizationHelper.getLocalizedMessage(errorCode, params);
        Response response = new Response<>(new ErrorResponse(errorCode.getCode(), message, Collections.singletonList(params), detailMessage),
                path);
        LOGGER.error("Error Response :: {}", response);
        return response;
    }

    public static Response createErrorResponse(final Map<String, ValidationCode> validationError, String path) {
        List<ErrorResponse> errorResponses = validationError.keySet()
                .parallelStream()
                .map(key -> {
                    ValidationCode validation = validationError.get(key);
                    String message = LocalizationHelper.getLocalizedMessage(validation, key);
                    return new ErrorResponse(validation.getCode(), message, Collections.singletonList(key), null);
                })
                .collect(Collectors.toList());

        Response response = new Response<>(errorResponses, path);
        LOGGER.error("Error Response :: {}", response);
        return response;
    }

    public static Response createErrorResponse(final List<FieldError> fieldErrors, String path) {
        List<ErrorResponse> errorResponses = fieldErrors.parallelStream()
                .map(error -> {
                    //Get validation error code, if we have given message with specific error code
                    //eg. @NotBlank(message="E1000")
                    ValidationCode validation = ValidationCode.lookup(error.getDefaultMessage());

                    if (validation == null) {
                        //Get validation error code based on type of validation @NotBlank, @Min, @Max etc.
                        //FieldError getCode() return type of annotation
                        validation = ValidationCode.lookupType(error.getCode());
                    }
                    String message = LocalizationHelper.getLocalizedMessage(validation, error.getArguments());
                    List<Object> param = new ArrayList<>();
                    param.add(error.getField());
                    Arrays.asList(error.getArguments())
                            .forEach(value -> {
                                if(value instanceof Integer || value instanceof Long) {
                                    param.add(value);
                                }
                            });
                    return new ErrorResponse(validation.getCode(), message, param, null);
                })
                .collect(Collectors.toList());

        Response response = new Response<>(errorResponses, path);
        LOGGER.error("Error Response :: {}", response);
        return response;
    }
}