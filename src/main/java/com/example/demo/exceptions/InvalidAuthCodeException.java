package com.example.demo.exceptions;

public class InvalidAuthCodeException extends RuntimeException {
    public InvalidAuthCodeException(){
        super("Invalid authorization code");
    }
}
