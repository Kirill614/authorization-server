package com.example.demo.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String role){
        super(String.format("Role %s not found", role));
    }
}
