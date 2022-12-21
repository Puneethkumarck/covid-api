package com.paris.covidapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CovidException extends RuntimeException {

    @Getter
    private final HttpStatus httpStatus;


    /**
     * Constructor for CovidException
     * @param message String
     * @param httpStatus HttpStatus
     */
    public CovidException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
