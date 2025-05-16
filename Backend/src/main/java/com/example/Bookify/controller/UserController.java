package com.example.Bookify.controller;

import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.dto.user.UserDetailsResponse;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.enums.UserRole;
import com.example.Bookify.service.AuthenticationService;
import com.example.Bookify.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PutMapping("/activate/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void activateUser(@PathVariable int userId)
    {
        userService.activateUser(userId);
    }
    @PutMapping("/deactivate/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateUser
            ( @PathVariable int userId)
    {
        userService.deactivateUser(userId);
    }

    @PostMapping("/create-verifier")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public UserDetailsResponse createVerifierPerson
            (@Valid @RequestBody RegisterRequest request)
    {
       return authenticationService.register(request, UserRole.VERIFIER);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDetailsResponse> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size){
        return userService.getAllUsersPaged(page,size);
    }

}
