package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.persistence.AccountRepository;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.web.dto.AccountCredentialsDto;
import com.github.nelson54.dominion.web.dto.AccountDto;
import com.github.nelson54.dominion.web.dto.AuthenticationDto;
import com.github.nelson54.dominion.web.dto.RegistrationDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountRepository accountRepository;
    private AccountService accountService;

    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/authentication")
    public AuthenticationDto authentication(@RequestBody @Valid AccountCredentialsDto accountCredentials) {
        try {
            return accountService.authenticateWithCredentials(accountCredentials)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public AccountDto register(@RequestBody @Valid RegistrationDto registrationDto) {
        return accountService.createAccount(registrationDto).map(AccountDto::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

    }

    @GetMapping("/account")
    public AccountDto getUser() {
        return accountService
                .getAuthorizedAccount()
                .map(AccountDto::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
