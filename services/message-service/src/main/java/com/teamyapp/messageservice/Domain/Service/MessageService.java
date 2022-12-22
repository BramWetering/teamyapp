package com.teamyapp.messageservice.Domain.Service;

import com.teamyapp.messageservice.Database.Repository.IMessageRepository;
import com.teamyapp.messageservice.Domain.DTO.AddMessageDTO;
import com.teamyapp.messageservice.Domain.Models.Message;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    private final IMessageRepository repository;

    private final List<Message> messages = new ArrayList<>();

    public MessageService(IMessageRepository repository) {
        this.repository = repository;
    }

    public Message addMessage(AddMessageDTO newMessage, Authentication authContext) throws IllegalAccessException{
        Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);
        String userId = claims.get("oid").toString();

        Message message = Message.builder()
                .title(newMessage.getTitle())
                .description(newMessage.getDescription())
                .userId(userId)
                .build();

        message = repository.save(message);
        messages.add(message);
        return message;
    }
}
