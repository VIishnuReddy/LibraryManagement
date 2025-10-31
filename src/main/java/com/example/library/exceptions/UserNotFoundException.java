package com.example.library.exceptions;

public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
