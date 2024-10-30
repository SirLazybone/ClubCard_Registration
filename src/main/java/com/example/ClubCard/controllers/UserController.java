package com.example.ClubCard.controllers;

import com.example.ClubCard.configuration.PasswordEncoderConfig;
import com.example.ClubCard.data.models.Enum.Role;
import com.example.ClubCard.data.models.User;
import com.example.ClubCard.data.models.forms.ChangePasswordForm;
import com.example.ClubCard.data.models.forms.RegistrationForm;
import com.example.ClubCard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.ValidationException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoderConfig passwordEncoderConfig;
    @GetMapping("/user_data")
    public String userData(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.loadUserByUsername(username);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("fatherName", user.getFatherName());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            model.addAttribute("birthdate", user.getBirthdate().format(formatter));
        } catch (Exception ignored) {
        }
        model.addAttribute("role", user.getRole().toString());

        return "/user_data";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());

        return "/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("registrationForm")RegistrationForm registrationForm, Model model) {
        User user = User.builder().username(registrationForm.getUsername()).role(Role.USER).password(passwordEncoderConfig.getPasswordEncoder().encode(registrationForm.getPassword()))
                .name(registrationForm.getName()).surname(registrationForm.getSurname())
                .fatherName(registrationForm.getFatherName()).email(registrationForm.getEmail())
                .phone(registrationForm.getPhone()).birthdate(registrationForm.getBirthDate()).build();
        try {
            userService.saveNew(user);
        } catch (ValidationException e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/info/{username}")
    public HashMap<String, String> getInfo(@PathVariable String username) {
        User user = userService.loadUserByUsername(username);
        HashMap<String, String> info = new HashMap<>();
        info.put("email", user.getEmail());
        info.put("role", user.getRole().toString());
        return info;
    }

    @GetMapping("/change_password")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "/change_password";
    }

    @PostMapping("/change_password")
    public String changePassword(@ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.loadUserByUsername(username);
        try {
            userService.updatePassword(user, changePasswordForm);
            return "redirect:/login";
        } catch (ValidationException e) {
            model.addAttribute("error", e.getMessage());
            return "/change_password";
        }

    }
}
