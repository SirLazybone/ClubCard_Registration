package com.example.ClubCard.data.models.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN"),
    USER_NOT_ACTIVATED("USER_NOT_ACTIVATED"),
    ADMIN_NOT_ACTIVATED("ADMIN_NOT_ACTIVATED");

    private final String name;

    Role(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String getAuthority() {
        return this.toString();
    }


}
