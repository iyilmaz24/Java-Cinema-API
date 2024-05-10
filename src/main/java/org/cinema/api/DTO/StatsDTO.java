package org.cinema.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"income", "available", "purchased"})
public class StatsDTO {

    @JsonProperty("income") private int income;
    @JsonProperty("available") private int availableSeats;
    @JsonProperty("purchased") int soldSeats;

    public StatsDTO(int income, int availableSeats, int soldSeats) {
        this.income = income;
        this.availableSeats = availableSeats;
        this.soldSeats = soldSeats;
    }

}
