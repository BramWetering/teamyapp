package com.teamyapp.messageservice.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddMessageDTO {

    private String id;
    private String userId;
    private String username;
    private String title;
    private String description;
}
