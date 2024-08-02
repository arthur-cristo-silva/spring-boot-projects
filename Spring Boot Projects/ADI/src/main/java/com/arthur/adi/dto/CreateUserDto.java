package com.arthur.adi.dto;

import com.arthur.adi.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank(message = "The username is required.")
        @Size(min = 3, max = 20, message = "The length of username must be between 3 and 20 characters.")
        String username,
        @NotBlank(message = "The email is required.")
        @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
        String email,
        @NotBlank(message = "The password is required.")
        @Size(min = 3, max = 20, message = "The length of password must be between 3 and 20 characters.")
        String password
) {
    public User toUser() {
        return new User(
                username,
                email.toLowerCase(),
                password
        );
    }
}

