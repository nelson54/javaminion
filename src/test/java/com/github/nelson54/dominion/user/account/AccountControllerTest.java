package com.github.nelson54.dominion.user.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.user.UserEntity;
import com.github.nelson54.dominion.user.authorization.AuthenticationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
class AccountControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(AccountControllerTest.class);

    private MockMvc mockMvc;
    private TestRestTemplate restTemplate;
    private AccountController accountController;
    private AccountRepository accountRepository;
    private ObjectMapper mapper;

    @Autowired
    public AccountControllerTest(MockMvc mockMvc,
                                 TestRestTemplate restTemplate,
                                 AccountController accountController,
                                 AccountRepository accountRepository,
                                 ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.restTemplate = restTemplate;
        this.accountController = accountController;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @BeforeEach
    void setup() {
        accountRepository.findByUserUsername("bill")
                .ifPresent(accountRepository::delete);
    }

    @Test
    void signupMockTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUsername("bill");
        registrationDto.setFirstname("bill");
        registrationDto.setEmail("bill@test.com");
        registrationDto.setPassword("testing");

        this.mockMvc.perform(post("/api/register")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(registrationDto))
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"username\":\"bill\"")));
    }

    @Test
    void authentication() {
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

        AccountCredentialsDto accountCredentialsDto = AccountCredentialsDto.builder()
                .username("bill")
                .password("testing").build();

        AuthenticationDto auth = accountController.authentication(accountCredentialsDto);

        try {
            logger.info(mapper.writeValueAsString(auth));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}