package com.teamyapp.userservice.Domain.Models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

}
