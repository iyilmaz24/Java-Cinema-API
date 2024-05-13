package org.cinema.api.Model;

import org.cinema.api.DTO.StatsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;


@JsonPropertyOrder( {"totalSeats", "availableSeats", "seats"} )
public class Room {

    @JsonIgnore private int id;
    @JsonProperty("totalSeats") private int totalSeats;
    @JsonProperty("availableSeats") private int availableSeats;
    @JsonProperty("seats") private Seat[] seats;

    public Room(StatsDTO statsDTO, List<Seat> seats) {
        this.totalSeats = statsDTO.getTotalSeats();
        this.availableSeats = statsDTO.getAvailableSeats();
        this.seats = seats.toArray(new Seat[this.totalSeats]);
    }

    private Room() {;} // for use by spring-boot

    public int getId() {
        return id;
    }

}
