package com.nathanflp.url_shortener.exceptions;

import com.nathanflp.url_shortener.dtos.response.*;
import com.nathanflp.url_shortener.exceptions.shortUrlExceptions.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.time.*;

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

}
