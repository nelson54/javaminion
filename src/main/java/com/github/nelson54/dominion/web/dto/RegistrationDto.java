package com.github.nelson54.dominion.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegistrationDto {

    @NotBlank
    @Size(min = 1, max = 100)
    private String username;

    @NotBlank
    @Size(min = 1, max = 100)
    private String firstname;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 6, max = 60)
    private String password;


    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}