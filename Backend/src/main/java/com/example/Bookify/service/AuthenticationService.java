package com.example.Bookify.service;


import com.example.Bookify.dto.auth.AuthenticationRequest;
import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.entity.user.User;

public interface AuthenticationService {
    User register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

}
