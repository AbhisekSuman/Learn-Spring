package com.kodnest.salessavvy.services;

import com.kodnest.salessavvy.entities.JWTToken;
import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.repository.JWTTokenRepository;
import com.kodnest.salessavvy.repository.UserRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final Key SIGNIN_KEY;

    private final UserRepository userRepository;
    private final JWTTokenRepository jwtTokenRepository;
    private final BCryptPasswordEncoder encoder;

    public AuthService(UserRepository userRepository, JWTTokenRepository jwtTokenRepository, @Value("${jwt.secret}") String jwtSecret) {
        this.userRepository = userRepository;
        this.jwtTokenRepository = jwtTokenRepository;
        this.encoder = new BCryptPasswordEncoder();
        this.SIGNIN_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public User authenticate(String username, String password) throws RuntimeException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("Username is not valid");
        }
        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Enter Valid username and password");
        }

        return user;
    }

    public String generateToken(User user) {
        JWTToken token = jwtTokenRepository.findByUserId(user.getId());

        if (token != null && token.getExpires().isAfter(LocalDateTime.now())) {
            return token.getToken();
        } else {
            if (token != null) {
                jwtTokenRepository.delete(token);
            }
            return generateNewToken(user);
        }
    }

    public String generateNewToken(User user) {
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user.getUsername());
        builder.claim("role", user.getRole().name());
        builder.setIssuedAt(new Date());
        builder.setExpiration(new Date(new Date().getTime() + 86400000L));
        builder.signWith(SIGNIN_KEY);
        String token = builder.compact();
        saveToken(token, user);
        return token;
    }

    public void saveToken(String token, User user) {
        JWTToken jwtToken = new JWTToken(user, token, LocalDateTime.now(), LocalDateTime.now().plusHours(10));
        jwtTokenRepository.save(jwtToken);
    }

    public boolean validateToken(String token) {
        try {
            // Parse and validate token
            Jwts.parserBuilder()
                    .setSigningKey(SIGNIN_KEY)
                    .build()
                    .parseClaimsJws(token);

            // Check token existence in DB
            Optional<JWTToken> jwtToken = jwtTokenRepository.findByToken(token);
            return jwtToken.isPresent() && jwtToken.get().getExpires().isAfter(LocalDateTime.now());

        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String extractUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(SIGNIN_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public void logout(User user) {
        int userId = user.getId();

        JWTToken token = jwtTokenRepository.findByUserId(userId);

        if (token != null) {
            jwtTokenRepository.deleteTokenByUserId(userId);
        } else {
            throw new RuntimeException("Token not exist");
        }
    }
}
