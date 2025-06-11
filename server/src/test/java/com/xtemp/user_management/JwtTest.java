package com.xtemp.user_management;

import com.xtemp.user_management.config.JwtProperties;
import com.xtemp.user_management.entity.pojo.User;
import com.xtemp.user_management.service.impl.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.Date;

import static com.xtemp.user_management.config.JwtProperties.ACCESS_TOKEN_DURATION;

@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private JwtService jwtService;


    @Test
    void generateJwt() {
        String secret = jwtProperties.getAccessSecret();
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        String jwt = Jwts.builder()
                .setSubject("xtempy")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_DURATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("Generated JWT:\n" + jwt);
    }

    @Test
    void testJwtFlow() {
        User user = User.builder()
                .username("xtempy")
                .password("password")
                .build();

        String token = jwtService.generateToken(user);

        System.out.println("JWT: " + token);
        System.out.println("Username: " + jwtService.extractUsername(token));
        System.out.println("All Claims: " + jwtService.extractAllClaims(token));
    }

    @Test
    void testJwtExpired() {
        User user = User.builder()
                .username("xtempy")
                .password("password")
                .build();

        String token = jwtService.generateToken(user, 0);
        System.out.println(jwtService.isTokenValid(token, user));
    }

}
