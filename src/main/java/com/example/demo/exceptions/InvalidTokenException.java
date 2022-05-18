package com.example.demo.exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(){
        super("token is not valid");
    }
}
