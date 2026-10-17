package com.dynamicbank.webapp.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min=6)
    private String password;

    @NotBlank
    @Size(min=6)
    private String confirmPassword;

    @AssertTrue(message = "Password and Confirm password must match")
    public boolean isPasswordMatching(){
        if(password == null || confirmPassword == null){
            return false;
        }
        return password.equals(confirmPassword);
    }

}
