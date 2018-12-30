package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.web.dto.AccountCredentialsDto;
import com.github.nelson54.dominion.web.dto.AuthenticationDto;
import com.github.nelson54.dominion.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/authentication")
    public AuthenticationDto authentication(@RequestBody @Valid AccountCredentialsDto accountCredentials) {
        try {
            return accountService.authenticateWithCredentials(accountCredentials);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public Account register(@RequestBody @Valid UserDto userDto) {
        return accountService.createAccount(userDto);
    }
}
