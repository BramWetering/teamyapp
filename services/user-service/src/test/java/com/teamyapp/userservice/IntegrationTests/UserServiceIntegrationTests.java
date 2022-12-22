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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        user1.setName("Test Name");
        user1.setEmail("Test Email");
        user1.setPassword("Test PW");

        user2 = new User();
        user2.setName("Test2 Name");
        user2.setEmail("Test2 Email");
        user2.setPassword("Test2 PW");
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers() throws Exception {

        JSONArray roles = new JSONArray();
        roles.add("Role.Admins");

        when(userService.getAllUsers())
                .thenReturn(List.of());
        mvc
                .perform(get("/all").with(jwt().jwt(jwt -> jwt.claim("roles", roles))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

//        //Arrange
//        Integer userCount = userRepository.findAll().size();
//
//        //Act/Assert
//        mockMvc.perform(MockMvcRequestBuilders.get("/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(userCount)));
    }

}
