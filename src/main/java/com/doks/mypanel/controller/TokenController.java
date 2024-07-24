package com.doks.mypanel.controller;

import com.doks.mypanel.service.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TokenController {

    private final AuthenticationService authenticationService;


    public TokenController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/oauth/token")
    public String authtenticate (Authentication authentication) {
        return  authenticationService.authenticate(authentication);
    }

}
