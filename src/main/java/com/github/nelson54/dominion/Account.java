package com.github.nelson54.dominion;

import org.springframework.security.core.userdetails.User;

public class Account {

    private Long id;
    private User user;
    private String firstname;
    private String email;
    private Boolean ai;


    public Account(Long id, User user, String email, String firstname, Boolean ai) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.firstname = firstname;
        this.ai = ai;
    }

    public Account(User user, String email, String firstname, Boolean ai) {
        this.user = user;
        this.email = email;
        this.firstname = firstname;
        this.ai = ai;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getFirstname() {
        return firstname;
    }

    public Boolean getAi() {
        return ai;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return this.email;
    }
}
