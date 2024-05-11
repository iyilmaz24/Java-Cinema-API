package org.cinema.api.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"totalSeats", "availableSeats", "purchasedSeats", "totalRevenue"})
public class StatsDTO {

    @JsonProperty("totalSeats") int totalSeats;
    @JsonProperty("availableSeats") private int availableSeats;
    @JsonProperty("purchasedSeats") int purchasedSeats;
    @JsonProperty("totalRevenue") private int revenue;

    public StatsDTO(int totalSeats, int availableSeats, int purchasedSeats, int revenue) {
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.purchasedSeats = purchasedSeats;
        this.revenue = revenue;
    }

    public int getTotalSeats() {
        return totalSeats;
    }
    public int getAvailableSeats() {
        return availableSeats;
    }
    public int getPurchasedSeats() {
        return purchasedSeats;
    }
    public int getRevenue() {
        return revenue;
    }

}
