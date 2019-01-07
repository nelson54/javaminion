package com.github.nelson54.dominion.persistence.entities.match;

import com.github.nelson54.dominion.persistence.entities.AccountEntity;

import javax.persistence.*;

@Entity
@Table(name="card_type_reference")
public class PlayerScoreEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade=CascadeType.ALL)
    private AccountEntity account;

    @Column
    private Integer score;

    @Column
    private Integer place;

    public PlayerScoreEntity() {}

    public PlayerScoreEntity(AccountEntity account, Integer score, Integer place) {
        this.account = account;
        this.score = score;
        this.place = place;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }
}
