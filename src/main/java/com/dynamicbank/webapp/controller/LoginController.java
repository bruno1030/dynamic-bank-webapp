package com.dynamicbank.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/logged-page")
    public String getLoggedPage() {
        return "logged-page";
    }

}
