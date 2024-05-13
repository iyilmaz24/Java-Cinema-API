package org.cinema.api.Service;

import org.cinema.api.DAO.RoomSeatDAO;
import org.cinema.api.DTO.StatsDTO;
import org.cinema.api.DTO.TicketDTO;
import org.cinema.api.Model.Room;
import org.cinema.api.Model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import static java.util.UUID.randomUUID;


@Service
public class CinemaService {

    RoomSeatDAO cinema_room;

    @Autowired
    public CinemaService(RoomSeatDAO cinema_room) {
        this.cinema_room = cinema_room;
    }

    public Seat getSeat(int row, int col) {
        return cinema_room.getSeatByRowColumn(row, col);
    }

    public TicketDTO setPurchased(Seat seat) {
        UUID newToken = randomUUID();
        seat.setToken(String.valueOf(newToken)); seat.setPurchased(true);

        cinema_room.sellSeatByRowColumn(seat.getCustomerFirstName(), String.valueOf(newToken), seat.getRow(), seat.getColumn());
        return new TicketDTO(seat, newToken); // calls the 2 arg purchase-specific constructor
    }

    public Seat findByToken(String token) {
        return cinema_room.getSeatByToken(token);
    }

    public void refundSeatByToken(String token) {
        cinema_room.refundSeatByToken(token);
    }

    public Room getRoomInfo() {
        return cinema_room.getRoomInfo();
    }

    public StatsDTO getStats(String password) {
        return cinema_room.getStatistics(password);
    }

}
