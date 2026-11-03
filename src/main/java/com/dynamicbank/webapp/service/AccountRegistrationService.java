package com.dynamicbank.webapp.service;

import com.dynamicbank.webapp.client.AccountServiceClient;
import com.dynamicbank.webapp.client.dto.AuthResponseDto;
import com.dynamicbank.webapp.client.dto.CreateAccountRequestDto;
import com.dynamicbank.webapp.dto.CreateUserDto;
import org.springframework.stereotype.Service;

@Service
public class AccountRegistrationService {

    private final AccountServiceClient accountServiceClient;

    public AccountRegistrationService(AccountServiceClient accountServiceClient) {
        this.accountServiceClient = accountServiceClient;
    }

    public AuthResponseDto register(CreateUserDto form) {
        CreateAccountRequestDto request = new CreateAccountRequestDto(
                form.getFullName(),
                form.getUsername(),
                form.getEmail(),
                form.getBirthDate(),
                form.getPassword());

        return accountServiceClient.createAccount(request);
    }
}
