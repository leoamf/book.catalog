package com.challenge.book.catalog.exception;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends ApiException {
    public RecordNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("There isn't a record with id: %s", id));
    }
}
