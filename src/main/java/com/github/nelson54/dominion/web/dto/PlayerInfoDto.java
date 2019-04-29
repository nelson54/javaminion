package com.github.nelson54.dominion.web.dto;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.commands.Command;
import com.github.nelson54.dominion.match.Match;

import java.util.List;

public class PlayerInfoDto {

    private Account account;

    private List<Match> matches;

    private Long rank;

    private Long totalPlayers;

    private List<Command> commands;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Long getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(Long totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
