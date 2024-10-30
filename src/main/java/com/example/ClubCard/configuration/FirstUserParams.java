package com.example.ClubCard.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.GregorianCalendar;

@Getter
@NoArgsConstructor
public class FirstUserParams {
    private final String username = "admin";
    private final String password = "1234567890";
    private final String name = "Иван";
    private final String surname = "Иванов";
    private final String fatherName = "Иванович";
    private final String email = "test@mail.ru";
    private final String phone = "+71234567890";
    private final LocalDate birthDate = LocalDate.of(2000, 1, 1);
}
