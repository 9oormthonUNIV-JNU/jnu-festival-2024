package com.jnu.festival.global.error;

import lombok.Getter;

@Getter
public class ExceptionDto {
//    private HttpStatus status;
    private final String message;

//    private ExceptionResponseDto(final ErrorCode errorCode) {
//        this.status = errorCode.getStatus();
//        this.message = errorCode.getMessage();
//    }
//
//    public ExceptionResponseDto(final ErrorCode errorCode, final String message) {
//        this.status = errorCode.getStatus();
//        this.message = errorCode.getMessage();
//    }

    public ExceptionDto(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }

//    public static ExceptionResponseDto of(final ErrorCode errorCode, final String message) {
//        return new ExceptionResponseDto(errorCode, message);
//    }
}
