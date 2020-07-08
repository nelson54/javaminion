package com.github.nelson54.dominion.user;

import com.github.nelson54.dominion.user.account.AccountEntity;
import com.github.nelson54.dominion.user.account.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserEntityDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public UserEntityDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = accountRepository.findByUserUsername(username)
                .map(AccountEntity::getUser)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(userEntity.getUsername(), userEntity.getPassword(), emptyList());
    }
}