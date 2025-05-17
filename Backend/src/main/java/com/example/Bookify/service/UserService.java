package com.example.Bookify.service;

import com.example.Bookify.dto.auth.RegisterRequest;
import com.example.Bookify.dto.user.PasswordChangeRequest;
import com.example.Bookify.dto.user.UserDetailsResponse;
import com.example.Bookify.entity.user.User;
import org.springframework.data.domain.Page;

public interface UserService {

    User getUser(int userId);


    void activateUser(int userId);

    void deactivateUser(int userId);


    Page<UserDetailsResponse> getAllUsersPaged(int page, int size);
    int changeUserAccountPassword(PasswordChangeRequest passwordChangeRequest);

}
