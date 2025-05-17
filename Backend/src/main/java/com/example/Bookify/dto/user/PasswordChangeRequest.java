package com.example.Bookify.dto.user;


import com.example.Bookify.util.annotation.ValidPassword;
import jakarta.validation.constraints.NotNull;

public record PasswordChangeRequest(

                                    @NotNull
                                    String oldPassword,

                                    @NotNull
                                     @ValidPassword
                                     String newPassword) {
}
