package org.cinema.api.Controller;

import org.cinema.api.Model.Seat;
import org.cinema.api.Model.Token;
import org.cinema.api.DTO.TicketDTO;
import org.cinema.api.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/return")
public class ReturnController {

    private final CinemaService cinemaService;

    @Autowired
    public ReturnController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping("")
    public ResponseEntity<TicketDTO> getReturn(@RequestBody Token token) {
        Seat foundSeat = cinemaService.findByToken(token.getToken());

        cinemaService.refundSeat(foundSeat);
        return new ResponseEntity<>(new TicketDTO(foundSeat), HttpStatus.OK);
    }

}
