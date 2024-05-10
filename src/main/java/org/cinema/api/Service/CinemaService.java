package org.cinema.api.Service;

import org.cinema.api.DTO.TicketDTO;
import org.cinema.api.Exception.InvalidIndexException;
import org.cinema.api.Model.Room;
import org.cinema.api.Model.Seat;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class CinemaService {

    Room cinema_room;

    public CinemaService() {
        cinema_room = new Room(0, 9, 9);
    }

    public Room getCinemaRoom() {
        return cinema_room;
    }

    public Seat getSeat(int row, int col) {
        if(row > cinema_room.getRowCount() || col > cinema_room.getColCount()) {
            throw new InvalidIndexException("The number of a row or a column is out of bounds!");
        }
        return cinema_room.getSeat(row, col);
    }

    public TicketDTO setPurchased(Seat seat) {
        UUID newToken = randomUUID();
        seat.setToken(newToken);
        seat.setPurchased();
        return new TicketDTO(seat, newToken); // calls the 2 arg purchase-specific constructor
    }

    public Optional<Seat> findByToken(UUID token) {
        return cinema_room.findByToken(token);
    }

    public void resetSeat(Seat seat) {
        cinema_room.resetSeat(seat);
    }

}
