package com.github.nelson54.dominion.cards.types;

import com.github.nelson54.dominion.Game;
import com.github.nelson54.dominion.Player;
import com.github.nelson54.dominion.Turn;
import com.github.nelson54.dominion.cards.CardState;
import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;
import com.github.nelson54.dominion.effects.Effect;
import com.github.nelson54.dominion.exceptions.NoValidChoiceException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ComplexActionCard extends ActionCard {

    public ComplexActionCard() {
        isKingdom = true;
        cardTypes.add(CardType.ACTION);
    }

    public abstract Choice getChoiceForTarget(Choice choice, Player target, Game game) throws NoValidChoiceException ;

    public abstract Effect getEffect(Player player, Game game);

    public abstract void play(Player player, Game game);

    public CardState getState(Choice choice){
        return CardState.RESOLVING;
    }

    public Set<Player> getTargets(Player player, Game game) {
        Set<Player> targets = new HashSet<>();
        targets.add(player);
        return targets;
    }

    public void apply(Player player, Game game) {
        play(player, game);

        for (Player target : getTargets(player, game)) {
            addChoice(target, game, null, null);
        }
    }

    public void addChoice(Player player, Game game, ChoiceResponse response, Choice parent) {
        Turn turn = game.getTurn();

        Choice choice = new Choice(player, this);
        choice.setOwner(getOwner());
        choice.setComplete(false);
        choice.setParentChoice(parent);
        choice.setGame(game);
        choice.setSource(this);

        choice.setState(getState(choice));

        Effect effect;
        if (parent == null) {
            effect = getEffect(player, game);
        } else {
            effect = parent.getEffect();
        }
        choice.bind(effect);

        try {
            choice = getChoiceForTarget(choice, player, game);
        } catch (NoValidChoiceException e) {
            choice.getEffect().setNoValidTarget(true);
            choice.setComplete(true);
        }

        if(parent != null && parent.getChoices() != null)
            choice.getChoices().addAll(parent.getChoices());

        if(response != null && response.getChoices() != null)
            choice.getChoices().addAll(response.getChoices());

        if(choice != null && choice.getChoices() != null)
            choice.getOptions().removeAll(choice.getChoices());


        if(!choice.isComplete())
            game.addChoice(choice);

        choice.resolveIfComplete(turn);
    }

    private Choice findParentChoice(Game game, Player player) {
        Turn turn = game.getTurn();

        for (Choice choice : player.getChoices()) {
            if (this.equals(choice.getSource())) {
                return choice;
            }
        }

        return null;
    }


    Set<Player> getOtherPlayers(Player player, Game game) {
        return game.getPlayers()
                .values()
                .stream()
                .filter(p -> !p.getId().equals(player.getId()))
                .collect(Collectors.toSet());
    }

}
