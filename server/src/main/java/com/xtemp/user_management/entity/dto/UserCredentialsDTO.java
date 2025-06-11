package com.xtemp.user_management.entity.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCredentialsDTO {

    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;
}