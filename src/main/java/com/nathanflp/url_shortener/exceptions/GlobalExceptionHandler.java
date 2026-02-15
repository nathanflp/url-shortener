package com.nathanflp.url_shortener.exceptions;

import com.nathanflp.url_shortener.exceptions.shortUrlExceptions.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.time.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<DefaultApiExceptionResponse> urlNotFoundHandler(UrlNotFoundException exception) {
        DefaultApiExceptionResponse apiResponse = new DefaultApiExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND, Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

}
