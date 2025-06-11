package com.xtemp.user_management.service;

import com.xtemp.user_management.entity.dto.UserCredentialsDTO;
import com.xtemp.user_management.entity.dto.WithRefreshToken;

public interface AuthService {
    WithRefreshToken register(UserCredentialsDTO request);
    WithRefreshToken login(UserCredentialsDTO request);
    WithRefreshToken refreshToken(String refreshToken);
    void logout(String refreshToken);
}
