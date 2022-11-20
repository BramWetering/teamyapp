package com.teamyapp.userservice.Domain.Models.Service;

import com.teamyapp.userservice.Database.Repository.IUserRepository;

import com.teamyapp.userservice.Domain.Models.User;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final IUserRepository repository;
    private final DaprClient daprClient;


    @Autowired
    public UserService(IUserRepository repository){
        this.repository = repository;
        this.daprClient = new DaprClientBuilder().build();
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(String userId) {
        return repository.findById(userId).orElse(null);
    }

//    public boolean deleteUser(long userid){
//
//    }



}
