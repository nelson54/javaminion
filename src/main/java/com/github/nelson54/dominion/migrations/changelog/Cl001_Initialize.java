package com.github.nelson54.dominion.migrations.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.github.nelson54.dominion.game.ai.AiName;
import com.github.nelson54.dominion.game.commands.Command;
import com.github.nelson54.dominion.match.MatchEntity;
import com.github.nelson54.dominion.user.UserEntity;
import com.github.nelson54.dominion.user.account.AccountEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ChangeLog(order = "1")
public class Cl001_Initialize {

    @ChangeSet(id = "Clear database", order = "001", author = "Derek")
    public void clear(MongockTemplate template) {
        template.dropCollection(AccountEntity.class);
        template.dropCollection(Command.class);
        template.dropCollection(MatchEntity.class);
    }

    @ChangeSet(id = "Add Root Account", order = "002", author = "Derek")
    public void addRootAccount(MongockTemplate template) {

        String password = "$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK";
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("Root"));
        authorities.add(new SimpleGrantedAuthority("Admin"));

        UserEntity user = new UserEntity("derek", password, authorities);
        AccountEntity account = new AccountEntity(false, "Derek", "contact@dereknelson.io", user);
        template.save(account);
    }

    @ChangeSet(id = "Add Ai Accounts", order = "003", author = "Derek")
    public void addAiAccounts(MongockTemplate template) {

        String password = "$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK";
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("AI"));

        Arrays.stream(AiName.values()).map(Enum::toString).map(username -> {
                String email = username.toLowerCase() + "@example.com";
                UserEntity user = new UserEntity(username, password, authorities);
                return new AccountEntity(true, username, email, user);
            })

            .forEach(template::save);


    }


}
