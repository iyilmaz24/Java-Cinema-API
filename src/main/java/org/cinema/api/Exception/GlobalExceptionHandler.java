package org.cinema.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AlreadyPurchasedException.class, InvalidIndexException.class})
    public ResponseEntity<ErrorObject> handleAlreadyPurchasedException(RuntimeException ex) {

        ErrorObject error = new ErrorObject(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
