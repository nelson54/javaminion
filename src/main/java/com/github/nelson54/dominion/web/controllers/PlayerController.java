package com.github.nelson54.dominion.web.controllers;

import com.github.nelson54.dominion.match.Match;
import com.github.nelson54.dominion.persistence.AccountRepository;
import com.github.nelson54.dominion.persistence.MatchRepository;
import com.github.nelson54.dominion.persistence.entities.match.MatchEntity;
import com.github.nelson54.dominion.services.AccountService;
import com.github.nelson54.dominion.web.dto.PlayerInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api")
public class PlayerController {

    private final AccountRepository accountRepository;
    private AccountService accountService;

    private final MatchRepository matchRepository;

    public PlayerController(AccountService accountService, AccountRepository accountRepository, MatchRepository matchRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/players/{id}")
    public PlayerInfoDto getPlayerInfo(
            @PathVariable Long id) {
        PlayerInfoDto playerInfo = new PlayerInfoDto();

        accountService.findById(id)
                .ifPresent(playerInfo::setAccount);

        Iterable<MatchEntity> matchEntities = matchRepository
                .findMatchEntitiesWithPlayer(playerInfo.getAccount().getId());

        List<Match> matches = StreamSupport
                .stream(matchEntities.spliterator(), false)
                .map((MatchEntity::toMatch))
                .collect(Collectors.toList());

        playerInfo.setMatches(matches);

        playerInfo.setRank(accountRepository.findRank(playerInfo.getAccount().getElo()));
        playerInfo.setTotalPlayers(accountRepository.findTotalPlayers(playerInfo.getAccount().getElo()));

        return playerInfo;
    }
}
