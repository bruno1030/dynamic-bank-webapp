package com.dynamicbank.webapp.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.security.Principal;
import java.util.UUID;

/**
 * Usuário autenticado na webapp. Fica guardado na sessão (e, mais adiante, no Redis),
 * por isso é Serializable. Carrega o JWT emitido pelo AccountService.
 */
@Getter
@AllArgsConstructor
public class AuthenticatedUser implements Principal, Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final String fullName;
    private final String username;
    private final String token;

    @Override
    public String getName() {
        return username;
    }
}