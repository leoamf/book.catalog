package com.challenge.book.catalog.exception;

import org.springframework.http.HttpStatus;

public class RecordReferenceFoundException extends ApiException {
    public RecordReferenceFoundException(String document, String id) {
        super(HttpStatus.UNPROCESSABLE_ENTITY,
                String.format("Existe um registro associado na tabela %s (id: %s)", document, id));
    }
}
