package com.example.ClubCard.configuration;

import com.example.ClubCard.data.models.Enum.Role;
import com.example.ClubCard.data.models.User;
import com.example.ClubCard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        CreateFirstUser();
    }

    @Autowired
    UserService userService;

    private final FirstUserParams firstUserParams = new FirstUserParams();
    public void CreateFirstUser() {
        User user = null;
        try {
            user = userService.loadUserByUsername(firstUserParams.getUsername());
        } catch (UsernameNotFoundException e) {
            user = User.builder().username(firstUserParams.getUsername()).password(firstUserParams.getPassword()).role(Role.ADMIN)
                    .name(firstUserParams.getName()).surname(firstUserParams.getSurname()).fatherName(firstUserParams.getFatherName())
                    .email(firstUserParams.getEmail()).phone(firstUserParams.getPhone()).birthdate(firstUserParams.getBirthDate())
                    .build();
            userService.saveNew(user);
        }
    }
}
