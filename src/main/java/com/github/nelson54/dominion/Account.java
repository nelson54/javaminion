package com.github.nelson54.dominion;

import org.springframework.security.core.userdetails.User;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id) &&
                email.equals(account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
