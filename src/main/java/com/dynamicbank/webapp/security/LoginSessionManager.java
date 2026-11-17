package com.dynamicbank.webapp.security;

import com.dynamicbank.webapp.client.dto.AuthResponseDto;
import com.dynamicbank.webapp.client.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Estabelece a sessão autenticada a partir da resposta do AccountService
 * (usado depois do cadastro e, na próxima etapa, depois do login).
 */
@Component
public class LoginSessionManager {

    private static final String ROLE_CLIENT = "ROLE_CLIENT";

    private final SecurityContextRepository securityContextRepository;

    public LoginSessionManager(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    public void establish(AuthResponseDto authResponse,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        UserResponseDto user = authResponse.getUser();

        AuthenticatedUser principal = new AuthenticatedUser(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                authResponse.getToken());

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                principal, null, List.of(new SimpleGrantedAuthority(ROLE_CLIENT)));

        // Proteção contra fixação de sessão: troca o id de uma sessão já existente.
        if (request.getSession(false) != null) {
            request.changeSessionId();
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // Sem este passo, o login se perderia na próxima requisição.
        securityContextRepository.saveContext(context, request, response);
    }
}