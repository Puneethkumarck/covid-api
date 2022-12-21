package com.paris.covidapi.exception;


public class CountryNotFoundException extends RuntimeException {

    /**
     * Constructor for CountryNotFoundException
     * @param message String
     */
    public CountryNotFoundException(String message) {
        super(message);
    }
}
