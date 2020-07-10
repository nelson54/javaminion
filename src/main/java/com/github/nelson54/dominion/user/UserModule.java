package com.github.nelson54.dominion.user;

import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.user.account.AccountEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class UserModule {

    @Autowired
    void configureMapper(ModelMapper modelMapper) {
        modelMapper.addConverter((conversion) -> {
            AccountEntity accountEntity = conversion.getSource();

            if(accountEntity == null) {
                return null;
            }

            Account account = new Account(
                    accountEntity.getId(),
                    accountEntity.getUser().asUser(),
                    accountEntity.getEmail(),
                    accountEntity.getFirstname(),
                    accountEntity.getAi());

            account.setElo(accountEntity.getElo());
            return account;
        }, AccountEntity.class, Account.class);

        modelMapper.addConverter((conversion) -> {
            Account account = conversion.getSource();

            if(account == null) {
                return null;
            }

            AccountEntity accountEntity = new AccountEntity(
                    account.getAi(),
                    account.getFirstname(),
                    account.getEmail(),
                    null);

            accountEntity.setId(account.getId());
            accountEntity.setUser(UserEntity.ofUser(account.getUser()));
            accountEntity.setElo(account.getElo());

            return accountEntity;
        }, Account.class, AccountEntity.class);
    }
}
