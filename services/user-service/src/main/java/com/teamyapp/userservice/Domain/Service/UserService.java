package com.teamyapp.userservice.Domain.Service;

import com.teamyapp.userservice.Database.Repository.IUserRepository;

import com.teamyapp.userservice.Domain.DTO.NewUserDTO;
import com.teamyapp.userservice.Domain.Models.User;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public User getUserInfo(Authentication authContext) throws IllegalAccessException {
        Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);
        return repository.findById((String) claims.get("oid")).get();
    }

    public User PostUser(){
        //Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);

        User user = new User("48384298345","Test User", "Username", "password");
        user = repository.save(user);
        NewUserDTO dto = new NewUserDTO();
        dto.setId(user.getId());

        return repository.save(user);
    }

    public boolean isFirstLogin(Authentication authContext) throws IllegalAccessException {
        Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);
        boolean exists = repository.existsById((String) claims.get("oid"));
        if(exists) {
            return false;
        } else {
            User user = new User((String) claims.get("oid"), (String) claims.get("name"), (String) claims.get("preferred_username"), "10");
            user = repository.save(user);
            NewUserDTO dto = new NewUserDTO();
            dto.setId(user.getId());

            // Todo: wrap blocking call
            daprClient.publishEvent("pubsub", "newUser", dto).block();

        }
        return true;
    }

}
