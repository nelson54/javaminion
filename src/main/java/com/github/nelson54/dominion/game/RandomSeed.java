package com.github.nelson54.dominion.game;

import java.util.Random;

public class RandomSeed {
    private Random random;

    private Long seed;

    public static RandomSeed create() {
        Random random = new Random();
        random.nextLong();
        return new RandomSeed(random.nextLong());
    }

    public static RandomSeed create(Long seed) {
        return new RandomSeed(seed);
    }

    private RandomSeed(Long seed) {
        this.random = new Random(seed);
        this.seed = seed;
    }


    public Long next() {
        return this.random.nextLong();
    }

    public Random random() {
        return new Random(this.random.nextLong());
    }
}
