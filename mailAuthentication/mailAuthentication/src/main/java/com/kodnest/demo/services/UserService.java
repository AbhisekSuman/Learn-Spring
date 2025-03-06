package com.kodnest.demo.services;

import com.kodnest.demo.entities.User;
import com.kodnest.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class UserService {

    UserRepository repository;

    JavaMailSender mailSender;

    public UserService(UserRepository repository, JavaMailSender mailSender) {
        this.repository = repository;
        this.mailSender = mailSender;
    }

    public boolean login(String email, String password) {
        User user = repository.findByEmail(email);

        if (user != null) {
            if (password.equals(user.getPassword())) {
                generateOTP(user);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String verify(int otp, String email) {
        User user = repository.findByEmail(email);

        if (user.getOtp() == otp) {
            if (ChronoUnit.MINUTES.between(user.getTimestamp(), LocalDateTime.now()) < 1)
            {
                return user.getRole();
            }
            else {
                System.err.println("Minute extended: " + LocalDateTime.now() +  user.getTimestamp());
                return "fail";
            }
        }
        else {
            return "fail";
        }
    }

    public void generateOTP(User user) {
        Integer otp = new Random().nextInt(999999);
        LocalDateTime time = LocalDateTime.now();
        user.setOtp(otp);
        user.setTimestamp(time);
        repository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("nayaksuman115@gmail.com");
        message.setSubject("OTP Verification of Spring Boot Application");
        message.setTo(user.getEmail());
        message.setText("Your OTP for verification is:- " + otp + "\n" + "ତମ account hack କରିଦେବି otp  ଜଲ୍ଦି Abhi  କୁ ଦିଅ 😁😁😁 ");
//        message.setText();

        mailSender.send(message);
        System.out.println("Mail Sent");
    }
}
