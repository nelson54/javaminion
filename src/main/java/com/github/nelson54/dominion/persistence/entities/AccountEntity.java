package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.Account;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="accounts")
public class AccountEntity {

    public AccountEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="is_ai")
    private Boolean ai;

    @NotNull
    @Column(name = "first_name", length = 100, unique = true, nullable = false)
    private String firstname;

    @NotNull
    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @OneToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private UserEntity user;


    public AccountEntity(Boolean ai, @NotNull String firstname, @NotNull String email, UserEntity user) {
        this.ai = ai;
        this.firstname = firstname;
        this.user = user;
        this.email = email;
    }

    public static AccountEntity ofAccount(Account account) {
        AccountEntity accountEntity = new AccountEntity(account.getAi(), account.getFirstname(), account.getEmail(), null);
        accountEntity.id = account.getId();
        accountEntity.user = UserEntity.ofUser(account.getUser());
        return accountEntity;
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
        return new Account(id, user.asUser(), email, firstname, ai);
    }
}
