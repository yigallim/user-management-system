package com.xtemp.user_management.entity.dto;

import com.xtemp.user_management.entity.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAuthResponse {
    private String accessToken;
    private UserDTO user;

    @Data
    @Builder
    public static class UserDTO {
        private Integer id;
        private String username;
        private User.Role role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
