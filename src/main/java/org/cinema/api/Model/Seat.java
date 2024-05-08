package org.cinema.api.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( {"row", "column", "price"} )
public class Seat {

    private int row;
    private int column;
    private int price;
    @JsonIgnore private boolean purchased;
    @JsonIgnore private String customerName;

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.purchased = false;
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

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
