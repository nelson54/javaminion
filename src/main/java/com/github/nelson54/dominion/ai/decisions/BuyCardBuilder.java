package com.github.nelson54.dominion.ai.decisions;

import com.github.nelson54.dominion.ai.AiGameFacade;
import com.github.nelson54.dominion.cards.types.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BuyCardBuilder {
    AiGameFacade game;
    AiDecisionBuilder parent;
    List<ActionCondition<String>> buyOptions;
    Optional<ActionCondition<String>> currentDecision;

    static BuyCardBuilder create(AiDecisionBuilder parent){
        return new BuyCardBuilder(parent);
    }

    public BuyCardBuilder pick(String card){
        if(currentDecision.isPresent()){
            throw new IllegalStateException();
        }

        currentDecision = Optional.of(new ActionCondition<>(card));
        return this;
    }

    public BuyCardBuilder when(Boolean... decisions){
        if(!currentDecision.isPresent()){
            throw new IllegalStateException();
        }

        List<Boolean> decides = new ArrayList<>();
        decides.addAll(Arrays.asList(decisions));

        currentDecision.ifPresent(condition -> condition.setDecisions(decides));

        return this;
    }

    public BuyCardBuilder or(){
        if(!currentDecision.isPresent()){
            throw new IllegalStateException();
        }

        buyOptions.add(currentDecision.get());
        currentDecision = Optional.empty();
        return this;
    }

    public List<Card> matches(){
        currentDecision.ifPresent(buyOptions::add);
        return buyOptions.stream()
                .filter(b -> game.getKingdom().getCardMarket().containsKey(b.getObj()))
                .filter(b -> b.getDecisions() == null || b.getDecisions().stream().allMatch(t -> t))
                .map(b -> game.getKingdom().getCardMarket().get(b.getObj()).stream().findFirst().get())
                .filter(game::canAffordCard)
                .collect(Collectors.toList());
    }

    public Optional<Card> findFirstMatch(){

        return matches().stream()
                .findFirst();
    }

    private BuyCardBuilder(AiDecisionBuilder parent){
        this.parent = parent;
        this.game = parent.getFacade();
        buyOptions = new ArrayList<>();
        currentDecision = Optional.empty();
    }
}