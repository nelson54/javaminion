package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.Account;
import com.github.nelson54.dominion.ai.AiUtils;

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

    public static MatchParticipant createAi() {
        MatchParticipant ai = new MatchParticipant(AiUtils.randomPlayer().getAccount());
        ai.ai = true;
        return ai;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isAi() {
        return ai;
    }
}
