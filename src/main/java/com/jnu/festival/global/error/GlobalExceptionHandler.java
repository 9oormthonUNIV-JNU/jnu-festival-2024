package com.jnu.festival.global.error;

import com.jnu.festival.global.util.ResponseDto;
import com.jnu.festival.global.error.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    // Convertor 에서 바인딩 실패시 발생하는 예외
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ResponseDto<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail(e));
    }

    // 지원되지 않는 미디어 타입을 사용할 때 발생하는 예외
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ResponseDto<?>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail(e));
    }

    // 지원되지 않는 HTTP 메소드를 사용할 때 발생하는 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<ResponseDto<?>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.fail(e));
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ResponseDto<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.fail(e));
    }

    // Validation 에서 검증 실패시 발생하는 예외
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseDto<?>> handleArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail(e));
    }

//    @ExceptionHandler(value = {ConstraintViolationException.class})
//    public ResponseEntity<ResponseDto<?>> handleConstraintViolationException(ConstraintViolationException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail(e));
//    }

//    @ExceptionHandler(value = {UnexpectedTypeException.class})
//    public ResponseEntity<ResponseDto<?>> handleUnexpectedTypeException(UnexpectedTypeException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail(e));
//    }

    // 메소드의 인자 타입이 일치하지 않을 때 발생하는 예외
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ResponseDto<?>> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.fail(e));
    }

    // 필수 파라미터가 누락되었을 때 발생하는 예외
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ResponseDto<?>> handleArgumentNotValidException(MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail(e));
    }

    // 개발자가 직접 정의한 예외
    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ResponseDto<?>> handleApiException(BusinessException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fail(e));
    }

    // 서버, DB 예외
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseDto<?>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.fail(new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)));
    }
}