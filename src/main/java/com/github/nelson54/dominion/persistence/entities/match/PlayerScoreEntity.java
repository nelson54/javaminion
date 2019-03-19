package com.github.nelson54.dominion.persistence.entities.match;

import com.github.nelson54.dominion.persistence.entities.AccountEntity;

import javax.persistence.*;

@Entity
@Table(name = "card_type_reference")
public class PlayerScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private AccountEntity account;

    @Column
    private Long score;

    public PlayerScoreEntity() {}

    public PlayerScoreEntity(AccountEntity account, Long score) {
        this.account = account;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
