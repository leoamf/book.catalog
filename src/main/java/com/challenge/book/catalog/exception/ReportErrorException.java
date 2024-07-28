package com.challenge.book.catalog.exception;

import org.springframework.http.HttpStatus;

public class ReportErrorException extends ApiException {
    public ReportErrorException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY,
                String.format("Houve um erro no processamento do relatorio."));
    }
}
