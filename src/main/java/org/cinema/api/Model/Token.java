package org.cinema.api.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Token {

    private UUID token;

    public Token(UUID token) {
        this.token = token;
    }

    public Token() {;}

    public UUID getTokenUUID() {
        return token;
    }

    @JsonIgnore public String getTokenString() {
        return token.toString();
    }

}