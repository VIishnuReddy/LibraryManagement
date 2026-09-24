package com.example.library.controllers;

import com.example.library.dtos.LoginRequestDto;
import com.example.library.dtos.UserRegistrationDto;
import com.example.library.dtos.UserResponseDto;
import com.example.library.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserResponseDto> registration(@RequestBody UserRegistrationDto registrationDto){
        return ResponseEntity.ok(userService.registration(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto requestDto){
        return ResponseEntity.ok(userService.login(requestDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable String userId) {

        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
