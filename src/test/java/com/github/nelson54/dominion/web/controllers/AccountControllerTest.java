package com.github.nelson54.dominion.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelson54.dominion.web.Application;
import com.github.nelson54.dominion.web.dto.AccountCredentialsDto;
import com.github.nelson54.dominion.web.dto.AccountDto;
import com.github.nelson54.dominion.web.dto.AuthenticationDto;
import com.github.nelson54.dominion.web.dto.RegistrationDto;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

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
class AccountControllerTest {

    private static final Logger logger = Logger.getLogger(AccountControllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountController accountController;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void signup() throws Exception {
        String url = "http://localhost:" + port + "/api/register";
        URI uri = new URI(url);

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUsername("derek");
        registrationDto.setFirstname("derek");
        registrationDto.setEmail("derek@test.com");
        registrationDto.setPassword("testing");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = mapper.writeValueAsString(registrationDto);
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        this.restTemplate.postForEntity(uri, request, AccountDto.class);
    }

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
        accountCredentialsDto.setUsername("derek");
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

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUsername("derek");
        registrationDto.setFirstname("derek");
        registrationDto.setEmail("derek@test.com");
        registrationDto.setPassword("testing");

        AccountDto accountDto = accountController.register(registrationDto);

        try {
            logger.info(mapper.writeValueAsString(accountDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}