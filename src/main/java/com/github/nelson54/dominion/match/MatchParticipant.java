package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.user.account.Account;
import com.github.nelson54.dominion.game.ai.AiStrategies;
import com.github.nelson54.dominion.game.ai.AiStrategy;

import java.util.Objects;

public class MatchParticipant {
    private Account account;
    private AiStrategy aiStrategy;
    private boolean ai;

    public MatchParticipant(Account account) {
        this.ai = account.getAi();
        this.account = account;
    }

    public static MatchParticipant createAi(Account account) {
        MatchParticipant ai = new MatchParticipant(account);
        ai.aiStrategy = AiStrategies.random();
        ai.ai = true;
        return ai;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isAi() {
        return ai;
    }

    public AiStrategy getAiStrategy() {
        return aiStrategy;
    }

    public void setAiStrategy(AiStrategy strategy) {
        this.aiStrategy = strategy;
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
