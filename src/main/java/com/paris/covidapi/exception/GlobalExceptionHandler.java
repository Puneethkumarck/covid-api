package com.paris.covidapi.exception;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.concurrent.TimeoutException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handle(ConstraintViolationException e) {
        log.error("ConstraintViolationException" ,e);
        ValidationErrorResponse error = new ValidationErrorResponse();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations ) {
            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }

    @ExceptionHandler(CovidException.class)
    @ResponseBody
    ResponseEntity<ErrorResponse> resultNotFound(CovidException e) {
        log.error("exception occurred" ,e);
        return  new ResponseEntity<>(ErrorResponse.of(e.getMessage()),e.getHttpStatus());
    }

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseBody
    ResponseEntity<ErrorResponse> countryNotFound(CountryNotFoundException e) {
        log.error("exception occurred" ,e);
        return  new ResponseEntity<>(ErrorResponse.of(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestNotPermitted.class)
    @ResponseBody
    ResponseEntity<ErrorResponse>  rateLimiterException(RequestNotPermitted e) {
        log.error("Ratelimter exception" ,e);
        return  new ResponseEntity<>(ErrorResponse.of(e.getMessage()), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    @ResponseBody
    ResponseEntity<ErrorResponse>  callNotPermitedException(CallNotPermittedException e) {
        log.error("Callnot permited exception" ,e);
        return  new ResponseEntity<>(ErrorResponse.of(e.getMessage()),HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
    }

    @ExceptionHandler(TimeoutException.class)
    @ResponseBody
    ResponseEntity<ErrorResponse>  timeOutException(TimeoutException e) {
        log.error("Timeout exception" ,e);
        return  new ResponseEntity<>(ErrorResponse.of(e.getMessage()),HttpStatus.REQUEST_TIMEOUT);
    }
}
