package com.example.Bookify.service.impl;

import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.dto.user.UserDetailsResponse;
import com.example.Bookify.entity.user.User;
import com.example.Bookify.exception.EntityNotFoundException;
import com.example.Bookify.exception.IllegalActionException;
import com.example.Bookify.mapper.UserMapper;
import com.example.Bookify.repository.UserRepository;
import com.example.Bookify.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public User getUser(int userId) {
        return userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User with id = "+userId+" doesn't exist"));
    }

    @Override
    @Transactional
    public void activateUser(int userId) {
        User user=getUser(userId);

        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(authenticatedUser.getId()!=userId){
                   user.setIsActive(true);
        }
        else throw new IllegalActionException("You can't activate your account");

    }

    @Override
    @Transactional
    public void deactivateUser(int userId) {
        User user=getUser(userId);

        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(authenticatedUser.getId()!=userId){
            user.setIsActive(false);
        }
        else throw new IllegalActionException("You can't deactivate yourself");

    }

    @Override
    public Page<UserDetailsResponse> getAllUsersPaged(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
       Page<User>users= userRepository.findAll(pageable);

        return users.map(userMapper::toUserResponse);
    }

}
