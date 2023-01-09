package com.teamyapp.userservice.Domain.Service;

import com.teamyapp.userservice.Database.Repository.IUserRepository;

import com.teamyapp.userservice.Domain.DTO.ChangeUsernameDto;
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

        Optional<User> foundUser = repository.findById(userId);
        if(foundUser.isEmpty())
        {
            //exception
        }
        User user = foundUser.get();

        return user;
    }



    public User getUserInfo(Authentication authContext) throws IllegalAccessException {
        Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);
        return repository.findById((String) claims.get("oid")).get();
    }

    public User PostUser(){
        //Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);

        User user = new User("48384298345","Test User", "Username");
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
            User user = new User((String) claims.get("oid"), (String) claims.get("name"), (String) claims.get("preferred_username"));
            user = repository.save(user);
            NewUserDTO dto = new NewUserDTO();
            dto.setId(user.getId());

            // Todo: wrap blocking call
            daprClient.publishEvent("pubsub", "newUser", dto).block();

        }
        return true;
    }

    public User changeUserName(ChangeUsernameDto dto, Authentication authContext) throws Exception {
        Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);
        String userId = claims.get("oid").toString();


        Optional<User> lookupUser = repository.findById(userId);
        if (lookupUser.isPresent()) {
            User user = lookupUser.get();
            user.setName(dto.getName());

            ChangeUsernameDto tosend = new ChangeUsernameDto();
            tosend.setName(dto.getName());
            tosend.setId(dto.getId());
            DaprClient client = new DaprClientBuilder().build();
            client.publishEvent("pubsub", "changeUsername", tosend).block();
            return repository.save(user);
        } else {
            throw new Exception("User not found");
        }
    }

    public User addUser(NewUserDTO dto, Authentication authContext) throws IllegalAccessException {
        Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);
        String userId = claims.get("oid").toString();
        User newuser = User.builder()
                .id(userId)
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        return repository.save(newuser);

    }

}
