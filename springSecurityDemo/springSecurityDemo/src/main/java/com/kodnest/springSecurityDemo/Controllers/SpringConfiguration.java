package com.kodnest.springSecurityDemo.Controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//              Step-1 ->  Disable CSRF

        http.csrf(csrf -> {
            csrf.disable();
        });

//        Step-2 -> Configuration our Rules
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/public").permitAll();
            auth.requestMatchers("/admin").authenticated();
        });

//        Step-3 -> Decide what to display during authentication
        http.httpBasic(httpBasics -> {

        });

//        Step-4 -> Finalize the rules
        return http.build();
    }
}
