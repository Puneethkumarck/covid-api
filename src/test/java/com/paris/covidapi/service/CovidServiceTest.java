package com.paris.covidapi.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paris.covidapi.fiegnclient.CovidClient;
import com.paris.covidapi.util.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {CovidService.class})
class CovidServiceTest extends AbstractTest {

    @Autowired
    private CovidService covidService;

    @MockBean
    private CovidClient covidClient;

    @Test
    void getStatistics() throws Exception {
        Optional<ObjectNode> objectNode = convertJson(getStatistics);

        when(covidClient.findCovidStatistics()).thenReturn(objectNode);

        Object response = covidService.getStatistics();

        verify(covidClient).findCovidStatistics();

        Assertions.assertNotNull(response);
    }

    @Test
    void getHistory() throws Exception {
        Optional<ObjectNode> countryListObject = convertJson(getCountries);

        when(covidClient.getCountryList()).thenReturn(countryListObject);

        when(covidClient.findHistory(anyString(),anyString())).thenReturn(convertJson(getHistory));

        Object response = covidService.getHistory("Tunisia","2022-06-18");

        verify(covidClient).findHistory(anyString(),anyString());

        verify(covidClient).getCountryList();

        Assertions.assertNotNull(response);
    }
}