package com.teamyapp.messageservice.API.Controller;

import com.teamyapp.messageservice.Domain.DTO.AddMessageDTO;
import com.teamyapp.messageservice.Domain.Models.Message;
import com.teamyapp.messageservice.Domain.Service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public @ResponseBody ResponseEntity<Object> newMessage(@RequestBody AddMessageDTO DTO) throws IllegalAccessException {
        Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
        Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);
        String userId = claims.get("oid").toString();
        DTO.setUserId(userId);
        Message message = service.addMessage(DTO);
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create massage", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("update message")
    @PostMapping("/update")
    public ResponseEntity<Object> updateMessage(@RequestBody AddMessageDTO dto) throws IllegalAccessException {
        Authentication authContext = SecurityContextHolder.getContext().getAuthentication();
        Map<?, ?> claims = (Map<?, ?>) FieldUtils.readField(authContext.getPrincipal(), "claims", true);
        String userId = claims.get("oid").toString();
        dto.setUserId(userId);
        try {
            Message message = service.updateMessage(dto);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("delete message")
    @DeleteMapping("/{messageId}}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable String messageId) throws IllegalAccessException {
        Authentication authContext = SecurityContextHolder.getContext().getAuthentication();

        service.deleteMessageById(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
