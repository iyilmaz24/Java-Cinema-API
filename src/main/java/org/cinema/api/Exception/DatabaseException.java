package org.cinema.api.Exception;

import java.sql.SQLException;

public class DatabaseException extends SQLException {

    public DatabaseException(String message) {
        super(message);
    }

}
