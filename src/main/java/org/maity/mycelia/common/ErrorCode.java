package org.maity.mycelia.common;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INTERNAL_ERROR("MYC_EMS_OO1", "An internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND("MYC_EMS_OO2", "User not found", HttpStatus.NOT_FOUND),
    INVALID_CREDENTIALS("MYC_EMS_OO3", "User Name or Password Does Not Match!", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("MYC_EMS_OO4", "Invalid Token!", HttpStatus.BAD_REQUEST)
    ;


    private final String code;
    private final String errorMsg;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String errorMsg, HttpStatus httpStatus) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.httpStatus = httpStatus;
    }

    public String code() {
        return code;
    }

    public String errorMsg() {
        return errorMsg;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }
}
