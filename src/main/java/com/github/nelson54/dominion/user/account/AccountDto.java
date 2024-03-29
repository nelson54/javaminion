package com.github.nelson54.dominion.user.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AccountDto {

    @JsonProperty
    private String id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String username;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 2, max = 60)
    @JsonIgnore
    private String firstname;

    @NotBlank
    @Size(min = 6, max = 60)
    @JsonIgnore
    private String password;

    public static AccountDto fromAccount(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setFirstname(account.getFirstname());
        accountDto.setUsername(account.getUser().getUsername());

        return accountDto;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}