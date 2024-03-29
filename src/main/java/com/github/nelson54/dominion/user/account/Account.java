package com.github.nelson54.dominion.user.account;

import org.springframework.security.core.userdetails.User;

import java.util.Objects;

public class Account {

    private String id;
    private User user;
    private String firstname;
    private String email;
    private Boolean ai;
    private Long elo;

    public Account(String id, User user, String email, String firstname, Boolean ai) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.firstname = firstname;
        this.ai = ai;
        this.elo = 1000L;
    }

    public Account(User user, String email, String firstname, Boolean ai) {
        this.user = user;
        this.email = email;
        this.firstname = firstname;
        this.ai = ai;
        this.elo = 1000L;
    }

    public Account(String id, User user, String email, String firstname, Boolean ai, Long elo) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.firstname = firstname;
        this.ai = ai;
        this.elo = elo;
    }

    public Account(User user, String email, String firstname, Boolean ai, Long elo) {
        this.user = user;
        this.email = email;
        this.firstname = firstname;
        this.ai = ai;
        this.elo = elo;
    }

    public String getId() {
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

    public Long getElo() {
        return elo;
    }

    public void setElo(Long elo) {
        this.elo = elo;
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
