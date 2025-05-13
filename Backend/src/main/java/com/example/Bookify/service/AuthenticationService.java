package com.example.Bookify.service;


import com.example.Bookify.dto.auth.AuthenticationRequest;
import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.dto.user.UserDetailsResponse;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.enums.UserRole;

public interface AuthenticationService {
    UserDetailsResponse register(RegisterRequest request , UserRole userRole);

    AuthenticationResponse authenticate(AuthenticationRequest request);

}
