package com.example.Bookify.dto.auth;


import com.example.Bookify.enums.UserRole;
import lombok.Builder;

@Builder
public record AuthenticationResponse
        (
               String accessToken,
              String refreshToken
        ) {
}
