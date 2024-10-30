package com.example.ClubCard.view;

import com.example.ClubCard.data.models.Enum.Role;
import com.example.ClubCard.data.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class UserListItemView {
    private final String admin = "Администратор";

    private String username;
    private String role;

    @Setter
    private boolean currentUser = false;

    public UserListItemView(User user) {
        this.username = user.getUsername();
    }


}
