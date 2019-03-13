package org.nj.validator.enums.error;

public enum ApplicationCode implements ErrorCode{
    UNHANDLED_EXCEPTION("E5000", "Error occurred while processing your request. Please try after some time or contact admin."),

    INVALID_LOGIN_NAME("E5014", "User with login name does't exist."),
    INVALID_USER_ID("E5015", "User with login id does't exist."),
    INACTIVE_USER("E5016", "Your are not able to login because your account is inactive. Please contact admin."),
    ;

    private final String code;
    private final String description;

    ApplicationCode(String number, String description) {
        this.code = number;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
