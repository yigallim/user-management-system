package com.xtemp.user_management.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WithRefreshToken extends BaseAuthResponse {
    private String refreshToken;

    public WithRefreshToken(String accessToken, BaseAuthResponse.UserDTO user, String refreshToken) {
        super(accessToken, user);
        this.refreshToken = refreshToken;
    }
}
