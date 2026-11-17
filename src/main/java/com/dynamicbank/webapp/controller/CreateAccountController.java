package com.dynamicbank.webapp.controller;

import com.dynamicbank.webapp.client.AccountServiceException;
import com.dynamicbank.webapp.client.dto.AuthResponseDto;
import com.dynamicbank.webapp.dto.CreateUserDto;
import com.dynamicbank.webapp.security.LoginSessionManager;
import com.dynamicbank.webapp.service.AccountRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class CreateAccountController {

    private final AccountRegistrationService registrationService;
    private final LoginSessionManager loginSessionManager;

    public CreateAccountController(AccountRegistrationService registrationService,
                                   LoginSessionManager loginSessionManager) {
        this.registrationService = registrationService;
        this.loginSessionManager = loginSessionManager;
    }

    @GetMapping("/create-account")
    public String getCreateAccountPage(Model model) {
        // Se veio de um redirecionamento com erro, o formulário já está no model (flash).
        if (!model.containsAttribute("createUserDto")) {
            model.addAttribute("createUserDto", new CreateUserDto());
        }
        return "create-account";
    }

    // Metodo chamado ao dar submit na tela de criar conta
    @PostMapping("/create-account")
    public String postCreateAccount(@Valid CreateUserDto createUserDto,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "create-account";
        }

        AuthResponseDto authResponse;
        try {
            authResponse = registrationService.register(createUserDto);
        } catch (AccountServiceException e) {
            log.warn("Falha ao criar conta no AccountService (status={})", e.getStatus(), e);

            // As senhas não devem ficar guardadas na sessão, nem por um instante.
            createUserDto.setPassword(null);
            createUserDto.setConfirmPassword(null);

            redirectAttributes.addFlashAttribute("createUserDto", createUserDto);
            redirectAttributes.addFlashAttribute("errorMessage", messageFor(e));
            return "redirect:/create-account";
        }

        // Conta criada: o usuário já fica logado (JWT guardado na sessão).
        loginSessionManager.establish(authResponse, request, response);

        return "redirect:/dashboard";
    }

    private String messageFor(AccountServiceException e) {
        if (e.isUnavailable()) {
            return "Serviço de contas indisponível. Tente novamente em instantes.";
        }
        return switch (e.getStatus()) {
            case 409 -> "Email ou username já cadastrado.";
            case 400 -> "Dados inválidos. Confira os campos e tente novamente.";
            default -> "Não foi possível criar a conta. Tente novamente.";
        };
    }
}
