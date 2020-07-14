package com.github.nelson54.dominion.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data @Getter @Setter @Builder
@NoArgsConstructor
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

    @Field
    private List<GrantedAuthority> authorities;

    public UserEntity(String id, @NotNull String username, @NotNull String password, Boolean enabled, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

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