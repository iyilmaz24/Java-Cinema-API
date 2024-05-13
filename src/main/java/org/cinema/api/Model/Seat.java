package org.cinema.api.Model;

import com.fasterxml.jackson.annotation.*;


@JsonPropertyOrder( {"row", "column", "price", "purchased", "customerFirstName", "token"} )
public class Seat {

    private int row;
    private int column;
    private int price;
    private boolean purchased;
    @JsonIgnore private String customerFirstName;
    @JsonIgnore private String token;

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public Seat(int row, int column, int price, boolean purchased) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.purchased = purchased;
    }

    public Seat(int row, int column, int price, boolean purchased, String firstName, String token) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.purchased = purchased;
        this.customerFirstName = firstName;
        this.token = token;
    }

    private Seat() {;} // for use by spring-boot

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public int getPrice() {
        return price;
    }

    public boolean isPurchased() {
        return purchased;
    }
    public void setPurchased(boolean status) {
        this.purchased = status;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

}
