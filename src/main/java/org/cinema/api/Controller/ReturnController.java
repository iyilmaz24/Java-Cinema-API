package org.cinema.api.Controller;

import org.cinema.api.DTO.TicketDTO;
import org.cinema.api.Exception.IncorrectTokenException;
import org.cinema.api.Model.Seat;
import org.cinema.api.Model.Token;
import org.cinema.api.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Optional<Seat> foundSeat = cinemaService.findByToken(token.getToken());
        if (!foundSeat.isPresent()) { // if Optional<Seat> has no value inside it
            throw new IncorrectTokenException("Wrong token!");
        }

        TicketDTO ticket = new TicketDTO(foundSeat.get());
        cinemaService.resetSeat(foundSeat.get());
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

}
