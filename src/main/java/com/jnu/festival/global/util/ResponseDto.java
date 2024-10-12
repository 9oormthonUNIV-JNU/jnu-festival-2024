package com.jnu.festival.global.util;

import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.ExceptionDto;
import com.jnu.festival.global.error.exception.ArgumentNotValidExceptionDto;
import com.jnu.festival.global.error.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private Boolean success;
    private T data;
    private ExceptionDto error;

    public static <T> ResponseDto<T> ok(final T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> created(final T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static ResponseDto<Object> fail(final HttpMessageNotReadableException e) {
        return new ResponseDto<>(false, null, ExceptionDto.of(ErrorCode.BAD_REQUEST_JSON));
    }

    public static ResponseDto<Object> fail(final HttpMediaTypeNotSupportedException e) {
        return new ResponseDto<>(false, null, ExceptionDto.of(ErrorCode.UNSUPPORTED_MEDIA_TYPE));
    }

    public static ResponseDto<Object> fail(final NoHandlerFoundException e) {
        return new ResponseDto<>(false, null, ExceptionDto.of(ErrorCode.UNSUPPORTED_MEDIA_TYPE));
    }

    public static ResponseDto<Object> fail(final HttpRequestMethodNotSupportedException e) {
        return new ResponseDto<>(false, null, ExceptionDto.of(ErrorCode.UNSUPPORTED_MEDIA_TYPE));
    }

    public static ResponseDto<Object> fail(final MethodArgumentNotValidException e) {
        return new ResponseDto<>(false, null, new ArgumentNotValidExceptionDto(e));
    }

//    public static ResponseDto<Object> fail(final ConstraintViolationException e) {
//        return new ResponseDto<>(HttpStatus.BAD_REQUEST, false, null, new ArgumentNotValidExceptionDto(e));
//    }
//
//    public static ResponseDto<Object> fail(final UnexpectedTypeException e) {
//        return new ResponseDto<>(HttpStatus.BAD_REQUEST, false, null, ExceptionDto.of(ErrorCode.INVALID_PARAMETER_FORMAT));
//    }

    public static ResponseDto<Object> fail(final MissingServletRequestParameterException e) {
        return new ResponseDto<>(false, null, ExceptionDto.of(ErrorCode.MISSING_REQUEST_PARAMETER));
    }

    public static ResponseDto<Object> fail(final MethodArgumentTypeMismatchException e) {
        return new ResponseDto<>(false, null, ExceptionDto.of(ErrorCode.INVALID_PARAMETER_FORMAT));
    }

    public static ResponseDto<Object> fail(final BusinessException e) {
        return new ResponseDto<>(false, null, ExceptionDto.of(e.getErrorCode()));
    }
}
