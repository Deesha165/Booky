package com.example.Bookify.controller;

import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RefreshTokenRequest;
import com.example.Bookify.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/token")
public class TokenController {

private final AuthenticationService authenticationService;

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
public AuthenticationResponse obtainRefreshToken(@RequestBody RefreshTokenRequest request){

        return authenticationService.generateAccessToken(request);
}


}
