package com.github.nelson54.dominion.view;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter @Builder
public class TurnViewModel {
    boolean isMyTurn;
    long money;
    long buys;
    long actions;
}
