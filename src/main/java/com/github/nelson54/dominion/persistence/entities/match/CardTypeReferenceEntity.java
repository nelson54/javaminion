package com.github.nelson54.dominion.persistence.entities.match;

import com.github.nelson54.dominion.cards.CardTypeReference;
import com.github.nelson54.dominion.cards.types.Card;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "card_type_reference")
public class CardTypeReferenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String clazz;

    @Column
    private String name;

    public static CardTypeReferenceEntity ofCardTypeReference(CardTypeReference cardTypeReference) {
        return new CardTypeReferenceEntity(
                cardTypeReference.getCardClass().toString(),
                cardTypeReference.toString());
    }

    public CardTypeReferenceEntity() {}

    private CardTypeReferenceEntity(String clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public CardTypeReference asCardTypeReference() {
        try {
            Pattern p = Pattern.compile("^class (.*)$");
            Matcher match = p.matcher(this.clazz);


            if (match.find()) {
                this.clazz = match.group(1);
            }

            Class<Card> clazz = (Class<Card>) Class.forName(this.clazz);
            return CardTypeReference.of(name, clazz);

        } catch (Exception e) {
            // This shouldn't be possible
            e.printStackTrace();
            return null;
        }
    }

}
