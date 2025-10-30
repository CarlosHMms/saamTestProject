package com.saamaudit.saamTestProject.exceptions.entities;

import com.saamaudit.saamTestProject.exceptions.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(NotFoundException e){
        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        ExceptionPayload exceptionPayload = new ExceptionPayload(
                e.getMessage(),
                statusCode,
                ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))

        );
        return new ResponseEntity<>(exceptionPayload, statusCode);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialsExceptionHandler(BadCredentialsException e){
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        ExceptionPayload exceptionPayload = new ExceptionPayload(
                e.getMessage(),
                statusCode,
                ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
        );
        return new ResponseEntity<>(exceptionPayload, statusCode);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestExceptionHandler(BadRequestException e){
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        ExceptionPayload exceptionPayload = new ExceptionPayload(
                e.getMessage(),
                statusCode,
                ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
        );
        return new ResponseEntity<>(exceptionPayload, statusCode);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedExceptionHandler(AccessDeniedException e){
        HttpStatus statusCode = HttpStatus.UNAUTHORIZED;
        ExceptionPayload exceptionPayload = new ExceptionPayload(
                e.getMessage(),
                statusCode,
                ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
        );
        return new ResponseEntity<>(exceptionPayload, statusCode);
    }
}