package com.github.nelson54.dominion.services;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.persistence.AccountRepository;
import com.github.nelson54.dominion.persistence.UserRepository;
import com.github.nelson54.dominion.persistence.entities.AccountEntity;
import com.github.nelson54.dominion.persistence.entities.UserEntity;
import com.github.nelson54.dominion.web.dto.AccountCredentialsDto;
import com.github.nelson54.dominion.web.dto.AuthenticationDto;
import com.github.nelson54.dominion.web.dto.UserDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import java.util.ArrayList;

@Service
@Resource(name="accountService")
public class AccountService {

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

    public AuthenticationDto authenticateWithCredentials(AccountCredentialsDto accountCredentials)
            throws AuthenticationException {
        UserEntity userEntity = userRepository.findByUsername(accountCredentials.getUsername())
                .orElseThrow(() -> new RuntimeException("Incorrect username"));

        if (!passwordEncoder.matches(accountCredentials.getPassword(), userEntity.getPassword())) {
            throw new AuthenticationException("Incorrect password");
        }

        User user = new User(
                accountCredentials.getUsername(),
                accountCredentials.getPassword(),
                new ArrayList<>()
        );

        String token = JwtTokenService.generateToken(
                new UsernamePasswordAuthenticationToken(user, null)
        );

        UserDto userDto = UserDto.fromUserEntity(userEntity);
        return new AuthenticationDto(token, userDto);
    }

    public Account createAccount(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setEmail(userDto.getEmail());

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userEntity.setPassword(encryptedPassword);

        userRepository.save(userEntity);

        AccountEntity accountEntity = new AccountEntity(false, userDto.getFirstname(), userEntity);
        accountRepository.save(accountEntity);

        return accountEntity.asAccount();
    }
}