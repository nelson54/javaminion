package com.github.nelson54.dominion.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.user.UserEntity;
import com.github.nelson54.dominion.user.account.AccountCredentialsDto;
import com.github.nelson54.dominion.user.account.AccountEntity;
import com.github.nelson54.dominion.user.authorization.AuthenticationDto;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountEntity accountEntity;
    @Before
    void setup() {

        UserEntity userEntity = UserEntity.builder()
                .username("bob")
                .password("testing")
                .enabled(Boolean.TRUE).build();

        this.accountEntity = AccountEntity.builder()
                .elo(1000L).firstname("bob")
                .email("bob@example.com")
                .user(userEntity)
                .ai(false).build();


    }

    @Test
    void test1_authorize() throws Exception {
        AccountCredentialsDto accountCredentialsDto = new AccountCredentialsDto();
        accountCredentialsDto.setUsername("bob");
        accountCredentialsDto.setPassword("testing");

        MvcResult authResult = this.mockMvc.perform(post("/api/authentication")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(accountCredentialsDto))
        ).andReturn();

        byte[] resultBody = authResult.getResponse().getContentAsByteArray();

        AuthenticationDto auth = objectMapper.readValue(resultBody, AuthenticationDto.class);

        /*String authorization = "Bearer " + auth.getToken();

        MatchDto matchDto = new MatchDto();
        matchDto.setCount(2);
        matchDto.setNumberOfHumanPlayers(1);
        matchDto.setNumberOfAiPlayers(1);
        matchDto.setCards("First Game");

        MvcResult  mvcResult = this.mockMvc.perform(post("/com.github.nelson54.dominion/matches")
                .header("Authorization", authorization)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(matchDto))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("\"playerCount\":2")))
                .andReturn();

        byte[] matchResponse = mvcResult.getResponse().getContentAsByteArray();

        matchDto = objectMapper.readValue(matchResponse, MatchDto.class);
        */
    }
}