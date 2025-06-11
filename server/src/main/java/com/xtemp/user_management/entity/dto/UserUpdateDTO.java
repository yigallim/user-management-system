package com.xtemp.user_management.entity.dto;

import com.xtemp.user_management.entity.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String username;
    private String password;
    private Boolean disabled;
}
