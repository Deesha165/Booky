package com.example.Bookify.security;


import com.example.Bookify.entity.user.User;
import com.example.Bookify.exception.EntityNotFoundException;
import com.example.Bookify.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String userEmail) {

        return userRepository.findByEmail(userEmail)
                .orElseThrow(()->new EntityNotFoundException("user with email "+userEmail+" doesn't exist"));
    }
}
