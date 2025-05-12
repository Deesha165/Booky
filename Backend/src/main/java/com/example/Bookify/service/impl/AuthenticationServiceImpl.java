package com.example.Bookify.service.impl;

import com.example.Bookify.dto.auth.AuthenticationRequest;
import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.exception.DuplicateResourceException;
import com.example.Bookify.exception.ResourceType;
import com.example.Bookify.mapper.UserMapper;
import com.example.Bookify.repository.UserRepository;
import com.example.Bookify.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public User register(RegisterRequest request) {

        User user = userMapper.mapFromSignRequestToUser(request);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("This email already exists", ResourceType.EMAIL);
        }

        User savedUser=userRepository.save(user);

        return savedUser;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
