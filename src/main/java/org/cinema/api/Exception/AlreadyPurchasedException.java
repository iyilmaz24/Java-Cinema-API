package org.cinema.api.Exception;

public class AlreadyPurchasedException extends RuntimeException {

    public AlreadyPurchasedException(String message) {
        super(message);
    }

}
