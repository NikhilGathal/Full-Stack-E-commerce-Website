package com.jsp.SpringBoot_React.dto;

import lombok.Getter;
import lombok.Setter;


public class LoginResponse {
    private String message;
    private boolean isAdmin; // Field to store the admin status

    // Constructor
    public LoginResponse(String message, boolean isAdmin) {
        this.message = message;
        this.isAdmin = isAdmin;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
    
    

    // Getters and Setters
  
}

