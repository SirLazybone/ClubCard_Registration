package com.example.ClubCard.repositories;

import com.example.ClubCard.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    int deleteByUsername(String username);
}