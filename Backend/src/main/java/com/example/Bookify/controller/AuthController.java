package com.example.Bookify.controller;

import com.example.Bookify.dto.auth.AuthenticationRequest;
import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.dto.user.UserDetailsResponse;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.enums.UserRole;
import com.example.Bookify.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;



    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest logInRequest) {
        return authenticationService.authenticate(logInRequest);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetailsResponse singUp(@Valid @RequestBody RegisterRequest signUpRequest) {
        return authenticationService.register(signUpRequest, UserRole.USER);
    }
}
