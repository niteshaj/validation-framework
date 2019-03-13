package org.nj.validator.enums.error;

public enum ValidationCode implements ErrorCode {
    VALUE_NULL("E2001", "NotNull", "field is required."),
    VALUE_REQUIRED("E2001", "NotEmpty", "field is required."),
    INVALID_FORMAT("E2002", "Pattern", "Invalid format."),
    VALUE_BETWEEN("E2003", "Size", "Size out of range"),
    VALUE_TOO_SHORT("E2004", "Min", "Value is to short"),
    VALUE_TOO_LONG("E2005", "Max", "Value is to long"),
    INVALID_DATE("E2006", "Date", "Date could not be parsed."),
    LESS_THEN_SYSTEM_DATE ("E2007", "SystemDate", "Date should be less than system date."),
    FUTURE_DATE ("E2008", "FutureDate", "Date should be future date."),
    INVALID_DATE_TIME("E2009", "Datetime", "Datetime could not be parsed."),
    INVALID_EMAIL_FORMAT("E2010", "Email", "Invalid Email format."),
    INVALID_VALUE ("E2011", "Value", "Please provide appropriate value."),
    INVALID_NUMBER_FORMAT("E2012", "Number", "Invalid number format."),
    INVALID_DECIMAL_FORMAT("E2013", "Decimal", "Invalid decimal number."),
    INVALID_FIELD_ID ("E2014", "FieldId", "Field id does't exist.");


    private final String code;
    private final String type;
    private final String description;

    ValidationCode(String number, String type, String description) {
        this.code = number;
        this.type = type;
        this.description =description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static ValidationCode lookup(String code) {
        for (ValidationCode validation : ValidationCode.values()) {
            if (validation.code.equalsIgnoreCase(code)) {
                return validation;
            }
        }
        return null;
    }

    public static ValidationCode lookupType(String type) {
        for (ValidationCode validation : ValidationCode.values()) {
            if (validation.type.equalsIgnoreCase(type)) {
                return validation;
            }
        }
        return null;
    }
}
