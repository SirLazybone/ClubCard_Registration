package com.example.ClubCard.configuration;

import com.example.ClubCard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.formLogin(form -> form
                        .loginPage("/login").defaultSuccessUrl("/user_data", true))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/users").hasAuthority("ADMIN")
                        .requestMatchers("/user_data").authenticated()
                        .requestMatchers("/change_password").authenticated()
                        .requestMatchers("/").authenticated()
                        .requestMatchers("/registration").permitAll()
                        .anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .logout((logout) -> logout.logoutSuccessUrl("/login")).build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoderConfig.getPasswordEncoder());
    }
}