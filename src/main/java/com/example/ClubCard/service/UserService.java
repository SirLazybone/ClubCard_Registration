package com.example.ClubCard.service;

import com.example.ClubCard.configuration.PasswordEncoderConfig;
import com.example.ClubCard.data.models.User;
import com.example.ClubCard.data.models.forms.ChangePasswordForm;
import com.example.ClubCard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoderConfig passwordEncoderConfig;


    public User saveNew(User user, boolean encode_password) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new ValidationException("Пользователем с таким логином уже существует.");
        }
        if (encode_password) {
            user.setPassword(passwordEncoderConfig.getPasswordEncoder().encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    public User saveNew(User user){
        return saveNew(user, true);
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;

    }

    public List<User> loadAll() {
        return userRepository.findAll();
    }

    @Transactional
    public int delete(String username) {
        return userRepository.deleteByUsername(username);
    }


    public User updatePassword(User user, ChangePasswordForm changePasswordForm) {
        if (!passwordEncoderConfig.getPasswordEncoder().matches(changePasswordForm.getOldPassword(), user.getPassword())) {
            throw new ValidationException("Неправильный старый пароль");
        }
        if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getNewPasswordConfirmation())) {
            throw new ValidationException("Пароли не совпадают");
        }
        if (changePasswordForm.getNewPassword().equals(changePasswordForm.getOldPassword())) {
            throw new ValidationException("Новый пароль не может совпадать со старым");
        }
        user.setPassword(passwordEncoderConfig.getPasswordEncoder().encode(changePasswordForm.getNewPassword()));
        userRepository.save(user);
        return user;
    }
}
