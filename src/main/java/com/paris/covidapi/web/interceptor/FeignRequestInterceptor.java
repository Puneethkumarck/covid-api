package com.paris.covidapi.web.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("Content-Type", "application/json");
        template.header("Accept", "application/json");
        template.header("X-RapidAPI-Key", "39a9671bfcmsheeef10465b47017p1cd613jsn6a44aeca0c79");
        template.header("X-RapidAPI-Host", "covid-193.p.rapidapi.com");
        log.info("FeignRequestInterceptor: url: {}  headers:{} body: {}", template.url(), template.headers(), template.body());
    }
}
