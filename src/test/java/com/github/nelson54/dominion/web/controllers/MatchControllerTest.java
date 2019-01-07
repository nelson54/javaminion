package com.github.nelson54.dominion.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelson54.dominion.web.Application;
import com.github.nelson54.dominion.web.dto.AccountCredentialsDto;
import com.github.nelson54.dominion.web.dto.AuthenticationDto;
import com.github.nelson54.dominion.web.dto.MatchDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class MatchControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountController accountController;

    @Test
    void createMatch() throws Exception {
        AccountCredentialsDto accountCredentialsDto = new AccountCredentialsDto();
        accountCredentialsDto.setUsername("bob");
        accountCredentialsDto.setPassword("testing");

        AuthenticationDto auth = accountController.authentication(accountCredentialsDto);

        MatchDto matchDto = new MatchDto();

        matchDto.setCount(2);
        matchDto.setNumberOfHumanPlayers(0);
        matchDto.setNumberOfAiPlayers(2);
        matchDto.setCards("First Game");

        this.mockMvc.perform(post("/dominion/matches")
                .header("Authorization", "Bearer: " + auth.getToken())
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(matchDto))
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"username\":\"bill\"")));
    }
}