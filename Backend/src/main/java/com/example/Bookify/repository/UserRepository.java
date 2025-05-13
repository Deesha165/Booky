package com.example.Bookify.repository;

import com.example.Bookify.entity.user.User;
import com.example.Bookify.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByRole(UserRole userRole);
}
