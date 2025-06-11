package com.xtemp.user_management.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("refresh_tokens")
public class RefreshToken {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    @TableField("token")
    private String token;

    private LocalDateTime expiresAt;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    private Boolean revoked;
}
