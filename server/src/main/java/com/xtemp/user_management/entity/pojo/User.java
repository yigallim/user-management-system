package com.xtemp.user_management.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xtemp.user_management.entity.dto.BaseAuthResponse;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;
    private String password;

    private Role role;
    private Boolean disabled;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @Getter
    @AllArgsConstructor
    public enum Role {
        ADMIN("admin"),
        USER("user");

        @EnumValue
        @JsonValue
        private final String value;

        @JsonCreator
        public static Role fromValue(String input) {
            for (Role role : Role.values()) {
                if (role.name().equalsIgnoreCase(input) || role.value.equalsIgnoreCase(input)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Invalid role: " + input);
        }
    }

    public BaseAuthResponse.UserDTO toUserDTO() {
        return BaseAuthResponse.UserDTO.builder()
                .id(this.id)
                .username(this.username)
                .role(this.role)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}