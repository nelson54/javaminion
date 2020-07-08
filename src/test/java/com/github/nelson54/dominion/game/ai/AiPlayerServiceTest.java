package com.github.nelson54.dominion.game.ai;

import com.github.nelson54.dominion.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class AiPlayerServiceTest {

    @Autowired
    AiPlayerService aiPlayerService;

    @Test
    void random() {

        aiPlayerService.random(3);
    }
}