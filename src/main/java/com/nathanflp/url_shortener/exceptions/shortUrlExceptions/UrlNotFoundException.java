package com.nathanflp.url_shortener.exceptions.shortUrlExceptions;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException() {
    }

    public UrlNotFoundException(String message) {
        super(message);
    }
}
