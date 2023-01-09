package com.teamyapp.messageservice.API.Controller;

import com.teamyapp.messageservice.Domain.DTO.AddMessageDTO;
import com.teamyapp.messageservice.Domain.Models.Message;
import com.teamyapp.messageservice.Domain.Service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Message Controller")
@RestController
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('SCOPE_Message.All')")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @ApiOperation(value = "Test")
    @GetMapping(value = "/")
    public String getUser() {
        return "test";
    }


    @ApiOperation(value = "Get all messages")
    @GetMapping(value = "/all")
    public List<Message> getAllMessages(){
        return this.service.getAllMessages();
    }

    @ApiOperation(value = "Post new message")
    @PostMapping(value = "/add")
    public @ResponseBody ResponseEntity<Message> newMessage(@RequestBody AddMessageDTO DTO) throws IllegalAccessException {
        Authentication authContext = SecurityContextHolder.getContext().getAuthentication();

        return new ResponseEntity<>(service.addMessage(DTO, authContext), HttpStatus.CREATED);
    }
}
