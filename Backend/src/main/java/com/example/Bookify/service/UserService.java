package com.example.Bookify.service;

import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.entity.user.User;

public interface UserService {

    User getUser(int userId);


    void activateUser(int userId);

    void deactivateUser(int userId);



}
