package org.binchoo.study.spring.resttemplate.movieservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@ComponentScan(basePackages = "org.binchoo.study.spring.resttemplate.movieservice")
@Configuration
public class ClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(messageConverter()));
        return restTemplate;
    }

    @Bean
    public MappingJackson2HttpMessageConverter messageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(jodaModule());
        return objectMapper;
    }

    @Bean
    public JodaModule jodaModule() {
        return new JodaModule();
    }
}
