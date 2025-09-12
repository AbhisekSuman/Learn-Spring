package com.kodnest.salessavvy.services;

import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder encoder;

    public UserService(UserRepository repository) {
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public User registration(User user) throws RuntimeException {
        String email = user.getEmail();
        String username = user.getUsername();

        if (repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email is already Exist");
        }

        if (repository.findByUsername(username) != null) {
            throw new RuntimeException("Username is already taken");
        }

        String encryptedPassword = encoder.encode(user.getPassword());

        user.setPassword(encryptedPassword);

        return repository.save(user);
    }
}
