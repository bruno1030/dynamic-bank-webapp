package com.dynamicbank.webapp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(AccountServiceProperties.class)
public class RestClientConfig {

    // A API key não é cabeçalho padrão: só as chamadas ao "/create" e ao "/login" a enviam.
    @Bean
    public RestClient accountServiceRestClient(RestClient.Builder builder,
                                               AccountServiceProperties properties) {
        return builder
                .baseUrl(properties.getBaseUrl())
                .build();
    }
}
