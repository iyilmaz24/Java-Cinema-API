package org.cinema.api.Model;


public class Token {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    private Token () {;} // for use by spring-boot

    public String getToken() {
        return token;
    }

}
