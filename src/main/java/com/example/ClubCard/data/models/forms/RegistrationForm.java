package com.example.ClubCard.data.models.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.GregorianCalendar;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationForm {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String fatherName;
    private String email;
    private String phone;
    private LocalDate birthDate;
}
