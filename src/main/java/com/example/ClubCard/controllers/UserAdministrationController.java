package com.example.ClubCard.controllers;

import com.example.ClubCard.data.models.Enum.Role;
import com.example.ClubCard.data.models.User;
import com.example.ClubCard.data.models.forms.NewUserForm;
import com.example.ClubCard.service.UserService;
import com.example.ClubCard.view.UserListItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ValidationException;
import java.util.List;

@Controller
@RequestMapping("users")
@SessionAttributes("newUserForm")
public class UserAdministrationController {
    @Autowired
    UserService userService;

    @ModelAttribute("newUserForm")
    public NewUserForm getForm() {
        return new NewUserForm();
    }

    @GetMapping("/new")
    public String newUserView(Model model, NewUserForm newUserForm) {
        model.addAttribute("newUserForm", new NewUserForm());
        return "/user_creation";
    }


    @PostMapping("/new")
    public String newUser(Model model, @ModelAttribute("newUserForm") NewUserForm newUserForm,
                          BindingResult result, SessionStatus sessionStatus) {
        User user = User.builder().username(newUserForm.getUsername()).password(newUserForm.getPassword()).role(
                newUserForm.isAdmin() == true ?
                        Role.ADMIN : Role.USER)
                .name(newUserForm.getName()).surname(newUserForm.getSurname()).fatherName(newUserForm.getFatherName())
                .phone(newUserForm.getPhone()).email(newUserForm.getEmail()).birthdate(newUserForm.getBirthDate()).build();
        try {
            userService.saveNew(user, true);
            sessionStatus.setComplete();
            return "redirect:/users";
        } catch (ValidationException e) {
            model.addAttribute("error", e.getMessage());
            return newUserView(model, newUserForm);
        }

    }

    public String newUserDataView(Model model, @ModelAttribute("newUserForm") NewUserForm newUserForm) {
        model.addAttribute("newUserForm", newUserForm);
        return "/user_data";
    }

    @GetMapping
    public String userList(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        List<UserListItemView> userListItemViewList = userService.loadAll().stream().map(user -> new UserListItemView(user)).toList();
        userListItemViewList.stream().forEach(userListItemView -> {
            if (userListItemView.getUsername().equals(userDetails.getUsername())) {
                userListItemView.setCurrentUser(true);
            }
        });
        model.addAttribute("users", userListItemViewList);
        return "/users";
    }

    @PostMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username, @AuthenticationPrincipal UserDetails userDetails) {
        if (!userDetails.getUsername().equals(username)) {
            userService.delete(username);
        }
        else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Вы не можете удалить сами себя");
        }
        return "redirect:/users";
    }

}
