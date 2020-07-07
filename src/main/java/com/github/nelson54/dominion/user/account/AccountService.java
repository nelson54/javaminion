package com.github.nelson54.dominion.user.account;

import com.github.nelson54.dominion.user.authorization.JwtTokenService;
import com.github.nelson54.dominion.user.authorization.AuthenticationDto;
import com.github.nelson54.dominion.user.UserEntity;
import com.github.nelson54.dominion.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public Optional<Account> findById(String id) {
        return accountRepository.findById(id).map(AccountEntity::asAccount);
    }

    public Iterable<Account> findAll() {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false)
            .map(AccountEntity::asAccount).collect(Collectors.toList());
    }

    public Optional<AuthenticationDto> authenticateWithCredentials(
            AccountCredentialsDto accountCredentials) throws AuthenticationException {
        AccountEntity accountEntity = accountRepository.findByUserUsername(accountCredentials.getUsername())
                .orElseThrow(() -> new RuntimeException("Incorrect username"));

        if (!passwordEncoder.matches(accountCredentials.getPassword(), accountEntity.getUser().getPassword())) {
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
                .findByUserUsername(user.getUsername())
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

        accountRepository.save(accountEntity);

        return Optional.of(accountEntity.asAccount());
    }

    public Optional<Account> getAuthorizedAccount() {
        return accountRepository
                .findByUserUsername((String)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal()).stream()
                .map(AccountEntity::asAccount).findFirst();
    }
}