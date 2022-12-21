package com.paris.covidapi.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Optional;

public abstract class AbstractTest {

    @Value("classpath:stubs/response/get_statistics.json")
    protected Resource getStatistics;

    @Value("classpath:stubs/response/get_countries.json")
    protected Resource getCountries;

    @Value("classpath:stubs/response/get_history.json")
    protected Resource getHistory;


    @InjectMocks
    private ObjectMapper objectMapper;

    protected Optional<ObjectNode> convertJson(Resource resource) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper.readValue(resource.getFile(),
                new TypeReference<>() {
                });
    }

}
