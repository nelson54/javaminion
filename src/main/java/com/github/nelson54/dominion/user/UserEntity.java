package com.github.nelson54.dominion.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Objects;

public class UserEntity {

    @Id
    private String id;

    @NotNull
    //@Column(name = "username", length = 100, unique = true, updatable = false)
    @Field(name="username")
    private String username;

    @JsonIgnore
    @NotNull
    //@Column(name = "password", length = 60, updatable = false)
    @Field
    private String password;

    @Field
    private Boolean enabled;

    /**
     * Creates a UserEntity that can be saved to the database.
     * @param user
     * @return
     */
    public static UserEntity ofUser(User user) {

        if (user != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.username = user.getUsername();
            userEntity.password = user.getPassword();
            return userEntity;
        }

        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public User asUser() {
        return new User(username, "", new ArrayList());
    }

    public UserEntity fromUser(User user) {
        UserEntity ue = new UserEntity();
        ue.setUsername(user.getUsername());
        return ue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEntity user = (UserEntity) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "User{ username='" + username + "'}";
    }
}