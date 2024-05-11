package org.cinema.api.Controller;

import org.cinema.api.Exception.IncorrectTokenException;
import org.cinema.api.Model.Seat;
import org.cinema.api.Model.Token;
import org.cinema.api.DTO.TicketDTO;
import org.cinema.api.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/return")
public class ReturnController {

    CinemaService cinemaService;

    @Autowired
    public ReturnController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping("")
    public ResponseEntity<TicketDTO> getReturn(@RequestBody Token token) {
        Seat foundSeat = cinemaService.findByToken(token.getTokenString());
        TicketDTO ticket = new TicketDTO(foundSeat);

        cinemaService.refundSeatByToken(foundSeat.getToken());
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

}
