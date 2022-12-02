package com.teamyapp.userservice.API.Controller;

import com.teamyapp.userservice.Domain.DTO.NewUserDTO;
import com.teamyapp.userservice.Domain.Service.UserService;
import com.teamyapp.userservice.Domain.Models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "User Controller")
@RestController
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('SCOPE_User.All')")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }


    @ApiOperation(value = "Get user")
    @GetMapping(value = "/")
    public User getUser() {

        return new User("1", "Test88 User", "Test@mail.com","password");
    }

    @ApiOperation(value = "Get all users")
    @GetMapping(value = "/all")
    public List<User> getAllUsers() {

        return this.service.getAllUsers();
    }

    @ApiOperation(value = "Returns user info of the authenticated user")
    @GetMapping("/me")
    public User getUserInfo() throws IllegalAccessException {
        Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
        return service.getUserInfo(authContext);
    }

    @ApiOperation(value = "Returns user info of the authenticated user")
    @PostMapping("/newuser")
    public User NewUser(){
        //Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
        return service.PostUser();
    }

    @ApiOperation(value = "On login, checks if this is the user's first time. If it is, the user data is parsed from the token claims and saved to the database")
    @GetMapping("/login/check")
    public String isFirstLogin() {
        return "Test";
        //Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
        //return service.isFirstLogin(authContext);
    }
}
