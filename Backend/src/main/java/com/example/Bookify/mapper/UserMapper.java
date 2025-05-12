package com.example.Bookify.mapper;

import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
//remember to encode pass when add security
    public User mapFromSignRequestToUser(RegisterRequest request) {
        return User.builder().
        name(request.name()).
                email(request.email()).
                password(request.password()).
                role(UserRole.valueOf("USER")).
                isActive(true).
                build();
    }
}
