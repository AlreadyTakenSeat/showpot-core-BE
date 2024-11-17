package org.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    @Bean
    public Builder restClientBuilder() {
        return RestClient.builder();
    }
}
