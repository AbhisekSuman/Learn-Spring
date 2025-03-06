package com.kodnest.demo.controller;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TokenValidationController {

    private final SecretKey signKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @GetMapping("/validate")
    public String saveCookie(@RequestParam String username, @RequestParam String role, HttpServletResponse response) {
        String token = generateToken(username, role);

        Cookie cookie = new Cookie("authToken", token);
        cookie.setMaxAge(360000);

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);

        return "Success";
    }

    public String generateToken(String username, String role) {
        JwtBuilder builder = Jwts.builder();

        builder.setSubject(username);
        builder.claim("role", role);
        builder.setIssuedAt(new Date());
        builder.setExpiration(new Date(System.currentTimeMillis() + 36000000));

        builder.signWith(signKey);
        String token = builder.compact();

        return token;
    }

    @GetMapping("/get")
    public String getData(HttpServletRequest request) {
        try {
            Cookie cookies[] = request.getCookies();
            Cookie cookie = cookies[0];
//            String token = cookie.getAttribute("authToken");
            String token = cookie.getValue();

            JwtParserBuilder builder = Jwts.parserBuilder();
            builder.setSigningKey(signKey);
            JwtParser parser = builder.build();
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();

            String subject = claims.getSubject();
            String  role = (String) claims.get("role");

            return "Subject: " + subject + " Role: " + role;
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid token!";
        }
    }

    @PostMapping("/getUser")
    public String getUser(@RequestParam String token) {
        try {
            JwtParserBuilder builder = Jwts.parserBuilder();
            builder.setSigningKey(signKey);
            JwtParser parser = builder.build();
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();

            String subject = claims.getSubject();
            String  role = (String) claims.get("role");

            return "Subject: " + subject + " Role: " + role;
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid";
        }

    }
}
