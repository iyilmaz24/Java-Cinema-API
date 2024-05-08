package org.cinema.api.Service;

import org.cinema.api.Exception.InvalidIndexException;
import org.cinema.api.Model.Room;
import org.cinema.api.Model.Seat;
import org.springframework.stereotype.Service;

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

}
