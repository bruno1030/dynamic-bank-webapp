package com.dynamicbank.webapp.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    UUID id;
    String fullName;
    String username;
    String email;

}
