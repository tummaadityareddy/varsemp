package com.vars.varsemp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VarsController {

    @GetMapping("/welcome")
    public String welcomeMessage(){
        return "Welcome to VARS employee portal";
    }

}
