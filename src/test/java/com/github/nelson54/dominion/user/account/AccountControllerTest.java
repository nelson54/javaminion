package com.github.nelson54.dominion.user.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelson54.dominion.Application;
import com.github.nelson54.dominion.user.authorization.AuthenticationDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.Registration;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(AccountControllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private ObjectMapper mapper;

    @Test
    void signupMockTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUsername("bill");
        registrationDto.setFirstname("bill");
        registrationDto.setEmail("bill@test.com");
        registrationDto.setPassword("testing");

        this.mockMvc.perform(post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(registrationDto))
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"username\":\"bill\"")));
    }

    @Test
    void authentication() {
        AccountCredentialsDto accountCredentialsDto = new AccountCredentialsDto();
        accountCredentialsDto.setUsername("bob");
        accountCredentialsDto.setPassword("testing");

        AuthenticationDto auth = accountController.authentication(accountCredentialsDto);

        try {
            logger.info(mapper.writeValueAsString(auth));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void register() {
        accountRepository.deleteAll();

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setFirstname("Derek");
        registrationDto.setFirstname("Nelson");
        registrationDto.setUsername("derek");
        registrationDto.setPassword("testing");
        registrationDto.setEmail("contact@dereknelson.io");

        accountController.register(registrationDto);
    }
}