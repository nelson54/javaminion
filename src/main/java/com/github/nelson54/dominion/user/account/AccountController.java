package com.github.nelson54.dominion.user.account;

import com.github.nelson54.dominion.game.commands.CommandRepository;
import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.match.MatchEntity;
import com.github.nelson54.dominion.match.MatchRepository;
import com.github.nelson54.dominion.user.authorization.AuthenticationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CommandRepository commandRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public AccountController(AccountService accountService, AccountRepository accountRepository, CommandRepository commandRepository, MatchRepository matchRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.commandRepository = commandRepository;
        this.matchRepository = matchRepository;
    }

    @PostMapping("/authentication")
    public AuthenticationDto authentication(
            @RequestBody @Valid AccountCredentialsDto accountCredentials) {
        try {
            return accountService.authenticateWithCredentials(accountCredentials)
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public AccountDto register(@RequestBody @Valid RegistrationDto registrationDto) {
        return accountService.createAccount(registrationDto).map(AccountDto::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

    }

    @GetMapping("/account")
    public AccountDto getUser() {
        return accountService
                .getAuthorizedAccount()
                .map(AccountDto::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    @GetMapping("/accounts")
    public Iterable<Account> getUsers() {
        return accountService.findAll();
    }

    @GetMapping("/account/{id}")
    public AccountInfoDto getPlayerInfo(
            @PathVariable String id) {
        AccountInfoDto playerInfo = new AccountInfoDto();

        accountService.findById(id)
                .ifPresent(playerInfo::setAccount);

        //Iterable<MatchEntity> matchEntities = matchRepository
        //        .findMatchEntitiesWithPlayer(playerInfo.getAccount().getId());
        Iterable<MatchEntity> matchEntities = matchRepository.findAll();

        List<Match> matches = StreamSupport
                .stream(matchEntities.spliterator(), false)
                .map((MatchEntity::toMatch))
                .collect(Collectors.toList());

        playerInfo.setMatches(matches);

        commandRepository.findByAccountIdAndBuyNameNotNull(id)
                .ifPresent(playerInfo::setCommands);

        //playerInfo.setRank(accountRepository.findRank(playerInfo.getAccount().getElo()));
        //playerInfo.setTotalPlayers(accountRepository.findTotalPlayers(playerInfo.getAccount().getElo()));

        return playerInfo;
    }
}
