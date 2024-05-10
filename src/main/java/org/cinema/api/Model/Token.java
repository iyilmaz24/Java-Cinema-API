package org.cinema.api.Model;

import java.util.UUID;

public class Token {

    private UUID token;

    public Token(UUID token) {
        this.token = token;
    }

    public Token() {;}

    public UUID getToken() {
        return token;
    }

}