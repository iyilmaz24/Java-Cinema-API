package org.cinema.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AlreadyPurchasedException.class, InvalidIndexException.class,
            IncorrectTokenException.class, NoResultsFoundException.class})
    public ResponseEntity<ErrorObject> handleRuntimeExceptions(RuntimeException ex) {

        ErrorObject error = new ErrorObject(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IncorrectPasswordException.class})
    public ResponseEntity<ErrorObject> handlePasswordExceptions(RuntimeException ex) {

        ErrorObject error = new ErrorObject(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler( {MethodNotAllowedException.class} )
    public ResponseEntity<ErrorObject> handleMethodNotAllowedExceptions(HttpRequestMethodNotSupportedException ex) {

        ErrorObject error = new ErrorObject(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
