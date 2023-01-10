package com.teamyapp.userservice.API.Controller;

import com.teamyapp.userservice.Domain.DTO.ChangeUsernameDto;
import com.teamyapp.userservice.Domain.DTO.NewUserDTO;
import com.teamyapp.userservice.Domain.Service.UserService;
import com.teamyapp.userservice.Domain.Models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "User Controller")
@RestController
@Slf4j
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

        return new User("1", "Test88 User", "Test@mail.com");
    }

    @ApiOperation(value = "Get all users")
    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        log.info("Gets all users");
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

    @ApiOperation(value = "Get User by ID")
    @GetMapping(value = "/{userId}")
    public @ResponseBody User getUserByID(@PathVariable String userId) {
        return service.getUserById(userId);
    }

    @ApiOperation("Update User")
    @PutMapping("/changeUsername")
    public ResponseEntity<Object> updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody ChangeUsernameDto dto) throws Exception {
        Authentication authContext = SecurityContextHolder.getContext().getAuthentication();

        User user = service.changeUserName(dto, authContext);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @ApiOperation("Create User")
    @PostMapping("/new")
    public ResponseEntity<Object> createUser(@RequestBody NewUserDTO dto) throws IllegalAccessException {
        Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
        User user = service.addUser(dto, authContext);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create user", HttpStatus.BAD_REQUEST);
        }
    }
}
