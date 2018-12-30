package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name="account")
public class AccountEntity {

    public AccountEntity() {}

    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private Boolean ai;

    @NotNull
    @Column(name = "first_name", length = 100, unique = true, nullable = false)
    private String firstname;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn
    private UserEntity user;

    public AccountEntity(Boolean ai, @NotNull String firstname, UserEntity user) {
        this.ai = ai;
        this.firstname = firstname;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public Boolean isAi() {
        return ai;
    }

    public Account asAccount() {
        return new Account(id, user.asUser(), firstname, ai);
    }

}
