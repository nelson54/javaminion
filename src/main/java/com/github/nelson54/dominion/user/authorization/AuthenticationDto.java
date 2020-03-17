package com.github.nelson54.dominion.user.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.dominion.user.account.AccountDto;

public class AuthenticationDto {

    @JsonProperty
    private String token;

    @JsonProperty
    private AccountDto account;

    public AuthenticationDto() {
    }

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
