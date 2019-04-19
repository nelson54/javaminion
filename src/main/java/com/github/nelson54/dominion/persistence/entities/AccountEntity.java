package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.Account;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
@DynamicInsert
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_ai", updatable = false)
    private Boolean ai;

    @NotNull
    @Column(name = "first_name", length = 100, unique = true, nullable = false)
    private String firstname;

    @NotNull
    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn
    private UserEntity user;

    @Column(name = "elo")
    @NotNull
    @ColumnDefault(value = "1000")
    private Long elo;

    public AccountEntity() {}

    public AccountEntity(
            Boolean ai,
            @NotNull String firstname,
            @NotNull String email,
            UserEntity user) {
        this.ai = ai;
        this.firstname = firstname;
        this.user = user;
        this.email = email;
    }

    public static AccountEntity ofAccount(Account account) {
        AccountEntity accountEntity = new AccountEntity(
                account.getAi(),
                account.getFirstname(),
                account.getEmail(),
                null);

        accountEntity.id = account.getId();
        accountEntity.user = UserEntity.ofUser(account.getUser());
        accountEntity.elo = account.getElo();

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
        Account account = new Account(id, user.asUser(), email, firstname, ai);
        account.setElo(elo);
        return account;
    }

    public Long getElo() {
        return elo;
    }

    public void setElo(Long elo) {
        this.elo = elo;
    }
}
