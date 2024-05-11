package org.cinema.api.Service;

import org.cinema.api.DAO.RoomSeatDAO;
import org.cinema.api.DTO.StatsDTO;
import org.cinema.api.DTO.TicketDTO;
import org.cinema.api.Model.Room;
import org.cinema.api.Model.Seat;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class CinemaService {

    private final int ROWS = 9; private final int COLUMNS = 9;
    private final int ROOM_ID = 1;
//    Room cinema_room;

    RoomSeatDAO cinema_room;

    public CinemaService() {
        RoomSeatDAO cinema_room = new RoomSeatDAO();
//        this.availableSeats = cinema_room.getTotalSeats();
    }

//    public Room getCinemaRoom() {
//        return cinema_room;
//    }

    public Seat getSeat(int row, int col) {
//        if(row > cinema_room.getRowCount() || col > cinema_room.getColCount()) {
//            throw new InvalidIndexException("The number of a row or a column is out of bounds!");
//        }
        return cinema_room.getSeatByRowColumn(row, col);
    }

    public TicketDTO setPurchased(Seat seat) {
        UUID newToken = randomUUID();
        seat.setToken(newToken);
        seat.setPurchased();
        this.income += seat.getPrice(); this.soldSeats += 1; this.availableSeats -= 1;
        return new TicketDTO(seat, newToken); // calls the 2 arg purchase-specific constructor
    }

    public Optional<Seat> findByToken(UUID token) {
        return cinema_room.findByToken(token);
    }

    public void resetSeat(Seat seat) {
        this.income -= seat.getPrice(); this.soldSeats -= 1; this.availableSeats += 1;
        cinema_room.resetSeat(seat);
    }

    public Room getRoomInfo() {
        return cinema_room.getRoomInfo();
    }

    public StatsDTO getStats(String password) {
        return cinema_room.getStatistics(password);
    }

}
