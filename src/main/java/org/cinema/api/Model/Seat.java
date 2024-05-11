package org.cinema.api.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;

@JsonPropertyOrder( {"row", "column", "price"} )
public class Seat {

    private int row;
    private int column;
    private int price;
    @JsonIgnore private boolean purchased;
    @JsonIgnore private String customerName;
    @JsonIgnore private UUID token;

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
    }

    public Seat() {;}

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
    public void setPurchased() {
        this.purchased = true;
    }
    public void setPurchased(boolean status) {
        this.purchased = status;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
    public UUID getToken() {
        return token;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
