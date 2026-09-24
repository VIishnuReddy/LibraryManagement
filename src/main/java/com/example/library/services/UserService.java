package com.example.library.services;


import com.example.library.dtos.LoginRequestDto;
import com.example.library.dtos.UserRegistrationDto;
import com.example.library.dtos.UserResponseDto;
import com.example.library.exceptions.InvalidLoginFoundException;
import com.example.library.exceptions.UserAlreadyExistsException;
import com.example.library.models.User;
import com.example.library.reposiories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    public UserResponseDto registration(UserRegistrationDto registrationDto){
        if(userRepository.findByEmail(registrationDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with Email "+ registrationDto.getEmail()+" already present" +
                    "Please try to login or sing up with other Email");
        }

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());
        user.setName(registrationDto.getName());
        user.setMobileNumber(registrationDto.getPhoneNumber());
        userRepository.save(user);
        return new UserResponseDto(user.getId(), user.getEmail());
    }

    public UserResponseDto login(LoginRequestDto requestDto){

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() ->new InvalidLoginFoundException("Incorrect Email. Please check"));

        if(!user.getPassword().equals(requestDto.getPassword())){
            throw new InvalidLoginFoundException("Incorrect password");
        }

        return new UserResponseDto(user.getId(), user.getEmail());

    }

    public UserResponseDto getUserById(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDto(user.getId(), user.getEmail());
    }
}
