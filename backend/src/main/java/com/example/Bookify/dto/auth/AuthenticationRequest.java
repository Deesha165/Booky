package com.example.Bookify.dto.auth;


import com.example.Bookify.util.annotation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AuthenticationRequest
        (
                @Email(message = "Invalid email format")
                @NotBlank
                String email,
                @NotBlank(message = "Password is required")
                @Size(min = 8, message = "Password must be at least 8 characters long")
                @ValidPassword
                String password
        )
{
}
