package com.cse19.ue.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        int strength = 10; // number of rounds
        String salt = "6B59703373367639"; // salt value
        return new BCryptPasswordEncoder(strength, new SecureRandom(salt.getBytes()));
    }
}
