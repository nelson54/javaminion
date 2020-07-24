package com.github.nelson54.dominion.user.account;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Data @Builder @Getter @Setter
public class AccountCredentialsDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
