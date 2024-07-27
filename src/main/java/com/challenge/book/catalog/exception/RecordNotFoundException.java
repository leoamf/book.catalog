package com.challenge.book.catalog.exception;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends ApiException {
    public RecordNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, String.format("Não existe registro com o codigo: %s", id));
    }
}
