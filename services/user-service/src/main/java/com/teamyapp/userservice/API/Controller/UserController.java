package com.teamyapp.userservice.API.Controller;

import com.teamyapp.userservice.Domain.Models.Service.UserService;
import com.teamyapp.userservice.Domain.Models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Api(tags = "User Controller")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }


    @ApiOperation(value = "Get user")
    @GetMapping(value = "/")
    public User getUser() {

        return new User("1", "Test88 User", "Test@mail.com");
    }


    @ApiOperation(value = "Get all users")
    @GetMapping(value = "/all")
    public List<User> getAllUsers(){

        return this.service.getAllUsers();

    }


}
