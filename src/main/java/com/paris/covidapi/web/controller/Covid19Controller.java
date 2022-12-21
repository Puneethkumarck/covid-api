package com.paris.covidapi.web.controller;


import com.paris.covidapi.service.CovidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;


@RestController
@Validated
public class Covid19Controller {

    private CovidService covidService;

    public Covid19Controller(CovidService covidService) {
        this.covidService = covidService;
    }

    @GetMapping(value = "/statistics", produces = "application/json")
    public ResponseEntity<Object> getStatistics() {
        return new ResponseEntity<>(covidService.getStatistics(), HttpStatus.OK);
    }

    @GetMapping(value = "/history", produces = "application/json")
    public ResponseEntity<Object> getHistory(@RequestParam("country") String country, @RequestParam("day")
   @Valid @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "invalid day format , please pass day format YYYY-MM-DD") String day) {
        return new ResponseEntity<>(covidService.getHistory(country,day), HttpStatus.OK);
    }
}
