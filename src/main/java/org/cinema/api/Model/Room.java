package org.cinema.api.Model;

import org.cinema.api.Exception.IncorrectTokenException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.*;

@JsonPropertyOrder( {"rows", "columns", "seats"} )
public class Room {

    @JsonIgnore private int id;
    private Seat[][] seats;

    @JsonProperty("rows") private int rowCount;
    @JsonProperty("columns") private int colCount;

    @JsonProperty("seats")
    public List<Seat> seats1dArray() { // 1D representation of 2D matrix for use in HTTP JSON response
        List<Seat> seats1d = new ArrayList<>(rowCount * colCount);
        Arrays.stream(seats).toList().forEach(
                list -> seats1d.addAll(Arrays.asList(list))
        );
        return seats1d;
    };

    public Room(int id, int row, int col) {
        this.id = id; this.rowCount = row; this.colCount = col;
        seats = new Seat[rowCount][colCount];

        for (int i = 0; i < row; i++) {
            int price = i <= 3 ? 10 : 8; // Rows <= 4 have price of $10, all others have price of $8
            for (int j = 0; j < col; j++) {
                seats[i][j] = new Seat(i + 1, j + 1, price); // start counting at 1
            }
        }
    }

    public void assignSeat(String customerName, int row, int column) {
        seats[row-1][column-1].setCustomerName(customerName);
    }

    public Seat getSeat(int row, int col) {
        return seats[row-1][col-1];
    }

    public int getId() {
        return id;
    }
    public int getRowCount() {
        return rowCount;
    }
    public int getColCount() {
        return colCount;
    }

    public Optional<Seat> findByToken(UUID token) {
        return Arrays.stream(seats).flatMap(Arrays::stream)
                .filter(seat -> (seat.isPurchased() &&
                        seat.getToken().equals(token))).findFirst();
    }

    public void resetSeat(Seat seat) {
        seat.setPurchased(false);
        seat.setToken(null);
        seat.setCustomerName(null);
    }

}
