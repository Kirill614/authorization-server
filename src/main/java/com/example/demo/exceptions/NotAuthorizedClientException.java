package com.example.demo.exceptions;

public class NotAuthorizedClientException extends RuntimeException{
    public NotAuthorizedClientException(String msg){
        super(msg);
    }
}
