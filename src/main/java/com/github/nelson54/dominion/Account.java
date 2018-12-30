package com.github.nelson54.dominion;

import org.springframework.security.core.userdetails.User;

public class Account {

    private Long id;
    private User user;
    private String firstname;
    private Boolean ai;


    public Account(Long id, User user, String firstname, Boolean ai) {
        this.id = id;
        this.user = user;
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
}
