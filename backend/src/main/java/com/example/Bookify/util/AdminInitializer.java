package com.example.Bookify.util;


import com.example.Bookify.entity.user.User;
import com.example.Bookify.enums.UserRole;
import com.example.Bookify.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AdminInitializer implements CommandLineRunner {

       private final UserRepository userRepository;
       private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if(!userRepository.existsByRole(UserRole.ADMIN)) {

            User user= User.builder().name("mustafa").email("mustafatarek112@gmail.com")
                    .password(passwordEncoder.encode("12345Mustafa@Tarek!"))
                    .role(UserRole.ADMIN)
                    .isActive(true)
                    .build();
            userRepository.save(user);
        }
    }



}
