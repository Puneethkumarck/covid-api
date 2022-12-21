package com.paris.covidapi.config;

import com.paris.covidapi.exception.CovidFeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfiguration {

    @Bean
    public CovidFeignErrorDecoder feignErrorDecoder() {
        return new CovidFeignErrorDecoder();
    }

}
