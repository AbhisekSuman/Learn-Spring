package com.kodnest.salessavvy.services.adminservices;

import com.kodnest.salessavvy.entities.JWTToken;
import com.kodnest.salessavvy.entities.Role;
import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.repository.JWTTokenRepository;
import com.kodnest.salessavvy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {

    UserRepository userRepository;
    JWTTokenRepository tokenRepository;

    public AdminUserService(UserRepository userRepository, JWTTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public User findUserById(int userId){
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not fount with ID: " + userId));
    }

    public User modifyUser(int userId, String name, String email, String role) throws Exception{
        try {
            User user = findUserById(userId);

            if (name != null && !name.isEmpty())
                user.setUsername(name);
            if (email != null && !email.isEmpty())
                user.setEmail(email);
            if (role != null && !role.isEmpty())
                user.setRole(Role.valueOf(role));

            tokenRepository.deleteTokenByUserId(userId);

            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteUserById(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not Found"));

        userRepository.delete(user);
    }
}
