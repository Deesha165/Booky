package com.example.Bookify.dto.user;


import com.example.Bookify.enums.UserRole;
import lombok.Builder;

@Builder
public record UserDetailsResponse
        (  int id,
           String name,
          String email,
         Boolean isActive,
         UserRole role
        )
{

}
