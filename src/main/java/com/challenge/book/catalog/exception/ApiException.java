package com.challenge.book.catalog.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final HttpStatus code;
    private final String message;

    ApiException(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpStatus code() {
        return code;
    }

    public String message() {
        return message;
    }
}
