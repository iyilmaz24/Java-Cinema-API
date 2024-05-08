package org.cinema.api.Controller;

import org.cinema.api.Exception.AlreadyPurchasedException;
import org.cinema.api.Exception.InvalidIndexException;
import org.cinema.api.Model.Seat;
import org.cinema.api.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private CinemaService cinemaService;

    @Autowired
    public PurchaseController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping("")
    public ResponseEntity<Seat> purchase(@RequestBody Seat seat) {
        if(seat.getRow() < 0 || seat.getColumn() < 0) {
            throw new InvalidIndexException("The number of a row or a column is out of bounds!");
        }
        Seat currentSeat = cinemaService.getSeat(seat.getRow(), seat.getColumn());

        if (currentSeat.isPurchased()) {
            throw new AlreadyPurchasedException("The ticket has been already purchased!");
        }

        currentSeat.setPurchased();
        return new ResponseEntity<>(currentSeat, HttpStatus.OK);
    }

}
