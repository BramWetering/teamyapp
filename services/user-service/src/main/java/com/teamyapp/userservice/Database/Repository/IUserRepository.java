package com.teamyapp.userservice.Database.Repository;

import com.teamyapp.userservice.Domain.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface IUserRepository extends MongoRepository<User, String> {

}

