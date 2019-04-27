package com.github.nelson54.dominion.web.dto;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.match.Match;

import java.util.List;

public class PlayerInfoDto {

    private Account account;

    private List<Match> matches;

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
}
