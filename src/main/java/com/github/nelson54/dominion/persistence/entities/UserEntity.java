package com.github.nelson54.dominion.persistence.entities;

import com.github.nelson54.dominion.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="user")
public class UserEntity {
    @Id
    @Column(name="user_id")
    private String id;
    private String name;
    private String password;
    private Boolean isAi;

    public UserEntity() {
    }

    private UserEntity(String id, String name, boolean isAi) {
        this.id = id;
        this.name = name;
        this.isAi = isAi;
    }

    static UserEntity ofUser(User user) {
        return new UserEntity(user.getId(), user.getName(), user.isAi());
    }

    User asUser() {
        return new User(id, name);
    }

    public String getId() {
        return id;
    }

    public Boolean isAi() {
        return isAi;
    }
}
