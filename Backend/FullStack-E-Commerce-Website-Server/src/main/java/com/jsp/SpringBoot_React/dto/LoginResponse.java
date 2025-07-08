package com.jsp.SpringBoot_React.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String message;
    private boolean isAdmin; // Field to store the admin status

    // Constructor
    public LoginResponse(String message, boolean isAdmin) {
        this.message = message;
        this.isAdmin = isAdmin;
    }

    // Getters and Setters
  
}

