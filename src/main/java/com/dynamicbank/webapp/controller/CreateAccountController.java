package com.dynamicbank.webapp.controller;

import com.dynamicbank.webapp.client.AccountServiceException;
import com.dynamicbank.webapp.dto.CreateUserDto;
import com.dynamicbank.webapp.service.AccountRegistrationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class CreateAccountController {

    private final AccountRegistrationService registrationService;

    public CreateAccountController(AccountRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // Metodo que abre a tela de criação de conta
    @GetMapping("/create-account")
    public String getCreateAccountPage(Model model) {
        model.addAttribute("createUserDto", new CreateUserDto());
        return "create-account";
    }

    // Metodo chamado ao dar submit na tela de criar conta
    @PostMapping("/create-account")
    public String postCreateAccount(@Valid CreateUserDto createUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-account";
        }

        try {
            registrationService.register(createUserDto);
        } catch (AccountServiceException e) {
            log.warn("Falha ao criar conta no AccountService (status={})", e.getStatus(), e);
            bindingResult.reject("account.service.error", messageFor(e));
            return "create-account";
        }

        // Post/Redirect/Get. O login automático (JWT na sessão) entra na próxima etapa.
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