package com.paris.covidapi.web.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paris.covidapi.service.CovidService;
import com.paris.covidapi.util.AbstractTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(Covid19Controller.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class Covid19ControllerTest extends AbstractTest {

    @MockBean
    private CovidService covidService;

    @InjectMocks
    private Covid19Controller covid19Controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getStatistics() throws Exception {

        Optional<ObjectNode> objectNode = convertJson(getStatistics);

        //when
        when(covidService.getStatistics()).thenReturn(objectNode);

        //then
        mockMvc.perform(get("/statistics").
                        header("X-RapidAPI-Key", "39a9671bfcmsheeef10465b47017p1cd613jsn6a44aeca0c79").
                        header("X-RapidAPI-Host", "covid-193.p.rapidapi.com").
                        contentType(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.response").isNotEmpty()).
                andExpect(jsonPath("$.results").isNotEmpty()).
                andDo(document("getStatistics", requestHeaders(
                                headerWithName("X-RapidAPI-Key").description("API Key for Covid API"),
                                headerWithName("X-RapidAPI-Host").description("Host for Covid API"))));

        verify(covidService).getStatistics();
    }

   // @Test
    void getHistory() throws Exception {
        Optional<ObjectNode> getHistoryJson = convertJson(getHistory);

        //when
        when(covidService.getHistory(anyString(),anyString())).thenReturn(getHistoryJson);

        //then
        mockMvc.perform(get("/history").
                        param("country", "France").
                        param("day", "2020-04-01").
                        header("X-RapidAPI-Key", "39a9671bfcmsheeef10465b47017p1cd613jsn6a44aeca0c79").
                        header("X-RapidAPI-Host", "covid-193.p.rapidapi.com").
                        contentType(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.response").isNotEmpty()).
                andDo(document("getHistory", requestHeaders(
                        headerWithName("X-RapidAPI-Key").description("API Key for Covid API"),
                        headerWithName("X-RapidAPI-Host").description("Host for Covid API"))));

        verify(covidService).getHistory(anyString(),anyString());

    }
}