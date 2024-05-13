package org.cinema.api.DTO;

import org.cinema.api.Model.Seat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;


@JsonPropertyOrder( {"token", "ticket" } )
public class TicketDTO {

    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private UUID token;

    @JsonProperty("ticket") private Seat ticket;

    public TicketDTO(Seat seat, UUID token) { // for use in purchase, to send assigned UUID
        this.ticket = new Seat(seat.getRow(), seat.getColumn(), seat.getPrice(), seat.isPurchased());
        this.token = token;
    }

    public TicketDTO(Seat seat) { // for use in return, sending of UUID not applicable
        this.ticket = new Seat(seat.getRow(), seat.getColumn(), seat.getPrice());
    }

}
