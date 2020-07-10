package com.github.nelson54.dominion.match;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @NoArgsConstructor @Getter @Setter
public class MatchDto {
    private String id;
    private Integer numberOfHumanPlayers;
    private Integer numberOfAiPlayers;
    private Integer count;
    private String cards;
}
