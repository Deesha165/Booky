package com.example.Bookify.dto.auth;

import com.example.Bookify.enums.UserRole;
import lombok.Builder;

@Builder
public record UserClaims(
        UserRole userRole,
        String name
) {
}
