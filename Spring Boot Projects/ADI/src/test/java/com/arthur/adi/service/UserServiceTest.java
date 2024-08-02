package com.arthur.adi.service;

import com.arthur.adi.dto.CreateUserDto;
import com.arthur.adi.dto.UpdateUserDto;
import com.arthur.adi.entity.User;
import com.arthur.adi.exception.AdiException;
import com.arthur.adi.exception.UserNotFoundException;
import com.arthur.adi.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> idArgumentCaptor;

    @Nested
    class CreateUser {

        @Test
        @DisplayName("Should Create a User")
        void shouldCreateUser() {
            // Arrange
            var user = new User(
                    "username",
                    "email@email.com",
                    "123");
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "123");
            // Act
            var output = userService.createUser(input);
            // Assert
            assertNotNull(output);
            assertEquals(userArgumentCaptor.getValue().getUsername(), output.getUsername());
            assertEquals(userArgumentCaptor.getValue().getEmail(), output.getEmail());
            assertEquals(userArgumentCaptor.getValue().getPassword(), output.getPassword());
        }

        @Test
        @DisplayName("Should Throw Exception When Error Occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            doThrow(new AdiException()).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "123");
            // Act & Assert
            assertThrows(AdiException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class UpdateUser {
        @Test
        void shouldUpdateUser() {
            // Arrange
            var userId = UUID.randomUUID();
            var oldUser = new User(
                    userId,
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    Instant.now(),
                    null
            );
            var updatedUser = new User(
                    UUID.randomUUID(),
                    "username2",
                    "email2@email.com",
                    "1234",
                    Instant.now(),
                    Instant.now(),
                    null
            );
            var updateDto = new UpdateUserDto(
                    "username2",
                    "email2@email.com",
                    "1234"
            );
            doReturn(Optional.of(oldUser)).when(userRepository).findById(idArgumentCaptor.capture());
            doReturn(updatedUser).when(userRepository).save(userArgumentCaptor.capture());
            // Act
            var output = userService.updateUser(userId.toString(), updateDto);
            // Assert
            assertNotNull(output);
            assertEquals(userArgumentCaptor.getValue().getId(), output.getId());
            assertEquals(userArgumentCaptor.getValue().getUsername(), output.getUsername());
            assertEquals(userArgumentCaptor.getValue().getEmail(), output.getEmail());
            assertEquals(userArgumentCaptor.getValue().getPassword(), output.getPassword());
        }

        @Test
        @DisplayName("Should Throw Exception When Error Occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            var oldUser = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    Instant.now(),
                    null
            );
            var userId = oldUser.getId();
            doThrow(new UserNotFoundException(userId.toString())).when(userRepository).findById(idArgumentCaptor.capture());
            // Act & Assert
            assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId.toString()));
        }
    }

    @Nested
    class GetAllUsers {
        @Test
        @DisplayName("Should Get All Users")
        void shouldGetAllUsers() {
            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();
            // Act
            var output = userService.getAllUsers();
            // Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }

        @Test
        @DisplayName("Should Throw Exception When Error Occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            doThrow(new AdiException()).when(userRepository).findAll();
            // Act & Assert
            assertThrows(AdiException.class, () -> userService.getAllUsers());
        }
    }

    @Nested
    class GetUserById {
        @Test
        @DisplayName("Should Get User By Id")
        void shouldGetUserById() {
            // Arrange
            var userId = UUID.randomUUID();
            var user = new User(
                    userId,
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(idArgumentCaptor.capture());
            // Act
            var output = userService.getUserById(userId.toString());
            // Assert
            assertNotNull(output);
            assertEquals(user.getId(), output.getId());
        }

        @Test
        @DisplayName("Should Throw Exception When Error Occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            var userId = UUID.randomUUID();
            doThrow(new UserNotFoundException(userId.toString())).when(userRepository).findById(idArgumentCaptor.capture());
            // Act & Assert
            assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId.toString()));
        }
    }

    @Nested
    class DeleteUserById {
        @Test
        void deleteUserById() {
            // Arrange
            var userId = UUID.randomUUID();
            var user = new User(
                    userId,
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(idArgumentCaptor.capture());
            doNothing().when(userRepository).delete(user);
            // Act
            var output = userService.deleteUserById(userId.toString());
            // Assert
            assertNotNull(output);
            assertEquals(userId, output.getId());
            assertEquals(user.getUsername(), output.getUsername());
            assertEquals(user.getEmail(), output.getEmail());
            assertEquals(user.getPassword(), output.getPassword());
        }

        @Test
        @DisplayName("Should Throw Exception When Error Occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            var userId = UUID.randomUUID();
            doThrow(new UserNotFoundException(userId.toString())).when(userRepository).findById(idArgumentCaptor.capture());
            // Act & Assert
            assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId.toString()));
        }
    }
}