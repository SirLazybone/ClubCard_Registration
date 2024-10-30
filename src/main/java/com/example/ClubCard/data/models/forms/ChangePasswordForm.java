package com.example.ClubCard.data.models.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordForm{
    private String oldPassword;

    private String newPassword;
    private String newPasswordConfirmation;

}
