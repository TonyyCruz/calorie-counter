package com.anthony.calorie_counter.integration.config;

import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
public class TestBase {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    protected final String USER_URL = "/api/v1/users";
    protected final String AUTH_URL = "/api/v1/auth/login";
    protected String userToken;
    protected String adminToken;

    @BeforeEach
    protected void setUp() throws Exception {
        performSaveUser(userModelTest());
        performSaveUser(adminModelTest());
        userToken = performLogin(userModelTest());
        adminToken = performLogin(adminModelTest());
    }

    public String performLogin(UserModel user) throws Exception {
        ResultActions resultActions = mockMvc.perform(post(AUTH_URL).with(httpBasic(user.getEmail(), user.getPassword())));
        MvcResult mvcResult = resultActions.andDo(MockMvcResultHandlers.print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(contentAsString);
        return "Bearer " + json.getString("token");
//        return "Bearer " + json.getJSONObject("data").getString("token");
    }

    public void performSaveUser(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public UserModel userModelTest() {
        UserModel user = new UserModel();
        user.setId(UUID.fromString("d89401d3-64ac-40c7-8198-b35cdaa4935b"));
        user.setName("user");
        user.setEmail("testUser@email.com");
        user.setPhoneNumber("(11) 95797-9692");
        user.setPassword("123456");
        return user;
    }

    public UserModel adminModelTest() {
        UserModel user = new UserModel();
        user.setId(UUID.fromString("47f4b4f7-40a4-4bc5-aa09-d7828fc911f2"));
        user.setName("admin");
        user.setEmail("testAdmin@email.com");
        user.setPhoneNumber("(11) 95797-9692");
        user.setPassword("123456");
        return user;
    }
}
