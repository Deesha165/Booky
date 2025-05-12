package com.example.Bookify.controller;

import com.example.Bookify.dto.auth.AuthenticationRequest;
import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.entity.user.User;
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



  /*  @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest logInRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(logInRequest));
    }
*/
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public User singUp(@Valid @RequestBody RegisterRequest signUpRequest) {
        return authenticationService.register(signUpRequest);
    }
}
