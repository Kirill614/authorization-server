package com.example.demo.exceptions;

public class WrongResponseTypeException extends RuntimeException{
    public WrongResponseTypeException(String responseType){
        super(String.format("Response type %s doesnot supports", responseType));
    }
}
