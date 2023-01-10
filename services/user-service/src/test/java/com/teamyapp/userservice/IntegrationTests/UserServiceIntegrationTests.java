package com.teamyapp.userservice.IntegrationTests;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.teamyapp.userservice.API.Controller.UserController;
import com.teamyapp.userservice.Domain.Models.User;
import com.teamyapp.userservice.Domain.Service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("dev")
public class UserServiceIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private User user1,user2;

    @BeforeEach
    public void userData() {
        user1 = new User();
        user1.setId("1");
        user1.setName("Test Name");
        user1.setEmail("Test Email");


        user2 = new User();
        user2.setName("Test2 Name");
        user2.setEmail("Test2 Email");
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers() throws Exception {

        JSONArray roles = new JSONArray();
        roles.add("Role.Admins");

        when(userService.getAllUsers())
                .thenReturn(List.of(user1));
        mvc
                .perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserById() throws Exception {
        when(userService
                .getUserById(user1.getId()))
                .thenReturn(user1);
        mvc.perform(get("/" + user1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Name"))
                .andExpect(jsonPath("$.email").value("Test Email"));

    }

}
