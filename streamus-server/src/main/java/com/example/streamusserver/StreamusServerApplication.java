package com.example.streamusserver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = OAuth2ClientAutoConfiguration.class)
public class StreamusServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamusServerApplication.class, args);
    }
//    @Bean
//    public ObjectMapper objectMapper() {
//        return JsonMapper.builder()
//                .enable(SerializationFeature.INDENT_OUTPUT)
//                .serializationInclusion(JsonInclude.Include.NON_NULL)
//                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
//                .build();
//    }

}
