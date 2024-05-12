package org.cinema.api.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Token {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public Token() {;}

    public String getToken() {
        return token;
    }

}