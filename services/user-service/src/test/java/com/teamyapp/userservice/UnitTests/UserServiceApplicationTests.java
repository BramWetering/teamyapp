package com.teamyapp.userservice.UnitTests;

import com.teamyapp.userservice.Database.Repository.IUserRepository;
import com.teamyapp.userservice.Domain.Models.User;
import com.teamyapp.userservice.Domain.Service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user1,user2;

    @BeforeEach
    public void userData() {

        user1 = new User();
        user1.setName("Test Name");
        user1.setEmail("Test Email");


        user2 = new User();
        user2.setName("Test2 Name");
        user2.setEmail("Test2 Email");


    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
    }


    @Test
    void contextLoads() {
        Assert.assertEquals("test", "test");
    }



    @Test
    void getUserWithId() throws Exception {

        // set data
        userRepository.save(user1);

        // get the expected user
        User expectedUser = userService.getUserById(user1.getId());

        // assert
        assertNotNull(user1);
        assertNotNull(expectedUser);
        assertEquals(user1.getId(), expectedUser.getId());
        assertEquals(user1.getName(), expectedUser.getName());
        assertEquals(user1.getEmail(), expectedUser.getEmail());

    }

    @Test
    void getInvalidMoodBoosterById() {
        user1.setId(null);
        // cancel incorrect mood booster
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.getUserById(user1.getId()));
        assertEquals("The given id must not be null!", exception.getMessage());
    }
}






