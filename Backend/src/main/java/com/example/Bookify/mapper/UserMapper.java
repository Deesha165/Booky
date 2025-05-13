package com.example.Bookify.mapper;

import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.dto.user.UserDetailsResponse;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
//remember to encode pass when add security

    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse toAuthResponse(String token)
    {
        return  AuthenticationResponse.builder().accessToken(token).build();
    }
    public User mapFromSignUpRequestToUser(RegisterRequest request, UserRole userRole) {
        return User.builder().
                name(request.name()).
                email(request.email()).
                password(passwordEncoder.encode(request.password())).
                role(userRole).
                isActive(true).
                build();
    }

    public UserDetailsResponse toUserResponse(User user) {
        return
                UserDetailsResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .isActive(user.getIsActive())
                        .build();
    }


}
