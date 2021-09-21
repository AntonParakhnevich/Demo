package com.example.demo.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.warn("Caught exception while handling a request: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Something went wrong. Try it again later.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return createResponseEntity(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
        log.warn("Caught exception while handling a request: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return createResponseEntity(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException ex) {
        log.warn("Caught exception while handling a request: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return createResponseEntity(errorResponse);
    }

    private ResponseEntity<ErrorResponse> createResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

}
