package com.jnu.festival.global.error.exception;

import com.jnu.festival.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

//    public BusinessBaseException(String message, ErrorCode errorCode) {
//        super(message);
//        this.errorCode = errorCode;
//    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
