package com.example.Bookify.repository;

import com.example.Bookify.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Object> findByEmail(String email);
}
