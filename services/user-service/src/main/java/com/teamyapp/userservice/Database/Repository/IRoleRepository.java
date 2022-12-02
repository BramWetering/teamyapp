package com.teamyapp.userservice.Database.Repository;

import com.teamyapp.userservice.Domain.Models.ERole;
import com.teamyapp.userservice.Domain.Models.Role;
import com.teamyapp.userservice.Domain.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IRoleRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
