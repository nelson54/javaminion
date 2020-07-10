package com.github.nelson54.dominion.match;

import com.github.nelson54.dominion.user.account.AccountEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Document("match")
@Data @NoArgsConstructor
public class MatchEntity {

    @Id @Getter @Setter
    private String id;

    @Field @Getter @Setter
    private Long seed;

    @DBRef @Getter @Setter
    private List<AccountEntity> players;

    @Field @Getter @Setter
    private Integer playerCount;

    @Field @Getter @Setter
    private String turnOrder;

    @Enumerated(EnumType.STRING)
    @Field @Getter @Setter
    private MatchState state;

    @Getter @Setter
    private Set<PlayerScoreEntity> scores;

    @Getter
    private List<CardTypeReferenceEntity> gameCards;

    @DBRef @Getter @Setter
    public AccountEntity winner;

    @Field @Getter @Setter
    @CreatedDate
    public LocalDateTime createdAt;

    @Field @Getter @Setter
    public LocalDateTime finishedAt;

    public AccountEntity findPlayerById(String id) {
        Map<String, AccountEntity> playersById = new HashMap<>();
        this.players.forEach((player) -> playersById.put(player.getId(), player));

        return playersById.get(id);
    }
}
