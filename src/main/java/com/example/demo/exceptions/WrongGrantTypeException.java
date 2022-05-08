package com.example.demo.exceptions;

public class WrongGrantTypeException extends RuntimeException{
    public WrongGrantTypeException(){
        super("wrong grant type");
    }
}
