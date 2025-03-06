package com.kodnest.demo.services;

import com.kodnest.demo.entities.User;
import com.kodnest.demo.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

    @Autowired
    HttpSession session;

    public String login(String email, String password) {
        User user = repo.findByEmail(email);

        if (user != null) {
            if (password.equals(user.getPassword())) {
                int otp = generateOTP();
                saveOtp(user, otp);
                return "Success";
            } else {
                return "Failed";
            }
        } else {
            return "Failed";
        }
    }


    public String verifyOTP(int otp, String email) {
        User user = repo.findByEmail(email);

        if (otp == user.getOtp()) {
            return user.getRole();
        } else {
         return "failed";
        }
    }

    public int generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        System.out.println("Your OTP is: " + otp);
        session.setAttribute("otp", otp);
        return otp;
    }

    public void saveOtp(User user, int otp) {
        user.setOtp(otp);
        repo.save(user);
    }
}
