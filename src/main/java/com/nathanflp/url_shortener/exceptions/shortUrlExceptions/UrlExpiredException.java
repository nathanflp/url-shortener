package com.nathanflp.url_shortener.exceptions.shortUrlExceptions;

public class UrlExpiredException extends RuntimeException {

    public UrlExpiredException() {
        super("The URL has expired.");
    }

    public UrlExpiredException(String message) {
        super(message);
    }
}
