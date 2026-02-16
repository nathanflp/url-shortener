package com.nathanflp.url_shortener.exceptions;

import com.nathanflp.url_shortener.dtos.response.*;
import com.nathanflp.url_shortener.exceptions.shortUrlExceptions.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.time.*;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<?> urlNotFoundHandler(UrlNotFoundException exception) {
        DefaultApiExceptionResponse apiResponse = new DefaultApiExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND, Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(UrlExpiredException.class)
        public ResponseEntity<?> urlExpiredHandler(UrlExpiredException exception) {
            DefaultApiExceptionResponse apiResponse = new DefaultApiExceptionResponse(exception.getMessage(), HttpStatus.GONE, Instant.now());
            return ResponseEntity.status(HttpStatus.GONE).body(apiResponse);
        }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errorMessage = Optional.ofNullable(
                ex.getBindingResult()
                .getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("Invalid request");

        DefaultApiExceptionResponse apiResponse =
                new DefaultApiExceptionResponse(
                        errorMessage,
                        HttpStatus.BAD_REQUEST,
                        Instant.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

}
