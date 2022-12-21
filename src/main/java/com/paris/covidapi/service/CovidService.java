package com.paris.covidapi.service;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paris.covidapi.exception.CountryNotFoundException;
import com.paris.covidapi.fiegnclient.CovidClient;
import com.paris.covidapi.util.JsonNodeUtility;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CovidService {

    private CovidClient covidClient;

    public CovidService(CovidClient covidClient) {
        this.covidClient = covidClient;
    }

    public Optional<ObjectNode> getStatistics(){
        return covidClient.findCovidStatistics();
    }

    public Object getHistory(String country, String day){
        validCountry(country);
        return covidClient.findHistory(country, day);
    }


    /**
     * Validate the country name.
     * @param country String
     * @return boolean
     */
    private boolean validCountry(String country){
        Optional<ObjectNode> objectNode = covidClient.getCountryList();
          if(objectNode.isPresent()){
                ObjectNode node = objectNode.get();
                List<String> countries = JsonNodeUtility.convertToList((ArrayNode) node.get("response"));
                if(!countries.contains(country)){
                    throw new CountryNotFoundException("Invalid country");
                }
            }
        return true;
    }

}
