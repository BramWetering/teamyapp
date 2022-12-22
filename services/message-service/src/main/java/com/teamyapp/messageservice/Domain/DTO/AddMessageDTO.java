package com.teamyapp.messageservice.Domain.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddMessageDTO {

    private String title;
    private String description;
}
