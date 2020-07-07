package com.github.nelson54.dominion.cards;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardController {

    @GetMapping(value = "/cards/recommended")
    public RecommendedCards[] getRecommendedCards(){
        return RecommendedCards.values();
    }
}
