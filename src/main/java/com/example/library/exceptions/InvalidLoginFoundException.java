package com.example.library.exceptions;

public class InvalidLoginFoundException extends  RuntimeException{
    public InvalidLoginFoundException(String message){
        super(message);
    }
}
