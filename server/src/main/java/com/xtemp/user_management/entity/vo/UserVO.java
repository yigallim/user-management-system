package com.xtemp.user_management.entity.vo;

import com.xtemp.user_management.entity.pojo.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    private Integer id;
    private String username;
    private String password;
    private User.Role role;
    private Boolean disabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
