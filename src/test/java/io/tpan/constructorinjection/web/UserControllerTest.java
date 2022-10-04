package io.tpan.constructorinjection.web;

import io.tpan.constructorinjection.dto.UserDataDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;
@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserDataDto userDataDto;
    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserDataDto userDataDto() {
            return new UserDataDto();
        }

    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .apply(sharedHttpSession())
                .build();
    }
    @Test
    void saveUserNameAndGetUserName() throws Exception {
        String userName = "USERNAME";
        mockMvc.perform(post("/saveUserName")
                        .with(user("user1").password("password").roles("ADMIN"))
                        .content(userName))
                .andExpect(status().is2xxSuccessful());

        MvcResult result = mockMvc.perform(get("/getUserName")
                .with(user("user1").password("password").roles("ADMIN")))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        Assertions.assertEquals(userName, response);
    }

    @Test
    void saveUserNameReplaceInstanceAndGetUserName() throws Exception {
        String userName = "USERNAME";
        mockMvc.perform(post("/saveUserNameReplaceInstance")
                        .with(user("user1").password("password").roles("ADMIN"))
                        .content(userName))
                .andExpect(status().is2xxSuccessful());

        MvcResult result = mockMvc.perform(get("/getUserName")
                        .with(user("user1").password("password").roles("ADMIN")))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        Assertions.assertEquals(userName, response);
    }
}