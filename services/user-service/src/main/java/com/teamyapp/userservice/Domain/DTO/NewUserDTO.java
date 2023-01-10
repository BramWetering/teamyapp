package com.teamyapp.userservice.Domain.DTO;

import com.bol.secure.Encrypted;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewUserDTO {
    private String id;
    private String name;
    @Encrypted
    private String email;
}
