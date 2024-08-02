package com.arthur.adi.controller;

import com.arthur.adi.dto.AccountResponseDto;
import com.arthur.adi.dto.CreateAccountDto;
import com.arthur.adi.dto.CreateUserDto;
import com.arthur.adi.dto.UpdateUserDto;
import com.arthur.adi.entity.Account;
import com.arthur.adi.entity.User;
import com.arthur.adi.service.AccountService;
import com.arthur.adi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserDto userDto) {
        if (userDto == null) {
            return ResponseEntity.badRequest().body(null);
        }
        var user = userService.createUser(userDto);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id,
                                           @RequestBody @Valid UpdateUserDto userDto) {
        var user = userService.updateUser(id, userDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        var user = userService.deleteUserById(id);
        return ResponseEntity.ok(String.format("User %s with ID of %s was deleted.", user.getUsername(), id));
    }

    @PostMapping("{id}/accounts")
    public ResponseEntity<Account> createAccount(@PathVariable String id, @RequestBody @Valid CreateAccountDto accountDto) {
        var account = accountService.createAccount(id, accountDto);
        return ResponseEntity.ok(account);
    }

    @GetMapping("{id}/accounts")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(@PathVariable String id) {
        var account = accountService.getAllAccounts(id);
        return ResponseEntity.ok(account);
    }
}
