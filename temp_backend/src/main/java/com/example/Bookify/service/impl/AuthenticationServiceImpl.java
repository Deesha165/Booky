package com.example.Bookify.service.impl;

import com.example.Bookify.dto.auth.AuthenticationRequest;
import com.example.Bookify.dto.auth.AuthenticationResponse;
import com.example.Bookify.dto.auth.RefreshTokenRequest;
import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.dto.user.UserDetailsResponse;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.enums.UserRole;
import com.example.Bookify.exception.DuplicateResourceException;
import com.example.Bookify.exception.EntityNotFoundException;
import com.example.Bookify.exception.IllegalActionException;
import com.example.Bookify.exception.ResourceType;
import com.example.Bookify.mapper.UserMapper;
import com.example.Bookify.repository.UserRepository;
import com.example.Bookify.security.JwtService;
import com.example.Bookify.security.UserDetailsService;
import com.example.Bookify.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private  final  UserDetailsService userDetailsService;

    @Override
    public UserDetailsResponse register(RegisterRequest request, UserRole userRole) {

        User user = userMapper.mapFromSignUpRequestToUser(request, userRole);

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateResourceException("This email already exists", ResourceType.EMAIL);
        }

        User savedUser=userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));

        User user=userRepository.findByEmail(request.email()).orElseThrow(()->new EntityNotFoundException("user not found"));

        String accessToken=jwtService.generateToken(user);

        String refreshToken=jwtService.generateRefreshToken(user);

        return userMapper.toAuthResponse(accessToken,refreshToken);
    }

    @Override
    public AuthenticationResponse generateAccessToken(RefreshTokenRequest request) {

        String refreshToken = request.refreshToken();

        String username = jwtService.extractUsername(refreshToken);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw  new IllegalActionException("Refresh Token is not valid");
        }

        String newAccessToken = jwtService.generateToken(user);
        return userMapper.toAuthResponse(newAccessToken,refreshToken);
    }
}
