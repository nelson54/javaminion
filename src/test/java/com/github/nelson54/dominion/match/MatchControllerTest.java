package com.github.nelson54.dominion.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.user.UserEntity;
import com.github.nelson54.dominion.user.account.AccountCredentialsDto;
import com.github.nelson54.dominion.user.account.AccountEntity;
import com.github.nelson54.dominion.user.account.AccountRepository;
import com.github.nelson54.dominion.user.authorization.AuthenticationDto;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
class MatchControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AccountRepository accountRepository;

    @Autowired
    MatchControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, AccountRepository accountRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.accountRepository = accountRepository;
    }

    @BeforeEach
    void setup() {
        accountRepository.findByUserUsername("bill")
                .ifPresent(accountRepository::delete);

        UserEntity userEntity = UserEntity.builder()
                .username("bill")
                .password("$2a$10$UCrtNnihvVBF63xf.Y3rXODwEtrO4W/Pj6UyohY.23HdBfRmc58eK")
                .enabled(Boolean.TRUE).build();

        AccountEntity accountEntity = AccountEntity.builder()
                .elo(1000L).firstname("bill")
                .email("bill@example.com")
                .user(userEntity)
                .ai(false).build();

        accountRepository.save(accountEntity);
    }

    @Test
    void test1_authorize() throws Exception {
        AccountCredentialsDto accountCredentialsDto = AccountCredentialsDto.builder()
                .username("bill").password("testing").build();

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