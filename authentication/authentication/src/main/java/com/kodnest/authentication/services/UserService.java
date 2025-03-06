package com.kodnest.authentication.services;

import com.kodnest.authentication.entities.User;
import com.kodnest.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public String authenticateUser(String name, String pw) {
        User user = repository.findByName(name);

        if (user == null) {
            return "Invalid user or password";
        }
        else {
            if (BCrypt.checkpw(pw, user.getPassword())) {
                return user.getRole();
            } else {
                return "error";
            }
        }
    }

    public boolean saveUser(String name, String password, String role) {
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(name, encryptedPassword, role);
        try {
            repository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
