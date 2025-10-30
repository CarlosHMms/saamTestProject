package com.saamaudit.saamTestProject.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
    public NotFoundException(){}
}
