package com.example.demo.exceptions;

public class RedirectUriDoesnotMatchException extends RuntimeException{
    public RedirectUriDoesnotMatchException(String redirectUri){
        super(String.format("Redirect URI %s in your auth code request " +
                "doesnot match with redirect uri of registered client", redirectUri));
    }
}
