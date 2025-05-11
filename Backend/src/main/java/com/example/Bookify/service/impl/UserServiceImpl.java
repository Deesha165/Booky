package com.example.Bookify.service.impl;

import com.example.Bookify.entity.user.User;
import com.example.Bookify.exception.EntityNotFoundException;
import com.example.Bookify.repository.UserRepository;
import com.example.Bookify.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User getUser(int userId) {
        return userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User with id = "+userId+" doesn't exist"));
    }
}
