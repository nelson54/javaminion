package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.persistence.AccountRepository;
import com.github.nelson54.dominion.persistence.UserRepository;
import com.github.nelson54.dominion.persistence.entities.AccountEntity;
import com.github.nelson54.dominion.persistence.entities.UserEntity;
import com.github.nelson54.dominion.web.dto.AccountCredentialsDto;
import com.github.nelson54.dominion.web.dto.AccountDto;
import com.github.nelson54.dominion.web.dto.AuthenticationDto;
import com.github.nelson54.dominion.web.dto.RegistrationDto;
import org.checkerframework.checker.nullness.Opt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Resource(name = "accountService")
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);
    BCryptPasswordEncoder passwordEncoder;
    UserRepository userRepository;
    AccountRepository accountRepository;

    public AccountService(BCryptPasswordEncoder passwordEncoder,
                          UserRepository userRepository,
                          AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id).map(AccountEntity::asAccount);
    }

    public Optional<AuthenticationDto> authenticateWithCredentials(
            AccountCredentialsDto accountCredentials) throws AuthenticationException {
        UserEntity userEntity = userRepository.findByUsername(accountCredentials.getUsername())
                .orElseThrow(() -> new RuntimeException("Incorrect username"));

        if (!passwordEncoder.matches(accountCredentials.getPassword(), userEntity.getPassword())) {
            throw new AuthenticationException("Incorrect password");
        }

        logger.info("User {} has logged in", accountCredentials.getUsername());

        User user = new User(
                accountCredentials.getUsername(),
                accountCredentials.getPassword(),
                new ArrayList<>()
        );

        String token = JwtTokenService.generateToken(
                new UsernamePasswordAuthenticationToken(user, null)
        );

        return accountRepository
                .findByUserUsername(userEntity.getUsername())
                .map(AccountEntity::asAccount)
                .map(AccountDto::fromAccount)
                .map((accountDto) -> new AuthenticationDto(token, accountDto));
    }

    public Optional<Account> createAccount(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registrationDto.getUsername());

        String encryptedPassword = passwordEncoder.encode(registrationDto.getPassword());
        userEntity.setPassword(encryptedPassword);

        AccountEntity accountEntity = new AccountEntity(
                false,
                registrationDto.getFirstname(),
                registrationDto.getEmail(),
                userEntity);
        accountEntity.setElo(1000L);
        accountRepository.save(accountEntity);

        return Optional.of(accountEntity.asAccount());
    }

    public Optional<Account> getAuthorizedAccount() {
        return accountRepository
                .findByUserUsername((String)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .map(AccountEntity::asAccount);
    }
}