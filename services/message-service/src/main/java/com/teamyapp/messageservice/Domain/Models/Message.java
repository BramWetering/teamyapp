package com.teamyapp.messageservice.Domain.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type= "uuid-char")
    private String id;
    private String userId;
    private String username;
    private String title;
    private String description;
}
