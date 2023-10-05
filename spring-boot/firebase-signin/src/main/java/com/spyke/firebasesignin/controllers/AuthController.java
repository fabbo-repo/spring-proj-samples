package com.spyke.firebasesignin.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping(path = "/check")
    public String check(Principal principal) {
        return principal.getName();
    }
}
