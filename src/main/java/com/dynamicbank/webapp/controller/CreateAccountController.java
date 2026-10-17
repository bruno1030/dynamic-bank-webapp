package com.dynamicbank.webapp.controller;

import com.dynamicbank.webapp.dto.CreateUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateAccountController {

    @GetMapping("/create-account")
    public String getCreateAccountPage(Model model){
        model.addAttribute("createUserDto", new CreateUserDto());
        return "create-account";
    }

    @PostMapping("/create-account")
    public String postCreateAccount(CreateUserDto createUserDto){
        return "account-created";
    }

}
