package com.dynamicbank.webapp.client;

import com.dynamicbank.webapp.client.dto.AuthResponseDto;
import com.dynamicbank.webapp.client.dto.CreateAccountRequestDto;
import com.dynamicbank.webapp.config.AccountServiceProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class AccountServiceClient {

    private static final String API_KEY_HEADER = "X-API-Key";

    private final RestClient restClient;
    private final AccountServiceProperties properties;

    public AccountServiceClient(RestClient restClient, AccountServiceProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    public AuthResponseDto createAccount(CreateAccountRequestDto request) {
        try {
            return restClient.post()
                    .uri("/accounts/create")
                    .header(API_KEY_HEADER, properties.getApiKey())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(AuthResponseDto.class);
        } catch (RestClientResponseException e) {
            // O AccountService respondeu com 4xx ou 5xx.
            int status = e.getStatusCode().value();
            throw new AccountServiceException("AccountService respondeu " + status, status, e);
        } catch (ResourceAccessException e) {
            // Conexão recusada, timeout etc.
            throw new AccountServiceException("AccountService indisponível", null, e);
        }
    }
}