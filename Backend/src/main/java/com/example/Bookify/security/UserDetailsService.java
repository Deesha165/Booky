package com.example.Bookify.security;


import com.example.Bookify.entity.user.User;

public interface UserDetailsService {
    User loadUserByUsername(String username);
}
