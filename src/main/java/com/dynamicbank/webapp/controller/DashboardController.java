package com.dynamicbank.webapp.controller;

import com.dynamicbank.webapp.security.AuthenticatedUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal AuthenticatedUser user, Model model) {
        model.addAttribute("fullName", user.getFullName());
        return "dashboard";
    }
}