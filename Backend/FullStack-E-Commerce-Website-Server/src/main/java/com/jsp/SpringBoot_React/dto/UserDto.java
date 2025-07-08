package com.jsp.SpringBoot_React.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private Boolean isAdmin; // For differentiating between Admin and User roles

    // Getters and Setters
}
