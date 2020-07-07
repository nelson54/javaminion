package com.github.nelson54.dominion.user.account;

import com.github.nelson54.dominion.user.UserEntity;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Document("account")
public class AccountEntity {

    @Id
    private String id;

    @Field(name = "is_ai")
    private Boolean ai;

    // TODO: add annotation details for column
    @NotNull
    @Field(name = "first_name")//, length = 100, unique = true, nullable = false)
    private String firstname;

    // TODO: add annotation details for column
    @NotNull
    @Field(name = "email")//, length = 100, unique = true, nullable = false)
    private String email;

    @Field(name = "user")
    private UserEntity user;

    @Field(name = "elo")
    @NotNull
    private Long elo = 1000L;

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

    public String getId() {
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
