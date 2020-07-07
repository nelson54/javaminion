package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.user.account.AccountEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document
public class PlayerScoreEntity {
    @Id
    private String id;

    @DBRef
    private AccountEntity account;

    private Long score;

    public PlayerScoreEntity() {}

    public PlayerScoreEntity(AccountEntity account, Long score) {
        this.account = account;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
