package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.User;

import java.util.UUID;

public class MatchParticipant {
    private User user;
    private boolean ai;

    public MatchParticipant(User user) {
        this.ai = false;
        this.user = user;
    }

    MatchParticipant() {
        this.user = new User(UUID.randomUUID().toString());
        this.ai = true;
    }

    public User getUser() {
        return user;
    }

    public boolean isAi() {
        return ai;
    }
}
