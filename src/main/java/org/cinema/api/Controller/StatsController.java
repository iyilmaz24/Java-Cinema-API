package org.cinema.api.Controller;

import org.cinema.api.DTO.StatsDTO;
import org.cinema.api.Exception.IncorrectPasswordException;
import org.cinema.api.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/stats")
public class StatsController {

    CinemaService cinemaService;

    @Autowired
    public StatsController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping("")
    public ResponseEntity<StatsDTO> stats(@RequestBody String password) { // self-handle blank/null
        if(password == null || password.isBlank()) throw new IncorrectPasswordException("The password is not provided!");
        return new ResponseEntity<>(this.cinemaService.getStats(password), HttpStatus.OK);
    }

}
