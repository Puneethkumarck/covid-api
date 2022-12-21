package com.paris.covidapi.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CovidFeignErrorDecoder implements ErrorDecoder {


    private ErrorDecoder errorDecoder = new Default();

    private static final String DETAIL = "message";

    @Override
    public Exception decode(String s, Response response) {

        String errorMessage = this.getErrorMessage(response);

        return new CovidException(errorMessage, HttpStatus.resolve(response.status()));
    }

    private String getErrorMessage(Response response) {
        String defaultErrorMessage = "service provider is not available";
        try {
            String responseString = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8.name());
            Map<String, String> errorMap = new ObjectMapper().readValue(responseString, new TypeReference<>() {});
            if (!errorMap.containsKey(DETAIL)) {
                return defaultErrorMessage;
            }
            return errorMap.get(DETAIL);

        } catch (IOException ex) {
            return defaultErrorMessage;
        }
    }
}
