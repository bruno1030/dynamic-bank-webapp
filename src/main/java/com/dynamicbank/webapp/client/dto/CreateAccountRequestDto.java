package com.dynamicbank.webapp.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/** Corpo enviado ao POST /accounts/create do AccountService (sem confirmPassword). */
@Getter
@AllArgsConstructor
public class CreateAccountRequestDto {

    String fullName;

    String username;

    String email;

    LocalDate birthDate;

    String password;

}
