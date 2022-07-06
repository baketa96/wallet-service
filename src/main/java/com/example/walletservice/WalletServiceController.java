package com.example.walletservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletServiceController {

    @GetMapping("/")
    public String helloWorld(){
        return "Hello World";
    }


}
