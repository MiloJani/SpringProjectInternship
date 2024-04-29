package com.example.demo.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value
            = RecordAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse
    handleCustomerAlreadyExistsException(
            RecordAlreadyExists ex)
    {
        return new ErrorResponse(HttpStatus.CONFLICT.value(),
                ex.getMessage());
    }

    @ExceptionHandler(value
            = RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse
    handleException(RecordNotFoundException ex)
    {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}

