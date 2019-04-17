package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.ai.AiUtils;

import java.util.Objects;

public class MatchParticipant {
    private Account account;
    private boolean ai;

    public MatchParticipant(Account account) {
        this.ai = account.getAi();
        this.account = account;
    }

    public static MatchParticipant createAi(Account account) {
        MatchParticipant ai = new MatchParticipant(account);
        ai.ai = true;
        return ai;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isAi() {
        return ai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchParticipant that = (MatchParticipant) o;
        return ai == that.ai &&
                account.equals(that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, ai);
    }
}
