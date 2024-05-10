package org.cinema.api.Controller;

import org.cinema.api.DTO.StatsDTO;
import org.cinema.api.Exception.IncorrectPasswordException;
import org.cinema.api.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/stats")
public class StatsController {

    CinemaService cinemaService;

    @Autowired
    public StatsController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("")
    public ResponseEntity<StatsDTO> stats(@RequestParam(required = false) String password) { // self-handle exceptions
        if(password == null) throw new IncorrectPasswordException("The password is wrong!");

        Optional<StatsDTO> stats = this.cinemaService.getStats(password);
        if (stats.isEmpty()) {
            throw new IncorrectPasswordException("The password is wrong!");
        }
        return new ResponseEntity<>(stats.get(), HttpStatus.OK);
    }

}
