package com.example.demo.exceptions;

public class RegisteredClientNotFoundException extends RuntimeException{
    public RegisteredClientNotFoundException(String id){
        super(String.format("Registered client with id %s not found", id));
    }
}
