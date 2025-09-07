package org.maity.mycelia.common;

import lombok.Getter;

import java.util.Optional;

public class CustomException extends RuntimeException {
    @Getter
    private final ErrorCode errorCode;
    @Getter
    private Optional<String> customMessage = Optional.empty();


    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String customMessage) {
        this.errorCode = errorCode;
        this.customMessage = customMessage.describeConstable();
    }
}
