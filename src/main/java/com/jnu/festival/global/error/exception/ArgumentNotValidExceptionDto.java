package com.jnu.festival.global.error.exception;

import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.ExceptionDto;
import lombok.Getter;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ArgumentNotValidExceptionDto extends ExceptionDto {
    private final Map<String, String> errorFields;

    public ArgumentNotValidExceptionDto(final MethodArgumentNotValidException methodArgumentNotValidException) {
        super(ErrorCode.INVALID_ARGUMENT);

        this.errorFields = new HashMap<>();
        methodArgumentNotValidException.getBindingResult()
                .getAllErrors().forEach(e -> this.errorFields.put(((FieldError) e).getField(), e.getDefaultMessage()));
    }

//    public ArgumentNotValidExceptionDto(final ConstraintViolationException constraintViolationException) {
//        super(ErrorCode.INVALID_ARGUMENT);
//
//        this.errorFields = new HashMap<>();
//
//        for (ConstraintViolation<?> constraintViolation : constraintViolationException.getConstraintViolations()) {
//            errorFields.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
//        }
//    }
}
