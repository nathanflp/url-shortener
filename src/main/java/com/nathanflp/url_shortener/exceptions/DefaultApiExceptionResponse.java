package com.nathanflp.url_shortener.exceptions;

import com.nathanflp.url_shortener.utils.*;
import org.springframework.http.*;

import java.time.*;

public class DefaultApiExceptionResponse {

    private final String message;
    private final HttpStatus status;
    private final String timestamp;

    public DefaultApiExceptionResponse(String message, HttpStatus status, Instant timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = DateFormatter.formatInstant(timestamp);
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
