package com.dynamicbank.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransferMoneyController {

    @GetMapping("/transfer-money")
    public String getTransferMoneyPage(){
        return "transfer-money";
    }

}
