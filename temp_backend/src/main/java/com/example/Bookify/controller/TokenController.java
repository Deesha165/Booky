package com.example.Bookify.controller;

import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RefreshTokenRequest;
import com.example.Bookify.dto.auth.UserClaims;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.service.AuthenticationService;
import com.example.Bookify.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/token")
public class TokenController {
private final  AuthUtil authUtil;
private final AuthenticationService authenticationService;

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
public AuthenticationResponse obtainRefreshToken(@RequestBody RefreshTokenRequest request){

        return authenticationService.generateAccessToken(request);
}


@GetMapping("/user-claims")
@ResponseStatus(HttpStatus.OK)
@PreAuthorize("isAuthenticated()")
    public UserClaims obtainUserClaims(){

       User user= authUtil.getAuthenticatedUser();
       return UserClaims.builder()
               .userRole(user.getRole()).name(user.getName()).build();
}

}
