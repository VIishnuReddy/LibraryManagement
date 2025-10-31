package com.example.library.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
