package com.paris.covidapi.fiegnclient;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paris.covidapi.config.FeignConfiguration;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name="covidapi",url = "${feign.clients.covidapi.url}", configuration = FeignConfiguration.class)
public interface CovidClient {

    @GetMapping(value="/statistics")
    Optional<ObjectNode> findCovidStatistics();


   @GetMapping(value="/history")
   Optional<ObjectNode> findHistory(@RequestParam("country") String country, @RequestParam("day") String day);


   @GetMapping(value="/countries")
   Optional<ObjectNode> getCountryList();
}
