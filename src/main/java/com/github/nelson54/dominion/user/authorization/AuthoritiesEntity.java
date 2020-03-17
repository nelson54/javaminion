package com.github.nelson54.dominion.user.authorization;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class AuthoritiesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String authority;
}
