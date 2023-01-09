package com.teamyapp.messageservice.Domain.Service;

import com.teamyapp.messageservice.Database.Repository.IMessageRepository;
import com.teamyapp.messageservice.Domain.DTO.AddMessageDTO;
import com.teamyapp.messageservice.Domain.DTO.ChangeUsernameDto;
import com.teamyapp.messageservice.Domain.Models.Message;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageService {

    private final IMessageRepository repository;

    private final List<Message> messages = new ArrayList<>();

    public MessageService(IMessageRepository repository) {
        this.repository = repository;
    }

    public Message addMessage(AddMessageDTO dto) throws IllegalAccessException{
        Message newmessage = Message.builder()
                .description(dto.getDescription())
                .userId(dto.getUserId())
                .username(dto.getUsername())
                .build();
        newmessage = repository.save(newmessage);
        return newmessage;
    }

    public List<Message> getAllMessages() {
        return repository.findAll();
    }

    public Message updateMessage(AddMessageDTO dto) throws Exception {
        Optional<Message> lookupMessage = repository.findById(dto.getId());
        if (lookupMessage.isPresent()) {
            Message message = Message.builder()
                    .id(dto.getId())
                    .description(dto.getDescription())
                    .title(dto.getTitle())
                    .username(dto.getUsername())
                    .build();
            message = repository.save(message);
            return message;
        } else {
            throw new Exception("Message not found");
        }
    }

    public void updateUserName(ChangeUsernameDto dto) {
        List<Message> messages = repository.findAll();

        for (Message message : messages) {
            if (message.getUserId().equals(dto.getId())) {
                message.setUsername(dto.getName());
            }
        }

        repository.saveAll(messages);
    }
}
