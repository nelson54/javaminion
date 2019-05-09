package com.github.nelson54.dominion.ai;

import com.github.nelson54.dominion.cards.CardType;
import com.github.nelson54.dominion.cards.types.ActionCard;
import com.github.nelson54.dominion.choices.Choice;
import com.github.nelson54.dominion.choices.ChoiceResponse;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.github.nelson54.dominion.choices.OptionType.CARD;
import static com.github.nelson54.dominion.choices.OptionType.YES_OR_NO;

class DoNothingAi extends AiStrategy {
    @Override
    public void actionPhase(AiGameFacade game) {
        game.endPhase();
    }

    @Override
    public void buyPhase(AiGameFacade game) {
        game.endPhase();
    }

    @Override
    public void choice(AiGameFacade game) {
        game.getChoice().ifPresent(c -> handleChoice(game, c));
    }

    public boolean playIfFound(AiGameFacade game, String name) {
        Optional<ActionCard> card = game.getHand().stream()
                .filter(c -> c.getName().equals(name) )
                .filter(c -> c.isType(CardType.ACTION))
                .map(c -> (ActionCard)c)
                .findFirst();

        if (card.isPresent() && game.getActions() > 0) {
            game.play(card.get());
            return true;
        }

        return false;
    }

    void handleChoice(AiGameFacade game, Choice choice) {
        ChoiceResponse choiceResponse = new ChoiceResponse();
        choiceResponse.setChoice(choice.getId().toString());
        choiceResponse.setSource(choice.getTarget());

        choiceResponse.setTargetChoice(choice.getId().toString());

        if (!choice.isRequired()) {
            choiceResponse.setDone(true);
        } else if (choice.getExpectedAnswerType().equals(YES_OR_NO)) {
            choiceResponse.setYes(false);
        } else if (choice.getExpectedAnswerType().equals(CARD)) {
            Set<Long> choices = new HashSet<>();

            Optional<Long> option = choice.getOptions().stream().findFirst();

            option.ifPresent(choices::add);

            choiceResponse.setCard(game.getCardById(option.get()));
            choiceResponse.setChoices(choices);
        }

        game.respond(choiceResponse);
    }
}
