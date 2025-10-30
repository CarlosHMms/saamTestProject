package com.saamaudit.saamTestProject.exceptions.entities;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ExceptionPayload {
    private final String message; // Mensagem de erro
    private final HttpStatus httpStatus;// Status HTTP associado ao erro
    private final ZonedDateTime timeStamp; // Momento em que o erro ocorreu

    public ExceptionPayload(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }
}