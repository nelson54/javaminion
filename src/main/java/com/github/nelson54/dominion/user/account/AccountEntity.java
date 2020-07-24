package com.github.nelson54.dominion.user.account;

import com.github.nelson54.dominion.user.UserEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Document("account")
@Data @NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class AccountEntity {

    @Id
    private String id;

    @Field(name = "is_ai")
    private Boolean ai;

    @NotNull
    @Field(name = "first_name")
    private String firstname;

    // TODO: add annotation details for column
    @NotNull
    @Field(name = "email")
    @Indexed(unique=true)
    private String email;

    @Field(name = "user")
    private UserEntity user;

    @Field(name = "elo")
    @NotNull
    private Long elo;


    public AccountEntity(
            Boolean ai,
            @NotNull String firstname,
            @NotNull String email,
            UserEntity user) {
        this.ai = ai;
        this.firstname = firstname;
        this.user = user;
        this.email = email;
        this.elo = 1000L;
    }
}
