package com.teamyapp.messageservice.Database.Repository;

import com.teamyapp.messageservice.Domain.Models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMessageRepository extends MongoRepository<Message, String> {

}
