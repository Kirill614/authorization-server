package com.example.demo.exceptions;

public class NotValidTokenException extends RuntimeException{
    public NotValidTokenException(){
        super("token is not valid");
    }
}
