package com.dynamicbank.webapp.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUserDto {

    @NotBlank
    @Size(max = 150)
    private String fullName;

    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9._-]+$",
            message = "Use apenas letras, números, ponto, hífen e underscore")
    private String username;

    @NotNull
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @NotBlank
    @Size(min = 8, max = 72)
    private String password;

    @NotBlank
    private String confirmPassword;

    @AssertTrue(message = "Password and Confirm password must match")
    public boolean isPasswordMatching() {
        if (password == null || confirmPassword == null) {
            return true; // campo vazio já é tratado pelo @NotBlank
        }
        return password.equals(confirmPassword);
    }

}