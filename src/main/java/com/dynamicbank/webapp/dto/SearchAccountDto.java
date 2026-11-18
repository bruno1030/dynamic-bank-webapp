package com.dynamicbank.webapp.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Busca da conta de destino: username (trecho) OU email (completo), nunca os dois.
 * Campos em branco devem chegar aqui como null (ver StringTrimmerEditor no controller).
 */
@Getter
@Setter
public class SearchAccountDto {

    @Size(min = 3, max = 50, message = "O username deve ter no mínimo 3 caracteres")
    private String username;

    @Email(message = "Informe um email válido")
    @Size(max = 255)
    private String email;

    @AssertTrue(message = "Pesquise por apenas um: username ou email")
    public boolean isExactlyOneFilled() {
        return hasText(username) != hasText(email);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}