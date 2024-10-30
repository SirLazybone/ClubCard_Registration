package com.example.ClubCard.configuration;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Getter
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

}
