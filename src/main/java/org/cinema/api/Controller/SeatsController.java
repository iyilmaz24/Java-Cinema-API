package org.cinema.api.Controller;

import org.cinema.api.Model.Room;
import org.cinema.api.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatsController {

    CinemaService cinemaService;

    @Autowired
    public SeatsController (CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("")
    public ResponseEntity<Room> seats() {
        return new ResponseEntity<>(cinemaService.getCinemaRoom(), HttpStatus.OK);
    }

}
