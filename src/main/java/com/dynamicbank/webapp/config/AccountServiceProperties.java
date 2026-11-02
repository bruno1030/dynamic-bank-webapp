package com.dynamicbank.webapp.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuração de acesso ao AccountService, lida do application.properties:
 * account-service.base-url  -> URL base do AccountService
 * account-service.api-key   -> a webapp-key (mesmo valor de security.api-keys.webapp no AccountService)
 */

@Validated
@ConfigurationProperties(prefix = "account-service")
@Getter
@Setter
public class AccountServiceProperties {

    @NotBlank
    private String baseUrl;

    @NotBlank
    private String apiKey;

}
