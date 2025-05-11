package com.example.Bookify.entity.user;


import com.example.Bookify.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users",indexes = {
        @Index(name = "idx_users_email", columnList = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;


    @Column(name = "user_name",nullable = false)
    @Size(min = 3,message = "user name must be at least three charchters")
    private String name;


    @Column(name = "email",unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active")
    private Boolean isActive ;

    @NotNull(message = "role can't be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private UserRole role;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
