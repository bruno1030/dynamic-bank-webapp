package com.dynamicbank.webapp.client.dto;


import lombok.Getter;

@Getter
public class AuthResponseDto {

    String token;
    UserResponseDto user;

}
