package com.github.nelson54.dominion.persistence.entities.match;

import javax.persistence.*;

@Entity
@Table(name="match_entity")
public class CommandEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
}
