package com.github.nelson54.dominion.web.dto;

public class AuthenticationDto {

    private String token;
    private AccountDto account;

    public AuthenticationDto(String token, AccountDto account) {
        this.token = token;
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }
}
