package com.arthur.adi.service;

import com.arthur.adi.dto.CreateUserDto;
import com.arthur.adi.dto.UpdateUserDto;
import com.arthur.adi.entity.User;
import com.arthur.adi.exception.UserNotFoundException;
import com.arthur.adi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(CreateUserDto userDto) {
        return userRepository.save(userDto.toUser());
    }

    public User updateUser(String userId, UpdateUserDto userDto) {
        var user = getUserById(userId);
        if (userDto.username() != null) {
            user.setUsername(userDto.username());
        }
        if (userDto.email() != null) {
            user.setEmail(userDto.email());
        }
        if (userDto.password() != null) {
            user.setPassword(userDto.password());
        }
        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User deleteUserById(String id) {
        var user = getUserById(id);
        userRepository.delete(user);
        return user;
    }

}
